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
public class insumo_unidad {
    /**
     * CREATE TABLE "insumo_unidad" (
	"idinsumo_unidad" SERIAL NOT NULL ,
	"nom_compra" TEXT NOT NULL ,
	"nom_venta" TEXT NOT NULL ,
	"conversion_unidad" NUMERIC(10,2) NOT NULL ,
	PRIMARY KEY("idinsumo_unidad")
);
     */
    private int idinsumo_unidad;
    private String nom_compra;
    private String nom_venta;
    private double conversion_unidad;
    private static String tabla;
    private static String idtabla;

    public insumo_unidad() {
        setTabla("insumo_unidad");
        setIdtabla("idinsumo_unidad");
    }
    
    public int getIdinsumo_unidad() {
        return idinsumo_unidad;
    }

    public void setIdinsumo_unidad(int idinsumo_unidad) {
        this.idinsumo_unidad = idinsumo_unidad;
    }

    public String getNom_compra() {
        return nom_compra;
    }

    public void setNom_compra(String nom_compra) {
        this.nom_compra = nom_compra;
    }

    public String getNom_venta() {
        return nom_venta;
    }

    public void setNom_venta(String nom_venta) {
        this.nom_venta = nom_venta;
    }

    public double getConversion_unidad() {
        return conversion_unidad;
    }

    public void setConversion_unidad(double conversion_unidad) {
        this.conversion_unidad = conversion_unidad;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        insumo_unidad.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        insumo_unidad.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "insumo_unidad{" + "idinsumo_unidad=" + idinsumo_unidad + ", nom_compra=" + nom_compra + ", nom_venta=" + nom_venta + ", conversion_unidad=" + conversion_unidad + '}';
    }
    
}
