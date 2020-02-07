package com.example.a12053.voicectroller;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = this.getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        textView = findViewById(R.id.user_name);
        textView.setText("欢迎回来: " + intent.getExtras().getString("name"));

        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

    }
}
