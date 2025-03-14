package dao;

import model.Loai;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiDAO {

    // Lấy danh sách tất cả các loại sản phẩm
    public static List<Loai> getAllLoai() {
        List<Loai> loaiList = new ArrayList<>();
        String sql = "SELECT * FROM Loai";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maloai = rs.getString("maloai");
                String tenloai = rs.getString("tenloai");
                loaiList.add(new Loai(maloai, tenloai));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loaiList;
    }

    // Lấy loại sản phẩm theo mã loại
    public Loai getLoaiById(String maloai) {
        String sql = "SELECT * FROM Loai WHERE maloai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maloai);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tenloai = rs.getString("tenloai");
                    return new Loai(maloai, tenloai);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
