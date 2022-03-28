package com.example.creativematch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        radioGroupOne = findViewById(R.id.radioGroupOne);


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioGroup rb = (RadioGroup) findViewById(R.id.radioGroupOne);
        ArrayList<RadioButton> list = new ArrayList<RadioButton>();

        int childCount = rb.getChildCount() + 1;
        for (int i=1; i < childCount; i++){
            View v = rb.getChildAt(i);
            if(v instanceof  RadioButton)
                if(((RadioButton)v).isChecked())
                    ;
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
}