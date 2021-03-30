package azmithabet.com.tam.model;

public class Category {
    private String id;
    private String name;
    private String image;
    private int hasProduct;

    public Category() {
    }

    public Category(String id, String name, String image, int hasProduct) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.hasProduct = hasProduct;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getHasProduct() {
        return hasProduct;
    }
}
