package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 邮件激活用户
     *
     * @param code
     * @return
     */
    boolean activeUser(String code);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
