package com.example.creativematch;

import androidx.annotation.NonNull;

public class OtherUser {

    String profession;
    String name;
    int agreeableness;
    int openness;
    int conscientiousness;

    public int getAgreeableness() {
        return agreeableness;
    }

    @NonNull
    @Override
    public String toString() {
        return "OtherUser{" +
                "profession='" + profession + '\'' +
                ", name='" + name + '\'' +
                ", agreeableness=" + agreeableness +
                ", openness=" + openness +
                ", conscientiousness=" + conscientiousness +
                '}';
    }

    public int getOpenness() {
        return openness;
    }

    public int getConscientiousness() {
        return conscientiousness;
    }

    public String getProfession() {
        return profession;
    }

    public String getName() {
        return name;
    }
    public OtherUser(String profession, String name) {
        this.profession = profession;
        this.name = name;


    }
    public OtherUser(String profession, String name, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;



    }

}
