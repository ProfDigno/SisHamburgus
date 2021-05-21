/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_insumo_categoria;
import FORMULARIO.ENTIDAD.insumo_categoria;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_insumo_categoria {
    private DAO_insumo_categoria pcdao = new DAO_insumo_categoria();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_insumo_categoria(insumo_categoria cate, JTable tbltabla) {
        String titulo = "insertar_insumo_categoria";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pcdao.insertar_insumo_categoria(conn, cate);
            pcdao.actualizar_tabla_insumo_categoria(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, cate.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cate.toString(), titulo);
            }
        }
    }

    public void update_insumo_categoria(insumo_categoria cate, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTA CATEGORIA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_insumo_categoria";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pcdao.update_insumo_categoria(conn, cate);
                pcdao.actualizar_tabla_insumo_categoria(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, cate.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, cate.toString(), titulo);
                }
            }
        }
    }
}
