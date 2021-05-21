package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_venta_mesa_venta;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
//	EvenFecha evefec = new EvenFecha();
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_venta_mesa_venta {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "ITEM_VENTA_MESA_VENTA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_VENTA_MESA_VENTA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_venta_mesa_venta(iditem_venta_mesa_venta,fk_idventa,fk_iditem_venta_mesa) VALUES (?,?,?);";
    private String sql_update = "UPDATE item_venta_mesa_venta SET fk_idventa=?,fk_iditem_venta_mesa=? WHERE iditem_venta_mesa_venta=?;";
    private String sql_select = "SELECT iditem_venta_mesa_venta,fk_idventa,fk_iditem_venta_mesa FROM item_venta_mesa_venta order by 1 desc;";
    private String sql_cargar = "SELECT iditem_venta_mesa_venta,fk_idventa,fk_iditem_venta_mesa FROM item_venta_mesa_venta WHERE iditem_venta_mesa_venta=";

    public void insertar_item_venta_mesa_venta(Connection conn, item_venta_mesa_venta ivmv) {
        ivmv.setC1iditem_venta_mesa_venta(eveconn.getInt_ultimoID_mas_uno(conn, ivmv.getTb_item_venta_mesa_venta(), ivmv.getId_iditem_venta_mesa_venta()));
        String titulo = "insertar_item_venta_mesa_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ivmv.getC1iditem_venta_mesa_venta());
            pst.setInt(2, ivmv.getC2fk_idventa());
            pst.setInt(3, ivmv.getC3fk_iditem_venta_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ivmv.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ivmv.toString(), titulo);
        }
    }

    public void update_item_venta_mesa_venta(Connection conn, item_venta_mesa_venta ivmv) {
        String titulo = "update_item_venta_mesa_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, ivmv.getC2fk_idventa());
            pst.setInt(2, ivmv.getC3fk_iditem_venta_mesa());
            pst.setInt(3, ivmv.getC1iditem_venta_mesa_venta());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ivmv.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ivmv.toString(), titulo);
        }
    }

    public void cargar_item_venta_mesa_venta(Connection conn, item_venta_mesa_venta ivmv, JTable tabla) {
        String titulo = "Cargar_item_venta_mesa_venta";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                ivmv.setC1iditem_venta_mesa_venta(rs.getInt(1));
                ivmv.setC2fk_idventa(rs.getInt(2));
                ivmv.setC3fk_iditem_venta_mesa(rs.getInt(3));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ivmv.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ivmv.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_venta_mesa_venta(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_venta_mesa_venta(tbltabla);
    }

    public void ancho_tabla_item_venta_mesa_venta(JTable tbltabla) {
        int Ancho[] = {33, 33, 33};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
