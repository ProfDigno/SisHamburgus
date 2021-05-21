package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_venta_mesa_venta;
import FORMULARIO.ENTIDAD.item_venta_mesa_venta;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_item_venta_mesa_venta {

    private DAO_item_venta_mesa_venta ivmv_dao = new DAO_item_venta_mesa_venta();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_venta_mesa_venta(item_venta_mesa_venta ivmv) {
        String titulo = "insertar_item_venta_mesa_venta";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ivmv_dao.insertar_item_venta_mesa_venta(conn, ivmv);
//            ivmv_dao.actualizar_tabla_item_venta_mesa_venta(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ivmv.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ivmv.toString(), titulo);
            }
        }
    }

    public void update_item_venta_mesa_venta(item_venta_mesa_venta ivmv, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_VENTA_MESA_VENTA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_item_venta_mesa_venta";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ivmv_dao.update_item_venta_mesa_venta(conn, ivmv);
                ivmv_dao.actualizar_tabla_item_venta_mesa_venta(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ivmv.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ivmv.toString(), titulo);
                }
            }
        }
    }
}
