package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.venta_mesa;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;



public class DAO_venta_mesa {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "VENTA_MESA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_MESA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_mesa(idventa_mesa,nombre,estado,monto,habilitar) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE venta_mesa SET nombre=?,monto=?,habilitar=? WHERE idventa_mesa=?;";
    private String sql_updateEst = "UPDATE venta_mesa SET estado=?,monto=? WHERE idventa_mesa=?;";
    private String sql_select = "SELECT idventa_mesa,nombre,estado,monto,habilitar FROM venta_mesa order by 1 desc;";
    private String sql_cargar = "SELECT idventa_mesa,nombre,estado,monto,habilitar FROM venta_mesa WHERE idventa_mesa=";

    public void insertar_venta_mesa(Connection conn, venta_mesa vmesa) {
        vmesa.setC1idventa_mesa(eveconn.getInt_ultimoID_mas_uno(conn, vmesa.getTb_venta_mesa(), vmesa.getId_idventa_mesa()));
        String titulo = "insertar_venta_mesa";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, vmesa.getC1idventa_mesa());
            pst.setString(2, vmesa.getC2nombre());
            pst.setString(3, vmesa.getC3estado());
            pst.setDouble(4, vmesa.getC4monto());
            pst.setBoolean(5, vmesa.getC5habilitar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + vmesa.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + vmesa.toString(), titulo);
        }
    }

    public void update_venta_mesa(Connection conn, venta_mesa vmesa) {
        String titulo = "update_venta_mesa";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, vmesa.getC2nombre());
//            pst.setString(2, vmesa.getC3estado());
            pst.setDouble(2, vmesa.getC4monto());
            pst.setBoolean(3, vmesa.getC5habilitar());
            pst.setInt(4, vmesa.getC1idventa_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vmesa.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vmesa.toString(), titulo);
        }
    }
    public void update_venta_mesaEstado(Connection conn, venta_mesa vmesa) {
        String titulo = "update_venta_mesaEstado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_updateEst);
//            pst.setString(1, vmesa.getC2nombre());
            pst.setString(1, vmesa.getC3estado());
            pst.setDouble(2, vmesa.getC4monto());
//            pst.setBoolean(3, vmesa.getC5habilitar());
            pst.setInt(3, vmesa.getC1idventa_mesa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vmesa.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vmesa.toString(), titulo);
        }
    }
    public void cargar_venta_mesa(Connection conn, venta_mesa vmesa, JTable tabla) {
        String titulo = "Cargar_venta_mesa";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                vmesa.setC1idventa_mesa(rs.getInt(1));
                vmesa.setC2nombre(rs.getString(2));
                vmesa.setC3estado(rs.getString(3));
                vmesa.setC4monto(rs.getDouble(4));
                vmesa.setC5habilitar(rs.getBoolean(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + vmesa.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vmesa.toString(), titulo);
        }
    }
    public int getInt_sumar_venta_mesa(Connection conn,String idmesa) {
        String titulo = "sumar_venta_mesa";
        int totalventa = 0;
        String sql = "select sum(vc.monto_venta) as totalventa \n"
                + "from item_venta_mesa vm,item_venta_mesa_venta ivm,venta vc\n"
                + "where vm.estado='ABIERTO' \n"
                + "and vc.estado='EMITIDO'\n"
                + "and vm.iditem_venta_mesa=ivm.fk_iditem_venta_mesa\n"
                + "and ivm.fk_idventa=vc.idventa\n"
                + "and vm.fk_idventa_mesa=" + idmesa;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                totalventa = rs.getInt("totalventa");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return totalventa;
    }
    public String getString_sumar_venta_mesa(Connection conn,String idmesa) {
        String titulo = "sumar_venta_mesa";
        String totalventa = "0";
        String sql = "select TRIM(to_char(sum(vc.monto_venta),'999G999G999')) as monto_mesa \n"
                + "from item_venta_mesa vm,item_venta_mesa_venta ivm,venta vc\n"
                + "where vm.estado='ABIERTO' \n"
                + "and vc.estado='EMITIDO'\n"
                + "and vm.iditem_venta_mesa=ivm.fk_iditem_venta_mesa\n"
                + "and ivm.fk_idventa=vc.idventa\n"
                + "and vm.fk_idventa_mesa=" + idmesa;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                totalventa = rs.getString("monto_mesa");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return totalventa;
    }
    public int cargar_idventa_mesa(Connection conn,String idmesa) {
        int iditem_venta_mesa = 0;
        String titulo = "cargar_idventa_mesa";
        String sql = "select * from item_venta_mesa \n"
                + "where estado='ABIERTO' \n"
                + "and fk_idventa_mesa=" + idmesa;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                iditem_venta_mesa = rs.getInt("iditem_venta_mesa");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return iditem_venta_mesa;
    }
    public void actualizar_tabla_venta_mesa(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_mesa(tbltabla);
    }

    public void ancho_tabla_venta_mesa(JTable tbltabla) {
        int Ancho[] = {20, 20, 20, 20, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
