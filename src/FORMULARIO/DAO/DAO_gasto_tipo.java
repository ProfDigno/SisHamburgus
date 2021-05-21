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
public class DAO_gasto_tipo {
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt=new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "GASTO TIPO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "GASTO TIPO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.gasto_tipo(\n"
            + "            idgasto_tipo, nombre, activar)\n"
            + "    VALUES (?, ?, ?);";
    private String sql_update = "UPDATE public.gasto_tipo\n"
            + "   SET  nombre=?, activar=?\n"
            + " WHERE idgasto_tipo=?";
    private String sql_select="select * from gasto_tipo order by 2 asc";
    private String sql_cargar="select nombre,activar from gasto_tipo where idgasto_tipo=";
    public void cargar_gasto_tipo(gasto_tipo entre,JTable tabla) {
        String titulo = "cargar_gasto_tipo";
        int id=evejt.getInt_select_id(tabla);
        Connection conn=ConnPostgres.getConnPosgres();
        try {
            ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
            if(rs.next()){
                entre.setIdgasto_tipo(id);
                entre.setNombre(rs.getString("nombre"));
                entre.setActivar(rs.getBoolean("activar"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + entre.toString(), titulo);
        }
    }
    public void insertar_gasto_tipo(Connection conn, gasto_tipo entre) {
        entre.setIdgasto_tipo(eveconn.getInt_ultimoID_mas_uno(conn, entre.getTabla(), entre.getIdtabla()));
        String titulo = "insertar_gasto_tipo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, entre.getIdgasto_tipo());
            pst.setString(2, entre.getNombre());
            pst.setBoolean(3, entre.isActivar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + entre.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + entre.toString(), titulo);
        }
    }
    public void insertar_gasto_tipo(Connection conn2, int idgasto_tipo, String nombre,boolean activar) {
        String titulo = "insertar_producto_unidad";
        String sql_insert = "INSERT INTO public.gasto_tipo(\n"
                + "            idgasto_tipo, nombre, activar)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn2.prepareStatement(sql_insert);
            pst.setInt(1, idgasto_tipo);
            pst.setString(2, nombre);
            pst.setBoolean(3, activar);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
    public void update_gasto_tipo(Connection conn, gasto_tipo entre) {
        String titulo = "update_gasto_tipo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, entre.getNombre());
            pst.setBoolean(2, entre.isActivar());
            pst.setInt(3, entre.getIdgasto_tipo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + entre.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + entre.toString(), titulo);
        }
    }
    public void actualizar_tabla_gasto_tipo(Connection conn,JTable tbltabla){
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_gasto_tipo(tbltabla);
    }
    public void ancho_tabla_gasto_tipo(JTable tbltabla){
        int Ancho[]={20,60,20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
