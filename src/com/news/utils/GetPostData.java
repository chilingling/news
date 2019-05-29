package com.news.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class GetPostData {
    /**
     *  读取POST传过来的数据
     * @param req
     * @return
     */
    public static String getPostData (HttpServletRequest req) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = req.getReader();
            while (null != (line = reader.readLine())){
                data.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        String params = data.toString();
        return params;
    }
}
