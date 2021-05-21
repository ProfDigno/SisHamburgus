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
import FORMULARIO.ENTIDAD.compra_insumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_compra_insumo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenRender everende = new EvenRender();
    EvenJasperReport rep = new EvenJasperReport();
    EvenFecha evefec = new EvenFecha();
    compra_insumo comp = new compra_insumo();
    private String mensaje_insert = "COMPRA INSUMO GUARDADO CORRECTAMENTE";
    private String mensaje_update = " MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.compra_insumo(\n"
            + "            idcompra_insumo, fecha_emision, monto_compra, observacion, estado, \n"
            + "            tiponota, indice,fk_idusuario)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private String sql_anular = "UPDATE public.compra_insumo\n"
            + "   SET  estado=?,tiponota=?,fecha_confirmado=? "
            + " WHERE idcompra_insumo=?;";
    private String sql_suma_monto = "select \n"
            + "sum(ici.cantidad*ici.precio)  as subtotal\n"
            + "from compra_insumo ci,item_compra_insumo ici,insumo_producto ip,\n"
            + "insumo_categoria ic,insumo_unidad iu\n"
            + "where  ci.idcompra_insumo=ici.fk_idcompra_insumo\n"
            + "and ici.fk_idinsumo_producto=ip.idinsumo_producto\n"
            + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
            + "and ci.estado='CONFIRMADO' \n"
            + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad ";
    private String sql_cargar = "SELECT idcompra_insumo, monto_compra, \n"
            + "       observacion, estado, tiponota, indice, fk_idusuario\n"
            + "  FROM public.compra_insumo where idcompra_insumo=";

    //5,10,5,20,10,10,10,10,10,10
    public void cargar_compra_insumo(Connection conn, compra_insumo compi, int idcompra_insumo) {
        String titulo = "cargar_compra_insumo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcompra_insumo, titulo);
            if (rs.next()) {
                compi.setIdcompra_insumo(idcompra_insumo);
                compi.setMonto_compra(rs.getDouble(2));
                compi.setObservacion(rs.getString(3));
                compi.setEstado(rs.getString(4));
                compi.setTiponota(rs.getString(5));
                compi.setIndice(rs.getString(6));
                compi.setFk_idusuario(rs.getInt(7));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + compi.toString(), titulo);
        }
    }

    public void insertar_compra_insumo(Connection conn, compra_insumo comp) {
        int idcompra_insumo = (eveconn.getInt_ultimoID_mas_uno(conn, comp.getTabla(), comp.getIdtabla()));
        comp.setIdcompra_insumo(idcompra_insumo);
        comp.setFecha_emision(evefec.getString_formato_fecha_hora());
        String titulo = "insertar_compra_insumo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, comp.getIdcompra_insumo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
//            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setDouble(3, comp.getMonto_compra());
            pst.setString(4, comp.getObservacion());
            pst.setString(5, comp.getEstado());
            pst.setString(6, comp.getTiponota());
            pst.setString(7, comp.getIndice());
            pst.setInt(8, comp.getFk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + comp.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + comp.toString(), titulo);
        }
    }

    public void update_estado_compra_insumo(Connection conn, compra_insumo comp) {
        String titulo = "update_estado_compra_insumo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setString(1, comp.getEstado());
            pst.setString(2, comp.getTiponota());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setInt(4, comp.getIdcompra_insumo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + comp.toString(), titulo);
//            evemen.modificado_correcto(mensaje_terminar, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_anular + "\n" + comp.toString(), titulo);
        }
    }

    public void actualizar_tabla_compra_insumo(Connection conn, JTable tblcompra_insumo, String filtro) {
        String sql_select = "select ci.idcompra_insumo as idcompra,ci.indice,\n"
                + "to_char(ci.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "to_char(ci.fecha_confirmado,'yyyy-MM-dd HH24:MI') as confirmado, \n"
                + "ci.observacion,ci.estado,ci.tiponota,u.nombre as usuario,\n"
                + "TRIM(to_char(ci.monto_compra,'999G999G999')) as monto\n"
                + "from compra_insumo ci,usuario u\n"
                + "where ci.fk_idusuario=u.idusuario \n" + filtro
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tblcompra_insumo);
        ancho_tabla_compra_insumo(tblcompra_insumo);
        everende.rendertabla_estados(tblcompra_insumo, 5);
    }

    public void ancho_tabla_compra_insumo(JTable tblcompra_insumo) {
        int Ancho[] = {5, 5, 15, 15, 15, 12, 12, 13, 10};
        evejt.setAnchoColumnaJtable(tblcompra_insumo, Ancho);
    }

    public void imprimir_PRODUCTOS_MAS_COMPRADOS(Connection conn, String filtro_fecha, String idcategoria) {
        String sql = "select \n"
                + "ic.nombre as categoria,\n"
                + "ip.idinsumo_producto as idinsumo,\n"
                + "(ic.nombre||'-'||ip.nombre||'-'||iu.nom_compra) as producto_insumo,\n"
                + "(ici.precio) as precio,\n"
                + "sum(ici.cantidad) as cantidad,\n"
                + "sum(ici.cantidad*ici.precio)  as subtotal\n"
                + "from compra_insumo ci,item_compra_insumo ici,insumo_producto ip,\n"
                + "insumo_categoria ic,insumo_unidad iu\n"
                + "where  ci.idcompra_insumo=ici.fk_idcompra_insumo\n"
                + "and ici.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and ci.estado='CONFIRMADO' \n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n " + filtro_fecha + idcategoria
                + " group by 1,2,3,4\n"
                + " order by 1,2 desc";
        String titulonota = "PRODUCTOS MAS COMPRADOS";
        String direccion = "src/REPORTE/COMPRAINSUMO/repCOMPRA_INSUMO_mas_comprado.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void actualizar_tabla_filtro_compra_insumo(Connection conn, JTable tblfiltro_compra_insumo, String filtro_fecha, String idcategoria) {
        String sql_select = "select \n"
                + "ip.idinsumo_producto as idinsumo,\n"
                + "(ic.nombre||'-'||ip.nombre) as producto_insumo,\n"
                + "iu.nom_compra as unid_compra,"
                + "TRIM(to_char(ici.precio,'999G999G999')) as precio,\n"
                + "sum(ici.cantidad) as cantidad,\n"
                + "TRIM(to_char(sum(ici.cantidad*ici.precio),'999G999G999'))  as subtotal\n"
                + "from compra_insumo ci,item_compra_insumo ici,insumo_producto ip,\n"
                + "insumo_categoria ic,insumo_unidad iu\n"
                + "where  ci.idcompra_insumo=ici.fk_idcompra_insumo\n"
                + "and ici.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and ci.estado='CONFIRMADO' \n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n " + filtro_fecha + idcategoria
                + " group by 1,2,3,4 \n"
                + " order by sum(ici.cantidad*ici.precio) desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tblfiltro_compra_insumo);
        ancho_tabla_filtro_compra_insumo(tblfiltro_compra_insumo);
    }

    public void ancho_tabla_filtro_compra_insumo(JTable tblcompra_insumo) {
        int Ancho[] = {10, 45, 15, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tblcompra_insumo, Ancho);
    }

    public double sumar_monto_producto_mas_comprado(Connection conn, String filtro_fecha, String idcategoria) {
        return eveconn.getdouble_sql_suma(conn, sql_suma_monto + filtro_fecha + idcategoria);
    }
}
