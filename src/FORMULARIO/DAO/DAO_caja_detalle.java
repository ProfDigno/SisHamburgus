/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_caja_detalle {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec=new EvenFecha();
    private String sql_insert = "INSERT INTO public.caja_detalle(\n"
            + "            idcaja_detalle, fecha_emision, descripcion, monto_venta, monto_delivery, \n"
            + "            monto_gasto, monto_compra, monto_vale, id_origen, tabla_origen, \n"
            + "            fk_idusuario,indice,equipo,cierre,monto_caja,monto_cierre)\n"
            + "    VALUES (?, ?, ?, ?, ?, \n"
            + "            ?, ?, ?, ?, ?,?,?,?,?,?,?);";
    private String sql_select="select date(fecha_emision) as fecha,"
                + "TRIM(to_char(sum(monto_venta),'999G999G999')) as venta,"
                + "TRIM(to_char(sum(monto_delivery),'999G999G999')) as delivery,"
                + "TRIM(to_char(sum(monto_compra),'999G999G999')) as compra,\n" 
                + "TRIM(to_char(sum(monto_gasto),'999G999G999')) as gasto,"
                + "TRIM(to_char(sum(monto_vale),'999G999G999')) as vale "
                + "from caja_detalle "
                + "group by 1 order by 1 desc";
    private String sql_anular="update caja_detalle set descripcion=(descripcion||'-(ANULADO)'),"
                + "monto_venta=0,monto_delivery=0,monto_gasto=0,monto_vale=0,monto_compra=0 "
                + "where indice=?";
    private String sql_update="update caja_detalle set fecha_emision=?,descripcion=?,"
                + "monto_venta=?,monto_delivery=?,monto_gasto=?,monto_compra=?,monto_vale=? "
                + "where indice=?";
    private String sql_update_cerrartodo="update caja_detalle set cierre='C' "
                + "where cierre='A' ";

    public void insertar_caja_detalle(Connection conn, caja_detalle caja) {
        int idcaja_detalle = (eveconn.getInt_ultimoID_mas_uno(conn, caja.getTabla(), caja.getIdtabla()));
        caja.setC1idcaja_detalle(idcaja_detalle);
        String titulo = "insertar_caja_detalle";
        PreparedStatement pst = null;
        caja.setC14cierre("A");
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, caja.getC1idcaja_detalle());
            pst.setTimestamp(2, evefec.getTimestamp_fecha_cargado(caja.getC2fecha_emision()));
            pst.setString(3, caja.getC3descripcion());
            pst.setDouble(4, caja.getC4monto_venta());
            pst.setDouble(5, caja.getC5monto_delivery());
            pst.setDouble(6, caja.getC6monto_gasto());
            pst.setDouble(7, caja.getC7monto_compra());
            pst.setDouble(8, caja.getC8monto_vale());
            pst.setInt(9, caja.getC9id_origen());
            pst.setString(10, caja.getC10tabla_origen());
            pst.setInt(11, caja.getC11fk_idusuario());
            pst.setString(12,caja.getC12indice());
            pst.setString(13,caja.getC13equipo());
            pst.setString(14,caja.getC14cierre());
            pst.setDouble(15,caja.getC15monto_caja());
            pst.setDouble(16,caja.getC16monto_cierre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + caja.toString(), titulo);
        }
    }
    public void update_caja_detalle(Connection conn, caja_detalle caja) {
        String titulo = "update_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_fecha_cargado(caja.getC2fecha_emision()));
            pst.setString(2, caja.getC3descripcion());
            pst.setDouble(3, caja.getC4monto_venta());
            pst.setDouble(4, caja.getC5monto_delivery());
            pst.setDouble(5, caja.getC6monto_gasto());
            pst.setDouble(6, caja.getC7monto_compra());
            pst.setDouble(7, caja.getC8monto_vale());
            pst.setString(8, caja.getC12indice());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_update + "\n" + caja.toString(), titulo);
        }
    }
    public void anular_caja_detalle(Connection conn, caja_detalle caja){
        String titulo = "anular_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
//            pst.setInt(1, caja.getC9id_origen());
            pst.setString(1, caja.getC12indice());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_anular + "\n" + caja.toString(), titulo);
        }
    }
    public void update_caja_detalle_CERRARTODO(Connection conn){
        String titulo = "anular_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_cerrartodo);
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_cerrartodo + "\n", titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_update_cerrartodo + "\n", titulo);
        }
    }
    public void actualizar_tabla_caja_detalle(Connection conn, JTable tblcaja_resumen) {
        eveconn.Select_cargar_jtable(conn, sql_select, tblcaja_resumen);
    }

    public void ancho_tabla_cliente(JTable tbltabla) {
        int Ancho[] = {10,60,15,15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_grafico_caja_detalle(Connection conn,JTable tblgrafico_lavado,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,\n"
                + "TRIM(to_char(sum(monto_venta),'999G999G999')) as ingreso,\n"
                + "TRIM(to_char(sum(monto_gasto+monto_vale+monto_compra),'999G999G999')) as egreso \n"
                + "from caja_detalle \n"
                + "where  "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblgrafico_lavado);
    }
    public void actualizar_tabla_grafico_caja_detalle_venta(Connection conn,JTable tblcaja_venta,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_venta),'999G999G999')) as venta\n"
                + "from caja_detalle \n"
                + "where  monto_venta>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_venta);
    }
    public void actualizar_tabla_grafico_caja_detalle_gasto(Connection conn,JTable tblcaja_gasto,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_gasto),'999G999G999')) as gasto\n"
                + "from caja_detalle \n"
                + "where  monto_gasto>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_gasto);
    }
    public void actualizar_tabla_grafico_caja_detalle_vale(Connection conn,JTable tblcaja_vale,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_vale),'999G999G999')) as vale\n"
                + "from caja_detalle \n"
                + "where  monto_vale>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_vale);
    }
    public void actualizar_tabla_grafico_caja_detalle_delivery(Connection conn,JTable tblcaja_vale,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_delivery),'999G999G999')) as delivery\n"
                + "from caja_detalle \n"
                + "where  monto_delivery>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_vale);
    }
    public void actualizar_tabla_grafico_caja_detalle_compra_ins(Connection conn,JTable tblcaja_vale,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_compra),'999G999G999')) as compra\n"
                + "from caja_detalle \n"
                + "where  monto_compra>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_vale);
    }
}
