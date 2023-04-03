package com.rfli.springbootrf.service;

import com.rfli.springbootrf.dao.UserDao;
import com.rfli.springbootrf.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 添加用户信息
     */
    public int save(UserEntity u) {
        return this.userDao.insertUser(u);
    }

    /**
     * 更新用户信息
     */
    public int updateUser(UserEntity u) {
        return this.userDao.updateUser(u);
    }

    /**
     * 根据用户id删除用户
     */
    public int deleteUserById(int id) {
        return this.userDao.deleteUser(id);
    }

    /**
     * 根据用户id查询用户
     */
    public UserEntity getUserById(int id) {
        return this.userDao.selectUserById(id);
    }

    /**
     * 根据用户名查询用户
     */
    public UserEntity getUserByName(String username) {
        return this.userDao.selectUserByName(username);
    }

    /**
     * 查询所有用户信息
     */
    public List<UserEntity> listAll() {
        return this.userDao.selectAll();
    }
}