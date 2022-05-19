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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class SignUp extends AppCompatActivity {

    public static FirebaseHelper firebaseHelper;
    public final String TAG = "Denna";
    private EditText nameET, emailET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseHelper = new FirebaseHelper();

    }
    public void signUp(View v) {
        // Make references to EditText in xml
        nameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        // Get user data
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        Log.i(TAG, name + " " + email + " " + password);

        // verify all user data is entered
        if (name.length() == 0 || email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }

        // verify password is at least 6 char long (otherwise firebase will deny)
        else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        }
        else {
            // code to sign up user

            firebaseHelper.getmAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
                                FirebaseMessaging.getInstance().getToken()
                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(@NonNull Task<String> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                                    return;
                                                }

                                                // Get new FCM registration token
                                                String token = task.getResult();
                                                firebaseHelper.addUserToFirestore(name, token, user.getUid());


                                                Intent intent = new Intent(SignUp.this, SurveyActivity.class);
                                                startActivity(intent);

                                            }
                                        });


                            }
                            else{
                                Log.d(TAG, "sign up failed");
                            }
                        }
                    });
        }


    }

}