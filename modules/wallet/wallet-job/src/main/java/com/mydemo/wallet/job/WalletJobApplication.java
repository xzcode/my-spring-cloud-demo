package com.mydemo.wallet.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.mydemo")
@EnableDiscoveryClient
@EnableScheduling
public class WalletJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletJobApplication.class, args);
    }
}
