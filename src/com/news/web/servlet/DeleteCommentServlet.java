package com.news.web.servlet;

import com.news.domain.Comment;
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

public class DeleteCommentServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        String json = "";
        Map<String, String[]> params = req.getParameterMap();
        int id = Integer.valueOf(params.get("id")[0]); // 要删除的评论的ID
        Cookie[] cookies = req.getCookies();
        Integer userid = FindUserIdByName.getUserIdByName(cookies);
        if (userid == null) {
            json = JsonUtil.toJson(401, "error", "用户未登录");
            resp.getWriter().write(json);
            return;
        }
        CommentService service = new CommentService();
        Comment comment = service.getSingleCommnetById(id);
        if (comment == null) {
            json = JsonUtil.toJson(404, "error", "不存在该条评论");
            resp.getWriter().write(json);
            return;
        }
        if (comment.getFromuid() != userid) {
            json = JsonUtil.toJson(403, "error", "你不是该条评论的拥有者");
            resp.getWriter().write(json);
            return;
        }
        boolean isSuccess = service.deleteCommentById(comment.getId());
        if (isSuccess) {

            json = JsonUtil.toJson(200, "OK", null);
        } else {
            json = JsonUtil.toJson(400, "error", "删除评论失败");
        }
        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }
}
