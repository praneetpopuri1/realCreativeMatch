package com.example.creativematch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.creativematch.firebase.FirebaseHelper;
import com.example.creativematch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignIn extends AppCompatActivity {

    public static FirebaseHelper firebaseHelper;
    public final String TAG = "Denna";
    private EditText emailET, passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseHelper = new FirebaseHelper();
    }




    public void signIn(View v) {
        // Note we don't care what they entered for name here
        // it could be blank
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        // Get user data
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        Log.i(TAG,  email + " " + password);
        // verify all user data is entered
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }

        // verify password is at least 6 char long (otherwise firebase will deny)
        else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        }
        else {

            // code to sign in user
            firebaseHelper.getmAuth().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Log.i(TAG, email + " logged in");



                                Intent intent = new Intent(SignIn.this, MainPage.class);
                                startActivity(intent);
                            }
                            else{
                                Log.d(TAG, "sign In failed for " + email + " " + password);
                            }
                        }
                    });


        }
    }


}