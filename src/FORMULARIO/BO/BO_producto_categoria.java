/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_producto_categoria {

    private DAO_producto_categoria pcdao = new DAO_producto_categoria();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_producto_categoria(producto_categoria ingre) {
        String titulo = "insertar_producto_categoria";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pcdao.insertar_producto_categoria(conn, ingre);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ingre.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ingre.toString(), titulo);
            }
        }
    }

    public void update_producto_categoria(producto_categoria ingre) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTA CATEGORIA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_categoria";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pcdao.update_producto_categoria(conn, ingre);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ingre.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ingre.toString(), titulo);
                }
            }
        }
    }
}
