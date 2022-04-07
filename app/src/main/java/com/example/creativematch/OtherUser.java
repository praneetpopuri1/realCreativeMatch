package com.example.creativematch;

public class OtherUser {

    String profession;
    String name;
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

    @Override
    public String toString() {
        return "OtherUser{" +
                "profession='" + profession + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
