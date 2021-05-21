/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class control_vista {

    /**
     * @return the comanda_abierto
     */
    public static boolean isComanda_abierto() {
        if(!comanda_abierto){
            JOptionPane.showMessageDialog(null,"EL FORMULARIO COMANDA YA ESTA ABIERO","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return comanda_abierto;
    }

    /**
     * @param aComanda_abierto the comanda_abierto to set
     */
    public static void setComanda_abierto(boolean aComanda_abierto) {
        comanda_abierto = aComanda_abierto;
    }
    private static boolean comanda_abierto;
}
