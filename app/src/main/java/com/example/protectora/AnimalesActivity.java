package com.example.protectora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnimalesActivity extends AppCompatActivity {
    Button altaAnimales;

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
    }
}
