package dao;

import model.MonthlyRevenue;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class RevenueDAO {

    // Phương thức lấy danh sách doanh thu theo tháng
    public ArrayList<MonthlyRevenue> getMonthlyRevenue() {
        ArrayList<MonthlyRevenue> revenueList = new ArrayList<>();

        String sql = "SELECT * FROM doanhthu_theothang";

        try (Connection conn = DBConnection.getConnection(); // Kết nối DB
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int year = rs.getInt("Nam");
                int month = rs.getInt("Thang");
                int totalQuantity = rs.getInt("TongSoLuongBan");
                long totalRevenue = rs.getLong("TongDoanhThu");

                MonthlyRevenue revenue = new MonthlyRevenue(year, month, totalQuantity, totalRevenue);
                revenueList.add(revenue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return revenueList;
    }
}
