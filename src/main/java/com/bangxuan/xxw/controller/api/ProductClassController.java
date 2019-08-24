package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.entity.SkuInfo;
import com.bangxuan.xxw.entity.SkuValues;
import com.bangxuan.xxw.entity.User;
import com.bangxuan.xxw.service.*;
import com.bangxuan.xxw.util.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Api(description = "ProductClass接口")
@RestController
@RequestMapping("/productclass")
@CrossOrigin
public class ProductClassController {

    @Autowired
    private ProductClassService productClassService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuValuesService skuValuesService;

    @GetMapping("/getByLevel")
    public Mono<Message> getByLevel(){
        JSONArray data = new JSONArray();
        List<JSONObject> list =productClassService.getProductClass();
        list.forEach(l->{
            if(l.getInteger("level")==1){
                setData(list,l);
                data.add(l);
            }
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/getByBrand")
    public Mono<Message> getByBrand(@RequestParam("brandid")String brandid){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByBrand(brandid)));
    }

    @GetMapping("/getByLevelHaveLogo")
    public Mono<Message> getByLevelAndLogo(){
        JSONArray data = new JSONArray();
        List<JSONObject> list =productClassService.getProductClassHaveLogo();
        list.forEach(l->{
            if(l.getInteger("level")==1){
                setData(list,l);
                data.add(l);
            }
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/getNew")
    public Mono<Message> getNew(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getNewTOP14()));
    }

    public void setData(List<JSONObject> productClassList,JSONObject jo){
        JSONArray data =new JSONArray();
        productClassList.forEach(p->{
          if(p.getString("pid").equals(jo.getString("id"))){
              setData(productClassList,p);
              data.add(p);
          }
        });
        jo.put("data",data);
    }

    @PostMapping("/getByLevelCondition")
    public Mono<Message> getByLevelCondition(@RequestBody ProductClass productClass){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevelCondition(productClass)));
    }

