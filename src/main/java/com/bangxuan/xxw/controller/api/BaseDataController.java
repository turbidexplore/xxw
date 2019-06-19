package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.UnitMapper;
import com.bangxuan.xxw.dao.UserMapper;
import com.bangxuan.xxw.entity.*;
import com.bangxuan.xxw.service.*;
import com.bangxuan.xxw.util.FileOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.*;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Api(value = "By basedata",description = "基础数据")
@RestController
@RequestMapping("/basedata")
@CrossOrigin
public class BaseDataController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductClassService productClassService;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileLibService fileLibService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MongoTemplate mt; //自动注入MongoTemplate

    @GetMapping("/getStatistics")
    @ApiOperation("数据统计")
    public Mono<Message> getStatistics(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spareparts",productClassService.getSKUCount());
        jsonObject.put("spareparts_class",productClassService.getCount());
        jsonObject.put("brand",companyService.getCount());
        jsonObject.put("pdf",productClassService.getPDFCount());
        jsonObject.put("threeD",productClassService.get3DCount());


        return Mono.just(Message.SCUESSS(Message.SECUESS,jsonObject));
    }

    public List<JSONObject> jsonArraySort(String KEY_NAME,String jsonArrStr) {
        List<JSONObject> list = JSONArray.parseArray(jsonArrStr, JSONObject.class);
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                int a = o1.getInteger(KEY_NAME);
                int b = o2.getInteger(KEY_NAME);
                if (a < b) {
                    return 1;
                } else if(a == b) {
                    return 0;
                } else
                    return -1;
            }
        });
        return list;
    }

    @GetMapping("/advertisement")
    @ApiOperation("广告位")
    public Mono<Message> advertisement(){
        JSONObject data = new JSONObject();
        data.put("basicurl"," https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/ad/");
        data.put("data",advertisementService.all());

        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/task")
    @ApiOperation("任务")
    public Mono<Message> task(Principal principal,@RequestParam("level") String level){
        JSONObject data = new JSONObject();
        if(0==tasksService.getStatusCount(level,principal.getName(),"0")){
           List<JSONObject> str=null;
           if(level.equals("5")){
              str = productClassService.getProductClassNotLogob(level);
           }else {
               str =productClassService.getProductClassNotLogo(level);
           }
           for (int i = 0;i< str.size(); i++) {
               Tasks tasks=new Tasks();
               tasks.setStatus("0");
               tasks.setTaskno(str.get(i).getString("id"));
               tasks.setUser(principal.getName());
               tasks.setType("logo");
               tasks.setLevel(str.get(i).getString("level"));
               tasksService.insert(tasks);
           }
       }
        data.put("allcount",tasksService.getAllCount(principal.getName()));
        data.put("okcount",tasksService.getStatusCount("3",principal.getName(),"1")+tasksService.getStatusCount("4",principal.getName(),"1")+tasksService.getStatusCount("5",principal.getName(),"1"));
        data.put("todaycount",tasksService.getTodayCount(principal.getName()));
        data.put("ingcount",tasksService.getStatusCount("3",principal.getName(),"0")+tasksService.getStatusCount("4",principal.getName(),"0")+tasksService.getStatusCount("5",principal.getName(),"0"));
        data.put("count",tasksService.getAllCount0()+"/"+tasksService.getAllCount());
        data.put("level3",tasksService.getLevel("3")+"/"+tasksService.getLevelALL("3"));
        data.put("level4",tasksService.getLevel("4")+"/"+tasksService.getLevelALL("4"));
        data.put("level5",tasksService.getLevel("5")+"/"+tasksService.getLevelALL("5"));
        data.put("countb",accuracy(tasksService.getAllCount(),tasksService.getAllCount0(),1));
        data.put("level3b",accuracy(tasksService.getLevelALL("3"),tasksService.getLevel("3"),1));
        data.put("level4b",accuracy(tasksService.getLevelALL("4"),tasksService.getLevel("4"),1));
        data.put("level5b",accuracy(tasksService.getLevelALL("5"),tasksService.getLevel("5"),1));
        data.put("data",tasksService.getTaskLogo(principal.getName(),level));

        JSONArray userdata=new JSONArray();
        List<JSONObject> users=userService.getAllAdminData();
        users.forEach(user->{
            user.put("today",tasksService.getTodayCount(user.getString("phonenumber")));
            user.put("all",tasksService.getAllCount(user.getString("phonenumber")));
            userdata.add(user);
        });
        data.put("todaydata",jsonArraySort("today",userdata.toJSONString()));
        data.put("alldata",jsonArraySort("all",userdata.toJSONString()));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }



    public String accuracy(double total, double num, int scale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num)+"%";
    }

    @PutMapping("/updatetask")
    @ApiOperation("任务")
    public Mono<Message> updatetask(@RequestParam("url")String url,@RequestParam("id")String id){
        productClassService.updateLogo(url,id);
        tasksService.updateLogo(id);
        return Mono.just(Message.SCUESSS(Message.SECUESS,null));
    }

    @GetMapping("/tg")
    @ApiOperation("任务")
    public Mono<Message> tg(Principal principal,@RequestParam("level")String level,@RequestParam("id")String id){
        JSONObject data = new JSONObject();
            tasksService.updatetg(id);
        productClassService.updateStatus(id);
            List<JSONObject> str=null;
            if(level.equals("5")){
                str = productClassService.getProductClassNotLogob(level);
            }else {
                str = productClassService.getProductClassNotLogo(level);
            }
            for (int i = 0;i< str.size(); i++) {
                Tasks tasks=new Tasks();
                tasks.setStatus("0");
                tasks.setTaskno(str.get(i).getString("id"));
                tasks.setUser(principal.getName());
                tasks.setType("logo");
                tasks.setLevel(str.get(i).getString("level"));
                tasksService.insert(tasks);
            }
        data.put("data",tasksService.getTaskLogo(principal.getName(),level));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/mytasks")
    @ApiOperation("任务")
    public Mono<Message> mytasks(Principal principal,@RequestParam("page") int page,@RequestParam("size")int size,@RequestParam("text")String text){
        return Mono.just(Message.SCUESSS(String.valueOf(tasksService.getMyCount(principal.getName(),text)),tasksService.getMy(principal.getName(),page,size,text)));
    }

    @GetMapping("/areas")
    @ApiOperation("地区信息")
    public Mono<Message> areas(@RequestParam("pid")String pid){
        return Mono.just(Message.SCUESSS(Message.SECUESS,areaService.getByPid(pid)));
    }

    @PutMapping("/fileLib")
    @ApiOperation("采集资料")
    public Mono<Message> updatetask(@RequestBody FileLib fileLib){
        userSecurityService.findByAdmin().forEach(u->{
        userMapper.updateYBCount(userMapper.get(u).getYbcount()+1,u);
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,fileLibService.add(fileLib)));
    }

    @GetMapping("/filelibs")
    @ApiOperation("获取采集资料")
    public Mono<Message> fileLibs(Principal principal,int page){
        return Mono.just(Message.SCUESSS(String.valueOf(fileLibService.findCount()),fileLibService.findPage(page,20)));
    }

    @PostMapping("/downloadFilelibs")
    public Mono<Message> downloadFilelibs(Principal principal){
        if(userMapper.get(principal.getName()).getYbcount()>0) {
            userMapper.updateYBCount(userMapper.get(principal.getName()).getYbcount() - 1, principal.getName());
        }
        return Mono.just(Message.SCUESSS("ok",null));
    }

    @GetMapping("/sitemap")
    public Mono<Message> sitemap() throws Exception {
        //打印推送结果
        ProductClass productClass=new ProductClass();
        productClass.setLevel(Byte.valueOf("4"));
        File file=new File("/home/alex/images/sitemap.xml");
        FileOperation.createFile(file);
        List<SiteMap> sb =new ArrayList<>();
        productClassService.getByLevelCondition(productClass).forEach(p->{
            try {
                SiteMap test=new SiteMap();
                test.setUrl("https://www.lingjianbang.com/level4/"+p.getId());
                sb.add(test);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        productClass.setLevel(Byte.valueOf("3"));
        productClassService.getByLevelCondition(productClass).forEach(p->{
            try {
                SiteMap test=new SiteMap();
                test.setUrl("https://www.lingjianbang.com/level3/"+p.getId());
                sb.add(test);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        createXml(file,sb, SiteMap.class);
        return null;
    }

    @PutMapping("/saveclassdata")
    public Mono<Message> saveclassdata(@RequestBody JSONArray jsonArray,@RequestParam("id")String id){
        List<List> lists= jsonArray.toJavaList(List.class);
        JSONObject rowjson=new JSONObject();
        List key=new ArrayList();
        for (int i=0;i<lists.get(0).size();i++){
            if(null!=lists.get(0).get(i)&&""!=lists.get(0).get(i)&&!"".equals(lists.get(0).get(i))) {
                key.add(lists.get(0).get(i));
            }
        }
        rowjson.put("key",key);
        for (int i=1;i<lists.size();i++){
            List<JSONObject> row = new ArrayList<>();
            for (int a=0;a<lists.get(i).size();a++) {
                if(null!=lists.get(0).get(a)&&""!=lists.get(0).get(a)&&!"".equals(lists.get(0).get(a))) {
                    JSONObject cell=new JSONObject();
                    cell.put("key",lists.get(0).get(a));
                    cell.put("value",lists.get(i).get(a));
                    row.add(cell);
                }
            }
            rowjson.put(String.valueOf(i),row);
        }
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            mt.remove(new Query(new Criteria()),id);
        }
        mt.insert(rowjson, id);
        return Mono.just(Message.SCUESSS("保存成功",0));
    }

    @PostMapping("/getclassdata")
    public Mono<Message> getclassdata(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS("ok",mt.find(new Query(new Criteria()),JSONObject.class,id)));
    }

    @PostMapping("/sendmail")
    public Mono<Message> sendmail(@RequestBody JSONArray jsonArray){
        String text=jsonArray.get(0).toString().replace("ondrag=\"changeurl(this)\"","").replace("onclick=\"changeimg(this)\"","").replace("onclick=\"changeword(this)\"","").replace("border: 2px solid green;","");
        jsonArray.forEach(a->{
            mailService.sendHtmlMail(a.toString(), text);
        });
        return Mono.just(Message.SCUESSS("发送成功",0));
    }


    @SuppressWarnings("unchecked")
    public static <T> void createXml(File file, List<T> list, Class<T> clz) {
        try {
            // 创建Document
            Document document = DocumentHelper.createDocument();
            // 创建根节点
            Element root = document.addElement("root");
            // 获取类中所有的字段
            Field[] fields = clz.getDeclaredFields();
            // 先把List<T>对象转成json字符串
            String str = JSONObject.toJSONString(list);
            // 把json字符串转换成List<Map<Object, Object>>
            List<Map<Object, Object>> mapList = (List<Map<Object, Object>>) JSONArray.parse(str);

            Element element;
            Map<Object, Object> map;
            // 迭代拼接xml节点数据
            for (int i = 0; i < mapList.size(); i++) {
                // 在根节点下添加子节点
                element = root.addElement(clz.getSimpleName());
                // 获取Map<Object, Object>对象
                map = mapList.get(i);
                // 从map中获取数据，拼接xml
                for (Field field : fields) {
                    // 在子节点下再添加子节点
                    element.addElement(field.getName())
                            .addAttribute("attr", field.getType().getName())
                            .addText(String.valueOf(map.get(field.getName())));
                }
            }
            // 把xml内容输出到文件中
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(document);
            System.out.println("Dom4jUtils Create Xml success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Autowired
    private UnitMapper unitMapper;

    @GetMapping("/parameter")
    public Mono<Message> getParameter(){
        JSONObject data = new JSONObject();
        data.put("unit",unitMapper.allubit());
        data.put("datatype",unitMapper.alldatatype());
        return Mono.just(Message.SCUESSS("ok",data));
    }
}
