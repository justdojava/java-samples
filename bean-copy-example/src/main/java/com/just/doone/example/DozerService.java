package com.just.doone.example;

import com.just.doone.example.domain.StudentDO;
import com.just.doone.example.domain.StudentDTO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/8/20
 */
public class DozerService {

    @Autowired
    Mapper mapper;

    public void objMapping(StudentDTO studentDTO) {
        // 直接使用
        StudentDO studentDO =
                mapper.map(studentDTO, StudentDO.class);
    }
}
