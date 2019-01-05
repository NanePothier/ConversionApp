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

public class VolumeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner startVolumeSpinner;
    private Spinner endVolumeSpinner;
    private EditText startVolume;

    private ArrayAdapter<String> volumeAdapter;
    private ArrayList<String> volumeUnits;

    private Button convertButton;

    private TextView resultVolumeView;
    private TextView resultUnitView;

    private ImageButton backBtn;

    private final double ounceToMlFactor = 29.5735;
    private final double quartToMlFactor = 946.353;
    private final double cupToMlFactor = 236.588;
    private final double mlToCupFactor = 0.0042268;
    private final double mlToOuncesFactor = 0.033814;
    private final double ouncesToCupFactor = 0.125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startVolumeSpinner = findViewById(R.id.start_volume_spinner);
        endVolumeSpinner = findViewById(R.id.end_volume_spinner);
        startVolume = findViewById(R.id.start_volume);
        resultVolumeView = findViewById(R.id.volume_result);
        resultUnitView = findViewById(R.id.result_unit_volume);

        startVolumeSpinner.setOnItemSelectedListener(this);
        endVolumeSpinner.setOnItemSelectedListener(this);

        createUnitsArray();

        volumeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, volumeUnits);
        volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startVolumeSpinner.setAdapter(volumeAdapter);
        endVolumeSpinner.setAdapter(volumeAdapter);

        convertButton = findViewById(R.id.convert_button_volume);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                convertVolume();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.back_button_volume);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VolumeActivity.this, MainMenuActivity.class));
            }
        });
    }

    public void createUnitsArray(){

        volumeUnits = new ArrayList<>();

        volumeUnits.add("ml");
        volumeUnits.add("cup");
        volumeUnits.add("ounces");
        volumeUnits.add("quart");
    }

    public void convertVolume(){

        double startVol = Double.parseDouble(startVolume.getText().toString());
        String startUnit = startVolumeSpinner.getSelectedItem().toString();
        String endUnit = endVolumeSpinner.getSelectedItem().toString();
        double newVolume = 0;

        if(startUnit.equals("ml")){

            if(endUnit.equals("cup")){

                newVolume = convert(startVol, mlToCupFactor);
                resultUnitView.setText("cups");

            }else if(endUnit.equals("ounces")){

                newVolume = convert(startVol, mlToOuncesFactor);
                resultUnitView.setText("ounces");

            }else{
                showNotSupportedMessage();
            }
        }else if(startUnit.equals("cup")){

            if(endUnit.equals("ml")){

                newVolume = convert(startVol, cupToMlFactor);
                resultUnitView.setText("ml");

            }else{
                showNotSupportedMessage();
            }
        }else if(startUnit.equals("ounces")){

            if(endUnit.equals("ml")){

                newVolume = convert(startVol, ounceToMlFactor);
                resultVolumeView.setText("ml");

            }else if(endUnit.equals("cup")){

                newVolume = convert(startVol, ouncesToCupFactor);
                resultVolumeView.setText("cups");

            }else{
                showNotSupportedMessage();
            }
        }else if(startUnit.equals("quart")){

            if(endUnit.equals("ml")){

                newVolume = convert(startVol, quartToMlFactor);
                resultUnitView.setText("ml");

            }else{
                showNotSupportedMessage();
            }
        }

        setResultView(Double.toString(newVolume));
    }

    public double convert(double startV, double conversionFactor){

        double newV = (startV * conversionFactor);
        double newVolume = Math.round((newV * 10.0))/10.0;

        return newVolume;
    }

    public void showNotSupportedMessage(){

        Snackbar.make(findViewById(R.id.volume_activity_layout), "Selected conversion is not supported", Snackbar.LENGTH_LONG)
                .show();
    }

    private void setResultView(String volume){

        resultVolumeView.setText(volume);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
