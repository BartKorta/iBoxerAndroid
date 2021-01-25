package com.example.iboxer;

public class RowData implements Comparable<RowData>{
    public String nickname;
    public String country;
    public int points;
    public String userId;

    public RowData(String nickname, String country, int points, String userId) {
        this.nickname = nickname;
        this.country = country;
        this.points = points;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStrPoints(){
        return ""+points;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(RowData o) {
        if (this.points < o.getPoints()) return 1;
        else return -1;
    }
}
