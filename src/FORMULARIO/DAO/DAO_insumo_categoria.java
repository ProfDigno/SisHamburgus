/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.insumo_categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_insumo_categoria {
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt=new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "INSUMO CATEGORIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "INSUMO CATEGORIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.insumo_categoria(\n"
            + "            idinsumo_categoria, nombre)\n"
            + "    VALUES (?, ?);";
    private String sql_update = "UPDATE public.insumo_categoria\n"
            + "   SET  nombre=? \n"
            + " WHERE idinsumo_categoria=?";
    private String sql_select="select * from insumo_categoria order by 2 asc";
    private String sql_cargar="select nombre from insumo_categoria where idinsumo_categoria=";
    public void cargar_insumo_categoria(insumo_categoria unid,JTable tabla) {
        String titulo = "cargar_insumo_categoria";
        int id=evejt.getInt_select_id(tabla);
        Connection conn=ConnPostgres.getConnPosgres();
        try {
            ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
            if(rs.next()){
                unid.setIdinsumo_categoria(id);
                unid.setNombre(rs.getString("nombre"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + unid.toString(), titulo);
        }
    }
    public void insertar_insumo_categoria(Connection conn, insumo_categoria unid) {
        unid.setIdinsumo_categoria(eveconn.getInt_ultimoID_mas_uno(conn, unid.getTabla(), unid.getIdtabla()));
        String titulo = "insertar_insumo_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, unid.getIdinsumo_categoria());
            pst.setString(2, unid.getNombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + unid.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + unid.toString(), titulo);
        }
    }
    public void update_insumo_categoria(Connection conn, insumo_categoria unid) {
        String titulo = "update_insumo_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, unid.getNombre());
            pst.setInt(2, unid.getIdinsumo_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + unid.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + unid.toString(), titulo);
        }
    }
    public void actualizar_tabla_insumo_categoria(Connection conn,JTable tbltabla){
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_insumo_categoria(tbltabla);
    }
    public void ancho_tabla_insumo_categoria(JTable tbltabla){
        int Ancho[]={20,80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
