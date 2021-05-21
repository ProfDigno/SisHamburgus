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
public class backup {
    /**
     * CREATE TABLE "backup" (
	"idbackup" INTEGER NOT NULL ,
	"fecha" DATE NOT NULL ,
	"direc_dump" TEXT NOT NULL ,
	"direc_backup" TEXT NOT NULL ,
	"basedato" TEXT NOT NULL ,
	"localhost" TEXT NOT NULL ,
	"puerto" TEXT NOT NULL ,
	"usuario" TEXT NOT NULL ,
	"senha" TEXT NOT NULL ,
	"cantidad" INTEGER NOT NULL 
);
     */
    private int b1idbackup;
    private String b2fecha;
    private String b3direc_dump;
    private String b4direc_backup;
    private String b5basedato;
    private String b6localhost;
    private String b7puerto;
    private String b8usuario;
    private String b9senha;
    private int b10cantidad;
    private static String tabla;
    private static String idtabla;

    public backup() {
        setTabla("backup");
        setIdtabla("idbackup");
    }
    
    public int getB1idbackup() {
        return b1idbackup;
    }

    public void setB1idbackup(int b1idbackup) {
        this.b1idbackup = b1idbackup;
    }

    public String getB2fecha() {
        return b2fecha;
    }

    public void setB2fecha(String b2fecha) {
        this.b2fecha = b2fecha;
    }

    public String getB3direc_dump() {
        return b3direc_dump;
    }

    public void setB3direc_dump(String b3direc_dump) {
        this.b3direc_dump = b3direc_dump;
    }

    public String getB4direc_backup() {
        return b4direc_backup;
    }

    public void setB4direc_backup(String b4direc_backup) {
        this.b4direc_backup = b4direc_backup;
    }

    public String getB5basedato() {
        return b5basedato;
    }

    public void setB5basedato(String b5basedato) {
        this.b5basedato = b5basedato;
    }

    public String getB6localhost() {
        return b6localhost;
    }

    public void setB6localhost(String b6localhost) {
        this.b6localhost = b6localhost;
    }

    public String getB7puerto() {
        return b7puerto;
    }

    public void setB7puerto(String b7puerto) {
        this.b7puerto = b7puerto;
    }

    public String getB8usuario() {
        return b8usuario;
    }

    public void setB8usuario(String b8usuario) {
        this.b8usuario = b8usuario;
    }

    public String getB9senha() {
        return b9senha;
    }

    public void setB9senha(String b9senha) {
        this.b9senha = b9senha;
    }

    public int getB10cantidad() {
        return b10cantidad;
    }

    public void setB10cantidad(int b10cantidad) {
        this.b10cantidad = b10cantidad;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        backup.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        backup.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "backup{" + "b1idbackup=" + b1idbackup + ", b2fecha=" + b2fecha + ", b3direc_dump=" + b3direc_dump + ", b4direc_backup=" + b4direc_backup + ", b5basedato=" + b5basedato + ", b6localhost=" + b6localhost + ", b7puerto=" + b7puerto + ", b8usuario=" + b8usuario + ", b9senha=" + b9senha + ", b10cantidad=" + b10cantidad + '}';
    }
    
}
