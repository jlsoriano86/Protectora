package com.example.protectora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Principal extends AppCompatActivity {
    ImageView tareas;
    ImageView animales;
    ImageView tratamientos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tareas = (ImageView) findViewById(R.id.imgTareas);
        tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personas = new Intent(Principal.this, com.example.protectora.TareasActivity.class);
                startActivity(personas);
            }
        });

        animales = (ImageView) findViewById(R.id.imgAnimales);
        animales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animales = new Intent(Principal.this, com.example.protectora.AnimalesActivity.class);
                startActivity(animales);
            }
        });

        tratamientos = (ImageView) findViewById(R.id.imgTratamientos);
        tratamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tratamientos = new Intent(Principal.this, com.example.protectora.TratamientosActivity.class);
                startActivity(tratamientos);
            }
        });
    }
}
