package com.example.sovjanta.utilities;

import android.os.Build;


import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatterUtility {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String format(String time) {
        String timefinal=time;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(timefinal, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }
}