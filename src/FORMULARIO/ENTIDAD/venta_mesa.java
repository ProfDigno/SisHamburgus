	package FORMULARIO.ENTIDAD;
public class venta_mesa {

//---------------DECLARAR VARIABLES---------------
private int C1idventa_mesa;
private String C2nombre;
private String C3estado;
private double C4monto;
private boolean C5habilitar;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta_mesa() {
		setTb_venta_mesa("venta_mesa");
		setId_idventa_mesa("idventa_mesa");
	}
	public static String getTb_venta_mesa(){
		return nom_tabla;
	}
	public static void setTb_venta_mesa(String nom_tabla){
		venta_mesa.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_mesa(){
		return nom_idtabla;
	}
	public static void setId_idventa_mesa(String nom_idtabla){
		venta_mesa.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa_mesa(){
		return C1idventa_mesa;
	}
	public void setC1idventa_mesa(int C1idventa_mesa){
		this.C1idventa_mesa = C1idventa_mesa;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public String getC3estado(){
		return C3estado;
	}
	public void setC3estado(String C3estado){
		this.C3estado = C3estado;
	}
	public double getC4monto(){
		return C4monto;
	}
	public void setC4monto(double C4monto){
		this.C4monto = C4monto;
	}
	public boolean getC5habilitar(){
		return C5habilitar;
	}
	public void setC5habilitar(boolean C5habilitar){
		this.C5habilitar = C5habilitar;
	}
	public String toString() {
		return "venta_mesa(" + ",idventa_mesa=" + C1idventa_mesa + " ,nombre=" + C2nombre + " ,estado=" + C3estado + " ,monto=" + C4monto + " ,habilitar=" + C5habilitar + " )";
	}
}
