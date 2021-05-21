/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONFIGURACION;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLprepar;
//import ClaseSQL.FunMensaje;
//import ClaseSQL.SQLprepar;
import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Optimus
 */
public class ClaCorteAdmin {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String fecha_corte;
    private int cant_dias=0;
    public boolean verificar_corte_admin(Connection conn) {
        boolean bloquear=true;
        String titulo = "verificar_corte_admin";
        String sql = "select ((date('" + getFecha_corte() + "'))-(date('now()'))) as dias;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int dias = rs.getInt("dias");
                setCant_dias(dias);
                if (dias <= 4 && dias >= -3) {
                    JOptionPane.showMessageDialog(null, "FALTAN " + dias + " PARA CORTE ADMINISTRATIVO ", "BLOQUEO", JOptionPane.WARNING_MESSAGE);
                    bloquear=true;
                }
                if (dias < -3 || dias > 350) {
                    JOptionPane.showMessageDialog(null, "CORTE ADMINISTRATIVO ", "BLOQUEO", JOptionPane.ERROR_MESSAGE);
                    bloquear=false;
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
            bloquear=false;
        }
        return bloquear;
    }

    /**
     * @return the fecha_corte
     */
    public String getFecha_corte() {
        return fecha_corte;
    }

    /**
     * @param fecha_corte the fecha_corte to set
     */
    public void setFecha_corte(String fecha_corte) {
        this.fecha_corte = fecha_corte;
    }

    /**
     * @return the cant_dias
     */
    public int getCant_dias() {
        return cant_dias;
    }

    /**
     * @param cant_dias the cant_dias to set
     */
    public void setCant_dias(int cant_dias) {
        this.cant_dias = cant_dias;
    }
}
