package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.service.ProductClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Api(value = "By ProductClass",description = "产品类型")
@RestController
@RequestMapping("/productclass")
@CrossOrigin
public class ProductClassController {

    @Autowired
    private ProductClassService productClassService;

    @GetMapping("/getByLevel")
    @ApiOperation("获取菜单产品分类")
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
    @ApiOperation("获取菜单产品分类带LOGO")
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
    @ApiOperation("获取最新产品")
    public Mono<Message> getNew(){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getNewTOP14()));
    }

    /**
     * 序列化数据
     * @param productClassList
     * @param jo
     */
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
    @ApiOperation("组合查询")
    public Mono<Message> getByLevelCondition(@RequestBody ProductClass productClass){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevelCondition(productClass)));
    }

    @PostMapping("/getByAuth")
    @ApiOperation("组合查询")
    public Mono<Message> getByAuth(@RequestBody JSONObject jsonObject){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevelAuth(jsonObject)));
    }

    @PostMapping("/getAuthLevel5")
    public Mono<Message> getAuthLevel5(@RequestParam("id") String id){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevel5(id)));
    }


    @GetMapping("/getByText")
    @ApiOperation("模糊查询")
    public Mono<Message> getByText(@RequestParam("text") String text){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByText(text)));
    }

    @GetMapping("/levellogo")
    @ApiOperation("模糊查询")
    public Mono<Message> getByLevelHaveLogo(@RequestParam("id") String id){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByLevelHaveLogo(id)));
    }

    @GetMapping("/allOrderPinyin")
    @ApiOperation("四级拼音排序")
    public Mono<Message> allOrderPinyin(@RequestParam("text")String text){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.allOrderPinyin(text.toUpperCase())));
    }

    @GetMapping("/allNumber")
    @ApiOperation("四级数字")
    public Mono<Message> allNumber(){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.allNumber()));
    }

    @GetMapping("/allQt")
    @ApiOperation("四级其他")
    public Mono<Message> allQt(){

        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.allqt()));
    }

    @GetMapping("/getByCompanyId")
    @ApiOperation("通过企业id获取产品信息")
    public Mono<Message> getByCompanyId(@RequestParam("companyid") String companyid,@RequestParam("pid") Integer pid){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getByCompanyId(companyid,pid)));
    }

    @GetMapping("/getOne")
    public Mono<Message> getOne(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.getOne()));
    }

    @PutMapping("/saveClassdata")
    public Mono<Message> saveClassdata(@RequestParam("id") Integer id,@RequestParam("value") Integer value){
        return Mono.just(Message.SCUESSS(Message.SECUESS,productClassService.updateClassData(id,value)));
    }

    @PutMapping("/updateDesc")
    public Mono<Message> updateDesc(@RequestBody JSONObject jsonObject){
        jsonObject.getJSONArray("imgs").forEach(img->{
            productClassService.addImage(jsonObject.getString("id"),img.toString());
        });
        productClassService.updateDesc(jsonObject.getString("desc"),jsonObject.getString("id"));
        return Mono.just(Message.SCUESSS("ok",null));
    }



}
