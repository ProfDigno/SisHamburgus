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
public class factura {

    /**
     * @return the factura_cargada
     */
    public static boolean isFactura_cargada() {
        return factura_cargada;
    }

    /**
     * @param aFactura_cargada the factura_cargada to set
     */
    public static void setFactura_cargada(boolean aFactura_cargada) {
        factura_cargada = aFactura_cargada;
    }
    /**
     * CREATE TABLE "factura" (
	"idfactura" SERIAL NOT NULL ,
	"nro_factura" INTEGER NOT NULL ,
	"fecha_emision" TIMESTAMP NOT NULL ,
	"estado" TEXT NOT NULL ,
	"totalmonto" NUMERIC(14,0) NOT NULL ,
	"iva" NUMERIC(14,2) NOT NULL ,
	"totalletra" TEXT NOT NULL ,
	"fk_idcliente" INT NOT NULL ,
	PRIMARY KEY("idfactura")
);
     */
    private int c1idfactura;
    private int c2nro_factura;
    private String c3fecha_emision;
    private String c4estado;
    private double c5totalmonto;
    private double c6iva;
    private String c7totalletra;
    private int c8fk_idcliente;
    private static String tabla;
    private static String idtabla;
    private static boolean factura_cargada; 

    public factura() {
        setTabla("factura");
        setIdtabla("idfactura");
    }
    
    public int getC1idfactura() {
        return c1idfactura;
    }

    public void setC1idfactura(int c1idfactura) {
        this.c1idfactura = c1idfactura;
    }

    public int getC2nro_factura() {
        return c2nro_factura;
    }

    public void setC2nro_factura(int c2nro_factura) {
        this.c2nro_factura = c2nro_factura;
    }

    public String getC3fecha_emision() {
        return c3fecha_emision;
    }

    public void setC3fecha_emision(String c3fecha_emision) {
        this.c3fecha_emision = c3fecha_emision;
    }

    public String getC4estado() {
        return c4estado;
    }

    public void setC4estado(String c4estado) {
        this.c4estado = c4estado;
    }

    public double getC5totalmonto() {
        return c5totalmonto;
    }

    public void setC5totalmonto(double c5totalmonto) {
        this.c5totalmonto = c5totalmonto;
    }

    public double getC6iva() {
        return c6iva;
    }

    public void setC6iva(double c6iva) {
        this.c6iva = c6iva;
    }

    public String getC7totalletra() {
        return c7totalletra;
    }

    public void setC7totalletra(String c7totalletra) {
        this.c7totalletra = c7totalletra;
    }

    public int getC8fk_idcliente() {
        return c8fk_idcliente;
    }

    public void setC8fk_idcliente(int c8fk_idcliente) {
        this.c8fk_idcliente = c8fk_idcliente;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        factura.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        factura.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "factura{" + "c1idfactura=" + c1idfactura + ", c2nro_factura=" + c2nro_factura + ", c3fecha_emision=" + c3fecha_emision + ", c4estado=" + c4estado + ", c5totalmonto=" + c5totalmonto + ", c6iva=" + c6iva + ", c7totalletra=" + c7totalletra + ", c8fk_idcliente=" + c8fk_idcliente + '}';
    }
    
}
