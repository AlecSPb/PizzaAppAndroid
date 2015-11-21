package com.pizzaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pizzaapp.model.Address;
import com.pizzaapp.model.Order;
import com.pizzaapp.ui.LineItemAdapter;
import com.pizzaapp.ui.MenuItemAdapter;

public class Summary extends AppCompatActivity {

    public Order myOrder;
    public LineItemAdapter lineItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        myOrder = (Order) getIntent().getExtras().get("order");

        ListView menuItemLV = (ListView) findViewById(R.id.summaryMenuItems);
        lineItemAdapter = new LineItemAdapter(this, null, myOrder.getLineItems());
        menuItemLV.setAdapter(lineItemAdapter);

        Button placeOrderButton = (Button) findViewById(R.id.summaryPlaceOrderButton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView addr = (TextView) findViewById(R.id.summaryAddr);
                TextView city = (TextView) findViewById(R.id.summaryCity);
                TextView state = (TextView) findViewById(R.id.summaryState);
                TextView zip = (TextView) findViewById(R.id.summaryZip);
                TextView country = (TextView) findViewById(R.id.summaryCountry);
                TextView phone = (TextView) findViewById(R.id.summaryPhone);

                Address address = new Address();
                address.setAddress1(addr.getText().toString());
                address.setCity(city.getText().toString());
                address.setLocality(state.getText().toString());
                address.setPostalCode(zip.getText().toString());
                address.setCountry(country.getText().toString());

                myOrder.setAddress(address);
                myOrder.setPhoneNumber(phone.getText().toString());
                myOrder.setIsDelivery(true);

                Intent intent = new Intent();
                intent.putExtra("finishedOrder", myOrder);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
