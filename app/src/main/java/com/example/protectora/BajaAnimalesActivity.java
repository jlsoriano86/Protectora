package com.example.protectora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BajaAnimalesActivity extends AppCompatActivity {
    ImageView imgImagen;
    EditText txtNombre, txtId, txtNacimiento, txtTipo, txtEstado;
    Spinner spAnimal;
    Button btnBaja;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja_animales);

        spAnimal=(Spinner)findViewById(R.id.spAnimal);
        imgImagen=(ImageView) findViewById(R.id.imgImagen);
        txtNombre=(EditText)findViewById(R.id.txtTitulo);
        txtNacimiento=(EditText)findViewById(R.id.txtNacimiento);
        txtTipo=(EditText)findViewById(R.id.txtTipo);
        btnBaja=(Button) findViewById(R.id. btnBaja);

        btnBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarBaja("http://protectora-animales.ddns.net/api/animals/deleteAnimalById.php");
            }
        });

    }

            private void ejecutarBaja(String URL) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "El título ha sido eliminado", Toast.LENGTH_SHORT).show();
                        limpiarPantalla();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error de conexión",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> parametros=new HashMap<String,String>();
                        parametros.put("id",txtNombre.getText().toString());
                        return parametros;
                    }
                };
                requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }

    private void limpiarPantalla(){
        txtId.setText("");
        txtNacimiento.setText("");
        txtTipo.setText("");
        txtEstado.setText("");

    }

}