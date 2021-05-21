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
public class cotizacion {

    /**
     * @return the Stdolar_guarani
     */
    public String getStdolar_guarani() {
        return Stdolar_guarani;
    }

    /**
     * @param Stdolar_guarani the Stdolar_guarani to set
     */
    public void setStdolar_guarani(String Stdolar_guarani) {
        this.Stdolar_guarani = Stdolar_guarani;
    }

    /**
     * @return the Streal_guarani
     */
    public String getStreal_guarani() {
        return Streal_guarani;
    }

    /**
     * @param Streal_guarani the Streal_guarani to set
     */
    public void setStreal_guarani(String Streal_guarani) {
        this.Streal_guarani = Streal_guarani;
    }
    /**
     * CREATE TABLE "cotizacion" (
	"idcotizacion" INTEGER NOT NULL ,
	"dolar_guarani" NUMERIC(14,0) NOT NULL ,
	"real_guarani" NUMERIC(14,0) NOT NULL ,
	PRIMARY KEY("idcotizacion")
);
     */
    private int idcotizacion;
    private double dolar_guarani;
    private double real_guarani;
    private String Stdolar_guarani;
    private String Streal_guarani;
    private static double dolar_guarani_STATIC;
    private static double real_guarani_STATIC;
    private static String tabla;
    private static String idtabla;

    public static double getDolar_guarani_STATIC() {
        return dolar_guarani_STATIC;
    }

    public static void setDolar_guarani_STATIC(double dolar_guarani_STATIC) {
        cotizacion.dolar_guarani_STATIC = dolar_guarani_STATIC;
    }

    public static double getReal_guarani_STATIC() {
        return real_guarani_STATIC;
    }

    public static void setReal_guarani_STATIC(double real_guarani_STATIC) {
        cotizacion.real_guarani_STATIC = real_guarani_STATIC;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        cotizacion.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        cotizacion.idtabla = idtabla;
    }
    
    public cotizacion() {
//        setDolar_guarani(5600);
//        setReal_guarani(1550);
        setTabla("cotizacion");
        setIdtabla("idcotizacion");
    }

    public int getIdcotizacion() {
        return idcotizacion;
    }

    public void setIdcotizacion(int idcotizacion) {
        this.idcotizacion = idcotizacion;
    }

    public double getDolar_guarani() {
        return dolar_guarani;
    }

    public void setDolar_guarani(double dolar_guarani) {
        this.dolar_guarani = dolar_guarani;
    }

    public double getReal_guarani() {
        return real_guarani;
    }

    public void setReal_guarani(double real_guarani) {
        this.real_guarani = real_guarani;
    }

    @Override
    public String toString() {
        return "cotizacion{" + "idcotizacion=" + idcotizacion + ", dolar_guarani=" + dolar_guarani + ", real_guarani=" + real_guarani + '}';
    }
    
}
