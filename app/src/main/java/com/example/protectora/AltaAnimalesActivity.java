package com.example.protectora;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class AltaAnimalesActivity extends AppCompatActivity {

    ImageView imgImagen;
    EditText txtNombre, txtNacimiento, txtTipo;
    Spinner spEstado;
    Button btnAlta;

    RequestQueue requestQueue;

    // Constantes para identificar la procedencia de la acción solicitada:
    private static int DESDE_CAMARA = 1;
    private static int DESDE_GALERIA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_animales);

        imgImagen=(ImageView) findViewById(R.id.imgImagen);
        txtNombre=(EditText)findViewById(R.id.txtId);
        txtNacimiento=(EditText)findViewById(R.id.txtNacimiento);
        txtTipo=(EditText)findViewById(R.id.txtTipo);
        spEstado=(Spinner)findViewById(R.id.txtEstado);
        btnAlta=(Button) findViewById(R.id.btnAlta);

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarAlta("http://protectora-animales.ddns.net/phpMyAdmin/api/animals/postAnimals.php");
            }
        });

    }

    public void clicObtener(View view) {
        RadioButton radCamara = findViewById(R.id.radCamara);
        RadioButton radGaleria = findViewById(R.id.radGaleria);

        Intent intent = null;
        int requestCode = 0;
        if (radCamara.isChecked()) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            requestCode = DESDE_CAMARA;
        }
        if (radGaleria.isChecked()) {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            requestCode = DESDE_GALERIA;
        }

        startActivityForResult(intent, requestCode);

    }

    // Método que se ejecuta cuando concluye el intent en el que se pide por una imagen
    // ya sea desde la cámara como desde la galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap imagen = null;

        if (requestCode == DESDE_CAMARA) {
            imagen = (Bitmap) data.getParcelableExtra("data");
        }

        if (requestCode == DESDE_GALERIA) {
            Uri rutaImagen = data.getData();
            try {
                imagen = BitmapFactory.decodeStream(new BufferedInputStream(getContentResolver().openInputStream(rutaImagen)));
            } catch (FileNotFoundException e) {
            }
            Log.i("MyApp", rutaImagen.getPath());
        }

        ImageView imgImagen = findViewById(R.id.imgImagen);
        imgImagen.setImageBitmap(imagen);
    }

    private void ejecutarAlta(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Animal registrado", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("birth",txtNacimiento.getText().toString());
                parametros.put("animal_type",txtTipo.getText().toString());
                parametros.put("animal_name",txtNombre.getText().toString());
                parametros.put("state_id", spEstado.toString());
                //¿state_desc?
                parametros.put("img",imgImagen.toString());

                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
