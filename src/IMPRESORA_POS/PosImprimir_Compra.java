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
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_Compra {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    private static String v1_idcompra_insumo = "0";
    private static String v2_fecha = "0";
    private static String v3_usuario = "0";
    private static String v6_monto = "0";
    private static String v7_observacion = "0";
    private static String v8_proveedor = "oca";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_compra.txt";
    private static int dato_vector = 500;
    private static String[] iv1_cantidad = new String[dato_vector];
    private static String[] iv2_precio = new String[dato_vector];
    private static String[] iv3_total = new String[dato_vector];
    private static String[] iv4_descripcion = new String[dato_vector];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila;
    private static String nombre_ticket = "COMPRA";

    private void cargar_datos_compra(Connection conn, int idcompra_insumo) {
        String titulo = "cargar_datos_compra";
        String sql = "select ci.idcompra as idcompra,\n"
                + "to_char(ci.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "ci.observacion,u.nombre as usuario,\n"
                + "TRIM(to_char(ci.monto_compra,'999G999G999')) as monto,\n"
                + "(ici.fk_idproducto||'-'||ici.descripcion) as producto,\n"
                + "TRIM(to_char(ici.precio_compra,'999G999G999')) as precio,\n"
                + "TRIM(to_char(ici.cantidad,'999999D99')) as cantidad,\n"
                + "TRIM(to_char((ici.cantidad*ici.precio_compra),'999G999G999'))  as subtotal,p.nombre as proveedor \n"
                + "from compra ci,usuario u,item_compra ici,proveedor p \n"
                + "where ci.fk_idusuario=u.idusuario \n"
                + "and ci.fk_idproveedor=p.idproveedor \n"
                + "and ci.idcompra=ici.fk_idcompra\n"
                + "and ci.idcompra=" + idcompra_insumo
                + " order by ici.descripcion desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            while (rs.next()) {
                v1_idcompra_insumo = rs.getString("idcompra");
                v2_fecha = rs.getString("fecha");
                v3_usuario = rs.getString("usuario");
                v6_monto = rs.getString("monto");
                v7_observacion = rs.getString("observacion");
                v8_proveedor = rs.getString("proveedor");
                iv1_cantidad[tk_iv_fila] = rs.getString("cantidad");
                iv2_precio[tk_iv_fila] = rs.getString("precio");
                iv3_total[tk_iv_fila] = rs.getString("subtotal");
                iv4_descripcion[tk_iv_fila] = rs.getString("producto");
                tk_iv_fila++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "===============NOTA DE COMPRA================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "IDCOMPRA:" + v1_idcompra_insumo + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + v2_fecha + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO: " + v3_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "PROVEEDOR: " + v8_proveedor + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            mensaje_impresora = mensaje_impresora + iv4_descripcion[i] + saltolinea;
            String item = "C: " + iv1_cantidad[i] + tabular + "-P: " + iv2_precio[i] + tabular + "-T: " + iv3_total[i] + saltolinea;
            mensaje_impresora = mensaje_impresora + item;
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "OBSERVACION: " + v7_observacion + saltolinea;
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
        int totalfila = jsprint.getTt_fila_com_in() + (tk_iv_fila * 2);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + nombre_ticket + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, "IDCOM:" + v1_idcompra_insumo);
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_fecha(), totalColumna, "FEC:" + v2_fecha);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "USU: " + v3_usuario);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "PROV: " + v8_proveedor);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        for (int i = 0; i < tk_iv_fila; i++) {
            printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
            printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad[i] + " X");
            printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_precio(), totalColumna, iv2_precio[i] + " =");
            printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_subtotal(), totalColumna, iv3_total[i]);
            tempfila = tempfila + 2;
        }
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, "OBSERVACION: " + v7_observacion);
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
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), tk_ruta_archivo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_compra(Connection conn, int idcompra_insumo) {
        cargar_datos_compra(conn, idcompra_insumo);
        if (tk_iv_fila > 0) {
            crear_mensaje_textarea_y_confirmar();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO DATOS PARA ESTA COMPRA");
        }
    }
}
