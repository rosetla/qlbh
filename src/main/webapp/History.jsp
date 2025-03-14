<%@ page import="java.util.List" %>
<%@ page import="model.History" %>
<%@ include file="header.jsp" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử mua hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2 class="mb-4">Lịch sử mua hàng</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mã khách hàng</th>
                    <th>Tên khách hàng</th>
                    <th>Mã hóa đơn</th>
                    <th>Ngày mua</th>
                    <th>Sản phẩm</th>
                    <th>Tổng số lượng</th>
                    <th>Tổng tiền</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<History> historyList = (List<History>) request.getAttribute("historyList");
                    if (historyList != null && !historyList.isEmpty()) {
                        for (History history : historyList) {
                %>
                <tr>
                    <td><%= history.getMaKhachHang() %></td>
                    <td><%= history.getTenKhachHang() %></td>
                    <td><%= history.getMaHoaDon() %></td>
                    <td><%= history.getNgayMua() %></td>
                    <td><%= history.getTenHangHoa() %></td>
                    <td><%= history.getTongSoLuongMua() %></td>
                    <td><%= NumberFormat.getInstance().format(history.getTongTien()) %> VND</td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" class="text-center">Không có lịch sử mua hàng.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
