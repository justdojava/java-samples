package com.just.doone.example;


import com.just.doone.example.domain.Course;
import com.just.doone.example.domain.StudentDO;
import com.just.doone.example.domain.StudentDTO;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/8/20
 */
public class DozerSpringExample {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Mapper mapper = applicationContext.getBean(Mapper.class);

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

        StudentDO studentDO = mapper.map(studentDTO, StudentDO.class);
        System.out.println(studentDO);
    }
}
