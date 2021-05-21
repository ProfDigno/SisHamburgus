/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_compra_insumo;
import FORMULARIO.DAO.DAO_item_compra_insumo;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.compra_insumo;
import FORMULARIO.ENTIDAD.item_compra_insumo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_compra_insumo {

    private DAO_compra_insumo vdao = new DAO_compra_insumo();
    private DAO_item_compra_insumo ivdao = new DAO_item_compra_insumo();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();

    public boolean getBoolean_compra_insumo(JTable tblitem_compra_insumo, compra_insumo comp) {
        boolean insertado = false;
        String titulo = "getBoolean_compra_insumo";
        Connection conn = null;
        try {
            conn = ConnPostgres.getConnPosgres();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.insertar_compra_insumo(conn, comp);
            ivdao.insertar_item_compra_insumo_de_tabla(conn, tblitem_compra_insumo, comp);
//            cdao.insertar_caja_detalle(conn, caja);
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            insertado = false;
            evmen.mensaje_error(e, comp.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, comp.toString(), titulo);
            }
        }
        return insertado;
    }

    public boolean getBoolean_update_estado_compra_insumo(compra_insumo comp, caja_detalle caja,boolean anularcaja) {
        boolean anulado = false;
//        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR ESTA COMPRA", "ANULAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_anular_compra_insumo";
            Connection conn = null;
            try {
                conn = ConnPostgres.getConnPosgres();
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vdao.update_estado_compra_insumo(conn, comp);
                if(anularcaja){
                cdao.anular_caja_detalle(conn, caja);
                }
                conn.commit();
                anulado = true;
            } catch (SQLException e) {
                anulado = false;
                evmen.mensaje_error(e, comp.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, comp.toString(), titulo);
                }
            }
//        }
        return anulado;
    }

}
