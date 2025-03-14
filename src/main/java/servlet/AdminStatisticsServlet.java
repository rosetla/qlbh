package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RevenueDAO;
import model.MonthlyRevenue;

/**
 * Servlet implementation class AdminStatisticsServlet
 */
@WebServlet("/AdminStatisticsServlet")
public class AdminStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RevenueDAO revenueDAO = new RevenueDAO();
        ArrayList<MonthlyRevenue> revenueList = revenueDAO.getMonthlyRevenue();

        if (revenueList == null || revenueList.isEmpty()) {
            request.setAttribute("error", "Không có dữ liệu thống kê doanh thu.");
        } else {
            request.setAttribute("revenueList", revenueList);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_statistics.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
