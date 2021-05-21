	package FORMULARIO.ENTIDAD;
public class item_venta_mesa_venta {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_venta_mesa_venta;
private int C2fk_idventa;
private int C3fk_iditem_venta_mesa;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_venta_mesa_venta() {
		setTb_item_venta_mesa_venta("item_venta_mesa_venta");
		setId_iditem_venta_mesa_venta("iditem_venta_mesa_venta");
	}
	public static String getTb_item_venta_mesa_venta(){
		return nom_tabla;
	}
	public static void setTb_item_venta_mesa_venta(String nom_tabla){
		item_venta_mesa_venta.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_venta_mesa_venta(){
		return nom_idtabla;
	}
	public static void setId_iditem_venta_mesa_venta(String nom_idtabla){
		item_venta_mesa_venta.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_venta_mesa_venta(){
		return C1iditem_venta_mesa_venta;
	}
	public void setC1iditem_venta_mesa_venta(int C1iditem_venta_mesa_venta){
		this.C1iditem_venta_mesa_venta = C1iditem_venta_mesa_venta;
	}
	public int getC2fk_idventa(){
		return C2fk_idventa;
	}
	public void setC2fk_idventa(int C2fk_idventa){
		this.C2fk_idventa = C2fk_idventa;
	}
	public int getC3fk_iditem_venta_mesa(){
		return C3fk_iditem_venta_mesa;
	}
	public void setC3fk_iditem_venta_mesa(int C3fk_iditem_venta_mesa){
		this.C3fk_iditem_venta_mesa = C3fk_iditem_venta_mesa;
	}
	public String toString() {
		return "item_venta_mesa_venta(" + ",iditem_venta_mesa_venta=" + C1iditem_venta_mesa_venta + " ,fk_idventa=" + C2fk_idventa + " ,fk_iditem_venta_mesa=" + C3fk_iditem_venta_mesa + " )";
	}
}
