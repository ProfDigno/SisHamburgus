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
public class BO_cliente {

    private DAO_cliente pidao = new DAO_cliente();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_cliente(Connection conn, cliente ingre, JTable tbltabla, boolean cargar_json) {
        String titulo = "insertar_cliente";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pidao.insertar_cliente(conn, ingre, true, cargar_json);
            pidao.actualizar_tabla_cliente(conn, tbltabla);
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

    public void insertar_cliente_directo(Connection conn, cliente ingre, boolean cargar_json) {
        String titulo = "insertar_cliente_directo";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pidao.insertar_cliente(conn, ingre, false, cargar_json);
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

    public void update_cliente(cliente ingre, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_cliente";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pidao.update_cliente(conn, ingre);
                pidao.actualizar_tabla_cliente(conn, tbltabla);
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

    public void update_cliente_MUDAR_ZONA(cliente ingre) {
        String titulo = "update_cliente_MUDAR_ZONA";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pidao.update_cliente_MUDAR_ZONA(conn, ingre);
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
