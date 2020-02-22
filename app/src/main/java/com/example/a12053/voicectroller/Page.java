package com.example.a12053.voicectroller;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Page extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5;

    private Button moreBtn;

    private Button[] buttons = new Button[]{button1, button2, button3, button4, button5};

    private TextView ts;

    int flag = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);

        buttons[0] = findViewById(R.id.nameDBinKon);
        buttons[1] = findViewById(R.id.nameShanyuDon);
        buttons[2] = findViewById(R.id.nameZuojunXie);
        buttons[3] = findViewById(R.id.nameMinghangChen);
        buttons[4] = findViewById(R.id.nameJieLee);
        moreBtn    = findViewById(R.id.more);
        ts = findViewById(R.id.briefIntroduction);

        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

        setOnclick();
    }

    private void setOnclick(){
        Onclick onclick = new Onclick();

        buttons[0].setOnClickListener(onclick);
        buttons[1].setOnClickListener(onclick);
        buttons[2].setOnClickListener(onclick);
        buttons[3].setOnClickListener(onclick);
        buttons[4].setOnClickListener(onclick);
        moreBtn.setOnClickListener(onclick);
    }

    private class Onclick implements View.OnClickListener{

        public void onClick(View v){
            switch (v.getId()){
                case R.id.nameDBinKon:
                    if(flag == -1){
                        flag = 0;
                    }else {
                        buttons[flag].setActivated(false);
                    }
                    buttons[0].setActivated(true);
                    ts.setText("孔德彬的简介");
                    flag = 0;
                    break;
                case R.id.nameShanyuDon:
                    if(flag == -1){
                        flag = 1;
                    }else {
                        buttons[flag].setActivated(false);
                    }
                    buttons[1].setActivated(true);
                    ts.setText("董善宇的简介");
                    flag = 1;
                    break;
                case R.id.nameZuojunXie:
                    if(flag == -1){
                        flag = 2;
                    }else {
                        buttons[flag].setActivated(false);
                    }
                    buttons[2].setActivated(true);
                    ts.setText("谢卓君的简介");
                    flag = 2;
                    break;
                case R.id.nameMinghangChen:
                    if(flag == -1){
                        flag = 3;
                    }else {
                        buttons[flag].setActivated(false);
                    }
                    buttons[3].setActivated(true);
                    ts.setText("陈明航的简介");
                    flag = 3;
                    break;
                case R.id.nameJieLee:
                    if(flag == -1){
                        flag = 4;
                    }else {
                        buttons[flag].setActivated(false);
                    }
                    buttons[4].setActivated(true);
                    ts.setText("李捷的简介");
                    flag = 4;
                    break;
                case R.id.more:
                    Intent intent;
                    intent = new Intent(Page.this, More.class);
                    startActivity(intent);
            }
        }
    }
}
