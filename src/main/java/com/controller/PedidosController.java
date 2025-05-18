package com.controller;

import com.model.PedidosModel;
import com.model.UsuariosModel;
import com.model.ProductosModel;
import com.view.PedidosView;

import javax.swing.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class PedidosController {

    private PedidosModel model;
    private PedidosView view;

    /**
     * Constructor del controlador de pedidos.
     *
     * @param model El modelo de pedidos.
     * @param view  La vista de pedidos.
     */
    public PedidosController(PedidosModel model, PedidosView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        cargarPedidos();
    }

    /**
     * Carga los pedidos desde el modelo y actualiza la vista.
     */
    public void cargarPedidos() {
        List<String[]> pedidos = model.obtenerPedidos();
        view.actualizarTabla(pedidos);
    }

    /**
     * Guarda un pedido utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del pedido a guardar.
     */
    public void guardarPedido(Object[] datos) {
        if (model.guardarPedido(datos) > 0) {
            cargarPedidos();
            view.mostrarMensaje("Pedido guardado correctamente.", "Éxito");
        } else {
            view.mostrarMensaje("Error al guardar el pedido.", "Error");
        }
    }

    /**
     * Elimina un pedido utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del pedido a eliminar.
     */
    public void eliminarPedido(Object[] datos) {
        try {
            int idPedido = datos[0] instanceof Integer ? (int) datos[0] : Integer.parseInt(datos[0].toString());

            if (PedidosModel.eliminarPedido(idPedido) > 0) {
                view.mostrarMensaje("El pedido con ID " + datos[0] + " ha sido eliminado con éxito.", "Éxito");
                cargarPedidos();
            } else {
                view.mostrarMensaje("No se puede eliminar el pedido porque está siendo utilizado en otros registros.", "Error");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            view.mostrarMensaje("No se puede eliminar el pedido porque está siendo utilizado en otros registros.", "Error");
        } catch (NumberFormatException e) {
            view.mostrarMensaje("El ID del pedido no es válido.", "Error");
        } catch (Exception e) {
            view.mostrarMensaje("Ocurrió un error al intentar eliminar el pedido.", "Error");
            e.printStackTrace();
        }
    }

    public boolean verificarUsuario(int usuarioId) {
        return UsuariosModel.verificarUsuario(usuarioId);
    }

    public boolean verificarProducto(int productoId) {
        return ProductosModel.verificarProducto(productoId);
    }

    public void exportarPedidos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar ubicación para exportar pedidos");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos XML", "xml"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".xml")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xml");
            }

            try {
                XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(fileToSave));
                XMLEventFactory eventFactory = XMLEventFactory.newInstance();
                XMLEvent end = eventFactory.createDTD("\n");

                StartDocument startDocument = eventFactory.createStartDocument();
                eventWriter.add(startDocument);
                eventWriter.add(end);

                StartElement rootElement = eventFactory.createStartElement("", "", "pedidos");
                eventWriter.add(rootElement);
                eventWriter.add(end);

                for (String[] pedido : model.obtenerPedidos()) {
                    eventWriter.add(eventFactory.createStartElement("", "", "pedido"));
                    eventWriter.add(end);

                    createNode(eventWriter, "id", pedido[0]);
                    createNode(eventWriter, "usuario", pedido[1]);
                    createNode(eventWriter, "producto", pedido[2]);
                    createNode(eventWriter, "cantidad", pedido[3]);
                    createNode(eventWriter, "fecha", pedido[4]);

                    eventWriter.add(eventFactory.createEndElement("", "", "pedido"));
                    eventWriter.add(end);
                }

                eventWriter.add(eventFactory.createEndElement("", "", "pedidos"));
                eventWriter.add(end);
                eventWriter.add(eventFactory.createEndDocument());
                eventWriter.close();

                view.mostrarMensaje("Pedidos exportados correctamente a " + fileToSave.getAbsolutePath(), "Éxito");
            } catch (Exception e) {
                view.mostrarMensaje("Error al exportar los pedidos: " + e.getMessage(), "Error");
                e.printStackTrace();
            }
        }
    }

    private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        // Crear la etiqueta de inicio
        StartElement startElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(startElement);

        // Crear el contenido
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        // Crear la etiqueta de cierre
        EndElement endElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(endElement);
        eventWriter.add(end);
    }
}