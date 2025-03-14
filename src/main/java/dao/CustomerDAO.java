package dao;

import model.Customer;
import utils.DBConnection;

import java.sql.*;

public class CustomerDAO {
    public Customer checkLogin(String tendn, String pass) {
        Customer customer = null;
        String sql = "SELECT * FROM khachhang WHERE tendn = ? AND pass = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tendn);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setMakh(rs.getString("makh"));
                customer.setHoten(rs.getString("hoten"));
                customer.setDiachi(rs.getString("diachi"));
                customer.setSodt(rs.getString("sodt"));
                customer.setEmail(rs.getString("email"));
                customer.setTendn(rs.getString("tendn"));
                customer.setPass(rs.getString("pass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    public String getCustomerName(long makh) {
        // Thực hiện truy vấn DB để lấy tên khách hàng bằng makh
        String sql = "SELECT hoten FROM Customer WHERE makh = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
               stmt.setLong(1, makh);
               ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("hoten");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Khách hàng không tồn tại";
    }
 // Kiểm tra tên đăng nhập có tồn tại trong cơ sở dữ liệu hay không
    public boolean checkUsernameExists(String tendn) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM khachhang WHERE tendn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, tendn);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng ký khách hàng mới
    public boolean registerCustomer(Customer customer) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO khachhang (hoten, diachi, sodt, email, tendn, pass) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getHoten());
                stmt.setString(2, customer.getDiachi());
                stmt.setString(3, customer.getSodt());
                stmt.setString(4, customer.getEmail());
                stmt.setString(5, customer.getTendn());
                stmt.setString(6, customer.getPass());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
