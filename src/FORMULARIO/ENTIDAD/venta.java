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
public class venta {

    /**
     * @return the c16nombre_mesa
     */
    public String getC16nombre_mesa() {
        return c16nombre_mesa;
    }

    /**
     * @param c16nombre_mesa the c16nombre_mesa to set
     */
    public void setC16nombre_mesa(String c16nombre_mesa) {
        this.c16nombre_mesa = c16nombre_mesa;
    }

    /**
     * @return the idventaGlobal
     */
    public static int getIdventaGlobal() {
        return idventaGlobal;
    }

    /**
     * @param aIdventaGlobal the idventaGlobal to set
     */
    public static void setIdventaGlobal(int aIdventaGlobal) {
        idventaGlobal = aIdventaGlobal;
    }

    /**
     * @return the monto_ventaGlobal
     */
    public static double getMonto_ventaGlobal() {
        return monto_ventaGlobal;
    }

    /**
     * @param aMonto_ventaGlobal the monto_ventaGlobal to set
     */
    public static void setMonto_ventaGlobal(double aMonto_ventaGlobal) {
        monto_ventaGlobal = aMonto_ventaGlobal;
    }


    /**
     * @return the c15indice
     */
    public String getC15indice() {
        return c15indice;
    }

    /**
     * @param c15indice the c15indice to set
     */
    public void setC15indice(String c15indice) {
        this.c15indice = c15indice;
    }

    /**
     * @return the c12fk_idcliente_estatico
     */
    public static int getC12fk_idcliente_estatico() {
        return c12fk_idcliente_estatico;
    }

    /**
     * @param aC12fk_idcliente_estatico the c12fk_idcliente_estatico to set
     */
    public static void setC12fk_idcliente_estatico(int aC12fk_idcliente_estatico) {
        c12fk_idcliente_estatico = aC12fk_idcliente_estatico;
    }
    private int c1idventa;
    private static int c1idventa_estatico;
    private String c2fecha_inicio;
    private String c3fecha_fin;
    private String c4tipo_entrega;
    private String c5estado;
    private double c6monto_venta;
    private double c7monto_delivery;
    private boolean c8delivery;
    private String c9observacion;
    private String c10comanda;
    private String c11equipo;
    private int c12fk_idcliente;
    private static int c12fk_idcliente_estatico;
    private int c13fk_idusuario;
    private int c14fk_identregador;
    private String c15indice;
    private String c16nombre_mesa;
    private String c17forma_pago;
    private static String tabla;
    private static String idtabla;
    private static String campo_cliente_comanda;
    private static boolean venta_aux;
    private static int idventaGlobal;
    private static double monto_ventaGlobal;

    public static boolean isVenta_aux() {
        return venta_aux;
    }

    public static void setVenta_aux(boolean venta_aux) {
        venta.venta_aux = venta_aux;
    }

    
    
    public venta() {
        setTabla("venta");
        setIdtabla("idventa");
    }

    public int getC1idventa() {
        return c1idventa;
    }

    public void setC1idventa(int c1idventa) {
        this.c1idventa = c1idventa;
    }

    public String getC2fecha_inicio() {
        return c2fecha_inicio;
    }

    public void setC2fecha_inicio(String c2fecha_inicio) {
        this.c2fecha_inicio = c2fecha_inicio;
    }

    public String getC3fecha_fin() {
        return c3fecha_fin;
    }

    public void setC3fecha_fin(String c3fecha_fin) {
        this.c3fecha_fin = c3fecha_fin;
    }

    public String getC4tipo_entrega() {
        return c4tipo_entrega;
    }

    public void setC4tipo_entrega(String c4tipo_entrega) {
        this.c4tipo_entrega = c4tipo_entrega;
    }

    public String getC5estado() {
        return c5estado;
    }

    public void setC5estado(String c5estado) {
        this.c5estado = c5estado;
    }

    public double getC6monto_venta() {
        return c6monto_venta;
    }

    public void setC6monto_venta(double c6monto_venta) {
        this.c6monto_venta = c6monto_venta;
    }

    public double getC7monto_delivery() {
        return c7monto_delivery;
    }

    public void setC7monto_delivery(double c7monto_delivery) {
        this.c7monto_delivery = c7monto_delivery;
    }

    public boolean isC8delivery() {
        return c8delivery;
    }

    public void setC8delivery(boolean c8delivery) {
        this.c8delivery = c8delivery;
    }

    public String getC9observacion() {
        return c9observacion;
    }

    public void setC9observacion(String c9observacion) {
        this.c9observacion = c9observacion;
    }

    public String getC10comanda() {
        return c10comanda;
    }

    public void setC10comanda(String c10comanda) {
        this.c10comanda = c10comanda;
    }

    public String getC11equipo() {
        return c11equipo;
    }

    public void setC11equipo(String c11equipo) {
        this.c11equipo = c11equipo;
    }

    public int getC12fk_idcliente() {
        return c12fk_idcliente;
    }

    public void setC12fk_idcliente(int c12fk_idcliente) {
        this.c12fk_idcliente = c12fk_idcliente;
    }

    public int getC13fk_idusuario() {
        return c13fk_idusuario;
    }

    public void setC13fk_idusuario(int c13fk_idusuario) {
        this.c13fk_idusuario = c13fk_idusuario;
    }

    public int getC14fk_identregador() {
        return c14fk_identregador;
    }

    public void setC14fk_identregador(int c14fk_identregador) {
        this.c14fk_identregador = c14fk_identregador;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        venta.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        venta.idtabla = idtabla;
    }

    /**
     * @return the c1idventa_estatico
     */
    public static int getC1idventa_estatico() {
        return c1idventa_estatico;
    }

    /**
     * @param aC1idventa_estatico the c1idventa_estatico to set
     */
    public static void setC1idventa_estatico(int aC1idventa_estatico) {
        c1idventa_estatico = aC1idventa_estatico;
    }

    /**
     * @return the campo_cliente_comanda
     */
    public static String getCampo_cliente_comanda() {
        return campo_cliente_comanda;
    }

    /**
     * @param aCampo_cliente_comanda the campo_cliente_comanda to set
     */
    public static void setCampo_cliente_comanda(String aCampo_cliente_comanda) {
        campo_cliente_comanda = aCampo_cliente_comanda;
    }

    /**
     * @return the c17forma_pago
     */
    public String getC17forma_pago() {
        return c17forma_pago;
    }

    /**
     * @param c17forma_pago the c17forma_pago to set
     */
    public void setC17forma_pago(String c17forma_pago) {
        this.c17forma_pago = c17forma_pago;
    }
     
    
    
}
