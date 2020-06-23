package com.project.civillian.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String DATE_PATTERN_DB = "yyyyMMdd";
    public static String DATE_PATTERN_VIEW = "dd MMMM yyyy";

    public static String dateToString(Date date, String pattern){
        if(date != null){
            try {
                SimpleDateFormat sd = new SimpleDateFormat(pattern);
                return sd.format(date);
            } catch (Exception e){
                return "";
            }
        } else {
            return "";
        }
    }


    public static Date StringToDate(String date, String pattern){
        if(date != null){
            try {
                SimpleDateFormat sd = new SimpleDateFormat(pattern);
                return sd.parse(date);
            } catch (Exception e){
                return null;
            }
        } else {
            return null;
        }
    }

}
