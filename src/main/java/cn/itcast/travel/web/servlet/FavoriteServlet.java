package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {


    /**
     * 列出我的收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void listMyFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO

    }


    /**
     * 列出收藏排行榜
     */
    public void listRankFavorite(){
        //TODO
    }
}
