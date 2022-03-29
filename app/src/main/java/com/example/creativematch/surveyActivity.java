package com.example.creativematch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class surveyActivity extends AppCompatActivity {
    RadioGroup radioGroupOne;
    private int extraversion, Openness;
    public final String TAG = "Denna";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);


    }

    public void onRadioButtonClicked(View view) {

        RadioGroup rbOne = (RadioGroup) findViewById(R.id.radioGroupOne);
        //https://stackoverflow.com/questions/16561247/collecting-data-from-multiple-radio-button-groups-in-android
        int childCount = rbOne.getChildCount();
        // gets how many buttons are in the radio group
        // iterates through every radio button to see if checked
        // if checked then adds whatever number the i is which will be relative to whatever the order is sequenced
        // it will most like be sequenced at the greater the extraversion then the greater the number is in the sequence or the other way


        for (int i = 0; i < childCount; i++) {
            View v = rbOne.getChildAt(i);

            if (v instanceof RadioButton)
                if (((RadioButton) v).isChecked())
                    lExtraversion += i + 1;
                    extraversion += lExtraversion;
            Log.i(TAG, "The Extraversion is: " + extraversion);
        }


        RadioGroup rbTwo = (RadioGroup) findViewById(R.id.radioGroupTwo);
        childCount = rbTwo.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = rbTwo.getChildAt(i);
            if (v instanceof RadioButton)
                if (((RadioButton) v).isChecked())
                    Openness = Openness + i + 1;
            Log.i(TAG, "The Openness is: " + Openness);
        }
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