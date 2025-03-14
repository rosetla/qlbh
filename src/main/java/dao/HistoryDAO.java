package dao;

import model.History;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    // Phương thức lấy tất cả lịch sử mua hàng
    public static List<History> getAllHistory() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT " +
                "kh.makh AS MaKhachHang, " +
                "kh.hoten AS TenKhachHang, " +
                "hd.mahoadon AS MaHoaDon, " +
                "hd.ngaymua AS NgayMua, " +
                "STRING_AGG(hh.tenhang, ', ') AS TenHangHoa, " +  // Tên các hàng hóa trong hóa đơn
                "SUM(cthd.soluongmua) AS TongSoLuongMua, " +    // Tổng số lượng mua
                "SUM(cthd.soluongmua * hh.gia) AS TongTien " +   // Tổng tiền của hóa đơn
                "FROM hoadon hd " +
                "INNER JOIN chitiethoadon cthd ON hd.mahoadon = cthd.mahoadon " +
                "INNER JOIN khachhang kh ON hd.makh = kh.makh " +
                "INNER JOIN hanghoa hh ON cthd.mahang = hh.mahang " +
                "WHERE hd.damua = 1 " +                          // Chỉ tính những hóa đơn đã được mua
                "GROUP BY " +
                "kh.makh, " +
                "kh.hoten, " +
                "hd.mahoadon, " +
                "hd.ngaymua;";

        // Sử dụng try-with-resources để tự động đóng kết nối và statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                History history = new History();
                history.setMaKhachHang(resultSet.getString("MaKhachHang"));
                history.setTenKhachHang(resultSet.getString("TenKhachHang"));
                history.setMaHoaDon(resultSet.getString("MaHoaDon"));
                history.setNgayMua(resultSet.getDate("NgayMua"));
                history.setTenHangHoa(resultSet.getString("TenHangHoa"));
                history.setTongSoLuongMua(resultSet.getInt("TongSoLuongMua"));
                history.setTongTien(resultSet.getDouble("TongTien"));

                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }
    public List<History> getCustomerHistory(String maKhachHang) {
        List<History> historyList = new ArrayList<>();

        String sql = "SELECT " +
                     "kh.makh AS MaKhachHang, " +
                     "kh.hoten AS TenKhachHang, " +
                     "hd.mahoadon AS MaHoaDon, " +
                     "hd.ngaymua AS NgayMua, " +
                     "STRING_AGG(hh.tenhang, ', ') AS TenHangHoa, " + 
                     "SUM(cthd.soluongmua) AS TongSoLuongMua, " + 
                     "SUM(cthd.soluongmua * hh.gia) AS TongTien " + 
                     "FROM hoadon hd " +
                     "INNER JOIN chitiethoadon cthd ON hd.mahoadon = cthd.mahoadon " +
                     "INNER JOIN khachhang kh ON hd.makh = kh.makh " +
                     "INNER JOIN hanghoa hh ON cthd.mahang = hh.mahang " +
                     "WHERE hd.damua = 1 AND kh.makh = ? " + 
                     "GROUP BY " +
                     "kh.makh, " +
                     "kh.hoten, " +
                     "hd.mahoadon, " +
                     "hd.ngaymua;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKhachHang); // Gán giá trị cho tham số makh

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    History history = new History();
                    history.setMaKhachHang(rs.getString("MaKhachHang"));
                    history.setTenKhachHang(rs.getString("TenKhachHang"));
                    history.setMaHoaDon(rs.getString("MaHoaDon"));
                    history.setNgayMua(rs.getDate("NgayMua"));
                    history.setTenHangHoa(rs.getString("TenHangHoa"));
                    history.setTongSoLuongMua(rs.getInt("TongSoLuongMua"));
                    history.setTongTien(rs.getDouble("TongTien"));

                    historyList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }
    
}
