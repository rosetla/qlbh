<%@ page import="java.util.List" %>
<%@ page import="model.OrderDetail" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Chi tiết đơn hàng</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mã sản phẩm</th>
                    <th>Số lượng mua</th>
                    <th>Mã hóa đơn </th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetails");
                    if (orderDetails != null && !orderDetails.isEmpty()) {
                        for (OrderDetail detail : orderDetails) {
                %>
                <tr>
                    <td><%= detail.getMahang() %></td>
                    <td><%= detail.getSoluongmua() %></td>
                    <td><%= detail.getMahoadon() %></td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="2">Không có chi tiết đơn hàng nào để hiển thị.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
        <a href="OrderServlet" class="btn btn-secondary">Quay lại</a>
    </div>
</body>
</html>
