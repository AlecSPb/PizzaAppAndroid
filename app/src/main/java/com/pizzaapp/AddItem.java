package com.pizzaapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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

    public class MenuService extends AsyncTask<Void, Void, List<com.pizzaapp.model.MenuItem>> {

        @Override
        protected List<com.pizzaapp.model.MenuItem> doInBackground(Void... params) {
            try {
                RestTemplate template = new RestTemplate();
                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                com.pizzaapp.model.MenuItem[] items = template.getForObject("http://montpelier.cs.colostate.edu:10105/menu", com.pizzaapp.model.MenuItem[].class);
                return Arrays.asList(items);

            } catch (Exception e) {
                Log.e("MenuService", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<com.pizzaapp.model.MenuItem> menu) {
            super.onPostExecute(menu);


            String all = "";
            for(com.pizzaapp.model.MenuItem item : menu) {
                all += item.getName() + "\n";
            }
           // TextView view = (TextView) findViewById(R.id.testTextView);
            //view.setText(all);
        }
    }
}
