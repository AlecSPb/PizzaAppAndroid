package com.pizzaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pizzaapp.model.Order;

public class Pay extends AppCompatActivity {

    public Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        order = (Order) getIntent().getExtras().get("order");
    }
}
