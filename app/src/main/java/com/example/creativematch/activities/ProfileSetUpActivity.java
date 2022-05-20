package com.example.creativematch.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.creativematch.R;
import com.example.creativematch.Utilities.Constants;
import com.example.creativematch.Utilities.PreferenceManager;
import com.example.creativematch.databinding.ActivityProfileSetUpBinding;
import com.github.javafaker.Bool;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.creativematch.firebase.FirebaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileSetUpActivity extends AppCompatActivity {

    private ActivityProfileSetUpBinding binding;
    private PreferenceManager preferenceManager;
    private String encodedImage;
    public static FirebaseHelper firebaseHelper;
    public final String TAG = "Denna";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSetUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseHelper = new FirebaseHelper();
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }

    private void setListeners() {// vid 3 0:58
        binding.submitButton.setOnClickListener(v->{
            if (isValidInformation()){
                confirmAccount();
            }
        });
        binding.layoutImage.setOnClickListener(v_-> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void confirmAccount(){
        loading(true);
        FirebaseUser usersAuth = firebaseHelper.getmAuth().getCurrentUser();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_DESCRIPTION, binding.Description.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection("users").document(usersAuth.getUid())
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        preferenceManager.putBoolean(Constants.KEY_IS_SINGED_IN, true);
                        preferenceManager.putString(Constants.Key_USER_ID, usersAuth.getUid());
                        preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
                        Log.d(TAG, "sucessfully put user information into account");
                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error adding user account");
                    }
                });
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
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imageProfile.setImageBitmap(bitmap);
                        binding.textAddImage.setVisibility(View.GONE);
                        encodedImage = encodeImage(bitmap);
                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }
    );

    private Boolean isValidInformation(){
        if(encodedImage==null){
            showToast("Select profile image");
            return false;
        }
        else if (binding.Description.getText().toString().trim().isEmpty()){
            showToast("Enter a description of yourself");
            return false;
        }
        else if (binding.workText.getText().toString().trim().isEmpty()){
            showToast("Enter links to some of your work");
            return false;
        }

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