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
public class BO_producto_ingrediente {

    private DAO_producto_ingrediente pidao = new DAO_producto_ingrediente();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_producto_ingrediente(producto_ingrediente ingre, JTable tbltabla) {
        String titulo = "insertar_producto_ingrediente";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pidao.insertar_producto_ingrediente(conn, ingre);
            pidao.actualizar_tabla_producto_ingrediente(conn, tbltabla);
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

    public void update_producto_ingrediente(producto_ingrediente ingre, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE INGREDIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_ingrediente";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pidao.update_producto_ingrediente(conn, ingre);
                pidao.actualizar_tabla_producto_ingrediente(conn, tbltabla);
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
