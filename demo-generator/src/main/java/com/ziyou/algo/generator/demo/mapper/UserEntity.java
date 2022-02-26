package com.ziyou.algo.generator.demo.mapper;

import java.util.Date;

public class UserEntity {
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getDesc() {
    return Desc;
  }

  public void setDesc(String desc) {
    Desc = desc;
  }

  public Date getCreateTime() {
    return CreateTime;
  }

  public void setCreateTime(Date createTime) {
    CreateTime = createTime;
  }

  public UserEntity(int id, String name, String desc, Date createTime) {
    this.id = id;
    Name = name;
    Desc = desc;
    CreateTime = createTime;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
      "id=" + id +
      ", Name='" + Name + '\'' +
      ", Desc='" + Desc + '\'' +
      ", CreateTime=" + CreateTime +
      '}';
  }

  private int id;
  private String Name;
  private String Desc;
  private Date CreateTime;
}
