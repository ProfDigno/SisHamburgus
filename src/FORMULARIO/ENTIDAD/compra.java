package FORMULARIO.ENTIDAD;

public class compra {

//---------------DECLARAR VARIABLES---------------
    private int C1idcompra;
    private String C2fecha_emision;
    private String C3estado;
    private String C4observacion;
    private String C5forma_pago;
    private double C6monto_compra;
    private int C7fk_idproveedor;
    private int C8fk_idusuario;
    private String C9indice;
    private static String nom_tabla;
    private static String nom_idtabla;
//---------------TABLA-ID---------------

    public compra() {
        setTb_compra("compra");
        setId_idcompra("idcompra");
    }

    public static String getTb_compra() {
        return nom_tabla;
    }

    public static void setTb_compra(String nom_tabla) {
        compra.nom_tabla = nom_tabla;
    }

    public static String getId_idcompra() {
        return nom_idtabla;
    }

    public static void setId_idcompra(String nom_idtabla) {
        compra.nom_idtabla = nom_idtabla;
    }
//---------------GET-SET-CAMPOS---------------

    public int getC1idcompra() {
        return C1idcompra;
    }

    public void setC1idcompra(int C1idcompra) {
        this.C1idcompra = C1idcompra;
    }

    public String getC2fecha_emision() {
        return C2fecha_emision;
    }

    public void setC2fecha_emision(String C2fecha_emision) {
        this.C2fecha_emision = C2fecha_emision;
    }

    public String getC3estado() {
        return C3estado;
    }

    public void setC3estado(String C3estado) {
        this.C3estado = C3estado;
    }

    public String getC4observacion() {
        return C4observacion;
    }

    public void setC4observacion(String C4observacion) {
        this.C4observacion = C4observacion;
    }

    public String getC5forma_pago() {
        return C5forma_pago;
    }

    public void setC5forma_pago(String C5forma_pago) {
        this.C5forma_pago = C5forma_pago;
    }

    public double getC6monto_compra() {
        return C6monto_compra;
    }

    public void setC6monto_compra(double C6monto_compra) {
        this.C6monto_compra = C6monto_compra;
    }

    public int getC7fk_idproveedor() {
        return C7fk_idproveedor;
    }

    public void setC7fk_idproveedor(int C7fk_idproveedor) {
        this.C7fk_idproveedor = C7fk_idproveedor;
    }

    public int getC8fk_idusuario() {
        return C8fk_idusuario;
    }

    public void setC8fk_idusuario(int C8fk_idusuario) {
        this.C8fk_idusuario = C8fk_idusuario;
    }

    public String toString() {
        return "compra(" + ",idcompra=" + C1idcompra + " ,fecha_emision=" + C2fecha_emision + " ,estado=" + C3estado + " ,observacion=" + C4observacion + " ,forma_pago=" + C5forma_pago + " ,monto_compra=" + C6monto_compra + " ,fk_idproveedor=" + C7fk_idproveedor + " ,fk_idusuario=" + C8fk_idusuario + " )";
    }

    /**
     * @return the C9indice
     */
    public String getC9indice() {
        return C9indice;
    }

    /**
     * @param C9indice the C9indice to set
     */
    public void setC9indice(String C9indice) {
        this.C9indice = C9indice;
    }
}
