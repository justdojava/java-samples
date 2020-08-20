package com.just.doone.example;

import com.just.doone.example.domain.Course;
import com.just.doone.example.domain.StudentDO;
import com.just.doone.example.domain.StudentDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/8/20 19:57
 * @since
 */
public class OrikaExample {
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

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new DateToStringConverter("yyyy-MM-dd"));
        mapperFactory.classMap(StudentDTO.class, StudentDO.class)
                .field("no", "number")
                .byDefault()
                .register();


        MapperFacade mapper = mapperFactory.getMapperFacade();

        StudentDO studentDO = mapper.map(studentDTO, StudentDO.class);

        System.out.println(studentDO);

    }
}
