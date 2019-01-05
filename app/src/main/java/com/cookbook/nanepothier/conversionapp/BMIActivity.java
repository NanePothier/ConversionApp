package com.cookbook.nanepothier.conversionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BMIActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private ToggleButton unitToggle;

    private ConstraintLayout kilogramForm, poundForm;

    private Button calculateBtn;

    private EditText weightKilogram;
    private EditText heightCentimeter;
    private EditText weightPounds;
    private EditText heightFeet;
    private EditText heightInches;

    private TextView bmiResult;

    private final double POUND_TO_KG_FACTOR = 0.454;
    private final double INCH_TO_CM_FACTOR = 2.54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backBtn = (ImageButton) findViewById(R.id.back_button_bmi);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BMIActivity.this, MainMenuActivity.class));
            }
        });

        kilogramForm = findViewById(R.id.kilogram_form);
        poundForm = findViewById(R.id.pound_form);

        weightKilogram = findViewById(R.id.person_weight_kilogram);
        heightCentimeter = findViewById(R.id.person_height_centimeter);
        weightPounds = findViewById(R.id.person_weight_pound);
        heightFeet = findViewById(R.id.person_height_feet);
        heightInches = findViewById(R.id.person_height_inches);
        bmiResult = findViewById(R.id.bmi_result);

        unitToggle = findViewById(R.id.bmi_toggle);
        unitToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){

                    kilogramForm.setVisibility(View.VISIBLE);
                    poundForm.setVisibility(View.INVISIBLE);

                }else{

                    kilogramForm.setVisibility(View.INVISIBLE);
                    poundForm.setVisibility(View.VISIBLE);
                }
            }
        });

        calculateBtn = findViewById(R.id.calculate_bmi_button);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(unitToggle.isChecked()){

                    calculateBMIUsingPounds();
                }else{
                    calculateBMIUsingKilogram();
                }
            }
        });
    }

    public void calculateBMIUsingPounds(){

        double weight = Double.parseDouble(weightPounds.getText().toString());
        double heightFt = Double.parseDouble(heightFeet.getText().toString());
        double heightIn = Double.parseDouble(heightInches.getText().toString());

        double totalInches = (heightFt * 12) + heightIn;
        double heightCentimeters = totalInches * INCH_TO_CM_FACTOR;
        double weightKilogram = weight * POUND_TO_KG_FACTOR;

        calculateBMI(weightKilogram, heightCentimeters);
    }

    public void calculateBMIUsingKilogram(){

        double weight = Double.parseDouble(weightKilogram.getText().toString());
        double height = Double.parseDouble(heightCentimeter.getText().toString());

        calculateBMI(weight, height);
    }

    public void calculateBMI(double weight, double height){

        double heightMeters = height/100.0;

        double bmi = weight/(heightMeters * heightMeters);
        double roundedBMI = Math.round((bmi * 10.0))/10.0;

        bmiResult.setText(Double.toString(roundedBMI));
    }

}
