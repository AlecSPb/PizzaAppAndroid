package com.pizzaapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        lineItemAdapter = new LineItemAdapter(this, myOrderItems);

        ListView view = (ListView) findViewById(R.id.listView2);
        view.setAdapter(lineItemAdapter);

        Button addButton = (Button) findViewById(R.id.button_Add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddItem.class);
                Home.this.startActivity(intent);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        LineItem item = (LineItem) intent.getSerializableExtra("newLineItem");

        boolean alreadyExists = false;
        for (LineItem myOrderItem : myOrderItems) {
            if(myOrderItem.getItem().getName().equals(item.getItem().getName())) {
                myOrderItem.setQuantity(myOrderItem.getQuantity() + 1);
                alreadyExists = true;
                break;
            }
        }

        if(!alreadyExists) {
            myOrderItems.add(item);
        }
    }

}
