package com.example.scut.school_days;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnDateSelectedListener,OnMonthChangedListener{

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    private MaterialCalendarView calendarView;

    private ListView listView;

    private Button add_button;

    private SimpleAdapter adapter;

    private List<Map<String,Object>> listitem;

    private Button today;
    private int choose_item_position;

    final String activity_name = "activity_name";
    final String activity_start_time = "strat_time";
    final String activity_start_time_hour = "hour";
    final String activity_start_time_minute = "minute";
    final String position_str = "pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView=(MaterialCalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangedListener(this);
        calendarView.setTopbarVisible(false);
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setOnMonthChangedListener(this);
        calendarView.setDynamicHeightEnabled(true);
        calendarView.setDateTextAppearance(android.R.style.TextAppearance_Material_Small);
        calendarView.setTileHeightDp(35);
        calendarView.setTileWidthDp(53);
        getSupportActionBar().setTitle(Format.formatDateTitle(CalendarDay.today()));

        listitem=new ArrayList<Map<String, Object>>();
        adapter = new SimpleAdapter(this,listitem,R.layout.list_item,
                new String[]{activity_name,activity_start_time},new int[]{R.id.acitvity_name,R.id.activity_start_time});
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        add_button = (Button) findViewById(R.id.add_but);
        add_button.setOnClickListener(this);
        today=(Button) findViewById(R.id.today);
        today.setOnClickListener(this);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                choose_item_position = position;
                deleteItem();
                return true;
            }
        });


        // intent's bundle include position activity_name start_time
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = listitem.get(position);

//                TextView name= (TextView) view.findViewById(R.id.acitvity_name);
//                TextView time = (TextView) view.findViewById(R.id.activity_start_time);
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra(activity_name, (String) map.get(activity_name) );
                intent.putExtra(activity_start_time_hour, (Integer) map.get(activity_start_time_hour));
                intent.putExtra(activity_start_time_minute,(Integer) map.get(activity_start_time_minute));
                intent.putExtra(position_str,position);
                startActivityForResult(intent,0);
            }
        });

    }



    void deleteItem(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.longclick_ask_title).setMessage(
                R.string.longclick_ask_message).setPositiveButton(R.string.longclick_ask_positive_btn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listitem.remove(choose_item_position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.longclick_ask_negative_btn,null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                break;
            case R.id.restore:
                break;
            case R.id.arrange:
                break;
            case R.id.jump:
                DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendarView.setCurrentDate(CalendarDay.from(year,monthOfYear,dayOfMonth));
                        calendarView.setSelectedDate(CalendarDay.from(year,monthOfYear,dayOfMonth));
                    }
                },CalendarDay.today().getYear(),CalendarDay.today().getMonth(),CalendarDay.today().getDay());

                datePicker.show();
                break;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(),ScheduleActivity.class));
                break;
            case R.id.log:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



    private Map<String,Object> generate_item(String title,int hour,int minute){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(activity_name,title);
        map.put(activity_start_time_hour,hour);
        map.put(activity_start_time_minute,minute);
        map.put(activity_start_time,Format.formatRemindTitle(hour,minute));
        return map;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        loadSelectedDaySchedule(date);
    }

    void loadSelectedDaySchedule(CalendarDay selectday){
        listitem.clear();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_but:
                startActivityForResult(new Intent(getApplicationContext(),DetailActivity.class),1);
//                CalendarDay day = calendarView.getSelectedDate();

//                Map<String, Object> map = generate_item(day.toString(), "content");
//                listitem.add(map);
//                adapter.notifyDataSetChanged();
//                Log.d(LOG_TAG,"add_but clicked");
                break;
            case R.id.today:
                CalendarDay currentmonth = CalendarDay.today();
                calendarView.setCurrentDate(currentmonth);
                calendarView.setSelectedDate(currentmonth);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            switch (requestCode){
                case 0:
                    setItem(bundle.getInt(position_str),bundle.getString(activity_name),bundle.getInt(activity_start_time_hour),bundle.getInt(activity_start_time_minute));
                    break;
                case 1:
                    setItem(-1,bundle.getString(activity_name), bundle.getInt(activity_start_time_hour),bundle.getInt(activity_start_time_minute));
                    break;
            }
        }

    }

    private void setItem(int position,String title,int hour,int minute){
        Map<String, Object> map = generate_item(title, hour,minute);
        if(position==-1)
            listitem.add(map);
        else
            listitem.set(position,map);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        String s = Format.formatDateTitle(date.getYear(),date.getMonth());
//        calendarView.setCurrentDate(CalendarDay.from(date.getYear(),date.getMonth(),CalendarDay.today().getDay()));
//        calendarView.setSelectedDate(CalendarDay.from(date.getYear(),date.getMonth(),CalendarDay.today().getDay()));
        getSupportActionBar().setTitle(s);
    }
}
