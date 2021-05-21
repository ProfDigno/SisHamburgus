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
import FORMULARIO.ENTIDAD.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class DAO_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.producto(\n"
            + "            idproducto, nombre, precio_venta, precio_compra, stock, orden, \n"
            + "            activar, cocina, descontar_stock, comprar, vender, fk_idproducto_categoria, \n"
            + "            fk_idproducto_unidad,fk_idproducto_grupo)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, \n"
            + "            ?, ?, ?, ?, ?, ?, \n"
            + "            ?, ?);";
    private String sql_update = "UPDATE public.producto\n"
            + "   SET  nombre=?, precio_venta=?, precio_compra=?, stock=?, \n"
            + "       orden=?, activar=?, cocina=?, descontar_stock=?, comprar=?, vender=?, \n"
            + "       fk_idproducto_categoria=?, fk_idproducto_unidad=?\n"
            + " WHERE idproducto=?;";
    private String sql_update_compra = "UPDATE public.producto\n"
            + "   SET   precio_compra=? "
            + " WHERE idproducto=?;";
    private String sql_update_stock_descontar = "UPDATE public.producto\n"
            + "   SET   stock=(stock-?) "
            + " WHERE idproducto=?;";
    private String sql_update_stock_aumentar = "UPDATE public.producto\n"
            + "   SET   stock=(stock+?) "
            + " WHERE idproducto=?;";
    private String sql_select = "SELECT p.idproducto,(pc.nombre||'-'||pu.nombre||'-'||p.nombre) as nombre, "
            + "TRIM(to_char(p.precio_venta,'999G999G999')) as precio_v,\n"
            + "  (select count(*) as cant from insumo_item_producto where fk_idproducto=p.idproducto) as ing"
            + "  FROM producto p,producto_categoria pc,producto_unidad pu\n"
            + "  where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "  and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
            + "  order by pc.nombre,pu.nombre asc";
    private String sql_cargar = "SELECT p.idproducto, p.nombre, p.precio_venta, p.precio_compra, p.stock, p.orden, \n"
            + "       p.activar, p.cocina, p.descontar_stock, p.comprar, p.vender, p.fk_idproducto_categoria, \n"
            + "       p.fk_idproducto_unidad,pc.nombre as categoria,pu.nombre as unidad,p.fk_idproducto_grupo \n"
            + "  FROM producto p,producto_categoria pc,producto_unidad pu\n"
            + "  where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "  and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
            + "  and p.idproducto=";
private String sql_update_precio="UPDATE public.producto\n"
            + "   SET  precio_compra=? "
            + " WHERE idproducto=?;";
    public void cargar_producto(producto prod, int id) {
        String titulo = "cargar_producto";
//        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                prod.setP1idproducto(id);
                prod.setP2nombre(rs.getString(2));
                prod.setP3precio_venta(rs.getDouble(3));
                prod.setP4precio_compra(rs.getDouble(4));
                prod.setP5stock(rs.getDouble(5));
                prod.setP6orden(rs.getInt(6));
                prod.setP7activar(rs.getBoolean(7));
                prod.setP8cocina(rs.getBoolean(8));
                prod.setP9descontar_stock(rs.getBoolean(9));
                prod.setP10comprar(rs.getBoolean(10));
                prod.setP11vender(rs.getBoolean(11));
                prod.setP12fk_idproducto_categoria(rs.getInt(12));
                prod.setP13fk_idproducto_unidad(rs.getInt(13));
                prod.setP12categoria(rs.getString(14));
                prod.setP13unidad(rs.getString(15));
                prod.setP14fk_idproducto_grupo(rs.getInt(16));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prod.toString(), titulo);
        }
    }

    public double getDouble_precio_compra_producto(Connection conn, String idproducto) {
        String titulo = "getString_precio_compra_producto";
        double precio_compra = 0;
        String sql = "select precio_compra from producto where idproducto=";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql + idproducto, titulo);
            if (rs.next()) {
                precio_compra = rs.getDouble("precio_compra");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return precio_compra;
    }

    public void insertar_producto(Connection conn, producto prod) {
        prod.setP1idproducto(eveconn.getInt_ultimoID_mas_uno(conn, prod.getTabla(), prod.getIdtabla()));
        String titulo = "insertar_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prod.getP1idproducto());
            pst.setString(2, prod.getP2nombre());
            pst.setDouble(3, prod.getP3precio_venta());
            pst.setDouble(4, prod.getP4precio_compra());
            pst.setDouble(5, prod.getP5stock());
            pst.setInt(6, prod.getP6orden());
            pst.setBoolean(7, prod.isP7activar());
            pst.setBoolean(8, prod.isP8cocina());
            pst.setBoolean(9, prod.isP9descontar_stock());
            pst.setBoolean(10, prod.isP10comprar());
            pst.setBoolean(11, prod.isP11vender());
            pst.setInt(12, prod.getP12fk_idproducto_categoria());
            pst.setInt(13, prod.getP13fk_idproducto_unidad());
            pst.setInt(14, prod.getP14fk_idproducto_grupo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prod.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prod.toString(), titulo);
        }
    }

    public void insertar_producto(Connection connServi, int P1idproducto, String P2nombre, double P3precio_venta, double P4precio_compra,
            double P5stock, int P6orden,
            boolean P7activar, boolean P8cocina, boolean P9descontar_stock, boolean P10comprar, boolean P11vender,
            int P12fk_idcategoria, int P13fk_idunidad) {
        String titulo = "insertar_producto";
        String sql_insert = "INSERT INTO public.producto(\n"
                + "            idproducto, nombre, precio_venta, precio_compra, stock, orden, \n"
                + "            activar, cocina, descontar_stock, comprar, vender, fk_idproducto_categoria, \n"
                + "            fk_idproducto_unidad)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, \n"
                + "            ?, ?, ?, ?, ?, ?, \n"
                + "            ?);";
        PreparedStatement pst = null;
        try {
            pst = connServi.prepareStatement(sql_insert);
            pst.setInt(1, P1idproducto);
            pst.setString(2, P2nombre);
            pst.setDouble(3, P3precio_venta);
            pst.setDouble(4, P4precio_compra);
            pst.setDouble(5, P5stock);
            pst.setInt(6, P6orden);
            pst.setBoolean(7, P7activar);
            pst.setBoolean(8, P8cocina);
            pst.setBoolean(9, P9descontar_stock);
            pst.setBoolean(10, P10comprar);
            pst.setBoolean(11, P11vender);
            pst.setInt(12, P12fk_idcategoria);
            pst.setInt(13, P13fk_idunidad);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
    public void update_producto_pre_compra(Connection conn, producto prod) {
        String titulo = "update_producto_pre_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_compra);
            pst.setDouble(1, prod.getP4precio_compra());
            pst.setInt(2, prod.getP1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_compra + "\n" + prod.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_compra + "\n" + prod.toString(), titulo);
        }
    }
    public void update_producto_stock_descontar(Connection conn, producto prod) {
        String titulo = "update_producto_stock_descontar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_stock_descontar);
            pst.setDouble(1, prod.getP15cantidad());
            pst.setInt(2, prod.getP1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_stock_descontar + "\n" + prod.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_stock_descontar + "\n" + prod.toString(), titulo);
        }
    }
    public void update_producto_stock_aumentar(Connection conn, producto prod) {
        String titulo = "update_producto_stock_aumentar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_stock_aumentar);
            pst.setDouble(1, prod.getP15cantidad());
            pst.setInt(2, prod.getP1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_stock_aumentar + "\n" + prod.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_stock_aumentar + "\n" + prod.toString(), titulo);
        }
    }
    public void update_producto(Connection conn, producto prod) {
        String titulo = "update_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, prod.getP2nombre());
            pst.setDouble(2, prod.getP3precio_venta());
            pst.setDouble(3, prod.getP4precio_compra());
            pst.setDouble(4, prod.getP5stock());
            pst.setInt(5, prod.getP6orden());
            pst.setBoolean(6, prod.isP7activar());
            pst.setBoolean(7, prod.isP8cocina());
            pst.setBoolean(8, prod.isP9descontar_stock());
            pst.setBoolean(9, prod.isP10comprar());
            pst.setBoolean(10, prod.isP11vender());
            pst.setInt(11, prod.getP12fk_idproducto_categoria());
            pst.setInt(12, prod.getP13fk_idproducto_unidad());
            pst.setInt(13, prod.getP1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prod.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prod.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto(tbltabla);
    }

    public void buscar_tabla_producto(Connection conn, JTable tbltabla, JTextField txtbuscar, int tipo) {
        String filtro = "";
        if (txtbuscar.getText().trim().length() > 0) {
            String buscar = txtbuscar.getText();
            if (tipo == 1) {
                filtro = " and pc.nombre ilike'%" + buscar + "%' ";
            }
            if (tipo == 2) {
                filtro = " and pu.nombre ilike'%" + buscar + "%' ";
            }
            if (tipo == 3) {
                filtro = " and p.nombre ilike'%" + buscar + "%' ";
            }
        }
        String sql = "SELECT p.idproducto,(pc.nombre||'-'||pu.nombre||'-'||p.nombre) as nombre, "
                + "TRIM(to_char(p.precio_venta,'999G999G999')) as precio_v, \n"
                + "(select count(*) as cant from insumo_item_producto where fk_idproducto=p.idproducto) as ing"
                + "  FROM producto p,producto_categoria pc,producto_unidad pu\n"
                + "  where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "  and p.fk_idproducto_unidad=pu.idproducto_unidad\n" + filtro
                + "  order by pc.nombre,pu.nombre asc ";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto(tbltabla);
    }

    public void ancho_tabla_producto(JTable tbltabla) {
        int Ancho[] = {10, 60, 20,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_producto_en_grupo(Connection conn, JTable tblpro_producto, String fechadesde, String fechahasta, int idproducto_categoria) {
        String sql = "select p.idproducto as id,(c.nombre||'-'||u.nombre||'-'||p.nombre) as nombre,"
                + "TRIM(to_char(p.precio_venta,'999G999')) as pre_ven,"
                + "case when "
                + "((select sum(iv.cantidad)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fechadesde + "' and date(v.fecha_inicio) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto)>0) "
                + "then "
                + "(((select sum(iv.cantidad)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fechadesde + "' and date(v.fecha_inicio) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto))) "
                + "else 0 end as cant, "
                + "TRIM(to_char((select sum(iv.cantidad*iv.precio_venta)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and (iv.tipo='P' or iv.tipo='N')\n"
                + "and date(v.fecha_inicio) >= '" + fechadesde + "' and date(v.fecha_inicio) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto),'999G999G999')) as total "
                + "from producto p,producto_categoria c,producto_unidad u "
                + "where c.idproducto_categoria=" + idproducto_categoria + " "
                + "and c.idproducto_categoria=p.fk_idproducto_categoria "
                + "and u.idproducto_unidad=p.fk_idproducto_unidad "
                + "order by 4 desc; ";
        eveconn.Select_cargar_jtable(conn, sql, tblpro_producto);
        ancho_tabla_producto_en_categoria(tblpro_producto);
    }

    public void ancho_tabla_producto_en_categoria(JTable tbltabla) {
        int Ancho[] = {10, 50, 15, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void update_producto_precio_compra(Connection conn, producto prod) {
        String titulo = "update_insumo_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_precio);
            pst.setDouble(1, prod.getP4precio_compra());
            pst.setInt(2, prod.getP1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_precio + "\n" + prod.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_precio + "\n" + prod.toString(), titulo);
        }
    }
}
