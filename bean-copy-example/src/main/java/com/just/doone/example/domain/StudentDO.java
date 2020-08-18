package com.just.doone.example.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/8/18 19:05
 * @since
 */
@Data
public class StudentDO {

    private String name;

    private String age;

    private String number;

    private List<String> subjects;

    private Course course;

    private Date createDate;


}
