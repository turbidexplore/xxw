package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.UnitMapper;
import com.bangxuan.xxw.dao.UserMapper;
import com.bangxuan.xxw.entity.*;
import com.bangxuan.xxw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

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
    private MongoTemplate mt;
    @Autowired
    private UnitMapper unitMapper;

    @GetMapping("/getStatistics")
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
    public Mono<Message> advertisement(){
        JSONObject data = new JSONObject();
        data.put("basicurl"," https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/ad/");
        data.put("data",advertisementService.all());

        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/task")
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
        df.setMaximumFractionDigits(scale);
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num)+"%";
    }

    @PutMapping("/updatetask")
    public Mono<Message> updatetask(@RequestParam("url")String url,@RequestParam("id")String id){
        productClassService.updateLogo(url,id);
        tasksService.updateLogo(id);
        return Mono.just(Message.SCUESSS(Message.SECUESS,null));
    }

    @GetMapping("/tg")
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
    public Mono<Message> mytasks(Principal principal,@RequestParam("page") int page,@RequestParam("size")int size,@RequestParam("text")String text){
        return Mono.just(Message.SCUESSS(String.valueOf(tasksService.getMyCount(principal.getName(),text)),tasksService.getMy(principal.getName(),page,size,text)));
    }

    @GetMapping("/areas")
    public Mono<Message> areas(@RequestParam("pid")String pid){
        return Mono.just(Message.SCUESSS(Message.SECUESS,areaService.getByPid(pid)));
    }

    @PutMapping("/fileLib")
    public Mono<Message> updatetask(@RequestBody FileLib fileLib){
        userSecurityService.findByAdmin().forEach(u->{
        userMapper.updateYBCount(userMapper.get(u).getYbcount()+1,u);
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,fileLibService.add(fileLib)));
    }

    @GetMapping("/filelibs")
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
        int count=0;
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
            count++;
        }
        rowjson.put("count",count);
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            mt.remove(new Query(new Criteria()),id);
        }
        mt.insert(rowjson, id);
        return Mono.just(Message.SCUESSS("保存成功",0));
    }

    @PutMapping("/bdsclassdata")
    public Mono<Message> bdsclassdata(@RequestBody JSONArray jsonArray,@RequestParam("id")String id,@RequestParam("text")String text){
        List<List> lists= jsonArray.toJavaList(List.class);
        JSONObject rowjson=new JSONObject();
        rowjson.put("data",lists);
        rowjson.put("bds",text);
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            mt.remove(new Query(new Criteria()),id);
        }
        mt.insert(rowjson, id);
        return Mono.just(Message.SCUESSS("保存成功",0));
    }


    @PostMapping("/getclassdata")
    public Mono<Message> getclassdata(@RequestParam("id")String id){
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            JSONArray data=new JSONArray();

          List<JSONObject> list= mt.find(new Query(new Criteria()),JSONObject.class,id);
            data.add(list.get(0).getJSONArray("key"));
              for (int i=1;i<list.get(0).getInteger("count")+1;i++){
                  JSONArray row=new JSONArray();
                  int finalI = i;
                  list.get(0).getJSONArray(String.valueOf(finalI)).forEach(b->{
                      JSONObject col= (JSONObject) b;
                      row.add(col.getString("value"));
                  });
                  data.add(row);
              }
            return Mono.just(Message.SCUESSS("ok",data));
        }
        return Mono.just(Message.SCUESSS("ok",0));
    }

    @GetMapping("/classdata")
    public Mono<Message> classdata(@RequestParam("id")String id){
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            List<JSONObject> list= mt.find(new Query(new Criteria()),JSONObject.class,id);
            JSONObject data =new JSONObject();
            data.put("images", productClassService.getImages(id));
            JSONArray array= new JSONArray();
            for (int i=5;i<list.get(0).getInteger("count")+1;i++){
                JSONObject sku=new JSONObject();
                sku.put("name",list.get(0).getJSONArray(String.valueOf(i)).getObject(1,JSONObject.class).getString("value"));
                JSONArray row=new JSONArray();
                for (int j=2;j<list.get(0).getJSONArray(String.valueOf(i)).size();j++) {
                    JSONObject col=list.get(0).getJSONArray(String.valueOf(i)).getObject(j,JSONObject.class);
                    col.put("en",list.get(0).getJSONArray(String.valueOf(1)).getObject(j,JSONObject.class).getString("value"));
                    col.put("code",list.get(0).getJSONArray(String.valueOf(2)).getObject(j,JSONObject.class).getString("value"));
                    col.put("unit",list.get(0).getJSONArray(String.valueOf(3)).getObject(j,JSONObject.class).getString("value"));
                    col.put("datatype",list.get(0).getJSONArray(String.valueOf(4)).getObject(j,JSONObject.class).getString("value"));
                    row.add(col);
                }
                sku.put("data",row);
                array.add(sku);
            }
            data.put("data",array);
            return Mono.just(Message.SCUESSS("ok",data));
        }
        return Mono.just(Message.SCUESSS("ok",0));
    }

    @PostMapping("/sendmail")
    public Mono<Message> sendmail(@RequestBody JSONArray jsonArray){
        String text=jsonArray.get(0).toString().replace("ondrag=\"changeurl(this)\"","").replace("onclick=\"changeimg(this)\"","").replace("onclick=\"changeword(this)\"","").replace("border: 2px solid green;","");
        if(mt.find(new Query(new Criteria()),JSONObject.class,"email").size()!=0){
            mt.remove(new Query(new Criteria()),"email");
        }
        mt.insert(jsonArray.get(0).toString(), "email");
        jsonArray.forEach(a->{
            mailService.sendHtmlMail(a.toString(), text);
        });
        return Mono.just(Message.SCUESSS("发送成功",0));
    }

    @GetMapping("/getemail")
    public Mono<Message> getemail(){
        List<String> value= mt.find(new Query(new Criteria()),String.class,"email");
        return Mono.just(Message.SCUESSS("发送成功",value.get(0)));
    }


    @GetMapping("/parameter")
    public Mono<Message> getParameter(){
        JSONObject data = new JSONObject();
        data.put("unit",unitMapper.allubit());
        data.put("datatype",unitMapper.alldatatype());
        return Mono.just(Message.SCUESSS("ok",data));
    }
}
