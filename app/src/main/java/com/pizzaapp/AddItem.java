package com.pizzaapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pizzaapp.model.MenuItem;
import com.pizzaapp.service.ApiProtocol;
import com.pizzaapp.ui.MenuItemAdapter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItem extends AppCompatActivity {

    private final List<MenuItem> menu = new ArrayList<>();
    private MenuItemAdapter menuItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        menuItemAdapter = new MenuItemAdapter(this, menu);

        new MenuService().execute();
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
