/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_item_factura;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.item_factura;
import FORMULARIO.ENTIDAD.factura;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_factura {
    private DAO_factura vdao = new DAO_factura();
    private DAO_item_factura ivdao=new DAO_item_factura();
    private DAO_caja_detalle cdao=new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getBoolean_insertar_factura(JTable tblitem_producto,item_factura item,factura fac) {
        boolean insertado=false;
        String titulo = "insertar_factura";
        Connection conn = null;
        try {
            conn = ConnPostgres.getConnPosgres();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.insertar_factura(conn, fac); 
            ivdao.insertar_item_factura_de_tabla(conn, tblitem_producto, item, fac);
//            cdao.insertar_caja_detalle(conn, caja);
            conn.commit();
            insertado=true;
        } catch (SQLException e) {
            insertado=false;
            evmen.mensaje_error(e, fac.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, fac.toString(), titulo);
            }
        }
        return insertado;
    }
    public boolean getBoolean_update_anular_factura(factura fac) {
        boolean anulado=false;
        String titulo = "update_anular_factura";
        Connection conn = null;
        try {
            conn = ConnPostgres.getConnPosgres();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_estado_factura(conn, fac);
//            cdao.anular_caja_detalle(conn, caja);
            conn.commit();
            anulado=true;
        } catch (SQLException e) {
            anulado=false;
            evmen.mensaje_error(e, fac.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, fac.toString(), titulo);
            }
        }
        return anulado;
    }
}
