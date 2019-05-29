package com.news.dao;

import com.news.utils.Count;
import com.news.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class LikesDao {
    public int getLikesCountById (int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) count from likes where comment_id = " + id;
        Count count = runner.query(sql, new BeanHandler<Count>(Count.class));
        return count.getCount();
    }
    public boolean getIsLiked (int commentId, int userid) throws SQLException{
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) count from likes where comment_id = ? and user_id = ? limit 1";
        Count count = runner.query(sql,new BeanHandler<Count>(Count.class), commentId, userid);
        return count.getCount() > 0;
    }
    public int cancelLike (int commentid, int userid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from likes where comment_id = ? and user_id = ?";
        int update = runner.update(sql, commentid, userid);
        return update;
    }
}
