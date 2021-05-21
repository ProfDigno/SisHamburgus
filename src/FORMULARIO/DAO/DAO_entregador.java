/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.JasperReport.EvenJasperReport;
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
 *
 */
public class DAO_entregador {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "ENTREGADOR GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ENTREGADOR MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.entregador(\n"
            + "            identregador, nombre, activar)\n"
            + "    VALUES (?, ?, ?);";
    private String sql_update = "UPDATE public.entregador\n"
            + "   SET  nombre=?, activar=?\n"
            + " WHERE identregador=?";
    private String sql_select = "select * from entregador order by 2 asc";
    private String sql_select_v = "select identregador as id,nombre from entregador where activar=true order by 1 asc";
    private String sql_cargar = "select nombre,activar from entregador where identregador=";

    public void cargar_entregador(entregador entre, JTable tabla) {
        String titulo = "cargar_entregador";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                entre.setIdentregador(id);
                entre.setNombre(rs.getString("nombre"));
                entre.setActivar(rs.getBoolean("activar"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + entre.toString(), titulo);
        }
    }

    public void insertar_entregador(Connection conn, entregador entre) {
        entre.setIdentregador(eveconn.getInt_ultimoID_mas_uno(conn, entre.getTabla(), entre.getIdtabla()));
        String titulo = "insertar_entregador";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, entre.getIdentregador());
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

    public void update_entregador(Connection conn, entregador entre) {
        String titulo = "update_entregador";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, entre.getNombre());
            pst.setBoolean(2, entre.isActivar());
            pst.setInt(3, entre.getIdentregador());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + entre.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + entre.toString(), titulo);
        }
    }

    public void actualizar_tabla_entregador(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_entregador(tbltabla);
    }

    public void ancho_tabla_entregador(JTable tbltabla) {
        int Ancho[] = {20, 60, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_entregador_venta(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select_v, tbltabla);
        ancho_tabla_entregador_venta(tbltabla);
    }

    public void ancho_tabla_entregador_venta(JTable tbltabla) {
        int Ancho[] = {20, 80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_suma_delivery(Connection conn, JTable tbltabla, int identregador, String fecdesde, String fechasta) {
        String sql_suma_delivery = "select v.idventa,to_char(v.fecha_inicio,'yyyy-MM-dd') as fecha,v.comanda,c.direccion,\n"
                + "TRIM(to_char(v.monto_venta,'999G999G999')) as monto,TRIM(to_char(v.monto_delivery,'999G999G999')) as delivery \n"
                + "from venta v,cliente c "
                + "where v.fk_idcliente=c.idcliente "
                + "and v.delivery=true "
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO') "
                + "and v.fk_identregador=" + identregador + " \n"
                + "and date(v.fecha_inicio)>='" + fecdesde + "' and date(v.fecha_inicio)<='" + fechasta + "'  order by v.idventa desc;";
        eveconn.Select_cargar_jtable(conn, sql_suma_delivery, tbltabla);
        ancho_tabla_suma_delivery(tbltabla);
    }

    public void ancho_tabla_suma_delivery(JTable tbltabla) {
        int Ancho[] = {10, 10, 40, 26, 8, 8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void imprimir_venta_delivery(Connection conn, int identregador, String fecdesde, String fechasta) {
        String sql = "select v.idventa as idventa,to_char(v.fecha_inicio,'yyyy-MM-dd') as fecha,c.nombre as cliente,c.direccion as direc_cli,\n"
                + "v.monto_venta as monto,v.monto_delivery as delivery,e.nombre as entregador \n"
                + "from venta v,cliente c,entregador e "
                + "where v.fk_idcliente=c.idcliente "
                + "and v.delivery=true "
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO') "
                + "and v.fk_identregador=e.identregador "
                + "and v.fk_identregador=" + identregador + " \n"
                + "and date(v.fecha_inicio)>='" + fecdesde + "' and date(v.fecha_inicio)<='" + fechasta + "'  "
                + "order by v.idventa desc;";
        String titulonota = "VENTA DELIVERY";
        String direccion = "src/REPORTE/DELIVERY/rep_venta_delivery.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
