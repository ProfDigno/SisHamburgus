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
public class PosImprimir_comanda {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    private static String v1_idventa = "0";
    private static String v2_fecha = "0";
    private static String v3_cliente = "0";
    private static String v5_direccion = "0";
    private static String v8_tipo_entrega = "local";
    private static String v7_observacion = "0";
    private static String v9_usuario = "0";
    private static String v11_forma_pago = "pago";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_comanda.txt";
    private static String[] iv1_cantidad = new String[200];
    private static String[] iv4_descripcion = new String[200];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila;
    private String nombre_ticket = "ticket_venta";
    private static int tk_iv_sum_fila;

    private void cargar_datos_venta(Connection conn, int idventa) {
        String titulo = "cargar_datos_venta";
        String sql = "select v.idventa,v.tipo_entrega,to_char(v.fecha_inicio,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "(c.idcliente||'-'||c.nombre) as cliente,\n"
                + "c.telefono,c.direccion,\n"
                + "TRIM(to_char(v.monto_venta,'999G999G999')) as monto,\n"
                + "v.observacion,iv.cantidad,iv.descripcion,\n"
                + "TRIM(to_char(iv.precio_venta,'999G999G999')) as precio,iv.precio_venta as precioint,\n"
                + "TRIM(to_char((iv.cantidad*iv.precio_venta),'999G999G999'))  as total,u.nombre as usuario,v.forma_pago \n"
                + "from venta v,cliente c,item_venta iv,usuario u \n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.fk_idusuario=u.idusuario\n "
                + "and v.idventa=iv.fk_idventa\n"
                + "and iv.tipo!='D'\n"
                + "and  v.idventa=" + idventa
                + " order by iv.iditem_venta asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            tk_iv_sum_fila = 0;
            while (rs.next()) {
                v1_idventa = rs.getString("idventa");
                v8_tipo_entrega = rs.getString("tipo_entrega");
                v2_fecha = rs.getString("fecha");
                v3_cliente = rs.getString("cliente");
                v5_direccion = rs.getString("direccion");
                v7_observacion = rs.getString("observacion");
                v9_usuario = rs.getString("usuario");
                v11_forma_pago = rs.getString("forma_pago");
                iv1_cantidad[tk_iv_fila] = rs.getString("cantidad");
                iv4_descripcion[tk_iv_fila] = rs.getString("descripcion");
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
        mensaje_impresora = mensaje_impresora + "===============" + config.getNombre_sistema() + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + v8_tipo_entrega + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + v9_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "VENTA:" + v1_idventa + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + v2_fecha + saltolinea;
        mensaje_impresora = mensaje_impresora + "CLIENTE: " + v3_cliente + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            mensaje_impresora = mensaje_impresora + iv1_cantidad[i] + tabular + iv4_descripcion[i] + saltolinea;
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "OBS: " + v7_observacion + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_coman() + (tk_iv_fila + tk_iv_sum_fila);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + config.getNombre_sistema() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, v8_tipo_entrega);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + v9_usuario);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "VENTA:" + v1_idventa);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_fecha(), totalColumna, "FEC:" + v2_fecha);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "CLI: " + v3_cliente);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "DIR: " + v5_direccion);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        for (int i = 0; i < tk_iv_fila; i++) {
            printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad[i] + " X");
            printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_item_precio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
            tempfila = tempfila + 1;
        }
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, "PAGO: " + v11_forma_pago);
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, "OBS: " + v7_observacion);

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

    public void boton_imprimir_pos_COMANDA(Connection conn, int idventa) {
        if (jsprint.isPrint_comanda()) {
            cargar_datos_venta(conn, idventa);
            crear_mensaje_textarea_y_confirmar();
        }
    }
}
