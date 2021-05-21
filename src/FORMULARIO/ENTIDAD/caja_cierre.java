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
public class caja_cierre {
    
//---------------DECLARAR VARIABLES---------------
private int C1idcaja_cierre;
private String C2fecha_inicio;
private String C3fecha_fin;
private String C4estado;
private int C5fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_cierre() {
		setTb_caja_cierre("caja_cierre");
		setId_idcaja_cierre("idcaja_cierre");
	}
	public static String getTb_caja_cierre(){
		return nom_tabla;
	}
	public static void setTb_caja_cierre(String nom_tabla){
		caja_cierre.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_cierre(){
		return nom_idtabla;
	}
	public static void setId_idcaja_cierre(String nom_idtabla){
		caja_cierre.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcaja_cierre(){
		return C1idcaja_cierre;
	}
	public void setC1idcaja_cierre(int C1idcaja_cierre){
		this.C1idcaja_cierre = C1idcaja_cierre;
	}
	public String getC2fecha_inicio(){
		return C2fecha_inicio;
	}
	public void setC2fecha_inicio(String C2fecha_inicio){
		this.C2fecha_inicio = C2fecha_inicio;
	}
	public String getC3fecha_fin(){
		return C3fecha_fin;
	}
	public void setC3fecha_fin(String C3fecha_fin){
		this.C3fecha_fin = C3fecha_fin;
	}
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public int getC5fk_idusuario(){
		return C5fk_idusuario;
	}
	public void setC5fk_idusuario(int C5fk_idusuario){
		this.C5fk_idusuario = C5fk_idusuario;
	}
	public String toString() {
		return "caja_cierre(" + ",idcaja_cierre=" + C1idcaja_cierre + " ,fecha_inicio=" + C2fecha_inicio + " ,fecha_fin=" + C3fecha_fin + " ,estado=" + C4estado + " ,fk_idusuario=" + C5fk_idusuario + " )";
	}
}
