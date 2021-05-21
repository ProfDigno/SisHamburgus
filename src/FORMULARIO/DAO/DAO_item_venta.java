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
import Evento.Utilitario.EvenUtil;
import FORMULARIO.ENTIDAD.item_venta;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.venta;
import FORMULARIO.ENTIDAD.zona_delivery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_item_venta {

    EvenConexion evconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenUtil eveut=new EvenUtil();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = " GUARDADO CORRECTAMENTE";
    private String sql_insert = "INSERT INTO public.item_venta(\n"
            + "            iditem_venta, descripcion, precio_venta, precio_compra, cantidad, \n"
            + "            tipo, fk_idventa, fk_idproducto,grupo)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private String sql_select = "select descripcion,"
            + "TRIM(to_char(precio_venta,'999G999G999')) as precio,cantidad as ca,"
            + "TRIM(to_char((precio_venta*cantidad),'999G999G999')) as total "
            + "from item_venta where fk_idventa=";

    public void insertar_item_venta(Connection conn, item_venta item) {
        item.setC1iditem_venta(evconn.getInt_ultimoID_mas_uno(conn, item.getTabla(), item.getIdtabla()));
        String titulo = "insertar_item_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, item.getC1iditem_venta());
            pst.setString(2, item.getC2descripcion());
            pst.setDouble(3, item.getC3precio_venta());
            pst.setDouble(4, item.getC4precio_compra());
            pst.setDouble(5, item.getC5cantidad());
            pst.setString(6, item.getC6tipo());
            pst.setInt(7, item.getC7fk_idventa());
            pst.setInt(8, item.getC8fk_idproducto());
            pst.setInt(9, item.getC9grupo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + item.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + item.toString(), titulo);
        }
    }

    public void insertar_item_venta_de_tabla(Connection conn, JTable tblitem_producto, item_venta item, venta ven) {
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String tipo = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 4).toString()));
            String grupo = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            try {
                producto prod = new producto();
                pdao.cargar_producto(prod, Integer.parseInt(idproducto));
                item.setC2descripcion(eveut.getString_salto_de_linea(descripcion));
                item.setC6tipo(tipo);
                item.setC3precio_venta(Double.parseDouble(precio_venta));
                item.setC4precio_compra(prod.getP4precio_compra());
                item.setC5cantidad(Double.parseDouble(cantidad));
                item.setC7fk_idventa(ven.getC1idventa_estatico());
                item.setC8fk_idproducto(Integer.parseInt(idproducto));
                item.setC9grupo(Integer.parseInt(grupo));
                insertar_item_venta(conn, item);
                if(tipo.equals("N") || tipo.equals("P")){
                    if(prod.isP9descontar_stock()){
                        prod.setP15cantidad(Double.parseDouble(cantidad));
                        prod.setP1idproducto(Integer.parseInt(idproducto));
                        pdao.update_producto_stock_descontar(conn, prod);
                    }
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_de_tabla");
                break;
            }
            
        }
    }
    public void tabla_item_cliente_filtro(Connection conn, JTable tblitem_producto_filtro, item_venta item){
        evconn.Select_cargar_jtable(conn, sql_select+item.getC7fk_idventa(), tblitem_producto_filtro);
        ancho_tabla_item_cliente_filtro(tblitem_producto_filtro);
    }
    public void ancho_tabla_item_cliente_filtro(JTable tblitem_producto_filtro) {
        int Ancho[] = { 55, 15,5, 15};
        evejt.setAnchoColumnaJtable(tblitem_producto_filtro, Ancho);
    }
    public void recargar_stock_producto(Connection conn, venta ven) {
        String titulo = "recargar_stock_producto";
        String sql = "select fk_idproducto,cantidad,tipo "
                + "from item_venta "
                + "where fk_idventa=" + ven.getC1idventa();
        try {
            ResultSet rs = evconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                int fk_idproducto = (rs.getInt("fk_idproducto"));
                double cantidad = (rs.getDouble("cantidad"));
                String tipo = rs.getString("tipo");
                producto prod = new producto();
                pdao.cargar_producto(prod,fk_idproducto);
                if(tipo.equals("N") || tipo.equals("P")){
                    if(prod.isP9descontar_stock()){
                        prod.setP15cantidad(cantidad);
                        prod.setP1idproducto(fk_idproducto);
                        pdao.update_producto_stock_aumentar(conn, prod);
                    }
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n", titulo);
        }
    }
}
