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
public class zona_delivery {

    /**
     * @return the zona_concat
     */
    public static String getZona_concat() {
        return zona_concat;
    }

    /**
     * @return the zona_mostrar
     */
    public static String getZona_mostrar() {
        return zona_mostrar;
    }
    private int idzona_delivery;
    private String nombre;
    private double delivery;
    private static String tabla;
    private static String idtabla;
    private static String nombretabla;
    private static String zona_mostrar="(nombre||'->'||delivery) as mostrar";
    private static String zona_concat="concat(nombre,'->',delivery)";
    public static String getNombretabla() {
        return nombretabla;
    }

    public static void setNombretabla(String nombretabla) {
        zona_delivery.nombretabla = nombretabla;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        zona_delivery.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        zona_delivery.idtabla = idtabla;
    }
    
    public zona_delivery() {
        setTabla("zona_delivery");
        setIdtabla("idzona_delivery");
        setNombretabla("nombre");
    }

    public zona_delivery(int idzona_delivery, String nombre, double delivery) {
        this.idzona_delivery = idzona_delivery;
        this.nombre = nombre;
        this.delivery = delivery;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    
    public int getIdzona_delivery() {
        return idzona_delivery;
    }

    public void setIdzona_delivery(int idzona_delivery) {
        this.idzona_delivery = idzona_delivery;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "zona_delivery{" + "idzona_delivery=" + idzona_delivery + ", nombre=" + nombre + ", delivery=" + delivery + '}';
    }
    
}
