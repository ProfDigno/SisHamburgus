/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.ENTIDAD.itemven_insumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_itemven_insumo {

    EvenConexion evconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenUtil eveut = new EvenUtil();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    DAO_producto pdao = new DAO_producto();
    EvenConexion eveconn = new EvenConexion();
    EvenJasperReport rep = new EvenJasperReport();
    private String mensaje_insert = " GUARDADO CORRECTAMENTE";
    private String sql_insert = "INSERT INTO public.itemven_insumo(\n"
            + "            iditemven_insumo, nombre, cantidad, precio, fk_iditem_venta, \n"
            + "            fk_idinsumo_item_producto)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?);";
    private String sql_select = "";
    private String sql_suma_monto = "select \n"
            + "(sum(ii.precio*iv.cantidad)) as total_monto\n"
            + "from insumo_producto ip,\n"
            + "insumo_item_producto iip,\n"
            + "itemven_insumo ii,item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
            + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
            + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
            + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
            + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
            + "and ii.fk_iditem_venta=iv.iditem_venta\n"
            + "and iv.fk_idventa=v.idventa\n"
            + "and (v.estado='TERMINADO' or v.estado='EMITIDO')  ";

    private void insertar_itemven_insumo(Connection conn, itemven_insumo iteminsu) {
        iteminsu.setIditemven_insumo(evconn.getInt_ultimoID_mas_uno(conn, iteminsu.getTabla(), iteminsu.getIdtabla()));
        String titulo = "insertar_itemven_insumo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, iteminsu.getIditemven_insumo());
            pst.setString(2, iteminsu.getNombre());
            pst.setDouble(3, iteminsu.getCantidad());
            pst.setDouble(4, iteminsu.getPrecio());
            pst.setInt(5, iteminsu.getFk_iditem_venta());
            pst.setInt(6, iteminsu.getFk_idinsumo_item_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + iteminsu.toString(), titulo);
//            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + iteminsu.toString(), titulo);
        }
    }

    public void recorrer_item_venta(Connection connLocal, itemven_insumo iteminsu, int fk_idventa) {
        String titulo = "recorrer_item_venta";
        String sql = "select iv.fk_idproducto,\n"
                + "ip.nombre,iip.cantidad,iip.precio,\n"
                + "iv.iditem_venta,iip.idinsumo_item_producto \n"
                + "from item_venta iv,insumo_item_producto iip,\n"
                + "insumo_producto ip \n"
                + "where iv.fk_idproducto=iip.fk_idproducto\n"
                + "and ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and iv.fk_idventa=" + fk_idventa;
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            while (rs.next()) {
                iteminsu.setNombre(rs.getString("nombre"));
                iteminsu.setCantidad(rs.getDouble("cantidad"));
                iteminsu.setPrecio(rs.getDouble("precio"));
                iteminsu.setFk_iditem_venta(rs.getInt("iditem_venta"));
                iteminsu.setFk_idinsumo_item_producto(rs.getInt("idinsumo_item_producto"));
                insertar_itemven_insumo(connLocal, iteminsu);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void actualizar_tabla_grafico_insumo_producto(Connection conn, JTable tblinsumo_producto, String campo_fecha, String filtro_fecha, int idinsumo_producto) {
        String sql = "select " + campo_fecha + ",\n"
                + "TRIM(to_char(sum(ii.cantidad*iv.cantidad),'999G999')) as tt_cant,\n"
                + "TRIM(to_char(sum(ii.precio*iv.cantidad),'999G999')) as tt_precio\n"
                + "from insumo_producto ip,insumo_item_producto iip,itemven_insumo ii,"
                + "item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO')\n"
                + "and ip.idinsumo_producto=" + idinsumo_producto + " " + filtro_fecha
                + " group by 1 "
                + "ORDER BY 1 ASC";
        evconn.Select_cargar_jtable(conn, sql, tblinsumo_producto);
    }

    public double getDoubleSuma_insumo_producto(Connection conn, String filtro_fecha, String campovalor, int idinsumo_producto) {
        String titulo = "getDoubleSuma_insumo_producto";
        double valor = 0;
        String sql = "select sum(ii.cantidad*iv.cantidad) as tt_cant,\n"
                + "sum(ii.precio*iv.cantidad) as tt_precio\n"
                + "from insumo_producto ip,insumo_item_producto iip,itemven_insumo ii,"
                + "item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO')\n"
                + "and ip.idinsumo_producto=" + idinsumo_producto + " " + filtro_fecha
                + " ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            if (rs.next()) {
                valor = (rs.getDouble(campovalor));
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return valor;
    }

    public void actualizar_tabla_filtro_venta_insumo(Connection conn, JTable tblfiltro_compra_insumo, String filtro_fecha, String idcategoria) {
        String sql_select = "select ip.idinsumo_producto,\n"
                + "(ic.nombre||'-'||ip.nombre) as producto_insumo,\n"
                + "iu.nom_venta,\n"
                + "sum(ii.cantidad*iv.cantidad) as cant_venta,\n"
                + "iu.nom_compra,\n"
                + "TRIM(to_char((sum((ii.cantidad*iv.cantidad)/iu.conversion_unidad)),'999999D99')) as cant_compra,\n"
                + "TRIM(to_char((sum(ii.precio*iv.cantidad)),'999G999G999')) as total_monto\n"
                + "from insumo_producto ip,\n"
                + "insumo_item_producto iip,\n"
                + "itemven_insumo ii,item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO')  \n" + filtro_fecha + idcategoria
                + "group by 1,2,3,5 "
                + "ORDER BY (sum(ii.precio*iv.cantidad)) desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tblfiltro_compra_insumo);
        ancho_tabla_filtro_venta_insumo(tblfiltro_compra_insumo);
    }

    public void ancho_tabla_filtro_venta_insumo(JTable tblcompra_insumo) {
        int Ancho[] = {5, 35, 15, 10, 15, 10, 10};
        evejt.setAnchoColumnaJtable(tblcompra_insumo, Ancho);
    }

    public double sumar_monto_producto_mas_vendido(Connection conn, String filtro_fecha, String idcategoria) {
        return eveconn.getdouble_sql_suma(conn, sql_suma_monto + filtro_fecha + idcategoria);
    }

    public void imprimir_PRODUCTOS_MAS_VENDIDOS(Connection conn, String filtro_fecha, String idcategoria) {
        String sql = "select ic.nombre as categoria,ip.idinsumo_producto as idinsumo,\n"
                + "(ic.nombre||'-'||ip.nombre) as producto_insumo,\n"
                + "iu.nom_venta as uni_venta,\n"
                + "sum(ii.cantidad*iv.cantidad) as cant_venta,\n"
                + "iu.nom_compra as uni_compra,\n"
                + "(sum((ii.cantidad*iv.cantidad)/iu.conversion_unidad)) as cant_compra,\n"
                + "(sum(ii.precio*iv.cantidad)) as total_monto\n"
                + "from insumo_producto ip,insumo_item_producto iip,\n"
                + "itemven_insumo ii,item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO')  \n" + filtro_fecha + idcategoria
                + "group by 1,2,3,4,6 "
                + "ORDER BY 1 desc";
        String titulonota = "PRODUCTOS INSUMO MAS VENDIDOS";
        String direccion = "src/REPORTE/VENTAINSUMO/repVENTA_INSUMO_mas_vendido.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
