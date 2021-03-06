package com.example.protectora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


//Esta clase nos permite navegar entre los activities correspondientes a tareas, animales e incidencias
public class Principal extends AppCompatActivity {
    ImageView tareas;
    ImageView animales;
    ImageView incidencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tareas = (ImageView) findViewById(R.id.imgTareas);
        tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tareas = new Intent(Principal.this, com.example.protectora.TareasActivity.class);
                startActivity(tareas);
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

        incidencias = (ImageView) findViewById(R.id.imgIncidencias);
        incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incidencias = new Intent(Principal.this, com.example.protectora.IncidenciasActivity.class);
                startActivity(incidencias);
            }
        });



    }
}
