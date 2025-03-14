<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký Khách hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
        <div class="card" style="width: 100%; max-width: 400px;">
            <div class="card-header text-center">
                <h4>Đăng ký Khách hàng</h4>
            </div>
            <div class="card-body">
                <form action="CustomerRegisterServlet" method="post">
                    <div class="mb-3">
                        <label for="hoten" class="form-label">Họ tên</label>
                        <input type="text" class="form-control" id="hoten" name="hoten" required>
                    </div>
                    <div class="mb-3">
                        <label for="diachi" class="form-label">Địa chỉ</label>
                        <input type="text" class="form-control" id="diachi" name="diachi" >
                    </div>
                    <div class="mb-3">
                        <label for="sodt" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="sodt" name="sodt" >
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email">
                    </div>
                    <div class="mb-3">
                        <label for="tendn" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" id="tendn" name="tendn" required>
                    </div>
                    <div class="mb-3">
                        <label for="pass" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="pass" name="pass" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmpass" class="form-label">Xác nhận mật khẩu</label>
                        <input type="password" class="form-control" id="confirmpass" name="confirmpass" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
                </form>
                <div class="mt-3 text-center">
                    <a href="CustomerLoginServlet">Đã có tài khoản? Đăng nhập</a>
                </div>
            </div>
            <!-- Hiển thị thông báo lỗi nếu có -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger mt-3">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>
