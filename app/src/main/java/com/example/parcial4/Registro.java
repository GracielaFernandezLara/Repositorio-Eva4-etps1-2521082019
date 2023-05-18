package com.example.parcial4;

public class Registro {
    private int id;
    private String nombre;

    public Registro(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
