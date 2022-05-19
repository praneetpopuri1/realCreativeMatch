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
        Intent intent = new Intent(this, ChatActivity.class);

    }

    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}