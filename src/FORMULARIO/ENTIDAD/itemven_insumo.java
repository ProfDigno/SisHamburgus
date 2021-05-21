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
public class itemven_insumo {
    /**
     * CREATE TABLE "itemven_insumo" (
	"iditemven_insumo" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"cantidad" NUMERIC(6,2) NOT NULL ,
	"precio" NUMERIC(14,2) NOT NULL ,
	"fk_iditem_venta" INT NOT NULL ,
	"fk_idinsumo_item_producto" INT NOT NULL ,
	PRIMARY KEY("iditemven_insumo")
);
     */
    private int iditemven_insumo;
    private String nombre;
    private double cantidad;
    private double precio;
    private int fk_iditem_venta;
    private int fk_idinsumo_item_producto;
    private static String tabla;
    private static String idtabla;

    public itemven_insumo() {
        setTabla("itemven_insumo");
        setIdtabla("iditemven_insumo");
    }

    public int getIditemven_insumo() {
        return iditemven_insumo;
    }

    public void setIditemven_insumo(int iditemven_insumo) {
        this.iditemven_insumo = iditemven_insumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getFk_iditem_venta() {
        return fk_iditem_venta;
    }

    public void setFk_iditem_venta(int fk_iditem_venta) {
        this.fk_iditem_venta = fk_iditem_venta;
    }

    public int getFk_idinsumo_item_producto() {
        return fk_idinsumo_item_producto;
    }

    public void setFk_idinsumo_item_producto(int fk_idinsumo_item_producto) {
        this.fk_idinsumo_item_producto = fk_idinsumo_item_producto;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        itemven_insumo.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        itemven_insumo.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "itemven_insumo{" + "iditemven_insumo=" + iditemven_insumo + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio + ", fk_iditem_venta=" + fk_iditem_venta + ", fk_idinsumo_item_producto=" + fk_idinsumo_item_producto + '}';
    }
    
    
}
