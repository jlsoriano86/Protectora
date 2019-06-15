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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//En esta clase consulto los animales almacenados en la base de datos haciendo uso de mi web service y modifico sus datos
public class ModificaAnimalesActivity extends AppCompatActivity {
    Spinner spAnimal;
    //URL donde está alojado el código PHP para consultar los animales almacenados en la base de datos
    String URL="http://5.154.58.36/apiAndroid/api/animals/getAnimals.php/";
    Button btnModifica;
    Spinner spEstado;
    ImageView imgImagen;
    TextView txtId;
    EditText txtNacimiento, txtTipo,  txtNombre;
    List<Estado> list;
    RequestQueue requestQueue;

    private static int DESDE_CAMARA = 1;
    private static int DESDE_GALERIA = 2;

    List<Animal> animales = new ArrayList<Animal>();
    ArrayAdapter<Animal> dataAdapter2;

    //Al iniciar el activity, consultamos los animales de la base de datos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_animales);
        // buscarAnimal();
        spAnimal = (Spinner) findViewById(R.id.spAnimal);

        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txtId = (TextView) findViewById(R.id.txtId);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNacimiento = (EditText) findViewById(R.id.txtNacimiento);
        txtTipo = (EditText) findViewById(R.id.txtTipo);
       // txtNombre = (TextView) findViewById(R.id.txtNombre);
        spEstado = (Spinner) findViewById(R.id.spEstado);
        btnModifica=(Button) findViewById(R.id.btnModifica);
        list = new ArrayList<Estado>();

        list.add(new Estado("Adoptado", "1"));
        list.add(new Estado("No adoptado", "2"));
        list.add(new Estado("Fallecido", "3"));


        ArrayAdapter<Estado> dataAdapter = new ArrayAdapter<Estado>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(dataAdapter);

        dataAdapter2 = new ArrayAdapter<Animal>(this,
                android.R.layout.simple_spinner_item, new ArrayList<Animal>());

        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimal.setAdapter(dataAdapter2);
        //Al seleccionar un animal en el Spinner, se cargan los demás datos del animal en el resto de TextViews
        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Animal animal = (Animal)spAnimal.getItemAtPosition(i);
                txtId.setText(animal.getId());
                txtNombre.setText(animal.getName());
                txtNacimiento.setText(animal.getBirth());
                txtTipo.setText(animal.getType());
                Estado st = list.get(spEstado.getSelectedItemPosition());
                java.net.URL url = null;
                try {
                    url = new URL("http://5.154.58.36/apiAndroid/img/"+animal.getImg());
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
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getValue().equals(animal.getState())) {
                        spEstado.setSelection(j);
                        return;
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }

        });

        buscarAnimal();
        //Al pulsar el btnModifica, hacemos uso de la api para modificar los datos del animal
        btnModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //URL donde está alojado el código PHP para modificar animales
                ejecutarModificacion("http://5.154.58.36/apiAndroid/api/animals/putAnimals.php");
            }
        });
    }
    //Este método nos permite obtener una foto del animal, ya sea desde la galería o desde nuestra propia cámara
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


    //Método para consultar los animales de la base de datos seleccionándolos desde el spinner
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
                    dataAdapter2.addAll(animales);


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
    //Este método nos permite modificar al animal seleccionado en la base de datos
    private void ejecutarModificacion(String URL){
        Estado st = list.get(spEstado.getSelectedItemPosition());
        String img64 = imgToBase64(imgImagen);


        JSONObject params = new JSONObject();
        try {
            params.put("id", txtId.getText().toString());
            params.put("birth",txtNacimiento.getText().toString());
            params.put("type",txtTipo.getText().toString());
            params.put("name",txtNombre.getText().toString());
            params.put("state_id", st.getValue());
            Map<String, String> img = new HashMap<String, String>();
            img.put("ext", "png");
            img.put("data", img64);
            params.put("img", new JSONObject(img));
        } catch (JSONException error) {

        }
        //Por medio de una Request y nuestros datos en formato JSON, ejecutamos la modificación a través de la API
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Si la modificación se ha llevado a cabo correctamente, aparecerá este mensaje Toast
                Toast.makeText(getApplicationContext(), "Animal modificado", Toast.LENGTH_SHORT).show();
                //Tras la modificación, reinicio la activity
                finish();
                startActivity(getIntent());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StringWriter sw = new StringWriter();
                error.printStackTrace(new PrintWriter(sw));
                //Si ha habido algún error en la modificación, aparecerá este mensaje Toast
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        } ){
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

}


