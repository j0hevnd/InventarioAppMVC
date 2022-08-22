package mintic.model;

public class Product {
    private Long code;
    private String name;
    private float price;
    private int stock;

    public Product() { }

    private Product(Long code, String name, float price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product createProduct(String name, float price, int stock) {
        return new Product(null, name, price, stock);
    }
    public static Product createProduct(Long code, String name, float price, int stock) {
        return new Product(code, name, price, stock);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    }
