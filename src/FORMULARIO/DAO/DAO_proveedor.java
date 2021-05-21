package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.proveedor;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_proveedor {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PROVEEDOR GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PROVEEDOR MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO proveedor(idproveedor,fecha_inicio,nombre,direccion,telefono,ruc) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE proveedor SET fecha_inicio=?,nombre=?,direccion=?,telefono=?,ruc=? WHERE idproveedor=?;";
    private String sql_select = "SELECT idproveedor,nombre,telefono,ruc FROM proveedor order by 1 desc;";
    private String sql_cargar = "SELECT idproveedor,fecha_inicio,nombre,direccion,telefono,ruc FROM proveedor WHERE idproveedor=";

    public void insertar_proveedor(Connection conn, proveedor prov) {
        prov.setC1idproveedor(eveconn.getInt_ultimoID_mas_uno(conn, prov.getTb_proveedor(), prov.getId_idproveedor()));
        String titulo = "insertar_proveedor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prov.getC1idproveedor());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prov.getC3nombre());
            pst.setString(4, prov.getC4direccion());
            pst.setString(5, prov.getC5telefono());
            pst.setString(6, prov.getC6ruc());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prov.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prov.toString(), titulo);
        }
    }

    public void update_proveedor(Connection conn, proveedor prov) {
        String titulo = "update_proveedor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prov.getC3nombre());
            pst.setString(3, prov.getC4direccion());
            pst.setString(4, prov.getC5telefono());
            pst.setString(5, prov.getC6ruc());
            pst.setInt(6, prov.getC1idproveedor());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prov.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prov.toString(), titulo);
        }
    }

    public void cargar_proveedor(Connection conn, proveedor prov, int id) {
        String titulo = "Cargar_proveedor";
//        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                prov.setC1idproveedor(rs.getInt(1));
                prov.setC2fecha_inicio(rs.getString(2));
                prov.setC3nombre(rs.getString(3));
                prov.setC4direccion(rs.getString(4));
                prov.setC5telefono(rs.getString(5));
                prov.setC6ruc(rs.getString(6));
                prov.setIdproveedor_static(rs.getInt(1));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prov.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prov.toString(), titulo);
        }
    }

    public void actualizar_tabla_proveedor(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_proveedor(tbltabla);
    }

    public void ancho_tabla_proveedor(JTable tbltabla) {
        int Ancho[] = {10, 60, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_proveedor_buscar(Connection conn, JTable tbltabla,String campo,String buscar) {
        String sql_select_buscar = "SELECT idproveedor as id,nombre,ruc,telefono "
            + "FROM proveedor where "
            + " "+campo+" ilike'%"+buscar+"%' "
            + "order by nombre desc limit 100;";
        eveconn.Select_cargar_jtable(conn, sql_select_buscar, tbltabla);
        ancho_tabla_proveedor(tbltabla);
    }
}
