package com.example.a12053.voicectroller;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Title extends LinearLayout {

    private Button btnPage;
    private Button btnMainActivity;
    private Button btnSettings;

    public Title(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);

        btnPage = findViewById(R.id.page);
        btnMainActivity = findViewById(R.id.translate);
        btnSettings = findViewById(R.id.settings);

        setListeners();
    }

    private void setListeners() {
        OnClick onClick = new OnClick();
        btnPage.setOnClickListener(onClick);
        btnMainActivity.setOnClickListener(onClick);
        btnSettings.setOnClickListener(onClick);
    }

    private class OnClick implements OnClickListener{

        @Override
        public void onClick(View v){
            Intent intent = null;

            switch (v.getId()){
                case R.id.page:
                    //启用Toast
                    Toast.makeText(getContext(),"You click page", Toast.LENGTH_SHORT).show();
                    //跳转到Page界面
                    intent = new Intent(getContext(),Page.class);
                    break;
                case R.id.translate:
                    //启用Toast
                    Toast.makeText(getContext(),"You click translate", Toast.LENGTH_SHORT).show();
                    //跳转到Translate界面
                    intent = new Intent(getContext(),MainActivity.class);
                    break;
                case R.id.settings:
                    //启用Toast
                    Toast.makeText(getContext(),"You click settings", Toast.LENGTH_SHORT).show();
                    //跳转到Settings界面
                    intent = new Intent(getContext(),Settings.class);
                    break;
            }

            getContext().startActivity(intent);
        }
    }
}

