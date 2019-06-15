package com.example.protectora;

import android.content.Intent;
import android.os.StrictMode;
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

/*
Esta clase contiene un login que aún no tiene funcionalidad real al no contar con una api en el web service para tal fin,
de forma que su única utilidad actualmente es dar paso a la siguiente pantalla.
En versiones posteriores se añadirá un login real.
 */
public class MainActivity extends AppCompatActivity {
    EditText txtNombreUsuario, txtContrasenaUsuario;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        txtContrasenaUsuario = (EditText) findViewById(R.id.txtContrasenaUsuario);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Conexión correcta",Toast.LENGTH_SHORT).show();
                Intent entrar = new Intent(MainActivity.this, com.example.protectora.Principal.class);
                startActivity(entrar);
            }
        });
    }

}
