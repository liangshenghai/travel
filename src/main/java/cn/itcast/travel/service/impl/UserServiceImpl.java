package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.Md5Util;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        //1.检查用户是否存在
        User u = userDao.findUserByUserName(user.getUsername());
        if (u != null) {
            return false;
        }
        //2. 保存用户
        try {
            //密码md5加密存储
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
            //邮件的状态和激活码设定
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());

            boolean b = userDao.save(user);
            if (b) {//保存成功开始发送邮件
                String title = "老梁旅游网，邮件激活";
                String content = "老梁旅游网，注册邮箱<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活</a><br>" +
                        "<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574575230139&di=bae6afa07d48219388b3ee2afd8f4926&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fimage%2F43278246%2Fleisineiyi%2520%25287%2529_960x540.jpg'/>";
                //开始发送邮件
                MailUtils.sendMail(user.getEmail(), content, title);
            }

            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean activeUser(String code) {
        //根据激活码找到User
        User user = userDao.findUserByCode(code);
        //更改User的未激活状态为Y(已激活状态)
        if (user != null && "N".equals(user.getStatus())) {
            return userDao.updateStatus(user);
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        User user = null;
        //password需要先加密
        try {
            password = Md5Util.encodeByMd5(password);
            // 到数据库中查找User信息
            user = userDao.findUserByUserNameAndPassword(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }
}
