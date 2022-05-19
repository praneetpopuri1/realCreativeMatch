package com.example.creativematch.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        Map<String, Object> fName = new HashMap<>();
        fName.put("token", token);


        // this will create a new document in the collection "users" and assign it a docID that is = to newUID
        db.collection("users").document(newUID)
                .set(fName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, name + "'s user acount added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error adding user account");
                    }
                });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("FCM", "Message: "+ message.getNotification().getBody());
    }
}
