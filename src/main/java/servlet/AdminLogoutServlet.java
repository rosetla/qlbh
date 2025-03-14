package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AdminLogoutServlet")
public class AdminLogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xóa session để đăng xuất
        HttpSession session = request.getSession();
        session.invalidate();
        
        // Chuyển hướng về trang đăng nhập
        response.sendRedirect("AdminLoginServlet");
    }
}
