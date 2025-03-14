package servlet;

import dao.OrderDAO;
import model.CartItem;
import model.Invoice;
import model.OrderDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.sendRedirect("AdminLoginServlet");
            return;
        }
        
        String action = request.getParameter("action");
        if ("viewDetails".equals(action)) {
            // Xem chi tiết đơn hàng
            long mahoadon = Long.parseLong(request.getParameter("mahoadon"));
            List<OrderDetail> details = orderDAO.getOrderDetails(mahoadon);
            request.setAttribute("orderDetails", details);
            request.getRequestDispatcher("admin_order_details.jsp").forward(request, response);
        } else if ("confirm".equals(action)) {
            // Xác nhận đơn hàng
            long mahoadon = Long.parseLong(request.getParameter("mahoadon"));
            boolean success = orderDAO.confirmOrder(mahoadon);

            if (success) {
                // Sau khi xác nhận, lấy chi tiết đơn hàng và quay lại trang danh sách
                List<OrderDetail> details = orderDAO.getOrderDetails(mahoadon);
                request.setAttribute("orderDetails", details);
                request.setAttribute("message", "Đơn hàng đã được xác nhận!");
            } else {
                request.setAttribute("error", "Xác nhận đơn hàng thất bại!");
            }

            // Lấy danh sách tất cả hóa đơn và hiển thị
            List<Invoice> invoices = orderDAO.getAllInvoices();
            request.setAttribute("invoices", invoices);
            request.getRequestDispatcher("admin_orders.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            // Hủy đơn hàng
            long mahoadon = Long.parseLong(request.getParameter("mahoadon"));
            boolean success = orderDAO.deleteOrder(mahoadon);

            if (success) {
                request.setAttribute("message", "Đơn hàng đã bị hủy!");
            } else {
                request.setAttribute("error", "Hủy đơn hàng thất bại!");
            }

            List<Invoice> invoices = orderDAO.getAllInvoices();
            request.setAttribute("invoices", invoices);
            request.getRequestDispatcher("admin_orders.jsp").forward(request, response);
        } else {
            // Mặc định: Hiển thị danh sách đơn hàng
            List<Invoice> invoices = orderDAO.getAllInvoices();
            request.setAttribute("invoices", invoices);
            request.getRequestDispatcher("admin_orders.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("checkout".equals(action)) {
            // Lấy giỏ hàng thanh toán từ session (chỉ chứa những mặt hàng được chọn)
            HttpSession session = request.getSession();
            Map<String, CartItem> checkoutCart = (Map<String, CartItem>) session.getAttribute("checkoutCart");

            if (checkoutCart != null && !checkoutCart.isEmpty()) {
                // Lấy thông tin khách hàng từ session
                String makhString = (String) session.getAttribute("makh");
                long makh = 0;
                if (makhString != null) {
                    try {
                        makh = Long.parseLong(makhString);
                    } catch (NumberFormatException e) {
                        response.sendRedirect("CustomerLoginServlet");
                        return;
                    }
                } else {
                    response.sendRedirect("CustomerLoginServlet");
                    return;
                }

                // Tạo đơn hàng chờ xác nhận dựa trên checkoutCart
                boolean isOrderCreated = false;
                try {
                    isOrderCreated = orderDAO.createPendingOrder(checkoutCart, makh);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isOrderCreated) {
                    // Sau khi tạo đơn hàng thành công, xóa giỏ hàng thanh toán khỏi session
                    session.removeAttribute("checkoutCart");
                    response.sendRedirect("OrderConfirmationServlet");
                } else {
                    response.getWriter().write("Tạo đơn hàng thất bại!");
                }
            } else {
                response.sendRedirect("customer_cart.jsp?error=emptyCart");
            }
        }
        // Các hành động khác có thể được xử lý ở đây
    }
}
