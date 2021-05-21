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
import FORMULARIO.ENTIDAD.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_usuario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "USUARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "USUARIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.usuario(\n"
            + "            idusuario, nombre, usuario, senha, nivel)\n"
            + "    VALUES (?, ?, ?, ?, ?);";
    private String sql_update = "UPDATE public.usuario\n"
            + "   SET  nombre=?, usuario=?, senha=?, nivel=?\n"
            + " WHERE idusuario=?;";
    private String sql_select = "select * from usuario order by 1 asc";
    private String sql_cargar = "SELECT idusuario, nombre, usuario, senha, nivel\n"
            + "  FROM public.usuario where idusuario=";

    public void cargar_usuario(usuario usu, JTable tabla) {
        String titulo = "cargar_usuario";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                usu.setU1idusuario(id);
                usu.setU2nombre(rs.getString("nombre"));
                usu.setU3usuario(rs.getString("usuario"));
                usu.setU4senha(rs.getString("senha"));
                usu.setU5nivel(rs.getString("nivel"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + usu.toString(), titulo);
        }
    }

    public void insertar_usuario(Connection conn, usuario usu) {
        usu.setU1idusuario(eveconn.getInt_ultimoID_mas_uno(conn, usu.getTabla(), usu.getIdtabla()));
        String titulo = "insertar_usuario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, usu.getU1idusuario());
            pst.setString(2, usu.getU2nombre());
            pst.setString(3, usu.getU3usuario());
            pst.setString(4, usu.getU4senha());
            pst.setString(5, usu.getU5nivel());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + usu.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + usu.toString(), titulo);
        }
    }

    public void update_usuario(Connection conn, usuario usu) {
        String titulo = "update_usuario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, usu.getU2nombre());
            pst.setString(2, usu.getU3usuario());
            pst.setString(3, usu.getU4senha());
            pst.setString(4, usu.getU5nivel());
            pst.setInt(5, usu.getU1idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + usu.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + usu.toString(), titulo);
        }
    }
    public boolean getBoolean_buscar_usuario_existente(Connection conn, usuario usu) {
        String titulo = "buscar_usuario_existente";
        String sql = "select * from usuario where usuario='"+usu.getU3usuario()+"' and senha='"+usu.getU4senha()+"' ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                usu.setGlobal_idusuario(rs.getInt("idusuario"));
                usu.setGlobal_nombre(rs.getString("nombre"));
                usu.setGlobal_nivel(rs.getString("nivel"));
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"USUARIO O SENHA INCORRECTA","ERROR",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
            return false;
        }
    }
    public boolean getBoolean_cantidad_cero(Connection conn) {
        boolean escero=false;
        String titulo = "getBoolean_cantidad_cero";
        String sql = "select ((count(*))=0) as cantidad from usuario  ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                escero=rs.getBoolean("cantidad");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return escero;
    }
    public void actualizar_tabla_usuario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_usuario(tbltabla);
    }

    public void ancho_tabla_usuario(JTable tbltabla) {
        int Ancho[] = {10, 40, 20,15,15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
