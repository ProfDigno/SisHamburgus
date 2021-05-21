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
 * CREATE TABLE "producto_ingrediente" (
	"idproducto_ingrediente" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"precio_venta" NUMERIC(14,0) NOT NULL ,
	PRIMARY KEY("idproducto_ingrediente")
);
 */
public class DAO_producto_ingrediente {
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt=new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "INGREDIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "INGREDIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.producto_ingrediente(\n"
            + "            idproducto_ingrediente, nombre, precio_venta)\n"
            + "    VALUES (?, ?, ?);";
    private String sql_update = "UPDATE public.producto_ingrediente\n"
            + "   SET  nombre=?, precio_venta=?\n"
            + " WHERE idproducto_ingrediente=?";
    private String sql_select="select * from producto_ingrediente order by 2 asc";
    private String sql_cargar="select nombre,precio_venta from producto_ingrediente where idproducto_ingrediente=";
    public void cargar_producto_ingrediente(producto_ingrediente ingre,JTable tabla) {
        String titulo = "cargar_producto_ingrediente";
        int id=evejt.getInt_select_id(tabla);
        Connection conn=ConnPostgres.getConnPosgres();
        try {
            ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
            if(rs.next()){
                ingre.setIdproducto_ingrediente(id);
                ingre.setNombre(rs.getString("nombre"));
                ingre.setPrecio_venta(rs.getDouble("precio_venta"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ingre.toString(), titulo);
        }
    }
    public void insertar_producto_ingrediente(Connection conn, producto_ingrediente ingre) {
        ingre.setIdproducto_ingrediente(eveconn.getInt_ultimoID_mas_uno(conn, ingre.getTabla(), ingre.getIdtabla()));
        String titulo = "insertar_producto_ingrediente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ingre.getIdproducto_ingrediente());
            pst.setString(2, ingre.getNombre());
            pst.setDouble(3, ingre.getPrecio_venta());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ingre.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ingre.toString(), titulo);
        }
    }
    public void insertar_producto_ingrediente(Connection conn, int idproducto_ingrediente, String nombre, double precio_venta) {
        String titulo = "insertar_producto_ingrediente";
        String sql_insert = "INSERT INTO public.producto_ingrediente(\n"
                + "            idproducto_ingrediente, nombre, precio_venta)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, idproducto_ingrediente);
            pst.setString(2, nombre);
            pst.setDouble(3, precio_venta);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
    public void update_producto_ingrediente(Connection conn, producto_ingrediente ingre) {
        String titulo = "update_producto_ingrediente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, ingre.getNombre());
            pst.setDouble(2, ingre.getPrecio_venta());
            pst.setInt(3, ingre.getIdproducto_ingrediente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ingre.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ingre.toString(), titulo);
        }
    }
    public void actualizar_tabla_producto_ingrediente(Connection conn,JTable tbltabla){
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_ingrediente(tbltabla);
    }
    public void ancho_tabla_producto_ingrediente(JTable tbltabla){
        int Ancho[]={20,60,20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}

