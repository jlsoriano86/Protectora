package com.example.protectora;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TareasActivity extends AppCompatActivity {
    String URL="http://5.154.58.36/apiAndroid/api/tareas/getTareas.php/";
    Spinner spTarea;
    TextView txtIdTarea, txtDescripcionTarea;
    RequestQueue requestQueue;
    List<Tarea> tareas = new ArrayList<Tarea>();
    ArrayAdapter<Tarea> dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        spTarea = (Spinner) findViewById(R.id.spTarea);
        txtIdTarea = (TextView) findViewById(R.id.txtIdTarea);
        txtDescripcionTarea = (TextView) findViewById(R.id.txtDescripcionTarea);
        dataAdapter = new ArrayAdapter<Tarea>(this,
                android.R.layout.simple_spinner_item, new ArrayList<Tarea>());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTarea.setAdapter(dataAdapter);

        spTarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("hola", "buenas colega");
                Tarea tarea = (Tarea) spTarea.getItemAtPosition(i);

                // Toast.makeText(getApplicationContext(),animal,Toast.LENGTH_LONG).show();
               txtIdTarea.setText(tarea.getIdTarea());
              // txtNombreTarea.setText(tarea.getNombreTarea());
               txtDescripcionTarea.setText(tarea.getDescripcionTarea());

                java.net.URL url = null;
                try {
                    url = new URL("http://5.154.58.36/apiAndroid/img/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                if (url != null) {
                    try {
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //imgImagen.setImageBitmap(bmp);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("hola", "na");
                // No hacer nada
            }

        });

        //buscarAnimal();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
