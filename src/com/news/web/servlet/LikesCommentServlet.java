package com.news.web.servlet;

import com.news.service.CommentService;
import com.news.utils.FindUserIdByName;
import com.news.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LikesCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        Cookie[] cookies = req.getCookies();
        Integer userid = FindUserIdByName.getUserIdByName(cookies);
        if (userid == null){ // cookie中找不到username的字段，未登录
            resp.getWriter().write(JsonUtil.toJson(401, "未登录", null));
            return;
        }
//        if (userid == 0) { // 找不到该用户
//            resp.getWriter().write(JsonUtil.toJson(600, "没有该用户", null));
//            return;
//        }

        CommentService service = new CommentService();
        Map<String, String[]> params =req.getParameterMap();
        int id = Integer.valueOf(params.get("id")[0]);
        boolean isLiked = service.getIsLiked(id, userid);
        if (isLiked) {
            resp.getWriter().write(JsonUtil.toJson(553, "err", "已经点赞"));
            return;
        }
        boolean isSuccess = service.addLikesById(userid, id);
        String json;
        if (isSuccess){
            json = JsonUtil.toJson(200, "OK", null);
        } else {
            json = JsonUtil.toJson(409, "error", "点赞失败");
        }
        resp.getWriter().write(json);
    }
}
