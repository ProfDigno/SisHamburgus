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
public class producto_unidad {
    private int idproducto_unidad;
    private String nombre;
    private static String tabla;
    private static String idtabla;
    private static String nombretabla;

    public static String getNombretabla() {
        return nombretabla;
    }

    public static void setNombretabla(String nombretabla) {
        producto_unidad.nombretabla = nombretabla;
    }
    
    public producto_unidad() {
        setTabla("producto_unidad");
        setIdtabla("idproducto_unidad");
        setNombretabla("nombre");
    }

    public int getIdproducto_unidad() {
        return idproducto_unidad;
    }

    public void setIdproducto_unidad(int idproducto_unidad) {
        this.idproducto_unidad = idproducto_unidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        producto_unidad.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        producto_unidad.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "producto_unidad{" + "idproducto_unidad=" + idproducto_unidad + ", nombre=" + nombre + '}';
    }
    
}
