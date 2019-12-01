package cn.itcast.travel.session;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class MySessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /**
         * 自定义sessionid
         */
        Cookie[] cookies = request.getCookies();
        String _mysessionid = null;
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if("_mysessionid".equals(cookie.getName())){
                    _mysessionid = cookie.getValue();
                }
            }
        }
        if(StringUtils.isEmpty(_mysessionid)){
            _mysessionid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("_mysessionid", _mysessionid);
            response.addCookie(cookie);
        }

        HttpServletRequestWrapper requestWrapper = new MyRequest(request,response,_mysessionid);
        filterChain.doFilter(requestWrapper,response);
    }

    @Override
    public void destroy() {

    }
}
