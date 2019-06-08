package com.news.web.servlet.jsp;

import com.news.domain.Classify;
import com.news.domain.Comment;
import com.news.domain.News;
import com.news.service.ClassifyService;
import com.news.service.CommentService;
import com.news.service.NewsService;
import com.news.utils.ClassifyListToMap;
import com.news.utils.FindUserIdByName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect("/news/index");
            return;
        }
        int id = Integer.valueOf(idParam);
        NewsService newsService = new NewsService();
        News news = newsService.getDetailById(id);
        ClassifyService classifyService = new ClassifyService();
        List<Classify> classifyList = classifyService.getAllClassify();
        Map<Integer, String> classifyMap = ClassifyListToMap.toMap(classifyList);
        req.setAttribute("classifyId", Integer.valueOf(news.getClassify()));
        String classifyName = classifyMap.get(Integer.valueOf(news.getClassify()));
        news.setClassify(classifyName);

        CommentService commentService = new CommentService();

        Cookie[] cookies = req.getCookies();
        Integer userid = FindUserIdByName.getUserIdByName(cookies);
        List<Comment> commentList =  commentService.getCommnetListById(id, 0, 10, userid == null ? 0 : userid);

        req.setAttribute("classifyList", classifyList);
        req.setAttribute("news", news);
        req.setAttribute("commentList", commentList);
        req.getRequestDispatcher("detail.jsp").forward(req,resp);
    }
}
