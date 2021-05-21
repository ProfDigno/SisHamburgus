/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Digno
 */
public class FrmEntregador_venta extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    entregador entre = new entregador();
    BO_entregador pcBO = new BO_entregador();
    DAO_entregador pcdao = new DAO_entregador();
    EvenJTextField evejtf = new EvenJTextField();
    EvenJtable evejt = new EvenJtable();
    EvenFecha evefec = new EvenFecha();
    Connection conn = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    cla_color_pelete clacolor= new cla_color_pelete();
    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("ENTREGADOR");
        evetbl.centrar_formulario(this);
        pcdao.actualizar_tabla_entregador(conn, tblentregador);
        txtfecha_desde.setText(evefec.getString_formato_fecha());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        color_formulario();
    }
    void color_formulario(){
        panel_entregador.setBackground(clacolor.getColor_insertar_primario());
        panel_filtro.setBackground(clacolor.getColor_insertar_secundario());
    }
    void seleccionar_tabla() {
        int identregador = evejt.getInt_select_id(tblentregador);
        String nombre = evejt.getString_select(tblentregador, 1);
        lblentregador.setText(nombre);
        String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
        String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
        pcdao.actualizar_tabla_suma_delivery(conn, tblentregador_venta, identregador, fecdesde, fechasta);
        suma_venta_delivery(identregador, fecdesde, fechasta);
    }

    void boton_hoy() {
        txtfecha_desde.setText(evefec.getString_formato_fecha());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        boton_buscar();
    }

    void boton_mes() {
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        boton_buscar();
    }

    void boton_buscar() {
        if (!evejt.getBoolean_validar_select(tblentregador)) {
            seleccionar_tabla();
        }
    }

    void boton_imprimir() {
        if (!evejt.getBoolean_validar_select(tblentregador)) {
            int identregador = evejt.getInt_select_id(tblentregador);
            String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
            String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
            pcdao.actualizar_tabla_suma_delivery(conn, tblentregador_venta, identregador, fecdesde, fechasta);
            suma_venta_delivery(identregador, fecdesde, fechasta);
            pcdao.imprimir_venta_delivery(conn, identregador, fecdesde, fechasta);
        }
    }

    void suma_venta_delivery(int identregador, String fecdesde, String fechasta) {
        String titulo = "caja_detalle_saldo";
        String sql = "select count(*) as cantidad,sum(monto_venta) as monto_venta,"
                + "sum(monto_delivery) as monto_delivery \n"
                + "from venta "
                + "where delivery=true "
                + "and (estado='EMITIDO' or estado='TERMINADO') "
                + "and fk_identregador=" + identregador + " "
                + "and date(fecha_inicio)>='" + fecdesde + "' and date(fecha_inicio)<='" + fechasta + "' ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int monto_venta = rs.getInt("monto_venta");
                int monto_delivery = rs.getInt("monto_delivery");
                String cantidad = rs.getString("cantidad");
                jFmonto_venta.setValue(monto_venta);
                jFmonto_delivery.setValue(monto_delivery);
                txtcantidad.setText(cantidad);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public FrmEntregador_venta() {
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

        panel_entregador = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblentregador = new javax.swing.JTable();
        panel_filtro = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblentregador_venta = new javax.swing.JTable();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        btnhoy = new javax.swing.JButton();
        btnmes = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jFmonto_venta = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFmonto_delivery = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcantidad = new javax.swing.JTextField();
        btnimp_venta_deli = new javax.swing.JButton();
        lblentregador = new javax.swing.JLabel();

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

        panel_entregador.setBackground(new java.awt.Color(51, 204, 255));
        panel_entregador.setBorder(javax.swing.BorderFactory.createTitledBorder("ENTREGADOR"));

        tblentregador.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblentregador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblentregador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentregadorMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblentregador);

        javax.swing.GroupLayout panel_entregadorLayout = new javax.swing.GroupLayout(panel_entregador);
        panel_entregador.setLayout(panel_entregadorLayout);
        panel_entregadorLayout.setHorizontalGroup(
            panel_entregadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
        );
        panel_entregadorLayout.setVerticalGroup(
            panel_entregadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );

        panel_filtro.setBackground(new java.awt.Color(51, 204, 255));
        panel_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("VENTA DELIVERY"));

        tblentregador_venta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblentregador_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblentregador_venta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentregador_ventaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblentregador_venta);

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Fecha Desde:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Fecha Hasta:");

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        btnbuscar.setText("BUSCAR");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        btnhoy.setText("HOY");
        btnhoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhoyActionPerformed(evt);
            }
        });

        btnmes.setText("MES");
        btnmes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmesActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("TOTAL VENTA:");

        jFmonto_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_venta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("TOTAL DELIVERY:");

        jFmonto_delivery.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_delivery.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_delivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("CANTIDAD:");

        txtcantidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtcantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnimp_venta_deli.setText("IMPRIMIR VENTA DELIVERY");
        btnimp_venta_deli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimp_venta_deliActionPerformed(evt);
            }
        });

        lblentregador.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblentregador.setText("entregador");

        javax.swing.GroupLayout panel_filtroLayout = new javax.swing.GroupLayout(panel_filtro);
        panel_filtro.setLayout(panel_filtroLayout);
        panel_filtroLayout.setHorizontalGroup(
            panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_filtroLayout.createSequentialGroup()
                .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_filtroLayout.createSequentialGroup()
                        .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnimp_venta_deli)
                            .addGroup(panel_filtroLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFmonto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFmonto_delivery, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_filtroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(panel_filtroLayout.createSequentialGroup()
                                .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_filtroLayout.createSequentialGroup()
                                        .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnbuscar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnhoy)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnmes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel15)))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_filtroLayout.setVerticalGroup(
            panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_filtroLayout.createSequentialGroup()
                .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnhoy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnmes, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblentregador, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnimp_venta_deli)
                    .addComponent(jLabel7)
                    .addComponent(jFmonto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jFmonto_delivery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_entregador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 737, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_entregador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_filtro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        pcdao.ancho_tabla_entregador(tblentregador);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblentregadorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentregadorMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblentregadorMouseReleased

    private void tblentregador_ventaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentregador_ventaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblentregador_ventaMouseReleased

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        // TODO add your handling code here:
        boton_buscar();
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btnhoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhoyActionPerformed
        // TODO add your handling code here:
        boton_hoy();
    }//GEN-LAST:event_btnhoyActionPerformed

    private void btnmesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmesActionPerformed
        // TODO add your handling code here:
        boton_mes();
    }//GEN-LAST:event_btnmesActionPerformed

    private void btnimp_venta_deliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimp_venta_deliActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimp_venta_deliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btnhoy;
    private javax.swing.JButton btnimp_venta_deli;
    private javax.swing.JButton btnmes;
    private javax.swing.JFormattedTextField jFmonto_delivery;
    private javax.swing.JFormattedTextField jFmonto_venta;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblentregador;
    private javax.swing.JPanel panel_entregador;
    private javax.swing.JPanel panel_filtro;
    private javax.swing.JTable tblentregador;
    private javax.swing.JTable tblentregador_venta;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
