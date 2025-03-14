package dao;

import model.Product;
import utils.DBConnection;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductDAO {
    
    // Phương thức lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM hanghoa";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setMahang(rs.getString("mahang"));
                product.setTenhang(rs.getString("tenhang"));
                product.setSoluong(rs.getInt("soluong"));
                product.setGia(rs.getLong("gia"));
                product.setMaloai(rs.getString("maloai"));
                product.setMota(rs.getString("mota"));
                product.setAnh(rs.getString("anh"));
                product.setHang(rs.getString("hang"));
                product.setNgaynhap(rs.getDate("ngaynhap"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Phương thức lấy sản phẩm theo mã hàng
    public Product getProductById(String mahang) {
        String query = "SELECT * FROM hanghoa WHERE mahang = ?";
        Product product = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mahang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setMahang(rs.getString("mahang"));
                    product.setTenhang(rs.getString("tenhang"));
                    product.setSoluong(rs.getInt("soluong"));
                    product.setGia(rs.getLong("gia"));
                    product.setMaloai(rs.getString("maloai"));
                    product.setMota(rs.getString("mota"));
                    product.setAnh(rs.getString("anh"));
                    product.setHang(rs.getString("hang"));
                    product.setNgaynhap(rs.getDate("ngaynhap"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    // Phương thức cập nhật sản phẩm
    public void updateProduct(Product product) {
        String sql = "UPDATE hanghoa SET tenhang = ?, soluong = ?, gia = ?, maloai = ?, mota = ?, anh = ?, hang = ?, ngaynhap = ? WHERE mahang = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getTenhang());
            pstmt.setLong(2, product.getSoluong());
            pstmt.setLong(3, product.getGia());
            pstmt.setString(4, product.getMaloai());
            pstmt.setString(5, product.getMota());
            pstmt.setString(6, product.getAnh());
            pstmt.setString(7, product.getHang());
            pstmt.setDate(8, (Date) product.getNgaynhap());
            pstmt.setString(9, product.getMahang());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveProduct(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();

            // Kiểm tra xem maloai đã tồn tại chưa trong bảng loai
            String checkCategorySql = "SELECT COUNT(*) FROM loai WHERE maloai = ?";
            stmt = conn.prepareStatement(checkCategorySql);
            stmt.setString(1, product.getMaloai());
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                // Nếu maloai chưa tồn tại, thêm mới vào bảng loai
                String insertCategorySql = "INSERT INTO loai (maloai, tenloai) VALUES (?, ?)";
                stmt = conn.prepareStatement(insertCategorySql);
                stmt.setString(1, product.getMaloai());
                stmt.setString(2, product.getTenloai()); // Sử dụng tenloai từ đối tượng product
                stmt.executeUpdate();
            }

            // Lưu sản phẩm vào bảng hanghoa
            String sql = "INSERT INTO hanghoa (mahang, tenhang, soluong, gia, maloai, mota, anh, hang, ngaynhap) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getMahang());
            stmt.setString(2, product.getTenhang());
            stmt.setLong(3, product.getSoluong());
            stmt.setLong(4, product.getGia());
            stmt.setString(5, product.getMaloai());
            stmt.setString(6, product.getMota());
            stmt.setString(7, product.getAnh());
            stmt.setString(8, product.getHang());
            stmt.setDate(9, (Date) product.getNgaynhap());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức xóa sản phẩm
    public void deleteProduct(String mahang) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM hanghoa WHERE mahang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, mahang);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // Trong ProductDAO
    public List<Product> searchProductsByNameADMIN(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM hanghoa WHERE tenhang LIKE ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setMahang(resultSet.getString("mahang"));
                product.setTenhang(resultSet.getString("tenhang"));
                product.setSoluong(resultSet.getInt("soluong"));
                product.setGia(resultSet.getLong("gia"));
                product.setMaloai(resultSet.getString("maloai"));
                product.setTenloai(resultSet.getString("tenloai"));
                product.setMota(resultSet.getString("mota"));
                product.setHang(resultSet.getString("hang"));
                product.setNgaynhap(resultSet.getDate("ngaynhap"));
                product.setAnh(resultSet.getString("anh"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM hanghoa WHERE tenhang LIKE ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setMahang(rs.getString("mahang"));
                    product.setTenhang(rs.getString("tenhang"));
                    product.setSoluong(rs.getInt("soluong"));
                    product.setGia(rs.getLong("gia"));
                    product.setMaloai(rs.getString("maloai"));
                    product.setMota(rs.getString("mota"));
                    product.setHang(rs.getString("hang"));
                    product.setNgaynhap(rs.getDate("ngaynhap"));
                    product.setAnh(rs.getString("anh"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product> getProductsByLoai(String maloai) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM hanghoa WHERE maloai = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, maloai);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setMahang(resultSet.getString("mahang"));
                    product.setTenhang(resultSet.getString("tenhang"));
                    product.setSoluong(resultSet.getInt("soluong"));
                    product.setGia(resultSet.getLong("gia"));
                    product.setMaloai(resultSet.getString("maloai"));
                    product.setMota(resultSet.getString("mota"));
                    product.setHang(resultSet.getString("hang"));
                    product.setNgaynhap(resultSet.getDate("ngaynhap"));
                    product.setAnh(resultSet.getString("anh"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<Product> getProductsByMultipleLoai(String[] maloaiArray) {
        List<Product> products = new ArrayList<>();
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < maloaiArray.length; i++) {
            placeholders.append("?");
            if (i < maloaiArray.length - 1) {
                placeholders.append(",");
            }
        }

        String query = "SELECT * FROM hanghoa WHERE maloai IN (" + placeholders + ")";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < maloaiArray.length; i++) {
                statement.setString(i + 1, maloaiArray[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	Product product = new Product();
                    product.setMahang(resultSet.getString("mahang"));
                    product.setTenhang(resultSet.getString("tenhang"));
                    product.setSoluong(resultSet.getInt("soluong"));
                    product.setGia(resultSet.getLong("gia"));
                    product.setMaloai(resultSet.getString("maloai"));
                    product.setMota(resultSet.getString("mota"));
                    product.setHang(resultSet.getString("hang"));
                    product.setNgaynhap(resultSet.getDate("ngaynhap"));
                    product.setAnh(resultSet.getString("anh"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public static String formatPrice(long price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(price);
    }

    public static void main(String[] args) {
        long gia = 13400000L;
        System.out.println("Giá: " + formatPrice(gia));
    }
}
