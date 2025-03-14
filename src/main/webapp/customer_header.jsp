<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ThongBao" %>
<%@ page import="dao.ThongBaoDAO" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><h3>SHOP ANH BA</h3></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto"> <!-- Thêm ms-auto để căn phải -->
                <!-- Liên kết đến danh sách sản phẩm -->
                <li class="nav-item">
                    <a class="nav-link" href="CustomerProductsServlet">Danh sách sản phẩm</a>
                </li>
                <!-- Liên kết đến giỏ hàng -->
                <li class="nav-item">
                    <a class="nav-link" href="CustomerCartServlet">Giỏ hàng</a>
                </li>
                <li class="nav-item">
				    <!-- Nút để mở modal -->
				    <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#notificationModal">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-bell-fill" viewBox="0 0 16 16">
						  <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2m.995-14.901a1 1 0 1 0-1.99 0A5 5 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901"/>
						</svg>
				    </a>
				</li>
				
				<!-- Modal hiển thị thông báo -->
				<div class="modal fade" id="notificationModal" tabindex="-1" aria-labelledby="notificationModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <h5 class="modal-title" id="notificationModalLabel" style="color:orange;">Thông báo</h5>
				                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				            </div>
				            <div class="modal-body">
				                <ul>
				                    <% 
				                        Customer customer = (Customer) session.getAttribute("customer");
				                        if (customer != null) {
				                            String maKhachHang = customer.getMakh();
				                            dao.ThongBaoDAO thongBaoDAO = new dao.ThongBaoDAO();
				                            List<model.ThongBao> thongBaoList = thongBaoDAO.getThongbao(maKhachHang);
				
				                            if (thongBaoList != null && !thongBaoList.isEmpty()) {
				                                for (model.ThongBao tb : thongBaoList) { 
				                    %>
				                                    <li>
				                                        <p>
				                                        	Đơn hàng <strong style="color:orange;"><%= tb.getTenhang() %></strong> của bạn đã được xác nhận
				                                        	và sẽ được giao cho đơn vị vận chuyển trong thời gian sớm nhất!
				                                        	
				                                        </p>
				                                    </li>
				                    <% 
				                                } 
				                            } else { 
				                    %>
				                                <p>Không có thông báo mới.</p>
				                    <% 
				                            }
				                        } else { 
				                    %>
				                        <p>Bạn cần đăng nhập để xem thông báo.</p>
				                    <% 
				                        } 
				                    %>
				                </ul>
				            </div>
				            <div class="modal-footer">
				                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
				            </div>
				        </div>
				    </div>
				</div>

                <!-- Hiển thị dropdown login/logout nếu khách hàng đã đăng nhập -->
                <% if (session.getAttribute("customer") != null) { %>
                    <!-- Hiển thị khi người dùng đã đăng nhập -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <%= ((Customer) session.getAttribute("customer")).getHoten() %> <!-- Hiển thị họ tên khách hàng -->
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a style="color:red;" class="dropdown-item" href="CustomerLogoutServlet">Log out</a></li>
                            <li><a class="dropdown-item" href="CustomerHistoryServlet">Lịch sử mua hàng</a></li>
                            <li><a class="dropdown-item" href="#">Thông tin cá nhân</a></li>
                        </ul>
                    </li>
                <% } else { %>
                    <!-- Hiển thị khi khách hàng chưa đăng nhập -->
                    <li class="nav-item">
                        <a class="nav-link" href="CustomerLoginServlet">
                            <span class="glyphicon glyphicon-log-in"></span> Đăng nhập
                        </a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
