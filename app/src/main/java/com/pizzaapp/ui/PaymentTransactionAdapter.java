package com.pizzaapp.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pizzaapp.Home;
import com.pizzaapp.Pay;
import com.pizzaapp.R;
import com.pizzaapp.model.LineItem;
import com.pizzaapp.model.PaymentTransaction;
import com.pizzaapp.util.StringFormatService;

import java.util.List;

public class PaymentTransactionAdapter extends ArrayAdapter<PaymentTransaction> {

    LayoutInflater inflater;
    Pay payScreen;

    public PaymentTransactionAdapter(Context context, Pay payScreen, List<PaymentTransaction> transactions) {
        super(context, 0, transactions);
        inflater = LayoutInflater.from(context);
        this.payScreen = payScreen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PaymentTransaction transaction = getItem(position);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.payment_transaction_row, parent, false);
        }


        TextView nameTV = (TextView) convertView.findViewById(R.id.paymentName);
        TextView amountTV = (TextView) convertView.findViewById(R.id.paymentAmount);

        String cardNumber = transaction.getCard().getNumber();
        String last4 = cardNumber.substring(cardNumber.length() - 5, cardNumber.length() -1);
        nameTV.setText("CC: " + last4);

        amountTV.setText(StringFormatService.sharedInstance().currencyToString(transaction.getAmount()));

        Button removeButton = (Button) convertView.findViewById(R.id.paymentRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentTransactionAdapter.this.remove(transaction);
                PaymentTransactionAdapter.this.notifyDataSetChanged();
                payScreen.updateBalance();
            }
        });

        return convertView;
    }
}
