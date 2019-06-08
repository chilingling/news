package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.User;
import com.news.service.UserService;
import com.news.utils.GetPostData;
import com.news.utils.JsonUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.mail.internet.ContentType;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String res = GetPostData.getPostData(request);
        User user = JSONObject.parseObject(res, User.class);

        UserService service = new UserService();
        boolean isRegisterSuccess = service.regist(user);
        String json;
        if (isRegisterSuccess) {
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
            json = JsonUtil.toJson(200, "ok", user);
            Cookie username = new Cookie("username", user.getName());
            username.setMaxAge(60*60);
            username.setHttpOnly(true);
            response.addCookie(username);
        } else {
            json = JsonUtil.toJson(555, "ok", "注册失败");
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
