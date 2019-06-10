package com.example.protectora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TareasActivity extends AppCompatActivity {
    public ListView listView;
    private static List<Tarea> lstTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        listView = findViewById(R.id.card_listView); listView.addHeaderView(new View(this)); // añade espacio arriba de la primera card
        listView.addFooterView(new View(this)); // añade espacio debajo de la última card

    }

    @Override
    protected void onResume() {
        super.onResume();
        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);

      //  lstTarea = listaTareas(this);
        if (lstTarea == null) {
            Toast.makeText(this, "La base de datos está vacía.", Toast.LENGTH_LONG).show();
        } else {
            for (Tarea t : lstTarea) {
                listadoDeCards.add(t);
            }
            listView.setAdapter(listadoDeCards);
        }

    }
}
