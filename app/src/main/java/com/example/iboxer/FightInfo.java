package com.example.iboxer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

public class FightInfo extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_info);
        handler = new Handler();
        final Intent i = new Intent(this, Game.class);
        CountDownTimer timer = new CountDownTimer(5000, 1000) //10seceonds Timer
        {
            @Override
            public void onTick(long l)
            {

            }

            @Override

            public void onFinish()
            {
                handler.post(new Runnable() {
                    public void run() {
                        startActivity(i);
                        finish();
                    }
                });
            };
        }.start();
    }
}