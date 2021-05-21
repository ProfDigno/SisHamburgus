/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.JasperReport.EvenJasperReport;
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
public class DAO_producto_categoria {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenJasperReport rep = new EvenJasperReport();
    private String mensaje_insert = "CATEGORIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CATEGORIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.producto_categoria(\n"
            + "            idproducto_categoria, nombre, activar,orden)\n"
            + "    VALUES (?, ?, ?, ?);";
    private String sql_update = "UPDATE public.producto_categoria\n"
            + "   SET  nombre=?, activar=?,orden=? \n"
            + " WHERE idproducto_categoria=?";
//    private String sql_select="select * from producto_categoria order by 2 asc";
    private String sql_cargar = "select nombre,activar,orden from producto_categoria where idproducto_categoria=";

    public void cargar_producto_categoria(producto_categoria cate, JTable tabla) {
        String titulo = "cargar_producto_categoria";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                cate.setIdproducto_categoria(id);
                cate.setNombre(rs.getString("nombre"));
                cate.setActivar(rs.getBoolean("activar"));
                cate.setOrden(rs.getInt("orden"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cate.toString(), titulo);
        }
    }
    public void cargar_producto_categoria_reporte(producto_categoria cate, int idproducto_categoria) {
        String titulo = "cargar_producto_categoria_reporte";
//        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_categoria, titulo);
            if (rs.next()) {
                cate.setIdproducto_categoria(idproducto_categoria);
                cate.setNombre(rs.getString("nombre"));
                cate.setActivar(rs.getBoolean("activar"));
                cate.setOrden(rs.getInt("orden"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cate.toString(), titulo);
        }
    }
    public void insertar_producto_categoria(Connection conn, producto_categoria cate) {
        cate.setIdproducto_categoria(eveconn.getInt_ultimoID_mas_uno(conn, cate.getTabla(), cate.getIdtabla()));
        String titulo = "insertar_producto_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cate.getIdproducto_categoria());
            pst.setString(2, cate.getNombre());
            pst.setBoolean(3, cate.isActivar());
            pst.setInt(4, cate.getOrden());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cate.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cate.toString(), titulo);
        }
    }

    public void insertar_producto_categoria(Connection connServi, int idproducto_categoria, String nombre, boolean activar, int orden) {
        String sql_insert = "INSERT INTO public.producto_categoria(\n"
                + "            idproducto_categoria, nombre, activar,orden)\n"
                + "    VALUES (?, ?, ?,?);";
        PreparedStatement pst = null;
        try {
            pst = connServi.prepareStatement(sql_insert);
            pst.setInt(1, idproducto_categoria);
            pst.setString(2, nombre);
            pst.setBoolean(3, activar);
            pst.setInt(4, orden);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }

    public void update_producto_categoria(Connection conn, producto_categoria cate) {
        String titulo = "update_producto_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, cate.getNombre());
            pst.setBoolean(2, cate.isActivar());
            pst.setInt(3, cate.getOrden());
            pst.setInt(4, cate.getIdproducto_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cate.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cate.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_categoria(Connection conn, JTable tbltabla, String fechadesde, String fechahasta) {
        String sql_select = "select ca.idproducto_categoria as idc,ca.nombre,ca.activar,"
                + " case when "
                + " ((select sum(iv.cantidad) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N') \n"
                + "and date(v.fecha_inicio) >= '" + fechadesde + "' and date(v.fecha_inicio) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=ca.idproducto_categoria)>0) "
                + "then "
                + "(((select sum(iv.cantidad) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N') \n"
                + "and date(v.fecha_inicio) >= '" + fechadesde + "' and date(v.fecha_inicio) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=ca.idproducto_categoria))) "
                + "else 0 end as cant_ven,    "
                + "TRIM(to_char((select sum(iv.cantidad*iv.precio_venta) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N') \n"
                + "and date(v.fecha_inicio) >= '" + fechadesde + "' and date(v.fecha_inicio) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=ca.idproducto_categoria),'999G999G999')) as total_ven,"
                + "ca.orden as ord   "
                + "from producto_categoria ca "
                + "order by 4 desc; ";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_categoria(tbltabla);
    }

    public void ancho_tabla_producto_categoria(JTable tbltabla) {
        int Ancho[] = {8, 42, 10, 15, 20, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void imprimir_rep_venta_producto_categoria(Connection conn, String fecdesde, String fechasta, String filtro_categoria) {
        String sql = "select p.idproducto as id,(c.nombre||'-'||u.nombre||'-'||p.nombre) as nombre,p.precio_venta as pre_ven,\n"
                + "    case when ((select sum(iv.cantidad)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto)>0) \n"
                + "     then (select sum(iv.cantidad)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto) else 0 end as cant,\n"
                
                + "     case when ((select sum(iv.cantidad*iv.precio_venta)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto)>0) \n"
                + "     then (select sum(iv.cantidad*iv.precio_venta)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto) else 0 end as total,\n"
                
                + "('('||c.idproducto_categoria||')-'||c.nombre) as categoria,\n"
                
                + "     case when  ((select sum(iv.cantidad) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=c.idproducto_categoria)>0) "
                + "     then (select sum(iv.cantidad) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=c.idproducto_categoria) else 0 end as cant_ven,\n"
                
                + "     case when  ((select sum(iv.cantidad*iv.precio_venta) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=c.idproducto_categoria)>0) \n"
                + "     then (select sum(iv.cantidad*iv.precio_venta) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=c.idproducto_categoria) else 0 end as total_ven,"
                
                + "('Fecha Desde:"+fecdesde+" Hasta:"+fechasta+"') as fecha \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where  c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and u.idproducto_unidad=p.fk_idproducto_unidad \n"
                + filtro_categoria+"\n"
                + "order by c.idproducto_categoria,4 desc; ";
        String titulonota = "VENTA PRODUCTO POR CATEGORIA";
        String direccion = "src/REPORTE/PRODUCTO/repVentaProductoCategoria.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
