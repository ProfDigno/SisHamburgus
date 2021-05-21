package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_proveedor;
import FORMULARIO.ENTIDAD.proveedor;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_proveedor {

    private DAO_proveedor prov_dao = new DAO_proveedor();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_proveedor(proveedor prov, JTable tbltabla) {
        String titulo = "insertar_proveedor";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            prov_dao.insertar_proveedor(conn, prov);
            prov_dao.actualizar_tabla_proveedor(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, prov.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, prov.toString(), titulo);
            }
        }
    }

    public void update_proveedor(proveedor prov, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PROVEEDOR", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_proveedor";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                prov_dao.update_proveedor(conn, prov);
                prov_dao.actualizar_tabla_proveedor(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, prov.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, prov.toString(), titulo);
                }
            }
        }
    }
}
