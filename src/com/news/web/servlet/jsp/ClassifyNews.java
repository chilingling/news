package com.news.web.servlet.jsp;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.Classify;
import com.news.domain.News;
import com.news.service.ClassifyService;
import com.news.service.NewsService;
import com.news.utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClassifyNews extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");
        String classifyid = req.getParameter("classify");

        int page = 1;
        int size = 10;
        if (pageParam != null) {
            page = Integer.valueOf(pageParam);
        }
        if (sizeParam != null) {
            size = Integer.valueOf(sizeParam);
        }
        if (classifyid == null) {
            resp.sendRedirect("/news/index");
            return;
        }
        ClassifyService classifyService = new ClassifyService();
        NewsService newsService = new NewsService();
        int num = classifyService.getClassifyNewsNum(classifyid);

        PageUtil pageUtil = new PageUtil(num, page, size);
        int cpage = pageUtil.getPageNumber();
        int csize = pageUtil.getSize();
        int start = (cpage - 1) *csize;

        List<Classify> classifyList = classifyService.getAllClassify();
        List<News> newsList =  classifyService.getNewsByClassify(classifyid, start, csize);
        String classifyName = "";
        int classifyIdInt = Integer.valueOf(classifyid);
        for (int i = 0; i < classifyList.size(); i++) {
            Classify item = classifyList.get(i);
            if (item.getId() == classifyIdInt) {
                classifyName = item.getName();
            }
        }
        JSONObject data = pageUtil.getResult();
        data.put("content", newsList);
        req.setAttribute("classifyList", classifyList);
        req.setAttribute("data", data);
        req.setAttribute("classifyName", classifyName);
        req.setAttribute("preLink", "/news/classifynews?classify=" + classifyIdInt + "&");
        req.setAttribute("classifyId", classifyIdInt);
        req.getRequestDispatcher("classify.jsp").forward(req, resp);

    }
}
