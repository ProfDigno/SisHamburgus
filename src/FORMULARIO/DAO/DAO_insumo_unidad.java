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
import FORMULARIO.ENTIDAD.insumo_unidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_insumo_unidad {

    /**
     * CREATE TABLE "insumo_unidad" ( "idinsumo_unidad" SERIAL NOT NULL ,
     * "nom_compra" TEXT NOT NULL , "nom_venta" TEXT NOT NULL ,
     * "conversion_unidad" NUMERIC(10,2) NOT NULL , PRIMARY
     * KEY("idinsumo_unidad") );
     */
    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "UNIDAD GUARDADO CORRECTAMENTE";
    private String mensaje_update = "UNIDAD MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.insumo_unidad(\n"
            + "            idinsumo_unidad, nom_compra, nom_venta, conversion_unidad)\n"
            + "    VALUES (?, ?, ?, ?);";
    private String sql_update = "UPDATE public.insumo_unidad\n"
            + "   SET  nom_compra=?, nom_venta=?, conversion_unidad=?\n"
            + " WHERE idinsumo_unidad=?;";
    private String sql_select = "select idinsumo_unidad,nom_compra,nom_venta,conversion_unidad from insumo_unidad order by 2 asc";
    private String sql_cargar = "select nom_compra,nom_venta,conversion_unidad from insumo_unidad where idinsumo_unidad=";

    public void cargar_insumo_unidad(insumo_unidad unid, JTable tabla) {
        String titulo = "cargar_insumo_unidad";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                unid.setIdinsumo_unidad(id);
                unid.setNom_compra(rs.getString("nom_compra"));
                unid.setNom_venta(rs.getString("nom_venta"));
                unid.setConversion_unidad(rs.getDouble("conversion_unidad"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + unid.toString(), titulo);
        }
    }

    public void insertar_insumo_unidad(Connection conn, insumo_unidad unid) {
        unid.setIdinsumo_unidad(eveconn.getInt_ultimoID_mas_uno(conn, unid.getTabla(), unid.getIdtabla()));
        String titulo = "insertar_insumo_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, unid.getIdinsumo_unidad());
            pst.setString(2, unid.getNom_compra());
            pst.setString(3, unid.getNom_venta());
            pst.setDouble(4, unid.getConversion_unidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + unid.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + unid.toString(), titulo);
        }
    }

    public void update_insumo_unidad(Connection conn, insumo_unidad unid) {
        String titulo = "update_insumo_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, unid.getNom_compra());
            pst.setString(2, unid.getNom_venta());
            pst.setDouble(3, unid.getConversion_unidad());
            pst.setInt(4, unid.getIdinsumo_unidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + unid.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + unid.toString(), titulo);
        }
    }

    public void actualizar_tabla_insumo_unidad(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_insumo_unidad(tbltabla);
    }

    public void ancho_tabla_insumo_unidad(JTable tbltabla) {
        int Ancho[] = {10,30,30,30};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
