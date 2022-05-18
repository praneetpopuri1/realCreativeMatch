package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.creativematch.OtherUser;
import com.example.creativematch.R;
import com.example.creativematch.firebase.FirebaseHelper;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

public class SwipeLeftOrRightOne extends AppCompatActivity {

    public static FirebaseHelper firebaseHelper;
    public final String TAG = "Denna";
    ArrayList<OtherUser> otherUsers = new ArrayList<OtherUser>();
    public int j = 80;
    public int r = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_left_or_right_one);


        firebaseHelper = new FirebaseHelper();
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
        //getRandomUsers();

        firebaseHelper.getPersonality(user.getUid(), new FirebaseHelper.FirestoreCallbackP() {
            @Override
            public void onCallback(ArrayList<Integer> personality) {
                Log.i(TAG, "Inside getRandomUsers, onCallBack " + personality.toString());

                firebaseHelper.queerySearch(personality.get(0), personality.get(1), personality.get(2),
                        new FirebaseHelper.FirestoreCallbackOU() {
                            @Override
                            public void onCallback(ArrayList<OtherUser> listUsers) {

                                Log.i(TAG, "Inside getRandomUsers, onCallBack the length of the array: " + listUsers.size() + " these are the users themselves " + listUsers.toString());
                                otherUsers.addAll(listUsers);
                                if(otherUsers.size() < 20) {
                                    firebaseHelper.paginateQueery(personality.get(0), personality.get(1), personality.get(2), j,
                                            new FirebaseHelper.FirestoreCallbackOU() {
                                                @Override
                                                public void onCallback(ArrayList<OtherUser> listUsers) {
                                                    otherUsers = listUsers;
                                                    Log.i(TAG, "Inside getRandomUsers, paginateQueery retrieves an array with length of: " + otherUsers.size() + " these are the users themselves " + otherUsers.toString());

                                                    if (otherUsers.size() < 20) {
                                                        j += 20;
                                                        r++;
                                                        firebaseHelper.getProfession(user.getUid(), new FirebaseHelper.FirestoreCallbackPro() {
                                                            @Override
                                                            public void onCallback(ArrayList<String> profession) {
                                                                otherUsers =  fillUsers(profession.get(0), otherUsers);
                                                            }
                                                        });

                                                    }
                                                    else{

                                                        firebaseHelper.getProfession(user.getUid(), new FirebaseHelper.FirestoreCallbackPro() {
                                                            @Override
                                                            public void onCallback(ArrayList<String> profession) {
                                                                otherUsers =  fillUsers(profession.get(0), otherUsers);


                                                            }
                                                        });

                                                    }
                                                }

                                            });
                                }
                                else{


                                    firebaseHelper.getProfession(user.getUid(), new FirebaseHelper.FirestoreCallbackPro() {
                                        @Override
                                        public void onCallback(ArrayList<String> profession) {

                                            otherUsers = fillUsers(profession.get(0),otherUsers);



                                        }
                                    });

                                }
                            }
                        } );

            }
        });
    }

    public void userCard()
    {

    }

    public static ArrayList<OtherUser> fillUsers(String profession,ArrayList<OtherUser> otherUsers)
    {
        int t = 0;
        ArrayList<OtherUser> samePUser = new ArrayList<OtherUser>();
        ArrayList<OtherUser> diffPUser = new ArrayList<OtherUser>();
        for (OtherUser users : otherUsers) {

            if (profession.equals(users.getProfession())) {
                samePUser.add(users);
            } else {
                diffPUser.add(users);
            }
            t++;
            if (t == 20) {
                break;
            }
        }

        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        //generate random values from 0-99
        int percentSamePUser = (int) (1.3 * 100 * (((double) (samePUser.size()) / otherUsers.size())));
        int percentDiffPUser = 100 - percentSamePUser;
        int k = 0;
        int l = 0;
        otherUsers.clear();


        for (int j = 0; j < 10; j++) {
            int int_random = rand.nextInt(upperbound + 1);
            if (int_random > percentDiffPUser && k < samePUser.size()) {

                otherUsers.add(samePUser.get(k));
                k++;
            }
            else {
                otherUsers.add(diffPUser.get(l));
                l++;
            }
        }
        return otherUsers;

    }

}