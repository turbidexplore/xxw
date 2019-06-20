package com.bangxuan.xxw.config;

import com.baidu.aip.ocr.AipOcr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaiduOcrConfig {

    public static final String APP_ID = "16188280";
    public static final String API_KEY = "UFre3WSD2OoR9aUnbcxMzNQR";
    public static final String SECRET_KEY = "4WZEl9fOYGDTd3GEu3YasIHAGCvY8NPV";

    @Bean
    public AipOcr aipOcr(){
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }
}
