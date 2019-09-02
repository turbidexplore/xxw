package com.bangxuan.xxw.controller.api;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.UnitMapper;
import com.bangxuan.xxw.dao.UserMapper;
import com.bangxuan.xxw.entity.*;
import com.bangxuan.xxw.service.*;
import com.bangxuan.xxw.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
    private MongoTemplate mt;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private SkuThread skuThread;
    @Autowired
    private SkuService skuService;
    @Autowired
    private SkuValuesService skuValuesService;
    @Autowired
    private ExpressService expressService;

    @GetMapping("/getStatistics")
    public Mono<Message> getStatistics(HttpServletRequest request, Principal principal){
        if(principal!=null&&null!=principal.getName()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userinfo",principal.getName());
            jsonObject.put("type",request.getHeader("Referer"));
            userService.addUserLogs(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        // 表达式总数
        Integer amount = expressService.getAmount();
        jsonObject.put("spareparts",productClassService.getSKUCount()+amount);

        jsonObject.put("spareparts_class",productClassService.getFiveClass());
        jsonObject.put("brand",companyService.getCount());
        jsonObject.put("pdf",productClassService.getPDFCount());
        jsonObject.put("numberclass",productClassService.getNumberclass());
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
    public Mono<Message> advertisement(@RequestParam(required = false) String postionId){
        JSONObject data = new JSONObject();
        data.put("basicurl"," https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/ad/");
        if(StringUtils.isEmpty(postionId)){
            data.put("data",advertisementService.all());
        }else{ 
            data.put("data",advertisementService.getByPostionId(postionId));
        }
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
        data.put("level5",(tasksService.getLevel("5"))+"/"+tasksService.getLevelALL("5"));
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

    @PutMapping("/updatetask")
    public Mono<Message> updatetask(@RequestParam("url")String url,@RequestParam("id")String id){
        productClassService.updateLogo(url,id);
        try {
            tasksService.updateLogo(id);
        }catch (Exception e){

        }
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

    @GetMapping("/myfiveclass")
    public Mono<Message> myfiveclass(Principal principal,@RequestParam("page") int page,@RequestParam("size")int size,@RequestParam("text")String text,@RequestParam("type")Integer type,@RequestParam("uname")String uname){
        String name="";
        if(type==1){
            name="and p.skuuser = '"+principal.getName()+"' ";
        }else if(type==2){
            name="and p.skuuser = '"+uname+"' ";
        }
        JSONObject data=new JSONObject();
        data.put("data",productClassService.myfiveclass(name,page,size,text));
        data.put("user",principal.getName());
        return Mono.just(Message.SCUESSS(String.valueOf(tasksService.getMyCount(principal.getName(),text)),data));
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
    public Mono<Message> fileLibs(int page){
        return Mono.just(Message.SCUESSS(String.valueOf(fileLibService.findCount()),fileLibService.findPage(page,20)));
    }

    @PostMapping("/downloadFilelibs")
    public Mono<Message> downloadFilelibs(Principal principal){
        if(userMapper.get(principal.getName()).getYbcount()>0) {
            userMapper.updateYBCount(userMapper.get(principal.getName()).getYbcount() - 1, principal.getName());
        }
        return Mono.just(Message.SCUESSS("ok",null));
    }

    @PutMapping("/bdsclassdata")
    public Mono<Message> bdsclassdata(@RequestBody JSONArray jsonArray,@RequestParam("id")String id) throws InterruptedException {
        List<List> lists= jsonArray.toJavaList(List.class);
        productClassService.tg(Integer.valueOf(id),1);
        mt.remove(new Query(new Criteria()),"skuinfos"+id);
        skuValuesService.deleteByClassId(id);
        skuService.deleteByClassId(id);
        for (int i=5;i<lists.size();i++){
            String uuid=UUID.randomUUID().toString().replace("-", "");
            skuThread.run(uuid,id,lists,i);
            skuThread.addSkuValue(uuid,id,i,lists);
        }
        JSONObject rowjson=new JSONObject();
        rowjson.put("data",lists);
        rowjson.put("type","bds");
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            mt.remove(new Query(new Criteria()),id);
        }
        mt.insert(rowjson, id);
        return Mono.just(Message.SCUESSS("保存成功",0));
    }

    @PutMapping("/bdsclassdatapl")
    public synchronized Mono<Message> bdsclassdatapl(@RequestBody JSONArray jsonArray,@RequestParam("id")String id) throws InterruptedException {
        List<List> lists= jsonArray.toJavaList(List.class);
        productClassService.tg(Integer.valueOf(id),1);
        mt.remove(new Query(new Criteria()),"skuinfos"+id);
        skuValuesService.deleteByClassId(id);
        skuService.deleteByClassId(id);
        for (int i=5;i<lists.size();i++){
            String uuid=UUID.randomUUID().toString().replace("-", "");
            skuThread.runpl(uuid,id,lists,i);
            skuThread.addSkuValuepl(uuid,id,i,lists);

        }
        JSONObject rowjson=new JSONObject();
        rowjson.put("data",lists);
        rowjson.put("type","bds");
        if(mt.find(new Query(new Criteria()),JSONObject.class,id).size()!=0){
            mt.remove(new Query(new Criteria()),id);
        }
        mt.insert(rowjson, id);
        return Mono.just(Message.SCUESSS("保存成功", productClassService.updateClassData(Integer.parseInt(id),2)));
    }


    @PostMapping("/getclassdata")
    public Mono<Message> getclassdata(@RequestParam("id")String id){
          List<JSONObject> list= mt.find(new Query(new Criteria()),JSONObject.class,id);
          if(list.size()>0){
              return Mono.just(Message.SCUESSS("ok",list.get(0).getJSONArray("data")));
             }
        return Mono.just(Message.SCUESSS("ok",0));
    }


    @GetMapping("/parameter")
    public Mono<Message> getParameter(){
        JSONObject data = new JSONObject();
        data.put("unit",unitMapper.allubit());
        data.put("datatype",unitMapper.alldatatype());
        return Mono.just(Message.SCUESSS("ok",data));
    }

    @GetMapping("/codes")
    public Mono<Message> getCodes(){
        return Mono.just(Message.SCUESSS("ok",unitMapper.codes()));
    }

    @PutMapping("/saveskunames")
    public Mono<Message> saveskunames(@RequestBody JSONArray jsonArray,@RequestParam("id")String id){
        List<List> lists= jsonArray.toJavaList(List.class);
        JSONObject rowjson=new JSONObject();
        rowjson.put("data",lists);
        if(mt.find(new Query(new Criteria()),JSONObject.class,"saveskunames"+id).size()!=0){
            mt.remove(new Query(new Criteria()),"saveskunames"+id);
        }
        mt.insert(rowjson, "saveskunames"+id);
        return Mono.just(Message.SCUESSS("保存成功",0));
    }

    @GetMapping("/getexpressbyclassid")
    public Mono<Message> getExpressByClassId(@RequestParam("classId")String classId){
        Express express = expressService.getByClassId(classId);
        if(express==null){
            return Mono.just(Message.ERROR("暂无数据"));
        }else{
            return Mono.just(Message.SCUESSS("ok",expressService.getByClassId(classId)));
        }
    }

    @PostMapping("/savesExpress")
    public Mono<Message> savesExpress(@RequestBody JSONObject expList, @RequestParam("id")String id){
//        System.out.println("expList="+expList.getJSONArray("expList"));
//        System.out.println("skuInfos="+expList.getJSONArray("skuInfos"));
        JSONArray lists = expList.getJSONArray("skuInfos");

        Express express = expressService.getByClassId(id);
        if(express!=null){
            express.setExpressjson(expList.getJSONArray("expList").toJSONString());
            express.setSkurules(expList.getJSONArray("skuRules").toJSONString());
            express.setCreatedate(new Date());
            express.setUpdatedate(new Date());
            if(expList.getJSONArray("ysList")!=null){
                express.setYsjson(expList.getJSONArray("ysList").toJSONString());
            }
            if(expList.getJSONArray("skuInfos")!=null){
                express.setSkuinfos(expList.getJSONArray("skuInfos").toJSONString());
            }
            express.setMaintotalcount(expList.getInteger("mainTotalCount"));
            express.setAlltotalcount(expList.getInteger("alltotalcount"));
            expressService.update(express);
        }else {
            // 保存表达式，保存约束
            express = new Express();
            express.setHasbuild(0);
            express.setClassid(Integer.valueOf(id));
            express.setExpressjson(expList.getJSONArray("expList").toJSONString());
            express.setSkurules(expList.getJSONArray("skuRules").toJSONString());
            express.setCreatedate(new Date());
            express.setUpdatedate(new Date());
            if(expList.getJSONArray("ysList")!=null){
                express.setYsjson(expList.getJSONArray("ysList").toJSONString());
            }
            if(expList.getJSONArray("skuInfos")!=null){
                express.setSkuinfos(expList.getJSONArray("skuInfos").toJSONString());
            }
            express.setMaintotalcount(expList.getInteger("mainTotalCount"));
            express.setAlltotalcount(expList.getInteger("alltotalcount"));
            expressService.insert(express);
        }
//        System.out.println("ysList="+expList.getJSONArray("ysList"));
//        System.out.println("id="+id);
        return Mono.just(Message.SCUESSS("保存成功",0));
    }
    @GetMapping("/getskuinfosCount")
    public Mono<Message> getskuinfosCount(@RequestParam("classId")String classId){
        int skuinfcount = skuService.countByClassId(classId);
        return Mono.just(Message.SCUESSS("操作成功",skuinfcount));
    }


    @PostMapping("/saveskuinfos")
    public Mono<Message> saveskuinfos(@RequestBody JSONObject expList, @RequestParam("id")String id){
//        System.out.println("expList="+expList.getJSONArray("expList"));
//        System.out.println("skuInfos="+expList.getJSONArray("skuInfos"));
        JSONArray lists = expList.getJSONArray("skuInfos");

        Express express = expressService.getByClassId(id);
        if(express!=null){
            express.setExpressjson(expList.getJSONArray("expList").toJSONString());
            express.setSkurules(expList.getJSONArray("skuRules").toJSONString());
            express.setCreatedate(new Date());
            express.setUpdatedate(new Date());
            express.setMaintotalcount(expList.getInteger("mainTotalCount"));
            express.setAlltotalcount(expList.getInteger("alltotalcount"));
            if(expList.getJSONArray("ysList")!=null){
                express.setYsjson(expList.getJSONArray("ysList").toJSONString());
            }
            if(expList.getJSONArray("skuInfos")!=null){
                express.setSkuinfos(expList.getJSONArray("skuInfos").toJSONString());
            }
            expressService.update(express);
        }else {
                // 保存表达式，保存约束
                express = new Express();
                express.setHasbuild(0);
                express.setClassid(Integer.valueOf(id));
                express.setExpressjson(expList.getJSONArray("expList").toJSONString());
                express.setSkurules(expList.getJSONArray("skuRules").toJSONString());
                express.setCreatedate(new Date());
                express.setUpdatedate(new Date());
                express.setMaintotalcount(expList.getInteger("mainTotalCount"));
                express.setAlltotalcount(expList.getInteger("alltotalcount"));
                if(expList.getJSONArray("ysList")!=null){
                    express.setYsjson(expList.getJSONArray("ysList").toJSONString());
                }
                if(expList.getJSONArray("skuInfos")!=null){
                    express.setSkuinfos(expList.getJSONArray("skuInfos").toJSONString());
                }
                expressService.insert(express);
        }


        // 删除原来的skuValue
        skuValuesService.deleteByClassId(id);
        // 删除原来的skuInfo
        skuService.deleteByClassId(id);

        for (int i=5;i<lists.size();i++){
            String uuid=UUID.randomUUID().toString().replace("-", "");
            JSONArray skuValuesArr = lists.getJSONArray(i);
//            https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf
            List<JSONObject> listPdf = this.productClassService.getPDF(id);
            String pdf = "";
            if(listPdf!=null&&listPdf.size()>0){
                pdf = "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/"+listPdf.get(0).getString("pdf_file");
            }


            skuThread.bdsrun(uuid,id,skuValuesArr,i,pdf);
            skuThread.addBdsSkuValue(uuid,id,i,skuValuesArr,lists);
        }

        return Mono.just(Message.SCUESSS("保存成功",0));
    }

    @GetMapping("/getskurules")
    public Mono<Message> getskuRules(@RequestParam("id")String id){
        Express express = expressService.getByClassId(id);
        if(express==null){
            return Mono.just(Message.ERROR("暂无数据"));
        }else{
            return Mono.just(Message.SCUESSS("ok",express.getSkurules()));
        }

    }

    @GetMapping("/getskunames")
    public Mono<Message> getskunames(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS("ok",mt.find(new Query(new Criteria()),JSONObject.class,"saveskunames"+id).get(0).getJSONArray("data")));
    }

    @GetMapping("/getenname")
    public Mono<Message> getEnname(@RequestParam("text")String text){
        if(text.equals("")||text==""||text==null){
            return Mono.just(Message.SCUESSS("ok",""));
        }
        return Mono.just(Message.SCUESSS("ok",productClassService.getEnname(text)));
    }



}