/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_cotizacion;
import FORMULARIO.ENTIDAD.cotizacion;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_cotizacion {
    private DAO_cotizacion bdao = new DAO_cotizacion();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_cotizacion(cotizacion coti) {
        String titulo = "insertar_cotizacion";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            bdao.insertar_cotizacion(conn, coti);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, coti.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, coti.toString(), titulo);
            }
        }
    }

    public void update_cotizacion(cotizacion coti) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE cotizacion", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_cotizacion";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                bdao.update_cotizacion(conn, coti);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, coti.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, coti.toString(), titulo);
                }
            }
        }
    }
}
