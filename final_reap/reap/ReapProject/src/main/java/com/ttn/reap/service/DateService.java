package com.ttn.reap.service;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class DateService {
    public Date solveDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt= null;
        try {
            dt = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
}
