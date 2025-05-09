package com.view.form;

    import javax.swing.*;
    import java.awt.*;
    import java.text.DecimalFormat;

    /**
     * Utility class for creating reusable form elements such as text fields,
     * formatted fields, and combo boxes with consistent styling.
     */
    public class FormElement {

        /**
         * Creates a panel containing a labeled text field.
         *
         * @param etiqueta The label text to display.
         * @param valor The initial value for the text field.
         * @return A JPanel containing the label and text field.
         */
        public static JPanel crearCampoTexto(String etiqueta, String valor) {
            return crearCampo(etiqueta, new JTextField(valor));
        }

        /**
         * Creates a panel containing a labeled formatted text field for numeric input.
         *
         * @param etiqueta The label text to display.
         * @param valor The initial value for the formatted text field.
         * @return A JPanel containing the label and formatted text field.
         */
        public static JPanel crearCampoPrecio(String etiqueta, Object valor) {
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            formato.setGroupingUsed(false);
            JFormattedTextField input = new JFormattedTextField(formato);

            // Validar y establecer el valor inicial
            if (valor instanceof Number) {
                input.setValue(valor);
            } else {
                input.setValue(0.0); // Valor predeterminado si el valor no es un número
            }

            return crearCampo(etiqueta, input);
        }

        /**
         * Creates a panel containing a labeled combo box.
         *
         * @param etiqueta The label text to display.
         * @param opciones The options to populate the combo box.
         * @param seleccion The initially selected item in the combo box.
         * @return A JPanel containing the label and combo box.
         */
        public static JPanel crearCampoComboBox(String etiqueta, String[] opciones, Object seleccion) {
            JComboBox<String> comboBox = new JComboBox<>(opciones);
            comboBox.setSelectedItem(seleccion);
            return crearCampo(etiqueta, comboBox);
        }

        /**
         * Creates a panel containing a label and a given input component.
         *
         * @param etiqueta The label text to display.
         * @param input The input component (e.g., text field, combo box) to include.
         * @return A JPanel containing the label and input component.
         */
        private static JPanel crearCampo(String etiqueta, JComponent input) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setPreferredSize(new Dimension(600, 90));
            panel.setMaximumSize(new Dimension(600, 90));
            panel.setBackground(Color.WHITE);

            JLabel label = new JLabel(etiqueta);
            label.setFont(new Font("Arial", Font.PLAIN, 24));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);

            configurarInput(input);

            panel.add(label);
            panel.add(input);
            return panel;
        }

        /**
         * Configures the styling and alignment of an input component.
         *
         * @param input The input component to configure.
         */
        private static void configurarInput(JComponent input) {
            input.setFont(new Font("Arial", Font.PLAIN, 24));
            input.setAlignmentX(Component.LEFT_ALIGNMENT);
            input.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            input.setMaximumSize(new Dimension(600, 40));
        }
    }