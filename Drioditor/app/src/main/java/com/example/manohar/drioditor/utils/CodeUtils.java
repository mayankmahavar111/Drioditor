package com.example.manohar.drioditor.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CodeUtils {
    public static String dateFromLong(Long time){
        DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy 'at' hh:mm aaa",Locale.US);
        return format.format(new Date(time));
    }
}
