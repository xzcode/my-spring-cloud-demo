package com.mydemo.mall.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.mydemo")
@EnableDiscoveryClient
public class MallConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallConsumerApplication.class, args);
    }
}
