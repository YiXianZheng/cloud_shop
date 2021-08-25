package com.shop.order.util;

import java.util.List;

public class PageResult {

    private static final long serialVersionUID = 1L;
    private String gridId;
    private Integer start;
    private Integer limit;
    private Long total;
    private List<?> rows;

    public PageResult() {
    }

    public Integer getStart() {
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getGridId() {
        return this.gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
