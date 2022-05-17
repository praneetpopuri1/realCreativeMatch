package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.creativematch.R;
import com.example.creativematch.Utilities.Constants;
import com.example.creativematch.databinding.ActivityChatBinding;
import com.example.creativematch.models.User;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private com.example.creativematch.models.User receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
    }

    private void loadReceiverDetails(){
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receiverUser.name);
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v->onBackPressed());
    }
}