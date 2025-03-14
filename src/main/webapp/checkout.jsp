<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="customer_header.jsp" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Xác nhận mua hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Xác nhận mua hàng</h2>
        <form action="OrderServlet" method="post">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Mã hàng</th>
                        <th>Tên hàng</th>
                        <th>Số lượng</th>
                        <th>Giá</th>
                        <th>Tổng tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        java.util.Map<String, model.CartItem> cart = 
                            (java.util.Map<String, model.CartItem>) request.getAttribute("cart");
                        long total = 0;
                        if (cart != null) {
                            for (model.CartItem item : cart.values()) {
                                long subtotal = item.getGia() * item.getSoluongmua();
                                total += subtotal;
                    %>
                    <tr>
                        <td><%= item.getMahang() %></td>
                        <td><%= item.getTenhang() %></td>
                        <td><%= item.getSoluongmua() %></td>
                        <td><%= NumberFormat.getInstance().format(item.getGia()) %></td>
                        <td><%= NumberFormat.getInstance().format(subtotal) %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
            <h4>Tổng cộng: <%= NumberFormat.getInstance().format(total) %> VND</h4>
            <div class="mt-3">
                <button type="submit" class="btn btn-success" name="action" value="checkout">Xác nhận</button>
                <a href="CustomerCartServlet?action=clear" class="btn btn-danger">Hủy đơn hàng</a>
            </div>
        </form>
    </div>
</body>
</html>
