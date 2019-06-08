package com.news.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.List;

public class PageUtil<T> {
    private List<T> list; // 对象记录结果集
    private int totalcount = 0; // 总记录数
    private int size = 20; // 每页显示记录数
    private int totalpages = 1; // 总页数
    private int currentpage = 1; // 当前页

    private boolean isFirstPage = false; // 是否为第一页
    private boolean isLastPage = false;  // 是否为最后一页

    public PageUtil(int total, int pageNumber){
        init(total, pageNumber, size);
    }
    public PageUtil(int total, int pageNumber, int size){
        init(total, pageNumber, size);
    }
    private void init (int total, int pageNumber, int size){
        // 设置基本参数
        this.totalcount = total;
        this.size = size;
        this.totalpages = (this.totalcount - 1) / this.size + 1;

        // 根据输入可能错误的当前号码进行自动纠正
        if (pageNumber < 1) {
            this.currentpage = 1;
        } else if(pageNumber > this.totalpages) {
            this.currentpage = this.totalpages;
        }else {
            this.currentpage = pageNumber;
        }
        // 基本参数设定之后进行导航页面的计算
//        calcNavigatePageNumbers();
        // 页面边界的判定
        judgePageBoundary();
    }
    /**
     * 计算导航页
     */
//    private void calcNavigatePageNumbers(){
//    }

    private void judgePageBoundary(){
        isFirstPage = currentpage == 1;
        isLastPage = currentpage == totalpages && currentpage != 1;
    }
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 得到当前页的内容
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 得到记录总数
     * @return
     */
    public int getTotal() {
        return totalcount;
    }

    /**
     * 得到每页显示多少条记录
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 得到当前页号
     * @return
     */
    public int getPageNumber() {
        return currentpage;
    }

    /**
     * 得到所有导航页号
     * @return
     */
//    public int[] getNavigatePageNumbers() {
//        return navigatePageNumbers;
//    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public JSONObject getResult () {
        JSONObject res = new JSONObject();
        res.put("totalcount", getTotal());  // 总记录条数
        res.put("totalpages", getTotalpages()); // 总页数
        res.put("currentPage", getPageNumber()); // 当前页面号
        res.put("size", getSize()); // 每一页的数量
        res.put("isFirstPage", isFirstPage()); // 是否是第一页
        res.put("isLastPage", isLastPage()); // 是否是最后一页
        return res;
    }
}
