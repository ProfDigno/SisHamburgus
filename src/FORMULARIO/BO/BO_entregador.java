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
public class BO_entregador {

    private DAO_entregador edao = new DAO_entregador();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_entregador(entregador entre, JTable tbltabla) {
        String titulo = "insertar_entregador";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            edao.insertar_entregador(conn, entre);
            edao.actualizar_tabla_entregador(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, entre.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, entre.toString(), titulo);
            }
        }
    }

    public void update_entregador(entregador entre, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE ENTREGADOR", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_entregador";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                edao.update_entregador(conn, entre);
                edao.actualizar_tabla_entregador(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, entre.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, entre.toString(), titulo);
                }
            }
        }
    }
}
