/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Fecha.EvenFecha;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.caja_cierre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_caja_cierre {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CAJA_CIERRE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_CIERRE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_cierre(idcaja_cierre,fecha_inicio,fecha_fin,estado,fk_idusuario) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE caja_cierre SET fecha_fin=?,estado=? WHERE idcaja_cierre=?;";
    private String sql_select = "select cc.idcaja_cierre as idcc,\n"
            + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
            + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fin,\n"
            + "TRIM(to_char(sum(cd.monto_caja),'999G999G999')) as in_Abrir,\n"
            + "TRIM(to_char(sum(cd.monto_venta),'999G999G999')) as in_venta,\n"
            + "TRIM(to_char(sum(cd.monto_compra),'999G999G999')) as eg_compra,\n"
            + "TRIM(to_char(sum(cd.monto_gasto),'999G999G999')) as eg_gasto,\n"
            + "TRIM(to_char(sum(cd.monto_vale),'999G999G999')) as eg_vale,\n"
            + "TRIM(to_char((sum(cd.monto_caja+cd.monto_venta))-(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale+cd.monto_delivery)),'999G999G999')) as sis_sin_deli,\n"
            + "TRIM(to_char(sum(cd.monto_cierre),'999G999G999')) as cierre,\n"
            + "TRIM(to_char((sum(cd.monto_cierre)-((sum(cd.monto_caja+cd.monto_venta))-(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale+cd.monto_delivery)))),'999G999G999')) as diferencia\n"
            + "from caja_cierre cc,item_caja_cierre icc,caja_detalle cd\n"
            + "where cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
            + "and cd.idcaja_detalle=icc.fk_idcaja_detalle\n"
            + "group by 1,2,3\n"
            + "order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_cierre,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre WHERE idcaja_cierre=";

    public void cargar_caja_cierre(caja_cierre cjcie) {
        String titulo = "cargar_gasto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + cjcie.getC1idcaja_cierre(), titulo);
            if (rs.next()) {
                cjcie.setC2fecha_inicio(rs.getString("fecha_inicio"));
                cjcie.setC3fecha_fin(rs.getString("fecha_fin"));
                cjcie.setC4estado(rs.getString("estado"));
                cjcie.setC5fk_idusuario(rs.getInt("fk_idusuario"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cjcie.toString(), titulo);
        }
    }

    public void insertar_caja_cierre(Connection conn, caja_cierre cjcie) {
        cjcie.setC1idcaja_cierre(eveconn.getInt_ultimoID_mas_uno(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
        String titulo = "insertar_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cjcie.getC1idcaja_cierre());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, cjcie.getC4estado());
            pst.setInt(5, cjcie.getC5fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cjcie.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cjcie.toString(), titulo);
        }
    }

    public void update_caja_cierre(Connection conn, caja_cierre cjcie) {
        String titulo = "update_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cjcie.getC4estado());
            pst.setInt(3, cjcie.getC1idcaja_cierre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cjcie.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cjcie.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_cierre(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_cierre(tbltabla);
    }

    public void ancho_tabla_caja_cierre(JTable tbltabla) {
        int Ancho[] = {4,13,14,8,9,8,8,8,8,8,9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

}
