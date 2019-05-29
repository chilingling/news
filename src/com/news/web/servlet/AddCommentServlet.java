package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.Comment;
import com.news.domain.User;
import com.news.service.CommentService;
import com.news.service.UserService;
import com.news.utils.FindUserIdByName;
import com.news.utils.GetPostData;
import com.news.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        String params = GetPostData.getPostData(req);
        Comment comment = JSONObject.parseObject(params, Comment.class);
        Cookie[] cookies = req.getCookies();
        Integer id = FindUserIdByName.getUserIdByName(cookies);
        if (id == null){ // cookie中找不到username的字段，未登录
            resp.getWriter().write(JsonUtil.toJson(401, "未登录", null));
            return;
        }
//        if (id == 0) { // 找不到该用户
//            resp.getWriter().write(JsonUtil.toJson(600, "没有该用户", null));
//            return;
//        }
        comment.setFromuid(id); // 设置评论的用户
        comment.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        CommentService commentService = new CommentService();
        Comment comment1 = commentService.addComment(comment); // 添加评论
        UserService userService = new UserService();
        User user = userService.getUserById(id);
        user.setPasswd(null);
        String json;
        if (comment1 != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user);
            jsonObject.put("detail", comment1);
            json = JsonUtil.toJson(200, "OK", jsonObject);
        } else{
            json = JsonUtil.toJson(404, "插入失败", null);
        }
        resp.getWriter().write(json);
    }
}
