/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import java.io.FileReader;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */

public class json_imprimir_pos {

    /**
     * @return the print_comanda
     */
    public static boolean isPrint_comanda() {
        return print_comanda;
    }

    /**
     * @param aPrint_comanda the print_comanda to set
     */
    public static void setPrint_comanda(boolean aPrint_comanda) {
        print_comanda = aPrint_comanda;
    }

    /**
     * @return the linea_ven_top_1
     */
    public static String getLinea_ven_top_1() {
        return linea_ven_top_1;
    }

    /**
     * @param aLinea_ven_top_1 the linea_ven_top_1 to set
     */
    public static void setLinea_ven_top_1(String aLinea_ven_top_1) {
        linea_ven_top_1 = aLinea_ven_top_1;
    }

    /**
     * @return the linea_ven_top_2
     */
    public static String getLinea_ven_top_2() {
        return linea_ven_top_2;
    }

    /**
     * @param aLinea_ven_top_2 the linea_ven_top_2 to set
     */
    public static void setLinea_ven_top_2(String aLinea_ven_top_2) {
        linea_ven_top_2 = aLinea_ven_top_2;
    }

    /**
     * @return the cant_top_venta
     */
    public static int getCant_top_venta() {
        return cant_top_venta;
    }

    /**
     * @param aCant_top_venta the cant_top_venta to set
     */
    public static void setCant_top_venta(int aCant_top_venta) {
        cant_top_venta = aCant_top_venta;
    }

    /**
     * @return the tt_fila_coman
     */
    public static int getTt_fila_coman() {
        return tt_fila_coman;
    }

    /**
     * @param aTt_fila_coman the tt_fila_coman to set
     */
    public static void setTt_fila_coman(int aTt_fila_coman) {
        tt_fila_coman = aTt_fila_coman;
    }

    /**
     * @return the tt_fila_ccvg
     */
    public static int getTt_fila_ccvg() {
        return tt_fila_ccvg;
    }

    /**
     * @param aTt_fila_ccvg the tt_fila_ccvg to set
     */
    public static void setTt_fila_ccvg(int aTt_fila_ccvg) {
        tt_fila_ccvg = aTt_fila_ccvg;
    }

    /**
     * @return the tt_fila_cc
     */
    public static int getTt_fila_cc() {
        return tt_fila_cc;
    }

    /**
     * @param aTt_fila_cc the tt_fila_cc to set
     */
    public static void setTt_fila_cc(int aTt_fila_cc) {
        tt_fila_cc = aTt_fila_cc;
    }

    /**
     * @return the tt_fila_ven
     */
    public static int getTt_fila_ven() {
        return tt_fila_ven;
    }

    /**
     * @param aTt_fila_ven the tt_fila_ven to set
     */
    public static void setTt_fila_ven(int aTt_fila_ven) {
        tt_fila_ven = aTt_fila_ven;
    }

    /**
     * @return the tt_fila_ven_ms
     */
    public static int getTt_fila_ven_ms() {
        return tt_fila_ven_ms;
    }

    /**
     * @param aTt_fila_ven_ms the tt_fila_ven_ms to set
     */
    public static void setTt_fila_ven_ms(int aTt_fila_ven_ms) {
        tt_fila_ven_ms = aTt_fila_ven_ms;
    }

    /**
     * @return the tt_fila_com_in
     */
    public static int getTt_fila_com_in() {
        return tt_fila_com_in;
    }

    /**
     * @param aTt_fila_com_in the tt_fila_com_in to set
     */
    public static void setTt_fila_com_in(int aTt_fila_com_in) {
        tt_fila_com_in = aTt_fila_com_in;
    }

    /**
     * @return the tt_fila_gas
     */
    public static int getTt_fila_gas() {
        return tt_fila_gas;
    }

    /**
     * @param aTt_fila_gas the tt_fila_gas to set
     */
    public static void setTt_fila_gas(int aTt_fila_gas) {
        tt_fila_gas = aTt_fila_gas;
    }

    /**
     * @return the tt_fila_val
     */
    public static int getTt_fila_val() {
        return tt_fila_val;
    }

    /**
     * @param aTt_fila_val the tt_fila_val to set
     */
    public static void setTt_fila_val(int aTt_fila_val) {
        tt_fila_val = aTt_fila_val;
    }

    /**
     * @return the linea_ven_detalle
     */
    public static String getLinea_ven_detalle() {
        return linea_ven_detalle;
    }

    /**
     * @param aLinea_ven_detalle the linea_ven_detalle to set
     */
    public static void setLinea_ven_detalle(String aLinea_ven_detalle) {
        linea_ven_detalle = aLinea_ven_detalle;
    }

