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
import android.widget.Toast;

import com.pizzaapp.model.Account;
import com.pizzaapp.model.LineItem;
import com.pizzaapp.model.MenuItemStatus;
import com.pizzaapp.model.Order;
import com.pizzaapp.model.PaymentTransaction;
import com.pizzaapp.service.ApiProtocol;
import com.pizzaapp.ui.LineItemAdapter;
import com.pizzaapp.ui.MenuItemAdapter;
import com.pizzaapp.util.StringFormatService;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Home extends AppCompatActivity {

    public Order myOrder;
    public List<LineItem> myOrderItems = new ArrayList<>();
    public LineItemAdapter lineItemAdapter;
    public Account account;
    public double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myOrder = new Order();

        account = (Account) getIntent().getExtras().get("account");

        TextView pointsTV = (TextView) findViewById(R.id.points);
        pointsTV.setText("Points: " + account.getPoints());

        lineItemAdapter = new LineItemAdapter(this, this, myOrderItems);

        ListView view = (ListView) findViewById(R.id.listView2);
        view.setAdapter(lineItemAdapter);

        Button addButton = (Button) findViewById(R.id.button_Add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddItem.class);
                startActivityForResult(intent, 1);
            }
        });

        Button payButton = (Button) findViewById(R.id.button_Pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Pay.class);
                myOrder.setLineItems(myOrderItems);
                intent.putExtra("order", myOrder);
                startActivityForResult(intent, 2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == RESULT_CANCELED) return;
        if(requestCode == 1) {
            LineItem item = (LineItem) intent.getSerializableExtra("newLineItem");

            boolean alreadyExists = false;
            for (LineItem myOrderItem : myOrderItems) {
                if (myOrderItem.getItem().getName().equals(item.getItem().getName())) {
                    myOrderItem.setQuantity(myOrderItem.getQuantity() + 1);
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                myOrderItems.add(item);
            }

            updateTotal();

            lineItemAdapter.notifyDataSetChanged();
        } else if(requestCode == 2) {
            Order finishedOrder = (Order) intent.getSerializableExtra("finishedOrder");
            if (finishedOrder != null) {
                new OrderService(finishedOrder).execute();
                resetOrder();
                updateTotal();

                double payed = 0.0;
                for (PaymentTransaction transaction : finishedOrder.getTransactions()) {
                    payed += transaction.getAmount();
                }

                new PointsService(account, (int)payed).execute();
            }
        }
    }

    public void updateTotal() {
        total = 0.0;
        for (LineItem myOrderItem : myOrderItems) {
            total += myOrderItem.getQuantity() * myOrderItem.getItem().getPrice();
        }

        TextView totalTV = (TextView) findViewById(R.id.total);
        totalTV.setText("Total: " + StringFormatService.sharedInstance().currencyToString(total));
    }

    public void resetOrder() {
        myOrder = new Order();
        myOrderItems.clear();
        lineItemAdapter.notifyDataSetChanged();
    }

    public class OrderService extends AsyncTask<Void, Void, Order> {

        public Order finishedOrder;

        public OrderService(Order finishedOrder) {
            super();
            this.finishedOrder = finishedOrder;
        }

        @Override
        protected Order doInBackground(Void... params) {
            try {
                RestTemplate template = new RestTemplate();
                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                return template.postForObject(ApiProtocol.server + "/orders", finishedOrder, Order.class);

            } catch (Exception e) {
                Log.e("OrderService", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Order order) {
            super.onPostExecute(order);

            Toast.makeText(getApplicationContext(), "Order placed.", Toast.LENGTH_LONG).show();
        }


    }

    public class PointsService extends AsyncTask<Void, Void, Account> {

        public Account account;
        public int points;

        public PointsService(Account account, int points) {
            super();
            this.account = account;
            this.points = points;
        }

        @Override
        protected Account doInBackground(Void... params) {
            try {
                RestTemplate template = new RestTemplate();
                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                account.setPoints(account.getPoints() + points);
                //return template.putForObject(ApiProtocol.server + "/accounts/" + account.getId(), account, Account.class);
                template.put(ApiProtocol.server + "/accounts/" + account.getId(), account);

                return account;

            } catch (Exception e) {
                Log.e("PointsService", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Account acc) {
            super.onPostExecute(acc);

            if(acc == null) return;

            Toast.makeText(getApplicationContext(), "Points Rewarded: " + points, Toast.LENGTH_LONG).show();
            TextView pointsTV = (TextView) findViewById(R.id.points);
            pointsTV.setText("Points: " + acc.getPoints());
        }


    }
}
