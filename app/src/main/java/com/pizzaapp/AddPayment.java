package com.pizzaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pizzaapp.model.CreditCard;
import com.pizzaapp.model.PaymentTransaction;

public class AddPayment extends AppCompatActivity {

    String name;
    String cardNumber;
    double amount;
    String expDate;
    String cvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        Button addButton = (Button) findViewById(R.id.buttonSubmit);
        addButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                //Get text from boxes and store them as STrings
                final EditText submittedName = (EditText) findViewById(R.id.editText_Name);
                final EditText submittedCC = (EditText) findViewById(R.id.editText_CardNumber);
                final EditText submittedAmount = (EditText) findViewById(R.id.editText_Amount);
                final EditText submittedDate = (EditText) findViewById(R.id.editText_expDate);
                final EditText submittedCVS = (EditText) findViewById(R.id.cvs_number);

                name = submittedName.getText().toString();
                cardNumber = submittedCC.getText().toString();
                String tempAmount = submittedAmount.getText().toString();
                expDate = submittedDate.getText().toString();
                cvs = submittedCVS.getText().toString();

                //Try to convert STring to double. Catch failure and display error message
                try {
                    amount = Double.parseDouble(tempAmount);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Invalid Amount Entered. Payment not submitted", Toast.LENGTH_LONG).show();
                }

                //Create a credit card and validate all data.
                CreditCard c = new CreditCard(name, cardNumber , expDate, cvs);
                if (!c.validName(c.getName())){
                    Toast.makeText(getApplicationContext(), "Invalid Name Entered. Payment not submitted", Toast.LENGTH_LONG).show();
                }else if(!c.validCC(c.getNumber())){
                    Toast.makeText(getApplicationContext(), "Invalid Card Number Entered. Payment not submitted", Toast.LENGTH_LONG).show();
                }else if(!c.validExpiration(c.getExpiration())){
                    Toast.makeText(getApplicationContext(), " Card Is Expired! Payment not submitted", Toast.LENGTH_LONG).show();
                }else if(!c.validCVS(c.getCvs())){
                    Toast.makeText(getApplicationContext(), "Invalid CVS Entered. Payment not submitted", Toast.LENGTH_LONG).show();
                }else{
                    //If everything is valid, create the paymenttransaction and send back to the summary activity
                    PaymentTransaction p = new PaymentTransaction(amount, c);
                    //Intent i = new Intent(AddPayment.this, Summary.class);

                    Intent intent = new Intent();
                    intent.putExtra("newPayment", p);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }



                Toast.makeText(getApplicationContext(), name + " " + cardNumber + " " + amount + " " + expDate, Toast.LENGTH_LONG).show();
            }
        });
    }
}
