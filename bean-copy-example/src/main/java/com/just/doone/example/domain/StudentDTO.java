package com.just.doone.example.domain;

import lombok.Data;

import java.util.List;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/8/18 19:01
 * @since
 */
@Data
public class StudentDTO {

    private String name;

    private Integer age;
    @org.dozer.Mapping("number")
    private String no;

    private List<String> subjects;

    private Course course;
    private String createDate;
}
