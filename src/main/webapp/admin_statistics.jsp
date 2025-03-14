<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê doanh thu</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Thống kê doanh thu theo tháng năm</h2>
    <hr>

    <!-- Hiển thị lỗi nếu có -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Hiển thị bảng thống kê -->
    <c:if test="${not empty revenueList}">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Năm</th>
                    <th>Tháng</th>
                    <th>Tổng Số Lượng Bán</th>
                    <th>Tổng Doanh Thu (VNĐ)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="revenue" items="${revenueList}">
                    <tr>
                        <td>${revenue.year}</td>
                        <td>${revenue.month}</td>
                        <td>${revenue.totalQuantity}</td>
                        <td>${revenue.totalRevenue}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Hiển thị nếu không có dữ liệu -->
    <c:if test="${empty revenueList}">
        <p class="text-center text-muted">Không có dữ liệu thống kê doanh thu.</p>
    </c:if>
</div>
</body>
</html>
