package com.mqd.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.service.EduSubjectService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类获取
 */
@RestController
@RequestMapping("/api/edu/service")
public class UserSubjectController {

    @Resource
    private EduSubjectService subjectService;

    @ApiOperation(value = "获取分类")
    @GetMapping("/subject")
    public Result getSubject(EduSubject sub) throws CustomException {
        String parentId = sub.getParentId();
        if(StringUtils.hasText(parentId)){
            QueryWrapper<EduSubject> subjectQuery = new QueryWrapper<>();
            subjectQuery.eq("parent_id",parentId);
            List<EduSubject> list = subjectService.list(subjectQuery);
            return Result.ok().addData("subject",list);
        }
        throw new CustomException("获取失败");
    }
}
