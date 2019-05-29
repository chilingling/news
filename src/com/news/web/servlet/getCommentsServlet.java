package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.news.domain.Comment;
import com.news.domain.User;
import com.news.service.CommentService;
import com.news.service.UserService;
import com.news.utils.FindUserIdByName;
import com.news.utils.JsonUtil;
import com.news.utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class getCommentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        Map<String, String[]> params = req.getParameterMap();
        CommentService service = new CommentService();
        UserService  userService = new UserService();
        int newsid = Integer.valueOf(params.get("newsid")[0]);
        int page = Integer.valueOf(params.get("page")[0]);
        int size = Integer.valueOf(params.get("size")[0]);
        int num =service.getCommentsNum(newsid);
        PageUtil pageUtil = new PageUtil(num, page, size);
        int start = (pageUtil.getPageNumber() - 1) * pageUtil.getSize();
        Cookie[] cookies = req.getCookies();
        Integer id= 0;
        id  = FindUserIdByName.getUserIdByName(cookies);
        List<Comment> comment = service.getCommnetListById(newsid, start, size, id == null ? 0 : id);

        List<JSONObject> data = new ArrayList<JSONObject>();
        for (int i = 0; i < comment.size(); i++){
            User user = userService.getUserById(comment.get(i).getFromuid());
            user.setPasswd(null);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user);
            jsonObject.put("detail", comment.get(i));
            data.add(jsonObject);
        }
        String json;
        if (comment != null || num == 0){
            JSONObject pager = pageUtil.getResult();
            pager.put("content", page > pageUtil.getTotalpages() ? new int[0] : data);
            String msg = page > pageUtil.getTotalpages() ? "请求超出范围" : "OK";
            json = JsonUtil.toJson(200, msg, pager);
        }else {
            json = JsonUtil.toJson(553, "error", null);
        }
        resp.getWriter().write(json);
    }
}
