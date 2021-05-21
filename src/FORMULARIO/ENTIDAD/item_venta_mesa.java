	package FORMULARIO.ENTIDAD;
public class item_venta_mesa {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_venta_mesa;
private String C2fecha_inicio;
private String C3fecha_cierre;
private String C4estado;
private double C5totalmonto;
private int C6fk_idventa_mesa;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_venta_mesa() {
		setTb_item_venta_mesa("item_venta_mesa");
		setId_iditem_venta_mesa("iditem_venta_mesa");
	}
	public static String getTb_item_venta_mesa(){
		return nom_tabla;
	}
	public static void setTb_item_venta_mesa(String nom_tabla){
		item_venta_mesa.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_venta_mesa(){
		return nom_idtabla;
	}
	public static void setId_iditem_venta_mesa(String nom_idtabla){
		item_venta_mesa.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_venta_mesa(){
		return C1iditem_venta_mesa;
	}
	public void setC1iditem_venta_mesa(int C1iditem_venta_mesa){
		this.C1iditem_venta_mesa = C1iditem_venta_mesa;
	}
	public String getC2fecha_inicio(){
		return C2fecha_inicio;
	}
	public void setC2fecha_inicio(String C2fecha_inicio){
		this.C2fecha_inicio = C2fecha_inicio;
	}
	public String getC3fecha_cierre(){
		return C3fecha_cierre;
	}
	public void setC3fecha_cierre(String C3fecha_cierre){
		this.C3fecha_cierre = C3fecha_cierre;
	}
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public double getC5totalmonto(){
		return C5totalmonto;
	}
	public void setC5totalmonto(double C5totalmonto){
		this.C5totalmonto = C5totalmonto;
	}
	public int getC6fk_idventa_mesa(){
		return C6fk_idventa_mesa;
	}
	public void setC6fk_idventa_mesa(int C6fk_idventa_mesa){
		this.C6fk_idventa_mesa = C6fk_idventa_mesa;
	}
	public String toString() {
		return "item_venta_mesa(" + ",iditem_venta_mesa=" + C1iditem_venta_mesa + " ,fecha_inicio=" + C2fecha_inicio + " ,fecha_cierre=" + C3fecha_cierre + " ,estado=" + C4estado + " ,totalmonto=" + C5totalmonto + " ,fk_idventa_mesa=" + C6fk_idventa_mesa + " )";
	}
}
