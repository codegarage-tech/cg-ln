package com.meembusoft.ln.util;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class DateManager {

    public static final String TAG = DateManager.class.getSimpleName();

    //Date time format
    public static final String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
    public static final String dd_MM_yyyy_hh_mm_ss = "dd-MM-yyyy hh:mm:ss";
    public static final String yyyy_MM_dd_hh_mm = "yyyy-MM-dd hh:mm";

    public static String convertDateTime(String date, String originalFormat, String outputFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.US);
        try {
            Date mDate = formatter.parse(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat(outputFormat, new Locale("US"));
            return dateFormat.format(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatCurrentDateTime(String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final CharSequence getFormattedTimestamp(long timestamp) {
        return DateUtils.getRelativeTimeSpanString(timestamp, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

    public static long convertDateTimeToMillisecond(String dateTime, String format) {
//        String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        long timeInMilliseconds = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date mDate = simpleDateFormat.parse(dateTime);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            timeInMilliseconds = 0;
        }
        Log.d(TAG, TAG + ">> " + "DateTimeToMillisecond: " + timeInMilliseconds);

        return timeInMilliseconds;
    }

    public static String convertEnglishDigitToBengali(String selectedText) {
        HashMap<Integer, String> hash = new HashMap<Integer, String>() {{
            put(0, "০");
            put(1, "১");
            put(2, "২");
            put(3, "৩");
            put(4, "৪");
            put(5, "৫");
            put(6, "৬");
            put(7, "৭");
            put(8, "৮");
            put(9, "৯");
        }};
        String bengaliText = "";
        char[] chars = selectedText.toCharArray();
        for (char digit : chars) {
            try {
                int selectedDigit = Integer.parseInt(String.valueOf(digit));
                bengaliText += hash.get(selectedDigit);
            } catch (Exception ex) {
                bengaliText += digit;
            }
        }
        return bengaliText;
    }

    public static String convertBengaliDigitToEnglish(String selectedText) {
        HashMap<String, Integer> hash = new HashMap<String, Integer>() {{
            put("০", 0);
            put("১", 1);
            put("২", 2);
            put("৩", 3);
            put("৪", 4);
            put("৫", 5);
            put("৬", 6);
            put("৭", 7);
            put("৮", 8);
            put("৯", 9);
        }};
        String englishText = "";
        char[] chars = selectedText.toCharArray();
        for (char digit : chars) {
            try {
                String selectedDigit = String.valueOf(digit);
                englishText += hash.get(selectedDigit);
            } catch (Exception ex) {
                englishText += digit;
            }
        }
        return englishText;
    }
}