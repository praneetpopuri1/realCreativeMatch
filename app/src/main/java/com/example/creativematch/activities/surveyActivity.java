package com.example.creativematch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.creativematch.firebase.FirebaseHelper;
import com.example.creativematch.R;
import com.google.firebase.auth.FirebaseUser;

public class surveyActivity extends AppCompatActivity {
    RadioGroup radioGroupOne;
    private int openness, agreeableness, conscientiousness;
    public static FirebaseHelper firebaseHelper;
    public final String TAG = "Denna";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        firebaseHelper = new FirebaseHelper();


    }
    // this function only needs to run for the last radio group because it iterates through all of them
    // that is true but if someone switches one of there answers then it doesn't work out
    public void onRadioButtonClicked(View view) {

        RadioGroup rbOne = (RadioGroup) findViewById(R.id.radioGroupOne);
        //https://stackoverflow.com/questions/16561247/collecting-data-from-multiple-radio-button-groups-in-android
        int childCount = rbOne.getChildCount();
        agreeableness = 0;
        openness = 0;
        conscientiousness = 0;
        // gets how many buttons are in the radio group
        // iterates through every radio button to see if checked
        // if checked then adds whatever number the i is which will be relative to whatever the order is sequenced
        // it will most like be sequenced at the greater the extraversion then the greater the number is in the sequence or the other way


        for (int i = 0; i < childCount; i++) {
            View v = rbOne.getChildAt(i);
            if (v instanceof RadioButton)
                if (((RadioButton) v).isChecked())


                    agreeableness += i + 1;
            Log.i(TAG, "The agreeableness is: " + agreeableness);
        }
        // same things is repeated here

        RadioGroup rbTwo = (RadioGroup) findViewById(R.id.radioGroupTwo);
        childCount = rbTwo.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = rbTwo.getChildAt(i);
            if (v instanceof RadioButton)
                if (((RadioButton) v).isChecked())
                    agreeableness = agreeableness + i + 1;
            Log.i(TAG, "The agreeableness is: " + agreeableness);
        }

    }
    public void nextPage(View view) {
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();

        firebaseHelper.addPersonalityData(openness, agreeableness, conscientiousness, user.getUid());
    }
        // Check which radio button was clicked
        /*
        switch(view.getId()) {
            case R.id.radio_one:
                if (checked)
                    break;
            case R.id.radio_two:
                if (checked)
                    break;
            case R.id.radio_three:
                if (checked)
                    break;
            case R.id.radio_four:
                if (checked)
                    break;
            case R.id.radio_five:
                if (checked)
                    break;


        }

         */



}