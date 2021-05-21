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
public class item_compra_insumo {
    /**
     * CREATE TABLE "item_compra_insumo" (
	"iditem_compra_insumo" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"precio" NUMERIC(14,0) NOT NULL ,
	"cantidad" NUMERIC(6,2) NOT NULL ,
	"fk_idinsumo_producto" INT NOT NULL ,
	"fk_idcompra_insumo" INT NOT NULL 
);
     */
    private int iditem_compra_insumo;
    private String nombre;
    private String unidad;
    private double precio;
    private double cantidad;
    private int fk_idinsumo_producto;
    private int fk_idcompra_insumo;
    private static String tabla;
    private static String idtabla;

    public item_compra_insumo() {
        setTabla("item_compra_insumo");
        setIdtabla("iditem_compra_insumo");
    }

    public int getIditem_compra_insumo() {
        return iditem_compra_insumo;
    }

    public void setIditem_compra_insumo(int iditem_compra_insumo) {
        this.iditem_compra_insumo = iditem_compra_insumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getFk_idinsumo_producto() {
        return fk_idinsumo_producto;
    }

    public void setFk_idinsumo_producto(int fk_idinsumo_producto) {
        this.fk_idinsumo_producto = fk_idinsumo_producto;
    }

    public int getFk_idcompra_insumo() {
        return fk_idcompra_insumo;
    }

    public void setFk_idcompra_insumo(int fk_idcompra_insumo) {
        this.fk_idcompra_insumo = fk_idcompra_insumo;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        item_compra_insumo.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        item_compra_insumo.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "item_compra_insumo{" + "iditem_compra_insumo=" + iditem_compra_insumo + ", nombre=" + nombre + ", precio=" + precio + ", cantidad=" + cantidad + ", fk_idinsumo_producto=" + fk_idinsumo_producto + ", fk_idcompra_insumo=" + fk_idcompra_insumo + '}';
    }
    
}
