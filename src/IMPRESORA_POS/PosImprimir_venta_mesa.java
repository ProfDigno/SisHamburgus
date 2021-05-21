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
public class PosImprimir_venta_mesa {

    ClaImpresoraPos clapos = new ClaImpresoraPos();
    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config=new json_config();
    private static json_imprimir_pos jsprint=new json_imprimir_pos();
    private static String[] tk_iv_cantidad = new String[200];
    private static String[] tk_iv_descripcion = new String[200];
    private static String[] tk_iv_precioUni = new String[200];
    private static int[] tk_iv_IntPrecioUni = new int[200];
    private static String[] tk_iv_subtotal = new String[200];
    private static String ven_fecha_cierre;
    private static int tk_iv_fila;
    private static String tk_ven_totalmonto;
    private static String ven_idventa_mesa;
    private static FileInputStream tk_entrada_dato_completo = null;
    private static String ven_cliente_mesa;
    private static String tk_ruta_archivo = "ticket_venta_mesa.txt";
    private static String nombre_ticket = "-VENTA MESA-";
    private static int tk_iv_sum_fila;
    void cargar_datos_venta_mesa(Connection conn, String idventa_mesa, String idmesa, int tipo) {
        String titulo = "cargar_datos_venta_mesa";
        String estado = "";
        String id = "";
        if (tipo == 1) {
            id = " and vm.iditem_venta_mesa=" + idventa_mesa;
            estado = "";
        }
        if (tipo == 2) {
            id = " and vm.fk_idventa_mesa=" + idmesa;
            estado = " and vm.estado='ABIERTO' ";
        }
        String sql = "select vm.iditem_venta_mesa,to_char(vm.fecha_cierre,'yyyy-MM-dd HH24:MI') as fecha_cierre,\n"
                + "m.nombre as mesa,to_char(vm.totalmonto,'999G999G999') as totalmonto\n"
                + "from item_venta_mesa vm,venta_mesa m \n"
                + "where m.idventa_mesa=vm.fk_idventa_mesa\n"
                + estado + " \n"
                + id + " \n";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                ven_idventa_mesa = rs.getString("iditem_venta_mesa");
                ven_fecha_cierre = rs.getString("fecha_cierre");
                ven_cliente_mesa = rs.getString("mesa");
                tk_ven_totalmonto = rs.getString("totalmonto").trim();
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public void boton_imprimir_pos_venta_mesa(Connection conn, String idventa_mesa, String idmesa, int tipo) {

        cargar_datos_venta_mesa(conn, idventa_mesa, idmesa, tipo);
        cargar_datos_itempedido(conn, idventa_mesa, idmesa, tipo);
        buscar_error_impresion_pos();
    }

    public String getString_venta_mesa(Connection conn, String idventa_mesa, String idmesa, int tipo) {
        String getmesa = "";
        cargar_datos_venta_mesa(conn, idventa_mesa, idmesa, tipo);
        cargar_datos_itempedido(conn, idventa_mesa, idmesa, tipo);
        getmesa = cargar_datos_para_mensaje_textarea();
        return getmesa;
    }

    void cargar_datos_itempedido(Connection conn, String idventa_mesa, String idmesa, int tipo) {
        String titulo = "";
        String estado = "";
        String id = "";
        if (tipo == 1) {
            id = " and vm.iditem_venta_mesa=" + idventa_mesa;
            estado = "";
        }
        if (tipo == 2) {
            id = " and vm.fk_idventa_mesa=" + idmesa;
            estado = " and (vc.estado='EMITIDO' or vc.estado='TERMINADO') ";
        }
        System.out.println("INICIO:tk_cargar_itempedido");
        String sql = "select TRIM(to_char(ip.cantidad,'999')) as cantidad,ip.descripcion,ip.precio_venta,\n"
                + "(ip.cantidad*ip.precio_venta) as subtotal,\n"
                + "to_char(ip.precio_venta,'9G999G999') as precio2,ip.precio_venta,\n"
                + "to_char((ip.cantidad*ip.precio_venta),'9G999G999') as subtotal2,\n"
                + "vc.idventa,vm.iditem_venta_mesa,vm.fk_idventa_mesa \n"
                + "from item_venta ip,venta vc,item_venta_mesa vm,item_venta_mesa_venta ivm \n"
                + "where vc.idventa=ip.fk_idventa\n"
                + "and vm.estado='ABIERTO'\n"
                + estado + "\n"
                + "and vm.iditem_venta_mesa=ivm.fk_iditem_venta_mesa\n"
                + "and ivm.fk_idventa=vc.idventa\n"
                + id + " \n"
                + "order by ip.iditem_venta asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            tk_iv_sum_fila = 0;
            while (rs.next()) {
                String cantidad = rs.getString("cantidad");
                String descripcion = rs.getString("descripcion");
                String precio = rs.getString("precio2");
                int intprecio = rs.getInt("precio_venta");
                String subtotal = rs.getString("subtotal2");
                precio = (precio.trim());
                subtotal = (subtotal.trim());
                tk_iv_cantidad[tk_iv_fila] = cantidad.trim();
                tk_iv_descripcion[tk_iv_fila] = descripcion.trim();
                tk_iv_precioUni[tk_iv_fila] = precio.trim();
                tk_iv_IntPrecioUni[tk_iv_fila] = intprecio;
                tk_iv_subtotal[tk_iv_fila] = subtotal.trim();
                if(tk_iv_IntPrecioUni[tk_iv_fila]!=0){
                    tk_iv_sum_fila++;
                }
                tk_iv_fila++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        System.out.println("FIN:tk_cargar_itempedido");
    }

    void buscar_error_impresion_pos() {
        System.out.println("INICIO:tk_seleccionar_impresora");
        for (int i = 0; i < tk_iv_fila; i++) {
            String item = tk_iv_cantidad[i] + "-->" + tk_iv_descripcion[i] + "->" + tk_iv_precioUni[i] + "->" + tk_iv_subtotal[i];
            System.out.println(item);
        }
        try {
            crear_archivo_texto_impresion();
            crear_mensaje_textarea_y_confirmar();
        } catch (Exception e) {
            System.out.println("ERROR:tk_seleccionar_impresora:" + e);
        }
        System.out.println("FIN:tk_seleccionar_impresora");
    }

    void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(20, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), tk_ruta_archivo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            buscar_error_inputStream();
        }
    }

