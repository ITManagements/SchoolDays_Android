package com.example.scut.school_days;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnDateSelectedListener,OnMonthChangedListener{

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private MaterialCalendarView calendarView;
    private ListView listView;
    private Button add_button;
    private Button today;

    private ScheduleHandler schedulerhandler;
    private SimpleAdapter adapter;


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



        add_button = (Button) findViewById(R.id.add_but);
        add_button.setOnClickListener(this);
        today=(Button) findViewById(R.id.today);
        today.setOnClickListener(this);

        schedulerhandler = new ScheduleHandler(calendarView.getSelectedDate());
        adapter = new SimpleAdapter(this,schedulerhandler.loadScheduleOnDay(),R.layout.list_item,
                new String[]{KEY.SCHEDULE_NAME,KEY.SCHEDULE_TIME_DESCRIPTION},new int[]{R.id.acitvity_name,R.id.activity_start_time});
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ScheduleHandler.choose_item_position = position;
                showDeleteItemDialog();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = schedulerhandler.getSchedule(position);
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra(KEY.SCHEDULE_NAME, (String) map.get(KEY.SCHEDULE_NAME) );
                intent.putExtra(KEY.SCHEDULE_START_HOUR, (Integer) map.get(KEY.SCHEDULE_START_HOUR));
                intent.putExtra(KEY.SCHEDULE_START_MINUTE,(Integer) map.get(KEY.SCHEDULE_START_MINUTE));
                intent.putExtra(KEY.SCHEDULE_POSITION,position);
                startActivityForResult(intent,0);
            }
        });

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
                        CalendarDay day = CalendarDay.from(year,monthOfYear,dayOfMonth);
                        calendarView.setCurrentDate(day);
                        calendarView.setSelectedDate(day);
                        schedulerhandler.loadScheduleOnDay(day);
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


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        schedulerhandler.loadScheduleOnDay(date);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_but:
                startActivityForResult(new Intent(getApplicationContext(),DetailActivity.class),1);
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
                    schedulerhandler.updateSchedule(bundle.getInt(KEY.SCHEDULE_POSITION),bundle.getString(KEY.SCHEDULE_NAME),bundle.getInt(KEY.SCHEDULE_START_HOUR),bundle.getInt(KEY.SCHEDULE_START_MINUTE));
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    schedulerhandler.addSchedule(bundle.getString(KEY.SCHEDULE_NAME), bundle.getInt(KEY.SCHEDULE_START_HOUR),bundle.getInt(KEY.SCHEDULE_START_MINUTE));
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        String s = Format.formatDateTitle(date.getYear(),date.getMonth());
//        calendarView.setCurrentDate(CalendarDay.from(date.getYear(),date.getMonth(),CalendarDay.today().getDay()));
//        calendarView.setSelectedDate(CalendarDay.from(date.getYear(),date.getMonth(),CalendarDay.today().getDay()));
        getSupportActionBar().setTitle(s);
    }

    void showDeleteItemDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.longclick_ask_title).setMessage(
                R.string.longclick_ask_message).setPositiveButton(R.string.longclick_ask_positive_btn,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        schedulerhandler.deleteSchedule();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.longclick_ask_negative_btn,null).show();
    }
}
