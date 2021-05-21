/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Color;

import java.awt.Color;

/**
 *
 * @author Digno
 */
public class cla_color_pelete {

    /**
     * @return the color_base
     */
    public static Color getColor_base() {
        return color_base;
    }

    /**
     * @param aColor_base the color_base to set
     */
    public static void setColor_base(Color aColor_base) {
        color_base = aColor_base;
    }
    public static Color color_insertar_primario;
    public static Color color_insertar_secundario;
    public static Color color_tabla;
    public static Color color_referencia;
    private static Color color_base;

    public static Color getColor_insertar_primario() {
        return color_insertar_primario;
    }

    public static void setColor_insertar_primario(Color color_insertar_primario) {
        cla_color_pelete.color_insertar_primario = color_insertar_primario;
    }

    public static Color getColor_insertar_secundario() {
        return color_insertar_secundario;
    }

    public static void setColor_insertar_secundario(Color color_insertar_secundario) {
        cla_color_pelete.color_insertar_secundario = color_insertar_secundario;
    }

    public static Color getColor_tabla() {
        return color_tabla;
    }

    public static void setColor_tabla(Color color_tabla) {
        cla_color_pelete.color_tabla = color_tabla;
    }

    public static Color getColor_referencia() {
        return color_referencia;
    }

    public static void setColor_referencia(Color color_referencia) {
        cla_color_pelete.color_referencia = color_referencia;
    }
    
}
