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
    private int year;
    private int month;
    private int day;

    public static int choose_item_position;

    public ScheduleHandler(CalendarDay selectedDay){
//        loadScheduleOnDay(selectedDay.getYear(),selectedDay.getMonth(),selectedDay.getDay());
        year = selectedDay.getYear();
        month = selectedDay.getMonth();
        day = selectedDay.getDay();
        listitem=new ArrayList<Map<String, Object>>();
    }

    public ScheduleHandler(int year,int month,int day){
//        loadScheduleOnDay(year,month,day);
        this.year = year;
        this.month = month;
        this.day = day;
        listitem=new ArrayList<Map<String, Object>>();
    }

    public List<Map<String,Object>> loadScheduleOnDay(int year,int month,int day){
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
            // TODO: 2016/11/3  load the data from storage 
            return listitem;
        }
    }

    public Map<String,Object> getSchedule(int position){
        return listitem.get(position);
    }

    public void addSchedule(String name,int hour,int minute){
        listitem.add(generate_item(name,hour,minute));
        // TODO: 2016/11/3 write the schedule item to storage 
    }

    public Map<String,Object> popSchedule(int position){
        // TODO: 2016/11/3 delete the item from storage 
        return listitem.remove(position);
    }

    public void deleteSchedule(int position){
        // TODO: 2016/11/3 delete the item from storage 
        listitem.remove(position);
    }

    public void deleteSchedule(){
        // TODO: 2016/11/3 delete the item from storage 
        listitem.remove(ScheduleHandler.choose_item_position);
    }

    // where is the old one ???
    public void updateSchedule(int position,String name,int hour,int minute){
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
