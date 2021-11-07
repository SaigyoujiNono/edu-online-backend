package com.mqd.servicebase.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisPlusConfig {
    @Bean
    @Primary
    public MybatisPlusProperties getMybatisPlusProperties(){
        return new MybatisPlusProperties();
    }
}
