package com.view.form;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class FormElement {
    /**
     * Crea un campo de texto con una etiqueta.
     *
     * @param etiqueta El texto de la etiqueta.
     * @param valor    El valor inicial del campo de texto.
     * @return Un panel que contiene la etiqueta y el campo de texto.
     */
    public static JPanel crearCampoTexto(String etiqueta, String valor) {
        JPanel panel = crearPanelCampo(); // Crear un panel base para el campo
        panel.add(crearEtiqueta(etiqueta)); // Añadir la etiqueta
        JTextField input = new JTextField(valor); // Crear el campo de texto con el valor inicial
        configurarInput(input); // Configurar el estilo del campo
        panel.add(input); // Añadir el campo al panel
        return panel;
    }

    /**
     * Crea un campo de texto formateado para ingresar precios.
     *
     * @param etiqueta El texto de la etiqueta.
     * @param valor    El valor inicial del campo de precio.
     * @return Un panel que contiene la etiqueta y el campo de precio.
     */
    public static JPanel crearCampoPrecio(String etiqueta, Object valor) {
        JPanel panel = crearPanelCampo(); // Crear un panel base para el campo
        panel.add(crearEtiqueta(etiqueta)); // Añadir la etiqueta
        DecimalFormat formato = new DecimalFormat("#,##0.00"); // Formato para el precio
        formato.setGroupingUsed(false); // Desactivar agrupación de miles
        JFormattedTextField input = new JFormattedTextField(formato); // Crear el campo formateado
        input.setValue(valor); // Establecer el valor inicial
        configurarInput(input); // Configurar el estilo del campo
        panel.add(input); // Añadir el campo al panel
        return panel;
    }

    /**
     * Crea un campo desplegable (ComboBox) con una etiqueta.
     *
     * @param etiqueta  El texto de la etiqueta.
     * @param opciones  Las opciones disponibles en el ComboBox.
     * @param seleccion La opción seleccionada inicialmente.
     * @return Un panel que contiene la etiqueta y el ComboBox.
     */
    public static JPanel crearCampoComboBox(String etiqueta, String[] opciones, Object seleccion) {
        JPanel panel = crearPanelCampo(); // Crear un panel base para el campo
        panel.add(crearEtiqueta(etiqueta)); // Añadir la etiqueta
        JComboBox<String> comboBox = new JComboBox<>(opciones); // Crear el ComboBox con las opciones
        comboBox.setSelectedItem(seleccion); // Establecer la opción seleccionada
        configurarInput(comboBox); // Configurar el estilo del ComboBox
        panel.add(comboBox); // Añadir el ComboBox al panel
        return panel;
    }

    /**
     * Crea un panel base para los campos del formulario.
     *
     * @return Un panel configurado para contener un campo del formulario.
     */
    private static JPanel crearPanelCampo() {
        JPanel panel = new JPanel(); // Crear un nuevo panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Establecer diseño en eje Y
        panel.setPreferredSize(new Dimension(600, 90)); // Tamaño preferido del panel
        panel.setMaximumSize(new Dimension(600, 90)); // Tamaño máximo del panel
        panel.setBackground(Color.WHITE); // Fondo blanco
        return panel;
    }

    /**
     * Crea una etiqueta con el texto especificado.
     *
     * @param texto El texto de la etiqueta.
     * @return Un JLabel configurado con el texto proporcionado.
     */
    private static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto); // Crear una nueva etiqueta
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 24)); // Establecer la fuente
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
        return etiqueta;
    }

    /**
     * Configura el estilo de un componente de entrada.
     *
     * @param input El componente de entrada a configurar.
     */
    private static void configurarInput(JComponent input) {
        input.setFont(new Font("Arial", Font.PLAIN, 24)); // Establecer la fuente
        input.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
        input.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Borde gris
        input.setMaximumSize(new Dimension(600, 40)); // Tamaño máximo del componente
    }
}
