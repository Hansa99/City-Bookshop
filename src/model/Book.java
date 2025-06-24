package model;

import java.util.UUID;

public class Book {
    private String id;
    private String name;
    private double price;
    private String categories;

    public Book(String name, double price, String categories) {
        this.id = generateUniqueID();
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
