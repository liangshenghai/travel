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

@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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


    /**
     * 返回服务器的json信息
     * @param info
     * @param response
     * @throws IOException
     */
    private void writeJson(ResultInfo info,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonStr);
    }
}
