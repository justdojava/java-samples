package com.just.doone.example;

import com.just.doone.example.domain.StudentDO;
import com.just.doone.example.domain.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/8/22
 */
@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "no", target = "number")
    @Mapping(source = "createDate", target = "createDate", dateFormat = "yyyy-MM-dd")
    StudentDO dtoToDo(StudentDTO studentDTO);
}
