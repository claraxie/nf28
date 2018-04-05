package com.nf28.td6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAjoute;
    public static List<Taches> taches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> tachesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Taches.getAllTaches(taches));

        ListView listView = (ListView) findViewById(R.id.liste);
        listView.setAdapter(tachesAdapter);

        btnAjoute = (Button) findViewById(R.id.ajoute);
        btnAjoute.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, Ajouter.class);
        this.startActivity(intent);
    }

    public static void addTache(Taches t){
        taches.add(t);
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
