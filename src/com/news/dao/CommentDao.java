package com.news.dao;

import com.news.domain.Comment;
import com.news.utils.Count;
import com.news.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CommentDao {
    public Comment addComment(Comment comment) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into comment(newsid, content, fromuid, tocid, time, likesnum) values(?, ?, ?, ?, ?, ?)";
        String sql1 = "SELECT * from comment where id = (SELECT max(id) FROM comment);";

        int update = runner.update(sql, comment.getNewsid(), comment.getContent(), comment.getFromuid(), comment.getTocid(), comment.getTime(), comment.getLikesnum());
        Comment comment1 = null;
        if (update > 0) {
             comment1 = runner.query(sql1, new BeanHandler<Comment>(Comment.class));
        }
        return comment1;
    }
    public List<Comment> queryCommnetById(int id, int start, int end) throws SQLException{
        List<Comment> list = null;
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from comment where newsid = " + id + " order by time desc limit " + start + "," + end;
        list = runner.query(sql,new BeanListHandler<Comment>(Comment.class));
        return list;
    }
    public int getCommentsNum (int id) throws SQLException{
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) count from comment where newsid = " + id;
        Count count = runner.query(sql, new BeanHandler<Count>(Count.class));
        return count.getCount();
    }
    public int updateLikesById(int userid, int commentid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into likes(comment_id, user_id) values(?, ?)";
        int update = runner.update(sql, commentid, userid);
        return update;
    }
    public Comment getSingleCommentById (int id) throws SQLException{
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from comment where id = " + id;
        Comment comment = runner.query(sql, new BeanHandler<Comment>(Comment.class));
        return comment;
    }
    public int deleteCommentById (int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from comment where id = " + id;
        int update = runner.update(sql);
        return update;
    }
}
