/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.ENTIDAD.usuario;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_usuario {
  private DAO_usuario udao = new DAO_usuario();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_usuario(usuario usu, JTable tbltabla) {
        String titulo = "insertar_usuario";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            udao.insertar_usuario(conn, usu);
            udao.actualizar_tabla_usuario(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, usu.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, usu.toString(), titulo);
            }
        }
    }
    public void insertar_usuario(usuario usu) {
        String titulo = "insertar_usuario";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            udao.insertar_usuario(conn, usu);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, usu.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, usu.toString(), titulo);
            }
        }
    }
    public void update_usuario(usuario usu, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE USUARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_usuario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                udao.update_usuario(conn, usu);
                udao.actualizar_tabla_usuario(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, usu.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, usu.toString(), titulo);
                }
            }
        }
    }  
}
