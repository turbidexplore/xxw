package com.bangxuan.xxw.controller.admin;

import com.bangxuan.xxw.service.ProductClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NavController {

    @Autowired
    private ProductClassService productClassService;

    @RequestMapping("/")
    public String home(){
        return "login";
    }

    @RequestMapping("/system/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/system/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/system/followInfo")
    public String followInfo(){
        return "followInfo";
    }

    @RequestMapping("/system/task")
    public String task(){
        return "task";
    }

    @RequestMapping("/system/fk")
    public String fk(){
        return "fk";
    }

    @RequestMapping("/system/tasks")
    public String tasks(){
        return "tasks";
    }

    @RequestMapping("/system/company")
    public String company(){
        return "company";
    }

    @RequestMapping("/system/userdata")
    public String userdata(){
        return "userdata";
    }

    @RequestMapping("/system/ocr")
    public String ocr(){
        return "ocr";
    }

    @RequestMapping("/system/filelib")
    public String filelib(){
        return "filelib";
    }

    @RequestMapping("/system/nodatafiveclass")
    public String nodatafiveclass(){
        return "nodatafiveclass";
    }

    @RequestMapping("/system/nodata")
    public String nodata(){
        return "nodata";
    }

    @RequestMapping("/system/fiveclass")
    public String fiveclass(@RequestParam("id")String id,@RequestParam("comid")String comid,HttpServletRequest request){
        request.setAttribute("id",id);
        request.setAttribute("comid",comid);
        return "fiveclass";
    }

    @RequestMapping("/system/myfiveclass")
    public String myfiveclass(){
        return "myfiveclass";
    }

    @RequestMapping("/system/fiveclassview")
    public String fiveclassview(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        productClassService.updateSkutype(id,1);
        return "fiveclassview";
    }

    @RequestMapping("/system/fiveclassview_vue")
    public String fiveclassview_vue(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        productClassService.updateSkutype(id,1);
        return "fiveclassview_vue";
    }

    @RequestMapping("/system/fiveclassviewpl")
    public String fiveclassviewpl(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        productClassService.updateSkutype(id,2);
        return "fiveclassviewpl";
}

    @RequestMapping("/system/tasklogo")
    public String tasklogo(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "tasklogo";
    }

    @RequestMapping("/system/skuinfo")
    public String skuinfo(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "skuinfo";
    }

    @RequestMapping("/system/skuinfopl")
    public String skuinfopl(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "skuinfopl";
    }
}
