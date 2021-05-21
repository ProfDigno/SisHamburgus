/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_insumo_item_producto;
import FORMULARIO.ENTIDAD.insumo_item_producto;
import FORMULARIO.ENTIDAD.item_producto_ingrediente;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_insumo_item_producto {
    private DAO_insumo_item_producto ipidao = new DAO_insumo_item_producto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_insumo_item_producto(insumo_item_producto iiprod) {
        String titulo = "insertar_insumo_item_producto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ipidao.insertar_insumo_item_producto(conn, iiprod);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, iiprod.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, iiprod.toString(), titulo);
            }
        }
    }
    public void eliminar_insumo_item_producto(insumo_item_producto insu) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ELIMINAR ESTE ITEM INSUMO", "ELIMINAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "eliminar_insumo_item_producto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ipidao.eliminar_insumo_item_producto(conn, insu);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, insu.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, insu.toString(), titulo);
                }
            }
        }
    }
}
