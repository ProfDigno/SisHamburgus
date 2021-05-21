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
public class DAO_item_producto_ingrediente {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    producto prod = new producto();
//    private static int idproducto;
    private String mensaje_insert = " GUARDADO CORRECTAMENTE";
    private String mensaje_update = " MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.item_producto_ingrediente(\n"
            + "            iditem_producto_ingrediente, fk_idproducto, fk_idproducto_ingrediente)\n"
            + "    VALUES (?, ?, ?);";
//    private static String sql_select = "select ipi.iditem_producto_ingrediente as idi,pi.nombre as ingrediente,\n"
//            + "pi.precio_venta as precio\n"
//            + "FROM item_producto_ingrediente ipi,producto_ingrediente pi\n"
//            + "where ipi.fk_idproducto_ingrediente=pi.idproducto_ingrediente\n"
//            + "and ipi.fk_idproducto="+idproducto
//            + " order by 1 desc";
    private String sql_delect = "DELETE FROM public.item_producto_ingrediente\n"
            + " WHERE iditem_producto_ingrediente=?;";

    public void insertar_item_producto_ingrediente(Connection conn, item_producto_ingrediente ingre) {
        ingre.setIditem_producto_ingrediente(eveconn.getInt_ultimoID_mas_uno(conn, ingre.getTabla(), ingre.getIdtabla()));
        String titulo = "insertar_producto_ingrediente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ingre.getIditem_producto_ingrediente());
            pst.setInt(2, ingre.getFk_idproducto());
            pst.setInt(3, ingre.getFk_idproducto_ingrediente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ingre.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ingre.toString(), titulo);
        }
    }
    public void insertar_item_producto_ingrediente(Connection connServi, int iditem_producto_ingrediente, int fk_idproducto, int fk_idproducto_ingrediente) {
        String titulo = "insertar_producto_ingrediente";
        String sql_insert = "INSERT INTO public.item_producto_ingrediente(\n"
                + "            iditem_producto_ingrediente, fk_idproducto, fk_idproducto_ingrediente)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = connServi.prepareStatement(sql_insert);
            pst.setInt(1, iditem_producto_ingrediente);
            pst.setInt(2, fk_idproducto);
            pst.setInt(3, fk_idproducto_ingrediente);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }    
    public void eliminar_item_producto_ingrediente(Connection conn, item_producto_ingrediente ingre) {
        String titulo = "eliminar_item_producto_ingrediente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_delect);
            pst.setInt(1, ingre.getIditem_producto_ingrediente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ingre.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ingre.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_producto_ingrediente(Connection conn, JTable tbltabla, int idproducto) {
        String sql_select = "select ipi.iditem_producto_ingrediente as idi,pi.nombre as ingrediente,\n"
//                + "TRIM(to_char(pi.precio_venta,'999G999G999')) as precio\n"
                + "pi.precio_venta as precio\n"
                + "FROM item_producto_ingrediente ipi,producto_ingrediente pi\n"
                + "where ipi.fk_idproducto_ingrediente=pi.idproducto_ingrediente\n"
                + "and ipi.fk_idproducto=" + idproducto
                + " order by 2 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_ingrediente(tbltabla);
    }
    public void actualizar_tabla_item_producto_ingrediente_enproducto(Connection conn, JTable tbltabla, int idproducto) {
        String sql_select = "select ipi.iditem_producto_ingrediente as idi,pi.nombre as ingrediente,\n"
                + "TRIM(to_char(pi.precio_venta,'999G999G999')) as precio\n"
//                + "pi.precio_venta as precio\n"
                + "FROM item_producto_ingrediente ipi,producto_ingrediente pi\n"
                + "where ipi.fk_idproducto_ingrediente=pi.idproducto_ingrediente\n"
                + "and ipi.fk_idproducto=" + idproducto
                + " order by 2 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_ingrediente(tbltabla);
    }
    public void ancho_tabla_producto_ingrediente(JTable tbltabla) {
        int Ancho[] = {10, 70, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public boolean getBoolean_cantidad_ingrediente(Connection conn, int idproducto) {
        String titulo = "getBoolean_cantidad_ingrediente";
        boolean mostrar = false;
        String sql = "select count(*) as cantidad from item_producto_ingrediente\n"
                + "where fk_idproducto=" + idproducto;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int cantidad=rs.getInt("cantidad");
                if(cantidad>0){
                    mostrar=true;
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return mostrar;
    }
}
