package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.creativematch.R;
import com.example.creativematch.firebase.FirebaseHelper;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

public class ListView extends AppCompatActivity {
    public static FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        firebaseHelper = new FirebaseHelper();
    }



    public ArrayList<otherUser> getRandomUsers(){
        ArrayList<otherUser> otherUsers = new ArrayList<otherUser>();
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
        int[] personality = firebaseHelper.getPersonality(user.getUid());
        ArrayList<otherUser> anotherUsers = firebaseHelper.queerySearch(personality[0],personality[1] , personality[2]);
        ArrayList<otherUser> samePUser = new ArrayList<otherUser>();
        ArrayList<otherUser> diffPUser = new ArrayList<otherUser>();
        int i = 0;
            for (otherUser users : anotherUsers){
                String originalProfession = firebaseHelper.getProfession(user.getUid());
                if(originalProfession.equals(users.profession)){
                    samePUser.add(users);
                }
                else{
                    diffPUser.add(users);
                }
                i++;
                if (i == 20){
                    break;
                }
            }

        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        //generate random values from 0-99
        int percentSamePUser= (int) (1.3 * 100 * (samePUser.size() / 20));
        int percentDiffPUser= 100-percentSamePUser;
        int k = 0;
        int l = 0;
        for (int j = 0; j < 10; j++) {
            int int_random = rand.nextInt(upperbound + 1);
            if (k < samePUser.size()) {
                if (int_random > percentDiffPUser) {

                    otherUsers.add(samePUser.get(k));
                    k++;
                }
            }
            else{
                if (k < samePUser.size()) {
                    otherUsers.add(diffPUser.get(l));
                    l++;
                }
            }
        }


        return otherUsers;
    }

}