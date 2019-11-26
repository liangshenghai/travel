package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private Integer totalCount;//数据总条数
    private Integer totalPage;//总页数   totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize)+1
    private Integer currentPage;//当前页数
    private Integer pageSize;//每页显示的条数
    private Integer start; //查询数据开始的位置  (currentPage - 1) * pageSize

    private List<T> pageList ;// 每页显示的具体数据对象集合


    private PageBean(){ }

    public PageBean(Integer totalCount, Integer currentPage, Integer pageSize) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return (currentPage - 1) * pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }


}
