	package FORMULARIO.ENTIDAD;
public class producto_grupo {

    /**
     * @return the nom_grupo_1
     */
    public static String getNom_grupo_1() {
        return nom_grupo_1;
    }

    /**
     * @param aNom_grupo_1 the nom_grupo_1 to set
     */
    public static void setNom_grupo_1(String aNom_grupo_1) {
        nom_grupo_1 = aNom_grupo_1;
    }

    /**
     * @return the nom_grupo_0
     */
    public static String getNom_grupo_0() {
        return nom_grupo_0;
    }

    /**
     * @param aNom_grupo_0 the nom_grupo_0 to set
     */
    public static void setNom_grupo_0(String aNom_grupo_0) {
        nom_grupo_0 = aNom_grupo_0;
    }

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_grupo;
private String C2nombre;
private static String nom_tabla;
private static String nom_idtabla;
private static String nom_grupo_0;
private static String nom_grupo_1;
//---------------TABLA-ID---------------
	public producto_grupo() {
		setTb_producto_grupo("producto_grupo");
		setId_idproducto_grupo("idproducto_grupo");
	}
	public static String getTb_producto_grupo(){
		return nom_tabla;
	}
	public static void setTb_producto_grupo(String nom_tabla){
		producto_grupo.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_grupo(){
		return nom_idtabla;
	}
	public static void setId_idproducto_grupo(String nom_idtabla){
		producto_grupo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_grupo(){
		return C1idproducto_grupo;
	}
	public void setC1idproducto_grupo(int C1idproducto_grupo){
		this.C1idproducto_grupo = C1idproducto_grupo;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public String toString() {
		return "producto_grupo(" + ",idproducto_grupo=" + C1idproducto_grupo + " ,nombre=" + C2nombre + " )";
	}
}
