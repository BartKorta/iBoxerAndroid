package com.example.iboxer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment sfrag = null;
            boolean fight = false;
            switch(menuItem.getItemId())
            {
                case R.id.bot_fight:
                    sfrag = new FightFragment();
                    fight = true;
                    break;
                case R.id.bot_ranking:
                    sfrag = new RankingFragment();
                    break;
                case R.id.bot_me:
                    sfrag = new MeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_menu,sfrag).commit();

            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        /*
        ImageView imageView =(ImageView)findViewById(R.id.glovesanim);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageView.startAnimation(pulse); */

        BottomNavigationView bnv = findViewById(R.id.bot_nav);
        bnv.setOnNavigationItemSelectedListener(navlistener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_menu,new FightFragment()).commit();

    }
}