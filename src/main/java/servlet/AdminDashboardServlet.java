package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	// Kiểm tra xem có đang đăng nhập hay không
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("admin") == null) {
                // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
                response.sendRedirect("AdminLoginServlet");
            } else {
                // Nếu đã đăng nhập, forward đến trang Admin Dashboard
                request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý đăng xuất
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // Hủy phiên làm việc
        }
        // Chuyển hướng về trang đăng nhập
        response.sendRedirect("AdminLoginServlet");
    }
}
