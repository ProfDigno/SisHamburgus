/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_gasto;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.gasto;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_gasto {

    private DAO_gasto gdao = new DAO_gasto();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getboolean_insertar_gasto(gasto gas, caja_detalle caja) {
        String titulo = "insertar_gasto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            gdao.insertar_gasto(conn, gas);
            cdao.insertar_caja_detalle(conn, caja);
            conn.commit();
            return true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, gas.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, gas.toString(), titulo);
            }
            return false;
        }
    }

    public void update_gasto(gasto gas,caja_detalle caja) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE GASTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_gasto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                gdao.update_gasto(conn, gas);
                cdao.update_caja_detalle(conn, caja);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, gas.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, gas.toString(), titulo);
                }
            }
        }
    }

    public void update_gasto_anular(gasto gas, caja_detalle caja) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR EL GASTO", "ANULAR", "ANULAR", "CANCELAR")) {
            String titulo = "update_gasto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                gdao.update_gasto_anular(conn, gas);
                cdao.anular_caja_detalle(conn, caja);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, gas.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, gas.toString(), titulo);
                }
            }
        }
    }
}
