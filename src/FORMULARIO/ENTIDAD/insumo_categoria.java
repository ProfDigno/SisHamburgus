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
public class insumo_categoria {
    /**
     * CREATE TABLE "insumo_categoria" (
	"idinsumo_categoria" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	PRIMARY KEY("idinsumo_categoria")
);
     */
    private int idinsumo_categoria;
    private String nombre;
    private static String tabla;
    private static String idtabla;

    public insumo_categoria() {
        setTabla("insumo_categoria");
        setIdtabla("idinsumo_categoria");
    }
    
    public int getIdinsumo_categoria() {
        return idinsumo_categoria;
    }

    public void setIdinsumo_categoria(int idinsumo_categoria) {
        this.idinsumo_categoria = idinsumo_categoria;
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
        insumo_categoria.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        insumo_categoria.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "insumo_categoria{" + "idinsumo_categoria=" + idinsumo_categoria + ", nombre=" + nombre + '}';
    }
    
}
