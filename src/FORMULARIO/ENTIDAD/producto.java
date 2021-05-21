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
public class producto {

    /**
     * @return the p14fk_idproducto_grupo
     */
    public int getP14fk_idproducto_grupo() {
        return p14fk_idproducto_grupo;
    }

    /**
     * @param p14fk_idproducto_grupo the p14fk_idproducto_grupo to set
     */
    public void setP14fk_idproducto_grupo(int p14fk_idproducto_grupo) {
        this.p14fk_idproducto_grupo = p14fk_idproducto_grupo;
    }
    /**
     * CREATE TABLE "producto" (
	"idproducto" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"precio_venta" NUMERIC(14,0) NOT NULL ,
	"precio_compra" NUMERIC(14,0) NOT NULL ,
	"stock" NUMERIC(10,0) NOT NULL ,
	"orden" INTEGER NOT NULL ,
	"activar" BOOLEAN NOT NULL ,
	"cocina" BOOLEAN NOT NULL ,
	"descontar_stock" BOOLEAN NOT NULL ,
	"comprar" BOOLEAN NOT NULL ,
	"vender" BOOLEAN NOT NULL ,
	"fk_idproducto_categoria" INT NOT NULL ,
	"fk_idproducto_unidad" INT NOT NULL ,
	PRIMARY KEY("idproducto")
);
     */
    private int p1idproducto;
    private static int p1idproducto_select;
    private String p2nombre;
    private double p3precio_venta;
    private double p4precio_compra;
    private double p5stock;
    private int p6orden;
    private boolean p7activar;
    private boolean p8cocina;
    private boolean p9descontar_stock;
    private boolean p10comprar;
    private boolean p11vender;
    private int p12fk_idproducto_categoria;
    private int p13fk_idproducto_unidad;
    private String p12categoria;
    private String p13unidad;
    private int p14fk_idproducto_grupo;
    private double p15cantidad;
    private static String tabla;
    private static String idtabla;
    
    public producto() {
        setTabla("producto");
        setIdtabla("idproducto");
    }

    public static int getP1idproducto_select() {
        return p1idproducto_select;
    }

    public static void setP1idproducto_select(int p1idproducto_select) {
        producto.p1idproducto_select = p1idproducto_select;
    }
    
    public int getP1idproducto() {
        return p1idproducto;
    }

    public void setP1idproducto(int p1idproducto) {
        this.p1idproducto = p1idproducto;
    }

    public String getP2nombre() {
        return p2nombre;
    }

    public void setP2nombre(String p2nombre) {
        this.p2nombre = p2nombre;
    }

    public double getP3precio_venta() {
        return p3precio_venta;
    }

    public void setP3precio_venta(double p3precio_venta) {
        this.p3precio_venta = p3precio_venta;
    }

    public double getP4precio_compra() {
        return p4precio_compra;
    }

    public void setP4precio_compra(double p4precio_compra) {
        this.p4precio_compra = p4precio_compra;
    }

    public double getP5stock() {
        return p5stock;
    }

    public void setP5stock(double p5stock) {
        this.p5stock = p5stock;
    }

    public int getP6orden() {
        return p6orden;
    }

    public void setP6orden(int p6orden) {
        this.p6orden = p6orden;
    }

    public boolean isP7activar() {
        return p7activar;
    }

    public void setP7activar(boolean p7activar) {
        this.p7activar = p7activar;
    }

    public boolean isP8cocina() {
        return p8cocina;
    }

    public void setP8cocina(boolean p8cocina) {
        this.p8cocina = p8cocina;
    }

    public boolean isP9descontar_stock() {
        return p9descontar_stock;
    }

    public void setP9descontar_stock(boolean p9descontar_stock) {
        this.p9descontar_stock = p9descontar_stock;
    }

    public boolean isP10comprar() {
        return p10comprar;
    }

    public void setP10comprar(boolean p10comprar) {
        this.p10comprar = p10comprar;
    }

    public boolean isP11vender() {
        return p11vender;
    }

    public void setP11vender(boolean p11vender) {
        this.p11vender = p11vender;
    }

    public int getP12fk_idproducto_categoria() {
        return p12fk_idproducto_categoria;
    }

    public void setP12fk_idproducto_categoria(int p12fk_idproducto_categoria) {
        this.p12fk_idproducto_categoria = p12fk_idproducto_categoria;
    }

    public int getP13fk_idproducto_unidad() {
        return p13fk_idproducto_unidad;
    }

    public void setP13fk_idproducto_unidad(int p13fk_idproducto_unidad) {
        this.p13fk_idproducto_unidad = p13fk_idproducto_unidad;
    }

    public String getP12categoria() {
        return p12categoria;
    }

    public void setP12categoria(String p12categoria) {
        this.p12categoria = p12categoria;
    }

    public String getP13unidad() {
        return p13unidad;
    }

    public void setP13unidad(String p13unidad) {
        this.p13unidad = p13unidad;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        producto.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        producto.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "producto{" + "p1idproducto=" + p1idproducto + ", p2nombre=" + p2nombre + ", p3precio_venta=" + p3precio_venta + ", p4precio_compra=" + p4precio_compra + ", p5stock=" + p5stock + ", p6orden=" + p6orden + ", p7activar=" + p7activar + ", p8cocina=" + p8cocina + ", p9descontar_stock=" + p9descontar_stock + ", p10comprar=" + p10comprar + ", p11vender=" + p11vender + ", p12fk_idproducto_categoria=" + p12fk_idproducto_categoria + ", p13fk_idproducto_unidad=" + p13fk_idproducto_unidad + ", p12categoria=" + p12categoria + ", p13unidad=" + p13unidad + '}';
    }

    /**
     * @return the p15cantidad
     */
    public double getP15cantidad() {
        return p15cantidad;
    }

    /**
     * @param p15cantidad the p15cantidad to set
     */
    public void setP15cantidad(double p15cantidad) {
        this.p15cantidad = p15cantidad;
    }
    
}
