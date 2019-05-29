package com.news.service;

import com.news.dao.UserDao;
import com.news.domain.User;

import java.sql.SQLException;

public class UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    public boolean regist (User user) {
        UserDao dao = new UserDao();
        int row = 0;
        try {
            row = dao.register(user);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return row > 0;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public User login (String username, String password) throws SQLException {
        UserDao dao = new UserDao();
        return dao.login(username, password);
    }

    /**
     * 根据用户名字获取用户id
     * @param name
     * @return
     */
    public Integer getUserId (String name) {
        UserDao dao = new UserDao();
//        Integer id = null;
        User user = null;
        try {
         user = dao.getUserByName(name);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    /**
     * 根据用户id获取用户实体类
     * @param id
     * @return
     */
    public User getUserById(int id) {
        User user = null;
        UserDao dao = new UserDao();
        try {
            user = dao.getUserById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 根据用户id修改用户密码
     * @param id
     * @param passwd
     * @return
     */
    public boolean updatePasswdByUserId (int id, String passwd) {
        UserDao userDao = new UserDao();
        int update = 0;
        try {
            update = userDao.changePassByUserId(id, passwd);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  update > 0;
    }
    public User getUserByName (String name) {
        User user = null;
        UserDao userDao = new UserDao();
        try {
            user = userDao.getUserByName(name);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  user;
    }
}
