/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JTextField;

import Evento.Fecha.EvenFecha;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class EvenJTextField {

    EvenFecha evefec = new EvenFecha();

    public void verificar_fecha(KeyEvent evt, JTextField txtfecha) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtfecha.getText().trim().length() > 0) {
                txtfecha.setText(evefec.getString_validar_fecha(txtfecha.getText()));
            }
        }
    }

    public void cantidad_caracteres(JTextField txttexto, int limite) {
        if (txttexto.getText().trim().length() >= limite) {
            JOptionPane.showMessageDialog(txttexto, "NO SE PUEDE CARGAR MAS DEL LIMITE", "ERROR", JOptionPane.ERROR_MESSAGE);
            txttexto.setText(null);
        }
    }

    public boolean getBoo_JTextField_vacio(JTextField txtcampo, String mensaje) {
        if (txtcampo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(txtcampo, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcampo.setBackground(Color.ORANGE);
            txtcampo.grabFocus();
            return true;
        } else {
            txtcampo.setBackground(Color.WHITE);
            return false;
        }
    }

    public boolean getBoo_JPasswordField_vacio(JPasswordField txtcampo, String mensaje) {
        if (txtcampo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(txtcampo, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcampo.setBackground(Color.ORANGE);
            txtcampo.grabFocus();
            return true;
        } else {
            txtcampo.setBackground(Color.WHITE);
            return false;
        }
    }

    public boolean getBoo_JTextarea_vacio(JTextArea txtcampo, String mensaje) {
        if (txtcampo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(txtcampo, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcampo.setBackground(Color.ORANGE);
            txtcampo.grabFocus();
            return true;
        } else {
            txtcampo.setBackground(Color.WHITE);
            return false;
        }
    }

    public void saltar_campo_enter(KeyEvent evt, JTextField txtcampo1, JTextField txtcampo2) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcampo1.setBackground(Color.WHITE);
            txtcampo2.setBackground(Color.YELLOW);
            txtcampo2.grabFocus();
        }
    }

    public void seleccionar_lista(KeyEvent evt, JList Jlista) {
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            Jlista.requestFocus();
            Jlista.setSelectedIndex(0);
        }
    }

    public void soloNumero(java.awt.event.KeyEvent evt) {
        int k = (int) evt.getKeyChar();
        if (k == 8 || k == 10 || k == 46 || k == 48 || k == 49 || k == 50 || k == 51 || k == 52 || k == 53 || k == 54 || k == 55 || k == 56 || k == 57) {
        } else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "No puede ingresar Letras!!! " + k + " ", "Error Datos", JOptionPane.ERROR_MESSAGE);
            if (k == 44) {
                JOptionPane.showMessageDialog(null, "USAR PUNTO (.) NO COMA (,) ", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getString_format_nro_entero1(JTextField txtmonto) {
        String pago_remplaso = "";
        if (txtmonto.getText().trim().length() > 0) {
            String Spagado = txtmonto.getText();
            pago_remplaso = Spagado.replace(".", "");
//            pago_remplaso = Spagado.replace(",", "");
            double Ipagado = Double.parseDouble(pago_remplaso);
            String for_Ipagado = String.format("%1$,.0f", Ipagado);
            txtmonto.setText(for_Ipagado);
        }
        return pago_remplaso;
    }

    public String getString_format_nro_entero(double Ipagado) {
        String pago_remplaso = "";
        int Imonto = (int) Ipagado;
        String Spagado = String.valueOf(Imonto);
        pago_remplaso = Spagado.replace(".", "");
//        pago_remplaso = Spagado.replace(",", "");
        return pago_remplaso;
    }

    public String getString_format_nro_decimal(String txtmonto) {
        String pago_remplaso = "";
        String monto_decimal = "";
        if (txtmonto.trim().length() > 0) {
            String Spagado = txtmonto;
            pago_remplaso = Spagado.replace(".", "");
//            pago_remplaso = Spagado.replace(",", "");
            double Ipagado = Double.parseDouble(pago_remplaso);
            String for_Ipagado = String.format("%1$,.0f", Ipagado);
            monto_decimal = (for_Ipagado);
        }
        return monto_decimal;
    }

    public String getString_format_nro_decimal(double Ipagado) {
        String monto_decimal = "";
        String for_Ipagado = String.format("%1$,.0f", Ipagado);
        monto_decimal = (for_Ipagado);
        return monto_decimal;
    }

    public double getDouble_format_nro_entero1(JTextField txtmonto) {
        double Ipagado = 0;
        String pago_remplaso = "";
        if (txtmonto.getText().trim().length() > 0) {
            String Spagado = txtmonto.getText();
             pago_remplaso = Spagado.replace(".", "");
//             pago_remplaso = Spagado.replace(",", "");
            Ipagado = Double.parseDouble(pago_remplaso);
            String for_Ipagado = String.format("%1$,.0f", Ipagado);
            txtmonto.setText(for_Ipagado);
        }
        return Ipagado;
    }

    public double getDouble_format_nro_entero(String txtmonto) {
        double Ipagado = 0;
        String pago_remplaso = "";
        if (txtmonto.trim().length() > 0) {
            String Spagado = txtmonto;
            pago_remplaso = Spagado.replace(".", "");
//            pago_remplaso = Spagado.replace(",", "");
            Ipagado = Double.parseDouble(pago_remplaso);
        }
        return Ipagado;
    }
    public double getDouble_convertir_JTextField(JTextField txtmonto) {
        double Ipagado = 0;
        if (txtmonto.getText().trim().length() > 0) {
            String Spagado = txtmonto.getText();
            Ipagado = Double.parseDouble(Spagado);
        }
        return Ipagado;
    }
}
