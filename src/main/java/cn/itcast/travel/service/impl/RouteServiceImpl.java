package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao  = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();



    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSize, int cid,String rname) {
        //1. 查询总记录数
        int totalCount = routeDao.getTotalCount(cid,rname);

        //2. 通过totalCount,currentPage,pageSize 构造PageBean对象
        PageBean<Route> pageBean = new PageBean<>(totalCount,currentPage,pageSize);

        //3. 调用Dao，分类查询
        Integer start = pageBean.getStart();
        List<Route> pageData = routeDao.getPageData(cid, start, pageSize, rname);
        pageBean.setPageList(pageData);

        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        Route route =  routeDao.findOne(rid);

        //查询旅游路线对应图片信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        route.setRouteImgList(routeImgList);

        //查询旅游路线对应的商家信息
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        int count = favoriteDao.findCountFavoriteByRid(rid);
        route.setCount(count);

        return route;
    }

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findFavoriteByRidAndUid(rid, uid);
        if(favorite != null)
            return  true;

        return false;
    }

    @Override
    public void favoriteRoute(int rid, int uid) {
        favoriteDao.addFavorite(rid,uid);
    }


}
