/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_insumo_producto;
import FORMULARIO.ENTIDAD.insumo_producto;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_insumo_producto {
    private DAO_insumo_producto pdao = new DAO_insumo_producto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_insumo_producto(insumo_producto prod, JTable tbltabla) {
        String titulo = "insertar_insumo_producto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pdao.insertar_insumo_producto(conn, prod);    
            pdao.actualizar_tabla_insumo_producto(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, prod.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, prod.toString(), titulo);
            }
        }
    }

    public void update_insumo_producto(insumo_producto prod, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE PRODUCTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_insumo_producto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pdao.update_insumo_producto(conn, prod);
                pdao.actualizar_tabla_insumo_producto(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, prod.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, prod.toString(), titulo);
                }
            }
        }
    }
}