    /**
     * @return the linea_separador
     */
    public static String getLinea_separador() {
        return linea_separador;
    }

    /**
     * @param aLinea_separador the linea_separador to set
     */
    public static void setLinea_separador(String aLinea_separador) {
        linea_separador = aLinea_separador;
    }

    /**
     * @return the sep_inicio
     */
    public static int getSep_inicio() {
        return sep_inicio;
    }

    /**
     * @param aSep_inicio the sep_inicio to set
     */
    public static void setSep_inicio(int aSep_inicio) {
        sep_inicio = aSep_inicio;
    }

    /**
     * @return the sep_numero
     */
    public static int getSep_numero() {
        return sep_numero;
    }

    /**
     * @param aSep_numero the sep_numero to set
     */
    public static void setSep_numero(int aSep_numero) {
        sep_numero = aSep_numero;
    }

    /**
     * @return the sep_item_cant
     */
    public static int getSep_item_cant() {
        return sep_item_cant;
    }

    /**
     * @param aSep_item_cant the sep_item_cant to set
     */
    public static void setSep_item_cant(int aSep_item_cant) {
        sep_item_cant = aSep_item_cant;
    }

    /**
     * @return the sep_item_precio
     */
    public static int getSep_item_precio() {
        return sep_item_precio;
    }

    /**
     * @param aSep_item_precio the sep_item_precio to set
     */
    public static void setSep_item_precio(int aSep_item_precio) {
        sep_item_precio = aSep_item_precio;
    }

    /**
     * @return the sep_item_subtotal
     */
    public static int getSep_item_subtotal() {
        return sep_item_subtotal;
    }

    /**
     * @param aSep_item_subtotal the sep_item_subtotal to set
     */
    public static void setSep_item_subtotal(int aSep_item_subtotal) {
        sep_item_subtotal = aSep_item_subtotal;
    }

    /**
     * @return the sep_total_gral
     */
    public static int getSep_total_gral() {
        return sep_total_gral;
    }

    /**
     * @param aSep_total_gral the sep_total_gral to set
     */
    public static void setSep_total_gral(int aSep_total_gral) {
        sep_total_gral = aSep_total_gral;
    }

    /**
     * @return the total_columna
     */
    public static int getTotal_columna() {
        return total_columna;
    }

