<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="customer_header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử mua hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Lịch sử mua hàng</h2>
    <hr>
    <c:if test="${not empty historyList}">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Mã Hóa Đơn</th>
                    <th>Ngày Mua</th>
                    <th>Sản Phẩm</th>
                    <th>Tổng Số Lượng</th>
                    <th>Tổng Tiền (VNĐ)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="history" items="${historyList}">
                    <tr>
                        <td>${history.maHoaDon}</td>
                        <td>${history.ngayMua}</td>
                        <td>${history.tenHangHoa}</td>
                        <td>${history.tongSoLuongMua}</td>
                        <td><fmt:formatNumber value="${history.tongTien}" type="currency" currencySymbol="₫" /></td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty historyList}">
        <p class="text-center text-muted">Bạn chưa có lịch sử mua hàng nào.</p>
    </c:if>
</div>
</body>
</html>
