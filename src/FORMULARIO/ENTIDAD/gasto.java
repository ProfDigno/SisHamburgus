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
public class gasto {
    /**
     * CREATE TABLE "gasto" (
	"idgasto" SERIAL NOT NULL ,
	"fecha_emision" TIMESTAMP NOT NULL ,
	"descripcion" TEXT NOT NULL ,
	"monto_gasto" NUMERIC(14,0) NOT NULL ,
	"equipo" TEXT NOT NULL ,
	"fk_idgasto_tipo" INT NOT NULL ,
	"fk_idusuario" INT NOT NULL ,
	PRIMARY KEY("idgasto")
);
     */
    private int idgasto;
    private String fecha_emision;
    private String descripcion;
    private double monto_gasto;
    private String equipo;
    private String estado;
    private int fk_idgasto_tipo;
    private int fk_idusuario;
    private String indice;
    private static String tabla;
    private static String idtabla;
    private static String gasto_tipo;

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }
    
    public static String getGasto_tipo() {
        return gasto_tipo;
    }

    public static void setGasto_tipo(String gasto_tipo) {
        gasto.gasto_tipo = gasto_tipo;
    }
    
    public gasto() {
        setTabla("gasto");
        setIdtabla("idgasto");
    }
    
    public int getIdgasto() {
        return idgasto;
    }

    public void setIdgasto(int idgasto) {
        this.idgasto = idgasto;
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

    public double getMonto_gasto() {
        return monto_gasto;
    }

    public void setMonto_gasto(double monto_gasto) {
        this.monto_gasto = monto_gasto;
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
    
    public int getFk_idgasto_tipo() {
        return fk_idgasto_tipo;
    }

    public void setFk_idgasto_tipo(int fk_idgasto_tipo) {
        this.fk_idgasto_tipo = fk_idgasto_tipo;
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
        gasto.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        gasto.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "gasto{" + "idgasto=" + idgasto + ", fecha_emision=" + fecha_emision + ", descripcion=" + descripcion + ", monto_gasto=" + monto_gasto + ", equipo=" + equipo + ", estado=" + estado + ", fk_idgasto_tipo=" + fk_idgasto_tipo + ", fk_idusuario=" + fk_idusuario + '}';
    }

    
    
}
