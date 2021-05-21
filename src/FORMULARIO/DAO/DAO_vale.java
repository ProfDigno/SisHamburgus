/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.vale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_vale {
     EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "VALE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VALE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.vale(\n"
            + "            idvale, fecha_emision, descripcion, monto_vale, equipo,estado, fk_idcliente, \n"
            + "            fk_idusuario,indice)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?,?,?);";
    private String sql_update = "UPDATE public.vale\n"
            + "   SET fecha_emision=?,descripcion=?, monto_vale=?,  \n"
            + "       fk_idcliente=?, fk_idusuario=?\n"
            + " WHERE idvale=?;";
    private String sql_anular = "UPDATE public.vale\n"
            + "   SET estado=?\n"
            + " WHERE indice=?;";
    private String sql_select = "select v.idvale,v.indice,to_char(v.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,\n"
            + "v.descripcion,cl.nombre as cliente,\n"
            + "TRIM(to_char(v.monto_vale,'999G999G999')) AS monto,\n"
            + "v.estado,v.equipo\n"
            + " from vale v,cliente cl\n"
            + "where v.fk_idcliente=cl.idcliente\n";
    private String sql_cargar = "select v.*,to_char(v.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,cl.nombre as cliente "
            + " from vale v,cliente cl\n"
            + "where v.fk_idcliente=cl.idcliente\n"
            + "and v.idvale=";
    private String sql_suma_monto = "select sum(v.monto_vale) as suma "
            + " from vale v,cliente cl\n"
            + "where v.fk_idcliente=cl.idcliente\n"
            + "and v.estado='EMITIDO'\n";
    private String sql_suma_cant = "select count(*) as cant "
            + " from vale v,cliente cl\n"
            + "where v.fk_idcliente=cl.idcliente\n"
            + "and v.estado='EMITIDO'\n";
    public void cargar_vale(vale vale, JTable tabla) {
        String titulo = "cargar_vale";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                vale.setIdvale(id);
                vale.setFecha_emision(rs.getString("fecha"));
                vale.setDescripcion(rs.getString("descripcion"));
                vale.setMonto_vale(rs.getDouble("monto_vale"));
                vale.setEquipo(rs.getString("equipo"));
                vale.setEstado(rs.getString("estado"));
                vale.setFk_idcliente(rs.getInt("fk_idcliente"));
                vale.setFk_idusuario(rs.getInt("fk_idusuario"));
                vale.setIndice(rs.getString("indice"));
                vale.setCliente(rs.getString("cliente"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vale.toString(), titulo);
        }
    }

    public void insertar_vale(Connection conn, vale vale) {
        vale.setIdvale(eveconn.getInt_ultimoID_mas_uno(conn, vale.getTabla(), vale.getIdtabla()));
        String titulo = "insertar_vale";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, vale.getIdvale());
            pst.setTimestamp(2, evefec.getTimestamp_fecha_cargado(vale.getFecha_emision()));
            pst.setString(3, vale.getDescripcion());
            pst.setDouble(4, vale.getMonto_vale());
            pst.setString(5, vale.getEquipo());
            pst.setString(6, vale.getEstado());
            pst.setInt(7, vale.getFk_idcliente());
            pst.setInt(8, vale.getFk_idusuario());
            pst.setString(9, vale.getIndice());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + vale.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + vale.toString(), titulo);
        }
    }

    public void update_vale(Connection conn, vale vale) {
        String titulo = "update_vale";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_fecha_cargado(vale.getFecha_emision()));
            pst.setString(2, vale.getDescripcion());
            pst.setDouble(3, vale.getMonto_vale());
            pst.setInt(4, vale.getFk_idcliente());
            pst.setInt(5, vale.getFk_idusuario());
            pst.setInt(6, vale.getIdvale());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vale.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vale.toString(), titulo);
        }
    }
    public void update_vale_anular(Connection conn, vale vale) {
        String titulo = "update_vale_anular";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setString(1, vale.getEstado());
            pst.setString(2, vale.getIndice());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + vale.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_anular + "\n" + vale.toString(), titulo);
        }
    }
    public void actualizar_tabla_vale(Connection conn, JTable tbltabla,String filtro,String orden) {
        eveconn.Select_cargar_jtable(conn, sql_select+filtro+orden, tbltabla);
        everende.rendertabla_estados(tbltabla, 6);
        ancho_tabla_vale(tbltabla);
    }

    public void ancho_tabla_vale(JTable tbltabla) {
        int Ancho[] = {5,5,10,20,20,8,8,14};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public double sumar_monto_vale(Connection conn,String filtro){
        return eveconn.getdouble_sql_suma(conn, sql_suma_monto+filtro);
    }
    public double sumar_cantidad_vale(Connection conn,String filtro){
        return eveconn.getdouble_sql_suma(conn, sql_suma_cant+filtro);
    }
}
