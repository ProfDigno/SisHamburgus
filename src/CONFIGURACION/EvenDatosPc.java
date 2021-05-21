/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONFIGURACION;

import Evento.Mensaje.EvenMensajeJoptionpane;
import java.net.InetAddress;

/**
 *
 * @author Digno
 */
public class EvenDatosPc {
    EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    public String getString_nombre_pc(){
        String titulo="getString_nombre_pc";
        String nombre_pc="error";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            nombre_pc=(localHost.getHostName());
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
        return nombre_pc;
    }
    public String getString_ip_pc(){
        String titulo="getString_ip_pc";
        String ip_pc="error";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ip_pc=(localHost.getHostAddress());
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
        return ip_pc;
    }
}
