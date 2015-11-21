package com.pizzaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pizzaapp.model.LineItem;
import com.pizzaapp.model.Order;
import com.pizzaapp.model.PaymentTransaction;
import com.pizzaapp.ui.PaymentTransactionAdapter;
import com.pizzaapp.util.StringFormatService;

import java.util.ArrayList;
import java.util.List;

public class Pay extends AppCompatActivity {

    public Order order;
    public List<PaymentTransaction> transactions;
    public PaymentTransactionAdapter paymentTransactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        order = (Order) getIntent().getExtras().get("order");
        transactions = new ArrayList<>();
        paymentTransactionAdapter = new PaymentTransactionAdapter(this, this, transactions);

        Button addPayment = (Button) findViewById(R.id.addPaymentButton);
        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pay.this, AddPayment.class);
                startActivityForResult(intent, 1);
            }
        });

        Button finishPayment = (Button) findViewById(R.id.payFinishButton);
        finishPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setTransactions(transactions);
                Intent intent = new Intent(Pay.this, Summary.class);
                intent.putExtra("order", order);
                startActivityForResult(intent, 2);
            }
        });

        ListView paymentListView = (ListView)findViewById(R.id.payListView);
        paymentListView.setAdapter(paymentTransactionAdapter);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 1) {
            PaymentTransaction transaction = (PaymentTransaction) intent.getSerializableExtra("newPayment");
            transactions.add(transaction);

            updateBalance();

            paymentTransactionAdapter.notifyDataSetChanged();
        } else if(requestCode == 2) {
            Order finishedOrder = (Order) intent.getSerializableExtra("finishedOrder");
            Intent unwind = new Intent();
            unwind.putExtra("finishedOrder", finishedOrder);
            setResult(Activity.RESULT_OK, unwind);
            finish();
        }
    }

    public void updateBalance() {
        double totalPayed = 0.0;
        for (PaymentTransaction transaction : transactions) {
            totalPayed += transaction.getAmount();
        }

        double totalOwed = 0.0;
        for (LineItem lineItem : order.getLineItems()) {
            totalOwed += lineItem.getQuantity() * lineItem.getItem().getPrice();
        }

        double balance = totalOwed - totalPayed;

        Button finishButton = (Button) findViewById(R.id.payFinishButton);
        TextView balanceTV = (TextView) findViewById(R.id.payBalance);
        if(balance >= 0) {
            balanceTV.setText("Balance: " + StringFormatService.sharedInstance().currencyToString(balance));
            finishButton.setEnabled(false);
        } else {
            balanceTV.setText("Tip: " + StringFormatService.sharedInstance().currencyToString(-balance));
            finishButton.setEnabled(true);
        }
    }
}
