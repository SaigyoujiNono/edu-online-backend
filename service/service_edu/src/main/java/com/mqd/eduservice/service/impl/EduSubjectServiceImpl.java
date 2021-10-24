package com.mqd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.mapper.EduSubjectMapper;
import com.mqd.eduservice.pojo.dto.SubjectDto;
import com.mqd.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
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

    @Override
    public boolean delSubject(String id) throws CustomException {

        EduSubject eduSubject = baseMapper.selectById(id);
        if (!eduSubject.getParentId().equals("0")) {
            //当其是次级目录的时候直接删除
             baseMapper.deleteById(id);
            log.info(eduSubject.getId()+":"+eduSubject.getTitle()+"--->已经成功删除");
        }else {
            //当其不是次级目录时，先判断是否存在子目录
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
            wrapper.eq("parent_id", id);
            Integer integer = baseMapper.selectCount(wrapper);
            if (integer>0){
                throw new CustomException("当前存在子分类,无法删除!");
            }
            int i = baseMapper.deleteById(id);
            return i > 0;
        }
        return true;
    }
}
