package com.example.ukarsha.hub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.LAST_SIGNIN_TIME;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.PREF_USER_LOGE_IN;

public class SplashScreen extends AppCompatActivity {
    TextView textView1, textView2;
    private static int splashTimeOut = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Welcome.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        textView1 = findViewById(R.id.txt);
        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.shake);
        textView1.startAnimation(animation);

        textView2 = findViewById(R.id.txt2);
        textView2.animate().x(100)
                .setDuration(5000)
                .setStartDelay(1000);

        //navigateUserToHomeOrLogin();
    }

    private void navigateUserToHomeOrLogin() {

        if (MyApplication.getInstance().getBooleanFromSharedPreference(PREF_USER_LOGE_IN)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date strDate = sdf.parse(MyApplication.getInstance().getFromSharedPreference(LAST_SIGNIN_TIME));
                Calendar c = Calendar.getInstance();
                Calendar TodaysDate = Calendar.getInstance();
                c.setTime(strDate); // Now use today date.
                c.add(Calendar.DATE, 14);
                if (TodaysDate.getTime().after(c.getTime())) {
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                } else if (TodaysDate.getTime().equals(strDate)) {
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            startActivity(new Intent(SplashScreen.this, Login.class));
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
