package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.HistoryDAO;
import model.History;

/**
 * Servlet implementation class CustomerHistoryServlet
 */
@WebServlet("/CustomerHistoryServlet")
public class CustomerHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Lấy thông tin khách hàng từ session
	        HttpSession session = request.getSession();
	        String maKhachHang = (String) session.getAttribute("makh");

	        if (maKhachHang == null) {
	            // Nếu khách hàng chưa đăng nhập, chuyển hướng đến trang đăng nhập
	            response.sendRedirect("CustomerLoginServlet");
	            return;
	        }

	        // Lấy lịch sử mua hàng từ DAO
	        HistoryDAO historyDAO = new HistoryDAO();
	        List<History> historyList = historyDAO.getCustomerHistory(maKhachHang);

	        // Đưa danh sách lịch sử vào request để chuyển sang JSP
	        request.setAttribute("historyList", historyList);

	        // Chuyển tiếp sang trang lịch sử mua hàng
	        request.getRequestDispatcher("customer_history.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
