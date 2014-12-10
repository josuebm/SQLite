package com.dam2.sqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "futbol.sqlite";
    //public static final int DATABASE_VERSION = 1;
    //Aquí ponemos un número superior para actualizar la estructura de la base de datos
    public static final int DATABASE_VERSION = 2;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        /*sql="create table "+Contrato.TablaAgenda.TABLA+
                " ("+ Contrato.TablaAgenda._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaAgenda.NOMBRE+" text, "+
                Contrato.TablaAgenda.TELEFONO+" text, "+
                Contrato.TablaAgenda.CORREO+" text)";
        db.execSQL(sql);

        sql = "create table " + Contrato.TablaJugador.TABLA + "( " +
                Contrato.TablaJugador._ID + " integer primary key autoincrement, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.VALORACION + " integer, " +
                Contrato.TablaJugador.FNAC + " text" +
                ")";
        //Log.v("sql", sql);
        db.execSQL(sql);*/
        sql = "create table " + Contrato.TablaJugador.TABLA + "( " +
                Contrato.TablaJugador._ID + " integer primary key autoincrement, " +
                Contrato.TablaJugador.NOMBRE + " text unique, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.FNAC + " text" +
                ")";
        db.execSQL(sql);

        sql = "create table " + Contrato.TablaPartido.TABLA + "( " +
                Contrato.TablaPartido._ID + " integer primary key autoincrement, " +
                Contrato.TablaPartido.IDJUGADOR + " integer, " +
                Contrato.TablaPartido.VALORACION + " integer, " +
                Contrato.TablaPartido.CONTRINCANTE + " text" +
                ")";
        db.execSQL(sql);

        sql = "create unique index jugadorcontrincante on " + Contrato.TablaPartido.TABLA +
                "( " +
                Contrato.TablaPartido.IDJUGADOR + "," +
                Contrato.TablaPartido.CONTRINCANTE + " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Transformar el esquema de la versión old a la versión new sin que se produzca ninguna pérdida de datos.
        //1º crear tablas de respaldo (idénticas)
        String sql = "create table respaldo" + Contrato.TablaJugador.TABLA + "( " +
                Contrato.TablaJugador._ID + " integer primary key, " +//Hemos eliminado el autoincrement por si falta algun ID por haber eliminado la tupla
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.VALORACION + " integer, " +
                Contrato.TablaJugador.FNAC + " text" +
                ")";
        db.execSQL(sql);
        //2º copiar los datos a esas tablas
        sql = "insert into respaldo"+ Contrato.TablaJugador.TABLA + " select * from " + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        //3º borrar las tablas originales
        sql = "drop table " + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        //4º crear las tablas nuevas (onCreate)
        onCreate(db);
        //5º copiar los datos de respaldo en mis tablas
        sql = "insert into "+ Contrato.TablaJugador.TABLA +
                " select " + Contrato.TablaJugador._ID + "," +
                Contrato.TablaJugador.NOMBRE + "," +
                Contrato.TablaJugador.TELEFONO + "," +
                Contrato.TablaJugador.FNAC + " from respaldo" + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);

        sql = "insert into "+ Contrato.TablaPartido.TABLA +
                "(" + Contrato.TablaPartido.IDJUGADOR + "," +
                Contrato.TablaPartido.VALORACION + ")" +
                " select " + Contrato.TablaJugador._ID + "," +
                Contrato.TablaJugador.VALORACION + " from respaldo" + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);

        sql = "update "+ Contrato.TablaPartido.TABLA +
                " set " + Contrato.TablaPartido.CONTRINCANTE + "='valoracion_antigua'";
        db.execSQL(sql);

        //6º borrar las tablas de respaldo
        sql="drop table respaldo" + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
    }
}