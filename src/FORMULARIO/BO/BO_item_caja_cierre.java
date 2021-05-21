/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_caja_cierre;
import FORMULARIO.ENTIDAD.item_caja_cierre;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_item_caja_cierre {

    private DAO_item_caja_cierre iccie_dao = new DAO_item_caja_cierre();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_caja_cierre(item_caja_cierre iccie, JTable tbltabla) {
        String titulo = "insertar_item_caja_cierre";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            iccie_dao.insertar_item_caja_cierre(conn, iccie);
            iccie_dao.actualizar_tabla_item_caja_cierre(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, iccie.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, iccie.toString(), titulo);
            }
        }
    }

    public void update_item_caja_cierre(item_caja_cierre iccie, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_CAJA_CIERRE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_item_caja_cierre";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                iccie_dao.update_item_caja_cierre(conn, iccie);
                iccie_dao.actualizar_tabla_item_caja_cierre(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, iccie.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, iccie.toString(), titulo);
                }
            }
        }
    }
}
