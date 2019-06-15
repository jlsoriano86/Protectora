package com.example.protectora;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//En esta clase doy de baja a animales en la base de datos haciendo uso de mi web service
public class BajaAnimalesActivity extends AppCompatActivity {
    Spinner spAnimal;
    Button btnBaja;
    //URL donde está alojado el código PHP para consultar los animales almacenados en la base de datos
    String URL="http://5.154.58.36/apiAndroid/api/animals/getAnimals.php/";

    ImageView imgImagen;
    TextView txtId, txtNacimiento, txtTipo, txtEstado;
    RequestQueue requestQueue;
    List<Animal> animales = new ArrayList<Animal>();
    ArrayAdapter<Animal> dataAdapter;

    //Al iniciar el activity, consultamos los animales de la base de datos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja_animales);

        spAnimal = (Spinner) findViewById(R.id.spAnimal);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txtId = (TextView) findViewById(R.id.txtNombre);
        txtNacimiento = (TextView) findViewById(R.id.txtNacimiento);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtEstado = (TextView) findViewById(R.id.spEstado);
        btnBaja = (Button) findViewById(R.id.btnBaja);


        dataAdapter = new ArrayAdapter<Animal>(this,
                android.R.layout.simple_spinner_item, new ArrayList<Animal>());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimal.setAdapter(dataAdapter);

        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Animal animal = (Animal) spAnimal.getItemAtPosition(i);
                txtId.setText(animal.getId());
                txtEstado.setText((animal.getState_desc()));
                txtNacimiento.setText(animal.getBirth());
                txtTipo.setText(animal.getType());
                java.net.URL url = null;
                try {
                    url = new URL("http://5.154.58.36/apiAndroid/img/" + animal.getImg());
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
        //Al pulsar el btnBaja, hacemos uso de la api para dar de baja al animal
               btnBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //URL donde está alojado el código PHP para dar de baja a animales
                ejecutarBaja("http://5.154.58.36/apiAndroid/api/animals/deleteAnimalById.php");
            }
        });
    }

    /*Método para consultar los animales de la base de datos seleccionándolos desde el spinner
    Tratamos los datos como un objeto JSON y hacemos uso de la librería volley
     */
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
    //Este método nos permite eliminar al animal seleccionado de la base de datos
    private void ejecutarBaja(String URL){
        JSONObject params = new JSONObject();
        try {
            params.put("id", txtId.getText().toString());
        } catch (JSONException error) {

        }
        //Por medio de una Request y nuestros datos en formato JSON, ejecutamos la baja a través de la API
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status = "";
                try {
                    status = (String) response.get("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals("success")){
                    //Si la baja se ha llevado a cabo correctamente, aparecerá este mensaje Toast
                    Toast.makeText(getApplicationContext(), "Animal borrado", Toast.LENGTH_SHORT).show();
                    //Tras la baja, reinicio la activity
                    finish();
                    startActivity(getIntent());
                } else {
                    //Si ha habido algún error en la baja, aparecerá este mensaje Toast
                    Toast.makeText(getApplicationContext(), "Error del servidor", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StringWriter sw = new StringWriter();
                error.printStackTrace(new PrintWriter(sw));

            }
        } ){
        };
        //Hacemos uso de la librería Volley para ejecutar nuestra request
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);


    }
}

