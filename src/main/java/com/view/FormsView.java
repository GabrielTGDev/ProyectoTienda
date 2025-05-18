package com.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa una vista de formulario con un título, contenido dinámico y botones inferiores.
 */
public class FormsView extends JPanel {

    /**
     * Constructor de FormsView.
     *
     * @param title         Título que se mostrará en la parte superior.
     * @param content       Contenido que se mostrará en el panel dinámico.
     * @param aceptarAction Acción a realizar al presionar el botón "Aceptar".
     */
    public FormsView(String title, JPanel content, ActionListener aceptarAction) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.WHITE);

        // Configurar y agregar el título
        add(crearTitulo(title), BorderLayout.NORTH);

        // Configurar y agregar el contenido dinámico
        add(crearPanelContenido(content), BorderLayout.CENTER);

        // Configurar y agregar el panel inferior con botones
        add(crearPanelInferior(aceptarAction), BorderLayout.SOUTH);
    }

    /**
     * Crea el componente del título.
     *
     * @param title Texto del título.
     * @return Un JLabel configurado como título.
     */
    private JLabel crearTitulo(String title) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(1280, 100));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.decode("#ECF0F1"));
        titleLabel.setForeground(Color.decode("#333333"));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        return titleLabel;
    }

    /**
     * Crea el panel dinámico para el contenido.
     *
     * @param content Contenido a mostrar en el panel.
     * @return Un JPanel configurado con el contenido.
     */
    private JPanel crearPanelContenido(JPanel content) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        if (content != null) {
            contentPanel.add(content, BorderLayout.CENTER);
        }
        return contentPanel;
    }

    /**
     * Crea el panel inferior con botones "Aceptar" y "Cancelar".
     *
     * @param aceptarAction Acción a realizar al presionar el botón "Aceptar".
     * @return Un JPanel con los botones configurados.
     */
    private JPanel crearPanelInferior(ActionListener aceptarAction) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setPreferredSize(new Dimension(1280, 80));
        bottomPanel.setBackground(Color.WHITE);

        bottomPanel.add(crearBotonAceptar(aceptarAction));
        bottomPanel.add(crearBotonCancelar());

        return bottomPanel;
    }

    /**
     * Crea el botón "Aceptar" con funcionalidad personalizada.
     *
     * @param aceptarAction Acción a realizar al presionar el botón.
     * @return Un JButton configurado.
     */
    private JButton crearBotonAceptar(ActionListener aceptarAction) {
        JButton botonAceptar = crearBoton("Aceptar", "#48BA78");
        botonAceptar.addActionListener(aceptarAction);
        return botonAceptar;
    }

    /**
     * Crea el botón "Cancelar" con funcionalidad para cerrar la ventana.
     *
     * @return Un JButton configurado.
     */
    private JButton crearBotonCancelar() {
        JButton botonCancelar = crearBoton("Cancelar", "#C54A3D");
        botonCancelar.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea cerrar? Los cambios no guardados se perderán.",
                    "Confirmar cierre",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            ) == JOptionPane.YES_OPTION) {
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        });
        return botonCancelar;
    }

    /**
     * Crea un botón con estilo personalizado.
     *
     * @param texto    Texto del botón.
     * @param colorHex Color de fondo del botón en formato hexadecimal.
     * @return Un JButton configurado.
     */
    private JButton crearBoton(String texto, String colorHex) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(140, 60));
        boton.setFont(new Font("Arial", Font.PLAIN, 24));
        boton.setBackground(Color.decode(colorHex));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.decode(colorHex), 1, true));
        return boton;
    }
}