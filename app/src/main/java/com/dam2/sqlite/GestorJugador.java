package com.dam2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2dam on 17/11/2014.
 */
public class GestorJugador {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorJugador(Context c) {
        abd = new Ayudante(c);
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO, objeto.getTelefono());
        valores.put(Contrato.TablaJugador.FNAC, objeto.getfNacimiento());
        long id = bd.insert(Contrato.TablaJugador.TABLA, null, valores);
        //id es el código autonumérico
        return id;
    }

    public int delete(Jugador objeto) {
        String condicion = Contrato.TablaJugador._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaJugador.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int delete(int id){
        return delete(new Jugador(id, null, null, null));
    }

    public int update(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO, objeto.getTelefono());
        valores.put(Contrato.TablaJugador.FNAC, objeto.getfNacimiento());
        String condicion = Contrato.TablaJugador._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Contrato.TablaJugador.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public List<Jugador> select(String condicion, String[] parametros, String orden) {
        List<Jugador> lo = new ArrayList<Jugador>();
        Cursor cursor = bd.query(Contrato.TablaJugador.TABLA, null, condicion, parametros, null, null, orden);
        cursor.moveToFirst();
        Jugador objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public ArrayList<Jugador> select() {
        ArrayList<Jugador> lo = new ArrayList<Jugador>();
        Cursor cursor = bd.query(Contrato.TablaJugador.TABLA, null, null, null, null, null, null);
        cursor.moveToFirst();
        Jugador objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public static Jugador getRow(Cursor c) {
        Jugador objeto = new Jugador();
        objeto.setId(c.getLong(0));
        objeto.setNombre(c.getString(1));
        objeto.setTelefono(c.getString(2));
        objeto.setfNacimiento(c.getString(3));
        return objeto;
    }

    public Jugador getRow(long id){
        List<Jugador> jugador = select(Contrato.TablaJugador._ID + " = ?", new String[]{id + ""}, null);
        Jugador objeto = jugador.get(0);
        if (!jugador.isEmpty())
            return objeto;
        else
            return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orden) {
        Cursor cursor = bd.query(Contrato.TablaJugador.TABLA, null, condicion, parametros, null, null, orden);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(Contrato.TablaJugador.TABLA, null, null, null, null, null, null);
        return cursor;
    }

    public Jugador getRow(String nombre) {
        String[] parametros = new String[] { nombre };
        Cursor c = bd.rawQuery("select * from "+
                Contrato.TablaJugador.TABLA
                + " where " + Contrato.TablaJugador.NOMBRE + " = ?", parametros);
        c.moveToFirst();
        Jugador objeto = getRow(c);
        c.close();
        return objeto;
    }
}
