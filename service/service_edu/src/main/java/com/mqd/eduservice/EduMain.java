package com.mqd.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mqd"})  //扫描swagger
public class EduMain {
    public static void main(String[] args) {
        SpringApplication.run(EduMain.class,args);
    }

}
