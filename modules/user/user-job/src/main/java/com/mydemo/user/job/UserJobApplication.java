package com.mydemo.user.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.mydemo")
@EnableDiscoveryClient
@EnableScheduling
public class UserJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserJobApplication.class, args);
    }
}
