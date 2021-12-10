package com.example.ukarsha.hub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.example.ukarsha.hub.R;

import net.frakbot.jumpingbeans.JumpingBeans;

public class Welcome extends AppCompatActivity {
    TextView textViewWel;
    JustifiedTextView text;
    ImageButton imageButtonGo;
    TextView textViewRead;
    JumpingBeans jumpingBeans1;
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        text = findViewById(R.id.txt1);
        text.setText(R.string.welcome);
        textViewWel = findViewById(R.id.welcome);
        //textViewWel.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        textViewWel.animate().x(40)
                .setDuration(5000)
                .setStartDelay(1000);

        imageButtonGo = findViewById(R.id.go);
        imageButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this, Login.class));
                finish();
            }
        });

        textViewRead = findViewById(R.id.readMore);
        jumpingBeans1 = JumpingBeans.with(textViewRead)
                .appendJumpingDots()
                .build();

        textViewRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this, About1.class
                ));
            }
        });

        buttonStart = findViewById(R.id.go1);
        buttonStart.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
    }

}
