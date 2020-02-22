package com.example.a12053.voicectroller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class More extends AppCompatActivity {
    private GridLayout mGridLayout;
    private int columnCount; //列数
    private int screenWidth; //屏幕宽度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);

        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

        mGridLayout = findViewById(R.id.gridlayout);
        columnCount = mGridLayout.getColumnCount();
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        for (int i = 0; i < mGridLayout.getChildCount(); i++) {
            Button button = (Button) mGridLayout.getChildAt(i);
            button.setWidth(screenWidth / columnCount);
        }
    }
}
