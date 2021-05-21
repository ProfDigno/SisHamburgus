package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_compra;
import FORMULARIO.ENTIDAD.item_compra;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_item_compra {

    private DAO_item_compra itemc_dao = new DAO_item_compra();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_compra(item_compra itemc, JTable tbltabla) {
        String titulo = "insertar_item_compra";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            itemc_dao.insertar_item_compra(conn, itemc);
//			itemc_dao.actualizar_tabla_item_compra(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, itemc.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, itemc.toString(), titulo);
            }
        }
    }

    public void update_item_compra(item_compra itemc, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_COMPRA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_item_compra";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                itemc_dao.update_item_compra(conn, itemc);
//				itemc_dao.actualizar_tabla_item_compra(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, itemc.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, itemc.toString(), titulo);
                }
            }
        }
    }
}
