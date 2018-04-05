package com.nf28.td6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Ajouter extends AppCompatActivity {
    EditText libelleInput;
    CheckBox statutInput;
    Spinner prioriteInput;
    EditText dateInput;
    Button annule;
    Button efface;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        libelleInput = (EditText) findViewById(R.id.libelleInput);
        statutInput = (CheckBox) findViewById(R.id.statutInput);
        prioriteInput = (Spinner) findViewById(R.id.prioriteInput);
        dateInput = (EditText) findViewById(R.id.dateInput);

        annule = (Button) findViewById(R.id.annule);
        efface = (Button) findViewById(R.id.efface);
        ok = (Button) findViewById(R.id.ok);

        String[] items = new String[]{"Forte", "Moyenne", "Faible"};
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        prioriteInput.setAdapter(dropdownAdapter);

        ok.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date d = new Date();
                try {
                    d = formatter.parse(dateInput.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Taches t = new Taches(libelleInput.getText().toString(), statutInput.isChecked(), prioriteInput.getSelectedItem().toString(), d);
                MainActivity.addTache(t);
                finish();
            }
        });

        efface.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                libelleInput.setText("");
                statutInput.setChecked(false);
                prioriteInput.setSelection(0);
                dateInput.setText("");
            }
        });

        annule.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
}
