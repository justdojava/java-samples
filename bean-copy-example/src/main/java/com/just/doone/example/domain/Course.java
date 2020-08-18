package com.just.doone.example.domain;

import lombok.Data;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/8/18 19:02
 * @since
 */
@Data
public class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }
}
