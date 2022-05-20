package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.example.creativematch.OtherUser;
import com.example.creativematch.databinding.ActivityBioPageBinding;
import com.example.creativematch.databinding.ActivityUsersBinding;

import java.util.ArrayList;

public class BioPage extends AppCompatActivity {

    private ActivityBioPageBinding binding;
    private OtherUser otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBioPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        otherUser = (OtherUser) getIntent().getSerializableExtra("OtherUser");
        binding.NameID.setText(otherUser.getName());
        binding.imageView.setImageBitmap(getUserImage(otherUser.getImage()));
        setListeners();
    }

    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void setListeners(){
        binding.Like.setOnClickListener(v->Like());
        binding.Dislike.setOnClickListener(v->Dislike());
    }

    private void Like(){
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("OtherUser", otherUser);
        startActivity(intent);
        finish();
    }

    private void Dislike(){
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
    }
}