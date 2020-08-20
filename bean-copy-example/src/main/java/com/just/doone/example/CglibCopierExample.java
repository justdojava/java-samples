package com.just.doone.example;

import com.just.doone.example.domain.Course;
import com.just.doone.example.domain.StudentDO;
import com.just.doone.example.domain.StudentDTO;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/8/19
 */
public class CglibCopierExample {
    public static void main(String[] args) {
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
        BeanCopier beanCopier = BeanCopier.create(StudentDTO.class, StudentDO.class, true);

        beanCopier.copy(studentDTO, studentDO, new Converter() {
            @Override
            public Object convert(Object source, Class target, Object context) {
                if (source instanceof Integer) {
                    Integer num = (Integer) source;
                    return num.toString();
                }
                return null;
            }
        });

        System.out.println(studentDO);
    }
}
