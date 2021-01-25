package com.example.iboxer;

import android.widget.ImageView;

public class User {

    public String nickname, email, password, country;
    public User()
    {

    }
    public User(String nickname, String email, String password, String country)
    {
        this.nickname=nickname;
        this.email=email;
        this.password=password;
        this.country=country;
    }
}

