package com.news.web.servlet;

import com.news.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        Cookie newCookie = new Cookie("username", null);
        newCookie.setMaxAge(0);
        resp.addCookie(newCookie);
        resp.getWriter().write(JsonUtil.toJson(200, "OK", null));
    }
}
