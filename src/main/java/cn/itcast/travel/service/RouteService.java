package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    /**
     * 分页查询route
     * @param currentPage
     * @param pageSize
     * @param cid
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int currentPage, int pageSize, int cid ,String rname);

    /**
     * 根据rid查询一个Route
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * 根据rid和uid查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void favoriteRoute(int rid, int uid);
}
