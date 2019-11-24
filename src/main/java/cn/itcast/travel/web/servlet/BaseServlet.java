package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI(); // /travel/user/add
        // 通过请求路径获取method字符串 add方法
        String methodStr = uri.substring(uri.lastIndexOf("/") + 1);

        try {
            // 拿到子类的反射对象，java中this代表谁调用我，我就代表谁，所以子类被调用时，this就代码当前子类
            Class<? extends BaseServlet> aClass = this.getClass();
            Method method = aClass.getMethod(methodStr, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(aClass.newInstance(), req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回服务器的json信息
     *
     * @param info
     * @param response
     * @throws IOException
     */
    protected void writeJson(ResultInfo info, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    /**
     * 返回对象的json信息
     * @param t
     * @param response
     * @param <T>
     * @throws IOException
     */
    protected <T> void writeObjectJson(T t,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), t);
    }
}
