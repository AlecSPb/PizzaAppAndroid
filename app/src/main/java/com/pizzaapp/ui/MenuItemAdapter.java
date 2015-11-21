package com.pizzaapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pizzaapp.R;
import com.pizzaapp.model.MenuItem;
import com.pizzaapp.util.StringFormatService;

import java.util.List;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    public MenuItemAdapter(Context context, List<MenuItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuItem item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item_row, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.itemName);
        textView.setText(item.getName());

        TextView priceTextView = (TextView) convertView.findViewById(R.id.price);
        priceTextView.setText(StringFormatService.sharedInstance().currencyToString(item.getPrice()));

        if(item.getIsSpecial())
            priceTextView.setTextColor(Color.rgb(247, 202, 24));

        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
