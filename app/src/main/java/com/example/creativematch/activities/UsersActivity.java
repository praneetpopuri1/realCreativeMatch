package com.example.creativematch.activities;

import static com.example.creativematch.activities.ListView.swipeLeftOrRightOne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.creativematch.OtherUser;
import com.example.creativematch.R;
import com.example.creativematch.Utilities.PreferenceManager;
import com.example.creativematch.adapters.UsersAdapter;
import com.example.creativematch.databinding.ActivityUsersBinding;
import com.example.creativematch.firebase.FirebaseHelper;
import com.example.creativematch.listeners.UserListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;
    private ArrayList<OtherUser> users = new ArrayList<OtherUser>();
    public final String TAG = "Denna";
    private FirebaseFirestore db;
    public int j = 80;
    public int r = 0;
    ArrayList<OtherUser> otherUsers = new ArrayList<OtherUser>();
    public FirebaseHelper firebaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseHelper = new FirebaseHelper();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
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
                                Button button = (Button) findViewById(R.id.submitButtonRecyclerView);
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
                                                                setRecyclerView(otherUsers);
                                                                loading(false);
                                                                button.setVisibility(View.VISIBLE);
                                                                submitButton(button, otherUsers ,user);
                                                            }
                                                        });

                                                    }
                                                    else{

                                                        firebaseHelper.getProfession(user.getUid(), new FirebaseHelper.FirestoreCallbackPro() {
                                                            @Override
                                                            public void onCallback(ArrayList<String> profession) {
                                                                otherUsers =  swipeLeftOrRightOne.fillUsers(profession.get(0), otherUsers);
                                                                setRecyclerView(otherUsers);
                                                                loading(false);
                                                                button.setVisibility(View.VISIBLE);
                                                                submitButton(button, otherUsers ,user);
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
                                            setRecyclerView(otherUsers);
                                            loading(false);
                                            button.setVisibility(View.VISIBLE);
                                            submitButton(button, otherUsers ,user);
                                        }
                                    });

                                }
                            }
                        } );

            }
        });



    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v-> onBackPressed());
    }

    private void setRecyclerView(ArrayList<OtherUser> users){
        UsersAdapter usersAdapter = new UsersAdapter(users, this);
        binding.usersRecycleView.setAdapter(usersAdapter);
        binding.usersRecycleView.setVisibility(View.VISIBLE);
    }

    public void submitButton(@NonNull Button button, ArrayList<OtherUser> otherUsers, FirebaseUser user) {

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<OtherUser> finalScreening = new ArrayList<>();
                for (int i = 0; i < otherUsers.size(); i++) {
                    if (otherUsers.get(i).isChecked()) {
                        finalScreening.add(otherUsers.get(i));
                    }
                }
                Log.d(TAG, "onClick: user's uid = " + user.getUid());
                String [] UIDs = new String[finalScreening.size()];
                for (int i = 0; i < finalScreening.size(); i++) {
                    if (finalScreening.get(i).getUID() == null){
                        finalScreening.get(i).setUID("123456789");
                    }
                    UIDs[i] = finalScreening.get(i).getUID();
                }
                Log.d(TAG, "Final Screening UIDs are: " + UIDs.toString());
                List<String> UIDList = Arrays.asList(UIDs);

                db.collection("users").document(user.getUid()).update("uidList",  UIDList)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                nextActivity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });




            }
        });
    }
    public void nextActivity(){
        Intent intent=new Intent(this, MainPage.class);
        startActivity(intent);
    }
    /*
    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task->{
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.Key_USER_ID);
                    if(task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if(users.size()>0){
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            binding.usersRecycleView.setAdapter(usersAdapter);
                            binding.usersRecycleView.setVisibility(View.VISIBLE);
                        } else{
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }
    */
    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        } else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onUserClicked(OtherUser user) {


        Intent intent = new Intent(getApplicationContext(), BioPage.class);
        intent.putExtra("OtherUser", user);
        startActivity(intent);
        finish();
    }
}