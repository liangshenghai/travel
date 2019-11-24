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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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


    /**
     * 返回服务器的json信息
     *
     * @param info
     * @param response
     * @throws IOException
     */
    private void writeJson(ResultInfo info, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), info);
    }
}
