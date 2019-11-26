package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1. 从redis先查询，由于有顺序，所以需要使用sortedset
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categorySet = jedis.zrange("category", 0, -1);
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categoryList = null;
        //2. redis没有值，从数据库中查并缓存到redis中
        if(categorySet == null || categorySet.isEmpty()){
            System.out.println("从数据库中查询.....");
            //从数据库中查询
            categoryList = categoryDao.findAll();
            //缓存到redis中
            categoryList.stream().forEach(category -> {
                jedis.zadd("category",category.getCid(),category.getCname());
            });

        }else{
            System.out.println("从redis中查询.....");
            //把set转化为list
            categoryList = new ArrayList<>();
            for (Tuple tuple : categorySet) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
