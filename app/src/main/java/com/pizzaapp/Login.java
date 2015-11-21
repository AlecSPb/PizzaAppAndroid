package com.pizzaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pizzaapp.model.Account;
import com.pizzaapp.service.ApiProtocol;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView emailTV = (TextView) findViewById(R.id.email);
        TextView passwordTV = (TextView) findViewById(R.id.password);
        emailTV.setText("customer@cs414.com");
        passwordTV.setText("password");

        Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAuthenticate(false);
            }
        });

        Button register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAuthenticate(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public class AccountService extends AsyncTask<Void, Void, Account> {

        private Account login;
        private boolean isRegister;

        public AccountService(Account login, boolean isRegister) {
            super();
            this.login = login;
            this.isRegister = isRegister;
        }

        @Override
        protected Account doInBackground(Void... params) {
            try {
                RestTemplate template = new RestTemplate();
                template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                String serv = ApiProtocol.server;
                if(isRegister) {
                    serv += "/register";
                } else {
                    serv += "/login";
                }
                return template.postForObject(serv, login, Account.class);

            } catch (Exception e) {
                Log.e("AccountService", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);

            if(account == null) {
                Toast.makeText(getApplicationContext(), "Login failed.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Login.this, Home.class);
                intent.putExtra("account", account);
                Login.this.startActivity(intent);
            }
        }


    }

    private void tryAuthenticate(boolean isRegister) {
        TextView emailTV = (TextView) findViewById(R.id.email);
        TextView passwordTV = (TextView) findViewById(R.id.password);

        Account login = new Account();
        login.setEmail(emailTV.getText().toString());
        login.setPassword(passwordTV.getText().toString());

        new AccountService(login, isRegister).execute();

    }
}
