package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByUserName(String username) {

        String sql = "select * from tab_user where username = ?";

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean save(User user) {

        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";

        int update = jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(),
                user.getEmail(), user.getStatus(), user.getCode());

        if (update > 0) {
            return true;
        }

        return false;
    }

    @Override
    public User findUserByCode(String code) {

        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean updateStatus(User user) {
        String sql = "update tab_user set status='Y' where uid = ?";
        int update = jdbcTemplate.update(sql, user.getUid());
        return update > 0;
    }

    @Override
    public User findUserByUserNameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return user;
    }
}
