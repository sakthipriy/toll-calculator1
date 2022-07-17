package com.afry.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

    public static Date convertStringToDate(String date) throws ParseException {
         return DateUtils.parseDate(date,
                new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss" });
    }

    public static Date convertStringToTime(String timeInString) throws ParseException {
        return DateUtils.parseDate(timeInString,
                new String[] { "HH:mm:ss", "HH:mm:ss" });
    }

    public static Date convert(String dateInString) throws ParseException {
        return DateUtils.parseDate(dateInString,
                new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
    }

    public static String convertStrToDate(String dateInString) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateInString);
        String newString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return newString;
      /*  return DateUtils.parseDate(newString,
                new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });*/
    }

    public static String convertStrToTime(String dateInString) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateInString);
        String newString = new SimpleDateFormat("HH:mm:ss").format(date);
        return newString;
       /* return DateUtils.parseDate(newString,
                new String[] { "HH:mm:ss", "HH:mm:ss" });*/
    }




}
