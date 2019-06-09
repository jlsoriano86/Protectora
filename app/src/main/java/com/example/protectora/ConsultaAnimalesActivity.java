package com.example.protectora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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

import java.util.ArrayList;
import java.util.List;

public class ConsultaAnimalesActivity extends AppCompatActivity {
    Spinner spAnimal;
    String URL="http://5.154.58.36/apiAndroid/api/animals/getAnimals.php/";
    ArrayList<String> lstAnimales;

    ImageView imgImagen;
    TextView txtId, txtNacimiento, txtTipo, txtEstado;
    RequestQueue requestQueue;
    List<String> animales = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_animales);
        lstAnimales=new ArrayList<>();
        spAnimal = (Spinner) findViewById(R.id.spAnimal);


        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txtId = (TextView) findViewById(R.id.txtTitulo);
        txtNacimiento = (TextView) findViewById(R.id.txtNacimiento);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtEstado = (TextView) findViewById(R.id.txtEstado);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, animales);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimal.setAdapter(dataAdapter);
        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String animal= spAnimal.getItemAtPosition(spAnimal.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),animal,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }

        });
        buscarAnimal(URL);

    }



    private void buscarAnimal(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject data = json.getJSONObject("data");
                    JSONArray animals = data.getJSONArray("animals");
                    List<String> animalList = new ArrayList<String>();
                    for (int i = 0; i < animals.length(); i++) {
                        JSONObject animal = animals.getJSONObject(i);
                        animales.add(animal.getString("name"));
                    }

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

