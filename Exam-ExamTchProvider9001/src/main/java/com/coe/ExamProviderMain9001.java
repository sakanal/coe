package com.coe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ExamProviderMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(ExamProviderMain9001.class, args);
    }
}
