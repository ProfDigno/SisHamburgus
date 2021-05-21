/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_factura {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenRender everende = new EvenRender();
    EvenJasperReport rep = new EvenJasperReport();
    EvenFecha evefec = new EvenFecha();
    factura fac = new factura();
    private String mensaje_insert = "FACTURA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FACTURA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.factura(\n"
            + "            idfactura, nro_factura, fecha_emision, estado, totalmonto, iva, \n"
            + "            totalletra, fk_idcliente)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, \n"
            + "            ?, ?);";
    private String sql_anular = "UPDATE public.factura\n"
            + "   SET  estado=? "
            + " WHERE idfactura=?;";

    //5,10,5,20,10,10,10,10,10,10
    public void insertar_factura(Connection conn, factura fac) {
        int idfactura = (eveconn.getInt_ultimoID_mas_uno(conn, fac.getTabla(), fac.getIdtabla()));
        fac.setC1idfactura(idfactura);
        String titulo = "insertar_factura";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, fac.getC1idfactura());
            pst.setInt(2, fac.getC2nro_factura());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, fac.getC4estado());
            pst.setDouble(5, fac.getC5totalmonto());
            pst.setDouble(6, fac.getC6iva());
            pst.setString(7, fac.getC7totalletra());
            pst.setInt(8, fac.getC8fk_idcliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + fac.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + fac.toString(), titulo);
        }
    }

    public void update_estado_factura(Connection conn, factura fac) {
        String titulo = "update_estado_factura";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setString(1, fac.getC4estado());
            pst.setInt(2, fac.getC1idfactura());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + fac.toString(), titulo);
//            evemen.modificado_correcto(mensaje_terminar, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_anular + "\n" + fac.toString(), titulo);
        }
    }

    public void actualizar_tabla_factura(Connection conn, JTable tblfactura, String filtro) {
        String sql_select = "select f.idfactura as id,f.nro_factura as nro,"
                + "to_char(f.fecha_emision,'yyyy-MM-dd') as fecha, \n"
                + "c.nombre as cliente,f.estado,\n"
                + "TRIM(to_char(f.iva,'999G999D99')) as iva,\n"
                + "TRIM(to_char(f.totalmonto,'999G999G999')) as totalmonto\n"
                + "from factura f,cliente c\n"
                + "where f.fk_idcliente=c.idcliente \n" + filtro
                + " order by 1 desc ";
        eveconn.Select_cargar_jtable(conn, sql_select, tblfactura);
        ancho_tabla_factura(tblfactura);
        everende.rendertabla_estados(tblfactura, 4);
    }

    public void ancho_tabla_factura(JTable tblfactura) {
        int Ancho[] = {5, 10, 15, 40, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tblfactura, Ancho);
    }

    public void imprimir_factura(Connection conn, int idfactura) {
        String sql = "select f.idfactura as idfactura,f.nro_factura as nro,('CONTADO') as condicion,"
                + "to_char(f.fecha_emision,'yyyy-MM-dd') as emision,('-') as venci,"
                + "f.totalletra as ttletra,f.totalmonto as ttpagar,(0) as ttiva5,"
                + "f.iva as ttiva10,f.iva as ttiva,cl.ruc as ruc,"
                + "(cl.idcliente||'-'||cl.nombre) as cliente,"
                + "cl.telefono as telef,cl.direccion as direc\n"
                + "from factura f,factura_fila ff, cliente cl\n"
                + "where f.fk_idcliente=cl.idcliente and f.idfactura=" + idfactura;
        String titulonota = "FACTURA";
        String direccion = "src/REPORTE/FACTURA/repNotaFactura.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
