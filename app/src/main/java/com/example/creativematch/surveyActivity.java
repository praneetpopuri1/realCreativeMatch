package com.example.creativematch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class surveyActivity extends AppCompatActivity {
    RadioGroup radioGroupOne;
    RadioButton radioButtonOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        radioGroupOne = findViewById(R.id.radioGroupOne);


    }
    public void checkButton(View v){
        int radioIdOne = radioGroupOne.getCheckedRadioButtonId();

        radioButtonOne =  findViewById(radioIdOne);


    }
}