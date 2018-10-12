package com.neuedu.vo;

import java.io.Serializable;
import java.util.List;

// 分页模型
public class PageModel<T> implements Serializable {
    // 每页数据
    private List<T> data;
    // 总共页数
    private Long totalPage;
    // 是否为首页
    private boolean isFirst;
    // 是否为最后一页
    private boolean isLast;
    // 当前页
    private Integer CurrentPage;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public Integer getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        CurrentPage = currentPage;
    }
}
