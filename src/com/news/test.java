package com.news;

import com.news.dao.NewsDao;
import com.news.domain.News;
import com.news.domain.User;
import com.news.service.UserService;
import com.news.utils.Count;
import com.news.utils.DataSourceUtils;
import com.news.utils.JsonUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.List;

public class test {

    public static void main(String[] args) {
        NewsDao dao = new NewsDao();
        try {
            List<News> newsList = dao.getIndexNews(1, 2);
//            System.out.println(newsList);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
