# 在线视频教育系统后端部分

## 技术栈
- jdk8,maven3.6.x
- 基于spring cloud的微服务分布式系统
- 基于swagger2的web api服务
- 持久层mybatis-plus
- 数据库Mysql
- 阿里云oss服务

## 运行本项目
你需要在service/service_oss/src/main/resources下新建application-dev.yml
填写以下配置
```yaml
# 阿里云oss配置
aliyun:
  oss:
    file:
      endpoint: 
      key-id: 
      key-secret: 
      bucket-name: # 桶名
```

在service/service_edu/src/main/resources下新建application-dev.yml
填写以下配置
```yaml
spring:
  datasource:
    url: 
    username: 
    password: 
```

修改logback-spring.xml下的日志目录
```xml
<property name="log.path" value="{日志目录}" />
```
