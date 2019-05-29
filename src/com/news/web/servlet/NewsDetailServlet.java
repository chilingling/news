package com.news.web.servlet;

import com.news.domain.Classify;
import com.news.domain.News;
import com.news.service.ClassifyService;
import com.news.service.NewsService;
import com.news.utils.ClassifyListToMap;
import com.news.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NewsDetailServlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String path = request.getRequestURI();
        int id = Integer.valueOf(path.split("/", 4)[3]);
        NewsService service = new NewsService();
        service.addReadNumById(id);
        News news = service.getDetailById(id);
        ClassifyService classifyService = new ClassifyService();
        List<Classify> classifyList = classifyService.getAllClassify();
        Map<Integer, String> classifyMap = ClassifyListToMap.toMap(classifyList);
        news.setClassify(classifyMap.get(Integer.valueOf(news.getClassify())));
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String res;
        if (news != null ){
            res = JsonUtil.toJson(200, "OK", news);
        }else {
            res = JsonUtil.toJson(556, "查询出错", null);
        }
        response.getWriter().write(res);
    }
}
