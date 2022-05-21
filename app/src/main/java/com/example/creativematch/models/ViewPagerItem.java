package com.example.creativematch.models;

import android.graphics.drawable.Drawable;

public class ViewPagerItem {
    public Drawable imageID;
    public String name;
    public String profession;
    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "ViewPagerItem{" +
                ", name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public ViewPagerItem(Drawable imageID, String name, String profession) {
        this.imageID = imageID;
        this.name = name;
        this.profession = profession;
        isChecked = false;
    }

}
