package com.example.scut.school_days;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/3.
 */

public class ScheduleHandler {
    private List<Map<String,Object>> listitem;
    public int year;
    public int month;
    public int day;
    private List scheduleList;
    private static ScheduleHandler scheduleHandler;

    public static int choose_item_position;

    public static ScheduleHandler getInstance(){
        return scheduleHandler;
    }

    public static ScheduleHandler getInstance(CalendarDay selectedDay){
        scheduleHandler = new ScheduleHandler(selectedDay);
        return scheduleHandler;
    }

    public static ScheduleHandler getInstance(int year,int month,int day){
        scheduleHandler = new ScheduleHandler(year, month, day);
        return scheduleHandler;
    }

    private ScheduleHandler(CalendarDay selectedDay){
        year = selectedDay.getYear();
        month = selectedDay.getMonth();
        day = selectedDay.getDay();
        listitem=new ArrayList<Map<String, Object>>();
        scheduleList = new ArrayList<Schedule>();
    }

    private ScheduleHandler(int year,int month,int day){
//        loadScheduleOnDay(year,month,day);
        this.year = year;
        this.month = month;
        this.day = day;
        listitem=new ArrayList<Map<String, Object>>();
        scheduleList = new ArrayList<Schedule>();
    }

    public List<Map<String,Object>> loadScheduleOnDay(int year,int month,int day){
        // TODO: 2016/11/3 load the data from storage
        return listitem;
    }

    public List<Map<String,Object>> loadScheduleOnDay(){
        // TODO: 2016/11/3 load the data from storage 
        return listitem;
    }

    public List<Map<String,Object>> loadScheduleOnDay(CalendarDay selectedDay){
        int d = selectedDay.getDay();
        int m = selectedDay.getMonth();
        int y = selectedDay.getYear();
        if(d ==day && m == month && y == year)
            return listitem;
        else{
            year = y;
            month = m;
            day = d;
            listitem.clear();
            scheduleList.clear();
            // TODO: 2016/11/3  load the data from storage 
            return listitem;
        }
    }

    public Map<String,Object> getMapFromSchedule(int position){
        return listitem.get(position);
    }

    public Schedule getSchedule(int position){
        return (Schedule) scheduleList.get(position);
    }

    public void addSchedule(String name,int hour,int minute){
        listitem.add(generate_item(name,hour,minute));
        // TODO: 2016/11/3 write the schedule item to storage 
    }

    public void addSchedule(Schedule schedule){
        scheduleList.add(schedule);
        addSchedule(schedule.title,schedule.hour,schedule.minute);
    }

    public Map<String,Object> popSchedule(int position){
        // TODO: 2016/11/3 delete the item from storage
        scheduleList.remove(position);
        return listitem.remove(position);
    }

    public void deleteSchedule(int position){
        // TODO: 2016/11/3 delete the item from storage
        scheduleList.remove(position);
        listitem.remove(position);
    }

    public void deleteSchedule(){
        // TODO: 2016/11/3 delete the item from storage
        scheduleList.remove(ScheduleHandler.choose_item_position);
        listitem.remove(ScheduleHandler.choose_item_position);
    }

    // where is the old one ???
    public void updateSchedule(int position,Schedule schedule){
        scheduleList.set(position,schedule);
        String name = schedule.title;
        int hour = schedule.hour;
        int minute = schedule.minute;
        listitem.set(position,generate_item(name,hour,minute));
        // TODO: 2016/11/3 update the item detail to storage 
    }

    private Map<String,Object> generate_item(String name,int hour,int minute){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(KEY.SCHEDULE_NAME,name);
        map.put(KEY.SCHEDULE_START_HOUR,hour);
        map.put(KEY.SCHEDULE_START_MINUTE,minute);
        map.put(KEY.SCHEDULE_TIME_DESCRIPTION,Format.formatRemindTitle(hour,minute));
        return map;
    }
}
