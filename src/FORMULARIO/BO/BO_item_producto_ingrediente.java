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
public class BO_item_producto_ingrediente {

    private DAO_item_producto_ingrediente ipidao = new DAO_item_producto_ingrediente();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_producto_ingrediente(item_producto_ingrediente ingre, JTable tbltabla, int idproducto) {
        String titulo = "insertar_item_producto_ingrediente";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ipidao.insertar_item_producto_ingrediente(conn, ingre);
            ipidao.actualizar_tabla_item_producto_ingrediente(conn, tbltabla, idproducto);
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

    public void eliminar_item_producto_ingrediente(item_producto_ingrediente ingre, JTable tbltabla, int idproducto) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ELIMINAR ESTE INGREDIENTE", "ELIMINAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "eliminar_item_producto_ingrediente";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ipidao.eliminar_item_producto_ingrediente(conn, ingre);
                ipidao.actualizar_tabla_item_producto_ingrediente(conn, tbltabla, idproducto);
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
