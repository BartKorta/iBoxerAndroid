package com.example.iboxer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 6000;
    // Variables
    Animation topAnim, botAnim;
    ImageView img;
    TextView logo, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animations
        topAnim= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnim= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img = findViewById(R.id.logoimg);
        logo = findViewById(R.id.logotext);
        desc = findViewById(R.id.desctext);

        img.setAnimation(topAnim);
        logo.setAnimation(botAnim);
        desc.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                Pair[] pairs = new Pair[3];
                pairs[0]= new Pair<View,String>(img, "logoimg");
                pairs[1]= new Pair<View,String>(logo,"logotxt");
                pairs[2]= new Pair<View,String>(desc,"desctxt");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent, opt.toBundle());
                }
                else startActivity(intent);
                finish();
            }
            },SPLASH_SCREEN);
    }
}