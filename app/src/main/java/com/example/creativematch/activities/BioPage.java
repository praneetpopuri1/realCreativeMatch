package com.example.creativematch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.creativematch.OtherUser;
import com.example.creativematch.R;
import com.example.creativematch.databinding.ActivityBioPageBinding;

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
        binding.UserBioID.setText(otherUser.getDescription());
        Bitmap bitmap = getUserImage(otherUser.getImage());
        if(bitmap == null){
            binding.imageView.setImageResource(R.drawable.featured);
        }
        else{
            binding.imageView.setImageBitmap(bitmap);
        }
        setListeners();
    }

    private Bitmap getUserImage(String encodedImage){
        if( encodedImage == null) {
            return null;
        }
        else{
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }

    private void setListeners(){
        binding.Like.setOnClickListener(v->Like());
        binding.Dislike.setOnClickListener(v->Dislike());
    }

    private void Like(){
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("otherUser", otherUser);
        startActivity(intent);
        finish();
    }

    private void Dislike(){
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
        finish();
    }
}