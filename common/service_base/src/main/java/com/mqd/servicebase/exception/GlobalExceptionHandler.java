package com.mqd.servicebase.exception;

import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 统一异常处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e){
        log.error(e.getMessage());
        return Result.failed().setMessage(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result error(BindException e){
        log.error(e.getMessage());
        return Result.failed().setMessage(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result error(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.failed().setMessage("未知错误!");
    }
}
