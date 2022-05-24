package com.example.creativematch;

import java.io.Serializable;

public class OtherUser implements Serializable {

    String profession;



    String name;
    String email;
    String description;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    String image;
    String token;
    String UID;
    boolean isChecked = false;

    public OtherUser(String profession, String name, String email, String description, String image, String token, String UID, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.email = email;
        this.description = description;
        this.image = image;
        this.token = token;
        this.UID = UID;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
    }
    public OtherUser(String profession, String name, String image, String token, String UID, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.image = image;
        this.token = token;
        this.UID = UID;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
    }

    public OtherUser(){
        this.profession="";
        this.name = "";
        this.image = "";
        this.token = "";
        this.UID = "";
    }

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

    @Override
    public String toString() {
        return "OtherUser{" +
                "profession='" + profession + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", token='" + token + '\'' +
                ", UID='" + UID + '\'' +
                ", isChecked=" + isChecked +
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
    public String getUID() {
        return UID;
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

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setAgreeableness(int agreeableness) {
        this.agreeableness = agreeableness;
    }

    public void setOpenness(int openness) {
        this.openness = openness;
    }

    public void setConscientiousness(int conscientiousness) {
        this.conscientiousness = conscientiousness;
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
    public OtherUser(String image, String description,String profession, String name, int agreeableness, int openness, int conscientiousness) {
        this.profession = profession;
        this.name = name;
        this.agreeableness = agreeableness;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.image = image;
        this.description = description;


    }


}
