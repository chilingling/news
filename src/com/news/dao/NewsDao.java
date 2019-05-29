package com.news.dao;

import com.news.domain.News;
import com.news.utils.Count;
import com.news.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class NewsDao {
    public List<News> getIndexNews (int start, int end) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select id, title, time, source, classify, readnum, img, intro, commentnum from news order by time desc limit " + start + "," + end;
        List<News> list =  runner.query(sql, new BeanListHandler<News>(News.class));
        return list;
    }
    public List<News> getNewsByClassify (String classify, int start, int end) throws SQLException {
        List<News> list = null;
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select id, title, time, source, classify, readnum, img, intro, commentnum from news  where classify = " + classify + " order by time desc limit " + start + "," + end;
        list = runner.query(sql, new BeanListHandler<News>(News.class));
        return list;
    }
    public News getNewsById(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from news where id = " + id;
        News res = runner.query(sql, new BeanHandler<News>(News.class));
        return  res;
    }
    public int increCommentNumById (int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update news set commentnum = commentnum + 1 where id = " + id;
        int update = runner.update(sql);
        return update;
    }
    public int getNewsNumbers () throws SQLException{
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) count from news";
        Count count = runner.query(sql, new BeanHandler<Count>(Count.class));
        return count.getCount();
    }
    public int getNewNumByClassify(String classify) throws SQLException{
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) count from news where classify = " + classify;
        Count count = runner.query(sql, new BeanHandler<Count>(Count.class));
        return count.getCount();
    }
    public void addReadNumById (int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update news set readnum = readnum + 1 where id = " + id;
        runner.update(sql);
    }
}
