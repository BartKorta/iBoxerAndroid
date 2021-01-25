package com.example.iboxer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Game";
    private SensorManager sensorManager;
    private float highestVal=0f;
    private List<Float> listscores;
    Handler handler;
    Sensor accelerometer;
    Scores score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("Scores").child(uid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        listscores = new ArrayList<Float>();
        handler = new Handler();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final Intent i = new Intent(this, Menu.class);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(Game.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        final TextView punch = (TextView)findViewById(R.id.punchText);
        final TextView scores = (TextView)findViewById(R.id.scoresText);
        final TextView points = (TextView)findViewById(R.id.sensorsText);
        punch.setText("Punch!");
        scores.setText("");
        points.setText("");
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
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void run() {
                        Collections.sort(listscores,Collections.reverseOrder());
                        if(listscores.size()>20) {
                            for(int i=0; i<20; i++)
                                highestVal+=listscores.get(i);
                        }
                        else highestVal=0f;
                        int pts = (int)((highestVal/20)*100);
                        sensorManager.unregisterListener(Game.this,accelerometer);
                        punch.setText("");
                        scores.setText("Scores:");
                        points.setText(""+pts);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        score=new Scores(uid,pts, ZonedDateTime.now().format(formatter));
                        DataRef.push().setValue(score);
                        CountDownTimer timer2 = new CountDownTimer(5000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                startActivity(i);
                                finish();
                            }
                        }.start();

                    }
                });
            };
        }.start();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        listscores.add(Math.abs(sensorEvent.values[0]));
    }
}