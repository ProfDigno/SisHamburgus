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
public class caja_detalle {
    private int c1idcaja_detalle;
    private String c2fecha_emision;
    private String c3descripcion;
    private double c4monto_venta;
    private double c5monto_delivery;
    private double c6monto_gasto;
    private double c7monto_compra;
    private double c8monto_vale;
    private int c9id_origen;
    private String c10tabla_origen;
    private int c11fk_idusuario;
    private String c12indice;
    private String c13equipo;
    private String c14cierre;
    private double c15monto_caja;
    private double c16monto_cierre;
    private static String tabla;
    private static String idtabla;

    public caja_detalle() {
        setTabla("caja_detalle");
        setIdtabla("idcaja_detalle");
    }

    public String getC12indice() {
        return c12indice;
    }

    public void setC12indice(String c12indice) {
        this.c12indice = c12indice;
    }

    public String getC13equipo() {
        return c13equipo;
    }

    public void setC13equipo1(String c13equipo) {
        this.c13equipo = c13equipo;
    }

    public int getC1idcaja_detalle() {
        return c1idcaja_detalle;
    }

    public void setC1idcaja_detalle(int c1idcaja_detalle) {
        this.c1idcaja_detalle = c1idcaja_detalle;
    }

    public String getC2fecha_emision() {
        return c2fecha_emision;
    }

    public void setC2fecha_emision(String c2fecha_emision) {
        this.c2fecha_emision = c2fecha_emision;
    }

    public String getC3descripcion() {
        return c3descripcion;
    }

    public void setC3descripcion(String c3descripcion) {
        this.c3descripcion = c3descripcion;
    }

    public double getC4monto_venta() {
        return c4monto_venta;
    }

    public void setC4monto_venta(double c4monto_venta) {
        this.c4monto_venta = c4monto_venta;
    }

    public double getC5monto_delivery() {
        return c5monto_delivery;
    }

    public void setC5monto_delivery(double c5monto_delivery) {
        this.c5monto_delivery = c5monto_delivery;
    }

    public double getC6monto_gasto() {
        return c6monto_gasto;
    }

    public void setC6monto_gasto(double c6monto_gasto) {
        this.c6monto_gasto = c6monto_gasto;
    }

    public double getC7monto_compra() {
        return c7monto_compra;
    }

    public void setC7monto_compra(double c7monto_compra) {
        this.c7monto_compra = c7monto_compra;
    }

    public double getC8monto_vale() {
        return c8monto_vale;
    }

    public void setC8monto_vale(double c8monto_vale) {
        this.c8monto_vale = c8monto_vale;
    }

    public int getC9id_origen() {
        return c9id_origen;
    }

    public void setC9id_origen(int c9id_origen) {
        this.c9id_origen = c9id_origen;
    }

    public String getC10tabla_origen() {
        return c10tabla_origen;
    }

    public void setC10tabla_origen(String c10tabla_origen) {
        this.c10tabla_origen = c10tabla_origen;
    }

    public int getC11fk_idusuario() {
        return c11fk_idusuario;
    }

    public void setC11fk_idusuario(int c11fk_idusuario) {
        this.c11fk_idusuario = c11fk_idusuario;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        caja_detalle.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        caja_detalle.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "caja_detalle{" + "c1idcaja_detalle=" + c1idcaja_detalle + ", c2fecha_emision=" + c2fecha_emision + ", c3descripcion=" + c3descripcion + ", c4monto_venta=" + c4monto_venta + ", c5monto_delivery=" + c5monto_delivery + ", c6monto_gasto=" + c6monto_gasto + ", c7monto_compra=" + c7monto_compra + ", c8monto_vale=" + c8monto_vale + ", c9id_origen=" + c9id_origen + ", c10tabla_origen=" + c10tabla_origen + ", c11fk_idusuario=" + c11fk_idusuario + ", c12indice=" + c12indice + ", c13equipo=" + c13equipo + ", c14cierre=" + c14cierre + ", c15monto_caja=" + c15monto_caja + ", c16monto_cierre=" + c16monto_cierre + '}';
    }

    

    /**
     * @return the c14cierre
     */
    public String getC14cierre() {
        return c14cierre;
    }

    /**
     * @param c14cierre the c14cierre to set
     */
    public void setC14cierre(String c14cierre) {
        this.c14cierre = c14cierre;
    }

    /**
     * @return the c15monto_caja
     */
    public double getC15monto_caja() {
        return c15monto_caja;
    }

    /**
     * @param c15monto_caja the c15monto_caja to set
     */
    public void setC15monto_caja1(double c15monto_caja) {
        this.c15monto_caja = c15monto_caja;
    }

    /**
     * @return the c16monto_cierre
     */
    public double getC16monto_cierre() {
        return c16monto_cierre;
    }

    /**
     * @param c16monto_cierre the c16monto_cierre to set
     */
    public void setC16monto_cierre(double c16monto_cierre) {
        this.c16monto_cierre = c16monto_cierre;
    }

    
    
}
