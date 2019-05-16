package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.FollowList;
import utils.DBUtil;

/**
 * Servlet implementation class followDestroyServlet
 */
@WebServlet("/follow/destroy")
public class followDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public followDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            Integer fid = Integer.parseInt((String)request.getParameter("fid"));
            FollowList f = em.find(FollowList.class, fid);

            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "フォローを解除しました。");



            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }

}
