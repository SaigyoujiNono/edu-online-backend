package com.mqd.servicebase.exception;

import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.result.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result error(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.failed().setMessage("未知错误!");
    }
}
