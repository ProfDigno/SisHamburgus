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
public class insumo_item_producto {
    /**
     * CREATE TABLE "insumo_item_producto" (
	"idinsumo_item_producto" SERIAL NOT NULL ,
	"cantidad" NUMERIC(6,2) NOT NULL ,
	"precio" NUMERIC(14,2) NOT NULL ,
	"fk_idproducto" INT NOT NULL ,
	"fk_idinsumo_producto" INT NOT NULL ,
	PRIMARY KEY("idinsumo_item_producto")
);
     */
    private int idinsumo_item_producto;
    private double cantidad;
    private double precio;
    private int fk_idproducto;
    private int fk_idinsumo_producto;
    private static String tabla;
    private static String idtabla;

    public insumo_item_producto() {
        setTabla("insumo_item_producto");
        setIdtabla("idinsumo_item_producto");
    }

    public int getIdinsumo_item_producto() {
        return idinsumo_item_producto;
    }

    public void setIdinsumo_item_producto(int idinsumo_item_producto) {
        this.idinsumo_item_producto = idinsumo_item_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getFk_idproducto() {
        return fk_idproducto;
    }

    public void setFk_idproducto(int fk_idproducto) {
        this.fk_idproducto = fk_idproducto;
    }

    public int getFk_idinsumo_producto() {
        return fk_idinsumo_producto;
    }

    public void setFk_idinsumo_producto(int fk_idinsumo_producto) {
        this.fk_idinsumo_producto = fk_idinsumo_producto;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        insumo_item_producto.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        insumo_item_producto.idtabla = idtabla;
    }
    
}
