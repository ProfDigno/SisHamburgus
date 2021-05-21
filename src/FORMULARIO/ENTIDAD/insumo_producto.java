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
public class insumo_producto {

    /**
     * @return the codbarra
     */
    public int getCodbarra() {
        return codbarra;
    }

    /**
     * @param codbarra the codbarra to set
     */
    public void setCodbarra(int codbarra) {
        this.codbarra = codbarra;
    }

    public static String getActivar_select() {
        return activar_select;
    }
    public static void setActivar_select(String aActivar_select) {
        activar_select = aActivar_select;
    }
    private int idinsumo_producto;
    private String nombre;
    private double merma;
    private double precio;
    private double stock;
    private int fk_idinsumo_categoria;
    private int fk_idinsumo_unidad;
    private boolean activar;
    private int codbarra;
    private static String activar_select;
    private String nom_categoria;
    private String nom_unidad_compra;
    private String nom_conversion_unidad;
    private String nom_unidad_venta;
    private double conversion_unidad;
    private static String tabla;
    private static String idtabla;

    public insumo_producto() {
        setTabla("insumo_producto");
        setIdtabla("idinsumo_producto");
    }

    public double getConversion_unidad() {
        return conversion_unidad;
    }

    public void setConversion_unidad(double conversion_unidad) {
        this.conversion_unidad = conversion_unidad;
    }
    
    public String getNom_unidad_venta() {
        return nom_unidad_venta;
    }

    public void setNom_unidad_venta(String nom_unidad_venta) {
        this.nom_unidad_venta = nom_unidad_venta;
    }
    
    public int getIdinsumo_producto() {
        return idinsumo_producto;
    }

    public void setIdinsumo_producto(int idinsumo_producto) {
        this.idinsumo_producto = idinsumo_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMerma() {
        return merma;
    }

    public void setMerma(double merma) {
        this.merma = merma;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public int getFk_idinsumo_categoria() {
        return fk_idinsumo_categoria;
    }

    public void setFk_idinsumo_categoria(int fk_idinsumo_categoria) {
        this.fk_idinsumo_categoria = fk_idinsumo_categoria;
    }

    public int getFk_idinsumo_unidad() {
        return fk_idinsumo_unidad;
    }

    public void setFk_idinsumo_unidad(int fk_idinsumo_unidad) {
        this.fk_idinsumo_unidad = fk_idinsumo_unidad;
    }
    public boolean isActivar() {
        return activar;
    }
    public void setActivar(boolean activar) {
        this.activar = activar;
    }
    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public String getNom_unidad_compra() {
        return nom_unidad_compra;
    }

    public void setNom_unidad_compra(String nom_unidad) {
        this.nom_unidad_compra = nom_unidad;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        insumo_producto.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        insumo_producto.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "insumo_producto{" + "idinsumo_producto=" + idinsumo_producto + ", nombre=" + nombre + ", merma=" + merma + ", precio=" + precio + ", stock=" + stock + ", fk_idinsumo_categoria=" + fk_idinsumo_categoria + ", fk_idinsumo_unidad=" + fk_idinsumo_unidad + ", nom_categoria=" + nom_categoria + ", nom_unidad=" + nom_unidad_compra + '}';
    }

    /**
     * @return the nom_conversion_unidad
     */
    public String getNom_conversion_unidad() {
        return nom_conversion_unidad;
    }

    /**
     * @param nom_conversion_unidad the nom_conversion_unidad to set
     */
    public void setNom_conversion_unidad(String nom_conversion_unidad) {
        this.nom_conversion_unidad = nom_conversion_unidad;
    }
    
    
}
