<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="icon" href="image/ambatukam.jpg" type="image/x-icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Chào mừng Admin!</h2>
        <p>Đây là trang quản lý hệ thống.</p>

        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Quản lý Sản phẩm</h5>
                        <p class="card-text">Quản lý tất cả các sản phẩm trong hệ thống.</p>
                        <a href="ProductServlet" class="btn btn-primary">Quản lý sản phẩm</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Quản lý Đơn hàng</h5>
                        <p class="card-text">Xem và xác nhận đơn hàng của khách hàng.</p>
                        <a href="OrderServlet" class="btn btn-primary">Quản lý đơn hàng</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Thống kê Doanh thu</h5>
                        <p class="card-text">Xem thống kê về doanh thu từ các đơn hàng.</p>
                        <a href="AdminStatisticsServlet" class="btn btn-primary">Xem thống kê</a>
                    </div>
                </div>
            </div>
        </div>

        <br>
        <form method="post" action="AdminDashboardServlet">
        	<button type="submit" class="btn btn-danger"><i></i> Đăng Xuất</button>
        </form>
    </div>
</body>
</html>
