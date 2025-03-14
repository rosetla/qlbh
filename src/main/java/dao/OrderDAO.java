package dao;

import model.CartItem;
import model.Invoice;
import model.OrderDetail;
import utils.DBConnection;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class OrderDAO {

	public boolean createOrder(Map<String, CartItem> cart, long makh) {
	    Connection conn = null;
	    PreparedStatement pstmtOrder = null;
	    PreparedStatement pstmtOrderDetail = null;
	    ResultSet rs = null;

	    try {
	        conn = DBConnection.getConnection();
	        conn.setAutoCommit(false); // Bắt đầu transaction

	        // Tạo hóa đơn mới
	        String insertOrderSQL = "INSERT INTO hoadon (makh, ngaymua, damua) OUTPUT INSERTED.mahoadon VALUES (?, GETDATE(), 0)";
	        pstmtOrder = conn.prepareStatement(insertOrderSQL);
	        pstmtOrder.setLong(1, makh);

	        rs = pstmtOrder.executeQuery();
	        long mahoadon = 0;
	        if (rs.next()) {
	            mahoadon = rs.getLong(1); // Lấy giá trị mahoadon được trả về
	        }

	        // Thêm chi tiết hóa đơn
	        String insertOrderDetailSQL = "INSERT INTO chitiethoadon (mahang, soluongmua, mahoadon) VALUES (?, ?, ?)";
	        pstmtOrderDetail = conn.prepareStatement(insertOrderDetailSQL);

	        for (CartItem item : cart.values()) {
	            pstmtOrderDetail.setString(1, item.getMahang());
	            pstmtOrderDetail.setInt(2, item.getSoluongmua());
	            pstmtOrderDetail.setLong(3, mahoadon);
	            pstmtOrderDetail.addBatch();
	        }

	        pstmtOrderDetail.executeBatch();
	        conn.commit(); // Commit transaction

	        return true; // Đơn hàng đã được tạo thành công

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback(); // Rollback nếu có lỗi
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        return false; // Đơn hàng không thành công
	    } finally {
	        DBConnection.closeResultSet(rs);
	        DBConnection.closePreparedStatement(pstmtOrder);
	        DBConnection.closePreparedStatement(pstmtOrderDetail);
	        DBConnection.closeConnection(conn);
	    }
	}

 // Lấy danh sách hóa đơn
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT mahoadon, makh, ngaymua, damua FROM hoadon";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setMahoadon(rs.getLong("mahoadon"));
                invoice.setMakh(rs.getLong("makh"));
                invoice.setNgaymua(rs.getTimestamp("ngaymua"));
                invoice.setDamua(rs.getBoolean("damua"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

 // Lấy chi tiết hóa đơn
    public List<OrderDetail> getOrderDetails(long mahoadon) {
        List<OrderDetail> details = new ArrayList<>();
        String query = "SELECT d.mahang, d.soluongmua, d.mahoadon " +
                       "FROM chitiethoadon d " +
                       "WHERE d.mahoadon = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, mahoadon);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setMahang(rs.getString("mahang"));
                detail.setSoluongmua(rs.getInt("soluongmua"));
                detail.setMahoadon(rs.getLong("mahoadon"));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
    public boolean createPendingOrder(Map<String, CartItem> cart, long makh) throws Exception {
        String insertOrderSQL = "INSERT INTO hoadon (makh, ngaymua, damua) VALUES (?, GETDATE(), 0)";
        String insertOrderDetailSQL = "INSERT INTO chitiethoadon (mahang, soluongmua, mahoadon) VALUES (?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection()) {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);
            
            // Tạo hóa đơn mới
            try (PreparedStatement stmt = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, makh);
                stmt.executeUpdate();
                
                // Lấy mã hóa đơn vừa tạo
                ResultSet rs = stmt.getGeneratedKeys();
                long mahoadon = 0;
                if (rs.next()) {
                    mahoadon = rs.getLong(1); // Mã hóa đơn mới
                }
                
                // Thêm chi tiết đơn hàng
                try (PreparedStatement detailStmt = connection.prepareStatement(insertOrderDetailSQL)) {
                    for (CartItem item : cart.values()) {
                        detailStmt.setString(1, item.getMahang());
                        detailStmt.setInt(2, item.getSoluongmua());
                        detailStmt.setLong(3, mahoadon);
                        detailStmt.addBatch();
                    }
                    detailStmt.executeBatch();
                }
                
                // Cam kết giao dịch
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Lỗi khi tạo đơn hàng: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi tạo đơn hàng: " + e.getMessage(), e);
        }
    }

    
    public boolean deleteOrder(long mahoadon) {
        String deleteOrderDetailsSQL = "DELETE FROM chitiethoadon WHERE mahoadon = ?";
        String deleteInvoiceSQL = "DELETE FROM hoadon WHERE mahoadon = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement detailStmt = conn.prepareStatement(deleteOrderDetailsSQL);
             PreparedStatement invoiceStmt = conn.prepareStatement(deleteInvoiceSQL)) {

            // Xóa chi tiết hóa đơn
            detailStmt.setLong(1, mahoadon);
            detailStmt.executeUpdate();

            // Xóa hóa đơn
            invoiceStmt.setLong(1, mahoadon);
            return invoiceStmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean confirmOrder(long mahoadon) {
        String sql = "UPDATE hoadon SET damua = 1 WHERE mahoadon = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, mahoadon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
