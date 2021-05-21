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
import FORMULARIO.ENTIDAD.item_factura;
import FORMULARIO.ENTIDAD.factura;
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
public class DAO_item_factura {

    EvenConexion evconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenUtil eveut=new EvenUtil();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = " GUARDADO CORRECTAMENTE";
    private String sql_insert = "INSERT INTO public.item_factura(\n" +
"            iditem_factura, nombre, precio, cantidad, fk_idfactura, fk_idproducto)\n" +
"    VALUES (?, ?, ?, ?, ?, ?);";
    private String sql_select = "select descripcion,precio_factura as precio,cantidad as ca,(precio_factura*cantidad) as total "
            + "from item_factura where fk_idfactura=";

    public void insertar_item_factura(Connection conn, item_factura item) {
        item.setC1iditem_factura(evconn.getInt_ultimoID_mas_uno(conn, item.getTabla(), item.getIdtabla()));
        String titulo = "insertar_item_factura";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, item.getC1iditem_factura());
            pst.setString(2, item.getC2nombre());
            pst.setDouble(3, item.getC3precio());
            pst.setDouble(4, item.getC4cantidad());
            pst.setInt(5, item.getC5fk_idfactura());
            pst.setInt(6, item.getC6fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + item.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + item.toString(), titulo);
        }
    }

    public void insertar_item_factura_de_tabla(Connection conn, JTable tblitem_producto, item_factura item, factura ven) {
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
//            String tipo = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String precio_factura = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            try {
                item.setC2nombre(eveut.getString_salto_de_linea(descripcion));
                item.setC3precio(Double.parseDouble(precio_factura));
                item.setC4cantidad(Double.parseDouble(cantidad));
                item.setC5fk_idfactura(ven.getC1idfactura());
                item.setC6fk_idproducto(Integer.parseInt(idproducto));
                insertar_item_factura(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_factura_de_tabla");
                break;
            }
            
        }
    }
    public void tabla_item_cliente_filtro(Connection conn, JTable tblitem_producto_filtro, item_factura item){
        evconn.Select_cargar_jtable(conn, sql_select+item.getC5fk_idfactura(), tblitem_producto_filtro);
        ancho_tabla_item_cliente_filtro(tblitem_producto_filtro);
    }
    public void ancho_tabla_item_cliente_filtro(JTable tblitem_producto_filtro) {
        int Ancho[] = { 55, 15,5, 15};
        evejt.setAnchoColumnaJtable(tblitem_producto_filtro, Ancho);
    }
}
