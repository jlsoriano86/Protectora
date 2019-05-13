package com.example.protectora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnimalesActivity extends AppCompatActivity {
    Button altaAnimales;
    Button consultaAnimales;
    Button bajaAnimales;
    Button modificarAnimales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animales);

        altaAnimales = findViewById(R.id.btnAltaAnimales);
        altaAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent altaAnimales = new Intent(AnimalesActivity.this, AltaAnimalesActivity.class);
                startActivity(altaAnimales);
            }
        });

        consultaAnimales = findViewById(R.id.btnConsultarAnimales);
       consultaAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consultaAnimales = new Intent(AnimalesActivity.this, ConsultaAnimalesActivity.class);
                startActivity(consultaAnimales);
            }
        });

        bajaAnimales = findViewById(R.id.btnBajaAnimales);
        bajaAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bajaAnimales = new Intent(AnimalesActivity.this, BajaAnimalesActivity.class);
                startActivity(bajaAnimales);
            }
        });

        modificarAnimales = findViewById(R.id.btnModificarAnimales);
        modificarAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modificarAnimales = new Intent(AnimalesActivity.this, ModificaAnimalesActivity.class);
                startActivity(modificarAnimales);
            }
        });
    }
}
