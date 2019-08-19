package com.bangxuan.xxw;

import com.bangxuan.xxw.dao.CountMapper;
import com.bangxuan.xxw.service.SkuService;
import com.bangxuan.xxw.service.SkuThread;
import com.bangxuan.xxw.service.SkuValuesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XxwApplicationTests {



    @Test
    public void contextLoads() {

    }

    @Autowired
    private MongoTemplate mt;
    @Autowired
    private SkuThread skuThread;
    @Autowired
    private SkuService skuService;
    @Autowired
    private SkuValuesService skuValuesService;
    @Autowired
    private CountMapper countMapper;
    int dfas=0;

    @Test
    public void bdsclassdatapl()  {
//        countMapper.getaaa().forEach(id->{
//            skuValuesService.deleteByClassId(id);
//            skuService.deleteByClassId(id);
//            dfas=dfas+1;
//            List<List> lists= (List<List>) mt.find(new Query(new Criteria()),JSONObject.class,id).get(0).get("data");
//            for (int i=5;i<lists.size();i++){
//                try {
//                    String uuid=UUID.randomUUID().toString().replace("-", "");
//                    if(!lists.get(0).get(1).equals("产地")) {
//                        skuThread.runplx(uuid,id,lists,i);
//                        skuThread.addSkuValueplx(uuid,id,i,lists);
//                        System.out.println("---1"+lists.get(0).get(1));
//                    }else {
//                        skuThread.runpl(uuid,id,lists,i);
//                        skuThread.addSkuValuepl(uuid,id,i,lists);
//                        System.out.println("---2");
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("==============="+dfas);
//        });
    }
}
