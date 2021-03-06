package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 根据rid查询旅游的所有图片信息
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);

}
