package com.mqd.servicebase.exception;

import com.mqd.result.Result;
import com.mqd.result.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.failed().setCode(Status.ERROR.code).setMessage(Status.ERROR.msg);
    }
}
