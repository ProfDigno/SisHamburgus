/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import CONFIGURACION.EvenDatosPc;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.DAO.DAO_zona_delivery;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.zona_delivery;
import java.io.FileReader;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_crear_cliente {

    /**
     * @return the js_nombre
     */
    public String getJs_nombre() {
        return js_nombre;
    }

    /**
     * @param js_nombre the js_nombre to set
     */
    public void setJs_nombre(String js_nombre) {
        this.js_nombre = js_nombre;
    }

    /**
     * @return the js_direccion
     */
    public String getJs_direccion() {
        return js_direccion;
    }

    /**
     * @param js_direccion the js_direccion to set
     */
    public void setJs_direccion(String js_direccion) {
        this.js_direccion = js_direccion;
    }

    /**
     * @return the js_telefono
     */
    public String getJs_telefono() {
        return js_telefono;
    }

    /**
     * @param js_telefono the js_telefono to set
     */
    public void setJs_telefono(String js_telefono) {
        this.js_telefono = js_telefono;
    }

    /**
     * @return the js_ruc
     */
    public String getJs_ruc() {
        return js_ruc;
    }

    /**
     * @param js_ruc the js_ruc to set
     */
    public void setJs_ruc(String js_ruc) {
        this.js_ruc = js_ruc;
    }

    /**
     * @return the js_tipo
     */
    public String getJs_tipo() {
        return js_tipo;
    }

    /**
     * @param js_tipo the js_tipo to set
     */
    public void setJs_tipo(String js_tipo) {
        this.js_tipo = js_tipo;
    }

    /**
     * @return the js_monto_zona
     */
    public int getJs_monto_zona() {
        return js_monto_zona;
    }

    /**
     * @param js_monto_zona the js_monto_zona to set
     */
    public void setJs_monto_zona(int js_monto_zona) {
        this.js_monto_zona = js_monto_zona;
    }

    /**
     * @return the equipo_1
     */
    public String getEquipo_1() {
        return equipo_1;
    }

    /**
     * @param equipo_1 the equipo_1 to set
     */
    public void setEquipo_1(String equipo_1) {
        this.equipo_1 = equipo_1;
    }

    /**
     * @return the setRuta_1
     */
    public String getSetRuta_1() {
        return setRuta_1;
    }

    /**
     * @param setRuta_1 the setRuta_1 to set
     */
    public void setSetRuta_1(String setRuta_1) {
        this.setRuta_1 = setRuta_1;
    }

    /**
     * @return the equipo_2
     */
    public String getEquipo_2() {
        return equipo_2;
    }

    /**
     * @param equipo_2 the equipo_2 to set
     */
    public void setEquipo_2(String equipo_2) {
        this.equipo_2 = equipo_2;
    }

    /**
     * @return the setRuta_2
     */
    public String getSetRuta_2() {
        return setRuta_2;
    }

    /**
     * @param setRuta_2 the setRuta_2 to set
     */
    public void setSetRuta_2(String setRuta_2) {
        this.setRuta_2 = setRuta_2;
    }

    /**
     * @return the getRuta_1
     */
    public String getGetRuta_1() {
        return getRuta_1;
    }

    /**
     * @param getRuta_1 the getRuta_1 to set
     */
    public void setGetRuta_1(String getRuta_1) {
        this.getRuta_1 = getRuta_1;
    }

    /**
     * @return the getRuta_2
     */
    public String getGetRuta_2() {
        return getRuta_2;
    }

    /**
     * @param getRuta_2 the getRuta_2 to set
     */
    public void setGetRuta_2(String getRuta_2) {
        this.getRuta_2 = getRuta_2;
    }

    EvenUtil util = new EvenUtil();
    DAO_zona_delivery zdao = new DAO_zona_delivery();
    zona_delivery zona = new zona_delivery();
    JSONParser parser = new JSONParser();
    EvenDatosPc evepc = new EvenDatosPc();
    private String equipo_1;
    private String setRuta_1;
    private String getRuta_1;
    private String equipo_2;
    private String setRuta_2;
    private String getRuta_2;
    private String js_nombre;
    private String js_direccion;
    private String js_telefono;
    private String js_ruc;
    private String js_tipo;
    private int js_monto_zona;
    public void crear_jsom_cliente(cliente clie) {
//        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src\\json_config_cliente.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String setRuta="";
            String equipo_1 = (String) jsonObject.get("equipo_1");
            String setRuta_1 = (String) jsonObject.get("setRuta_1");
            String getRuta_1 = (String) jsonObject.get("getRuta_1");
            String equipo_2 = (String) jsonObject.get("equipo_2");
            String setRuta_2 = (String) jsonObject.get("setRuta_2");
            String getRuta_2 = (String) jsonObject.get("getRuta_2");
            setEquipo_1(equipo_1);
            setSetRuta_1(setRuta_1);
            setGetRuta_1(getRuta_1);
            setEquipo_2(equipo_2);
            setSetRuta_2(setRuta_2);
            setGetRuta_2(getRuta_2);
            String message;
            JSONObject jscli = new JSONObject();
            jscli.put("nombre", clie.getC3nombre());
            jscli.put("direccion", clie.getC4direccion());
            jscli.put("telefono", clie.getC5telefono());
            jscli.put("ruc", clie.getC6ruc());
            jscli.put("tipo", clie.getC8tipo());
            zdao.cargar_zona_delivery(zona, clie.getC9fk_idzona_delivery());
            jscli.put("monto_zona", (int) zona.getDelivery() / 1000);
            message = jscli.toString();
            System.out.println("json crear cliente:" + message);
            if(equipo_1.equals(evepc.getString_nombre_pc())){
                setRuta=setRuta_1;
                util.crear_archivo_en_ruta(setRuta, message);
            }
            if(equipo_2.equals(evepc.getString_nombre_pc())){
                setRuta=setRuta_2;
                util.crear_archivo_en_ruta(setRuta, message);
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
    }
    public void leer_jsom_cliente() {
//        JSONParser parser = new JSONParser();
        try {
            String getRuta="";
            if(getEquipo_1().equals(evepc.getString_nombre_pc())){
                getRuta=getGetRuta_1();
            }
            if(getEquipo_2().equals(evepc.getString_nombre_pc())){
                getRuta=getGetRuta_2();
            }
            Object obj = parser.parse(new FileReader(getRuta));
            JSONObject jsonObject = (JSONObject) obj;
            String nombre = (String) jsonObject.get("nombre");
            String direccion = (String) jsonObject.get("direccion");
            String ruc = (String) jsonObject.get("ruc");
            String tipo = (String) jsonObject.get("tipo");
            String telefono = (String) jsonObject.get("telefono");
            String monto_zona = (String) jsonObject.get("monto_zona");
            setJs_nombre(nombre);
            setJs_direccion(direccion);
            setJs_ruc(ruc);
            setJs_tipo(tipo);
            setJs_telefono(telefono);
//            setJs_monto_zona(Integer.parseInt(monto_zona));
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
    }

}
