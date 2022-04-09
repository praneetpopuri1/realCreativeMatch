package com.example.creativematch.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.creativematch.OtherUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    public final String TAG = "Denna";
    private static String uid = null;            // var will be updated for currently signed in user
    // inside MainActivity with the mAuth var
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public FirebaseHelper() {
        // will gel
        //get refersence to or te of the auth and firestore elements
        // these lines of code establish the conntetions to the auth and database we are linked to
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public FirebaseAuth getmAuth() {

        return mAuth;
    }

    /*
    public ArrayList <OtherUser> attachReadDataToUserOU(int agreeableness, int openness, int conscientiousness, String uid) {
        // need to put stuff here
        if (getmAuth().getCurrentUser() != null){
            uid =mAuth.getUid();
            addPersonalityData(String.valueOf(new FirestoreCallbackOU() {
                @Override
                public void onCallback(ArrayList<OtherUser> users) {
                    Log.i(TAG, "Inside attachReadDataToUser, onCallBack" + users.toString());
                }
            }), agreeableness, openness, conscientiousness, uid);
        }
        return
    }

     */




    //https://stackoverflow.com/questions/53301344/generate-unique-id-like-firebase-firestore
    public String generateUniqueFirestoreId(){
        // Alphanumeric characters
    String chars =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder autoId = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            autoId.append(chars.charAt((int) Math.floor(Math.random() * chars.length())));
        }

    return autoId.toString();
}

    public void addUserToFirestore(String name, String newUID) {
    /*
         This will add a document with the uid of the current user to the collection called "users"
          For this we will create a Hash map since there are only two fields - a name and athe uid value
        */
        // the doc Id of the doc we are adding will be the same as the uid of the current user
        // simmilar to making an object

        //put data into my obhject using a key value pair where i label each item I put in the map
        // the key "name" is tje key that is used to label the data fireStore
        // the parameter value of name is passed in to be the value assigned to name in firestore
        Map<String, Object> fName = new HashMap<>();
        fName.put("name", name);

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

    public void updateUid(String uid) {

    }

    public void addALotOfUsers(ArrayList<OtherUser> users){
        //need to itterate through all of the users and add them to the batch
        WriteBatch batch = db.batch();
        for (OtherUser user: users) {
            DocumentReference personalityRef = db.collection("users").document(generateUniqueFirestoreId());
            batch.set(personalityRef,user);
        }

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }




    public void addPersonalityData(String profession, int agreeableness, int openness, int conscientiousness, String uid) {
        //add a wishlist item to the data
        // this method will be overloaded and the other method wull incorprate the to
        // handle asynch calls for reading data to keep myItems al up to data
        DocumentReference personalityRef = db.collection("users").document(uid);

        Map<String, Object> personalityProfession = new HashMap<>();
        personalityProfession.put("agreeableness", agreeableness);
        personalityProfession.put("openness", openness);
        personalityProfession.put("conscientiousness", conscientiousness);
        personalityProfession.put("profession", profession);
        personalityRef
                .update(personalityProfession)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "personality successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });


    }

    public ArrayList <OtherUser> queerySearch(FirestoreCallbackOU firestoreCallbackOU,int agreeableness, int openness, int conscientiousness) {
        ArrayList<OtherUser> similarUsers = new ArrayList<OtherUser>();
        CollectionReference usersRef = db.collection("users");
        usersRef.whereGreaterThanOrEqualTo("agreeableness", agreeableness - 20)
                //.whereLessThanOrEqualTo("agreeableness", agreeableness + 20)
                //.whereGreaterThanOrEqualTo("openness", openness - 20)
                //.whereLessThanOrEqualTo("openness", openness + 20)
                //.whereGreaterThanOrEqualTo("conscientiousness", conscientiousness - 20)
                //.whereLessThanOrEqualTo("conscientiousness", conscientiousness + 20)
                .limit(20);


        usersRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userName = document.getString("name");
                                String profession = document.getString("profession");
                                OtherUser anotherUser = new OtherUser(profession,userName);
                                Log.d(TAG, "the other users are: " + anotherUser);
                                similarUsers.add(anotherUser);

                            }
                            firestoreCallbackOU.onCallback(similarUsers);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        Log.d(TAG, "the other users are: " + similarUsers.toString());
        return similarUsers;

       }

       public int[] getPersonality(FirestoreCallbackP firestoreCallbackP, String uid){
           DocumentReference docRef = db.collection("users").document(uid);
           int[] personalityArray = new int[3];
           docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

               @Override
               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                   if (task.isSuccessful()) {
                       DocumentSnapshot document = task.getResult();
                       if (document.exists()) {

                           personalityArray[0] = Integer.parseInt(document.getString("agreeableness"));
                           personalityArray[1] = Integer.parseInt(document.getString("openness"));
                           personalityArray[2] = Integer.parseInt(document.getString("conscientiousness"));
                           Log.d(TAG, "the personality of the users are: " + personalityArray.toString());
                       } else {
                           Log.d(TAG, "No such document");
                       }
                       firestoreCallbackP.onCallback(personalityArray);
                   } else {
                       Log.d(TAG, "get failed with ", task.getException());
                   }
               }
           });
           Log.d(TAG, "the personality of the users are: " + Arrays.toString(personalityArray));
        return personalityArray;
       }

    public String getProfession(FirestoreCallbackPro firestoreCallbackPro,String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        final String[] getProfession = new String[1];
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        getProfession[0] = document.getString("profession");
                        Log.d(TAG, "the personality of the users are: " + getProfession.toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                    firestoreCallbackPro.onCallback(getProfession);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        Log.d(TAG, "the personality of the users are: " + Arrays.toString(getProfession));
        return getProfession[0];
    }




 /*


    private void addData(WishListItem w, FirestoreCallback firestoreCallback)
    {
        db.collection("users").document(uid).collection("myWishList")
                .add(w)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // we are going to update the document we JUST added by
                        //editing the docID instance var so that it knows what
                        // the calue is for its docID in firestore

                        //in the onSuccess method, the document Reference parameter
                        //contains a refernce to the newly create socument. We
                        //can us this to extract the docID from firestore
                        db.collection("users").document(uid).collection("myWishList")
                                .document(documentReference.getId())
                                .update("docID", documentReference.getId());
                        Log.i(TAG, "just added " + w.getDocID());

                        // if we want the arrarList to be updaetd now, we call
                        // readData. If we dont care about comtuing our work, then
                        // you dont need to call readData
                        //later on practice expermient with commenting  this line out, see
                        // gow it is different

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error adding elemnt", e);
                    }
                });
    }
    public ArrayList<WishListItem> getWishListItems() {
        return myItems;
    }

    public void editData(WishListItem w) {

    }

    public void deleteData(WishListItem w) {

    }

    public void updateUid(String uid) {

    }

     https://www.youtube.com/watch?v=0ofkvm97i0s
    This video is good!!!   Basically he talks about what it means for tasks to be asychronous
    and how you can create an interface and then using that interface pass an object of the interface
    type from a callback method and access it after the callback method.  It also allows you to delay
    certain things from occuring until after the onSuccess is finished.


    private void readData(FirestoreCallback firestoreCallback) {
        myItems.clear();  //clears out the array list so it gets a fresh copy of the data
        db.collection("users").document(uid).collection("myWishList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc: task.getResult()){
                                // processing of doc of the query we made
                                // this is a snapshot of the sata at this moment in time when we
                                // requested to get the data
                                // to object allows us to pulll the document from the firestore objext
                                // convert into a java object using the constutor
                                WishListItem w = doc.toObject(WishListItem.class);
                                myItems.add(w);

                            }
                            //still inside the onComplete method, I want to call my onCallback method
                            // so tjat tje interface can do its job and let whoever is waiting know the
                            // asych method is done

                            Log.i(TAG, "sucess reading data" + myItems.toString());
                            firestoreCallback.onCallback(myItems);
                        }
                    }
                });
    }

  */

    //https://stackoverflow.com/questions/48499310/how-to-return-a-documentsnapshot-as-a-result-of-a-method/48500679#48500679
    public interface FirestoreCallbackOU {
        void onCallback (ArrayList<OtherUser> listUsers);
    }
    public interface FirestoreCallbackP {
        void onCallback (int[] personality);
    }
    public interface FirestoreCallbackPro {
        void onCallback (String[] profession);
    }


}
