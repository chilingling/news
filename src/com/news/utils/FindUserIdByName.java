package com.news.utils;

import com.news.domain.User;
import com.news.service.UserService;

import javax.servlet.http.Cookie;

public class FindUserIdByName {
    public static Integer getUserIdByName (Cookie[] cookies) {
        String username = null;
        if (cookies == null) {
            return  0;
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals("username")) {
                username = cookie.getValue().toString();
            }
        }
        if (username == null) {
            return  null;
        }
        UserService service = new UserService();
        Integer id = service.getUserId(username); // 获取用户id
        return id;
    }
}
