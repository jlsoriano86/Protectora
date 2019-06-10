package com.example.protectora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends ArrayAdapter {
    private List<Tarea> listado = new ArrayList<>();

    static class CardViewHolder {
        TextView line1;

    }

    public CardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    public void add(Tarea object) {
        listado.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.listado.size();
    }

    @Override
    public Tarea getItem(int index) {
        return this.listado.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            CardViewHolder viewHolder = new CardViewHolder();
            viewHolder.line1 = row.findViewById(R.id.line1);
            Tarea t = getItem(position);
            viewHolder.line1.setText(t.getNombreTarea());
            //Log.i("MyApp", "Nombre: " + l.getNombre());
            /*RatingBar rbValoracion = row.findViewById(R.id.rbValoracion);
            rbValoracion.setRating(l.getValoracion());*/
            row.setTag(viewHolder);
        }
        return row;
    }

}