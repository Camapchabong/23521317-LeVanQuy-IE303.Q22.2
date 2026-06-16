package shoestore.controller;

import shoestore.model.Product;
import shoestore.model.ProductRepository;
import shoestore.utils.ImageLoader;
import shoestore.view.MainFrame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private final ProductRepository repository;
    private final ImageLoader        imageLoader;
    private final MainFrame          mainFrame;

    private final List<Product>       products;
    private final List<BufferedImage> images;

    public ProductController(MainFrame mainFrame) {
        this.mainFrame   = mainFrame;
        this.repository  = new ProductRepository();
        this.imageLoader = new ImageLoader();

        products = new ArrayList<>(repository.findAll());

        images = new ArrayList<>();
        for (Product p : products) {
            images.add(imageLoader.load(p.getImageFile()));
        }
    }

    public void init() {
        mainFrame.getGridPanel().populate(products, images);

        mainFrame.getGridPanel().addSelectionListener(index ->
            onProductSelected(index, true)
        );

        onProductSelected(0, false);
    }

    private void onProductSelected(int index, boolean animate) {
        mainFrame.getDetailPanel().showProduct(products.get(index), images.get(index), animate);
    }
}
