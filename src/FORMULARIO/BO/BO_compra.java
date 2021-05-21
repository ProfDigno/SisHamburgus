package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_item_compra;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.compra;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_compra {

    private DAO_compra com_dao = new DAO_compra();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    private DAO_item_compra icdao=new DAO_item_compra();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
   public boolean getBoolean_compra(JTable tblitem_compra_insumo, compra comp,caja_detalle caja) {
        boolean insertado = false;
        String titulo = "getBoolean_compra";
        Connection conn = null;
        try {
            conn = ConnPostgres.getConnPosgres();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.insertar_compra(conn, comp);
            icdao.insertar_item_compra_de_tabla(conn, tblitem_compra_insumo, comp);
            cdao.insertar_caja_detalle(conn, caja);
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
    public void insertar_compra(compra com, JTable tbltabla) {
        String titulo = "insertar_compra";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.insertar_compra(conn, com);
            com_dao.actualizar_tabla_compra(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, com.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
            }
        }
    }

    public void update_compra(compra com, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMPRA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_compra";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                com_dao.update_compra(conn, com);
                com_dao.actualizar_tabla_compra(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, com.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
                }
            }
        }
    }
    public void update_anular_compra(Connection conn, compra comp, caja_detalle caja) {
        String titulo = "update_anular_compra";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.update_estado_compra(conn, comp);
            cdao.anular_caja_detalle(conn, caja);
            icdao.descontar_stock_producto(conn, comp);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, comp.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, comp.toString(), titulo);
            }
        }
    }
}
