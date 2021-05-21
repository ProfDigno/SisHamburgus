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
public class vale {
    private int idvale;
    private String fecha_emision;
    private String descripcion;
    private double monto_vale;
    private String equipo;
    private String estado;
    private int fk_idcliente;
    private int fk_idusuario;
    private String indice;
    private static String tabla;
    private static String idtabla;
    private static String cliente;

    public vale() {
        setTabla("vale");
        setIdtabla("idvale");
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }
    
    public int getIdvale() {
        return idvale;
    }

    public void setIdvale(int idvale) {
        this.idvale = idvale;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto_vale() {
        return monto_vale;
    }

    public void setMonto_vale(double monto_vale) {
        this.monto_vale = monto_vale;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getFk_idcliente() {
        return fk_idcliente;
    }

    public void setFk_idcliente(int fk_idcliente) {
        this.fk_idcliente = fk_idcliente;
    }

    public int getFk_idusuario() {
        return fk_idusuario;
    }

    public void setFk_idusuario(int fk_idusuario) {
        this.fk_idusuario = fk_idusuario;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        vale.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        vale.idtabla = idtabla;
    }

    public static String getCliente() {
        return cliente;
    }

    public static void setCliente(String cliente) {
        vale.cliente = cliente;
    }

    @Override
    public String toString() {
        return "vale{" + "idvale=" + idvale + ", fecha_emision=" + fecha_emision + ", descripcion=" + descripcion + ", monto_vale=" + monto_vale + ", equipo=" + equipo + ", estado=" + estado + ", fk_idcliente=" + fk_idcliente + ", fk_idusuario=" + fk_idusuario + '}';
    }
    
}
