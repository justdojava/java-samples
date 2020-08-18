package com.just.doone.example;

import com.just.doone.example.domain.Course;
import com.just.doone.example.domain.StudentDO;
import com.just.doone.example.domain.StudentDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/8/18 19:09
 * @since
 */
public class ApacheBeanUtilsExample {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("阿粉");
        studentDTO.setAge(18);
        studentDTO.setNo("6666");

        List<String> subjects = new ArrayList<>();
        subjects.add("math");
        subjects.add("english");
        studentDTO.setSubjects(subjects);
        studentDTO.setCourse(new Course("CS-1"));
        studentDTO.setCreateDate("2020-08-08");

        StudentDO studentDO = new StudentDO();

        BeanUtils.copyProperties(studentDO, studentDTO);

        ConvertUtils.register(new Converter() {
            @Override
            public <T> T convert(Class<T> type, Object value) {
                if (value == null) {
                    return null;
                }
                if (value instanceof String) {
                    String str = (String) value;
                    
                }


            }
        }, Date.class);


        BeanUtils.copyProperties(studentDO, studentDTO);

        System.out.println(studentDO);
    }
}

