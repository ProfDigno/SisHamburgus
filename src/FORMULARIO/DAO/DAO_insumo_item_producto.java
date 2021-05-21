/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.insumo_item_producto;
import FORMULARIO.ENTIDAD.insumo_producto;
import FORMULARIO.ENTIDAD.item_producto_ingrediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_insumo_item_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.insumo_item_producto(\n"
            + "            idinsumo_item_producto, cantidad, precio, fk_idproducto, fk_idinsumo_producto)\n"
            + "    VALUES (?, ?, ?, ?, ?);";
    private String sql_select = "select iip.idinsumo_item_producto as id,(ic.nombre||'-'||ip.nombre) as insumo,"
            + "ip.merma as merma,iip.cantidad as cant,iu.nom_venta as unidad,\n"
            + "TRIM(to_char((((ip.precio+((ip.precio*ip.merma)/100))/iu.conversion_unidad)*iip.cantidad),'999G999G999')) as precio_insumo \n"
            + "from insumo_item_producto iip,insumo_producto ip,insumo_unidad iu,insumo_categoria ic\n"
            + "where iip.fk_idinsumo_producto=ip.idinsumo_producto\n"
            + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad "
            + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria \n"
            + "and iip.fk_idproducto=";
    private String sql_delect = "DELETE FROM public.insumo_item_producto\n"
            + " WHERE idinsumo_item_producto=?";

    public void insertar_insumo_item_producto(Connection conn, insumo_item_producto prod) {
        prod.setIdinsumo_item_producto(eveconn.getInt_ultimoID_mas_uno(conn, prod.getTabla(), prod.getIdtabla()));
        String titulo = "insertar_insumo_item_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prod.getIdinsumo_item_producto());
            pst.setDouble(2, prod.getCantidad());
            pst.setDouble(3, prod.getPrecio());
            pst.setInt(4, prod.getFk_idproducto());
            pst.setInt(5, prod.getFk_idinsumo_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prod.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prod.toString(), titulo);
        }
    }

    public void eliminar_insumo_item_producto(Connection conn, insumo_item_producto insu) {
        String titulo = "eliminar_insumo_item_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_delect);
            pst.setInt(1, insu.getIdinsumo_item_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + insu.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + insu.toString(), titulo);
        }
    }

    public double suma_precio_insumo(Connection conn, int idproducto) {
        String sql = "select sum(((ip.precio+((ip.precio*ip.merma)/100))/iu.conversion_unidad)*iip.cantidad) as suma \n"
                + "from insumo_item_producto iip,insumo_producto ip,insumo_unidad iu\n"
                + "where iip.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad "
                + "and iip.fk_idproducto=" + idproducto;
        String titulo = "suma_precio_insumo";
        double suma_precio = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            if (rs.next()) {
                suma_precio = rs.getDouble("suma");
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return suma_precio;
    }

    public void actualizar_tabla_insumo_item_producto(Connection conn, JTable tbltabla, int idproducto) {
        eveconn.Select_cargar_jtable(conn, sql_select + idproducto, tbltabla);
        ancho_tabla_insumo_item_producto(tbltabla);
    }

    public void ancho_tabla_insumo_item_producto(JTable tbltabla) {
        int Ancho[] = {10, 40, 10, 10, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
