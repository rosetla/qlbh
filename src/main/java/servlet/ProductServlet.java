package servlet;

import dao.LoaiDAO;
import dao.ProductDAO;
import model.Loai;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Kiểm tra xem có đang đăng nhập hay không
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.sendRedirect("AdminLoginServlet");
        } else {
        	request.setCharacterEncoding("utf-8");
        	response.setCharacterEncoding("utf-8");
        	String action = request.getParameter("action");

            if ("edit".equals(action)) {
                String mahang = request.getParameter("mahang");
                Product product = productDAO.getProductById(mahang);

                // Chuyển sản phẩm vào form để chỉnh sửa
                request.setAttribute("product", product);
                request.getRequestDispatcher("admin_edit_product.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                String mahang = request.getParameter("mahang");
                productDAO.deleteProduct(mahang);
            }

            // Lấy tất cả sản phẩm để hiển thị lại danh sách sau khi thêm/sửa/xóa
            List<Product> products = productDAO.getAllProducts();
            // Lấy danh sách loại từ DAO
            List<Loai> loaiList = LoaiDAO.getAllLoai();

            // Đưa danh sách loại vào request
            request.setAttribute("loaiList", loaiList);
            
            request.setAttribute("products", products);
            request.getRequestDispatcher("admin_products.jsp").forward(request, response);
            
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // Kiểm tra nếu form là multipart
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Tạo thư mục tạm để lưu file
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            factory.setRepository(tempDir);

            ServletFileUpload upload = new ServletFileUpload(factory);
            String uploadPath = getServletContext().getRealPath("/") + "images/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir(); // Tạo thư mục nếu chưa tồn tại

            Product product = new Product();

            try {
                // Lấy danh sách các field trong form
                List<FileItem> items = upload.parseRequest(request);

                for (FileItem item : items) {
                    if (item.isFormField()) {
                        // Xử lý các trường input không phải file
                        switch (item.getFieldName()) {
                            case "mahang":
                                product.setMahang(item.getString("UTF-8"));
                                break;
                            case "tenhang":
                                product.setTenhang(item.getString("UTF-8"));
                                break;
                            case "soluong":
                                product.setSoluong(Integer.parseInt(item.getString("UTF-8")));
                                break;
                            case "gia":
                                product.setGia(Long.parseLong(item.getString("UTF-8")));
                                break;
                            case "maloai":
                                product.setMaloai(item.getString("UTF-8"));
                                break;
                            case "tenloai":
                                product.setTenloai(item.getString("UTF-8"));
                                break;
                            case "mota":
                                product.setMota(item.getString("UTF-8"));
                                break;
                            case "hang":
                                product.setHang(item.getString("UTF-8"));
                                break;
                            case "ngaynhap":
                                product.setNgaynhap(java.sql.Date.valueOf(item.getString("UTF-8")));
                                break;
                        }
                    } else {
                        // Xử lý file upload
                        if (item.getFieldName().equals("anh") && !item.getName().isEmpty()) {
                            String fileName = new File(item.getName()).getName();
                            String filePath = uploadPath + fileName;
                            File storeFile = new File(filePath);

                            // Lưu file lên server
                            item.write(storeFile);

                            // Lưu đường dẫn file vào thuộc tính của sản phẩm
                            product.setAnh("images/" + fileName);
                        }
                    }
                }

                // Kiểm tra sản phẩm có tồn tại hay không
                Product existingProduct = productDAO.getProductById(product.getMahang());
                if (existingProduct != null) {
                    // Nếu sản phẩm đã tồn tại, gọi updateProduct()
                    productDAO.updateProduct(product);
                } else {
                    // Nếu sản phẩm chưa tồn tại, gọi saveProduct()
                    productDAO.saveProduct(product);
                }

                // Chuyển hướng về danh sách sản phẩm
                response.sendRedirect("ProductServlet");
            } catch (Exception ex) {
                ex.printStackTrace();
                response.getWriter().println("Lỗi khi upload file: " + ex.getMessage());
            }
        }
    }


}
