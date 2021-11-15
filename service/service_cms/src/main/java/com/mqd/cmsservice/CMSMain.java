package com.mqd.cmsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mqd"})
@EnableDiscoveryClient
public class CMSMain {
    public static void main(String[] args) {
        SpringApplication.run(CMSMain.class,args);
    }
}
