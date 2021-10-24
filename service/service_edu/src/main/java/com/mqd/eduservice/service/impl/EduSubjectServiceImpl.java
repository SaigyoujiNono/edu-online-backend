package com.mqd.eduservice.service.impl;

import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.mapper.EduSubjectMapper;
import com.mqd.eduservice.pojo.dto.SubjectDto;
import com.mqd.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-10-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<SubjectDto> getSubjectTree() {
        List<SubjectDto> list = new ArrayList<>();
        List<EduSubject> res = baseMapper.selectList(null);
        res.forEach(n->{
            if (n.getParentId().equals("0")) {
                SubjectDto subjectDto = new SubjectDto()
                        .setId(n.getId())
                        .setLabel(n.getTitle());
                list.add(subjectDto);
            }
        });
        res.forEach(n->{
            list.forEach(m->{
                if (n.getParentId().equals(m.getId())) {
                    m.add(n);
                }
            });
        });
        return list;
    }
}
