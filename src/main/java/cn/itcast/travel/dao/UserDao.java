package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查找User对象
     *
     * @param username
     * @return
     */
    User findUserByUserName(String username);

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 根据激活码查找用户
     *
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 更新用户状态
     *
     * @param user
     */
    boolean updateStatus(User user);

    /**
     * 根据用户名和密码查找用户
     *
     * @param username
     * @param password
     * @return
     */
    User findUserByUserNameAndPassword(String username, String password);
}