    @PostMapping("/getByAuth")
    public Mono<Message> getByAuth(@RequestBody JSONObject jsonObject){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevelAuth(jsonObject)));
    }

    @PostMapping("/getAuthLevel5")
    public Mono<Message> getAuthLevel5(@RequestParam("id") String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevel5(id)));
    }

    @GetMapping("/getByText")
    public Mono<Message> getByText(@RequestParam("text") String text){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByText(text)));
    }
    @ApiOperation(value = "搜索" ,  notes="搜索")
    @GetMapping("/getByCompany")
    public Mono<Message> getByCompany(@RequestParam("text") String text){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByCompany(text)));
    }

    @GetMapping("/levellogo")
    public Mono<Message> getByLevelHaveLogo(@RequestParam("id") String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevelHaveLogo(id)));
    }

    @GetMapping("/allOrderPinyin")
    public Mono<Message> allOrderPinyin(@RequestParam("text")String text){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.allOrderPinyin(text.toUpperCase())));
    }

    @GetMapping("/allNumber")
    public Mono<Message> allNumber(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.allNumber()));
    }

    @GetMapping("/allQt")
    public Mono<Message> allQt(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.allqt()));
    }

    @GetMapping("/getByCompanyId")
    public Mono<Message> getByCompanyId(@RequestParam("companyid") String companyid,@RequestParam("pid") Integer pid){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByCompanyId(companyid,pid)));
    }

    @GetMapping("/getOne")
    public Mono<Message> getOne(Principal principal, @RequestParam("id")String id, @RequestParam("comid")String comid){
        List<JSONObject> datas=productClassService.getInfos(id,principal.getName());
        if(datas.size()>0||datas.size()==1&&datas.get(0).getString("status").equals("1")){
            JSONObject data= datas.get(0);
            data.put("images",productClassService.getImages(data.getString("id")));
            return Mono.just(Message.SCUESSS(Message.SECUESS,data));
        }else {
            if (id != "0" && !id.equals("0")) {
                JSONObject data = productClassService.get(id);
//                productClassService.updateUser(principal.getName(), data.getString("id"));
                data.put("images", productClassService.getImages(data.getString("id")));
                return Mono.just(Message.SCUESSS(Message.SECUESS, data));
            } else {
                JSONObject data=new JSONObject();
                if (comid != "0" && !comid.equals("0")) {
                    data= productClassService.getOneC(principal.getName(),comid);
                }else {
                    data= productClassService.getOne(principal.getName());
                }
                if(data==null){
                    return Mono.just(Message.SCUESSS(Message.SECUESS, null));
                }
//                productClassService.updateUser(principal.getName(), data.getString("id"));
                data.put("images", productClassService.getImages(data.getString("id")));
                return Mono.just(Message.SCUESSS(Message.SECUESS, data));
            }
        }
    }

    @PutMapping("/saveClassdata")
    public Mono<Message> saveClassdata(@RequestParam("id") Integer id,@RequestParam("value") Integer value){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.tg(id,value)));
    }

    @PutMapping("/updateDesc")
    public Mono<Message> updateDesc(@RequestBody JSONObject jsonObject) throws InterruptedException {
        productClassService.delImage(jsonObject.getString("id"));
        productClassService.updateDesc(jsonObject.getString("desc"),jsonObject.getString("id"),jsonObject.getString("industry"));
        jsonObject.getJSONArray("imgs").forEach(img->{
            productClassService.addImage(jsonObject.getString("id"),img.toString());
        });

        return Mono.just(Message.SCUESSS("ok",null));
    }


    @GetMapping("/getSkuInfo")
    public Mono<Message> getSkuInfo(@RequestParam("id") String id){

        List<JSONObject> list=mt.find(new Query(new Criteria()),JSONObject.class,"skuinfos"+id);
        if(list.size()!=0){
            return Mono.just(Message.SCUESSS(201,Message.SECUESS,list.get(0)));
        }
        List<JSONObject> skuInfos=mt.find(new Query(new Criteria()),JSONObject.class,"skunames"+id);
        return Mono.just(Message.SCUESSS(Message.SECUESS,skuInfos.get(0)));
    }

    @ApiOperation(value = "查询skuinfo" ,  notes="查询skuinfo")
    @PostMapping("/skuinfos")
    public Mono<Message> skuinfos(@RequestParam("id") String id,@RequestParam("page")Integer page,@RequestParam("size")Integer size,@RequestBody JSONArray jsonArray){
        String ids="where a.classid = "+id+" ";
        int s=0;
        if (jsonArray.size()>0){
            s=1;
           for (int i=0;i<jsonArray.size();i++){

               ids +="  and a.id in(select d.skuid from gy_class_skuvalues d " +
                       " where d.classid="+id+" and d.skucode ='"+jsonArray.getJSONObject(i).getString("code")+"'" +
                       " and d.skukey='"+jsonArray.getJSONObject(i).getString("key")+"' and d.skuvalue in(";
               for (int j=0;j<jsonArray.getJSONObject(i).getJSONArray("values").size();j++){
                   if(j==0){
                       ids+="'"+jsonArray.getJSONObject(i).getJSONArray("values").getString(j)+"'";
                   }else {
                       ids+=",'"+jsonArray.getJSONObject(i).getJSONArray("values").getString(j)+"'";
                   }
               }
               ids+=")) ";
           }
        }
        JSONObject productClass=  productClassService.get(id);
        List<SkuInfo> skuInfos=skuService.findById(id,page,size,ids);
        skuInfos.forEach(v->{
            if(v.getPdf()==""||v.getPdf()==null){
                v.setPdf("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/"+productClass.getString("pdf_file"));
            }
            v.setSkuValuesList(skuValuesService.findById(v.getId()));
        });
        if(s==1&&skuInfos.size()==0){
            return Mono.just(Message.SCUESSS("nodata",skuInfos));
        }
        return Mono.just(Message.SCUESSS(String.valueOf(skuService.countById(id,ids)),skuInfos));
    }


    @GetMapping("/getSkuinfos")
    public Mono<Message> getSkuinfos(@RequestParam("id") String id){

      List<JSONObject> list=  mt.find(new Query(new Criteria()),JSONObject.class,id);

      if(list.size()==0){
          return Mono.just(Message.SCUESSS("nodata",null));
      }else {
        List<JSONObject> data=new ArrayList<>();

          return Mono.just(Message.SCUESSS("ok",list.get(0).getJSONArray("data")) );
      }
    }

    @GetMapping("/skuvales")
    public Mono<Message> skuvalues(@RequestParam("id") String id){
        List<SkuValues> values=skuValuesService.findById(id);
        return Mono.just(Message.SCUESSS(Message.SECUESS,values));
    }

    @GetMapping("/parameter")
    public Mono<Message> parameter(@RequestParam("id") String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,skuValuesService.findValuesByClassid(id)));
    }

    @Autowired
    private SkuThread skuThread;

    @Autowired
    private MongoTemplate mt;

    @PutMapping("/saveSkuInfos")
    public Mono<Message> saveSkuInfos(@RequestBody JSONArray jsonArray,@RequestParam("id")String id){
        if(mt.find(new Query(new Criteria()),JSONObject.class,"saveSkuInfos"+id).size()!=0){
            mt.remove(new Query(new Criteria()),"saveSkuInfos"+id);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",jsonArray);
        mt.insert(jsonArray, "saveSkuInfos"+id);
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.updateClassData(Integer.parseInt(id),5)));
    }

    @PutMapping("/saveSkuInfopls")
    public Mono<Message> saveSkuInfopls(@RequestBody JSONArray jsonArray,@RequestParam("id")String id){
        List<List> lists= jsonArray.toJavaList(List.class);
        JSONObject rowjson=new JSONObject();
        rowjson.put("data",lists);
        if(mt.find(new Query(new Criteria()),JSONObject.class,"skuinfos"+id).size()!=0){
            mt.remove(new Query(new Criteria()),"skuinfos"+id);
        }
        mt.insert(rowjson, "skuinfos"+id);
        skuThread.skuinfopl(id, jsonArray);
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.updateClassData(Integer.parseInt(id),2)));
    }

    @GetMapping("/getFiveClassImages")
    public Mono<Message> getFiveClassImages(@RequestParam("id") String id){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("images",productClassService.getImages(id));
        jsonObject.put("classlogo",productClassService.getLogo(id));
        return Mono.just(Message.SCUESSS(Message.SECUESS,jsonObject));
    }

    @Autowired
    private UserService userService;

    @ApiOperation(value = "六级查询" ,  notes="六级查询")
    @GetMapping("/getSixClass")
    public Mono<Message> getSixClass(Principal principal,@RequestParam("skuid") String skuid){
        try {
            User user=userService.get(principal.getName());
            if(user!=null) {
                JSONObject jsonObject = new JSONObject();
                SkuInfo data = skuService.getOne(skuid);
                jsonObject.put("class", productClassService.get(data.getClassid().toString()));
                jsonObject.put("company", productClassService.getCompany(data.getClassid().toString()));
                jsonObject.put("brand", productClassService.getBrand(data.getClassid().toString()));
                jsonObject.put("skuinfo", data);
                jsonObject.put("skuvalues", skuValuesService.findById(skuid));
                jsonObject.put("images", productClassService.getImages(data.getClassid().toString()));
                return Mono.just(Message.SCUESSS(Message.SECUESS, jsonObject));
            }else {
                return Mono.just(Message.ERROR( "请登录!"));
            }
        }catch (Exception e){
            System.out.println(e);
            return Mono.just(Message.ERROR( "请登录!"));
        }

    }

    @GetMapping("/sixClassInfo")
    public Mono<Message> sixClassInfo(@RequestParam("skuid") String skuid){
        try {
                JSONObject jsonObject = new JSONObject();
                SkuInfo data = skuService.getOne(skuid);
                jsonObject.put("class_desc", productClassService.get(data.getClassid().toString()).getString("class_desc"));
                jsonObject.put("skuinfo", data);
                jsonObject.put("images", productClassService.getImages(data.getClassid().toString()));
                return Mono.just(Message.SCUESSS(Message.SECUESS, jsonObject));
        }catch (Exception e){
            return Mono.just(Message.ERROR( "错误!"));
        }
    }

    @PutMapping("/savehis")
    public Mono<Message> savehis(@RequestBody JSONObject jsonObject,@RequestParam("id")String id){
        if(mt.find(new Query(new Criteria()),JSONObject.class,"savehis"+id).size()!=0){
            mt.remove(new Query(new Criteria()),"savehis"+id);
        }
        mt.insert(jsonObject, "savehis"+id);
        return Mono.just(Message.SCUESSS(Message.SECUESS,null));
    }

    @GetMapping("/gethis")
    public Mono<Message> gethis(@RequestParam("id")String id){
       List<JSONObject> jsonObjects= mt.find(new Query(new Criteria()),JSONObject.class,"savehis"+id);
       if(jsonObjects.size()>0) {
           return Mono.just(Message.SCUESSS(Message.SECUESS, jsonObjects.get(0)));
       }else {
           return Mono.just(Message.SCUESSS(Message.SECUESS, null));
       }
    }
}
