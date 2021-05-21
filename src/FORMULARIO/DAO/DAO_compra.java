package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.compra;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

public class DAO_compra {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "COMPRA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "COMPRA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO compra(idcompra,fecha_emision,estado,observacion,forma_pago,monto_compra,fk_idproveedor,fk_idusuario,indice) VALUES (?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE compra SET fecha_emision=?,estado=?,observacion=?,forma_pago=?,monto_compra=?,fk_idproveedor=?,fk_idusuario=?,indice=? WHERE idcompra=?;";
    private String sql_select = "select c.idcompra as idc,c.indice,to_char(c.fecha_emision,'dd-MM-yyyy HH24:MI') as fecha, \n"
            + "p.nombre as provee,p.ruc,c.estado,TRIM(to_char(c.monto_compra,'999G999G999')) as monto,u.usuario\n"
            + "from compra c,proveedor p,usuario u \n"
            + "where c.fk_idproveedor=p.idproveedor \n"
            + "and c.fk_idusuario=u.idusuario\n"
            + "order by 1 desc;";
    private String sql_cargar = "SELECT idcompra,fecha_emision,estado,observacion,forma_pago,monto_compra,fk_idproveedor,fk_idusuario,indice FROM compra WHERE idcompra=";
    private String sql_estado = "UPDATE public.compra\n"
            + "   SET estado=?\n"
            + " WHERE indice=?;";

    public void insertar_compra(Connection conn, compra com) {
        com.setC1idcompra(eveconn.getInt_ultimoID_mas_uno(conn, com.getTb_compra(), com.getId_idcompra()));
        String titulo = "insertar_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, com.getC1idcompra());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, com.getC3estado());
            pst.setString(4, com.getC4observacion());
            pst.setString(5, com.getC5forma_pago());
            pst.setDouble(6, com.getC6monto_compra());
            pst.setInt(7, com.getC7fk_idproveedor());
            pst.setInt(8, com.getC8fk_idusuario());
            pst.setString(9, com.getC9indice());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + com.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + com.toString(), titulo);
        }
    }

    public void update_compra(Connection conn, compra com) {
        String titulo = "update_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, com.getC3estado());
            pst.setString(3, com.getC4observacion());
            pst.setString(4, com.getC5forma_pago());
            pst.setDouble(5, com.getC6monto_compra());
            pst.setInt(6, com.getC7fk_idproveedor());
            pst.setInt(7, com.getC8fk_idusuario());
            pst.setInt(8, com.getC1idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + com.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + com.toString(), titulo);
        }
    }

    public void cargar_compra(Connection conn, compra com, int id) {
        String titulo = "Cargar_compra";
//        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                com.setC1idcompra(rs.getInt(1));
                com.setC2fecha_emision(rs.getString(2));
                com.setC3estado(rs.getString(3));
                com.setC4observacion(rs.getString(4));
                com.setC5forma_pago(rs.getString(5));
                com.setC6monto_compra(rs.getDouble(6));
                com.setC7fk_idproveedor(rs.getInt(7));
                com.setC8fk_idusuario(rs.getInt(8));
                com.setC9indice(rs.getString(9));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + com.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + com.toString(), titulo);
        }
    }

    public void actualizar_tabla_compra(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_compra(tbltabla);
    }

    public void ancho_tabla_compra(JTable tbltabla) {
        int Ancho[] = {5, 10, 15, 25, 10, 15, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void update_estado_compra(Connection conn, compra com) {
        String titulo = "update_estado_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_estado);
            pst.setString(1, com.getC3estado());
            pst.setString(2, com.getC9indice());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_estado + "\n" + com.toString(), titulo);
//            evemen.modificado_correcto(mensaje_terminar, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_estado + "\n" + com.toString(), titulo);
        }
    }

    public double getDouble_suma_compra(Connection conn, String campo, String filtro) {
        double sumaventa = 0;
        String titulo = "getDouble_suma_compra";
        String sql = "select count(*) as cantidad,sum(c.monto_compra) as sumaventa\n"
                + "from compra c,proveedor p,usuario u\n"
                + "where c.fk_idproveedor=p.idproveedor\n"
                + "and c.fk_idusuario=u.idusuario\n"
                + " " + filtro + "\n"
                + " ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            if (rs.next()) {
                sumaventa = rs.getDouble(campo);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return sumaventa;
    }

    public void imprimir_rep_compra_todo(Connection conn, String filtro) {
        String sql = "select c.idcompra as idcom,to_char(c.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "p.ruc as ruc,('('||p.idproveedor||')'||p.nombre) as proveedor,\n"
                + "c.estado as estado,c.monto_compra as monto,u.usuario as usuario\n"
                + "from compra c,proveedor p,usuario u\n"
                + "where c.fk_idproveedor=p.idproveedor\n"
                + "and c.fk_idusuario=u.idusuario\n"
                + " " + filtro + "\n"
                + "order by c.idcompra desc;";
        String titulonota = "COMPRA TODOS";
        String direccion = "src/REPORTE/COMPRA/repCompraTodo.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_rep_compra_detalle(Connection conn, String filtro) {
        String sql = "select c.idcompra as idcom,to_char(c.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "('('||p.idproveedor||')'||p.nombre) as proveedor,\n"
                + "c.estado as estado,\n"
                + "('('||ic.fk_idproducto||')-'||ic.descripcion) as producto,\n"
                + "ic.precio_compra as precio,ic.cantidad as cant,(ic.cantidad*ic.precio_compra) as subtotal\n"
                + "from compra c,proveedor p,usuario u,item_compra ic\n"
                + "where c.fk_idproveedor=p.idproveedor\n"
                + "and c.idcompra=ic.fk_idcompra\n"
                + "and c.fk_idusuario=u.idusuario\n"
                + " " + filtro + "\n"
                + "order by c.idcompra desc;";
        String titulonota = "COMPRA DETALLE";
        String direccion = "src/REPORTE/COMPRA/repCompraDetalle.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
