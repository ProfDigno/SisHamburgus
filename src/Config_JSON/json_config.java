/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import java.io.FileReader;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_config {

    /**
     * @return the tel_sistema
     */
    public static String getTel_sistema() {
        return tel_sistema;
    }

    /**
     * @param aTel_sistema the tel_sistema to set
     */
    public static void setTel_sistema(String aTel_sistema) {
        tel_sistema = aTel_sistema;
    }

    /**
     * @return the dir_sistema
     */
    public static String getDir_sistema() {
        return dir_sistema;
    }

    /**
     * @param aDir_sistema the dir_sistema to set
     */
    public static void setDir_sistema(String aDir_sistema) {
        dir_sistema = aDir_sistema;
    }

    /**
     * @return the nom_sistema
     */
    public static String getNombre_sistema() {
        return nom_sistema;
    }

    /**
     * @param aNom_sistema the nom_sistema to set
     */
    public static void setNombre_sistema(String aNom_sistema) {
        nom_sistema = aNom_sistema;
    }

    /**
     * @return the version
     */
    public static String getVersion() {
        return version;
    }

    /**
     * @param aVersion the version to set
     */
    public static void setVersion(String aVersion) {
        version = aVersion;
    }

    /**
     * @return the fecha_sis
     */
    public static String getFecha_sis() {
        return fecha_sis;
    }

    /**
     * @param aFecha_sis the fecha_sis to set
     */
    public static void setFecha_sis(String aFecha_sis) {
        fecha_sis = aFecha_sis;
    }

    /**
     * @return the user_admin
     */
    public static String getUser_admin() {
        return user_admin;
    }

    /**
     * @param aUser_admin the user_admin to set
     */
    public static void setUser_admin(String aUser_admin) {
        user_admin = aUser_admin;
    }

    /**
     * @return the pass_admin
     */
    public static String getPass_admin() {
        return pass_admin;
    }

    /**
     * @param aPass_admin the pass_admin to set
     */
    public static void setPass_admin(String aPass_admin) {
        pass_admin = aPass_admin;
    }

    private static String nom_sistema;
    private static String tel_sistema;
    private static String dir_sistema;
    private static String version;
    private static String fecha_sis;
    private static String user_admin;
    private static String pass_admin;
    private static int tiempo_max_emitido;
    private static boolean venta_negativo;

    public void cargar_jsom_configuracion() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("src\\json_configuracion.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String nom_sistema = (String) jsonObject.get("nom_sistema");
            String tel_sistema = (String) jsonObject.get("tel_sistema");
            String dir_sistema = (String) jsonObject.get("dir_sistema");
            String fecha_sis = (String) jsonObject.get("fecha_sis");
            String version = (String) jsonObject.get("version");
            String user_admin = (String) jsonObject.get("user_admin");
            String pass_admin = (String) jsonObject.get("pass_admin");
            boolean vent_nega = (boolean) jsonObject.get("venta_negativo");
            String tiempo_max_emitido = (String) jsonObject.get("tiempo_max_emitido");
            setNombre_sistema(nom_sistema);
            setTel_sistema(tel_sistema);
            setDir_sistema(dir_sistema);
            setFecha_sis(fecha_sis);
            setVersion(version);
            setUser_admin(user_admin);
            setPass_admin(pass_admin);
            setVenta_negativo(vent_nega);
            setTiempo_max_emitido(Integer.parseInt(tiempo_max_emitido));
            System.out.println("json Configuracion:" + jsonObject);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
    }

    /**
     * @return the venta_negativo
     */
    public static boolean isVenta_negativo() {
        return venta_negativo;
    }

    /**
     * @param aVenta_negativo the venta_negativo to set
     */
    public static void setVenta_negativo(boolean aVenta_negativo) {
        venta_negativo = aVenta_negativo;
    }

    /**
     * @return the tiempo_max_emitido
     */
    public static int getTiempo_max_emitido() {
        return tiempo_max_emitido;
    }

    /**
     * @param aTiempo_max_emitido the tiempo_max_emitido to set
     */
    public static void setTiempo_max_emitido(int aTiempo_max_emitido) {
        tiempo_max_emitido = aTiempo_max_emitido;
    }

}
