package com.pizzaapp.model;

import java.io.Serializable;
import java.util.UUID;


public class LineItem implements Serializable {

    private String id;
    private int quantity;
    private String itemId;
    private MenuItem item;

    public LineItem() {
        id = UUID.randomUUID().toString();
    }

    public LineItem(int quantity, String itemId, MenuItem item){
        this.quantity = quantity;
        this.itemId = itemId;
        this.item = item;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public MenuItem getItem() {
        return item;
    }
    public void setItem(MenuItem item) {
        this.item = item;
    }

}
