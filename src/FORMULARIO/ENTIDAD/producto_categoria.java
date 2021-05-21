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
public class producto_categoria {
    private int idproducto_categoria;
    private String nombre;
    private boolean activar;
    private int orden;
    private static String tabla;
    private static String idtabla;
    private static String nombretabla;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    public static String getNombretabla() {
        return nombretabla;
    }

    public static void setNombretabla(String nombretabla) {
        producto_categoria.nombretabla = nombretabla;
    }

    public producto_categoria() {
        setTabla("producto_categoria");
        setIdtabla("idproducto_categoria");
        setNombretabla("nombre");
    }

    public int getIdproducto_categoria() {
        return idproducto_categoria;
    }

    public void setIdproducto_categoria(int idproducto_categoria) {
        this.idproducto_categoria = idproducto_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivar() {
        return activar;
    }

    public void setActivar(boolean activar) {
        this.activar = activar;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        producto_categoria.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        producto_categoria.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "producto_categoria{" + "idproducto_categoria=" + idproducto_categoria + ", nombre=" + nombre + ", activar=" + activar + '}';
    }
    
}
