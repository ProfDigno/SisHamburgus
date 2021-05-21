/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.ENTIDAD.gasto_tipo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_gasto_tipo {
    private DAO_gasto_tipo edao = new DAO_gasto_tipo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_gasto_tipo(gasto_tipo gtipo, JTable tbltabla) {
        String titulo = "insertar_gasto_tipo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            edao.insertar_gasto_tipo(conn, gtipo);
            edao.actualizar_tabla_gasto_tipo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, gtipo.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, gtipo.toString(), titulo);
            }
        }
    }

    public void update_gasto_tipo(gasto_tipo gtipo, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE ENTREGADOR", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_gasto_tipo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                edao.update_gasto_tipo(conn, gtipo);
                edao.actualizar_tabla_gasto_tipo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, gtipo.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, gtipo.toString(), titulo);
                }
            }
        }
    }
}
