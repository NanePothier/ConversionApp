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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TemperatureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner startTempSpinner;
    private Spinner endTempSpinner;
    private EditText startTemperature;

    private ArrayAdapter<String> tempAdapter;
    private ArrayList<String> tempUnits;

    private Button convertButton;

    private TextView resultTempView;
    private TextView resultUnitView;

    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startTempSpinner = (Spinner) findViewById(R.id.start_temp_spinner);
        endTempSpinner = (Spinner) findViewById(R.id.end_temp_spinner);
        startTemperature = (EditText) findViewById(R.id.start_temperature);
        resultTempView = (TextView) findViewById(R.id.temperature_result);
        resultUnitView = (TextView) findViewById(R.id.result_unit);

        startTempSpinner.setOnItemSelectedListener(this);
        endTempSpinner.setOnItemSelectedListener(this);

        tempUnits = new ArrayList<>();
        tempUnits.add("Celsius");
        tempUnits.add("Fahrenheit");

        tempAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempUnits);
        tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTempSpinner.setAdapter(tempAdapter);
        endTempSpinner.setAdapter(tempAdapter);

        convertButton = findViewById(R.id.convert_button);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                convertTemperature();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(TemperatureActivity.this, MainMenuActivity.class));
            }
        });
    }

    public void convertTemperature(){

        int startTemp = Integer.parseInt(startTemperature.getText().toString());
        String startUnit = startTempSpinner.getSelectedItem().toString();
        String endUnit = endTempSpinner.getSelectedItem().toString();

        if(startUnit.equals("Fahrenheit") && endUnit.equals("Celsius")){

            convertToCelsius(startTemp);

        }else if(startUnit.equals("Celsius") && endUnit.equals("Fahrenheit")){

            convertToFahrenheit(startTemp);

        }else{

            Snackbar.make(findViewById(R.id.temperature_activity_layout), "Please select units different from each other", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    public void convertToCelsius(int temperature){

        double newT = ((temperature - 32) * (5.0/9.0));
        int newTemperature = (int) (Math.round(newT));

        resultUnitView.setText("Celsius");
        setResultView(Integer.toString(newTemperature));
    }

    public void convertToFahrenheit(int temperature){

        double newT = ((temperature * (9.0/5.0)) + 32);
        int newTemperature = (int) (Math.round(newT));

        resultUnitView.setText("Fahrenheit");
        setResultView(Integer.toString(newTemperature));
    }

    private void setResultView(String temperature){

        resultTempView.setText(temperature);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

}
