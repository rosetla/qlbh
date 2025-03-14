<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Admin" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="AdminDashboardServlet"><h3>Hệ thống bán hàng</h3></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto"> <!-- Lớp me-auto để căn trái các item còn lại -->
                <li class="nav-item">
                    <a class="nav-link" href="ProductServlet">Quản lý sản phẩm</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="OrderServlet">Quản lý đơn hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="HistoryServlet">Lịch sử mua hàng khách hàng</a>
                </li>
            </ul>
            <ul class="navbar-nav ms-auto"> <!-- Lớp ms-auto để căn phải các item login và dropdown -->
                <% if (session.getAttribute("admin") != null) { %>
                    <!-- Hiển thị khi người dùng đã đăng nhập -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <%= ((Admin) session.getAttribute("admin")).getTendangnhap() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a style="color:red;" class="dropdown-item" href="AdminLogoutServlet">Log out</a></li>
                            <li><a class="dropdown-item" href="CustomerProfileServlet">Thông tin cá nhân</a></li>
                        </ul>
                    </li>
                <% } else { %>
                    <!-- Hiển thị khi người dùng chưa đăng nhập -->
                    <li class="nav-item">
                        <a class="nav-link" href="AdminLoginServlet">
                            <span class="glyphicon glyphicon-log-in"></span> Login
                        </a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<!-- Bootstrap JS (cần cho dropdown và các tính năng tương tác khác) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>