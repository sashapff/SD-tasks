package utils;

public class User {
    private final String id;
    private final String currency;

    public User(String id, String currency) {
        this.id = id;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String toString() {
        return "User {" +
                "id=" + id +
                ", currency=" + currency +
                '}';
    }
}

