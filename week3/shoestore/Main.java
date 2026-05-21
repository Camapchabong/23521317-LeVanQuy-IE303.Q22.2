package shoestore;

import shoestore.controller.ProductController;
import shoestore.view.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            ProductController controller = new ProductController(frame);
            controller.init();
            frame.setVisible(true);
        });
    }
}
