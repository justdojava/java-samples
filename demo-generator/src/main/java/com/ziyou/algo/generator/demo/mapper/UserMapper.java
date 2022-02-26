package com.ziyou.algo.generator.demo.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ziyou
 */
public interface UserMapper {
  /**
   * 查询操作示例
   *
   * @return
   */
  @Select("SELECT * FROM t_user;")
  @Results({
    @Result(property = "id", column = "Id"),
    @Result(property = "Name", column = "Name"),
    @Result(property = "Desc", column = "Desc"),
    @Result(property = "CreateTime", column = "CreateTime")
  })
  List<UserEntity> getAll();

  /**
   * get
   *
   * @param id
   * @return
   */
  @Select("SELECT * FROM t_user WHERE Id = #{id};")
  @Results({
    @Result(property = "id", column = "Id"),
    @Result(property = "Name", column = "Name"),
    @Result(property = "Desc", column = "Desc"),
    @Result(property = "CreateTime", column = "CreateTime")
  })
  UserEntity getEntity(int id);

  /**
   * 插入操作示例
   * 将自增ID绑定到实体，keyProperty是实体字段，keyColumn是数据库字段
   *
   * @param user
   * @return
   */
  @Insert("INSERT INTO t_user(`Name`,`Desc`,CreateTime) VALUES(#{Name}, #{Desc}, #{CreateTime});")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "Id")
  int insert(UserEntity user);

  /**
   * 修改操作示例
   *
   * @param user
   * @return
   */
  @Update("UPDATE t_user SET `Name`=#{Name},`Desc`=#{Desc} WHERE Id =#{id};")
  int update(UserEntity user);

  /**
   * 删除操作示例
   *
   * @param id
   * @return
   */
  @Delete("DELETE FROM t_user WHERE Id =#{id};")
  int delete(int id);
}
