package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码，校验验证码是否正确？
        String check = request.getParameter("check");
        //验证不通过，返回信息
        if(check == null || !check.equalsIgnoreCase((String) request.getSession().getAttribute("CHECKCODE_SERVER"))){
            writeJson(new ResultInfo(false,"验证码不正确！"),response);
            return ;
        }

        //1. 从request中取得数据封装到User
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //2. 调用UserService的register方法
        ResultInfo resultInfo = new ResultInfo();
        boolean b = userService.register(user);
        if(b){
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户注册失败！");
        }

        //3.返回json数据
        writeJson(resultInfo,response);
        return;
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        String code = request.getParameter("code");
        String msg = "";
        if(code != null && !"".equals(code)){
            boolean b = userService.activeUser(code);
            if(b){// 激活成功
                msg="<h2 color='green'>激活成功!<a href='"+request.getContextPath()+"/login.html'>【点此到登录页】</a></h2>";
            }else{// 激活失败
                msg="<h1 color='red'>激活失败，请联系管理员。</h1>";
            }
        }else{
            msg="<h1 color='red'>邮件激活码获取失败！请联系管理员。</h1>";
        }
        response.getWriter().write(msg);
        return;
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.校验验证码信息
        String check = request.getParameter("check");
        if (check == null || !check.equalsIgnoreCase((String) request.getSession().getAttribute("CHECKCODE_SERVER"))) {
            writeJson(new ResultInfo(false, "验证码不正确！"), response);
            return;
        }
        // 2. 获取登录表单用户信息
        Map<String, String[]> map = request.getParameterMap();
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.根据用户名和密码查询用户信息
        loginUser = userService.login(loginUser.getUsername(), loginUser.getPassword());
        ResultInfo info = new ResultInfo();
        //4.用户为空，则登录不正确
        if (loginUser == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误！");

            //5. 用户不为空，未激活，提示先激活
        } else if (loginUser != null && "N".equals(loginUser.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户未激活，请您先激活！");

            //6 . 用户登录成功，保存信息到session中
        } else if (loginUser != null && "Y".equals(loginUser.getStatus())) {
            info.setFlag(true);
            //7.保存到session中
            request.getSession().setAttribute("sessionUser", loginUser);
        }

        //8.往客户端写json信息
        writeJson(info, response);
    }

    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取信息
        User user = (User) request.getSession().getAttribute("sessionUser");

        //从session查询的数据返回到客户端
        writeObjectJson(user,response);
    }

    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //清空session
        request.getSession().invalidate();
        //跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
