package com.news.utils;

import com.news.domain.Classify;
import com.news.domain.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassifyListToMap {
    public static Map<Integer, String> toMap (List<Classify> list) {
        Map<Integer, String> res = new HashMap<Integer, String>();
        for (int i = 0; i < list.size(); i++){
            Classify item = list.get(i);
            res.put(item.getId(), item.getName());
        }
        return res;
    }
    public static List<News> convertClassifyName (List<News> newsList, List<Classify> classifyList) {
        Map<Integer, String> classifyMap = toMap(classifyList);
        List<News> res = new ArrayList<News>();
        for (int i = 0; i < newsList.size(); i++) {
            News item = newsList.get(i);
            item.setClassify(classifyMap.get(Integer.valueOf(item.getClassify())));
            res.add(item);
        }
        return res;
    }
}
