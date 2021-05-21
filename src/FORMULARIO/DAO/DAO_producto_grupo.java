package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_grupo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_grupo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_GRUPO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_GRUPO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_grupo(idproducto_grupo,nombre) VALUES (?,?);";
    private String sql_update = "UPDATE producto_grupo SET nombre=? WHERE idproducto_grupo=?;";
    private String sql_select = "SELECT idproducto_grupo,nombre FROM producto_grupo order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_grupo,nombre FROM producto_grupo WHERE idproducto_grupo=";

    public void insertar_producto_grupo(Connection conn, producto_grupo pgru) {
        pgru.setC1idproducto_grupo(eveconn.getInt_ultimoID_mas_uno(conn, pgru.getTb_producto_grupo(), pgru.getId_idproducto_grupo()));
        String titulo = "insertar_producto_grupo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, pgru.getC1idproducto_grupo());
            pst.setString(2, pgru.getC2nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + pgru.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + pgru.toString(), titulo);
        }
    }

    public void update_producto_grupo(Connection conn, producto_grupo pgru) {
        String titulo = "update_producto_grupo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, pgru.getC2nombre());
            pst.setInt(2, pgru.getC1idproducto_grupo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + pgru.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + pgru.toString(), titulo);
        }
    }

    public void cargar_producto_grupo(Connection conn, producto_grupo pgru, JTable tabla) {
        String titulo = "Cargar_producto_grupo";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                pgru.setC1idproducto_grupo(rs.getInt(1));
                pgru.setC2nombre(rs.getString(2));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pgru.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pgru.toString(), titulo);
        }
    }
    public void cargar_producto_grupo(Connection conn, producto_grupo pgru,int id) {
        String titulo = "Cargar_producto_grupo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                pgru.setC1idproducto_grupo(rs.getInt(1));
                pgru.setC2nombre(rs.getString(2));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pgru.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pgru.toString(), titulo);
        }
    }
    public void actualizar_tabla_producto_grupo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_grupo(tbltabla);
    }

    public void ancho_tabla_producto_grupo(JTable tbltabla) {
        int Ancho[] = {20, 80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
