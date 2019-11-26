package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    int getTotalCount(int cid);

    /**
     * 分页limite查询数据
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> getPageData(int cid, int start, int pageSize);

}
