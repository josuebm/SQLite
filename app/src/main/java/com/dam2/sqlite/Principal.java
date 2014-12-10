package com.dam2.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Principal extends Activity {

    private GestorJugador gj;
    private EditText etNombre, etTelefono, etFecha;
    private Adaptador ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        gj = new GestorJugador(this);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        etFecha = (EditText)findViewById(R.id.etFecha);
        final Context contexto = getApplicationContext();
        final ListView lv = (ListView)findViewById(R.id.lvJugadores);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(contexto, Secundaria.class);
                Cursor cursor = (Cursor)lv.getItemAtPosition(position);
                Jugador j = GestorJugador.getRow(cursor);
                i.putExtra("id", j.getId());
                i.putExtra("nombre", j.getNombre());
                startActivity(i);
            }
        });
        gj.open();
        Cursor c = gj.getCursor();
        ad = new Adaptador(this, c);
        lv.setAdapter(ad);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gj.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }


    public void alta(View v){
        String nombre, telefono, fecha;
        nombre = etNombre.getText().toString();
        telefono = etTelefono.getText().toString();
        fecha = etFecha.getText().toString();
        Jugador j = new Jugador(nombre, telefono, fecha);
        Cursor c = gj.getCursor();
        ad.changeCursor(c);
        vaciarCampos();
    }

    public void tostada(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void vaciarCampos(){
        etNombre.setText("");
        etTelefono.setText("");
        etFecha.setText("");
    }
}
