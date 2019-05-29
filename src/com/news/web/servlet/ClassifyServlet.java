package com.news.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.news.domain.Classify;
import com.news.domain.News;
import com.news.service.ClassifyService;
import com.news.utils.ClassifyListToMap;
import com.news.utils.JsonUtil;
import com.news.utils.PageUtil;
import sun.nio.cs.ext.HKSCS;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.rmi.ServerException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ClassifyServlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException{
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> params = request.getParameterMap();
        ClassifyService service = new ClassifyService();
//        String classify = URLDecoder.decode(params.get("classify")[0], "utf-8");
        String classify = params.get("classify")[0];
        int num = service.getClassifyNewsNum(classify);
        int initPage = Integer.valueOf(params.get("page")[0]);
        int initSize = Integer.valueOf(params.get("size")[0]);
        PageUtil pageUtil = new PageUtil(num, Integer.valueOf(params.get("page")[0]), Integer.valueOf(params.get("size")[0]));
        int page = pageUtil.getPageNumber();
        int size = pageUtil.getSize();
        int start = (page - 1)* size;
        List<Classify> classifyList = service.getAllClassify();
        List<News> list = service.getNewsByClassify(classify, start, size);
        list = ClassifyListToMap.convertClassifyName(list, classifyList);
        String res;
        if (list != null || num == 0) {
            JSONObject data = pageUtil.getResult();
            data.put("content", initPage > pageUtil.getTotalpages() ? new int[0] : list);
            String msg = initPage > pageUtil.getTotalpages() ? "请求超出范围" : "OK";
            res = JsonUtil.toJson(200, msg, data);
        } else {
            res = JsonUtil.toJson(433, "查找出错", null);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(res);
    }
}
