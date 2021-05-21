package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_compra;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.compra;
import FORMULARIO.ENTIDAD.producto;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_compra {
    
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = "ITEM_COMPRA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_COMPRA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_compra(iditem_compra,descripcion,precio_compra,cantidad,fk_idproducto,fk_idcompra) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_compra SET descripcion=?,precio_compra=?,cantidad=?,fk_idproducto=?,fk_idcompra=? WHERE iditem_compra=?;";
//    private String sql_select = "SELECT iditem_compra,descripcion,precio_compra,cantidad,fk_idproducto,fk_idcompra FROM item_compra order by 1 desc;";
    private String sql_cargar = "SELECT iditem_compra,descripcion,precio_compra,cantidad,fk_idproducto,fk_idcompra FROM item_compra WHERE iditem_compra=";
     private String sql_select = "select (iditem_compra||'-'||descripcion) as itemcompra,\n"
            + "cantidad as cant,(precio_compra*cantidad) as subtotal \n"
            + "from item_compra\n"
            + "where fk_idcompra=";
    public void insertar_item_compra(Connection conn, item_compra itemc) {
        itemc.setC1iditem_compra(eveconn.getInt_ultimoID_mas_uno(conn, itemc.getTb_item_compra(), itemc.getId_iditem_compra()));
        String titulo = "insertar_item_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, itemc.getC1iditem_compra());
            pst.setString(2, itemc.getC2descripcion());
            pst.setDouble(3, itemc.getC3precio_compra());
            pst.setDouble(4, itemc.getC4cantidad());
            pst.setInt(5, itemc.getC5fk_idproducto());
            pst.setInt(6, itemc.getC6fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + itemc.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + itemc.toString(), titulo);
        }
    }
    
    public void update_item_compra(Connection conn, item_compra itemc) {
        String titulo = "update_item_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, itemc.getC2descripcion());
            pst.setDouble(2, itemc.getC3precio_compra());
            pst.setDouble(3, itemc.getC4cantidad());
            pst.setInt(4, itemc.getC5fk_idproducto());
            pst.setInt(5, itemc.getC6fk_idcompra());
            pst.setInt(6, itemc.getC1iditem_compra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + itemc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + itemc.toString(), titulo);
        }
    }
    
    public void cargar_item_compra(Connection conn, item_compra itemc, JTable tabla) {
        String titulo = "Cargar_item_compra";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                itemc.setC1iditem_compra(rs.getInt(1));
                itemc.setC2descripcion(rs.getString(2));
                itemc.setC3precio_compra(rs.getDouble(3));
                itemc.setC4cantidad(rs.getDouble(4));
                itemc.setC5fk_idproducto(rs.getInt(5));
                itemc.setC6fk_idcompra(rs.getInt(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + itemc.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + itemc.toString(), titulo);
        }
    }
    
//    public void actualizar_tabla_item_compra(Connection conn, JTable tbltabla) {
//        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
//        ancho_tabla_item_compra(tbltabla);
//    }
//    
//    public void ancho_tabla_item_compra(JTable tbltabla) {
//        int Ancho[] = {16, 16, 16, 16, 16, 16};
//        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
//    }
    
    public void insertar_item_compra_de_tabla(Connection conn, JTable tblitem_compra_insumo, compra comp) {
        String titulo = "insertar_item_compra_de_tabla";
        item_compra item = new item_compra();
        for (int row = 0; row < tblitem_compra_insumo.getRowCount(); row++) {
            String idproducto = ((tblitem_compra_insumo.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_compra_insumo.getModel().getValueAt(row, 1).toString()));
            String precio_compra = ((tblitem_compra_insumo.getModel().getValueAt(row, 3).toString()));
            String cantidad = ((tblitem_compra_insumo.getModel().getValueAt(row, 4).toString()));
            try {
                item.setC2descripcion(descripcion);
                item.setC3precio_compra(Double.parseDouble(precio_compra));
                item.setC4cantidad(Double.parseDouble(cantidad));
                item.setC6fk_idcompra(comp.getC1idcompra());
                item.setC5fk_idproducto(Integer.parseInt(idproducto));
                insertar_item_compra(conn, item);
                producto prod = new producto();
                pdao.cargar_producto(prod, Integer.parseInt(idproducto));
                if (prod.isP9descontar_stock()) {
                    prod.setP15cantidad(Double.parseDouble(cantidad));
                    prod.setP1idproducto(Integer.parseInt(idproducto));
                    pdao.update_producto_stock_aumentar(conn, prod);
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }
            
        }
    }

    public void descontar_stock_producto(Connection conn, compra com) {
        String titulo = "descontar_stock_producto";
        String sql = "select fk_idproducto,cantidad "
                + "from item_compra "
                + "where fk_idcompra=" + com.getC1idcompra();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                int fk_idproducto = (rs.getInt("fk_idproducto"));
                double cantidad = (rs.getDouble("cantidad"));
                producto prod = new producto();
                pdao.cargar_producto(prod, fk_idproducto);
                if (prod.isP9descontar_stock()) {
                    prod.setP15cantidad(cantidad);
                    prod.setP1idproducto(fk_idproducto);
                    pdao.update_producto_stock_descontar(conn, prod);
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n", titulo);
        }
    }
    public void tabla_item_compra_insumo_filtro(Connection conn, JTable tblitem_producto_filtro,int idcompra_insumo){
        eveconn.Select_cargar_jtable(conn, sql_select+idcompra_insumo, tblitem_producto_filtro);
        ancho_tabla_compra_insumo_filtro(tblitem_producto_filtro);
    }
    public void ancho_tabla_compra_insumo_filtro(JTable tblitem_producto_filtro) {
        int Ancho[] = { 70,10,20};
        evejt.setAnchoColumnaJtable(tblitem_producto_filtro, Ancho);
    }
}
