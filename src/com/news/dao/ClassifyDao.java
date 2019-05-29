package com.news.dao;

import com.news.domain.Classify;
import com.news.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ClassifyDao {
    public List<Classify>  getAllClassify() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from classify";
        List<Classify> list = runner.query(sql, new BeanListHandler<Classify>(Classify.class));
        return list;
    }
}
