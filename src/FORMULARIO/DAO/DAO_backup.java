/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.backup;
import FORMULARIO.ENTIDAD.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class DAO_backup {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenFecha evefec = new EvenFecha();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "BACKUP GUARDADO CORRECTAMENTE";
    private String mensaje_update = "BACKUP MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.backup(\n"
            + "            idbackup, fecha, direc_dump, direc_backup, basedato, localhost, \n"
            + "            puerto, usuario, senha, cantidad)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, \n"
            + "            ?, ?, ?, ?);";
    private String sql_update = "UPDATE public.backup\n"
            + "   SET  fecha=?, direc_dump=?, direc_backup=?, basedato=?, \n"
            + "       localhost=?, puerto=?, usuario=?, senha=?, cantidad=?\n"
            + " WHERE idbackup=?";
    private String sql_cargar = "SELECT direc_dump, direc_backup, basedato, localhost, \n"
            + "       puerto, usuario, senha,cantidad \n"
            + "  FROM public.backup where idbackup=1;";
    private String sql_update_creado_hoy = "UPDATE public.backup\n"
            + "   SET fecha='now()',  cantidad=(cantidad+1)\n"
            + " WHERE idbackup=?;";
    public void insertar_backup(Connection conn, backup bac) {
        String titulo = "insertar_backup";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, bac.getB1idbackup());
            pst.setDate(2, evefec.getDateSQL_sistema());
            pst.setString(3, bac.getB3direc_dump());
            pst.setString(4, bac.getB4direc_backup());
            pst.setString(5, bac.getB5basedato());
            pst.setString(6, bac.getB6localhost());
            pst.setString(7, bac.getB7puerto());
            pst.setString(8, bac.getB8usuario());
            pst.setString(9, bac.getB9senha());
            pst.setInt(10, bac.getB10cantidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + bac.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + bac.toString(), titulo);
        }
    }

    public void update_backup(Connection conn, backup bac) {
        String titulo = "update_backup";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setDate(1, evefec.getDateSQL_sistema());
            pst.setString(2, bac.getB3direc_dump());
            pst.setString(3, bac.getB4direc_backup());
            pst.setString(4, bac.getB5basedato());
            pst.setString(5, bac.getB6localhost());
            pst.setString(6, bac.getB7puerto());
            pst.setString(7, bac.getB8usuario());
            pst.setString(8, bac.getB9senha());
            pst.setInt(9, bac.getB10cantidad());
            pst.setInt(10, bac.getB1idbackup());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + bac.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + bac.toString(), titulo);
        }
    }
    public void update_backup_creado_hoy(Connection conn) {
        String titulo = "update_backup_creado_hoy";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_creado_hoy);
            pst.setInt(1,1);
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_creado_hoy + "\n", titulo);
//            evemen.modificado_correcto(sql_update_creado_hoy, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_creado_hoy + "\n" , titulo);
        }
    }

    public void cargar_backup(backup bac) {
        String titulo = "cargar_backup";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar, titulo);
            if (rs.next()) {
                bac.setB3direc_dump(rs.getString(1));
                bac.setB4direc_backup(rs.getString(2));
                bac.setB5basedato(rs.getString(3));
                bac.setB6localhost(rs.getString(4));
                bac.setB7puerto(rs.getString(5));
                bac.setB8usuario(rs.getString(6));
                bac.setB9senha(rs.getString(7));
                bac.setB10cantidad(rs.getInt(8));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + bac.toString(), titulo);
        }
    }
    public boolean getBoolean_backup_existente(Connection conn) {
        String titulo = "getBoolean_backup_existente";
        String sql = "select count(*) as cant from backup ";
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
    public boolean getBoolean_backup_creado_hoy(Connection conn) {
        String titulo = "getBoolean_backup_creado_hoy";
        String hoy=evefec.getString_formato_fecha();
        String sql = "SELECT (date(fecha)<date('"+hoy+"')) as fecha FROM BACKUP where idbackup=1 ";
        boolean fecha=false;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                fecha=rs.getBoolean("fecha");
                if(fecha){
                    System.out.println("EL BACKUP AUN NO FUE CREDO");
                }else{
                    System.out.println("HOY YA FUE CREDO EL BACKUP");
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return fecha;
    }
}
