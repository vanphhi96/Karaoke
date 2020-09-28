package com.example.vanph.karaokemanage.Acitivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vanph on 01/11/2017.
 */

public class ConvertDate {
    public static String DateToString(Date date)
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String strDate = sdfDate.format(date);
        return  strDate;
    }
    public static Date StringToDate(String strDate)
    {
        Date date=null;
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        try{
             date = df.parse(strDate);
           return  date;

        }
        catch ( Exception ex ){
            System.out.println(ex);
            return date;

        }

    }
}
