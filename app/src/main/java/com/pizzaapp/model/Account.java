package com.pizzaapp.model;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {

    private String id;
    private String email;
    private String password;
    private int points;
    private String type;

    public Account(){
    }

    public String getId() {
        return id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getPoints(){
        return points;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
