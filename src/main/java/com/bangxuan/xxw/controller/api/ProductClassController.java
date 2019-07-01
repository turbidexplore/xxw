package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.service.ProductClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/productclass")
@CrossOrigin
public class ProductClassController {

    @Autowired
    private ProductClassService productClassService;

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
    public Mono<Message> getOne(){
        JSONObject data =productClassService.getOne();
        data.put("images",productClassService.getImages(data.getString("id")));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @PutMapping("/saveClassdata")
    public Mono<Message> saveClassdata(@RequestParam("id") Integer id,@RequestParam("value") Integer value){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.updateClassData(id,value)));
    }

    @PutMapping("/updateDesc")
    public Mono<Message> updateDesc(@RequestBody JSONObject jsonObject){
        productClassService.delImage(jsonObject.getString("id"));
        jsonObject.getJSONArray("imgs").forEach(img->{
            productClassService.addImage(jsonObject.getString("id"),img.toString());
        });
        productClassService.updateDesc(jsonObject.getString("desc"),jsonObject.getString("id"));
        return Mono.just(Message.SCUESSS("ok",null));
    }



}
