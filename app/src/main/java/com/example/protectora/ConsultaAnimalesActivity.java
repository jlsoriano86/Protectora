package com.example.protectora;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ConsultaAnimalesActivity extends AppCompatActivity {
    Spinner spAnimal;
    String URL="http://5.154.58.36/apiAndroid/api/animals/getAnimals.php/";
    ImageView imgImagen;
    TextView txtId, txtNacimiento, txtTipo, txtEstado;
    RequestQueue requestQueue;
    List<Animal> animales = new ArrayList<Animal>();
    ArrayAdapter<Animal> dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_animales);
        spAnimal = (Spinner) findViewById(R.id.spAnimal);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txtId = (TextView) findViewById(R.id.txtNombre);
        txtNacimiento = (TextView) findViewById(R.id.txtNacimiento);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtEstado = (TextView) findViewById(R.id.spEstado);
        dataAdapter = new ArrayAdapter<Animal>(this,
                android.R.layout.simple_spinner_item, new ArrayList<Animal>());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimal.setAdapter(dataAdapter);

        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Animal animal = (Animal)spAnimal.getItemAtPosition(i);
                txtId.setText(animal.getId());
                txtEstado.setText((animal.getState_desc()));
                txtNacimiento.setText(animal.getBirth());
                txtTipo.setText(animal.getType());
                java.net.URL url = null;
                try {
                    url = new URL("http://5.154.58.36/apiAndroid/img/"+animal.getImg());
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
                    imgImagen.setImageBitmap(bmp);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }

        });

        buscarAnimal();
    }



    private void buscarAnimal() {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject data = json.getJSONObject("data");
                    JSONArray animals = data.getJSONArray("animals");
                    List<String> animalList = new ArrayList<String>();
                    for (int i = 0; i < animals.length(); i++) {
                        JSONObject animal = animals.getJSONObject(i);
                        animales.add(new Animal(
                                animal.getString("name"),
                                animal.getString("id"),
                                animal.getString("birthDate"),
                                animal.getString("type"),
                                animal.getString("state"),
                                animal.getString("state_desc"),
                                animal.getString("img")
                        ));
                    }
                    dataAdapter.addAll(animales);


                } catch (JSONException e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
}
