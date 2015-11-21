package com.pizzaapp.model;

import java.io.Serializable;
import java.util.UUID;

public class MenuItem implements Serializable {

    private String id;
    private String name;
    private boolean inStock = true;
    private double price;
    private boolean isSpecial = false;
    private String size;
    private MenuItemStatus status = MenuItemStatus.OPEN;

    //default constructor
    public MenuItem() {
        this.id = UUID.randomUUID().toString();
    }
    //Constructor
    public MenuItem(String name, boolean inStock,
                    double price, boolean isSpecial, String size) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.inStock = inStock;
        this.price = price;
        this.isSpecial = isSpecial;
        this.size = size;
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

    public boolean getInStock() {
        return inStock;
    }
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsSpecial() {
        return isSpecial;
    }
    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }

    public MenuItemStatus getStatus(){
        return status;
    }

    public void setStatus(MenuItemStatus status){
        this.status = status;
    }
}
