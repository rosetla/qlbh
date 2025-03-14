<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập Khách hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <style>
        /* Đảm bảo carousel chiếm toàn bộ màn hình */
        .carousel-item img {
            object-fit: cover;
            width: 100%;
            height: 100vh; /* Chiếm toàn bộ chiều cao màn hình */
        }
        /* Đặt form login đè lên carousel */
        .login-form {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 10; /* Đảm bảo form hiển thị trên ảnh */
            width: 100%;
            max-width: 400px;
        }
    </style>
</head>
<body>
	    <div id="carouselExampleDark" class="carousel carousel-dark slide">
	  <div class="carousel-indicators">
	    <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
	    <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
	    <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
	  </div>
	  <div class="carousel-inner">
	    <div class="carousel-item active" data-bs-interval="10000">
	      <img src="image/anhnen1.jpeg" class="d-block w-100" alt="...">
	      <div class="carousel-caption d-none d-md-block">
	        <h5 style="color:gray;">CỬA HÀNG AN BÁ TÚ KANG</h5>
	        <p style="color:gray;">CHÚNG TÔI TỰ HÀO LÀ NHÀ CUNG CẤP DỊCH VỤ MUA BÁN CHẤT LƯỢNG SỐ 1 TẠI VIỆT NAM</p>
	      </div>
	    </div>
	    <div class="carousel-item" data-bs-interval="2000">
	      <img src="image/anhnen3.jpg" class="d-block w-100" alt="...">
	      <div class="carousel-caption d-none d-md-block">
	        <h5 style="color:gray;">CỬA HÀNG AN BÁ TÚ KANG</h5>
	        <p style="color:gray;">DỊCH VỤ MUA BÁN TRỰC TUYẾN TRÊN SÀN THƯƠNG MẠI ĐIỆN TỬ LỚN NHẤT VIỆT NAM</p>
	      </div>
	    </div>
	    <div class="carousel-item">
	      <img src="image/anhnen4.jpg" class="d-block w-100" alt="...">
	      <div class="carousel-caption d-none d-md-block">
	        <h5 style="color:gray;">CỬA HÀNG AN BÁ TÚ KANG</h5>
	        <p style="color:gray;">TRUY CẬP NGAY SHOPANHBATOCOM.COM, TRAO NIỀM TIN, NHẬN TÀI LỘC</p>
	      </div>
	    </div>
	  </div>
	  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="visually-hidden">Previous</span>
	  </button>
	  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="visually-hidden">Next</span>
	  </button>
	</div>

    <!-- Form đăng nhập -->
    <div class="container login-form">
        <div class="card">
            <div class="card-header text-center mb-3">
                <h4 class="text-secondary">Đăng nhập khách hàng</h4>
            </div>
            <div class="card-body">
                <form action="CustomerLoginServlet" method="post">
                    <div class="mb-3">
                        <label for="tendn" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" id="tendn" name="tendn" required>
                    </div>
                    <div class="mb-3">
                        <label for="pass" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="pass" name="pass" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                </form>
                <div class="mt-3 text-center">
                    <a href="CustomerRegisterServlet">Chưa có tài khoản? Đăng ký ngay</a>
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
