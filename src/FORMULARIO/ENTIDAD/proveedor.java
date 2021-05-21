	package FORMULARIO.ENTIDAD;
public class proveedor {

    /**
     * @return the proveedor_mostrar
     */
    public static String getProveedor_mostrar() {
        return proveedor_mostrar;
    }

    /**
     * @param aProveedor_mostrar the proveedor_mostrar to set
     */
    public static void setProveedor_mostrar(String aProveedor_mostrar) {
        proveedor_mostrar = aProveedor_mostrar;
    }

    /**
     * @return the proveedor_concat
     */
    public static String getProveedor_concat() {
        return proveedor_concat;
    }

    /**
     * @param aProveedor_concat the proveedor_concat to set
     */
    public static void setProveedor_concat(String aProveedor_concat) {
        proveedor_concat = aProveedor_concat;
    }

    /**
     * @return the idproveedor_static
     */
    public static int getIdproveedor_static() {
        return idproveedor_static;
    }

    /**
     * @param aIdproveedor_static the idproveedor_static to set
     */
    public static void setIdproveedor_static(int aIdproveedor_static) {
        idproveedor_static = aIdproveedor_static;
    }

//---------------DECLARAR VARIABLES---------------
private int C1idproveedor;
private String C2fecha_inicio;
private String C3nombre;
private String C4direccion;
private String C5telefono;
private String C6ruc;
private static String nom_tabla;
private static String nom_idtabla;
private static int idproveedor_static;
private static String proveedor_mostrar = "(idproveedor||'-('||nombre||')---TEL:('||telefono||')---RUC:('||ruc||')') as nombre ";
    private static String proveedor_concat = "concat(idproveedor,'-(',nombre,')---TEL:(',telefono,')---RUC:(',ruc,')')";
//---------------TABLA-ID---------------
	public proveedor() {
		setTb_proveedor("proveedor");
		setId_idproveedor("idproveedor");
	}
	public static String getTb_proveedor(){
		return nom_tabla;
	}
	public static void setTb_proveedor(String nom_tabla){
		proveedor.nom_tabla = nom_tabla;
	}
	public static String getId_idproveedor(){
		return nom_idtabla;
	}
	public static void setId_idproveedor(String nom_idtabla){
		proveedor.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproveedor(){
		return C1idproveedor;
	}
	public void setC1idproveedor(int C1idproveedor){
		this.C1idproveedor = C1idproveedor;
	}
	public String getC2fecha_inicio(){
		return C2fecha_inicio;
	}
	public void setC2fecha_inicio(String C2fecha_inicio){
		this.C2fecha_inicio = C2fecha_inicio;
	}
	public String getC3nombre(){
		return C3nombre;
	}
	public void setC3nombre(String C3nombre){
		this.C3nombre = C3nombre;
	}
	public String getC4direccion(){
		return C4direccion;
	}
	public void setC4direccion(String C4direccion){
		this.C4direccion = C4direccion;
	}
	public String getC5telefono(){
		return C5telefono;
	}
	public void setC5telefono(String C5telefono){
		this.C5telefono = C5telefono;
	}
	public String getC6ruc(){
		return C6ruc;
	}
	public void setC6ruc(String C6ruc){
		this.C6ruc = C6ruc;
	}
	public String toString() {
		return "proveedor(" + ",idproveedor=" + C1idproveedor + " ,fecha_inicio=" + C2fecha_inicio + " ,nombre=" + C3nombre + " ,direccion=" + C4direccion + " ,telefono=" + C5telefono + " ,ruc=" + C6ruc + " )";
	}
}
