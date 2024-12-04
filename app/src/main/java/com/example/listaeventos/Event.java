package com.example.listaeventos;
public class Event {
    private String name;
    private String description;
    private String location;
    private double price;
    private String date;

    public Event() {} // Constructor vacío para Firebase

    public Event(String name, String description, String location, double price, String date) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.price = price;
        this.date = date;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public String getDate() { return date; }
}
