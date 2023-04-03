package com.rfli.springbootrf.dao;

import com.rfli.springbootrf.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 插入一条数据
     */
    @Insert("insert into user(username,password) values(#{username},#{password})")
    int insertUser(UserEntity u);

    /**
     * 更新用户信息
     */
    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    int updateUser(UserEntity u);

    /**
     * 根据用户id删除用户
     */
    @Delete("delete from user where id=#{id}")
    int deleteUser(int id);

    /**
     * 根据用户id查询用户
     */
    @Select("select id,username,password from user where id = #{id}")
    UserEntity selectUserById(int id);

    /**
     * 根据用户名查询用户
     */
    @Select("select id,username,password from user where username = #{username}")
    UserEntity selectUserByName(String username);

    /**
     * 查询所有用户信息
     */
    @Select("select id,username,password from user")
    List<UserEntity> selectAll();
}