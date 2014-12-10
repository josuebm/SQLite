package com.dam2.sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Secundaria extends Activity {

    private GestorPartido gp;
    private EditText etValoracion, etContrincante;
    private TextView tvIdJugador, tvValoracionMedia;
    private AdaptadorPartido ad;
    private long idj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idj = (Long)getIntent().getExtras().get("id");
        String nombre = (String)getIntent().getExtras().get("nombre");
        setContentView(R.layout.activity_secundaria);
        gp = new GestorPartido(this);
        tvIdJugador = (TextView)findViewById(R.id.tvNombreJugador);
        tvIdJugador.setText(getResources().getString(R.string.jugador) + " " + nombre);
        tvValoracionMedia = (TextView)findViewById(R.id.tvValoracionMedia);
        etValoracion = (EditText)findViewById(R.id.etValoracion);
        etContrincante = (EditText)findViewById(R.id.etContrincante);
        ListView lv = (ListView)findViewById(R.id.lvPartido);
        gp.open();
        Cursor c = gp.getCursor(Contrato.TablaPartido.IDJUGADOR +"="+idj, null, null);
        ad = new AdaptadorPartido(this, c);
        lv.setAdapter(ad);
        calcularMedia();
    }


    public void tostada(String cadena){
        Toast.makeText(this, cadena, Toast.LENGTH_LONG).show();
    }

    public void alta(View v){
        String valoracion, contrincante;
        valoracion = etValoracion.getText().toString();
        contrincante = etContrincante.getText().toString();
        Partido p = new Partido(Integer.valueOf((int) idj), valoracion, contrincante);
        Cursor c = gp.getCursor(Contrato.TablaPartido.IDJUGADOR +"="+idj, null, null);
        ad.changeCursor(c);
        vaciarCampos();
        calcularMedia();
    }

    public void vaciarCampos(){
        etValoracion.setText("");
        etContrincante.setText("");
    }

    public void calcularMedia(){
        Cursor d = gp.getCursor("select avg(" + Contrato.TablaPartido.VALORACION + ") from " + Contrato.TablaPartido.TABLA +
                " where " + Contrato.TablaPartido.IDJUGADOR + " = " + idj);
        d.moveToFirst();
        tvValoracionMedia.setText(getResources().getString(R.string.valMedia) + " " + d.getFloat(0));
    }
}
