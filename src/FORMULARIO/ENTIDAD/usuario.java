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
public class usuario {
    /*
    CREATE TABLE "usuario" (
	"idusuario" SERIAL NOT NULL ,
	"nombre" TEXT NOT NULL ,
	"usuario" TEXT NOT NULL ,
	"senha" TEXT NOT NULL ,
	"nivel" TEXT NOT NULL ,
	PRIMARY KEY("idusuario")
);
    */
    private int u1idusuario;
    private String u2nombre;
    private String u3usuario;
    private String u4senha;
    private String u5nivel;
    private static int global_idusuario;
    private static String global_nombre;
    private static String global_nivel;
    private static String tabla;
    private static String idtabla;
    
    public usuario() {
         setTabla("usuario");
         setIdtabla("idusuario");
    }

    public static int getGlobal_idusuario() {
        return global_idusuario;
    }

    public static void setGlobal_idusuario(int global_idusuario) {
        usuario.global_idusuario = global_idusuario;
    }

    public static String getGlobal_nombre() {
        return global_nombre;
    }

    public static void setGlobal_nombre(String global_nombre) {
        usuario.global_nombre = global_nombre;
    }

    public static String getGlobal_nivel() {
        return global_nivel;
    }

    public static void setGlobal_nivel(String global_nivel) {
        usuario.global_nivel = global_nivel;
    }
    
    public int getU1idusuario() {
        return u1idusuario;
    }

    public void setU1idusuario(int u1idusuario) {
        this.u1idusuario = u1idusuario;
    }

    public String getU2nombre() {
        return u2nombre;
    }

    public void setU2nombre(String u2nombre) {
        this.u2nombre = u2nombre;
    }

    public String getU3usuario() {
        return u3usuario;
    }

    public void setU3usuario(String u3usuario) {
        this.u3usuario = u3usuario;
    }

    public String getU4senha() {
        return u4senha;
    }

    public void setU4senha(String u4senha) {
        this.u4senha = u4senha;
    }

    public String getU5nivel() {
        return u5nivel;
    }

    public void setU5nivel(String u5nivel) {
        this.u5nivel = u5nivel;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        usuario.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        usuario.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "usuario{" + "u1idusuario=" + u1idusuario + ", u2nombre=" + u2nombre + ", u3usuario=" + u3usuario + ", u4senha=" + u4senha + ", u5nivel=" + u5nivel + '}';
    }
    
}
