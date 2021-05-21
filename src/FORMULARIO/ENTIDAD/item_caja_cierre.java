/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.ENTIDAD;

/**
 *
 * @author Digno
 */
public class item_caja_cierre {

//---------------DECLARAR VARIABLES---------------
    private int C1iditem_caja_cierre;
    private int C2fk_idcaja_cierre;
    private int C3fk_idcaja_detalle;
    private static String nom_tabla;
    private static String nom_idtabla;
//---------------TABLA-ID---------------

    public item_caja_cierre() {
        setTb_item_caja_cierre("item_caja_cierre");
        setId_iditem_caja_cierre("iditem_caja_cierre");
    }

    public static String getTb_item_caja_cierre() {
        return nom_tabla;
    }

    public static void setTb_item_caja_cierre(String nom_tabla) {
        item_caja_cierre.nom_tabla = nom_tabla;
    }

    public static String getId_iditem_caja_cierre() {
        return nom_idtabla;
    }

    public static void setId_iditem_caja_cierre(String nom_idtabla) {
        item_caja_cierre.nom_idtabla = nom_idtabla;
    }
//---------------GET-SET-CAMPOS---------------

    public int getC1iditem_caja_cierre() {
        return C1iditem_caja_cierre;
    }

    public void setC1iditem_caja_cierre(int C1iditem_caja_cierre) {
        this.C1iditem_caja_cierre = C1iditem_caja_cierre;
    }

    public int getC2fk_idcaja_cierre() {
        return C2fk_idcaja_cierre;
    }

    public void setC2fk_idcaja_cierre(int C2fk_idcaja_cierre) {
        this.C2fk_idcaja_cierre = C2fk_idcaja_cierre;
    }

    public int getC3fk_idcaja_detalle() {
        return C3fk_idcaja_detalle;
    }

    public void setC3fk_idcaja_detalle(int C3fk_idcaja_detalle) {
        this.C3fk_idcaja_detalle = C3fk_idcaja_detalle;
    }

    public String toString() {
        return "item_caja_cierre(" + ",iditem_caja_cierre=" + C1iditem_caja_cierre + " ,fk_idcaja_cierre=" + C2fk_idcaja_cierre + " ,fk_idcaja_detalle=" + C3fk_idcaja_detalle + " )";
    }
}
