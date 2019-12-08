package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    int getTotalCount(int cid,String rname);

    /**
     * 分页limite查询数据
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> getPageData(int cid, int start, int pageSize,String rname);

    /**
     * 根据rid查询route对象
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
