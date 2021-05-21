/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.ENTIDAD.compra_insumo;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.item_compra_insumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_item_compra_insumo {

    EvenConexion evconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenUtil eveut = new EvenUtil();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = " GUARDADO CORRECTAMENTE";
    private String sql_insert = "INSERT INTO public.item_compra_insumo(\n"
            + "            iditem_compra_insumo, nombre,unidad, precio, cantidad, fk_idcompra_insumo, fk_idinsumo_producto)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?,?);";
    private String sql_select = "select (nombre||'-'||unidad) as insumo,\n"
            + "cantidad as cant,(precio*cantidad) as subtotal \n"
            + "from item_compra_insumo\n"
            + "where fk_idcompra_insumo=";

    public void insertar_item_compra_insumo(Connection conn, item_compra_insumo item) {
        item.setIditem_compra_insumo(evconn.getInt_ultimoID_mas_uno(conn, item.getTabla(), item.getIdtabla()));
        String titulo = "insertar_item_compra_insumo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, item.getIditem_compra_insumo());
            pst.setString(2, item.getNombre());
            pst.setString(3, item.getUnidad());
            pst.setDouble(4, item.getPrecio());
            pst.setDouble(5, item.getCantidad());
            pst.setInt(6, item.getFk_idcompra_insumo());
            pst.setInt(7, item.getFk_idinsumo_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + item.toString(), titulo);
//            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + item.toString(), titulo);
        }
    }

    public void insertar_item_compra_insumo_de_tabla(Connection conn, JTable tblitem_compra_insumo, compra_insumo comp) {
        String titulo = "insertar_item_compra_insumo_de_tabla";
        item_compra_insumo item = new item_compra_insumo();
        for (int row = 0; row < tblitem_compra_insumo.getRowCount(); row++) {
            String idproducto = ((tblitem_compra_insumo.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_compra_insumo.getModel().getValueAt(row, 1).toString()));
            String unidad = ((tblitem_compra_insumo.getModel().getValueAt(row, 2).toString()));
            String precio_compra = ((tblitem_compra_insumo.getModel().getValueAt(row, 3).toString()));
            String cantidad = ((tblitem_compra_insumo.getModel().getValueAt(row, 4).toString()));
            try {
                item.setNombre(descripcion);
                item.setUnidad(unidad);
                item.setPrecio(Double.parseDouble(precio_compra));
                item.setCantidad(Double.parseDouble(cantidad));
                item.setFk_idcompra_insumo(comp.getIdcompra_insumo());
                item.setFk_idinsumo_producto(Integer.parseInt(idproducto));
                insertar_item_compra_insumo(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }
    public void tabla_item_compra_insumo_filtro(Connection conn, JTable tblitem_producto_filtro,int idcompra_insumo){
        evconn.Select_cargar_jtable(conn, sql_select+idcompra_insumo, tblitem_producto_filtro);
        ancho_tabla_compra_insumo_filtro(tblitem_producto_filtro);
    }
    public void ancho_tabla_compra_insumo_filtro(JTable tblitem_producto_filtro) {
        int Ancho[] = { 70,10,20};
        evejt.setAnchoColumnaJtable(tblitem_producto_filtro, Ancho);
    }
}
