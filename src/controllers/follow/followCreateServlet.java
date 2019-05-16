package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.FollowList;
import utils.DBUtil;

/**
 * Servlet implementation class followCreateServlet
 */
@WebServlet("/follow/create")
public class followCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public followCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em =DBUtil.createEntityManager();


            FollowList f = new FollowList();

            f.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            f.setFollow_code((String)request.getParameter("ec"));
            f.setFollow_id(Integer.parseInt(request.getParameter("ei")));
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            f.setFollow_date(currentTime);

            //TODO 重複フォローに対する対策が必要
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();


            request.getSession().setAttribute("flush", "フォローが完了しました。");
            response.sendRedirect(request.getContextPath() + "/employees/index");
            }
        }
    }