    /**
     * @param aTotal_columna the total_columna to set
     */
    public static void setTotal_columna(int aTotal_columna) {
        total_columna = aTotal_columna;
    }
    private static String linea_separador;
    private static String linea_ven_detalle;
    private static String linea_ven_categoria;
    private static String linea_cabezera;
    private static String linea_ven_top_1;
    private static String linea_ven_top_2;
    private static int cant_top_venta;
    private static int salto_linea_item;
    private static int sep_inicio;
    private static int sep_numero;
    private static int sep_fecha;
    private static int sep_item_cant;
    private static int sep_item_precio;
    private static int sep_item_subtotal;
    private static int sep_total_gral;
    private static int total_columna;
    private static int tt_fila_ccvg;
    private static int tt_fila_cc;
    private static int tt_fila_ven;
    private static int tt_fila_coman;
    private static int tt_fila_ven_ms;
    private static int tt_fila_com_in;
    private static int tt_fila_gas;
    private static int tt_fila_val;
    private static int tt_text_descrip;
    private static boolean print_comanda;
    public void cargar_jsom_imprimir_pos() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src\\json_imprimir_pos.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String linea_separador = (String) jsonObject.get("linea_separador");
            String linea_ven_detalle = (String) jsonObject.get("linea_ven_detalle");
            String linea_ven_categoria = (String) jsonObject.get("linea_ven_categoria");
            String linea_cabezera = (String) jsonObject.get("linea_cabezera");
            String salto_linea_item = (String) jsonObject.get("salto_linea_item");
            String sep_inicio = (String) jsonObject.get("sep_inicio");
            String sep_numero = (String)jsonObject.get("sep_numero");
            String sep_fecha = (String)jsonObject.get("sep_fecha");
            String sep_item_cant = (String)jsonObject.get("sep_item_cant");
            String sep_item_precio = (String)jsonObject.get("sep_item_precio");
            String sep_item_subtotal = (String)jsonObject.get("sep_item_subtotal");
            String sep_total_gral = (String)jsonObject.get("sep_total_gral");
            String total_columna = (String)jsonObject.get("total_columna");
            String tt_text_descrip = (String)jsonObject.get("tt_text_descrip");
            String tt_fila_ccvg = (String)jsonObject.get("tt_fila_ccvg");
            String tt_fila_cc = (String)jsonObject.get("tt_fila_cc");
            String tt_fila_ven = (String)jsonObject.get("tt_fila_ven");
            String tt_fila_coman = (String)jsonObject.get("tt_fila_coman");
            String tt_fila_ven_ms = (String)jsonObject.get("tt_fila_ven_ms");
            String tt_fila_com_in = (String)jsonObject.get("tt_fila_com_in");
            String tt_fila_gas = (String)jsonObject.get("tt_fila_gas");
            String tt_fila_val = (String)jsonObject.get("tt_fila_val");
            String linea_ven_top_1 = (String) jsonObject.get("linea_ven_top_1");
            String linea_ven_top_2 = (String) jsonObject.get("linea_ven_top_2");
            String cant_top_venta = (String)jsonObject.get("cant_top_venta");
            String print_comanda = (String)jsonObject.get("print_comanda");
            setLinea_separador(linea_separador);
            setLinea_ven_detalle(linea_ven_detalle);
            setLinea_ven_categoria(linea_ven_categoria);
            setLinea_cabezera(linea_cabezera);
            setSalto_linea_item(Integer.parseInt(salto_linea_item));
            setSep_inicio(Integer.parseInt(sep_inicio));
            setSep_numero(Integer.parseInt(sep_numero));
            setSep_fecha(Integer.parseInt(sep_fecha));
            setSep_item_cant(Integer.parseInt(sep_item_cant));
            setSep_item_precio(Integer.parseInt(sep_item_precio));
            setSep_item_subtotal(Integer.parseInt(sep_item_subtotal));
            setSep_total_gral(Integer.parseInt(sep_total_gral));
            setTotal_columna(Integer.parseInt(total_columna));
            setTt_text_descrip(Integer.parseInt(tt_text_descrip));
            setTt_fila_ccvg(Integer.parseInt(tt_fila_ccvg));
            setTt_fila_cc(Integer.parseInt(tt_fila_cc));
            setTt_fila_ven(Integer.parseInt(tt_fila_ven));
            setTt_fila_coman(Integer.parseInt(tt_fila_coman));
            setTt_fila_ven_ms(Integer.parseInt(tt_fila_ven_ms));
            setTt_fila_com_in(Integer.parseInt(tt_fila_com_in));
            setTt_fila_gas(Integer.parseInt(tt_fila_gas));
            setTt_fila_val(Integer.parseInt(tt_fila_val));
            setLinea_ven_top_1(linea_ven_top_1);
            setLinea_ven_top_2(linea_ven_top_2);
            setCant_top_venta(Integer.parseInt(cant_top_venta));
            if(print_comanda.equals("true")){
                setPrint_comanda(true);
            }else{
                setPrint_comanda(false);
            }
            System.out.println("json imprimir pos:"+jsonObject);
        } catch (Exception ex) {
            System.err.println("Error json_imprimir_pos: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error json_imprimir_pos: " + ex.toString());
        } finally {

        }
    }

    /**
     * @return the linea_cabezera
     */
    public static String getLinea_cabezera() {
        return linea_cabezera;
    }

    /**
     * @param aLinea_cabezera the linea_cabezera to set
     */
    public static void setLinea_cabezera(String aLinea_cabezera) {
        linea_cabezera = aLinea_cabezera;
    }

    /**
     * @return the sep_fecha
     */
    public static int getSep_fecha() {
        return sep_fecha;
    }

    /**
     * @param aSep_fecha the sep_fecha to set
     */
    public static void setSep_fecha(int aSep_fecha) {
        sep_fecha = aSep_fecha;
    }

    /**
     * @return the tt_text_descrip
     */
    public static int getTt_text_descrip() {
        return tt_text_descrip;
    }

    /**
     * @param aTt_text_descrip the tt_text_descrip to set
     */
    public static void setTt_text_descrip(int aTt_text_descrip) {
        tt_text_descrip = aTt_text_descrip;
    }

    /**
     * @return the salto_linea_item
     */
    public static int getSalto_linea_item() {
        return salto_linea_item;
    }

    /**
     * @param aSalto_linea_item the salto_linea_item to set
     */
    public static void setSalto_linea_item(int aSalto_linea_item) {
        salto_linea_item = aSalto_linea_item;
    }

    /**
     * @return the linea_ven_categoria
     */
    public static String getLinea_ven_categoria() {
        return linea_ven_categoria;
    }

    /**
     * @param aLinea_ven_categoria the linea_ven_categoria to set
     */
    public static void setLinea_ven_categoria(String aLinea_ven_categoria) {
        linea_ven_categoria = aLinea_ven_categoria;
    }


}
