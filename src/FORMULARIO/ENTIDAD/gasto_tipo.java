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
public class gasto_tipo {
    /**
     * CREATE TABLE "gasto_tipo" (
	"idgasto_tipo" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"activar" BOOLEAN NOT NULL ,
	PRIMARY KEY("idgasto_tipo")
);
     */
    private int idgasto_tipo;
    private String nombre;
    private boolean activar;
    private static String tabla;
    private static String idtabla;

    public gasto_tipo() {
        setTabla("gasto_tipo");
        setIdtabla("idgasto_tipo");
    }
    
    public int getIdgasto_tipo() {
        return idgasto_tipo;
    }

    public void setIdgasto_tipo(int idgasto_tipo) {
        this.idgasto_tipo = idgasto_tipo;
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
        gasto_tipo.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        gasto_tipo.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "gasto_tipo{" + "idgasto_tipo=" + idgasto_tipo + ", nombre=" + nombre + ", activar=" + activar + '}';
    }
    
    
}
