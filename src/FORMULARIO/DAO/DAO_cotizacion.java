/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import FORMULARIO.ENTIDAD.cotizacion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
//import FORMULARIO.ENTIDAD.cotizacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_cotizacion {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "COTIZACION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "COTIZACION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.cotizacion(\n"
            + "            idcotizacion, dolar_guarani, real_guarani)\n"
            + "    VALUES (?, ?, ?);";
    private String sql_update = "UPDATE public.cotizacion\n"
            + "   SET  dolar_guarani=?, real_guarani=?\n"
            + " WHERE idcotizacion=?;";
    private String sql_select = "SELECT idcotizacion, dolar_guarani, real_guarani\n"
            + "  FROM public.cotizacion;";
    private String sql_cargar = "SELECT idcotizacion, dolar_guarani, real_guarani\n"
            + "  FROM public.cotizacion where idcotizacion=";

    public void cargar_cotizacion(cotizacion coti, int id) {
        String titulo = "cargar_cotizacion";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                coti.setIdcotizacion(id);
                coti.setDolar_guarani(rs.getDouble(2));
                coti.setReal_guarani(rs.getDouble(3));
                coti.setStdolar_guarani(rs.getString(2));
                coti.setStreal_guarani(rs.getString(3));
                coti.setDolar_guarani_STATIC(rs.getDouble(2));
                coti.setReal_guarani_STATIC(rs.getDouble(3));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + coti.toString(), titulo);
        }
    }

    public void insertar_cotizacion(Connection conn, cotizacion coti) {
        String titulo = "insertar_cotizacion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, coti.getIdcotizacion());
            pst.setDouble(2, coti.getDolar_guarani());
            pst.setDouble(3, coti.getReal_guarani());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + coti.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + coti.toString(), titulo);
        }
    }

    public void update_cotizacion(Connection conn, cotizacion coti) {
        String titulo = "update_cotizacion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setDouble(1, coti.getDolar_guarani());
            pst.setDouble(2, coti.getReal_guarani());
            pst.setInt(3, coti.getIdcotizacion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + coti.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + coti.toString(), titulo);
        }
    }
    public boolean getBoolean_cotizacion_existente(Connection conn) {
        String titulo = "getBoolean_cotizacion_existente";
        String sql = "select count(*) as cant from cotizacion ";
        int cantidad=0;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                cantidad=rs.getInt("cant");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        if(cantidad>0){
            return false;
        }else{
            return true;
        }
    }
}
