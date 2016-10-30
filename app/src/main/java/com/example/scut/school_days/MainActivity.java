package com.example.scut.school_days;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnDateSelectedListener,OnMonthChangedListener{

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    private MaterialCalendarView calendarView;

    private ListView listView;

    private Button button;

    private SimpleAdapter adapter;

    private List<Map<String,Object>> listitem;

    private Button today;

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
        calendarView.setTileHeightDp(32);
        calendarView.setTileWidthDp(50);
        getSupportActionBar().setTitle(FORMATTER.format(CalendarDay.today().getDate()));

        listitem=new ArrayList<Map<String, Object>>();

        adapter = new SimpleAdapter(this,listitem,R.layout.list_item,
                new String[]{"title","content"},new int[]{R.id.title,R.id.content});

        listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = adapter.getItem(position);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView content = (TextView) view.findViewById(R.id.content);
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("title",title.getText());
                intent.putExtra("content",content.getText());
                intent.putExtra("position",position);
                startActivityForResult(intent,0);
            }
        });

        button = (Button) findViewById(R.id.add_but);

        button.setOnClickListener(this);

        today=(Button) findViewById(R.id.today);

        today.setOnClickListener(this);

    }

//    private List<Map<String,Object>> getData(CalendarDay day){
//        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
//
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("title","big title");
//        map.put("content","small content");
//        data.add(map);
//
//        return  data;
//    }

    private Map<String,Object> generate_item(String title,String content){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("title",title);
        map.put("content",content);
        return map;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        System.out.println("selected day");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_but:
                CalendarDay day = calendarView.getSelectedDate();

                Map<String, Object> map = generate_item(day.toString(), "content");
                listitem.add(map);
                adapter.notifyDataSetChanged();
                Log.d(LOG_TAG,"add_but clicked");
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

        switch (resultCode){
            case RESULT_OK:
                Bundle bundle = data.getExtras();
                setItem(bundle.getInt("position"),bundle.getString("title"),bundle.getString("content"));
        }
    }

    private void setItem(int position,String title,String content){
        Map<String, Object> map = generate_item(title, content);
        listitem.set(position,map);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }
}
