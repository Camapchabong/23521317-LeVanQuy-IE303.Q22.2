package shoestore.model;

import shoestore.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lớp truy xuất dữ liệu sản phẩm từ CSDL SQLite (bảng "products").
 * Trước đây dữ liệu được lưu cứng (hard-code) trong một List tĩnh,
 * nay được thay bằng các câu lệnh truy vấn SQL thông qua JDBC.
 */
public class ProductRepository {

    /**
     * Truy vấn toàn bộ sản phẩm trong CSDL.
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, brand, image_path FROM products;";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Không thể đọc danh sách sản phẩm từ CSDL: " + e.getMessage());
            e.printStackTrace();
        }

        return Collections.unmodifiableList(products);
    }

    /**
     * Truy vấn sản phẩm theo id.
     */
    public Product findById(int id) {
        String sql = "SELECT id, name, description, price, brand, image_path " +
                "FROM products WHERE id = ?;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn sản phẩm theo id: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Truy vấn sản phẩm theo tên (tìm gần đúng, không phân biệt hoa/thường).
     */
    public List<Product> findByName(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, brand, image_path " +
                "FROM products WHERE name LIKE ? COLLATE NOCASE;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn sản phẩm theo tên: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Truy vấn sản phẩm theo thương hiệu (brand).
     */
    public List<Product> findByBrand(String brand) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, brand, image_path " +
                "FROM products WHERE brand = ? COLLATE NOCASE;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, brand);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn sản phẩm theo thương hiệu: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("price"),
                rs.getString("brand"),
                rs.getString("image_path")
        );
    }
}
