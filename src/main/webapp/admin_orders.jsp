<%@ page import="java.util.List" %>
<%@ page import="model.Invoice" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Danh sách đơn hàng</h2>

        <!-- Hiển thị thông báo thành công hoặc lỗi -->
        <%
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
            if (message != null) {
        %>
            <div class="alert alert-success"><%= message %></div>
        <%
            }
            if (error != null) {
        %>
            <div class="alert alert-danger"><%= error %></div>
        <%
            }
        %>

        <!-- Bảng danh sách đơn hàng -->
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mã hóa đơn</th>
                    <th>Mã khách hàng</th>
                    <th>Ngày mua</th>
                    <th>Trạng thái</th>
                    <th>Chi tiết hóa đơn</th> <!-- Cột Chi tiết hóa đơn -->
                    <th>Xác nhận đơn hàng</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Invoice> invoices = (List<Invoice>) request.getAttribute("invoices");
                    if (invoices != null && !invoices.isEmpty()) {
                        for (Invoice invoice : invoices) {
                %>
                <tr>
                    <td><%= invoice.getMahoadon() %></td>
                    <td><%= invoice.getMakh() %></td>
                    <td><%= invoice.getNgaymua() %></td>
                    <td><%= invoice.isDamua() ? "Đã mua" : "Chưa mua" %></td>
                    <td>
                        <!-- Link xem chi tiết hóa đơn -->
                        <a href="OrderServlet?action=viewDetails&mahoadon=<%= invoice.getMahoadon() %>" class="btn btn-info">Xem chi tiết</a>
                    </td>
                    <td>
                        <% if (!invoice.isDamua()) { %>
                        <a href="OrderServlet?action=confirm&mahoadon=<%= invoice.getMahoadon() %>" class="btn btn-success">Xác nhận</a>
                        <% } %>
                        <a href="OrderServlet?action=delete&mahoadon=<%= invoice.getMahoadon() %>" class="btn btn-danger">Hủy</a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center">Không có đơn hàng nào.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
