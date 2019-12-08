package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取页面上传递的参数
        String currentPageParam = request.getParameter("currentPage");
        String pageSizeParam = request.getParameter("pageSize");
        String cidParam = request.getParameter("cid");
        // 接受查询的参数
        String rname = request.getParameter("rname");
        if(!StringUtils.isEmpty(rname)) {
            rname = new String(rname.getBytes("ISO-8859-1"), "UTF-8");
        }
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
        PageBean<Route> pageBean = routeService.pageQuery(currentPage,pageSize,cid,rname);

        //4. 写回到客户端
        writeObjectJson(pageBean,response);
    }


    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受传过来的rid参数
        String ridParam = request.getParameter("rid");
        int rid = 0;
        if(!StringUtils.isEmpty(ridParam)){
            rid = Integer.parseInt(ridParam);
        }
        // 2. 查询route详情页
        Route route =  routeService.findOne(rid);

        //3. 写回到客户端
        writeObjectJson(route,response);

    }


    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String ridParam = request.getParameter("rid");
        int rid = 0;
        if(!StringUtils.isEmpty(ridParam)){
            rid = Integer.parseInt(ridParam);
        }
        // 从当前登录用户获取uid
        User user = (User) request.getSession().getAttribute("sessionUser");
        int uid = 0;
        if(user != null){
            uid = user.getUid();
        }

        //根据rid和uid查询是否收藏
       boolean flag =  routeService.isFavorite(rid,uid);

        //返回到客户端
        writeJson(new ResultInfo(flag),response);
    }

    public void favoriteRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String ridParam = request.getParameter("rid");
        int rid = 0;
        if(StringUtils.isEmpty(ridParam) || "0".equals(ridParam)){
            writeJson(new ResultInfo(false,"旅游线路不存在，请刷新页面重试！"),response);
            return;
        }else {
            rid = Integer.parseInt(ridParam);
        }
        // 从当前登录用户获取uid
        User user = (User) request.getSession().getAttribute("sessionUser");
        int uid = 0;
        if(user == null){
            //没有登录，让用户返回登录
            writeJson(new ResultInfo(false,"尚未登录，请您先登录！"),response);
            return;
        }else {
            uid = user.getUid();
        }
        //添加收藏
        routeService.favoriteRoute(rid,uid);
        writeJson(new ResultInfo(true),response);
        return;
    }
}
