package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.news.domain.User;
import com.news.service.UserService;
import com.news.utils.FindUserIdByName;
import com.news.utils.GetPostData;
import com.news.utils.JsonUtil;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UpdatePasswdServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        String json;
        Cookie[] cookies = req.getCookies();
        Integer id = FindUserIdByName.getUserIdByName(cookies);
        if (id == null) {
            json = JsonUtil.toJson(401, "error", "用户未登录");
            resp.getWriter().write(json);
            return;
        }
//        Map<String, String[]> params = req.getParameterMap();

        String paramsString = GetPostData.getPostData(req);
        JSONObject params = JSONObject.parseObject(paramsString);
        if (params == null) {
            json = JsonUtil.toJson(404, "error", "请传入原密码和新密码");
            resp.getWriter().write(json);
            return;
        }
        String newPasswd = params.getString("newpasswd");
        String priPasswd = params.getString("pripasswd");
//        String[] newPasswd0 = params.get("newpasswd");
//        String[] priPasswd0 = params.get("pripasswd");

        if (newPasswd == null || priPasswd == null) {
            json = JsonUtil.toJson(404, "eror", "参数不完整");
            resp.getWriter().write(json);
            return;
        }
//        String newPasswd = newPasswd0[0];
//        String priPasswd = priPasswd0[0];
        if (newPasswd == "" || priPasswd == "") {
            json = JsonUtil.toJson(404, "error", "参数不能为空");
            resp.getWriter().write(json);
            return;
        }
        UserService service = new UserService();
        User user = service.getUserById(id);
        if (!user.getPasswd().equals(priPasswd)) {
            json = JsonUtil.toJson(404, "error", "原密码不正确");
            resp.getWriter().write(json);
            return;
        }
        boolean isSuccess = service.updatePasswdByUserId(id, newPasswd);
        if (isSuccess) {
            json = JsonUtil.toJson(200, "OK", null);
        } else {
            json = JsonUtil.toJson(404, "error", "修改失败");
        }
        resp.getWriter().write(json);
    }
}
