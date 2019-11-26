package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao  = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSize, int cid) {
        //1. 查询总记录数
        int totalCount = routeDao.getTotalCount(cid);

        //2. 通过totalCount,currentPage,pageSize 构造PageBean对象
        PageBean<Route> pageBean = new PageBean<>(totalCount,currentPage,pageSize);

        //3. 调用Dao，分类查询
        Integer start = pageBean.getStart();
        List<Route> pageData = routeDao.getPageData(cid, start, pageSize);
        pageBean.setPageList(pageData);

        return pageBean;
    }
}
