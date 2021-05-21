package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_venta_mesa;
import FORMULARIO.ENTIDAD.venta_mesa;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_mesa {

    private DAO_venta_mesa vmesa_dao = new DAO_venta_mesa();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_venta_mesa(venta_mesa vmesa, JTable tbltabla) {
        String titulo = "insertar_venta_mesa";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vmesa_dao.insertar_venta_mesa(conn, vmesa);
            vmesa_dao.actualizar_tabla_venta_mesa(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, vmesa.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vmesa.toString(), titulo);
            }
        }
    }

    public void update_venta_mesa(venta_mesa vmesa, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_MESA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_mesa";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vmesa_dao.update_venta_mesa(conn, vmesa);
                vmesa_dao.actualizar_tabla_venta_mesa(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, vmesa.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vmesa.toString(), titulo);
                }
            }
        }
    }
    public void update_venta_mesa_Estado(venta_mesa vmesa) {
//        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_MESA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_mesa_Estado";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vmesa_dao.update_venta_mesaEstado(conn, vmesa);
//                vmesa_dao.actualizar_tabla_venta_mesa(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, vmesa.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vmesa.toString(), titulo);
                }
            }
//        }
    }
}
