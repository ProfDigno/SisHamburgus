/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
import Config_JSON.json_config;
import Config_JSON.json_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_caja_cierre_vgrupo {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    private static json_imprimir_pos jsprint=new json_imprimir_pos();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static String v1_idcaja_cierre = "0";
    private static String v2_fecha_inicio = "0";
    private static String v3_fecha_fin = "0";
    private static String v4_estado = "0";
    private static String v6_monto = "0";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_caja_cierre_grupo.txt";
    private static int cantidad_fila=1000;
    private static String[] iv1_cantidad = new String[cantidad_fila];
    private static String[] iv2_precio = new String[cantidad_fila];
    private static int[] iv2_precio_int = new int[cantidad_fila];
    private static String[] iv3_total = new String[cantidad_fila];
    private static String[] iv4_descripcion = new String[cantidad_fila];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila;
    private static int tk_iv_sum_fila;
    private String nombre_ticket = "ticket_venta";
    private static String nom_grupo="titulo";

    private void cargar_datos_venta(Connection conn, String fk_idcaja_cierre, String grupo) {
        String titulo = "cargar_datos_venta";
        String sql = "select cc.idcaja_cierre,\n"
                + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as fecha_inicio,\n"
                + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fecha_fin,cc.estado,\n"
                + "iv.descripcion as descripcion,\n"
                + "TRIM(to_char(iv.precio_venta,'999G999G999')) as precio,iv.precio_venta as intprecio,\n"
                + "iv.cantidad as cant,\n"
                + "TRIM(to_char((iv.precio_venta*iv.cantidad),'999G999G999')) as subtotal\n"
                + " from caja_detalle c,item_caja_cierre icc,venta v,item_venta iv,caja_cierre cc \n"
                + "where c.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and c.id_origen=v.idventa\n"
                + "and icc.fk_idcaja_cierre=cc.idcaja_cierre\n"
                + "and icc.fk_idcaja_cierre=" + fk_idcaja_cierre + "\n"
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO')\n"
                + " " + grupo + "\n"
                + "and c.tabla_origen='VENTA'\n"
                + "order by iv.iditem_venta asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            tk_iv_sum_fila=0;
            while (rs.next()) {
                v1_idcaja_cierre = rs.getString("idcaja_cierre");
                v2_fecha_inicio = rs.getString("fecha_inicio");
                v3_fecha_fin = rs.getString("fecha_fin");
                v4_estado = rs.getString("estado");
                iv1_cantidad[tk_iv_fila] = rs.getString("cant");
                iv2_precio[tk_iv_fila] = rs.getString("precio");
                iv3_total[tk_iv_fila] = rs.getString("subtotal");
                iv4_descripcion[tk_iv_fila] = rs.getString("descripcion");
                iv2_precio_int[tk_iv_fila] = rs.getInt("intprecio");
                if(iv2_precio_int[tk_iv_fila]!=0){
                    tk_iv_sum_fila++;
                }
                tk_iv_fila++;
            }
            v6_monto = getString_venta_grupo_cantidad_total(conn, fk_idcaja_cierre, grupo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }
String getString_venta_grupo_cantidad_total(Connection conn,String fk_idcaja_cierre, String grupo) {
        String total="0";
        String titulo = "venta_grupo_cantidad_total";
        String sql = "select count(*) as cantidad,\n"
                + "TRIM(to_char(sum((iv.precio_venta*iv.cantidad)+0),'999G999G999')) as total\n"
                + " from caja_detalle c,item_caja_cierre icc,venta v,item_venta iv \n"
                + "where c.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and c.id_origen=v.idventa\n"
                + "and icc.fk_idcaja_cierre=" + fk_idcaja_cierre + "\n"
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO')\n"
                + " " + grupo + "\n"
                + "and c.tabla_origen='VENTA';";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                 total = rs.getString("total");
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return total;
    }
    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "===============" + nom_grupo + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "CAJA CIERRE:" + v1_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA INICIO: " + v2_fecha_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA FIN: " + v3_fecha_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "ESTADO: " + v4_estado + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            mensaje_impresora = mensaje_impresora + iv4_descripcion[i] + saltolinea;
            if (iv2_precio_int[i] != 0) {
                String item = iv1_cantidad[i] + tabular + iv2_precio[i] + tabular + iv3_total[i] + saltolinea;
                mensaje_impresora = mensaje_impresora + item;
            }
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL :" + tabular + tabular + v6_monto + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_ccvg() + (tk_iv_fila + tk_iv_sum_fila);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + nom_grupo +jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, "CAJA CIERRE:" + v1_idcaja_cierre);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "FEC INI:");
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_fecha(), totalColumna, v2_fecha_inicio);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "FEC FIN:");
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_fecha(), totalColumna, v3_fecha_fin);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "ESTADO: ");
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_fecha(), totalColumna, v4_estado);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        for (int i = 0; i < tk_iv_fila; i++) {
                printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
            if (iv2_precio_int[i] != 0) {
                printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad[i] + " X");
                printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_precio(), totalColumna, iv2_precio[i] + " =");
                printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_subtotal(), totalColumna, iv3_total[i]);
                tempfila = tempfila + 2;
            } else {
                tempfila = tempfila + 1;
            }
        }
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna,jsprint.getLinea_separador());
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, "TOTAL :");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_total_gral(), totalColumna, v6_monto);

        printer.toFile(tk_ruta_archivo);
        try {
            inputStream = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
        if (inputStream == null) {
            return;
        }

    }

    void crear_archivo_enviar_impresora() {
        String titulo = "crear_archivo_enviar_impresora";
        try {
            crear_archivo_texto_impresion();
            pos.setInputStream(inputStream);
            pos.imprimir_ticket_Pos();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(20, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), nombre_ticket,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_VENTA_grupo(Connection conn, String fk_idcaja_cierre, String grupo,String nom_grupo) {
        this.nom_grupo=nom_grupo;
        cargar_datos_venta(conn, fk_idcaja_cierre, grupo);
        crear_mensaje_textarea_y_confirmar();
    }
}
