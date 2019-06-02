package com.example.protectora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class ConsultaAnimalesActivity extends AppCompatActivity {
    Spinner spAnimal;
    ImageView imgImagen;
    TextView txtId, txtNacimiento, txtTipo, txtEstado;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_animales);

        spAnimal = (Spinner) findViewById(R.id.spAnimal);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txtId = (TextView) findViewById(R.id.txtId);
        txtNacimiento = (TextView) findViewById(R.id.txtNacimiento);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtEstado = (TextView) findViewById(R.id.txtEstado);

        /*spAnimal.setOnItemSelectedListener(this);

        spAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarAnimal("http://protectora-animales.ddns.net/phpMyAdmin/api/animals/getAnimals.php");
            }
        });*/
    }

        private void buscarAnimal(String URL){
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                           // spAnimal.setOnItemSelectedListener(jsonObject.getString());
                            txtId.setText(jsonObject.getString("idAnimales"));
                            txtNacimiento.setText(jsonObject.getString("fechaNacimientoAnimales"));
                            txtTipo.setText(jsonObject.getString("tipoAnimales"));
                            txtEstado.setText(jsonObject.getString("descripcionEstadoAnimales"));
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error de conexión",Toast.LENGTH_SHORT).show();
                }
            }


            );
            requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }



    }

