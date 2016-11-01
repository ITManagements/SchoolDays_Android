package com.example.scut.school_days;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    public final String TAG = DetailActivity.class.getSimpleName();
    private Intent intent;

    private EditText activity_content_edittext;
    private Button pick_time_btn;

    final String activity_name = "activity_name";
    final String activity_start_time = "strat_time";
    final String activity_start_time_hour = "hour";
    final String activity_start_time_minute = "minute";
    final String position_str = "pos";

//    private String time;
    private int pick_hour;
    private int pick_minute;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context=this;

        pick_time_btn = (Button) findViewById(R.id.pick_time);
        activity_content_edittext = (EditText) findViewById(R.id.activity_content);

        pick_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        pick_hour = hourOfDay;
                        pick_minute = minute;
                        pick_time_btn.setText(Format.formatRemindTitle(hourOfDay,minute));
                    }
                }, new Date().getHours(),new Date().getMinutes(),true).show();
            }
        });

        intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null) {
            pick_hour = bundle.getInt(activity_start_time_hour);
            pick_minute = bundle.getInt(activity_start_time_minute);
            activity_content_edittext.setText(bundle.getString(activity_name));
            pick_time_btn.setText(Format.formatRemindTitle(pick_hour,pick_minute));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.confirm:
                intent.putExtra(activity_start_time_hour,pick_hour);
                intent.putExtra(activity_start_time_minute,pick_minute);
                intent.putExtra(activity_name, activity_content_edittext.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
