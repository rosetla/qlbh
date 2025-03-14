<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chỉnh sửa sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Chỉnh sửa sản phẩm</h2>

        <form action="ProductServlet" method="post" class="mb-4" enctype="multipart/form-data">
            <input type="hidden" name="edit" value="true"> <!-- Dấu hiệu chỉnh sửa sản phẩm -->
            <div class="mb-3">
                <label for="mahang" class="form-label">Mã hàng:</label>
                <input type="text" class="form-control" id="mahang" name="mahang" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getMahang() : "" %>" readonly required>
            </div>
            <div class="mb-3">
                <label for="tenhang" class="form-label">Tên hàng:</label>
                <input type="text" class="form-control" id="tenhang" name="tenhang" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getTenhang() : "" %>" required>
            </div>
            <div class="mb-3">
                <label for="soluong" class="form-label">Số lượng:</label>
                <input type="number" class="form-control" id="soluong" name="soluong" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getSoluong() : 0 %>" required>
            </div>
            <div class="mb-3">
                <label for="gia" class="form-label">Giá:</label>
                <input type="number" class="form-control" id="gia" name="gia" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getGia() : 0 %>" required>
            </div>
            <div class="mb-3">
                <label for="maloai" class="form-label">Mã loại:</label>
                <input type="text" class="form-control" id="maloai" name="maloai" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getMaloai() : "" %>" readonly required>
            </div>
            <div class="mb-3">
                <label for="mota" class="form-label">Mô tả:</label>
                <textarea class="form-control" id="mota" name="mota" required><%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getMota() : "" %></textarea>
            </div>
            <div class="mb-3">
			    <label for="anh" class="form-label">Ảnh sản phẩm:</label>
			    <input type="file" class="form-control" id="anh" name="anh" required>
			    <% 
			        String currentImage = ((model.Product) request.getAttribute("product")).getAnh();
			        if (currentImage != null && !currentImage.isEmpty()) {
			    %>
			        <img src="<%= currentImage %>" alt="Ảnh sản phẩm hiện tại" width="100" />
			    <% } %>
			</div>

            <div class="mb-3">
                <label for="hang" class="form-label">Hãng:</label>
                <input type="text" class="form-control" id="hang" name="hang" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getHang() : "" %>" required>
            </div>
            <div class="mb-3">
                <label for="ngaynhap" class="form-label">Ngày nhập:</label>
                <input type="date" class="form-control" id="ngaynhap" name="ngaynhap" value="<%= request.getAttribute("product") != null ? ((model.Product) request.getAttribute("product")).getNgaynhap().toString() : "" %>" required>
            </div>
            <button type="submit" class="btn btn-primary">Lưu</button>
        </form>
    </div>
</body>
</html>
