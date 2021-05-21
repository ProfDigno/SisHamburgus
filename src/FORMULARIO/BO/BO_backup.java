/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_backup;
import FORMULARIO.ENTIDAD.backup;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_backup {

    private DAO_backup bdao = new DAO_backup();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_backup(backup bac) {
        String titulo = "insertar_backup";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            bdao.insertar_backup(conn, bac);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, bac.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, bac.toString(), titulo);
            }
        }
    }

    public void update_backup(backup bac) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE BACKUP", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_backup";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                bdao.update_backup(conn, bac);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, bac.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, bac.toString(), titulo);
                }
            }
        }
    }

    public void update_backup_creado_hoy() {
        String titulo = "update_backup_creado_hoy";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            bdao.update_backup_creado_hoy(conn);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, "ERROR", titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, "ERROR", titulo);
            }
        }
    }
}
