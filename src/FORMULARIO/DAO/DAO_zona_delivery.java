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
 */
public class DAO_zona_delivery {
    EvenConexion evconn = new EvenConexion();
    EvenJtable evejt=new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "ZONA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ZONA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.zona_delivery(\n"
            + "            idzona_delivery, nombre, delivery)\n"
            + "    VALUES (?, ?, ?);";
    private String sql_update = "UPDATE public.zona_delivery\n"
            + "   SET  nombre=?, delivery=?\n"
            + " WHERE idzona_delivery=?";
    private String sql_select="select * from zona_delivery order by 2 asc";
    private String sql_cargar="select nombre,delivery from zona_delivery where idzona_delivery=";
    public void cargar_zona_delivery(zona_delivery zona,int id) {
        String titulo = "cargar_zona_delivery";
//        int id=evejt.getInt_select_id(tabla);
        Connection conn=ConnPostgres.getConnPosgres();
        try {
            ResultSet rs=evconn.getResulsetSQL(conn,sql_cargar+id, titulo);
            if(rs.next()){
                zona.setIdzona_delivery(id);
                zona.setNombre(rs.getString("nombre"));
                zona.setDelivery(rs.getDouble("delivery"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + zona.toString(), titulo);
        }
    }
    public void insertar_zona_delivery(Connection conn, zona_delivery zona) {
        zona.setIdzona_delivery(evconn.getInt_ultimoID_mas_uno(conn, zona.getTabla(), zona.getIdtabla()));
        String titulo = "insertar_zona_delivery";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, zona.getIdzona_delivery());
            pst.setString(2, zona.getNombre());
            pst.setDouble(3, zona.getDelivery());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + zona.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + zona.toString(), titulo);
        }
    }
    public void insertar_zona_delivery(Connection connServi, int Idzona_delivery, String Nombre, double Delivery) {
        String titulo = "insertar_zona_delivery";
        String sql_insert = "INSERT INTO public.zona_delivery(\n"
                + "            idzona_delivery, nombre, delivery)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = connServi.prepareStatement(sql_insert);
            pst.setInt(1, Idzona_delivery);
            pst.setString(2, Nombre);
            pst.setDouble(3, Delivery);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }

    public void update_zona_delivery(Connection conn, zona_delivery zona) {
        String titulo = "update_zona_delivery";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, zona.getNombre());
            pst.setDouble(2, zona.getDelivery());
            pst.setInt(3, zona.getIdzona_delivery());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + zona.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + zona.toString(), titulo);
        }
    }
    public void actualizar_tabla_zona_delivery(Connection conn,JTable tbltabla){
        evconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_zona_delivery(tbltabla);
    }
    public void ancho_tabla_zona_delivery(JTable tbltabla){
        int Ancho[]={20,60,20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}

