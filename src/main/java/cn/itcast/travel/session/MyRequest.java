package cn.itcast.travel.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyRequest extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String _mysessionid;

    public MyRequest(HttpServletRequest request,HttpServletResponse response,String _mysessionid) {
        super(request);
        this.request = request;
        this.response = response;
        this._mysessionid = _mysessionid;
    }

    @Override
    public HttpSession getSession() {
        /*
        TODO
         以后根据这个做判断需要提供的哪个session
         */
        return new RedisSession(request,_mysessionid);
    }
}
