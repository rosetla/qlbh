package servlet;

import dao.LoaiDAO;
import dao.ProductDAO;
import model.Loai;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

@WebServlet("/CustomerProductsServlet")
public class CustomerProductsServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("utf-8");
    	
    	String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        //String maloai = request.getParameter("maloai");
        String[] maloaiArray = request.getParameterValues("maloai"); // Lấy danh sách các loại đã chọn

        List<Product> products;
        List<Loai> loaiList;

        LoaiDAO loaiDAO = new LoaiDAO();
        loaiList = loaiDAO.getAllLoai(); // Lấy danh sách loại sản phẩm
        request.setAttribute("loaiList", loaiList);
        
        // Nếu có từ khóa tìm kiếm
        if (search != null && !search.isEmpty()) {
            products = productDAO.searchProductsByName(search);
        } else if (maloaiArray != null && maloaiArray.length > 0) {
            // Lọc sản phẩm theo nhiều loại
            products = productDAO.getProductsByMultipleLoai(maloaiArray);
        } else {
            products = productDAO.getAllProducts();
        }
		/*
		 * // Nếu có từ khóa tìm kiếm if (search != null && !search.isEmpty()) {
		 * products = productDAO.searchProductsByName(search); } else if (maloai != null
		 * && !maloai.isEmpty()) { // Nếu có yêu cầu lọc theo loại sản phẩm products =
		 * productDAO.getProductsByLoai(maloai); } else { // Lấy tất cả sản phẩm nếu
		 * không có tìm kiếm hoặc lọc products = productDAO.getAllProducts(); }
		 */
        // Nếu có yêu cầu sắp xếp
        if (sort != null) {
            if ("asc".equalsIgnoreCase(sort)) {
                products.sort(Comparator.comparingLong(Product::getGia)); // Sắp xếp tăng dần theo giá
            } else if ("desc".equalsIgnoreCase(sort)) {
                products.sort(Comparator.comparingLong(Product::getGia).reversed()); // Sắp xếp giảm dần theo giá
            }
        }

        // Đưa danh sách sản phẩm vào request để JSP có thể sử dụng
        request.setAttribute("products", products != null ? products : new ArrayList<>());

        // Chuyển hướng đến trang JSP để hiển thị
        request.getRequestDispatcher("customer_products.jsp").forward(request, response);
    }
}
