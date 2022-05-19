package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.creativematch.R;
import com.example.creativematch.firebase.FirebaseHelper;
import com.example.creativematch.OtherUser;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

public class ListView extends AppCompatActivity {
    public static FirebaseHelper firebaseHelper;
    public static SwipeLeftOrRightOne swipeLeftOrRightOne;
    public final String TAG = "Denna";
    public static ArrayList<OtherUser> others= new ArrayList<OtherUser>();
    ArrayList<OtherUser> otherUsers = new ArrayList<OtherUser>();
    public int j = 80;
    public int r = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
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
                                                                otherUsers =  swipeLeftOrRightOne.fillUsers(profession.get(0), otherUsers);

                                                            }
                                                        });

                                                    }
                                                    else{

                                                        firebaseHelper.getProfession(user.getUid(), new FirebaseHelper.FirestoreCallbackPro() {
                                                            @Override
                                                            public void onCallback(ArrayList<String> profession) {
                                                                otherUsers =  swipeLeftOrRightOne.fillUsers(profession.get(0), otherUsers);
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

                                            otherUsers =  swipeLeftOrRightOne.fillUsers(profession.get(0), otherUsers);

                                        }
                                    });

                            }
                            }
                        } );

            }
        });


        Log.d(TAG, "in onCreate the other users are: " + otherUsers.toString());

        sendArray(otherUsers);
    }

    public void sendArray(ArrayList<OtherUser> otherUsers){
        Intent intent=new Intent(this, UsersActivity.class);
        intent.putExtra("Users", otherUsers);
        startActivity(intent);
    }

 /*
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
                                new idekBro() {
                                    @Override
                                    public void topOffUsers(int agreeableness, int openness, int conscientiousness, int amountAft) {
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
                                };
                                ArrayList<OtherUser> samePUser = new ArrayList<OtherUser>();
                                ArrayList<OtherUser> diffPUser = new ArrayList<OtherUser>();

                                firebaseHelper.getProfession(user.getUid(), new FirebaseHelper.FirestoreCallbackPro() {
                                    @Override
                                    public void onCallback(ArrayList<String> profession) {
                                        int t = 0;
                                        for (OtherUser users : otherUsers) {

                                            String originalProfession = profession.get(0);
                                            if (originalProfession.equals(users.getProfession())) {
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
                                        Log.i(TAG, "the amount of users in samePUser are: " + samePUser.size() + " the users in samePUser are: " + samePUser.toString());
                                        Log.i(TAG, "the amount of users in diffPUser are: " + diffPUser.size() + " the users in diffPUser are: " + diffPUser.toString());
                                        Log.i(TAG, "the percentage chance that a samePUser is picked is: " + percentSamePUser);
                                        Log.i(TAG, "the percentage chance that a diffPUser is picked is: " + percentDiffPUser);


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
                                        Log.i(TAG, "the amount of users at the end are: " + otherUsers.size() + " the users at the end are: " + otherUsers.toString());

                                    }
                                });


                            }
                        });

            }
        });
    }

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



        public interface FirestoreCallbackP {
            void onCallback (ArrayList<Integer> personality);
        }

    public interface FirestoreCallbackOU {
        void onCallback (ArrayList<OtherUser> listUsers);
    }
    public interface FirestoreCallbackPro {
        void onCallback (ArrayList<String> profession);
    }
    /*
    public interface idekBro {
        void topOffUsers (int agreeableness, int openness,
                           int conscientiousness, int amountAft);
    }

     */


    }
