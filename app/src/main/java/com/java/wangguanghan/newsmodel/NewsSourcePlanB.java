package com.java.wangguanghan.newsmodel;

import java.util.List;

public class NewsSourcePlanB {

    String pagesize;
    int total;
    List<NewsBeanPlanB> data;
    String currentPage;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<NewsBeanPlanB> getData() {
        return data;
    }

    public void setData(List<NewsBeanPlanB> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }
}


