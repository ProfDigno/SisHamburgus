/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_insumo_unidad;
import FORMULARIO.ENTIDAD.insumo_unidad;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_insumo_unidad {
    private DAO_insumo_unidad pcdao = new DAO_insumo_unidad();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_insumo_unidad(insumo_unidad ingre, JTable tbltabla) {
        String titulo = "insertar_insumo_unidad";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pcdao.insertar_insumo_unidad(conn, ingre);
            pcdao.actualizar_tabla_insumo_unidad(conn, tbltabla);
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

    public void update_insumo_unidad(insumo_unidad ingre, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTA UNIDAD", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_insumo_unidad";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pcdao.update_insumo_unidad(conn, ingre);
                pcdao.actualizar_tabla_insumo_unidad(conn, tbltabla);
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
}
