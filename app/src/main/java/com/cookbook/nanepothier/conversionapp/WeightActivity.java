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
import static java.lang.Math.*;

public class WeightActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner startWeightSpinner;
    private Spinner endWeightSpinner;
    private EditText startWeight;

    private ArrayAdapter<String> weightAdapter;
    private ArrayList<String> weightUnits;

    private Button convertButton;

    private TextView resultWeightView;
    private TextView resultUnitView;

    private ImageButton backBtn;

    private final double kiloToPoundFactor = 2.205;
    private final double gramToPoundFactor = 0.0022;
    private final double poundToKiloFactor = 0.454;
    private final double poundToGramFactor = 453.6;
    private final double ouncesToGramFactor = 28.3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startWeightSpinner = findViewById(R.id.start_weight_spinner);
        endWeightSpinner = findViewById(R.id.end_weight_spinner);
        startWeight = findViewById(R.id.start_weight);
        resultWeightView = findViewById(R.id.weight_result);
        resultUnitView = findViewById(R.id.result_unit_weight);

        startWeightSpinner.setOnItemSelectedListener(this);
        endWeightSpinner.setOnItemSelectedListener(this);

        createUnitsArray();

        weightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, weightUnits);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startWeightSpinner.setAdapter(weightAdapter);
        endWeightSpinner.setAdapter(weightAdapter);

        convertButton = findViewById(R.id.convert_button_weight);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                convertWeight();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.back_button_weight);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WeightActivity.this, MainMenuActivity.class));
            }
        });
    }

    private void createUnitsArray(){

        weightUnits = new ArrayList<>();

        weightUnits.add("kilogram");
        weightUnits.add("gram");
        weightUnits.add("pound");
        weightUnits.add("ounces");
    }

    public void convertWeight(){

        double startW = Double.parseDouble(startWeight.getText().toString());
        String startUnit = startWeightSpinner.getSelectedItem().toString();
        String endUnit = endWeightSpinner.getSelectedItem().toString();
        double newWeight = 0;

        if(startUnit.equals("kilogram") && endUnit.equals("pound")){

            newWeight = convert(startW, kiloToPoundFactor);
            resultUnitView.setText("pounds");

        }else if(startUnit.equals("gram") && endUnit.equals("pound")){

            newWeight = convert(startW, gramToPoundFactor);
            resultUnitView.setText("pounds");

        }else if(startUnit.equals("pound") && endUnit.equals("kilogram")){

            newWeight = convert(startW, poundToKiloFactor);
            resultUnitView.setText("kilograms");

        }else if(startUnit.equals("pound") && endUnit.equals("gram")){

            newWeight = convert(startW, poundToGramFactor);
            resultUnitView.setText("grams");

        }else if(startUnit.equals("ounces") && endUnit.equals("gram")){

            newWeight = convert(startW, ouncesToGramFactor);
            resultUnitView.setText("grams");

        }else{
            showNotSupportedMessage();
        }

        setResultView(Double.toString(newWeight));
    }

    public double convert(double startW, double conversionFactor){

        double newW = (startW * conversionFactor);
        double newWeight = (round((newW * 10.0)))/10.0;

        return newWeight;
    }

    public void showNotSupportedMessage(){

        Snackbar.make(findViewById(R.id.weight_activity_layout), "Selected conversion is not supported", Snackbar.LENGTH_LONG)
                .show();
    }

    private void setResultView(String weight){

        resultWeightView.setText(weight);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

}
