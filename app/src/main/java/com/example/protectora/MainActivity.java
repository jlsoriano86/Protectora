package com.example.protectora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    EditText txtNombreUsuario, txtContrasenaUsuario;
    Button btnEntrar;
    JSONArray ja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        txtContrasenaUsuario = (EditText) findViewById(R.id.txtContrasenaUsuario);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  entrarPass("http://protectora-animales.ddns.net/phpMyAdmin/api/login/consultarusuario.php?user=" + txtNombreUsuario.getText().toString());
                Intent entrar = new Intent(MainActivity.this, com.example.protectora.Principal.class);
                startActivity(entrar);
            }
        });
    }
    private void entrarPass(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);
                    if(contra.equals(txtContrasenaUsuario.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Conexión correcta",Toast.LENGTH_SHORT).show();
                        Intent entrar = new Intent(MainActivity.this, com.example.protectora.Principal.class);
                        startActivity(entrar);

                    }else{
                        Toast.makeText(getApplicationContext(),"Contraseña incorrecta",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"Nombre de usuario incorrecto",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);


    }


}
