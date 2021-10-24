package com.mqd.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MybatisPlusFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入");
        this.strictInsertFill(metaObject, "gmtCreate", Date.class,new Date()); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "gmtModified", Date.class,new Date());
        this.strictInsertFill(metaObject, "isDeleted",Boolean.class,false); // 起始版本 3.3.3(推荐)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "gmtModified", Date.class,new Date());
    }
}
