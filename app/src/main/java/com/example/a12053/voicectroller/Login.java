package com.example.a12053.voicectroller;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        btnLogin = findViewById(R.id.login);
        et = findViewById(R.id.user);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                name = et.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(Login.this, "Name is empty",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this,"Successful Login",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Settings.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }
}
