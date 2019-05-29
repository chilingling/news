package com.news.web.servlet;

import com.news.domain.Classify;
import com.news.service.ClassifyService;
import com.news.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllClassifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        ClassifyService service = new ClassifyService();
        List<Classify> list = service.getAllClassify();
        String json;
        if (list != null){
            json = JsonUtil.toJson(200, "OK", list);
        }else {
            json = JsonUtil.toJson(453, "error", null);
        }
        resp.getWriter().write(json);
    }
}
