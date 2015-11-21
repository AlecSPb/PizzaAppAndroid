package com.pizzaapp.ui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pizzaapp.R;
import com.pizzaapp.model.LineItem;

import java.util.List;

public class LineItemAdapter extends ArrayAdapter<LineItem> {

    LayoutInflater inflater;

    public LineItemAdapter(Context context, List<LineItem> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LineItem item = getItem(position);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.line_item_row, parent, false);
        }


        TextView nameTextView = (TextView) convertView.findViewById(R.id.itemName);
        nameTextView.setText(item.getItem().getName());

        TextView quantityTextView = (TextView) convertView.findViewById(R.id.quantity);
        quantityTextView.setText(item.getQuantity() + "x");

        TextView priceTextView = (TextView) convertView.findViewById(R.id.price);
        priceTextView.setText("$" + item.getQuantity() * item.getItem().getPrice());

        return convertView;
    }
}
