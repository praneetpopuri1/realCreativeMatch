package com.example.creativematch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goSignUp(View v){
        Intent intent = new Intent(MainActivity.this,signUp.class);
        startActivity(intent);
    }
    public void goSignIn(View v){
        Intent intent = new Intent(MainActivity.this,signIn.class);
        startActivity(intent);
    }
}