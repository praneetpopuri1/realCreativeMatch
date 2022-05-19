package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.creativematch.OtherUser;
import com.example.creativematch.Utilities.Constants;
import com.example.creativematch.Utilities.PreferenceManager;
import com.example.creativematch.adapters.UsersAdapter;
import com.example.creativematch.databinding.ActivityUsersBinding;
import com.example.creativematch.listeners.UserListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;
    private ArrayList<OtherUser> users = new ArrayList<OtherUser>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        users = (ArrayList<OtherUser>) getIntent().getSerializableExtra("Users");

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        setListView(users);
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v-> onBackPressed());
    }

    private void setListView(ArrayList<OtherUser> users){
        UsersAdapter usersAdapter = new UsersAdapter(users, this);
        binding.usersRecycleView.setAdapter(usersAdapter);
        binding.usersRecycleView.setVisibility(View.VISIBLE);
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

        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("OtherUser", user);
        startActivity(intent);
        finish();
    }
}