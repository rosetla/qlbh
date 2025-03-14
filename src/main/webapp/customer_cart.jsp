<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.CartItem" %>
<%@ page import="java.util.Map" %>
<%@ include file="customer_header.jsp" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <div class="container mt-4">
        <h4>GIỎ HÀNG</h4>
        <%
            // Lấy danh sách giỏ hàng từ session
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            long total = 0;
            if (cart != null && !cart.isEmpty()) {
        %>
        <form action="CustomerCartServlet" method="post">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Select</th>
                        <th>Mã hàng</th>
                        <th>Tên hàng</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Tổng tiền</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (CartItem item : cart.values()) {
                            long subtotal = item.getGia() * item.getSoluongmua();
                            total += subtotal;
                    %>
                    <tr>
                        <td>
                            <input type="checkbox" name="selectedItems" value="<%= item.getMahang() %>">
                        </td>
                        <td><%= item.getMahang() %></td>
                        <td><%= item.getTenhang() %></td>
                        <td><%= NumberFormat.getInstance().format(item.getGia()) %> đ</td>
                        <td>
                            <input type="number" name="quantity_<%= item.getMahang() %>" 
                                   value="<%= item.getSoluongmua() %>" min="1" class="form-control">
                        </td>
                        <td><%= NumberFormat.getInstance().format(subtotal) %> đ</td>
                        <td>
                            <a href="CustomerCartServlet?action=delete&mahang=<%= item.getMahang() %>" 
                               class="btn btn-danger btn-sm">Xóa</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <h4>Tổng cộng: <%= NumberFormat.getInstance().format(total) %> VND</h4>
            <div class="mt-3">
                <!-- Hai nút submit với action khác nhau -->
                <button type="submit" name="action" value="update" class="btn btn-primary">Cập nhật giỏ hàng</button>
                <a href="CustomerCartServlet?action=clear" class="btn btn-danger">Xóa tất cả</a>
                <button type="submit" name="action" value="checkout" class="btn btn-success">Xác nhận mua hàng</button>
                <a href="CustomerProductsServlet" class="btn btn-warning">Tiếp tục mua hàng</a>
            </div>
        </form>
        <%
            } else {
        %>
        <div class="text-center mt-4">
            <h5 class="text-muted">Giỏ hàng của bạn đang trống!</h5>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>
