package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.User;
import com.news.service.UserService;
import com.news.utils.GetPostData;
import com.news.utils.JsonUtil;

import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException{

    }
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        request.setCharacterEncoding("utf-8");
        String res = GetPostData.getPostData(request);
        User user = JSONObject.parseObject(res, User.class);
        UserService service = new UserService();
        User user1 = null;
        try {
            user1 =  service.login(user.getName(), user.getPasswd());
        } catch (SQLException e){
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json;
        if (user1 != null) {
//            HttpSession session = request.getSession();
            user1.setPasswd("");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user1.getId());
            jsonObject.put("name", user1.getName());
            jsonObject.put("phone", user1.getPhone());
            json = JsonUtil.toJson(200, "登录成功", jsonObject);
            Cookie username = new Cookie("username", user.getName());
            username.setMaxAge(1000*60);
            username.setHttpOnly(true);
            username.setSecure(true);
            response.addCookie(username);
//            request.getSession().setAttribute("user", user.getName());
        } else {
            json = JsonUtil.toJson(401, "登录失败", "登录失败，请检查账号和密码");
        }
        response.getWriter().write(json);
    }
}
