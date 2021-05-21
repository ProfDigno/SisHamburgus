package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_venta_mesa;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
//	EvenFecha evefec = new EvenFecha();
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_venta_mesa {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "ITEM_VENTA_MESA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_VENTA_MESA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_venta_mesa(iditem_venta_mesa,fecha_inicio,fecha_cierre,estado,totalmonto,fk_idventa_mesa) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_venta_mesa SET fecha_inicio=?,fecha_cierre=?,estado=?,totalmonto=?,fk_idventa_mesa=? WHERE iditem_venta_mesa=?;";
    private String sql_updateMonto = "UPDATE item_venta_mesa SET totalmonto=? WHERE iditem_venta_mesa=?;";
    private String sql_updateCERRAR = "UPDATE item_venta_mesa SET fecha_cierre=?,estado=? WHERE iditem_venta_mesa=?;";
    private String sql_select = "SELECT iditem_venta_mesa,fecha_inicio,fecha_cierre,estado,totalmonto,fk_idventa_mesa FROM item_venta_mesa order by 1 desc;";
    private String sql_cargar = "SELECT iditem_venta_mesa,fecha_inicio,fecha_cierre,estado,totalmonto,fk_idventa_mesa FROM item_venta_mesa WHERE iditem_venta_mesa=";

    public void insertar_item_venta_mesa(Connection conn, item_venta_mesa ivm) {
        ivm.setC1iditem_venta_mesa(eveconn.getInt_ultimoID_mas_uno(conn, ivm.getTb_item_venta_mesa(), ivm.getId_iditem_venta_mesa()));
        String titulo = "insertar_item_venta_mesa";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ivm.getC1iditem_venta_mesa());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, ivm.getC4estado());
            pst.setDouble(5, ivm.getC5totalmonto());
            pst.setInt(6, ivm.getC6fk_idventa_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ivm.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ivm.toString(), titulo);
        }
    }

    public void update_item_venta_mesa(Connection conn, item_venta_mesa ivm) {
        String titulo = "update_item_venta_mesa";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ivm.getC4estado());
            pst.setDouble(4, ivm.getC5totalmonto());
            pst.setInt(5, ivm.getC6fk_idventa_mesa());
            pst.setInt(6, ivm.getC1iditem_venta_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ivm.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ivm.toString(), titulo);
        }
    }
    public void update_item_venta_mesa_monto(Connection conn, item_venta_mesa ivm) {
        String titulo = "update_item_venta_mesa_monto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_updateMonto);
//            pst.setTimestamp(1, evefec.getTimestamp_sistema());
//            pst.setTimestamp(2, evefec.getTimestamp_sistema());
//            pst.setString(3, ivm.getC4estado());
            pst.setDouble(1, ivm.getC5totalmonto());
//            pst.setInt(5, ivm.getC6fk_idventa_mesa());
            pst.setInt(2, ivm.getC1iditem_venta_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_updateMonto + "\n" + ivm.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_updateMonto + "\n" + ivm.toString(), titulo);
        }
    }
    public void update_item_venta_mesa_monto_CERRAR(Connection conn, item_venta_mesa ivm) {
        String titulo = "update_item_venta_mesa_monto_CERRAR";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_updateCERRAR);
//            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ivm.getC4estado());
//            pst.setDouble(1, ivm.getC5totalmonto());
//            pst.setInt(5, ivm.getC6fk_idventa_mesa());
            pst.setInt(3, ivm.getC1iditem_venta_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_updateCERRAR + "\n" + ivm.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_updateCERRAR + "\n" + ivm.toString(), titulo);
        }
    }
    public void cargar_item_venta_mesa(Connection conn, item_venta_mesa ivm, JTable tabla) {
        String titulo = "Cargar_item_venta_mesa";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                ivm.setC1iditem_venta_mesa(rs.getInt(1));
                ivm.setC2fecha_inicio(rs.getString(2));
                ivm.setC3fecha_cierre(rs.getString(3));
                ivm.setC4estado(rs.getString(4));
                ivm.setC5totalmonto(rs.getDouble(5));
                ivm.setC6fk_idventa_mesa(rs.getInt(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ivm.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ivm.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_venta_mesa(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_venta_mesa(tbltabla);
    }

    public void ancho_tabla_item_venta_mesa(JTable tbltabla) {
        int Ancho[] = {16, 16, 16, 16, 16, 16};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
