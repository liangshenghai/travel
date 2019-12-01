package cn.itcast.travel.session;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

public class RedisSession implements HttpSession {

    protected HttpServletRequest request;
    protected  HttpSession session;
    protected  String mysessionid;

   private RedisSession(){};

    public RedisSession(HttpServletRequest request,String mysessionid) {

        this.request = request;
        this.mysessionid = mysessionid;
        session = request.getSession();
    }

    public Enumeration getAttributeNames() {
        return session.getAttributeNames();
    }

    public long getCreationTime() {
        return session.getCreationTime();
    }

    public long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }

    public int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }

    public ServletContext getServletContext() {
        return session.getServletContext();
    }

    public HttpSessionContext getSessionContext() {
        return session.getSessionContext();
    }

    public Object getValue(String paramString) {
        return session.getValue(paramString);
    }

    public String[] getValueNames() {
        return session.getValueNames();
    }

    public boolean isNew() {
        return session.isNew();
    }

    public void putValue(String paramString, Object paramObject) {
        session.putValue(paramString, paramObject);
    }

    public void removeValue(String paramString) {
        session.removeValue(paramString);
    }


    public String getId() {
        return mysessionid;
    }

    public void invalidate() {
        RedisOps.hdelKey(getId());
    }

    public void setAttribute(String paramString, Object paramObject) {
        System.out.println("setAttribute:"+getId());
        // 设置redis对象
       RedisOps.hsetObject(getId(),paramString,paramObject);
    }

    public Object getAttribute(String paramString) {

        System.out.println("getAttribute:"+getId());
        // 获取redis对象
        return  RedisOps.hgetObject( getId(),paramString);
    }

    public void removeAttribute(String paramString) {
        RedisOps.hdelete(getId(),paramString);
    }

    public void setMaxInactiveInterval(int paramInt) {
        //设置Redis的失效时间
        RedisOps.hexpire(getId(),paramInt);
    }




}
