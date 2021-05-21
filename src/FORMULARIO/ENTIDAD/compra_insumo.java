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
public class compra_insumo {
    /**
     * CREATE TABLE "compra_insumo" (
	"idcompra_insumo" SERIAL NOT NULL ,
	"fecha_emision" TIMESTAMP NOT NULL ,
	"monto_compra" NUMERIC(14,0) NOT NULL ,
	"observacion" TEXT NOT NULL ,
	"estado" TEXT NOT NULL ,
	"tiponota" TEXT NOT NULL ,
	"fk_idusuario" INT NOT NULL ,
	PRIMARY KEY("idcompra_insumo")
);
     */
    private int idcompra_insumo;
    private String fecha_emision;
    private double monto_compra;
    private String observacion;
    private String estado;
    private String tiponota;
    private String indice;
    private int fk_idusuario;
    private static String tabla;
    private static String idtabla;

    public compra_insumo() {
        setTabla("compra_insumo");
        setIdtabla("idcompra_insumo");
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public int getIdcompra_insumo() {
        return idcompra_insumo;
    }

    public void setIdcompra_insumo(int idcompra_insumo) {
        this.idcompra_insumo = idcompra_insumo;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public double getMonto_compra() {
        return monto_compra;
    }

    public void setMonto_compra(double monto_compra) {
        this.monto_compra = monto_compra;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTiponota() {
        return tiponota;
    }

    public void setTiponota(String tiponota) {
        this.tiponota = tiponota;
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
        compra_insumo.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        compra_insumo.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "compra_insumo{" + "idcompra_insumo=" + idcompra_insumo + ", fecha_emision=" + fecha_emision + ", monto_compra=" + monto_compra + ", observacion=" + observacion + ", estado=" + estado + ", tiponota=" + tiponota + ", indice=" + indice + ", fk_idusuario=" + fk_idusuario + '}';
    }

   
    
}
