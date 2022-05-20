package com.example.creativematch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.example.creativematch.R;
import com.example.creativematch.databinding.ActivityBioPageBinding;
import com.example.creativematch.databinding.ActivitySettingsBinding;
import com.example.creativematch.firebase.FirebaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    boolean isChecked = false;
    private FirebaseFirestore db;
    public final String TAG = "Denna";
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = FirebaseFirestore.getInstance();
        firebaseHelper = new FirebaseHelper();

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        isChecked = ((CheckBox) view).isChecked();
    }

    public void onButtonClicked(View view) {
        // Is the view now checked?
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
        Map<String, Object> swipeOrListMap = new HashMap<>();
        swipeOrListMap.put("Preference", isChecked);
        db.collection("users").document(user.getUid())
                .set(swipeOrListMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, " Preference is saved user account added");
                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error setting preference");
                    }
                });
    }


}