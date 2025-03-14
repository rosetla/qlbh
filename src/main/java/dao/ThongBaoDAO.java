package dao;

import model.ThongBao;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThongBaoDAO {
    public List<ThongBao> getThongbao(String maKhachHang) {
        List<ThongBao> thongbaoList = new ArrayList<>();

        String sql = "SELECT hanghoa.tenhang, hanghoa.mahang " +
                     "FROM dbo.khachhang " +
                     "INNER JOIN dbo.hoadon ON khachhang.makh = hoadon.makh " +
                     "INNER JOIN dbo.chitiethoadon ON hoadon.mahoadon = chitiethoadon.mahoadon " +
                     "INNER JOIN dbo.hanghoa ON chitiethoadon.mahang = hanghoa.mahang " +
                     "WHERE hoadon.damua = 1 AND khachhang.makh = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKhachHang);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ThongBao thongbao = new ThongBao();
                    thongbao.setMahang(rs.getString("mahang"));
                    thongbao.setTenhang(rs.getString("tenhang"));
                    thongbaoList.add(thongbao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thongbaoList;
    }
}
