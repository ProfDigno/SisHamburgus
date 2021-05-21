/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Fecha;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class EvenFecha {

    String formato_fecha = "yyyy-MM-dd";
    String fecha_dia1 = "yyyy-MM-01";
    String formato_fechaHora = "yyyy-MM-dd HH:mm";
    private String formato_hora = "HH:mm:ss";

    public String getString_validar_fecha(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fecha);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha();
        }
        return Sfecha;
    }

    public String getString_validar_fecha_hora(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha_hora();
        }
        return Sfecha;
    }

    public java.sql.Date getDate_fecha_cargado(String fechaStr) {
        java.sql.Date dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Date(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return dateSql;
    }

    public java.sql.Timestamp getTimestamp_fecha_cargado(String fechaStr) {
        java.sql.Timestamp dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Timestamp(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            dateSql = getTimestamp_sistema();
        }
        return dateSql;
    }

    public String getString_formato_fecha() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_fecha);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_hora);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public int getInt_segundos_ahora() {
        java.util.Date utilDate = new java.util.Date(); //fecha actual
        SimpleDateFormat sdf_hora = new SimpleDateFormat("HH");
        int hora = Integer.parseInt(sdf_hora.format(utilDate));
        SimpleDateFormat sdf_min = new SimpleDateFormat("mm");
        int min = Integer.parseInt(sdf_min.format(utilDate));
        SimpleDateFormat sdf_seg = new SimpleDateFormat("ss");
        int seg = Integer.parseInt(sdf_seg.format(utilDate));
        int resul=((hora*3600)+(min*60)+seg);
        return resul;
    }
    public int getInt_diferencia_en_segundo(int tiempo_sql) {
        int resul=getInt_segundos_ahora()-tiempo_sql;
        return resul;
    }
    public String getString_convertir_segundo_hora(int num){
        int hor,min,seg;
        hor=num/3600;
        min=(num-(3600*hor))/60;
        seg=num-((hor*3600)+(min*60));
        String horaformada=hor+"h "+min+"m "+seg+"s";
        System.out.println(horaformada);
        return horaformada;
    }
    public String getString_fecha_dia1() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha_dia1);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_fecha_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_fechaHora);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public java.util.Date getDate_sistema() {
        java.util.Date fechaDate;
        try {
            fechaDate = new java.util.Date();
            return fechaDate;
        } catch (Exception e) {
            System.out.println("" + e);
            return null;
        }
    }

    public java.sql.Date getDateSQL_sistema() {
        java.sql.Date fechaDate;
        try {
            fechaDate = new java.sql.Date(new java.util.Date().getTime());
            return fechaDate;
        } catch (Exception e) {
            System.out.println("" + e);
            return null;
        }
    }

    public Timestamp getTimestamp_sistema() {
        long timeNow = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
        return ts;
    }

}
