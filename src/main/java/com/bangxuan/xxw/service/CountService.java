package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.CountMapper;
import com.bangxuan.xxw.entity.User;
import com.bangxuan.xxw.util.CodeLib;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CountService {

    @Autowired
    private CountMapper countMapper;

    @Autowired
    private UserService userService;

    @Cacheable(cacheNames={"redis_cache"}, key = "'countinfos'")
    public JSONObject count(){
        JSONObject data=new JSONObject();
        data.put("company",countMapper.companyCount());
        data.put("brand",countMapper.brandCount());
        data.put("class1",countMapper.productClass1Count());
        data.put("class2",countMapper.productClass2Count());
        data.put("class3",countMapper.productClass3Count());
        data.put("class4",countMapper.productClass4Count());
        data.put("class5",countMapper.productClass5Count());
        data.put("sku",countMapper.skuCount());
        data.put("user",countMapper.userCount());


        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
        data.put("duser",countMapper.userCountByTime(strNow[0]+"-"+strNow[1]+"-"+strNow[2]));
        data.put("muser",countMapper.userCountByTime(strNow[0]+"-"+strNow[1]));
        data.put("yuser",countMapper.userCountByTime(strNow[0]));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        List<String> days=new ArrayList<>();
        List<Integer> daycounts=new ArrayList<>();
        for (int i=12;i>=0;i--) {
            c.setTime(new Date());
            c.add(Calendar.DATE, -(i*7));
            Date d = c.getTime();
            String day = format.format(d);
            days.add(day);
            daycounts.add(countMapper.dayByTime(day));
        }
        data.put("daycounts",daycounts);
        data.put("days",days);

        data.put("todayuser",countMapper.todayuserCount(" not ").size());
        data.put("todaynouser",countMapper.todayuserCount(" ").size());
        data.put("fwl",countMapper.fwlCount());
        data.put("mfwl",countMapper.mfwlCount(strNow[0]+"-"+strNow[1]));
        return data;

    }

    public JSONArray userfiveclass() {
        JSONArray data=new JSONArray();
        countMapper.getFiveClassUser().forEach(v->{

            JSONObject value=new JSONObject();
            User u= userService.get(v);
            value.put("uname",v);
            value.put("name",u.getName());
            value.put("all",countMapper.getUserCount(v));
            value.put("day",countMapper.getDayUserCount(v));
            value.put("not",countMapper.getUserNotCount(v));
            value.put("headportrait",u.getHeadportrait());
            data.add(value);
        });
        return data;
    }

    @Autowired
    private CodeLib tencentOSS;

    public String day() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("零件帮统计数据\n\r");
        countMapper.countlogs().forEach(v->{
            sb.append("日期:"+v.getString("today")+
                    "\t会员登录人数:"+v.getString("m")+"人"+
                    "\t游客访问人数:"+v.getString("n")+"人"+
                    "\t页面访问量:"+v.getString("o")+"次"+
                    "\t今日注册人数:"+v.getString("j")+"人" +
                    "\t本月注册人数:"+v.getString("k")+"人"+
                    "\t本年注册人数:"+v.getString("l")+"人"+
                    "\t个人会员总数:"+v.getString("i")+"人\r"+
                    "分类一级目录:"+v.getString("c")+""+
                    "\t分类二级目录:"+v.getString("d")+""+
                    "\t分类三级目录:"+v.getString("e")+""+
                    "\t分类四级目录:"+v.getString("f")+""+
                    "\t分类五级目录:"+v.getString("g")+""+
                    "\t分类型号数量:"+v.getString("h")+""+
                    "\t入驻公司数量:"+v.getString("a")+""+
                    "\t入驻品牌数量:"+v.getString("b")+"\n\r"
            );
        });
        File file = null;
        file = File.createTempFile("count" ,".txt");
        CodeLib.createFile(file);
        CodeLib.writeTxtFile(sb.toString(),file);
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        String key = "public/"+file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        COSClient cosClient=tencentOSS.getClient();
        PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
        cosClient.shutdown();
        return "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/"+key;
    }

    @Transactional
    public Integer followClass(JSONObject jsonObject) {
        return countMapper.addFollowClass(jsonObject);
    }

    public List<JSONObject> followInfo() {
        return countMapper.followInfo();
    }

    public Integer followInfoCount() {
        return countMapper.followInfoCount();
    }
}
