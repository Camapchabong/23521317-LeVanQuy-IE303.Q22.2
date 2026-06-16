package shoestore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Quản lý việc kết nối và khởi tạo cơ sở dữ liệu SQLite cho ứng dụng.
 *
 * - Cơ sở dữ liệu được lưu trong file "products.db" (tự động tạo khi chạy lần đầu).
 * - Bảng "products" lưu thông tin sản phẩm: name, description, price, brand, image_path.
 * - Nếu bảng chưa có dữ liệu, sẽ tự động chèn (seed) các sản phẩm mẫu.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:products.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy SQLite JDBC Driver trong classpath!");
            e.printStackTrace();
        }
    }

    /**
     * Mở một kết nối tới CSDL SQLite.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Khởi tạo CSDL: tạo bảng "products" nếu chưa tồn tại,
     * và chèn dữ liệu mẫu nếu bảng đang trống.
     */
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "price TEXT NOT NULL, " +
                "brand TEXT, " +
                "image_path TEXT NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);

            String countSQL = "SELECT COUNT(*) FROM products;";
            try (ResultSet rs = stmt.executeQuery(countSQL)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("CSDL trống. Đang chèn dữ liệu sản phẩm mẫu...");
                    seedDefaultProducts(conn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Khởi tạo CSDL thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chèn dữ liệu sản phẩm mẫu ban đầu.
     */
    private static void seedDefaultProducts(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO products (name, description, price, brand, image_path) " +
                "VALUES (?, ?, ?, ?, ?);";

        String[][] data = {
            {"4DFWD PULSE SHOES", "This product is excluded from all promotional discounts and offers.",
                    "$160.00", "Adidas", "shoestore/resources/images/img3.png"},
            {"FORUM MID SHOES", "This product is excluded from all promotional discounts and offers.",
                    "$100.00", "Adidas", "shoestore/resources/images/img4.png"},
            {"SUPERNOVA SHOES", "NMD City Stock 2",
                    "$150.00", "Adidas", "shoestore/resources/images/img5.png"},
            {"Adidas NMD", "NMD City Stock 2",
                    "$160.00", "Adidas", "shoestore/resources/images/img6.png"},
            {"Adidas 4DFWD Black", "NMD City Stock 2",
                    "$120.00", "Adidas", "shoestore/resources/images/img1.png"},
            {"4DFWD PULSE RED", "This product is excluded from all promotional discounts and offers.",
                    "$160.00", "Adidas", "shoestore/resources/images/img2.png"},
            {"4DFWD PULSE GREEN", "This product is excluded from all promotional discounts and offers.",
                    "$160.00", "Adidas", "shoestore/resources/images/img3.png"},
            {"FORUM MID SHOES", "This product is excluded from all promotional discounts and offers.",
                    "$100.00", "Adidas", "shoestore/resources/images/img4.png"}
        };

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            for (String[] row : data) {
                pstmt.setString(1, row[0]);
                pstmt.setString(2, row[1]);
                pstmt.setString(3, row[2]);
                pstmt.setString(4, row[3]);
                pstmt.setString(5, row[4]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Đã chèn " + data.length + " sản phẩm mẫu vào CSDL.");
        }
    }
}
