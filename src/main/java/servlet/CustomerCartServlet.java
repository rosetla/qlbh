package servlet;

import model.CartItem;
import model.Product;
import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CustomerCartServlet")
public class CustomerCartServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if ("add".equals(action)) {
            String mahang = request.getParameter("mahang");
            Product product = productDAO.getProductById(mahang);

            if (product != null) {
                // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                if (cart.containsKey(mahang)) {
                    // Tăng số lượng sản phẩm nếu đã có trong giỏ hàng
                    CartItem item = cart.get(mahang);
                    item.setSoluongmua(item.getSoluongmua() + 1);
                } else {
                    // Thêm sản phẩm mới vào giỏ hàng
                    CartItem newItem = new CartItem(product.getMahang(), product.getTenhang(), product.getGia(), 1);
                    cart.put(mahang, newItem);
                }
            }
            // Sau khi thêm sản phẩm, chuyển hướng lại trang giỏ hàng
            response.sendRedirect("CustomerCartServlet");
        } else if ("delete".equals(action)) {
            String mahang = request.getParameter("mahang");
            cart.remove(mahang);
            response.sendRedirect("CustomerCartServlet");

        } else if ("clear".equals(action)) {
            cart.clear();
            response.sendRedirect("CustomerCartServlet");

        } else if ("checkout".equals(action)) {
            // Nếu checkout được gọi qua GET (có thể do liên kết cũ) chuyển hướng về giỏ hàng
            response.sendRedirect("CustomerCartServlet");
        } else {
            // Forward to the cart JSP to display cart items
            request.getRequestDispatcher("/customer_cart.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        // Cập nhật số lượng sản phẩm trong giỏ hàng
        for (String mahang : cart.keySet()) {
            String quantityParam = "quantity_" + mahang;
            String quantityStr = request.getParameter(quantityParam);
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        cart.get(mahang).setSoluongmua(quantity);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity for product " + mahang);
                }
            }
        }

        String action = request.getParameter("action");
        if ("checkout".equals(action)) {
            // Lấy các mặt hàng được chọn (checkbox)
            String[] selectedItems = request.getParameterValues("selectedItems");
            if (selectedItems != null && selectedItems.length > 0) {
                Map<String, CartItem> checkoutCart = new HashMap<>();
                for (String mahang : selectedItems) {
                    CartItem item = cart.get(mahang);
                    if (item != null) {
                        checkoutCart.put(mahang, item);
                    }
                }
                // Xóa các mặt hàng đã chọn khỏi giỏ hàng
                for (String mahang : checkoutCart.keySet()){
                    cart.remove(mahang);
                }
                // Lưu thông tin các mặt hàng chọn vào session với tên "checkoutCart"
                session.setAttribute("checkoutCart", checkoutCart);
                response.sendRedirect("CheckoutServlet");
                return;
            } else {
                // Nếu không có mặt hàng nào được chọn, quay lại giỏ hàng (có thể thêm thông báo lỗi)
                response.sendRedirect("CustomerCartServlet");
                return;
            }
        } else { // action = update
            response.sendRedirect("CustomerCartServlet");
        }
    }
}
