package shoestore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository {

    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        PRODUCTS.add(new Product("4DFWD PULSE SHOES",
                "This product is excluded from all promotional discounts and offers.",
                "$160.00", "Adidas", "shoestore/resources/images/img3.png"));

        PRODUCTS.add(new Product("FORUM MID SHOES",
                "This product is excluded from all promotional discounts and offers.",
                "$100.00", "Adidas", "shoestore/resources/images/img4.png"));

        PRODUCTS.add(new Product("SUPERNOVA SHOES",
                "NMD City Stock 2",
                "$150.00", "Adidas", "shoestore/resources/images/img5.png"));

        PRODUCTS.add(new Product("Adidas NMD",
                "NMD City Stock 2",
                "$160.00", "Adidas", "shoestore/resources/images/img6.png"));

        PRODUCTS.add(new Product("Adidas 4DFWD Black",
                "NMD City Stock 2",
                "$120.00", "Adidas", "shoestore/resources/images/img1.png"));

        PRODUCTS.add(new Product("4DFWD PULSE RED",
                "This product is excluded from all promotional discounts and offers.",
                "$160.00", "Adidas", "shoestore/resources/images/img2.png"));

        PRODUCTS.add(new Product("4DFWD PULSE GREEN",
                "This product is excluded from all promotional discounts and offers.",
                "$160.00", "Adidas", "shoestore/resources/images/img3.png"));

        PRODUCTS.add(new Product("FORUM MID SHOES",
                "This product is excluded from all promotional discounts and offers.",
                "$100.00", "Adidas", "shoestore/resources/images/img4.png"));
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(PRODUCTS);
    }
}