package com.dam2.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by 2dam on 21/11/2014.
 */
public class Adaptador extends CursorAdapter {

    public Adaptador(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1, tv2, tv3;
        tv1 = (TextView)view.findViewById(R.id.tvNombre);
        tv2 = (TextView)view.findViewById(R.id.tvNacimiento);
        tv3 = (TextView)view.findViewById(R.id.tvTelefono);
        Jugador j = GestorJugador.getRow(cursor);
        tv1.setText(j.getNombre());
        tv2.setText(context.getString(R.string.fNac) + " "+ j.getfNacimiento());
        tv3.setText(context.getString(R.string.tlf) + " " + j.getTelefono());
    }
}
