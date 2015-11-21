package com.pizzaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPayment extends AppCompatActivity {

    String name;
    String cardNumber;
    String amount;
    String expDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        Button addButton = (Button) findViewById(R.id.buttonSubmit);
        addButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                //TODO Create Summary Class
                final EditText submittedName = (EditText) findViewById(R.id.editText_Name);
                final EditText submittedCC = (EditText) findViewById(R.id.editText_CardNumber);
                final EditText submittedAmount = (EditText) findViewById(R.id.editText_Amount);
                final EditText submittedDate = (EditText) findViewById(R.id.editText_expDate);

                name = submittedName.getText().toString();
                cardNumber = submittedCC.getText().toString();
                amount = submittedAmount.getText().toString();
                expDate = submittedDate.getText().toString();

                Toast.makeText(getApplicationContext(), name + " " + cardNumber + " " + amount + " " + expDate, Toast.LENGTH_LONG).show();

                //Intent i = new Intent(AddPayment.this, Summary.class);
            }
        });
    }
}
