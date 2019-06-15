package com.news.web.servlet.jsp;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.Classify;
import com.news.domain.Comment;
import com.news.domain.News;
import com.news.domain.User;
import com.news.service.ClassifyService;
import com.news.service.CommentService;
import com.news.service.NewsService;
import com.news.service.UserService;
import com.news.utils.ClassifyListToMap;
import com.news.utils.FindUserIdByName;
import com.news.utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String idParam = req.getParameter("id");

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
        UserService userService = new UserService();
        Cookie[] cookies = req.getCookies();
        Integer userid = FindUserIdByName.getUserIdByName(cookies);

        int num = commentService.getCommentsNum(id);
        PageUtil pageUtil = new PageUtil(num, page, size);
        JSONObject JSONdata = pageUtil.getResult();

        int pageNum = pageUtil.getPageNumber();
        int pageSize = pageUtil.getSize();
        int start = (pageNum - 1) * size;

        List<Comment> commentList =  commentService.getCommnetListById(id, start, size, userid == null ? 0 : userid);

        List<JSONObject> data = new ArrayList<JSONObject>();
        for (int i = 0; i < commentList.size(); i++){
            User user = userService.getUserById(commentList.get(i).getFromuid());
            user.setPasswd(null);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user);
            jsonObject.put("detail", commentList.get(i));
            data.add(jsonObject);
        }
//        JSONdata.put("content", data);
        req.setAttribute("classifyList", classifyList);
        req.setAttribute("news", news);
        req.setAttribute("commentList", data);
        req.setAttribute("pagination", JSONdata);
        req.setAttribute("preLink", "/news/detailnews?id=" + id + "&");
        req.getRequestDispatcher("detail.jsp").forward(req,resp);
    }
}
