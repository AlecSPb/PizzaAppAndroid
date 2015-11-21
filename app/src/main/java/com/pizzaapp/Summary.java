package com.pizzaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pizzaapp.model.Order;

public class Summary extends AppCompatActivity {

    public Order myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        myOrder = (Order) getIntent().getExtras().get("order");
    }
}
