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
public class entregador {
    private int identregador;
    private String nombre;
    private boolean activar;
    private static String tabla;
    private static String idtabla;

    public entregador() {
        setTabla("entregador");
        setIdtabla("identregador");
    }

    public int getIdentregador() {
        return identregador;
    }

    public void setIdentregador(int identregador) {
        this.identregador = identregador;
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
        entregador.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        entregador.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "entregador{" + "identregador=" + identregador + ", nombre=" + nombre + ", activar=" + activar + '}';
    }
    
}
