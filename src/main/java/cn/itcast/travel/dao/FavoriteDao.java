package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    /**
     * 根据rid 和 uid 查询对象
     * @param rid
     * @param uid
     * @return
     */
    Favorite findFavoriteByRidAndUid(int rid , int uid);

    /**
     * 根据rid查询收藏次数
     * @param rid
     * @return
     */
    int findCountFavoriteByRid(int rid);

    /**
     * 添加收藏信息
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);
}
