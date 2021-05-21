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
public class producto_ingrediente {
    /*
    CREATE TABLE "producto_ingrediente" (
	"idproducto_ingrediente" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"precio_venta" NUMERIC(14,0) NOT NULL ,
	PRIMARY KEY("idproducto_ingrediente")
);
    */
    private int idproducto_ingrediente;
    private String nombre;
    private double precio_venta;
    private static String tabla;
    private static String idtabla;
    private static String nombreTabla;

    public static String getNombreTabla() {
        return nombreTabla;
    }

    public static void setNombreTabla(String nombreTabla) {
        producto_ingrediente.nombreTabla = nombreTabla;
    }
    
    public producto_ingrediente() {
        setTabla("producto_ingrediente");
        setIdtabla("idproducto_ingrediente");
        setNombreTabla("nombre");
    }

    public int getIdproducto_ingrediente() {
        return idproducto_ingrediente;
    }

    public void setIdproducto_ingrediente(int idproducto_ingrediente) {
        this.idproducto_ingrediente = idproducto_ingrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        producto_ingrediente.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        producto_ingrediente.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "producto_ingrediente{" + "idproducto_ingrediente=" + idproducto_ingrediente + ", nombre=" + nombre + ", precio_venta=" + precio_venta + '}';
    }
    
}
