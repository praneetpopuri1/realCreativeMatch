package com.example.creativematch;

import androidx.annotation.NonNull;

import com.example.creativematch.models.User;

import java.io.Serializable;

public class OtherUser implements Serializable {

    String profession;
    String name;
    String email;
    String description;
    String image;
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
                ", email" + email +'\'' +
                ", agreeableness=" + agreeableness +
                ", openness=" + openness +
                ", conscientiousness=" + conscientiousness +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
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

    public String getEmail(){
        return email;
    }

    public OtherUser(String profession, String name) {
        this.profession = profession;
        this.name = name;


    }
    public OtherUser(String profession, String name, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.email = email;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;



    }
    public OtherUser(String image, String description,String profession, String name, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.email = email;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.image = image;
        this.description = description;


    }
    public OtherUser(String image, String description,String profession, String name, String token, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.email = email;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.image = image;
        this.description = description;
        this.token = token;

    }


}
