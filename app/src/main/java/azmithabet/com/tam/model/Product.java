package azmithabet.com.tam.model;

public class Product {
    private String id;
    private String name;
    private String price;
    private String currency;
    private String shop_name;
    private String image;
    private String rate;
    private String cat_id;
    private int isWishList;

    public Product() {
    }

    public Product(String id, String name, String price, String currency, String shopName, String image, String rate, String categoryID, int isWishList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.shop_name = shopName;
        this.image = image;
        this.rate = rate;
        this.cat_id = categoryID;
        this.isWishList = isWishList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getShopName() {
        return shop_name;
    }

    public String getImage() {
        return image;
    }

    public String getRate() {
        return rate;
    }

    public String getCategoryID() {
        return cat_id;
    }

    public int getIsWishList() {
        return isWishList;
    }
}
