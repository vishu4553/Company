package com.example.donorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.donorapp.R;

public class Certificate extends AppCompatActivity {
    ImageView imgback;
    CardView cd_Certificate;
    LinearLayout llslide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        imgback = findViewById(R.id.arrowBack);
        cd_Certificate = findViewById(R.id.cardCertificate);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Certificate.this, Dashboard.class));
            }
        });
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);

        cd_Certificate.setAnimation(slide_up);
    }
}