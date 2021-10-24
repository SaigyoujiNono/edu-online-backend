package com.mqd.eduservice.pojo.dto;

import com.mqd.eduservice.pojo.EduSubject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class SubjectDto {
    private String id;
    private String label;
    private List<SubjectDto> children = new ArrayList<>();

    public SubjectDto add(EduSubject subject){
        SubjectDto subjectDto = new SubjectDto().setId(subject.getId()).setLabel(subject.getTitle());
        children.add(subjectDto);
        return this;
    }
}
