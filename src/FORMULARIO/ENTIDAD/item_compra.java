	package FORMULARIO.ENTIDAD;
public class item_compra {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_compra;
private String C2descripcion;
private double C3precio_compra;
private double C4cantidad;
private int C5fk_idproducto;
private int C6fk_idcompra;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_compra() {
		setTb_item_compra("item_compra");
		setId_iditem_compra("iditem_compra");
	}
	public static String getTb_item_compra(){
		return nom_tabla;
	}
	public static void setTb_item_compra(String nom_tabla){
		item_compra.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_compra(){
		return nom_idtabla;
	}
	public static void setId_iditem_compra(String nom_idtabla){
		item_compra.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_compra(){
		return C1iditem_compra;
	}
	public void setC1iditem_compra(int C1iditem_compra){
		this.C1iditem_compra = C1iditem_compra;
	}
	public String getC2descripcion(){
		return C2descripcion;
	}
	public void setC2descripcion(String C2descripcion){
		this.C2descripcion = C2descripcion;
	}
	public double getC3precio_compra(){
		return C3precio_compra;
	}
	public void setC3precio_compra(double C3precio_compra){
		this.C3precio_compra = C3precio_compra;
	}
	public double getC4cantidad(){
		return C4cantidad;
	}
	public void setC4cantidad(double C4cantidad){
		this.C4cantidad = C4cantidad;
	}
	public int getC5fk_idproducto(){
		return C5fk_idproducto;
	}
	public void setC5fk_idproducto(int C5fk_idproducto){
		this.C5fk_idproducto = C5fk_idproducto;
	}
	public int getC6fk_idcompra(){
		return C6fk_idcompra;
	}
	public void setC6fk_idcompra(int C6fk_idcompra){
		this.C6fk_idcompra = C6fk_idcompra;
	}
	public String toString() {
		return "item_compra(" + ",iditem_compra=" + C1iditem_compra + " ,descripcion=" + C2descripcion + " ,precio_compra=" + C3precio_compra + " ,cantidad=" + C4cantidad + " ,fk_idproducto=" + C5fk_idproducto + " ,fk_idcompra=" + C6fk_idcompra + " )";
	}
}
