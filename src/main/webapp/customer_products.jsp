<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.Loai" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="customer_header.jsp" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        /* Đảm bảo rằng tất cả các hình ảnh có kích cỡ đồng đều */
        .card-img-top {
            height: 300px;
            object-fit: fill; /* Giữ tỉ lệ của ảnh và cắt đi nếu cần thiết */
        }
        /* Làm cho card dài hơn */
        .card {
            height: 500px; /* Chiều cao của card */
        }
        /* Tạo khoảng cách giữa các sản phẩm và làm cho giao diện đẹp hơn */
        .card-body {
            padding: 1rem;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h2>Danh sách sản phẩm</h2>
        <!-- Form tìm kiếm -->
        <form action="CustomerProductsServlet" method="get" class="mb-4">
            <div class="input-group">
                <input type="text" name="search" class="form-control" placeholder="Tìm kiếm sản phẩm..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" />
                <button type="submit" class="btn btn-primary">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
					  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
					</svg>
                </button>
            </div>
        </form>
		<!-- Phân loại sản phẩm -->
		<form action="CustomerProductsServlet" method="get" class="mb-4">
		    <div class="row">
		        <% 
		            List<Loai> loaiList = (List<Loai>) request.getAttribute("loaiList");
		            String[] selectedLoai = request.getParameterValues("maloai"); // Lấy các giá trị đã chọn
		            if (loaiList != null && !loaiList.isEmpty()) { 
		                for (int i = 0; i < loaiList.size(); i++) {
		                    Loai loai = loaiList.get(i);
		                    boolean isChecked = false;
		
		                    // Kiểm tra xem loại hiện tại có trong danh sách đã chọn không
		                    if (selectedLoai != null) {
		                        for (String maloai : selectedLoai) {
		                            if (loai.getMaloai().equals(maloai)) {
		                                isChecked = true;
		                                break;
		                            }
		                        }
		                    }
		        %>
		        <div class="col-md-3">
		            <div class="form-check">
		                <input class="form-check-input" type="checkbox" name="maloai" value="<%= loai.getMaloai() %>" 
		                    id="loai_<%= i %>" <%= isChecked ? "checked" : "" %>>
		                <label class="form-check-label" for="loai_<%= i %>">
		                    <%= loai.getTenloai() %>
		                </label>
		            </div>
		        </div>
		        <% 
		                }
		            } else { 
		        %>
		        <p>Không có loại sản phẩm nào để hiển thị.</p>
		        <% 
		            }
		        %>
		    </div>
		    <button type="submit" class="btn btn-success mt-3">Lọc</button>
		</form>


		
        <!-- Dropdown sắp xếp -->
		<form action="CustomerProductsServlet" method="get" class="mb-4">
		    <input type="hidden" name="search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" />
		    <div class="dropdown">
		        <button class="btn btn-secondary dropdown-toggle" type="button" id="sortDropdown" data-bs-toggle="dropdown" aria-expanded="false">Sắp xếp
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-filter-circle" viewBox="0 0 16 16">
					  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
					  <path d="M7 11.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5m-2-3a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5m-2-3a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5"/>
					</svg>
		        </button>
		        <ul class="dropdown-menu" aria-labelledby="sortDropdown">
		            <li>
		                <button type="submit" name="sort" value="asc" class="dropdown-item">Giá tăng</button>
		            </li>
		            <li>
		                <button type="submit" name="sort" value="desc" class="dropdown-item">Giá giảm</button>
		            </li>
		        </ul>
		    </div>
		</form>

        
        <div class="row">
            <% 
                // Lấy danh sách sản phẩm từ request
                List<model.Product> products = (List<model.Product>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (model.Product product : products) {
            %>
            <div class="col-md-4">
			    <div class="card mb-4">
			        <img src="<%= product.getAnh() %>" class="card-img-top" alt="<%= product.getTenhang() %>">
			        <div class="card-body d-flex justify-content-between align-items-center">
			            <div>
			                <h5 class="card-title"><%= product.getTenhang() %></h5>
			                <p class="card-text">Số lượng còn: <%= product.getSoluong() %></p>
			                <p class="card-text">
							    <span style="font-weight: bold; color: #ff5722;">
							        Giá: <%= NumberFormat.getInstance().format(product.getGia()) %> VNĐ
							    </span>
							</p>
			                <a href="CustomerCartServlet?action=add&mahang=<%= product.getMahang() %>" class="btn btn-primary">Thêm vào giỏ</a>
			            </div>
			            <img src="https://cdn-icons-png.flaticon.com/512/2038/2038917.png" class="ms-3" alt="Icon" style="width: 50px; height: 50px;">
			        </div>
			    </div>
			</div>
            <% 
                    }
                } else {
            %>
            <p>Không có sản phẩm nào để hiển thị.</p>
            <% 
                }
            %>
        </div>
    </div>
</body>
</html>
