package com.dam2.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Josué on 08/12/2014.
 */
public class AdaptadorPartido extends CursorAdapter {

    public AdaptadorPartido(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle_partido, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1, tv2, tv3;
        tv1 = (TextView)view.findViewById(R.id.tvJugador);
        tv2 = (TextView)view.findViewById(R.id.tvValoracion);
        tv3 = (TextView)view.findViewById(R.id.tvContrincante);
        Partido p = GestorPartido.getRow(cursor);
        tv1.setText(p.getIdJugador()+"");
        tv2.setText(context.getString(R.string.valoracion) + " " + p.getValoracion());
        tv3.setText(context.getString(R.string.contrincante) + " " + p.getContrincante());
    }
}