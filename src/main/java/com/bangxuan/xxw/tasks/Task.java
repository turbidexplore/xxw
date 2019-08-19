package com.bangxuan.xxw.tasks;

import com.bangxuan.xxw.dao.CountMapper;
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

    @Autowired
    private CountMapper countMapper;

    @Scheduled(cron = "0 00 01 ? * *")
    public void testTasks() {
        //网站的服务器连接
        String url = "http://data.zz.baidu.com/urls?site=https://www.lingjianbang.com&token=itN9NRTnVsJdkUy8";
        String[] param = {"https://www.lingjianbang.com/","https://www.lingjianbang.com/findByABC","https://www.lingjianbang.com/fileCommit","https://www.lingjianbang.com/brandShow"};
        //执行推送方法
        String json = Post(url, param);
        //打印推送结果
        System.out.println("结果是" + json);
        ProductClass productClass=new ProductClass();
        productClass.setLevel(Byte.valueOf("5"));
        productClassService.getByLevelCondition(productClass).forEach(p->{
            String[] parama={"https://www.lingjianbang.com/level5/"+p.getId()};
            //执行推送方法
            String a = Post(url, parama);
            //打印推送结果
            System.out.println("结果是" + a);
        });
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
        for (int i=0;i<200;i++){
            String[] parama={"https://www.lingjianbang.com/news/"+i};
            //执行推送方法
            String a = Post(url, parama);
        }
    }

//    @Scheduled(cron = "0 30 23 ? * *")
//    public void aaa() {
//        JSONObject data=new JSONObject();
//        data.put("a",countMapper.companyCount());
//        data.put("b",countMapper.brandCount());
//        data.put("c",countMapper.productClass1Count());
//        data.put("d",countMapper.productClass2Count());
//        data.put("e",countMapper.productClass3Count());
//        data.put("f",countMapper.productClass4Count());
//        data.put("g",countMapper.productClass5Count());
//        data.put("h",countMapper.skuCount());
//        data.put("i",countMapper.userCount());
//
//
//        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
//        data.put("j",countMapper.userCountByTime(strNow[0]+"-"+strNow[1]+"-"+strNow[2]));
//        data.put("k",countMapper.userCountByTime(strNow[0]+"-"+strNow[1]));
//        data.put("l",countMapper.userCountByTime(strNow[0]));
//
//        data.put("m",countMapper.todayuserCount(" not ").size());
//        data.put("n",countMapper.todayuserCount(" ").size());
//        data.put("o",countMapper.fwlCount());
//        data.put("p",countMapper.mfwlCount(strNow[0]+"-"+strNow[1]));
//
//        countMapper.addLogs(data);
//    }




}
