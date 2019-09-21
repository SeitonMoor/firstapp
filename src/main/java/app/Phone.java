package app;

public class Phone {
    private int price;
    private int amount = 0;
    private String name;
    private String color;

    Phone(int price, String name, String color) {
        this.price = price;
        this.name = name;
        this.color = color;
    }

    int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    void addAmount(int amount) {
        this.amount = this.amount + amount;
    }

    void delAmount(int amount) {
        this.amount = this.amount - amount;
    }
}
