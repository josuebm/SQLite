package com.dam2.sqlite;

import java.io.Serializable;

/**
 * Created by Josué on 08/12/2014.
 */
public class Partido implements Serializable, Comparable <Partido>{

    private long id;
    private int idJugador, valoracion;
    private String contrincante;


    //1º Constructor predeterminado
    public Partido() {
            this(0, 0, 0, "");
    }

    //2º Constructor completo
    public Partido(long id, int idJugador, int valoracion, String contrincante) {
        this.id = id;
        this.idJugador = idJugador;
        this.valoracion = valoracion;
        this.contrincante = contrincante;
    }

    public Partido(long id, int idJugador, String valoracion, String contrincante) {
        this.id = id;
        this.idJugador = idJugador;
        try{
            this.valoracion = Integer.parseInt(valoracion);
        }catch (NumberFormatException e){
            this.valoracion = 0;
        }
        this.contrincante = contrincante;
    }

    public Partido(int idJugador, String valoracion, String contrincante) {
        this.id = 0;
        this.idJugador = idJugador;
        try{
            this.valoracion = Integer.parseInt(valoracion);
        }catch (NumberFormatException e){
            this.valoracion = 0;
        }
        this.contrincante = contrincante;
    }

    //3º Todos setters y getters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }


    //equals -> clave principal de la tabla


    @Override
    public int compareTo(Partido partido) {
        //juagores -> this, jugador
        if(this.valoracion != partido.valoracion)
            return this.valoracion - partido.valoracion;
        else
            if(this.contrincante.compareTo(partido.contrincante) != 0)
                return this.contrincante.compareTo(partido.contrincante);
            else
                return this.idJugador - partido.idJugador;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", idJugador=" + idJugador +
                ", valoracion=" + valoracion +
                ", contrincante='" + contrincante + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Partido.class != o.getClass()) return false;

        Partido partido = (Partido) o;

        if (id != partido.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
            return (int) (id ^ (id >>> 32));
    }
}
