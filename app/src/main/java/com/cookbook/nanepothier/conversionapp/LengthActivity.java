package com.cookbook.nanepothier.conversionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class LengthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner startLengthSpinner;
    private Spinner endLengthSpinner;
    private EditText startLength;

    private ArrayAdapter<String> lengthAdapter;
    private ArrayList<String> lengthUnits;

    private Button convertButton;

    private TextView resultLengthView;
    private TextView resultUnitView;

    private ImageButton backBtn;

    private final double inchToMeterFactor = 0.0254;
    private final double meterToInchFactor = 39.3701;
    private final double mileToKilometerFactor = 1.60934;
    private final double kilometerToMileFactor = 0.621371;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startLengthSpinner = findViewById(R.id.start_length_spinner);
        endLengthSpinner = findViewById(R.id.end_length_spinner);
        startLength = findViewById(R.id.start_length);
        resultLengthView = findViewById(R.id.length_result);
        resultUnitView = findViewById(R.id.result_unit_length);

        startLengthSpinner.setOnItemSelectedListener(this);
        endLengthSpinner.setOnItemSelectedListener(this);

        createUnitsArray();

        lengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lengthUnits);
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startLengthSpinner.setAdapter(lengthAdapter);
        endLengthSpinner.setAdapter(lengthAdapter);

        convertButton = findViewById(R.id.convert_button_length);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                convertLength();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.back_button_length);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LengthActivity.this, MainMenuActivity.class));
            }
        });
    }

    private void createUnitsArray(){

        lengthUnits = new ArrayList<>();

        lengthUnits.add("meter");
        lengthUnits.add("inches");
        lengthUnits.add("kilometer");
        lengthUnits.add("mile");
    }

    public void convertLength(){

        double startL = Double.parseDouble(startLength.getText().toString());
        String startUnit = startLengthSpinner.getSelectedItem().toString();
        String endUnit = endLengthSpinner.getSelectedItem().toString();
        double newLength = 0;

        if(startUnit.equals("meter")){

            if(endUnit.equals("inches")){

                newLength = convert(startL, meterToInchFactor);
                resultUnitView.setText("inches");

            }else{
                showNotSupportedMessage();
            }
        }else if(startUnit.equals("inches")){

            if(endUnit.equals("meter")){

                newLength = convert(startL, inchToMeterFactor);
                resultUnitView.setText("meter");

            }else{
                showNotSupportedMessage();
            }
        }else if(startUnit.equals("kilometer")){

            if(endUnit.equals("mile")){

                newLength = convert(startL, kilometerToMileFactor);
                resultUnitView.setText("miles");

            }else{
                showNotSupportedMessage();
            }
        }else if(startUnit.equals("mile")){

            if(endUnit.equals("kilometer")){

                newLength = convert(startL, mileToKilometerFactor);
                resultUnitView.setText("kilometers");

            }
        }

        setResultView(Double.toString(newLength));
    }

    public double convert(double startL, double conversionFactor){

        double newL = (startL * conversionFactor);
        double newLength = Math.round((newL * 10.0))/10.0;

        return newLength;
    }

    private void setResultView(String length){

        resultLengthView.setText(length);
    }

    public void showNotSupportedMessage(){

        Snackbar.make(findViewById(R.id.length_activity_layout), "Selected conversion is not supported", Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

}
