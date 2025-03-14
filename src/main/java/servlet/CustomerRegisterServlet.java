package servlet;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/CustomerRegisterServlet")
public class CustomerRegisterServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị trang đăng ký
        request.getRequestDispatcher("CustomerRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("utf-8");
    	String hoten = request.getParameter("hoten");
        String diachi = request.getParameter("diachi");
        String sodt = request.getParameter("sodt");
        String email = request.getParameter("email");
        String tendn = request.getParameter("tendn");
        String pass = request.getParameter("pass");
        String confirmpass = request.getParameter("confirmpass");

        // Kiểm tra mật khẩu và xác nhận mật khẩu có trùng khớp không
        if (!pass.equals(confirmpass)) {
            request.setAttribute("errorMessage", "Mật khẩu và xác nhận mật khẩu không trùng khớp.");
            request.getRequestDispatcher("CustomerRegister.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (customerDAO.checkUsernameExists(tendn)) {
            request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại.");
            request.getRequestDispatcher("CustomerRegister.jsp").forward(request, response);
            return;
        }

        // Tạo đối tượng Customer và lưu vào cơ sở dữ liệu
        Customer customer = new Customer();
        customer.setHoten(hoten);
        customer.setDiachi(diachi);
        customer.setSodt(sodt);
        customer.setEmail(email);
        customer.setTendn(tendn);
        customer.setPass(pass);

        boolean isRegistered = customerDAO.registerCustomer(customer);

        if (isRegistered) {
            // Đăng ký thành công, chuyển hướng tới trang đăng nhập
            response.sendRedirect("CustomerLoginServlet");
        } else {
            // Đăng ký thất bại
            request.setAttribute("errorMessage", "Đăng ký không thành công.");
            request.getRequestDispatcher("CustomerRegister.jsp").forward(request, response);
        }
    }
}
