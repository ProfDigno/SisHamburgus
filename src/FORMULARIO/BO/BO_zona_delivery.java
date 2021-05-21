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
public class BO_zona_delivery {

    private DAO_zona_delivery zdao = new DAO_zona_delivery();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_zona_delivery(zona_delivery zona, JTable tbltabla) {
        String titulo = "insertar_zona_delivery";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            zdao.insertar_zona_delivery(conn, zona);
            zdao.actualizar_tabla_zona_delivery(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, zona.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, zona.toString(), titulo);
            }
        }
    }

    public void update_zona_delivery(zona_delivery zona, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTA ZONA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_zona_delivery";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                zdao.update_zona_delivery(conn, zona);
                zdao.actualizar_tabla_zona_delivery(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, zona.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, zona.toString(), titulo);
                }
            }
        }
    }
}
