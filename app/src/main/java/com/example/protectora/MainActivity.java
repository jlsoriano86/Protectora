package com.example.protectora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtNombreUsuario, edittxtContrasenaUsuario;
    Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrar = findViewById(R.id.btnEntrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entrar = new Intent(MainActivity.this, com.example.protectora.Principal.class);
                startActivity(entrar);
            }
        });

/*

        entrar = (Button)findViewById(R.id.btnEntrar);
        txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        edittxtContrasenaUsuario = (EditText) findViewById(R.id.txtContrasenaUsuario);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioLogin();
            }
        });
    }

    private void usuarioLogin() {
        final String usuarioNombre = txtNombreUsuario.getText().toString();
        final String usuarioContrasena = edittxtContrasenaUsuario.getText().toString();

        if (TextUtils.isEmpty(usuarioNombre)) {
            txtNombreUsuario.setError("Por favor, introduce tu nombre de usuario");
            txtNombreUsuario.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(usuarioContrasena)) {
            edittxtContrasenaUsuario.setError("Por favor, introduce una contraseña");
            edittxtContrasenaUsuario.requestFocus();
            return;


            //PRUEBA DE AQUÍ AL FINAL
            

        }*/
    }
}
