package com.example.creativematch.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService {
    public static FirebaseHelper firebaseHelper;
    private FirebaseFirestore db;
    public final String TAG = "Denna";

    @Override
    public void onNewToken(String token) {
        Map<String, Object> fName = new HashMap<>();
        fName.put("token", token);
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();

        // this will create a new document in the collection "users" and assign it a docID that is = to newUID
        db.collection("users").document(user.getUid())
                .set(fName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG,  "FCM token was updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "FCM token was not updated");
                    }
                });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("FCM", "Message: "+ message.getNotification().getBody());
    }
}
