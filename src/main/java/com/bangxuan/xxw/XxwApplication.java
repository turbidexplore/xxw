package com.bangxuan.xxw;

import com.baidu.aip.ocr.AipOcr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableResourceServer
@EnableAuthorizationServer
public class XxwApplication {

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

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(XxwApplication.class, args);
    }

}