    void buscar_error_inputStream() {
        String titulo = "buscar_error_inputStream";
        try {
            clapos.setInputStream(tk_entrada_dato_completo);
            clapos.imprimir_ticket_Pos();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "==##" + config.getNombre_sistema() + "##==" + saltolinea;
        mensaje_impresora = mensaje_impresora + "Cliente Oca: " + ven_cliente_mesa + saltolinea;
        mensaje_impresora = mensaje_impresora + "idventamesa: " + ven_idventa_mesa + saltolinea;
        mensaje_impresora = mensaje_impresora + "Fec:" + ven_fecha_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "====================================" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            mensaje_impresora = mensaje_impresora + tk_iv_descripcion[i] + saltolinea;
            if (tk_iv_IntPrecioUni[i] != 0) {
                String item = tk_iv_cantidad[i] + tabular + tk_iv_precioUni[i] + tabular + tk_iv_subtotal[i] + saltolinea;
                mensaje_impresora = mensaje_impresora + item;
            }
        }
        mensaje_impresora = mensaje_impresora + "====================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL: " + tabular + tabular + tk_ven_totalmonto + saltolinea;
        if (tk_iv_fila == 0) {
            mensaje_impresora = "SIN DATOS";
        }
        return mensaje_impresora;
    }

    public static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        System.out.println("INICIO:tk_crear_archivo_impresion");
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        String pedidoticket = "000";
        if (Integer.parseInt(ven_idventa_mesa) > 1000) {
            pedidoticket = ven_idventa_mesa.substring(ven_idventa_mesa.length() - 3, ven_idventa_mesa.length());
        } else {
            pedidoticket = ven_idventa_mesa;
        }
        int totalfila = jsprint.getTt_fila_ven_ms() + (tk_iv_fila + tk_iv_sum_fila);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna,jsprint.getLinea_cabezera() + nombre_ticket + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, "Cli: " + ven_cliente_mesa);
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_fecha(), totalColumna, "idvme: " + ven_idventa_mesa + "-(" + pedidoticket + ")");
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "FECHA:" + ven_fecha_cierre);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna,jsprint.getLinea_separador());
        for (int i = 0; i < tk_iv_fila; i++) {
                printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(),jsprint.getTt_text_descrip(), tk_iv_descripcion[i]);//descripcionImp
            if (tk_iv_IntPrecioUni[i] != 0) {
                printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_item_cant(), totalColumna, tk_iv_cantidad[i] + " X");
                printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_item_precio(), totalColumna, tk_iv_precioUni[i] + " =");
                printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_item_subtotal(), totalColumna, tk_iv_subtotal[i]);
                tempfila = tempfila + 2;
            } else {
                tempfila = tempfila + 1;
            }
        }
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna,jsprint.getLinea_separador());
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "TOTAL: ");
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_total_gral(), totalColumna, tk_ven_totalmonto);
        printer.toFile(tk_ruta_archivo);
        try {
            tk_entrada_dato_completo = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("ERROR:tk_crear_archivo_impresion:" + ex);
        }
        if (tk_entrada_dato_completo == null) {
            return;
        }
        System.out.println("FIN:tk_crear_archivo_impresion");
    }
}
