/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_itemven_insumo;
import FORMULARIO.ENTIDAD.itemven_insumo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_itemven_insumo {
   private DAO_itemven_insumo ividao = new DAO_itemven_insumo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_itemven_insumo(itemven_insumo iteminsu,int fk_idventa) {
        String titulo = "insertar_itemven_insumo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ividao.recorrer_item_venta(conn, iteminsu, fk_idventa);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, iteminsu.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, iteminsu.toString(), titulo);
            }
        }
    } 
}
