/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.insumo_producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_insumo_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    insumo_producto prod=new insumo_producto();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.insumo_producto(\n"
            + "            idinsumo_producto, nombre, merma, precio, stock,fk_idinsumo_categoria, \n"
            + "            fk_idinsumo_unidad,activar,codbarra)\n"
            + "    VALUES (?, ?, ?, ?, ? ,?, ?,?,?);";
    private String sql_update = "UPDATE public.insumo_producto\n"
            + "   SET  nombre=?, merma=?, precio=?,stock=?, fk_idinsumo_categoria=?, \n"
            + "       fk_idinsumo_unidad=?,activar=?,codbarra=? \n"
            + " WHERE idinsumo_producto=?;";
    private String sql_select = "SELECT ip.idinsumo_producto as idip,ip.codbarra as cod,(ic.nombre||'-'||ip.nombre||'-'||iu.nom_compra) as insumo_compra,\n"
            + "TRIM(to_char(ip.precio,'999G999G999')) as pre_comp,ip.merma,\n"
            + "TRIM(to_char((ip.precio+((ip.precio*ip.merma)/100)),'999G999G999')) as pre_util,ip.activar \n"
            + "FROM insumo_producto ip,insumo_categoria ic,insumo_unidad iu\n"
            + "where ip.fk_idinsumo_categoria=ic.idinsumo_categoria \n"
            + "and ip.activar=true "
            + " and ip.fk_idinsumo_unidad=iu.idinsumo_unidad ORDER BY 3 ASC;";
    private String sql_cargar = "SELECT ip.idinsumo_producto, ip.nombre, ip.merma, ip.precio,ip.stock, ip.fk_idinsumo_categoria, \n"
            + "       ip.fk_idinsumo_unidad,ic.nombre,iu.nom_compra,iu.nom_venta,iu.conversion_unidad,ip.activar,ip.codbarra, \n"
            + "  ((to_char(iu.conversion_unidad,'999G999G999'))||' '||iu.nom_venta) as nom_conv_uni "
            + "  FROM insumo_producto ip,insumo_categoria ic,insumo_unidad iu\n"
            + "  where ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
            + "  and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
            + "  and ip.idinsumo_producto=";
    private String sql_update_precio="UPDATE public.insumo_producto\n"
            + "   SET  precio=? "
            + " WHERE idinsumo_producto=?;";

    public void cargar_insumo_producto(insumo_producto prod, JTable tabla) {
        String titulo = "cargar_insumo_producto";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                prod.setIdinsumo_producto(id);
                prod.setNombre(rs.getString(2));
                prod.setMerma(rs.getDouble(3));
                prod.setPrecio(rs.getDouble(4));
                prod.setStock(rs.getDouble(5));
                prod.setFk_idinsumo_categoria(rs.getInt(6));
                prod.setFk_idinsumo_unidad(rs.getInt(7));
                prod.setNom_categoria(rs.getString(8));
                prod.setNom_unidad_compra(rs.getString(9));
                prod.setNom_unidad_venta(rs.getString(10));
                prod.setConversion_unidad(rs.getDouble(11));
                prod.setActivar(rs.getBoolean(12));
                prod.setCodbarra(rs.getInt(13));
                prod.setNom_conversion_unidad(rs.getString(14));
                
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prod.toString(), titulo);
        }
    }
    public void cargar_insumo_producto(insumo_producto prod, int id) {
        String titulo = "cargar_insumo_producto";
//        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                prod.setIdinsumo_producto(id);
                prod.setNombre(rs.getString(2));
                prod.setMerma(rs.getDouble(3));
                prod.setPrecio(rs.getDouble(4));
                prod.setStock(rs.getDouble(5));
                prod.setFk_idinsumo_categoria(rs.getInt(6));
                prod.setFk_idinsumo_unidad(rs.getInt(7));
                prod.setNom_categoria(rs.getString(8));
                prod.setNom_unidad_compra(rs.getString(9));
                prod.setNom_unidad_venta(rs.getString(10));
                prod.setConversion_unidad(rs.getDouble(11));
                prod.setActivar(rs.getBoolean(12));
                prod.setCodbarra(rs.getInt(13));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prod.toString(), titulo);
        }
    }

    public void insertar_insumo_producto(Connection conn, insumo_producto prod) {
        prod.setIdinsumo_producto(eveconn.getInt_ultimoID_mas_uno(conn, prod.getTabla(), prod.getIdtabla()));
        String titulo = "insertar_insumo_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prod.getIdinsumo_producto());
            pst.setString(2, prod.getNombre());
            pst.setDouble(3, prod.getMerma());
            pst.setDouble(4, prod.getPrecio());
            pst.setDouble(5, prod.getStock());
            pst.setInt(6, prod.getFk_idinsumo_categoria());
            pst.setInt(7, prod.getFk_idinsumo_unidad());
            pst.setBoolean(8, prod.isActivar());
            pst.setInt(9,prod.getCodbarra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prod.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prod.toString(), titulo);
        }
    }

    public void update_insumo_producto(Connection conn, insumo_producto prod) {
        String titulo = "update_insumo_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, prod.getNombre());
            pst.setDouble(2, prod.getMerma());
            pst.setDouble(3, prod.getPrecio());
            pst.setDouble(4, prod.getStock());
            pst.setInt(5, prod.getFk_idinsumo_categoria());
            pst.setInt(6, prod.getFk_idinsumo_unidad());
            pst.setBoolean(7, prod.isActivar());
            pst.setInt(8, prod.getCodbarra());
            pst.setInt(9, prod.getIdinsumo_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prod.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prod.toString(), titulo);
        }
    }
    public void update_insumo_producto_precio(Connection conn, insumo_producto prod) {
        String titulo = "update_insumo_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_precio);
            pst.setDouble(1, prod.getPrecio());
            pst.setInt(2, prod.getIdinsumo_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_precio + "\n" + prod.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_precio + "\n" + prod.toString(), titulo);
        }
    }

    public void actualizar_tabla_insumo_producto(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_insumo_producto(tbltabla);
    }
    public void actualizar_tabla_insumo_producto_activar(Connection conn, JTable tbltabla,String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_insumo_producto(tbltabla);
    }
    public void ancho_tabla_insumo_producto(JTable tbltabla) {
        int Ancho[] = {10,5, 33,13,10,12,7};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
