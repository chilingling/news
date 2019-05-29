package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.Classify;
import com.news.domain.News;
import com.news.service.ClassifyService;
import com.news.service.NewsService;
import com.news.utils.ClassifyListToMap;
import com.news.utils.JsonUtil;
import com.news.utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class IndexNews extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        NewsService newsService = new NewsService();
        ClassifyService classifyService = new ClassifyService();
        Map<String, String[]> params = request.getParameterMap();
        int num = newsService.getNewsNumber();
        int initPage = Integer.valueOf(params.get("page")[0]);
        int initSize = Integer.valueOf(params.get("size")[0]);
        PageUtil pageUtil = new PageUtil(num, Integer.valueOf(params.get("page")[0]), Integer.valueOf(params.get("size")[0]));
        JSONObject data = pageUtil.getResult();
        int pageNumber = pageUtil.getPageNumber();
        int size = pageUtil.getSize();
        int start = (pageNumber - 1) * size;
        List<Classify> classifyList = classifyService.getAllClassify();
        List<News> list = newsService.getIndexNews(start, size);
        list = ClassifyListToMap.convertClassifyName(list, classifyList);
        String json;
        if (list != null && num != 0) {
            data.put("content", initPage > pageUtil.getTotalpages() ? new int[0] : list);
            String msg = initPage > pageUtil.getTotalpages() ? "请求超出范围" : "OK";
            json = JsonUtil.toJson(200, msg, data);
        } else{
            json = JsonUtil.toJson(505, "error", null);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(json);
    }
}
