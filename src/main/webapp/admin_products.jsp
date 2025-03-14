<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Loai" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Quản lý sản phẩm</h2>

        <!-- Form thêm/sửa sản phẩm -->
        <form action="ProductServlet" method="post" class="mb-4" enctype="multipart/form-data">
            <input type="hidden" name="action" value="save">
            <div class="mb-3">
                <label for="mahang" class="form-label">Mã hàng:</label>
                <input type="text" class="form-control" id="mahang" name="mahang" required>
            </div>
            <div class="mb-3">
                <label for="tenhang" class="form-label">Tên hàng:</label>
                <input type="text" class="form-control" id="tenhang" name="tenhang" required>
            </div>
            <div class="mb-3">
                <label for="soluong" class="form-label">Số lượng:</label>
                <input type="number" class="form-control" id="soluong" name="soluong" required>
            </div>
            <div class="mb-3">
                <label for="gia" class="form-label">Giá:</label>
                <input type="number" class="form-control" id="gia" name="gia" required>
            </div>
			<div class="mb-3">
			    <label for="maloai" class="form-label">Mã loại:</label>
			    <input list="maloai-options" class="form-control" id="maloai" name="maloai" required>
			    <datalist id="maloai-options">
			        <% 
			            List<Loai> loaiList = (List<Loai>) request.getAttribute("loaiList"); 
			            if (loaiList != null) {
			                for (Loai loai : loaiList) {
			        %>
			        <option value="<%= loai.getMaloai() %>"><%= loai.getTenloai() %></option>
			        <% 
			                }
			            }
			        %>
			    </datalist>
			</div>
            <div class="mb-3">
		        <label for="tenloai" class="form-label">Tên loại</label>
		        <input type="text" class="form-control" id="tenloai" name="tenloai">
		    </div>
            <div class="mb-3">
                <label for="mota" class="form-label">Mô tả:</label>
                <textarea class="form-control" id="mota" name="mota" required></textarea>
            </div>
            <div class="mb-3">
		        <label for="anh" class="form-label">Ảnh sản phẩm:</label>
		        <input type="file" class="form-control" id="anh" name="anh" required>
		    </div>
            <div class="mb-3">
                <label for="hang" class="form-label">Hãng:</label>
                <input type="text" class="form-control" id="hang" name="hang" required>
            </div>
            <div class="mb-3">
                <label for="ngaynhap" class="form-label">Ngày nhập:</label>
                <input type="date" class="form-control" id="ngaynhap" name="ngaynhap" required>
            </div>
            <button type="submit" class="btn btn-primary">Lưu</button>
        </form>

        <!-- Danh sách sản phẩm -->
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mã hàng</th>
                    <th>Tên hàng</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Mã loại</th>
                    <th>Mô tả</th>
                    <th>Ảnh</th>
                    <th>Hãng</th>
                    <th>Ngày nhập</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Lấy danh sách sản phẩm từ request
                    java.util.List<model.Product> products = (java.util.List<model.Product>) request.getAttribute("products");
                    if (products != null) {
                        for (model.Product product : products) {
                %>
                <tr>
                    <td><%= product.getMahang() %></td>
                    <td><%= product.getTenhang() %></td>
                    <td><%= product.getSoluong() %></td>
                    <td><%= product.getGia() %></td>
                    <td><%= product.getMaloai() %></td>
                    <td><%= product.getMota() %></td>
                    <td>
                    	<img src="<%= request.getContextPath() + "/" + product.getAnh() %>" alt="<%= product.getTenhang() %>" width="100" height="100">
                    </td>
                    <td><%= product.getHang() %></td>
                    <td><%= product.getNgaynhap() %></td>
                    <td>
                        <a href="ProductServlet?action=edit&mahang=<%= product.getMahang() %>" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="ProductServlet?action=delete&mahang=<%= product.getMahang() %>" class="btn btn-danger btn-sm">Xóa</a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
