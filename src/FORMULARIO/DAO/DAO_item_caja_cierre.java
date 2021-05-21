/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.item_caja_cierre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_item_caja_cierre {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "ITEM_CAJA_CIERRE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_CAJA_CIERRE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_caja_cierre(iditem_caja_cierre,fk_idcaja_cierre,fk_idcaja_detalle) VALUES (?,?,?);";
    private String sql_update = "UPDATE item_caja_cierre SET fk_idcaja_cierre=?,fk_idcaja_detalle=? WHERE iditem_caja_cierre=?;";
    private String sql_select = "SELECT iditem_caja_cierre,fk_idcaja_cierre,fk_idcaja_detalle FROM item_caja_cierre order by 1 desc;";

    public void insertar_item_caja_cierre(Connection conn, item_caja_cierre iccie) {
        iccie.setC1iditem_caja_cierre(eveconn.getInt_ultimoID_mas_uno(conn, iccie.getTb_item_caja_cierre(), iccie.getId_iditem_caja_cierre()));
        String titulo = "insertar_item_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, iccie.getC1iditem_caja_cierre());
            pst.setInt(2, iccie.getC2fk_idcaja_cierre());
            pst.setInt(3, iccie.getC3fk_idcaja_detalle());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + iccie.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + iccie.toString(), titulo);
        }
    }

    public void update_item_caja_cierre(Connection conn, item_caja_cierre iccie) {
        String titulo = "update_item_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, iccie.getC2fk_idcaja_cierre());
            pst.setInt(2, iccie.getC3fk_idcaja_detalle());
            pst.setInt(3, iccie.getC1iditem_caja_cierre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + iccie.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + iccie.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_caja_cierre(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_caja_cierre(tbltabla);
    }

    public void ancho_tabla_item_caja_cierre(JTable tbltabla) {
        int Ancho[] = {33, 33, 33};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
