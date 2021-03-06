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


//En esta clase consulto las tareas almacenadas en la base de datos haciendo uso de mi web service
public class TareasActivity extends AppCompatActivity {
    Spinner spTarea;
    //URL donde está alojado el código PHP para consultar las tareas almacenadas en la base de datos
    String URL="http://5.154.58.36/apiAndroid/api/tareas/getTareas.php/";

    TextView txtIdTarea, txtDescripcionTarea;
    RequestQueue requestQueue;
    List<Tarea> tareas = new ArrayList<Tarea>();
    ArrayAdapter<Tarea> dataAdapter;

    //Al iniciar el activity, consultamos las tareas  almacenadas en la base de datos
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
        //Al seleccionar una tarea en el Spinner, se cargan los demás datos de la tarea en el resto de TextViews
        spTarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Tarea tarea = (Tarea) spTarea.getItemAtPosition(i);
               txtIdTarea.setText(tarea.getId());
               txtDescripcionTarea.setText(tarea.getDescription());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }

        });
        buscarTarea();
    }

    /*Método para consultar las tareas almacenadas en la base de datos seleccionándolas desde el spinner
    los datos se trataran en formato Json y hago uso de la libreria Volley
     */
    private void buscarTarea() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject data = json.getJSONObject("data");
                    JSONArray tasks = data.getJSONArray("tasks");
                    List<String> tareaList = new ArrayList<String>();
                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject tarea = tasks.getJSONObject(i);
                        tareas.add(new Tarea(
                                tarea.getString("nameTask"),
                                tarea.getString("id"),
                                tarea.getString("descriptionTask")
                        ));
                    }
                    dataAdapter.addAll(tareas);


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
