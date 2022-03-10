package com.example.creativematch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class signUp extends AppCompatActivity {

    public static FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseHelper = new FirebaseHelper();

    }
    public void signIn(View v) {
        // Note we don't care what they entered for name here
        // it could be blank

        // Get user data
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

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
            firebaseHelper.getmAuth().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firebaseHelper.updateUid(firebaseHelper.getmAuth().getCurrentUser().getUid());
                                updateIfLoggedIn();
                                Log.i(TAG, email + " logged in");

                                firebaseHelper.attachReadDataToUser();

                                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Log.d(TAG, "sigh In failed for " + email + " " + password);
                            }
                        }
                    });


        }
    }
}