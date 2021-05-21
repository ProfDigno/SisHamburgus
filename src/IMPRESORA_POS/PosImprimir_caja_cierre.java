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
import FORMULARIO.DAO.DAO_producto_grupo;
import FORMULARIO.ENTIDAD.producto_grupo;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.print.PrintException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class PosImprimir_caja_cierre {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    DAO_producto_grupo pgDAO = new DAO_producto_grupo();
    producto_grupo pgru = new producto_grupo();
    private static String tk_usuario = "digno";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_caja_cierre.txt";
    private static FileInputStream inputStream = null;
    private static String nombre_ticket = "-CIERRE";
    private static String tk_idcaja_cierre = "000";
    private static String tk_inicio = "inicio";
    private static String tk_fin = "fin";
//     private static String tk_usuario = "fin";
    private static String tk_in_Abrir = "0";
    private static String tk_in_venta = "0";
    private static String tk_eg_compra = "0";
    private static String tk_eg_gasto = "0";
    private static String tk_eg_vale = "0";
    private static String tk_sistema_total = "0";
    private static String tk_sistema_sin_deli = "0";
    private static String tk_cierre = "0";
    private static String tk_diferencia = "0";
    private static String tk_venta_grupo0 = "0";
    private static String tk_venta_grupo1 = "0";
//    private static String tk_venta_deli = "0";
    private static String tk_venta_deli2 = "0";
    private static String nom_venta_grupo0 = "SIN G0";
    private static String nom_venta_grupo1 = "SIN G1";
    private static String nom_venta_deli = "DELIVERY";
    private static String[] iv1_cantidad_cate = new String[200];
    private static String[] iv2_nombre_cate = new String[200];
    private static int tk_iv_fila_cate;
    private static String[] iv1_cantidad_top = new String[200];
    private static String[] iv2_nombre_top = new String[200];
    private static int tk_iv_fila_anulado;
    private static String[] iv1_idventa_anulado = new String[200];
    private static String[] iv2_monto_anulado = new String[200];
    private static int tk_iv_fila_top;

    private void cargar_datos_caja_cierre(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_caja_cierre";
        String sql = "select cc.idcaja_cierre,\n"
                + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
                + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fin,u.nombre as usuario,\n"
                + "TRIM(to_char(sum(cd.monto_caja),'999G999G999')) as in_Abrir,\n"
                + "TRIM(to_char(sum(cd.monto_venta),'999G999G999')) as in_venta,\n"
                + "TRIM(to_char(sum(cd.monto_delivery),'999G999G999')) as in_delivery,\n"
                + "TRIM(to_char(sum(cd.monto_compra),'999G999G999')) as eg_compra,\n"
                + "TRIM(to_char(sum(cd.monto_gasto),'999G999G999')) as eg_gasto,\n"
                + "TRIM(to_char(sum(cd.monto_vale),'999G999G999')) as eg_vale,\n"
                + "TRIM(to_char((sum(cd.monto_caja+cd.monto_venta))-(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale)),'999G999G999')) as sistema_total,\n"
                + "TRIM(to_char((sum(cd.monto_caja+cd.monto_venta))-(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale+cd.monto_delivery)),'999G999G999')) as sistema_sin_deli,\n"
                + "TRIM(to_char(sum(cd.monto_cierre),'999G999G999')) as cierre,\n"
                + "TRIM(to_char((sum(cd.monto_cierre)-((sum(cd.monto_caja+cd.monto_venta))-"
                + "(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale+cd.monto_delivery)))),'999G999G999')) as diferencia\n"
                + "from caja_cierre cc,item_caja_cierre icc,caja_detalle cd,usuario u \n"
                + "where cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
                + "and cd.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and cc.fk_idusuario=u.idusuario "
                + "and cc.idcaja_cierre=" + idcaja_cierre
                + "group by 1,2,3,4\n";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tk_idcaja_cierre = rs.getString("idcaja_cierre");
                tk_inicio = rs.getString("inicio");
                tk_fin = rs.getString("fin");
                tk_usuario = rs.getString("usuario");
                tk_in_Abrir = rs.getString("in_Abrir");
                tk_in_venta = rs.getString("in_venta");
                tk_venta_deli2 = rs.getString("in_delivery");
                tk_eg_compra = rs.getString("eg_compra");
                tk_eg_gasto = rs.getString("eg_gasto");
                tk_eg_vale = rs.getString("eg_vale");
                tk_sistema_total = rs.getString("sistema_total");
                tk_sistema_sin_deli = rs.getString("sistema_sin_deli");
                tk_cierre = rs.getString("cierre");
                tk_diferencia = rs.getString("diferencia");
                cargar_producto_grupo(conn);
                String grupo_0 = " and iv.grupo=0 and (tipo='P' or tipo='I' or tipo='S') ";
                tk_venta_grupo0 = getString_venta_grupo_cantidad_total(conn, idcaja_cierre, grupo_0);
                String grupo_1 = " and iv.grupo=1 and (tipo='P' or tipo='N') ";
                tk_venta_grupo1 = getString_venta_grupo_cantidad_total(conn, idcaja_cierre, grupo_1);
//                String grupo_delivery = " and iv.grupo=0 and tipo='D' ";
//                tk_venta_deli = getString_venta_grupo_cantidad_total(conn, idcaja_cierre, grupo_delivery);

            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_datos_itemventa_categoria(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_itemventa_categoria";
        String sql = "select (pc.nombre) as categoria,sum(iv.cantidad) as cant\n"
                + "from caja_cierre cc,item_caja_cierre icc,caja_detalle cd,\n"
                + "venta v,item_venta iv,producto p,producto_categoria pc,producto_unidad pu\n"
                + "where cc.idcaja_cierre=\n" + idcaja_cierre
                + " and cd.tabla_origen='VENTA'\n"
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO')\n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
                + "and icc.fk_idcaja_detalle=cd.idcaja_detalle\n"
                + "and cd.id_origen=v.idventa\n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and iv.precio_venta>0\n"
                + "and (iv.tipo='P' or iv.tipo='N') \n"
                + "group by 1\n"
                + "order by 2 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila_cate = 0;
            while (rs.next()) {
                iv1_cantidad_cate[tk_iv_fila_cate] = rs.getString("cant");
                iv2_nombre_cate[tk_iv_fila_cate] = rs.getString("categoria");
                tk_iv_fila_cate++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_datos_itemventa_top_producto(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_itemventa_top_producto";
        String sql = "select (pu.nombre||'-'||p.nombre) as producto,sum(iv.cantidad) as cant\n"
                + "from caja_cierre cc,item_caja_cierre icc,caja_detalle cd,\n"
                + "venta v,item_venta iv,producto p,producto_categoria pc,producto_unidad pu\n"
                + "where cc.idcaja_cierre=\n" + idcaja_cierre
                + " and cd.tabla_origen='VENTA'\n"
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO')\n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
                + "and icc.fk_idcaja_detalle=cd.idcaja_detalle\n"
                + "and cd.id_origen=v.idventa\n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and iv.precio_venta>0\n"
                + "and (iv.tipo='P' or iv.tipo='N') \n"
                + "group by 1\n"
                + "order by 2 desc limit " + jsprint.getCant_top_venta();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila_top = 0;
            while (rs.next()) {
                iv1_cantidad_top[tk_iv_fila_top] = rs.getString("cant");
                iv2_nombre_top[tk_iv_fila_top] = rs.getString("producto");
                tk_iv_fila_top++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_datos_venta_anulada(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_venta_anulada";
        String sql = "select v.idventa as idventa,\n"
                + "TRIM(to_char(v.monto_venta,'999G999G999')) as total\n"
                + "from caja_detalle c,item_caja_cierre icc,venta v\n"
                + "where c.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and c.id_origen=v.idventa\n"
                + "and icc.fk_idcaja_cierre=\n"+idcaja_cierre
                + " and (v.estado='ANULADO' or v.estado='ANULADO_temp')\n"
                + "and c.tabla_origen='VENTA' order by 1 asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila_anulado = 0;
            while (rs.next()) {
                iv1_idventa_anulado[tk_iv_fila_anulado] = rs.getString("idventa");
                iv2_monto_anulado[tk_iv_fila_anulado] = rs.getString("total");
                tk_iv_fila_anulado++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    String getString_venta_grupo_cantidad_total(Connection conn, int fk_idcaja_cierre, String grupo) {
        String total = "0";
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
        if (total == null) {
            total = "0";
        }
        return total;
    }

    void cargar_producto_grupo(Connection conn) {
        pgDAO.cargar_producto_grupo(conn, pgru, 0);
        nom_venta_grupo0 = pgru.getC2nombre();
        pgDAO.cargar_producto_grupo(conn, pgru, 1);
        nom_venta_grupo1 = pgru.getC2nombre();

    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "------->> ";
        mensaje_impresora = mensaje_impresora + "=======" + config.getNombre_sistema() + nombre_ticket + "========" + saltolinea;
        mensaje_impresora = mensaje_impresora + "CODIGO:" + tk_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + tk_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "INICIO: " + tk_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + "FIN: " + tk_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + " CAJA ABRIR: " + tabular + tk_in_Abrir + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL VENTA: " + tabular + tk_in_venta + saltolinea;
        mensaje_impresora = mensaje_impresora + "------------------------------------------" + saltolinea;
        mensaje_impresora = mensaje_impresora + nom_venta_grupo0 + ": " + tabular + tk_venta_grupo0 + saltolinea;
        mensaje_impresora = mensaje_impresora + nom_venta_grupo1 + ": " + tabular + tk_venta_grupo1 + saltolinea;
        mensaje_impresora = mensaje_impresora + nom_venta_deli + ": " + tabular + tk_venta_deli2 + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + " TOTAL GASTO: " + tabular + tk_eg_gasto + saltolinea;
        mensaje_impresora = mensaje_impresora + "  TOTAL VALE: " + tabular + tk_eg_vale + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL COMPRA: " + tabular + tk_eg_compra + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "SISTEMA TOTAL: " + tabular + tk_sistema_total + saltolinea;
        mensaje_impresora = mensaje_impresora + "SISTEMA SIN DELIVERY: " + tabular + tk_sistema_sin_deli + saltolinea;
        mensaje_impresora = mensaje_impresora + "     CIERRE: " + tabular + tk_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + " DIFERENCIA: " + tabular + tk_diferencia + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_cc() + tk_iv_fila_cate + tk_iv_fila_top+tk_iv_fila_anulado;
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + config.getNombre_sistema() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, 10, totalColumna, "CODIGO:" + tk_idcaja_cierre);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "INICIO:");
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_fecha(), totalColumna, tk_inicio);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "FIN:");
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_fecha(), totalColumna, tk_fin);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + tk_usuario);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "CAJA ABRIR: ");
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_numero(), totalColumna, tk_in_Abrir);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, "TOTAL VENTA: ");
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_numero(), totalColumna, tk_in_venta);
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_ven_detalle());
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, nom_venta_grupo0 + ":");
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_numero(), totalColumna, tk_venta_grupo0);
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, nom_venta_grupo1 + ":");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_numero(), totalColumna, tk_venta_grupo1);
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, "DELIVERY:");
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_numero(), totalColumna, tk_venta_deli2);
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), totalColumna, "TOTAL GASTO:");
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_numero(), totalColumna, tk_eg_gasto);
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, "TOTAL VALE:");
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_numero(), totalColumna, tk_eg_vale);
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), totalColumna, "TOTAL COMPRA:");
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_numero(), totalColumna, tk_eg_compra);
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, "SIS. TOTAL:");
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_numero(), totalColumna, tk_sistema_total);
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_inicio(), totalColumna, "SIS. SIN DELIVERY:");
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_numero(), totalColumna, tk_sistema_sin_deli);
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_inicio(), totalColumna, "CIERRE:");
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_numero(), totalColumna, tk_cierre);
        printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_inicio(), totalColumna, "DIFERENCIA:");
        printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_numero(), totalColumna, "(" + tk_diferencia + ")");
        printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_ven_categoria());
        for (int i = 0; i < tk_iv_fila_cate; i++) {
            printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad_cate[i] + " X");
            printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_item_precio(), jsprint.getTt_text_descrip(), iv2_nombre_cate[i]);
            tempfila = tempfila + 1;
        }
        printer.printTextWrap(24 + tempfila, 24, jsprint.getSep_inicio(), totalColumna,
                jsprint.getLinea_ven_top_1() + jsprint.getCant_top_venta() + jsprint.getLinea_ven_top_2());
        for (int i = 0; i < tk_iv_fila_top; i++) {
            printer.printTextWrap(25 + tempfila, 25, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad_top[i] + " X");
            printer.printTextWrap(25 + tempfila, 25, jsprint.getSep_item_precio(), jsprint.getTt_text_descrip(), iv2_nombre_top[i]);
            tempfila = tempfila + 1;
        }
        printer.printTextWrap(26 + tempfila, 26, jsprint.getSep_inicio(), totalColumna,"------ANULADOS-----");
        for (int i = 0; i < tk_iv_fila_anulado; i++) {
            printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_item_cant(), totalColumna, "idv:"+iv1_idventa_anulado[i] + " monto:");
            printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_numero(), jsprint.getTt_text_descrip(), iv2_monto_anulado[i]);
            tempfila = tempfila + 1;
        }
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

    public void boton_imprimir_pos_caja_cierre(Connection conn, int idcaja_cierre) {
        cargar_datos_caja_cierre(conn, idcaja_cierre);
        cargar_datos_itemventa_categoria(conn, idcaja_cierre);
        cargar_datos_itemventa_top_producto(conn, idcaja_cierre);
        cargar_datos_venta_anulada(conn, idcaja_cierre);
        crear_mensaje_textarea_y_confirmar();
    }
}
