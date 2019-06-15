package com.example.protectora;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

//En esta clase doy de alta incidencias en la base de datos haciendo uso de mi web service
public class IncidenciasActivity extends AppCompatActivity {
    ImageView imgImagen;
    EditText txtTitulo, txtDescripcion;
    Button btnEnviar;
    RequestQueue requestQueue;
    private static int DESDE_CAMARA = 1;
    private static int DESDE_GALERIA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);

        imgImagen=(ImageView) findViewById(R.id.imgImagen);
        txtTitulo=(EditText)findViewById(R.id.txtNombre);
        txtDescripcion=(EditText)findViewById(R.id.txtDescripcion);
        btnEnviar=(Button) findViewById(R.id.btnEnviar);

        //Al pulsar el btnEnviar, hacemos uso de la api para dar de alta la incidencia
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //URL donde está alojado el código PHP para dar de alta incidencias
                ejecutarIncidencia("http://5.154.58.36/apiAndroid/api/incidencias/postIncidencias.php");
            }
        });

    }
    //Este método nos permite obtener una foto de la incidencia, ya sea desde la galería o desde nuestra propia cámara
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
        }

        ImageView imgImagen = findViewById(R.id.imgImagen);
        imgImagen.setImageBitmap(imagen);
    }
    //Este método nos permite utilizar Base64 para el tratamiento de imágenes
    private String imgToBase64(ImageView imgView) {
        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        int flags = Base64.NO_WRAP;
        String image = Base64.encodeToString(bb, flags);
        return "data:image/png;base64," + image;
    }
    //Este método recoge los parámetros de la incidencia para inyectar los datos en la base de datos
    private void ejecutarIncidencia(String URL){
        String img64 = imgToBase64(imgImagen);
        JSONObject params = new JSONObject();
        try {
            params.put("name_incidencia",txtTitulo.getText().toString());
            params.put("description_incidencia",txtDescripcion.getText().toString());
            params.put("user_id", "2");
            Map<String, String> img = new HashMap<String, String>();
            img.put("ext", "png");
            img.put("data", img64);
            params.put("img", new JSONObject(img));
        } catch (JSONException error) {

        }
        //Por medio de una Request y nuestros datos en formato JSON, ejecutamos el alta a través de la API
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Si el alta se ha llevado a cabo correctamente, aparecerá este mensaje Toast
                Toast.makeText(getApplicationContext(), "Incidencia registrada", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Si se ha producido un error, aparecerá este mensaje Toast
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        } ){
        };

        //Hacemos uso de la librería Volley para ejecutar nuestra request
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
