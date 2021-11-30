# 在线视频教育系统后端部分

## 技术栈
- jdk8,maven3.6.x
- 基于spring cloud的微服务分布式系统
- 基于swagger2的web api服务
- 持久层mybatis-plus
- 数据库Mysql
- 阿里云oss服务

## 运行本项目
需要修改每个服务下bootstrap.yml的nacos配置中心地址
```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: #配置中心地址:端口号
        namespace: nacos_dev  #配置中心id
```

然后在nacos配置中心创建指定命名空间创建以下配置文件
```yaml
# mysql.yaml
spring:
  datasource:
    url: # 数据库url
    username: # 数据库账户
    password: # 数据库密码
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      connection-timeout: 10000
      validation-timeout: 3000
      idle-timeout: 60000
      login-timeout: 5
      max-lifetime: 60000
      maximum-pool-size: 10
      minimum-idle: 5
      read-only: false
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
```
```yaml
# redis.yaml
spring:
  redis:
    host: # redis地址
    port: 6379
    lettuce:
      pool:
        max-wait: 10000
        max-idle: 10
        max-active: 100
    password: # redis密码，如果没有可以不设置
    timeout: 1800000
```

```yaml
# discovery.yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: # nacos注册中心地址
        namespace: nacos_dev
```

```yaml
# service-vod.yaml
server:
  port: 8003
spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB
aliyun:
  oss:
    video:
      # 阿里云配置信息
      key-id: 
      key-secret: 
      address: 
logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
```

```yaml
# gateway-api.yaml
server:
  port: 80
logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: service-user
          uri: lb://service-user
          predicates:
            - Path=/api/user/**

        - id: service-cms
          uri: lb://service-cms
          predicates:
            - Path=/api/cms/**

        - id: service-edu
          uri: lb://service-edu
          predicates:
            - Path=/api/edu/**

        - id: service-oss
          uri: lb://service-oss
          predicates:
            - Path=/api/oss/**

        - id: service-vod
          uri: lb://service-vod
          predicates:
            - Path=/api/vod/**

        - id: service-mail
          uri: lb://service-mail
          predicates:
            - Path=/api/email/**
```

```yaml
# service-email.yaml
server:
  port: 8005
spring:
  mail:
    # 邮箱配置
    username: 
    password: 
    host: 
    port: 
    properties:
      mail:
        smtp:
          auth: true
          starttls.enable: true
          ssl:
            trust: smtp.office365.com

logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
```

```yaml
# service-cms.yaml
server:
  port: 8004
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath*:com/mqd/cmsservice/mapper/xml/*Mapper.xml

logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
```

```yaml
# service-edu.yaml
server:
  port: 8001
feign:
  circuitbreaker:
    enabled: true
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath*:com/mqd/eduservice/mapper/xml/*Mapper.xml

logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
```

```yaml
# service-user.yaml
server:
  port: 8006
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath*:com/mqd/eduuser/mapper/xml/*Mapper.xml

logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
```

```yaml
# service-oss.yaml
server:
  port: 8002
spring:
  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 10MB

aliyun:
  oss:
    file:
      # 阿里云配置
      endpoint: 
      key-id: 
      key-secret: 
      bucket-name: 
logging:
  config: classpath:logback-nacos.xml
  level:
    root: INFO
  file:
    path: ./log
```