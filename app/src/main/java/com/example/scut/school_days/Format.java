package com.example.scut.school_days;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Created by Administrator on 2016/11/1.
 */

public class Format {
    public static String formatDateTitle(int year,int month){
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("年");
        sb.append(month);
        sb.append("月");
        return sb.toString();
    }

    public static String formatDateTitle(CalendarDay date){
        int year=date.getYear();
        int month = date.getMonth();
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("年");
        sb.append(month);
        sb.append("月");
        return sb.toString();
    }

    public static String formatRemindTitle(int hour,int minute){
        StringBuilder sb = new StringBuilder();
        sb.append("活动提醒时间： ");
        sb.append(hour);
        sb.append(":");
        sb.append(minute);
        return sb.toString();

    }
}
