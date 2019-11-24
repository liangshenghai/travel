package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //获取验证码
            String code = request.getParameter("code");
            String msg = "";
            if(code != null && !"".equals(code)){
                boolean b = userService.activeUser(code);
                if(b){// 激活成功
                    msg="<h2 color='green'>激活成功!<a href='login.html'>【点此到登录页】</a></h2>";
                }else{// 激活失败
                    msg="<h1 color='red'>激活失败，请联系管理员。</h1>";
                }
            }else{
                msg="<h1 color='red'>邮件激活码获取失败！请联系管理员。</h1>";
            }
            response.getWriter().write(msg);
            return;
    }
}
