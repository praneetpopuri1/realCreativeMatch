package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.creativematch.R;
import com.example.creativematch.firebase.FirebaseHelper;
import com.example.creativematch.OtherUser;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

public class ListView extends AppCompatActivity {
    public static FirebaseHelper firebaseHelper;
    public final String TAG = "Denna";
    ArrayList<OtherUser> otherUsers = new ArrayList<OtherUser>();
    public int j = 80;
    public int r = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        firebaseHelper = new FirebaseHelper();
        ArrayList<OtherUser> fOtherUsers = new ArrayList<OtherUser>();
        //fOtherUsers =
        getRandomUsers();
        //Log.d(TAG, "in onCreate the other users are: " + fOtherUsers.toString());
    }


    public //ArrayList<OtherUser
    void getRandomUsers() {

        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
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
                            topOffUsers(personality.get(0), personality.get(1), personality.get(2), 80);




                            Log.i(TAG, "Inside getRandomUsers, onCallBack the length of the array: " + otherUsers.size());


                        }
                    } );

            }
        });

        /*
        ArrayList<OtherUser> samePUser = new ArrayList<OtherUser>();
        ArrayList<OtherUser> diffPUser = new ArrayList<OtherUser>();
        int i = 0;
                for(OtherUser users :anotherUsers)

        {
            String originalProfession = firebaseHelper.getProfession(new FirebaseHelper.FirestoreCallbackPro() {
                @Override
                public void onCallback(String[] profession) {
                    Log.i(TAG, "Inside getRandomUsers, onCallBack" + profession.toString());
                }
            }, user.getUid());
            if (originalProfession.equals(users.getProfession())) {
                samePUser.add(users);
            } else {
                diffPUser.add(users);
            }
            i++;
            if (i == 20) {
                break;
            }
        }

        Random rand = new Random(); //instance of random class
        int upperbound = 100;
        //generate random values from 0-99
        int percentSamePUser = (int) (1.3 * 100 * (samePUser.size() / 20));
        int percentDiffPUser = 100 - percentSamePUser;
        int k = 0;
        int l = 0;
            for(
        int j = 0;
        j< 10;j++)

        {
            int int_random = rand.nextInt(upperbound + 1);
            if (k < samePUser.size()) {
                if (int_random > percentDiffPUser) {

                    otherUsers.add(samePUser.get(k));
                    k++;
                }
            } else {
                if (l < diffPUser.size()) {
                    otherUsers.add(diffPUser.get(l));
                    l++;
                }
            }
        }


            return otherUsers;

         */
    }

    public void topOffUsers(int agreeableness, int openness, int conscientiousness, int amountAft){
        if (r < 20) {
            firebaseHelper.paginateQueery(agreeableness, openness, conscientiousness, amountAft,
                    new FirebaseHelper.FirestoreCallbackOU() {
                        @Override
                        public void onCallback(ArrayList<OtherUser> listUsers) {
                            otherUsers = listUsers;
                            Log.i(TAG, "Inside getRandomUsers, paginateQueery retrieves an array with length of: " + otherUsers.size() + " these are the users themselves " + otherUsers.toString());

                            if (otherUsers.size() < 20) {
                                j += 20;
                                r++;
                                topOffUsers(agreeableness, openness, conscientiousness, j);
                            }
                        }

                    });
        }

    }
        public interface FirestoreCallbackP {
            void onCallback (ArrayList<Integer> personality);
        }

    public interface FirestoreCallbackOU {
        void onCallback (ArrayList<OtherUser> listUsers);
    }


    }
