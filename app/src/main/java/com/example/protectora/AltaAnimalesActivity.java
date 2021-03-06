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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//En esta clase doy de alta a animales en la base de datos haciendo uso de mi web service
public class AltaAnimalesActivity extends AppCompatActivity {

    ImageView imgImagen;
    EditText txtNombre, txtNacimiento, txtTipo;
    Spinner spEstado;
    Button btnAlta;
    List<Estado> list;

    RequestQueue requestQueue;

    private static int DESDE_CAMARA = 1;
    private static int DESDE_GALERIA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_animales);

        imgImagen=(ImageView) findViewById(R.id.imgImagen);
        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtNacimiento=(EditText)findViewById(R.id.txtNacimiento);
        txtTipo=(EditText)findViewById(R.id.txtTipo);
        spEstado=(Spinner)findViewById(R.id.spEstado);
        btnAlta=(Button) findViewById(R.id.btnAlta);
        //Esta lista contiene los valores de los estados coincidiendo con los almacenados en la base de datos
        list = new ArrayList<Estado>();
        list.add(new Estado("Adoptado", "1"));
        list.add(new Estado("No adoptado", "2"));
        list.add(new Estado("Fallecido", "3"));

        ArrayAdapter<Estado> dataAdapter = new ArrayAdapter<Estado>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(dataAdapter);

        //Al pulsar el btnAlta, hacemos uso de la api para dar de alta al animal
        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //URL donde está alojado el código PHP para dar de alta a animales
                ejecutarAlta("http://5.154.58.36/apiAndroid/api/animals/postAnimals.php");
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

    //Este método recoge los parámetros del animal para inyectar los datos en la base de datos como un objeto JSON
    private void ejecutarAlta(String URL){
        Estado st = list.get(spEstado.getSelectedItemPosition());
        String img64 = imgToBase64(imgImagen);
        JSONObject params = new JSONObject();
        try {
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
        //Por medio de una Request y nuestros datos en formato JSON, ejecutamos el alta a través de la API
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Si el alta se ha llevado a cabo correctamente, aparecerá este mensaje Toast
                Toast.makeText(getApplicationContext(), "Animal registrado", Toast.LENGTH_SHORT).show();
                //Tras el alta, reinicio la activity
                finish();
                startActivity(getIntent());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StringWriter sw = new StringWriter();
                error.printStackTrace(new PrintWriter(sw));
                //Si se ha producido un error, aparecerá este mensaje Toast
                Toast.makeText(getApplicationContext(), sw.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
        };
        //Hacemos uso de la librería Volley para ejecutar nuestra request
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
