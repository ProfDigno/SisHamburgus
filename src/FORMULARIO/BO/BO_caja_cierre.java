/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_caja_cierre {

    private DAO_caja_cierre cjcie_dao = new DAO_caja_cierre();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private DAO_caja_detalle cdao=new DAO_caja_detalle();
    public void insertar_caja_cierre(caja_cierre cjcie,caja_detalle caja) {
        String titulo = "insertar_caja_cierre";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            cjcie_dao.insertar_caja_cierre(conn, cjcie);
            cdao.insertar_caja_detalle(conn, caja);
//            cjcie_dao.actualizar_tabla_caja_cierre(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evemen.mensaje_error(e, cjcie.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evemen.Imprimir_serial_sql_error(e1, cjcie.toString(), titulo);
            }
        }
    }

    public void update_caja_cierre(caja_cierre cjcie, JTable tbltabla) {
        if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_cierre";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                cjcie_dao.update_caja_cierre(conn, cjcie);
                cjcie_dao.actualizar_tabla_caja_cierre(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evemen.mensaje_error(e, cjcie.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evemen.Imprimir_serial_sql_error(e1, cjcie.toString(), titulo);
                }
            }
        }
    }
}
