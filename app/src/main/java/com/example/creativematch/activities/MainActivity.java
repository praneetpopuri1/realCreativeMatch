package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.creativematch.R;
import com.github.javafaker.Faker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addUsers();

    }
    /*
    //https://stackoverflow.com/questions/5025651/java-randomly-generate-distinct-names I got this dependency from this link
    public void addUsers (){
        Faker faker = new Faker();
        for (int i = 0; i < 200; i++) {
            String name = faker.name().fullName();
        }


    }
    
     */

    public void goSignUp(View v){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }
    public void goSignIn(View v){
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        startActivity(intent);
    }
}