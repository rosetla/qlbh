package servlet;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị trang đăng nhập (form đăng nhập) cho khách hàng
        request.getRequestDispatcher("customer_login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tendangnhap = request.getParameter("tendn");
        String matkhau = request.getParameter("pass");

        // Kiểm tra đăng nhập của khách hàng
        Customer customer = customerDAO.checkLogin(tendangnhap, matkhau);

        if (customer != null) {
            // Đăng nhập thành công, lưu khách hàng vào session
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            session.setAttribute("makh", customer.getMakh());  // Lưu mã khách hàng vào session

            response.sendRedirect("CustomerProductsServlet"); // Chuyển đến trang sản phẩm hoặc trang chủ
        } else {
        	// Đăng nhập thất bại, truyền thông báo lỗi vào request
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("customer_login.jsp").forward(request, response);
        }
    }
}
