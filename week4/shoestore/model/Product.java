package shoestore.model;

public class Product {

    private final int    id;
    private final String name;
    private final String description;
    private final String price;
    private final String brand;
    private final String imageFile;

    public Product(int id, String name, String description, String price, String brand, String imageFile) {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.brand       = brand;
        this.imageFile   = imageFile;
    }

    public int getId()             { return id; }
    public String getName()        { return name; }
    public String getDescription() { return description; }
    public String getPrice()       { return price; }
    public String getBrand()       { return brand; }
    public String getImageFile()   { return imageFile; }
}
