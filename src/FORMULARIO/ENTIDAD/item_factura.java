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
public class item_factura {
    /**
     * CREATE TABLE "item_factura" (
	"iditem_factura" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"precio" NUMERIC(14,0) NOT NULL ,
	"cantidad" NUMERIC(10,0) NOT NULL ,
	"fk_idfactura" INT NOT NULL ,
	"fk_idproducto" INT NOT NULL ,
	PRIMARY KEY("iditem_factura")
);
     */
    private int c1iditem_factura;
    private String c2nombre;
    private double c3precio;
    private double c4cantidad;
    private int c5fk_idfactura;
    private int c6fk_idproducto;
    private static String tabla;
    private static String idtabla;

    public item_factura() {
        setTabla("item_factura");
        setIdtabla("iditem_factura");
    }

    public int getC1iditem_factura() {
        return c1iditem_factura;
    }

    public void setC1iditem_factura(int c1iditem_factura) {
        this.c1iditem_factura = c1iditem_factura;
    }

    public String getC2nombre() {
        return c2nombre;
    }

    public void setC2nombre(String c2nombre) {
        this.c2nombre = c2nombre;
    }

    public double getC3precio() {
        return c3precio;
    }

    public void setC3precio(double c3precio) {
        this.c3precio = c3precio;
    }

    public double getC4cantidad() {
        return c4cantidad;
    }

    public void setC4cantidad(double c4cantidad) {
        this.c4cantidad = c4cantidad;
    }

    public int getC5fk_idfactura() {
        return c5fk_idfactura;
    }

    public void setC5fk_idfactura(int c5fk_idfactura) {
        this.c5fk_idfactura = c5fk_idfactura;
    }

    public int getC6fk_idproducto() {
        return c6fk_idproducto;
    }

    public void setC6fk_idproducto(int c6fk_idproducto) {
        this.c6fk_idproducto = c6fk_idproducto;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        item_factura.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        item_factura.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "item_factura{" + "c1iditem_factura=" + c1iditem_factura + ", c2nombre=" + c2nombre + ", c3precio=" + c3precio + ", c4cantidad=" + c4cantidad + ", c5fk_idfactura=" + c5fk_idfactura + ", c6fk_idproducto=" + c6fk_idproducto + '}';
    }
    
}
