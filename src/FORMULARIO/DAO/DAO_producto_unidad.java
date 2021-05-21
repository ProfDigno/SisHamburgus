/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 *
 */
public class DAO_producto_unidad {
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt=new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "UNIDAD GUARDADO CORRECTAMENTE";
    private String mensaje_update = "UNIDAD MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.producto_unidad(\n"
            + "            idproducto_unidad, nombre)\n"
            + "    VALUES (?, ?);";
    private String sql_update = "UPDATE public.producto_unidad\n"
            + "   SET  nombre=? \n"
            + " WHERE idproducto_unidad=?";
    private String sql_select="select idproducto_unidad, nombre from producto_unidad order by nombre asc";
    private String sql_cargar="select nombre from producto_unidad where idproducto_unidad=";
    public void cargar_producto_unidad(producto_unidad unid,JTable tabla) {
        String titulo = "cargar_producto_unidad";
        int id=evejt.getInt_select_id(tabla);
        Connection conn=ConnPostgres.getConnPosgres();
        try {
            ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
            if(rs.next()){
                unid.setIdproducto_unidad(id);
                unid.setNombre(rs.getString("nombre"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + unid.toString(), titulo);
        }
    }
    public void insertar_producto_unidad(Connection conn, producto_unidad unid) {
        unid.setIdproducto_unidad(eveconn.getInt_ultimoID_mas_uno(conn, unid.getTabla(), unid.getIdtabla()));
        String titulo = "insertar_producto_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, unid.getIdproducto_unidad());
            pst.setString(2, unid.getNombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + unid.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + unid.toString(), titulo);
        }
    }
    public void insertar_producto_unidad(Connection connServi, int idunidad, String nombre) {
        String titulo = "insertar_producto_unidad";
        String sql_insert = "INSERT INTO public.producto_unidad(\n"
                + "            idproducto_unidad, nombre)\n"
                + "    VALUES (?, ?);";
        PreparedStatement pst = null;
        try {
            pst = connServi.prepareStatement(sql_insert);
            pst.setInt(1, idunidad);
            pst.setString(2, nombre);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
    public void update_producto_unidad(Connection conn, producto_unidad unid) {
        String titulo = "update_producto_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, unid.getNombre());
            pst.setInt(2, unid.getIdproducto_unidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + unid.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + unid.toString(), titulo);
        }
    }
    public void actualizar_tabla_producto_unidad(Connection conn,JTable tbltabla){
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_unidad(tbltabla);
    }
    public void ancho_tabla_producto_unidad(JTable tbltabla){
        int Ancho[]={20,80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}

