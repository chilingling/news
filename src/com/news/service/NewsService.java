package com.news.service;

import com.news.dao.NewsDao;
import com.news.domain.News;

import java.sql.SQLException;
import java.util.List;

public class NewsService {
    public List<News> getIndexNews (int start, int end) {
        NewsDao dao = new NewsDao();
        List<News> list = null;
        try {
           list = dao.getIndexNews(start, end);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public News getDetailById(int id){
        NewsDao dao = new NewsDao();
        News news = null;
        try {
            news = dao.getNewsById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return news;
    }
    public boolean increCommentNumById (int id) {
        NewsDao dao = new NewsDao();
        int update = 0;
        try {
            update = dao.increCommentNumById(id);
        }catch (SQLException e){
            e.printStackTrace();
            return update > 0;
        }
        return update > 0;
    }
    public int getNewsNumber () {
        NewsDao dao = new NewsDao();
        int num = 0;
        try {
            num = dao.getNewsNumbers();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return num;
    }
    public void addReadNumById (int id) {
        NewsDao dao = new NewsDao();
        try {
            dao.addReadNumById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
