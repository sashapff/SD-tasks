package sashapff.utils;

public class StockReport {
    int id;
    int price;
    int number;

    public StockReport(int id, int price, int number) {
        this.id = id;
        this.price = price;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }
}
