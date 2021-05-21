package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_producto_grupo;
import FORMULARIO.ENTIDAD.producto_grupo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_producto_grupo {

    private DAO_producto_grupo pgru_dao = new DAO_producto_grupo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_producto_grupo(producto_grupo pgru, JTable tbltabla) {
        String titulo = "insertar_producto_grupo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pgru_dao.insertar_producto_grupo(conn, pgru);
            pgru_dao.actualizar_tabla_producto_grupo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, pgru.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, pgru.toString(), titulo);
            }
        }
    }

    public void update_producto_grupo(producto_grupo pgru, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO_GRUPO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_grupo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pgru_dao.update_producto_grupo(conn, pgru);
                pgru_dao.actualizar_tabla_producto_grupo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, pgru.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, pgru.toString(), titulo);
                }
            }
        }
    }
}
