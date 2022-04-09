package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.creativematch.OtherUser;
import com.example.creativematch.R;
import com.example.creativematch.firebase.FirebaseHelper;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseHelper = new FirebaseHelper();
        //addUsers();

    }

    //https://stackoverflow.com/questions/5025651/java-randomly-generate-distinct-names I got this dependency from this link
    public void addUsers () {
        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        Faker faker = new Faker();
        ArrayList<OtherUser> users = new ArrayList<OtherUser>();
        for (int i = 0; i < 200; i++){
            String name = faker.name().fullName();
            int randAgreeableness = rand.nextInt(upperbound + 1);
            int randOpenness = rand.nextInt(upperbound + 1);
            int randConscientiousness = rand.nextInt(upperbound + 1);
            String profession;
            int int_random = rand.nextInt(2);
            if (int_random == 0) {
                profession = "farmer";
            } else {
                profession = "computer programmer";
            }
            OtherUser user = new OtherUser(profession, name, randAgreeableness, randOpenness, randConscientiousness);
            users.add(user);



        }
        firebaseHelper.addALotOfUsers(users);


    }
    


    public void goSignUp(View v){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }
    public void goSignIn(View v){
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        startActivity(intent);
    }
}