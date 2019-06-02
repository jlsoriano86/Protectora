package com.example.protectora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class ConsultaAnimalesActivity extends AppCompatActivity {
    Spinner spAnimal;
    String URL="http://protectora-animales.ddns.net/phpMyAdmin/api/animals/getAnimals.php";
    ArrayList<String> lstAnimales;

    ImageView imgImagen;
    TextView txtId, txtNacimiento, txtTipo, txtEstado;
    RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_animales);
        lstAnimales=new ArrayList<>();
        spAnimal = (Spinner) findViewById(R.id.spAnimal);
        buscarAnimal(URL);



        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txtId = (TextView) findViewById(R.id.txtId);
        txtNacimiento = (TextView) findViewById(R.id.txtNacimiento);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtEstado = (TextView) findViewById(R.id.txtEstado);
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

    }

        /*spAnimal.setOnItemSelectedListener(this);

        spAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarAnimal("http://protectora-animales.ddns.net/phpMyAdmin/api/animals/getAnimals.php");
            }
        });
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
        }*/





    private void buscarAnimal(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        JSONArray jsonArray=jsonObject.getJSONArray("name");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String animal=jsonObject1.getString("id");
                            txtNacimiento.setText(jsonObject.getString("fechaNacimientoAnimales"));
                            txtTipo.setText(jsonObject.getString("tipoAnimales"));
                            txtEstado.setText(jsonObject.getString("descripcionEstadoAnimales"));
                            /*String animal=jsonObject1.getString("birthDate");
                            String animal=jsonObject1.getString("type");
                            String animal=jsonObject1.getString("state");*/
                            lstAnimales.add(animal);
                        }
                    }

                    spAnimal.setAdapter(new ArrayAdapter<String>(ConsultaAnimalesActivity.this, android.R.layout.simple_spinner_dropdown_item, lstAnimales));
                }catch (JSONException e){e.printStackTrace();}
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

