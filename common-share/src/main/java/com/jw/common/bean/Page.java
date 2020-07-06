package com.jw.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenliang on 2017/6/8.
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID   = -4246122725865764580L;

    private static final int  DEFAULT_PAGE_SIZE  = 20;
    private static final int  DEFAULT_FIRST_SIZE = 1;
    private static final int  DEFAULT_TOTAL_SIZE = -1;
    private Integer           total              = -1;
    private Integer           pageSize           = 20;
    private Integer           currentPage;
    private Integer           startRow;
    private Long              lastId;
    private List<T>           rows               = null;
    private boolean           up;

    public Page() {
    }

    public Page(Integer pageSize, Integer currentPage) {
        this.setPageSize(pageSize.intValue());
        this.setCurrentPage(currentPage);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return Integer.valueOf(this.currentPage == null ? 0 : this.currentPage.intValue());
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startRow = this.pageSize * (this.getCurrentPage().intValue() - 1);
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public static int getDefaultpagesize() {
        return 20;
    }

    public static int getDefaultfristpage() {
        return 1;
    }

    public static int getDefaulttotleitem() {
        return -1;
    }

    public Long getLastId() {
        return this.lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public int getTotalPage() {
        int pgSize = this.getPageSize();
        int total = this.getTotal();
        int result = total / pgSize;
        if (total == 0 || total % pgSize != 0) {
            ++result;
        }

        return result;
    }

    public boolean isUp() {
        return this.up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
}
