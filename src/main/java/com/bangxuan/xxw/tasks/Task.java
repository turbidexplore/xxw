package com.bangxuan.xxw.tasks;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.TasksMapper;
import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.entity.Tasks;
import com.bangxuan.xxw.service.ProductClassService;

import com.bangxuan.xxw.service.UserSecurityService;
import com.bangxuan.xxw.util.DateDealwith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bangxuan.xxw.util.HttpUtils.Post;


@Component
public class Task {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ProductClassService productClassService;


//    //每隔2秒执行一次
//    @Scheduled(fixedRate = 300000)
//    public void testTasks() {
//
//        List<String> user = userSecurityService.findByAdmin();
//        List<JSONObject> str = productClassService.getProductClassNotLogo();
//        int count = str.size() / user.size();
//        for (int a = 0; a < user.size(); a++){
//            for (int i = a*count; i < (a+1)*count; i++) {
//                Tasks tasks=new Tasks();
//                tasks.setStatus("0");
//                tasks.setTaskno(str.get(i).getString("id"));
//                tasks.setUser(user.get(a));
//                tasks.setType("logo");
//                tasks.setLevel(str.get(i).getString("level"));
//                tasksMapper.insert(tasks);
//                System.out.println(str.size()+"//"+i);
//            }
//        }
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//
//    }



    @Scheduled(cron = "0 00 01 ? * *")
    public void testTasks() {
        //网站的服务器连接
        String url = "http://data.zz.baidu.com/urls?site=www.lingjianbang.com&token=QgYvexeQQtMkZauK";
        String[] param = {"https://www.lingjianbang.com/","https://www.lingjianbang.com/findByABC","https://www.lingjianbang.com/fileCommit","https://www.lingjianbang.com/brandShow"};
        //执行推送方法
        String json = Post(url, param);
        //打印推送结果
        System.out.println("结果是" + json);
        ProductClass productClass=new ProductClass();
        productClass.setLevel(Byte.valueOf("4"));
        productClassService.getByLevelCondition(productClass).forEach(p->{
            String[] parama={"https://www.lingjianbang.com/level4/"+p.getId()};
            //执行推送方法
            String a = Post(url, parama);
            //打印推送结果
            System.out.println("结果是" + a);
        });

        productClass.setLevel(Byte.valueOf("3"));
        productClassService.getByLevelCondition(productClass).forEach(p->{
            String[] parama={"https://www.lingjianbang.com/level3/"+p.getId()};
            //执行推送方法
            String a = Post(url, parama);
            //打印推送结果
            System.out.println("结果是" + a);
        });


    }
}
