package com.news.web.servlet.jsp;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.news.domain.Classify;
import com.news.domain.News;
import com.news.service.ClassifyService;
import com.news.service.NewsService;
import com.news.utils.ClassifyListToMap;
import com.news.utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        ClassifyService classifyService = new ClassifyService();
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");

        Integer page = 1;
        Integer size = 10;
        if (pageParam != null) {
            page = Integer.valueOf(pageParam);
        }
        if (sizeParam != null) {
            size = Integer.valueOf(sizeParam);
        }
        NewsService newsService = new NewsService();
        int num = newsService.getNewsNumber();
        PageUtil pageUtil = new PageUtil(num, page, size);
        JSONObject data = pageUtil.getResult();
        int pageNum = pageUtil.getPageNumber();
        int pageSize = pageUtil.getSize();
        int start = (pageNum - 1) * size;
        List<News> newsList = newsService.getIndexNews(start, size);
        List<Classify> classifyList =  classifyService.getAllClassify();
        newsList = ClassifyListToMap.convertClassifyName(newsList, classifyList);
        data.put("content", page > pageUtil.getTotalpages() ? new ArrayList<News>(0) : newsList);
        req.setAttribute("classifyList", classifyList);
        req.setAttribute("data", data);
        req.setAttribute("preLink", "/news/index?");
        req.setAttribute("classifyId", 0);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
