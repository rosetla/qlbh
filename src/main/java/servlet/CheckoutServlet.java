package servlet;

import model.CartItem;
import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Kiểm tra nếu khách hàng đã đăng nhập (kiểm tra makh trong session)
        if (session.getAttribute("makh") == null) {
            response.sendRedirect("CustomerLoginServlet"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }
        // Lấy các sản phẩm được chọn để thanh toán
        @SuppressWarnings("unchecked")
        Map<String, CartItem> checkoutCart = (Map<String, CartItem>) session.getAttribute("checkoutCart");

        if (checkoutCart == null || checkoutCart.isEmpty()) {
            response.sendRedirect("CustomerCartServlet"); // Nếu không có sản phẩm nào được chọn, quay lại giỏ hàng
            return;
        }

        // Tính tổng tiền của các sản phẩm được chọn
        long total = 0;
        for (CartItem item : checkoutCart.values()) {
            total += item.getGia() * item.getSoluongmua();
        }

        // Lưu thông tin giỏ hàng thanh toán và tổng tiền vào request để hiển thị trong JSP
        request.setAttribute("cart", checkoutCart);
        request.setAttribute("total", total);

        // Forward đến trang checkout.jsp để hiển thị
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("checkout".equals(action)) {
            // Xử lý thanh toán và xác nhận đơn hàng
            HttpSession session = request.getSession();
            
            // Kiểm tra nếu khách hàng đã đăng nhập
            if (session.getAttribute("makh") != null) {
                @SuppressWarnings("unchecked")
                Map<String, CartItem> checkoutCart = (Map<String, CartItem>) session.getAttribute("checkoutCart");

                if (checkoutCart != null && !checkoutCart.isEmpty()) {
                    // Giả sử bạn thực hiện lưu đơn hàng vào cơ sở dữ liệu ở đây
                    // Sau khi thanh toán, xóa thông tin checkoutCart khỏi session
                    session.removeAttribute("checkoutCart");

                    // Thực hiện các hành động khác như lưu đơn hàng vào cơ sở dữ liệu, gửi email, v.v.
                    // Chuyển hướng đến trang xác nhận đơn hàng
                    response.sendRedirect("OrderConfirmationServlet");
                } else {
                    response.sendRedirect("CustomerCartServlet"); // Nếu giỏ hàng thanh toán rỗng, quay lại giỏ hàng
                }
            } else {
                response.sendRedirect("CustomerLoginServlet"); // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            }
        } else {
            // Nếu không phải hành động checkout, chuyển hướng về giỏ hàng
            response.sendRedirect("CustomerCartServlet");
        }
    }
}
