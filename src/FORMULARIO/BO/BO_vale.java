/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_vale;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.vale;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_vale {

    private DAO_vale vdao = new DAO_vale();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getboolean_insertar_vale(vale vale, caja_detalle caja) {
        String titulo = "insertar_vale";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.insertar_vale(conn, vale);
            cdao.insertar_caja_detalle(conn, caja);
            conn.commit();
            return true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, vale.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vale.toString(), titulo);
            }
            return false;
        }
    }

    public void update_vale(vale vale,caja_detalle caja) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE VALE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_vale";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vdao.update_vale(conn, vale);
                cdao.update_caja_detalle(conn, caja);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, vale.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vale.toString(), titulo);
                }
            }
        }
    }

    public void update_vale_anular(vale vale, caja_detalle caja) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR EL VALE", "ANULAR", "ANULAR", "CANCELAR")) {
            String titulo = "update_vale";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vdao.update_vale_anular(conn, vale);
                cdao.anular_caja_detalle(conn, caja);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, vale.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vale.toString(), titulo);
                }
            }
        }
    }
}
