package com.dam2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué on 08/12/2014.
 */
public class GestorPartido {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorPartido(Context c) {
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

    public long insert(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.IDJUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        long id = bd.insert(Contrato.TablaPartido.TABLA, null, valores);
        //id es el código autonumérico
        return id;
    }

    public int delete(Partido objeto) {
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaPartido.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int delete(int id){
        return delete(new Partido(id, 0, null, null));
    }

    public int update(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.IDJUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Contrato.TablaPartido.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public List<Partido> select(String condicion, String[] parametros, String orden) {
        List<Partido> lo = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, condicion, parametros, null, null, orden);
        cursor.moveToFirst();
        Partido objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public ArrayList<Partido> select() {
        ArrayList<Partido> lo = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, null, null, null, null, null);
        cursor.moveToFirst();
        Partido objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public static Partido getRow(Cursor c) {
        Partido objeto = new Partido();
        objeto.setId(c.getLong(0));
        objeto.setIdJugador(c.getInt(1));
        objeto.setValoracion(c.getInt(2));
        objeto.setContrincante(c.getString(3));
        return objeto;
    }

    public Partido getRow(long id){
        List<Partido> partido = select(Contrato.TablaPartido._ID + " = ?", new String[]{id + ""}, null);
        Partido objeto = partido.get(0);
        if (!partido.isEmpty())
            return objeto;
        else
            return null;
    }

    public Cursor getCursor(String consulta) {
        Cursor cursor = bd.rawQuery(consulta, null);
        return cursor;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orden) {
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, condicion, parametros, null, null, orden);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, null, null, null, null, null);
        return cursor;
    }

    public Partido getRow(Integer idJugador) {
        String[] parametros = new String[] { idJugador+"" };
        Cursor c = bd.rawQuery("select * from "+
                Contrato.TablaPartido.TABLA
                + " where " + Contrato.TablaPartido.IDJUGADOR + " = ?", parametros);
        c.moveToFirst();
        Partido objeto = getRow(c);
        c.close();
        return objeto;
    }
}
