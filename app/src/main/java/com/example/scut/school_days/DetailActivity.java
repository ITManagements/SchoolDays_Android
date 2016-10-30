package com.example.scut.school_days;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public final String TAG = DetailActivity.class.getSimpleName();

    private Button submit_but;

    private Intent intent;

    private EditText detail_title;

    private EditText detail_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        submit_but = (Button) findViewById(R.id.submit_but);

        detail_title = (EditText) findViewById(R.id.detail_title);

        detail_content = (EditText) findViewById(R.id.detail_content);

        intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        detail_title.setText(bundle.getString("title"));
        detail_content.setText(bundle.getString("content"));

        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+detail_title.getText());
                intent.putExtra("title",detail_title.getText().toString());
                intent.putExtra("content",detail_content.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

}
