package com.pizzaapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pizzaapp.model.LineItem;
import com.pizzaapp.model.MenuItemStatus;
import com.pizzaapp.ui.LineItemAdapter;
import com.pizzaapp.ui.MenuItemAdapter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {


    public List<LineItem> myOrderItems = new ArrayList<>();
    public LineItemAdapter lineItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        com.pizzaapp.model.MenuItem item = new com.pizzaapp.model.MenuItem();
        item.setId("id");
        item.setInStock(true);
        item.setIsSpecial(true);
        item.setName("Beer");
        item.setPrice(14.99);
        item.setSize("Large");
        item.setStatus(MenuItemStatus.OPEN);

        LineItem lineItem = new LineItem();
        lineItem.setQuantity(2);
        lineItem.setItem(item);

        myOrderItems.add(lineItem);

        lineItemAdapter = new LineItemAdapter(this, myOrderItems);

        ListView view = (ListView) findViewById(R.id.listView2);
        view.setAdapter(lineItemAdapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pizza_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
