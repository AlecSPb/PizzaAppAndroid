package com.pizzaapp.model;

/**
 * Created by Ryan on 11/21/2015.
 */
import java.io.Serializable;
import java.util.UUID;

public class PaymentTransaction implements Serializable {

    private String id;
    private String timestamp;
    private double amount;
    private String paymentType;
    private CreditCard card;


    public PaymentTransaction(){
        this.id = UUID.randomUUID().toString();
        this.timestamp = "" + System.currentTimeMillis() / 1000L;
    }

    public PaymentTransaction(String timestamp, double amount, String paymentType){
        this.id = UUID.randomUUID().toString();
        this.timestamp =  timestamp;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public PaymentTransaction(double amount, CreditCard card){
        this.id = UUID.randomUUID().toString();
        this.timestamp = "" + System.currentTimeMillis() / 1000L;
        this.amount = amount;
        this.card = card;
    }

    public CreditCard getCard(){
        return card;
    }

    public void setCard(CreditCard card){
        this.card = card;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getPaymentType(){
        return paymentType;
    }

    public void setPaymentType(String paymentType){
        this.paymentType = paymentType;
    }
}