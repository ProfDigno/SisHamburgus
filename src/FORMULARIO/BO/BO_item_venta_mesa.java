package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_venta_mesa;
import FORMULARIO.ENTIDAD.item_venta_mesa;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_item_venta_mesa {

    private DAO_item_venta_mesa ivm_dao = new DAO_item_venta_mesa();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_venta_mesa(item_venta_mesa ivm) {
        String titulo = "insertar_item_venta_mesa";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ivm_dao.insertar_item_venta_mesa(conn, ivm);
//            ivm_dao.actualizar_tabla_item_venta_mesa(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ivm.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ivm.toString(), titulo);
            }
        }
    }

    public void update_item_venta_mesa(item_venta_mesa ivm) {
            String titulo = "update_item_venta_mesa";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ivm_dao.update_item_venta_mesa(conn, ivm);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ivm.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ivm.toString(), titulo);
                }
            }
    }
    public void update_item_venta_mesa_Monto(item_venta_mesa ivm) {
            String titulo = "update_item_venta_mesa_Monto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ivm_dao.update_item_venta_mesa_monto(conn, ivm);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ivm.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ivm.toString(), titulo);
                }
            }
    }
    public void update_item_venta_mesa_CERRAR(item_venta_mesa ivm) {
            String titulo = "update_item_venta_mesa_CERRAR";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ivm_dao.update_item_venta_mesa_monto_CERRAR(conn, ivm);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ivm.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ivm.toString(), titulo);
                }
            }
    }
}
