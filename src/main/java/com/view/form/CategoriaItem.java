package com.view.form;

public class CategoriaItem {
    private final int id;
    private final String nombre;

    public CategoriaItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nombre; // Esto se mostrará en el JComboBox
    }
}