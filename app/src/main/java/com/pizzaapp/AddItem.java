package com.pizzaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pizzaapp.model.LineItem;
import com.pizzaapp.model.MenuItem;
import com.pizzaapp.service.ApiProtocol;
import com.pizzaapp.ui.MenuItemAdapter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItem extends AppCompatActivity {

    private final ArrayList<MenuItem> menu = new ArrayList<>();
    private MenuItemAdapter menuItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        menuItemAdapter = new MenuItemAdapter(this, menu);

        final ListView menuList = (ListView)findViewById(R.id.listView_Menu);

        menuItemAdapter = new MenuItemAdapter(this, menu);
        menuList.setAdapter(menuItemAdapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
                MenuItem selectedItem = menu.get(pos);
                LineItem newItem = new LineItem();
                newItem.setQuantity(1);
                newItem.setItem(selectedItem);
                Toast.makeText(getApplicationContext(), "Added " + selectedItem, Toast.LENGTH_LONG).show();

                AddItem.this.returnItemToHome(newItem);
            }
        });

        new MenuService().execute();
    }

    public void returnItemToHome(LineItem item) {
        Intent intent = new Intent(AddItem.this, Home.class);
        intent.putExtra("newLineItem", item);
        startActivityForResult(intent, 1);
    }

    public class MenuService extends AsyncTask<Void, Void, List<com.pizzaapp.model.MenuItem>> {

        @Override
        protected List<com.pizzaapp.model.MenuItem> doInBackground(Void... params) {
            try {
                RestTemplate template = new RestTemplate();
                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                com.pizzaapp.model.MenuItem[] items = template.getForObject(ApiProtocol.server + "/menu", com.pizzaapp.model.MenuItem[].class);
                return Arrays.asList(items);

            } catch (Exception e) {
                Log.e("MenuService", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<com.pizzaapp.model.MenuItem> menu) {
            super.onPostExecute(menu);

            AddItem.this.menu.clear();
            AddItem.this.menu.addAll(menu);
            AddItem.this.menuItemAdapter.notifyDataSetChanged();

        }


    }
}
