package com.news.service;

import com.news.dao.ClassifyDao;
import com.news.dao.NewsDao;
import com.news.domain.Classify;
import com.news.domain.News;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class ClassifyService {
    public List<News> getNewsByClassify(String classify, int start, int end) {
        List<News> list = null;
        NewsDao newsDao = new NewsDao();
        try {
            list = newsDao.getNewsByClassify(classify, start, end);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public int getClassifyNewsNum(String classify) {
        NewsDao dao = new NewsDao();
        int num = 0;
        try {
            num = dao.getNewNumByClassify(classify);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return num;
    }
    public List<Classify> getAllClassify() {
        ClassifyDao dao = new ClassifyDao();
        List<Classify> list = null;
        try {
            list = dao.getAllClassify();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
