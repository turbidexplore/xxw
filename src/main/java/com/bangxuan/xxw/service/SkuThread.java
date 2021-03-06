package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.SkuInfo;
import com.bangxuan.xxw.entity.SkuValues;
import com.bangxuan.xxw.util.ExpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SkuThread  {

    private List<List> lists;

    public List<List> getLists() {
        return lists;
    }

    public void setLists(List<List> lists) {
        this.lists = lists;
    }

    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuValuesService skuValuesService;

    @Autowired
    private ProductClassService productClassService;

    @Async("asyncServiceExecutor")
    public void run(String uuid,String id,List<List> lists,int i) throws InterruptedException {

        SkuInfo skuInfo=new SkuInfo();
        skuInfo.setId(uuid);
        skuInfo.setClassid(Integer.parseInt(id));
        skuInfo.setSkuname(lists.get(i).get(0).toString());
        skuInfo.setIdx(i-4);
        skuService.insert(skuInfo);

    }

    @Async("asyncServiceAddSkuValue")
    public void addSkuValue(String uuid,String id,int i,List<List> lists){
        for (int a=1;a<lists.get(i).size();a++) {
            if(null!=lists.get(0).get(a)&&""!=lists.get(0).get(a)&&!"".equals(lists.get(0).get(a))) {
                SkuValues skuValues = new SkuValues();
                skuValues.setId(UUID.randomUUID().toString().replace("-", ""));
                skuValues.setSkuid(uuid);
                skuValues.setClassid(id);
                if(lists.get(0).get(a)!=null) {
                    skuValues.setSkukey(lists.get(0).get(a).toString());
                }
                if(lists.get(1).get(a)!=null) {
                    skuValues.setKey_en(lists.get(1).get(a).toString());
                }
                if(lists.get(2).get(a)!=null) {
                    skuValues.setSkucode(lists.get(2).get(a).toString());
                }
                if(lists.get(3).get(a)!=null) {
                    skuValues.setUnit(lists.get(3).get(a).toString());
                }
                if(lists.get(4).get(a)!=null) {
                    skuValues.setDatatype(lists.get(4).get(a).toString());
                }
                if(lists.get(i).get(a)!=null) {
                    skuValues.setSkuvalue(lists.get(i).get(a).toString());
                }

                skuValues.setIndex(a);
                skuValuesService.insert(skuValues);

            }
        }

    }

    @Async("asyncServiceExecutorpl")
    public void runpl(String uuid,String id,List<List> lists,int i) throws InterruptedException {
        productClassService.updateInfo(id," information=1 ");
        SkuInfo skuInfo=new SkuInfo();
        skuInfo.setId(uuid);
        skuInfo.setClassid(Integer.parseInt(id));
        skuInfo.setSkuname(lists.get(i).get(0).toString());
        if(lists.get(i).get(1)!=null&&lists.get(i).get(1)!="") {
            skuInfo.setOrigin(lists.get(i).get(1).toString());
        }
        if(lists.get(i).get(2)!=null&&lists.get(i).get(2)!="") {
            skuInfo.setUnitprice(lists.get(i).get(2).toString());
        }else {
            skuInfo.setUnitprice("未提供");
        }
        if(lists.get(i).get(3)!=null&&lists.get(i).get(3)!="") {
        skuInfo.setWholesaleprice(lists.get(i).get(3).toString());
        }else {
            skuInfo.setWholesaleprice("未提供");
        }
        if(lists.get(i).get(4)!=null&&lists.get(i).get(4)!="") {
            skuInfo.setMpq(lists.get(i).get(4).toString());
        }else {
            skuInfo.setMpq("1");
        }
        if(lists.get(i).get(5)!=null&&lists.get(i).get(5)!="") {
        skuInfo.setMoq(lists.get(i).get(5).toString());
        }else {
            skuInfo.setMoq("1");
        }
        if(lists.get(i).get(6)!=null&&lists.get(i).get(6)!="") {
        skuInfo.setQualityassurancetime(lists.get(i).get(6).toString());
        }else {
            skuInfo.setQualityassurancetime("12");
        }
        if(lists.get(i).get(7)!=null&&lists.get(i).get(7)!="") {
        skuInfo.setSample(lists.get(i).get(7).toString());
        }else {
            skuInfo.setSample("未提供");
        }
        if(lists.get(i).get(8)!=null&&lists.get(i).get(8)!="") {
        skuInfo.setZzsample(lists.get(i).get(8).toString());
            productClassService.updateInfo(id," pdfinfo=1 ");
        }else {
            skuInfo.setZzsample("未提供");
            productClassService.updateInfo(id," pdfinfo=1 ");
        }
        if(lists.get(i).get(9)!=null&&lists.get(i).get(9)!="") {

            skuInfo.setPdf(lists.get(i).get(9).toString());
        }
        if(lists.get(i).get(10)!=null&&lists.get(i).get(10)!="") {
            productClassService.updateInfo(id," modelb=1 ");
        skuInfo.setSd(lists.get(i).get(10).toString());
        }
        if(lists.get(i).get(11)!=null&&lists.get(i).get(11)!="") {
            productClassService.updateInfo(id," modela=1 ");
        skuInfo.setTd(lists.get(i).get(11).toString());
        }
        if(lists.get(i).get(12)!=null&&lists.get(i).get(12)!="") {
        skuInfo.setVideo(lists.get(i).get(12).toString());
        }
        if(lists.get(i).get(13)!=null&&lists.get(i).get(13)!="") {
        skuInfo.setLogo(lists.get(i).get(13).toString());
        }

        skuInfo.setIdx(i-4);
        skuService.insert(skuInfo);

    }

    @Async("asyncServiceAddSkuValuepl")
    public void addSkuValuepl(String uuid,String id,int i,List<List> lists){
        for (int a=14;a<lists.get(i).size();a++) {
            if(null!=lists.get(0).get(a)&&""!=lists.get(0).get(a)&&!"".equals(lists.get(0).get(a))) {
                SkuValues skuValues = new SkuValues();
                skuValues.setId(UUID.randomUUID().toString().replace("-", ""));
                skuValues.setSkuid(uuid);
                skuValues.setClassid(id);
                if(lists.get(0).get(a)!=null) {
                    skuValues.setSkukey(lists.get(0).get(a).toString());
                }
                if(lists.get(1).get(a)!=null) {
                    skuValues.setKey_en(lists.get(1).get(a).toString());
                }
                if(lists.get(2).get(a)!=null) {
                    skuValues.setSkucode(lists.get(2).get(a).toString());
                }
                if(lists.get(3).get(a)!=null) {
                    skuValues.setUnit(lists.get(3).get(a).toString());
                }
                if(lists.get(4).get(a)!=null) {
                    skuValues.setDatatype(lists.get(4).get(a).toString());
                }
                if(lists.get(i).get(a)!=null) {
                    skuValues.setSkuvalue(lists.get(i).get(a).toString());
                }

                skuValues.setIndex(a);
                skuValuesService.insert(skuValues);

            }
        }

    }

    String[] codes={"origin","unitprice","wholesaleprice","mpq","moq","qualityassurancetime","sample","zzsample","pdf","sd","td","video","logo"};

    @Async("asyncServiceSkuinfopl")
    public void skuinfopl(String id, JSONArray jsonArray) {
        productClassService.updateInfo(id," information=1 ");
        for (int i=1;i<jsonArray.size();i++){
            for (int j=1;j<jsonArray.getJSONArray(i).size();j++){
                if(codes[9]!=null&&codes[9]!=""&&!codes[9].equals("")){
                    productClassService.updateInfo(id," modela=1 ");
                }
                if(codes[10]!=null&&codes[10]!=""&&!codes[10].equals("")){

                    productClassService.updateInfo(id," modelb=1 ");
                }
                if(codes[8]!=null&&codes[8]!=""&&!codes[8].equals("")){

                    productClassService.updateInfo(id," pdfinfo=1 ");
                }
                skuService.updatevalue(jsonArray.getJSONArray(i).getString(0), id,codes[j-1],jsonArray.getJSONArray(i).getString(j));
            }
        }
    }
    @Async("asyncServiceSkuinfo")
    public void bdsrun(String uuid, String id, JSONArray skuValuesArr, int i,String pdf) {
        productClassService.updateInfo(id," information=1 ");
        if(!StringUtils.isEmpty(pdf)){
            productClassService.updateInfo(id," pdfinfo=1 ");
        }
        // 保存skuinfo
        SkuInfo skuInfo=new SkuInfo();
        skuInfo.setId(uuid);
        skuInfo.setClassid(Integer.parseInt(id));
        String skuname = skuValuesArr.get(0).toString().replace("{","").replace("}","");
        skuInfo.setSkuname(skuname);
        skuInfo.setSkunameexp(skuValuesArr.get(0).toString());
        skuInfo.setIdx(i-4);
        skuInfo.setPdf(pdf);
        skuService.insert(skuInfo);
    }
    @Async("asyncServiceSkuinfo")
    public void addBdsSkuValue(String uuid, String id, int i, JSONArray skuValuesArr,JSONArray lists) {
        for (int a=1;a<skuValuesArr.size();a++) {
            if(!StringUtils.isEmpty(skuValuesArr.get(a))) {
                SkuValues skuValues = new SkuValues();
                skuValues.setId(UUID.randomUUID().toString().replace("-", ""));
                skuValues.setSkuid(uuid);
                skuValues.setClassid(id);
//              JSONObject skuValue = skuValuesArr.getJSONObject(a);
                if(!StringUtils.isEmpty(lists.getJSONArray(0).get(a))) {
                    skuValues.setSkukey(lists.getJSONArray(0).get(a).toString());
                }

                if(!StringUtils.isEmpty(lists.getJSONArray(1).get(a))) {
                    skuValues.setKey_en(lists.getJSONArray(1).get(a).toString());
                }
                if(!StringUtils.isEmpty(lists.getJSONArray(2).get(a))) {
                    skuValues.setSkucode(lists.getJSONArray(2).get(a).toString());
                }
                if(!StringUtils.isEmpty(lists.getJSONArray(3).get(a))) {
                    skuValues.setUnit(lists.getJSONArray(3).get(a).toString());
                }
                if(!StringUtils.isEmpty(lists.getJSONArray(4).get(a))) {
                    skuValues.setDatatype(lists.getJSONArray(4).get(a).toString());
                }
                if(!StringUtils.isEmpty(skuValuesArr.get(a))) {
                    skuValues.setSkuvalue(skuValuesArr.get(a).toString());
                }

                skuValues.setIndex(a);
                skuValuesService.insert(skuValues);
            }
        }
    }

    @Async("asyncServiceExecutorpl")
    public void saveProductSkuInfos(String classId, JSONArray jsonArray) {
        productClassService.updateInfo(classId," information=1 ");
//        pdf继承5级

        // 更新product_class表商业参数
        List<SkuInfo> skuInfoList = skuService.findByClassid(classId);
        if (skuInfoList != null && skuInfoList.size() > 0) {
            for (SkuInfo skuInfo : skuInfoList) {
                // 表达式下一步的json
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    //
                    int index = json.getInteger("index");
                    String skuname = json.getString("skuname");
                    System.out.println("skuname="+skuname);
                    JSONArray typeValue = json.getJSONArray("typeValue");
                    JSONArray rules = json.getJSONArray("rules");
                    for (int j = 0; j < rules.size(); j++) {
                        JSONObject rule = rules.getJSONObject(j);
                        List<String> list = new ArrayList<>();

                        for (Map.Entry<String, Object> entry : rule.entrySet()) {
//                                System.out.println("entry.getValue()="+entry.getValue());
                            list.add((String) entry.getValue());
                        }
                        // 获取表达式
                        String skuexp = skuInfo.getSkunameexp();
                        List<String> listName = ExpUtils.extractSkuExpName(skuexp);
                        String typval = typeValue.getString(j);
                        boolean isMatch = true;
                        // 判断表达式是否和型号匹配
                        for(int jj=0;jj<listName.size();jj++){
                            if(!StringUtils.isEmpty(list.get(jj))){
                                if(!listName.get(jj).equals(list.get(jj))){
                                    isMatch =false;
                                    break;
                                }
                            }
                        }
//                            System.out.println("skuname"+skuname+"  listName="+StringUtils.join(listName,"")+"  list="+StringUtils.join(list,""));

                        if (isMatch) {
                            switch (index){
                                case 0: skuInfo.setOrigin(typval); // 产地
                                    break;
                                case 1: skuInfo.setUnitprice(typval); // 单价
                                    break;
                                case 2: skuInfo.setWholesaleprice(typval);   // 批量单价
                                    break;
                                case 3: skuInfo.setMpq(typval); // 最小包装量
                                    break;
                                case 4: skuInfo.setMoq(typval);   //最小起订量
                                    break;
                                case 5: skuInfo.setQualityassurancetime(typval); // 质保时间
                                    break;
                                case 6: skuInfo.setSample(typval);   // 样品
                                    break;
                                case 7: skuInfo.setZzsample(typval); // 纸质样本
                                    break;
                                case 8: skuInfo.setPdf(typval); // pdf样本
                                    if(!StringUtils.isEmpty(typval)){
                                        productClassService.updateInfo(classId," pdfinfo=1 ");
                                        //
                                    }
                                    break;
                                case 9: skuInfo.setSd(typval); // 3D模型
                                    if(!StringUtils.isEmpty(typval)){
                                        productClassService.updateInfo(classId," modelb=1 ");
                                    }
                                    break;
                                case 10: skuInfo.setTd(typval); // 2D模型
                                    if(!StringUtils.isEmpty(typval)) {
                                        productClassService.updateInfo(classId, " modela=1 ");
                                    }
                                    break;
                                case 11: skuInfo.setVideo(typval); // 产品视频
                                    break;
                                case 12: skuInfo.setLogo(typval); // logo
                                    break;
                                default: break;
                            }
                            skuService.updateSKU(skuInfo);
                        }
                    }
                }
            }
        }
    }
}

