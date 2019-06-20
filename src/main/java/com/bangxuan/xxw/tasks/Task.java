package com.bangxuan.xxw.tasks;

import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.service.ProductClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.bangxuan.xxw.util.CodeLib.Post;


@Component
public class Task {

    @Autowired
    private ProductClassService productClassService;

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

    @Scheduled(cron ="0 0 02 15 * ?")
    public void tasklogo() {
        productClassService.updateTaskLogo();
    }
}
