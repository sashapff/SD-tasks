package utils;

public class Product {
    private final String id;
    private final int price;
    private final String currency;

    public Product(String id, int price, String currency) {
        this.id = id;
        this.price = price;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String toString() {
        return "Product {" +
                "id=" + id +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
