package com.ziyou.algo.generator.demo.controller;

import com.ziyou.algo.generator.demo.mapper.UserEntity;
import com.ziyou.algo.generator.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserMapper userMapper;

  @RequestMapping("add")
  public int testInsert(String name, String desc) throws Exception {
    int id, num = 0;
    UserEntity u = new UserEntity(0, name, desc, new Date());
    num = userMapper.insert(u);
    id = u.getId();
    System.out.println("新增数据完成 id= " + id + ",num= " + num);
    return id;
  }

  @RequestMapping("get")
  public UserEntity testQueryOne(int id) throws Exception {
    UserEntity UserEntity = userMapper.getEntity(id);
    System.out.println(UserEntity.toString());
    return UserEntity;
  }

  @RequestMapping("getlist")
  public List<UserEntity> testQuery() throws Exception {
    List<UserEntity> UserEntitys = userMapper.getAll();
    System.out.println(UserEntitys.toString());
    return UserEntitys;
  }

  @RequestMapping("edit")
  public int testUpdate(int id, String name, String desc) throws Exception {
    int num = 0;
    UserEntity UserEntity = userMapper.getEntity(id);
    if (null != UserEntity && UserEntity.getId() > 0) {
      System.out.println(UserEntity.toString());
      UserEntity.setName(name);
      UserEntity.setDesc(desc);
      num = userMapper.update(UserEntity);
    }
    return num;
  }

  @RequestMapping("del")
  public int testDelete(int id) throws Exception {
    int num = 0;
    if (id > 0) {
      num = userMapper.delete(id);
    }
    return num;
  }
}
