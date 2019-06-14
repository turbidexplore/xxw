package com.bangxuan.xxw.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NavController {

    @RequestMapping("/")
    public String home(){
        return "loginv1";
    }

    @RequestMapping("/system/login")
    public String login(){
        return "loginv1";
    }

    @RequestMapping("/system/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/system/indextest")
    public String indextest(){
        return "indextest";
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

    @RequestMapping("/system/nodata")
    public String nodata(){
        return "nodata";
    }

    @RequestMapping("/system/fiveclass")
    public String fiveclass(){
        return "fiveclass";
    }

    @RequestMapping("/system/messages")
    public String messages(){
        return "messages";
    }

    @RequestMapping("/system/fiveclassview")
    public String fiveclassview(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "fiveclassview";
    }
    @RequestMapping("/system/tasklogo")
    public String fk(@RequestParam("id")String id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "tasklogo";
    }

}
