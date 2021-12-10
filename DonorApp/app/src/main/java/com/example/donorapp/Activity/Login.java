package com.example.donorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.donorapp.R;

public class Login extends AppCompatActivity {
    LinearLayout ll_registration;
    Button ll_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ll_registration = findViewById(R.id.registration);
        ll_login = findViewById(R.id.btn_login);
        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Login.this,Dashboard.class));
            }
        });
        ll_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Login.this, Registartion.class));
            }
        });
    }
}