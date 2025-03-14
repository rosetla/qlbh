package servlet;

import dao.AdminDAO;
import model.Admin;
import nl.captcha.Captcha;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị trang đăng nhập (form đăng nhập)
        request.getRequestDispatcher("admin_login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tendangnhap = request.getParameter("tendangnhap");
        String matkhau = request.getParameter("matkhau");
        String tb = null;

        // Số lần thử đăng nhập
        int dem = 0;
        if (session.getAttribute("dem") != null) {
            dem = (int) session.getAttribute("dem");
        }

        // Xử lý CAPTCHA nếu số lần thử >= 3
        if (dem >= 3) {
            Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
            String answer = request.getParameter("answer");

            if (captcha == null || !captcha.isCorrect(answer)) {
                tb = "CAPTCHA không đúng. Vui lòng thử lại.";
                request.setAttribute("error", tb);
                request.getRequestDispatcher("admin_login.jsp").forward(request, response);
                return;
            }
        }

        // Kiểm tra đăng nhập của Admin
        Admin admin = adminDAO.checkLogin(tendangnhap, matkhau);

        if (admin != null && admin.getQuyen()) {
            // Đăng nhập thành công, reset số lần thử và chuyển đến dashboard
            session.setAttribute("admin", admin);
            session.removeAttribute("dem"); // Xóa số lần đăng nhập thất bại
            response.sendRedirect("AdminDashboardServlet");
        } else {
            // Đăng nhập thất bại
            tb = "Tên đăng nhập hoặc mật khẩu không đúng!";
            dem++;
            session.setAttribute("dem", dem); // Tăng số lần thất bại
            request.setAttribute("error", tb);
            request.getRequestDispatcher("admin_login.jsp").forward(request, response);
        }
    }
}
