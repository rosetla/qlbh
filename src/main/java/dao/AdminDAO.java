package dao;

import model.Admin;
import utils.DBConnection;

import java.sql.*;

public class AdminDAO {
    public Admin checkLogin(String tendangnhap, String matkhau) {
        Admin admin = null;
        String sql = "SELECT * FROM dangnhap WHERE tendangnhap = ? AND matkhau = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tendangnhap);
            stmt.setString(2, matkhau);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setTendangnhap(rs.getString("tendangnhap"));
                admin.setMatkhau(rs.getString("matkhau"));
                admin.setQuyen(rs.getBoolean("quyen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
    public boolean confirmOrder(long mahoadon) {
        String sql = "UPDATE invoices SET damua = TRUE WHERE mahoadon = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, mahoadon);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
