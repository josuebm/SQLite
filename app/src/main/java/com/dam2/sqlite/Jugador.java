package com.dam2.sqlite;

import java.io.Serializable;

/**
 * Created by 2dam on 17/11/2014.
 */
public class Jugador implements Serializable, Comparable <Jugador>{

    private  long id;
    private  String nombre, telefono, fNacimiento;


    //1º Constructor predeterminado
    public Jugador() {
        this(0, "", "", "");
    }

    //2º Constructor completo
    public Jugador(long id, String nombre, String telefono, String fNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fNacimiento = fNacimiento;
    }

    public Jugador(String nombre, String telefono, String fNacimiento) {
        this.id = 0;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fNacimiento = fNacimiento;
    }

    //3º Todos setters y getters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(String fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    //equals -> clave principal de la tabla


    @Override
    public int compareTo(Jugador jugador) {
        //juagores -> this, jugador
        if(this.nombre.compareTo(jugador.nombre) != 0)
            return this.nombre.compareTo(jugador.nombre);
        else
            return this.fNacimiento.compareTo(jugador.fNacimiento);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fNacimiento='" + fNacimiento + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Jugador.class != o.getClass()) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


}
