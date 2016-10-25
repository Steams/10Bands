package com.example.steams.a10bands.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by steams on 10/22/16.
 */

public class CurrencyService {

    public static String makeString(Double value){
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        String str =  format.format(value);
        return str.substring(0,str.length()-3);
    }

}
