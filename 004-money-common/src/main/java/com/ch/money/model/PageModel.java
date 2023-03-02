package com.ch.money.model;

import java.io.Serializable;

public class PageModel implements Serializable {
    private Long totalCount;
    private Long totalPage;
    private Long cunPage;
    private Integer firstPage = 1;
    private Integer pageSize = 9;

    public PageModel() {
    }

    public PageModel(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        this.totalPage = totalCount%pageSize>0?totalCount/pageSize+1:totalCount/pageSize;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public Long getCunPage() {
        return cunPage;
    }

    public void setCunPage(Long cunPage) {
        this.cunPage = cunPage;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
