package controllers.toppage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.FollowList;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();


        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");


        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", login_employee)
                .setFirstResult(5 * (page - 1))
                .setMaxResults(5)
                .getResultList();


        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();





        int followReports_page;
        try{
            followReports_page = Integer.parseInt(request.getParameter("followReports_page"));
        }catch(Exception e){
            followReports_page = 1;
        }

        //フォローリストを取得
        List<FollowList> followList = em.createNamedQuery("getMyFollowList", FollowList.class)
                .setParameter("employee", login_employee)
                .getResultList();


        //フォローしている人の日報を格納するリストを作成
        List<Report> followReports = new ArrayList<Report>();


        //イテレータでフォローリストのFollow_idを順に取り出し。
        System.out.println("フォローしている人の日報を読み込みます。");
        Iterator<FollowList> fit = followList.iterator();


            while(fit.hasNext()){
                //取り出したFollow_idで社員情報を取得。
                List<Employee> employee = em.createNamedQuery("getOneEmployee", Employee.class)
                        .setParameter("follow_id", fit.next().getFollow_id())
                        .getResultList();


                //取得した社員情報で日報を取得し、followReportsリストに格納。
                followReports.addAll(
                        em.createNamedQuery("getMyAllReports", Report.class)
                        .setParameter("employee", employee)
                        .getResultList()
                        );
                System.out.println("現在のフォローリポートサイズ：" + followReports.size());
            }



        Collections.sort(
                followReports,
                new Comparator<Report>(){
                    @Override
                    public int compare(Report obj1, Report obj2){
                        return obj2.getUpdated_at().compareTo(obj1.getUpdated_at());
                    }
                }
                );


        long followReports_count = followReports.size();


        //フォローしている人の日報のページネーション
        int x = followReports_page;
        if(x != 1){
            followReports.subList(0, 5 + 5 * (x - 2)).clear();
            if(followReports.size() > 6){
                followReports.subList(5, followReports.size()).clear();
            }
        }else if(x == 1){
            followReports.subList(5, followReports.size()).clear();
        }


        em.close();


        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("followReports", followReports);
        request.setAttribute("followReports_page", followReports_page);
        request.setAttribute("followReports_count", followReports_count);



        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }


        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
