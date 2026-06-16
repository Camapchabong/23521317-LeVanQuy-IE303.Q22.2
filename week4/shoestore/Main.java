package shoestore;

import shoestore.controller.ProductController;
import shoestore.database.DatabaseManager;
import shoestore.model.Product;
import shoestore.model.ProductRepository;
import shoestore.view.MainFrame;

import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1. Khởi tạo CSDL: tạo bảng "products" và chèn dữ liệu mẫu nếu CSDL đang trống.
        DatabaseManager.initializeDatabase();

        // 2. Demo truy vấn sản phẩm từ CSDL (in ra console để kiểm chứng yêu cầu BTTH4).
        demoQueries();

        // 3. Khởi chạy giao diện Swing, dữ liệu hiển thị được lấy trực tiếp từ CSDL.
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            ProductController controller = new ProductController(frame);
            controller.init();
            frame.setVisible(true);
        });
    }

    private static void demoQueries() {
        ProductRepository repository = new ProductRepository();

        System.out.println("\n=== Truy vấn toàn bộ sản phẩm trong CSDL ===");
        List<Product> all = repository.findAll();
        for (Product p : all) {
            System.out.printf("#%d %-25s %-10s %s%n", p.getId(), p.getName(), p.getPrice(), p.getBrand());
        }

        System.out.println("\n=== Truy vấn sản phẩm có tên chứa \"FORUM\" ===");
        for (Product p : repository.findByName("FORUM")) {
            System.out.printf("#%d %-25s %s%n", p.getId(), p.getName(), p.getPrice());
        }

        System.out.println("\n=== Truy vấn sản phẩm theo id = 1 ===");
        Product byId = repository.findById(1);
        if (byId != null) {
            System.out.printf("#%d %s - %s%n", byId.getId(), byId.getName(), byId.getPrice());
        }
        System.out.println();
    }
}
