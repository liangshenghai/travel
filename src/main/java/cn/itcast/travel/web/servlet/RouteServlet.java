package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取页面上传递的参数
        String currentPageParam = request.getParameter("currentPage");
        String pageSizeParam = request.getParameter("pageSize");
        String cidParam = request.getParameter("cid");

        // 2. 页面上参数转换为int
        int currentPage = 1;// 默认是第一页
        if(!StringUtils.isEmpty(currentPageParam)){
            currentPage = Integer.parseInt(currentPageParam);
        }
        int pageSize = 5;// 默认是5条
        if(!StringUtils.isEmpty(pageSizeParam)){
            pageSize = Integer.parseInt(pageSizeParam);
        }
        int cid = 0;//
        if(!StringUtils.isEmpty(cidParam)){
            cid = Integer.parseInt(cidParam);
        }

        //3. 调用service，做分类查询
        PageBean<Route> pageBean = routeService.pageQuery(currentPage,pageSize,cid);

        //4. 写回到客户端
        writeObjectJson(pageBean,response);
    }

}
