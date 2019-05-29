package com.news.dao;

import com.news.domain.User;
import com.news.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.Objects;

public class UserDao {
    public int register (User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user(name, passwd, phone) values(?, ?, ?)";
        int update = runner.update(sql, user.getName(),user.getPasswd(), user.getPhone());
        return update;
    }
    public User login (String username, String password) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where name=? and passwd=?";
        return runner.query(sql, new BeanHandler<User>(User.class), username, password);
    }
    public User getUserByName(String username) throws SQLException{
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where name = ?";
        User user = runner.query(sql, new BeanHandler<User>(User.class), username);
        return user;
    }
    public User getUserById (int id)throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where id = " + id;
        User user = runner.query(sql, new BeanHandler<User>(User.class));
        return user;
    }
    public int changePassByUserId (int id, String passwd) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update user set passwd = ? where id = ? ";
        int update = runner.update(sql, passwd, id);
        return update;
    }
}
