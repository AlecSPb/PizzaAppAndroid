package com.pizzaapp.util;

import java.text.NumberFormat;
import java.util.Locale;

public class StringFormatService {

    private static StringFormatService instance = new StringFormatService();

    private StringFormatService() {}

    public static StringFormatService sharedInstance() {
        return instance;
    }

    public String currencyToString(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(amount);
    }

}
