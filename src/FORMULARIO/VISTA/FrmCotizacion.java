/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmCotizacion extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    cotizacion coti = new cotizacion();
    BO_cotizacion coBO = new BO_cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor= new cla_color_pelete();
    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("COTIZACION");
        evetbl.centrar_formulario(this);
        boton_guardar();
        color_formulario();
    }
    void color_formulario(){
        panel_cambio.setBackground(clacolor.getColor_insertar_primario());
    }
    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtdolar_guarani, "DEBE CARGAR UN MONTO DOLAR")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtreal_guarani, "DEBE CARGAR UN MONTO REAL")) {
            return false;
        }
        
        return true;
    }

    void boton_guardar() {
        if (codao.getBoolean_cotizacion_existente(conn)) {
            coti.setIdcotizacion(1);
            coti.setDolar_guarani(6000);
            coti.setReal_guarani(1500);
            coBO.insertar_cotizacion(coti);
            cargar_datos_cotizacion();
        } else {
            cargar_datos_cotizacion();
        }
    }

    void boton_editar() {
        if (validar_guardar()) {
            coti.setIdcotizacion(1);
            coti.setDolar_guarani(evejtf.getDouble_format_nro_entero1(txtdolar_guarani));
            coti.setReal_guarani(evejtf.getDouble_format_nro_entero1(txtreal_guarani));
            coBO.update_cotizacion(coti);
            cargar_datos_cotizacion();
            this.dispose();
        }
    }

    private void cargar_datos_cotizacion() {
        codao.cargar_cotizacion(coti,1);
        txtdolar_guarani.setText(evejtf.getString_format_nro_decimal(coti.getStdolar_guarani()));
        txtreal_guarani.setText(evejtf.getString_format_nro_decimal(coti.getStreal_guarani()));
        FrmMenuHamburgus.jFdolar.setValue(coti.getDolar_guarani());
        FrmMenuHamburgus.jFreal.setValue(coti.getReal_guarani());
        btneditar.setEnabled(true);
    }

    public FrmCotizacion() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_cambio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtdolar_guarani = new javax.swing.JTextField();
        btneditar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtreal_guarani = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        panel_cambio.setBackground(new java.awt.Color(153, 204, 255));
        panel_cambio.setBorder(javax.swing.BorderFactory.createTitledBorder("CAMBIO A GUARANI:"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("DOLAR:");

        txtdolar_guarani.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdolar_guarani.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdolar_guaraniKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdolar_guaraniKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdolar_guaraniKeyTyped(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("REAL:");

        txtreal_guarani.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtreal_guarani.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtreal_guaraniKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtreal_guaraniKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtreal_guaraniKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panel_cambioLayout = new javax.swing.GroupLayout(panel_cambio);
        panel_cambio.setLayout(panel_cambioLayout);
        panel_cambioLayout.setHorizontalGroup(
            panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cambioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_cambioLayout.createSequentialGroup()
                        .addGroup(panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(33, 33, 33)
                        .addGroup(panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtdolar_guarani, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(txtreal_guarani))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        panel_cambioLayout.setVerticalGroup(
            panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cambioLayout.createSequentialGroup()
                .addGroup(panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdolar_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtreal_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btneditar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_cambio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_cambio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void txtdolar_guaraniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdolar_guaraniKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtdolar_guarani, txtreal_guarani);
    }//GEN-LAST:event_txtdolar_guaraniKeyPressed

    private void txtreal_guaraniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtreal_guaraniKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_editar();
        }
    }//GEN-LAST:event_txtreal_guaraniKeyPressed

    private void txtdolar_guaraniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdolar_guaraniKeyReleased
        // TODO add your handling code here:
//        evejtf.getString_format_nro_entero(txtdolar_guarani);
    }//GEN-LAST:event_txtdolar_guaraniKeyReleased

    private void txtreal_guaraniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtreal_guaraniKeyReleased
        // TODO add your handling code here:
//        evejtf.getString_format_nro_entero(txtreal_guarani);
    }//GEN-LAST:event_txtreal_guaraniKeyReleased

    private void txtdolar_guaraniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdolar_guaraniKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtdolar_guaraniKeyTyped

    private void txtreal_guaraniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtreal_guaraniKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtreal_guaraniKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panel_cambio;
    private javax.swing.JTextField txtdolar_guarani;
    private javax.swing.JTextField txtreal_guarani;
    // End of variables declaration//GEN-END:variables
}
