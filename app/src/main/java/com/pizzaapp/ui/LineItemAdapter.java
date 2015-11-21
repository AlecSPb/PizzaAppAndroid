package com.pizzaapp.ui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pizzaapp.R;
import com.pizzaapp.model.LineItem;
import com.pizzaapp.util.StringFormatService;

import java.util.List;

public class LineItemAdapter extends ArrayAdapter<LineItem> {

    LayoutInflater inflater;

    public LineItemAdapter(Context context, List<LineItem> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LineItem item = getItem(position);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.line_item_row, parent, false);
        }


        TextView nameTextView = (TextView) convertView.findViewById(R.id.itemName);
        nameTextView.setText(item.getItem().getName());

        TextView quantityTextView = (TextView) convertView.findViewById(R.id.quantity);
        quantityTextView.setText(item.getQuantity() + "x");

        TextView priceTextView = (TextView) convertView.findViewById(R.id.price);
        double totalPrice = item.getItem().getPrice() * item.getQuantity();
        priceTextView.setText(StringFormatService.sharedInstance().currencyToString(totalPrice));

        Button removeButton = (Button) convertView.findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineItemAdapter.this.remove(item);
                LineItemAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
