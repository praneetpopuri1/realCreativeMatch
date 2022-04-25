package com.example.creativematch.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.creativematch.R;
import com.example.creativematch.databinding.ActivityProfileSetUpBinding;
import com.github.javafaker.Bool;

import java.io.ByteArrayOutputStream;

public class ProfileSetUpActivity extends AppCompatActivity {

    private ActivityProfileSetUpBinding binding;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSetUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {// vid 3 0:58
        binding.submitButton.setOnClickListener(v->{
            if (isValidImage()){
                confirmAccount();
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void confirmAccount(){

    }

    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight()*previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getData() != null){
                    Uri imageUri = result.getData().getData();
                    try{
                        
                    }
                }
            }
    )

    private Boolean isValidImage(){
        if(encodedImage==null){
            showToast("Select profile image");
            return false;
        }
        // ask praneet if we want to require them to share their work
        // and everything else vid 3 2:52
        return true;
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.submitButton.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else{
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.submitButton.setVisibility(View.VISIBLE);
        }
    }
}