/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.ENTIDAD;

import java.sql.Date;

/**
 *
 * @author Digno
 */
/*

*/
public class cliente {

    /**
     * @return the c11deliveryDouble
     */
    public double getC11deliveryDouble() {
        return c11deliveryDouble;
    }

    /**
     * @param c11deliveryDouble the c11deliveryDouble to set
     */
    public void setC11deliveryDouble(double c11deliveryDouble) {
        this.c11deliveryDouble = c11deliveryDouble;
    }
    private int c1idcliente;
    private String c2fecha_inicio;
    private String c3nombre;
    private String c4direccion;
    private String c5telefono;
    private String c6ruc;
    private String c7fecha_cumple;
    private String c8tipo;
    private int c9fk_idzona_delivery;
    private String c10zona;
    private String c11delivery;
    private double c11deliveryDouble;
    private static String tabla;
    private static String idtabla;
    private static String cliente_mostrar = "(idcliente||'-('||nombre||')---TEL:('||telefono||')---RUC:('||ruc||')') as nombre ";
    private static String cliente_concat = "concat(idcliente,'-(',nombre,')---TEL:(',telefono,')---RUC:(',ruc,')')";
    
    public String getC10zona() {
        return c10zona;
    }

    public void setC10zona(String c10zona) {
        this.c10zona = c10zona;
    }

    public String getC11delivery() {
        return c11delivery;
    }

    public void setC11delivery(String c11delivery) {
        this.c11delivery = c11delivery;
    }

    public String getC2fecha_inicio() {
        return c2fecha_inicio;
    }

    public void setC2fecha_inicio(String c2fecha_inicio) {
        this.c2fecha_inicio = c2fecha_inicio;
    }

    public String getC7fecha_cumple() {
        return c7fecha_cumple;
    }

    public void setC7fecha_cumple(String c7fecha_cumple) {
        this.c7fecha_cumple = c7fecha_cumple;
    }

    public cliente() {
        setTabla("cliente");
        setIdtabla("idcliente");
    }

    public int getC1idcliente() {
        return c1idcliente;
    }

    public void setC1idcliente(int c1idcliente) {
        this.c1idcliente = c1idcliente;
    }


    public String getC3nombre() {
        return c3nombre;
    }

    public void setC3nombre(String c3nombre) {
        this.c3nombre = c3nombre;
    }

    public String getC4direccion() {
        return c4direccion;
    }

    public void setC4direccion(String c4direccion) {
        this.c4direccion = c4direccion;
    }

    public String getC5telefono() {
        return c5telefono;
    }

    public void setC5telefono(String c5telefono) {
        this.c5telefono = c5telefono;
    }

    public String getC6ruc() {
        return c6ruc;
    }

    public void setC6ruc(String c6ruc) {
        this.c6ruc = c6ruc;
    }


    public String getC8tipo() {
        return c8tipo;
    }

    public void setC8tipo(String c8tipo) {
        this.c8tipo = c8tipo;
    }

    public int getC9fk_idzona_delivery() {
        return c9fk_idzona_delivery;
    }

    public void setC9fk_idzona_delivery(int c9fk_idzona_delivery) {
        this.c9fk_idzona_delivery = c9fk_idzona_delivery;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        cliente.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        cliente.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "cliente{" + "c1idcliente=" + c1idcliente + ", c2fecha_inicio=" + c2fecha_inicio + ", c3nombre=" + c3nombre + ", c4direccion=" + c4direccion + ", c5telefono=" + c5telefono + ", c6ruc=" + c6ruc + ", c7fecha_cumple=" + c7fecha_cumple + ", c8tipo=" + c8tipo + ", c9fk_idzona_delivery=" + c9fk_idzona_delivery + '}';
    }

    /**
     * @return the cliente_mostrar
     */
    public static String getCliente_mostrar() {
        return cliente_mostrar;
    }

    /**
     * @return the cliente_concat
     */
    public static String getCliente_concat() {
        return cliente_concat;
    }
    
}
