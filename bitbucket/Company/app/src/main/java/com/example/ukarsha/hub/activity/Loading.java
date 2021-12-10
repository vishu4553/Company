package com.example.ukarsha.hub.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ukarsha.hub.R;
import com.wang.avi.AVLoadingIndicatorView;

import net.frakbot.jumpingbeans.JumpingBeans;

public class Loading extends AppCompatActivity {
    AVLoadingIndicatorView loading;
    private static int splashTimeOut = 1600;
    JumpingBeans jumpingBeans1;
    TextView textViewLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        loading = findViewById(R.id.avi);
        startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Loading.this, HomeScreen.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        textViewLoading = (TextView) findViewById(R.id.loading);
        jumpingBeans1 = JumpingBeans.with(textViewLoading)
                .makeTextJump(0, textViewLoading.getText().toString().indexOf(' '))
                .setIsWave(true)
                .setLoopDuration(1000)  // ms
                .build();

    }

    void startAnim(){
        loading.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        loading.hide();
        // or avi.smoothToHide();
    }
}
