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
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_producto_grupo;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.producto_grupo;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRepVentaDetalle extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRepVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    Connection conn = ConnPostgres.getConnPosgres();
    DAO_venta vdao = new DAO_venta();
    EvenFecha evefec = new EvenFecha();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    EvenJTextField evejtf = new EvenJTextField();
    EvenConexion eveconn = new EvenConexion();
    DAO_cliente cdao = new DAO_cliente();
    cliente clie = new cliente();
    DAO_producto_grupo pgDAO = new DAO_producto_grupo();
    producto_grupo pgru = new producto_grupo();
    cla_color_pelete clacolor = new cla_color_pelete();
    private int fk_idcliente_local;

    void abrir_formulario() {
        this.setTitle("REPORTE VENTA DETALLE");
        evetbl.centrar_formulario(this);
        cargar_producto_grupo();
        color_formulario();
        reestableser();
    }
    void color_formulario() {
        panel_principal.setBackground(clacolor.getColor_insertar_primario());
        panel_fecha.setBackground(clacolor.getColor_insertar_secundario());
        panel_estado.setBackground(clacolor.getColor_insertar_secundario());
        panel_tipo.setBackground(clacolor.getColor_insertar_secundario());
        panel_grupo.setBackground(clacolor.getColor_insertar_secundario());
        panel_buscarcliente.setBackground(clacolor.getColor_insertar_secundario());
    }
    void cargar_producto_grupo() {
        pgDAO.cargar_producto_grupo(conn, pgru, 0);
        jCgrupo_0.setText(pgru.getC2nombre());
        pgDAO.cargar_producto_grupo(conn, pgru, 1);
        jCgrupo_1.setText(pgru.getC2nombre());
    }
    String filtro_venta_todos() {
        String filtro = "";
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {

            String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
            String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
            txtfecha_desde.setText(fecdesde);
            txtfecha_hasta.setText(fechasta);
            String filtro_estado = auxvent.filtro_estado(jCestado_emitido, jCestado_terminado, jCestado_anulado);
            String filtro_tipocliente = auxvent.filtro_tipocliente(jCtipo_cliente, jCtipo_funcionario);
            String filtro_grupo_pro=auxvent.filtro_grupo_producto(jCgrupo_0, jCgrupo_1,jCdelivery);
            String filtro_fecha = " and date(v.fecha_inicio) >= '" + fecdesde + "' and date(v.fecha_inicio) <= '" + fechasta + "' \n";
            String filtro_cliente = "";
            if (fk_idcliente_local >= 0) {
                filtro_cliente = " and v.fk_idcliente=" + fk_idcliente_local + "\n";
            }
            filtro = filtro + filtro_fecha;
            filtro = filtro + filtro_estado;
            filtro = filtro + filtro_tipocliente;
            filtro = filtro + filtro_cliente;
            filtro = filtro + filtro_grupo_pro;
        }
        return filtro;
    }

    void seleccionar_check() {
        double sumaventa = vdao.getDouble_suma_venta_detalle(conn,"sumaventa", filtro_venta_todos());
        jFtotal_venta.setValue(sumaventa);
        double cantidad = vdao.getDouble_suma_venta_detalle(conn,"cantidad", filtro_venta_todos());
        jFcant_fila.setValue(cantidad);
    }

    void boton_imprimir() {
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
            vdao.imprimir_rep_venta_detalle(conn, filtro_venta_todos());
        }
    }

    void reestableser() {
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        jCestado_emitido.setSelected(true);
        jCestado_terminado.setSelected(true);
        jCestado_anulado.setSelected(false);
        jCtipo_cliente.setSelected(true);
        jCtipo_funcionario.setSelected(false);
        jList_cliente.setVisible(false);
        txtbucarCliente_nombre.setText(null);
        fk_idcliente_local = -1;
        seleccionar_check();
    }

    void seleccionar_cargar_cliente() {
        fk_idcliente_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_cliente, "cliente", clie.getCliente_concat(), "idcliente");
        cdao.cargar_cliente(conn, clie, fk_idcliente_local);
        txtbucarCliente_nombre.setText(clie.getC3nombre());
    }

    public FrmRepVentaDetalle() {
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

        panel_principal = new javax.swing.JPanel();
        panel_fecha = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        panel_tipo = new javax.swing.JPanel();
        jCtipo_cliente = new javax.swing.JCheckBox();
        jCtipo_funcionario = new javax.swing.JCheckBox();
        panel_estado = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_terminado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        panel_buscarcliente = new javax.swing.JPanel();
        jList_cliente = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        txtbucarCliente_nombre = new javax.swing.JTextField();
        panel_grupo = new javax.swing.JPanel();
        jCgrupo_0 = new javax.swing.JCheckBox();
        jCgrupo_1 = new javax.swing.JCheckBox();
        jCdelivery = new javax.swing.JCheckBox();
        btnimprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jFtotal_venta = new javax.swing.JFormattedTextField();
        jFcant_fila = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        btnreset = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);

        panel_principal.setBorder(javax.swing.BorderFactory.createTitledBorder("REPORTE VENTA"));

        panel_fecha.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Desde:");

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Hasta:");

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_fechaLayout = new javax.swing.GroupLayout(panel_fecha);
        panel_fecha.setLayout(panel_fechaLayout);
        panel_fechaLayout.setHorizontalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_fechaLayout.setVerticalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        panel_tipo.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO CLIENTE"));

        jCtipo_cliente.setText("CLIENTE");
        jCtipo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtipo_clienteActionPerformed(evt);
            }
        });

        jCtipo_funcionario.setText("FUNCIONARIO");
        jCtipo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtipo_funcionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tipoLayout = new javax.swing.GroupLayout(panel_tipo);
        panel_tipo.setLayout(panel_tipoLayout);
        panel_tipoLayout.setHorizontalGroup(
            panel_tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCtipo_cliente)
                .addGap(73, 73, 73)
                .addComponent(jCtipo_funcionario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_tipoLayout.setVerticalGroup(
            panel_tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCtipo_cliente)
                    .addComponent(jCtipo_funcionario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_estado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO VENTA"));

        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_terminado.setText("TERMINADO");
        jCestado_terminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_terminadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_estadoLayout = new javax.swing.GroupLayout(panel_estado);
        panel_estado.setLayout(panel_estadoLayout);
        panel_estadoLayout.setHorizontalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_terminado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCestado_anulado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_estadoLayout.setVerticalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_terminado)
                    .addComponent(jCestado_anulado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_buscarcliente.setBorder(javax.swing.BorderFactory.createTitledBorder("POR CLIENTE"));

        jList_cliente.setBackground(new java.awt.Color(204, 204, 255));
        jList_cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_cliente.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_clienteMouseReleased(evt);
            }
        });
        jList_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_clienteKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CLI:");

        txtbucarCliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_buscarclienteLayout = new javax.swing.GroupLayout(panel_buscarcliente);
        panel_buscarcliente.setLayout(panel_buscarclienteLayout);
        panel_buscarclienteLayout.setHorizontalGroup(
            panel_buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buscarclienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(panel_buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_buscarclienteLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jList_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        panel_buscarclienteLayout.setVerticalGroup(
            panel_buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buscarclienteLayout.createSequentialGroup()
                .addGroup(panel_buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 132, Short.MAX_VALUE))
            .addGroup(panel_buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_buscarclienteLayout.createSequentialGroup()
                    .addContainerGap(35, Short.MAX_VALUE)
                    .addComponent(jList_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        panel_grupo.setBorder(javax.swing.BorderFactory.createTitledBorder("GRUPO PRODUCTO"));

        jCgrupo_0.setText("GRUPO_0");
        jCgrupo_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCgrupo_0ActionPerformed(evt);
            }
        });

        jCgrupo_1.setText("GRUPO_1");
        jCgrupo_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCgrupo_1ActionPerformed(evt);
            }
        });

        jCdelivery.setText("DELIVERY");
        jCdelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCdeliveryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_grupoLayout = new javax.swing.GroupLayout(panel_grupo);
        panel_grupo.setLayout(panel_grupoLayout);
        panel_grupoLayout.setHorizontalGroup(
            panel_grupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_grupoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCgrupo_0, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCgrupo_1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCdelivery)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_grupoLayout.setVerticalGroup(
            panel_grupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_grupoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_grupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCgrupo_0)
                    .addComponent(jCgrupo_1)
                    .addComponent(jCdelivery))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_principalLayout = new javax.swing.GroupLayout(panel_principal);
        panel_principal.setLayout(panel_principalLayout);
        panel_principalLayout.setHorizontalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_buscarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_grupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_principalLayout.setVerticalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addComponent(panel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_buscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnimprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("TOTAL VENTA:");

        jFtotal_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N

        jFcant_fila.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFcant_fila.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcant_fila.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("CANTIDAD FILA:");

        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnreset)
                            .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jFtotal_venta, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFcant_fila, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFcant_fila, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnreset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //            actualizar_gasto(2);
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void jList_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_cliente();
    }//GEN-LAST:event_jList_clienteMouseReleased

    private void jList_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_cliente();
            seleccionar_check();
        }
    }//GEN-LAST:event_jList_clienteKeyPressed

    private void txtbucarCliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtbucarCliente_telefono.grabFocus();
//        }
    }//GEN-LAST:event_txtbucarCliente_nombreKeyPressed

    private void txtbucarCliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucarCliente_nombre, jList_cliente, "cliente", "nombre", clie.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_nombreKeyReleased

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_terminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_terminadoActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCestado_terminadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void jCtipo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtipo_clienteActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCtipo_clienteActionPerformed

    private void jCtipo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtipo_funcionarioActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCtipo_funcionarioActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        reestableser();
    }//GEN-LAST:event_btnresetActionPerformed

    private void jCgrupo_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCgrupo_0ActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCgrupo_0ActionPerformed

    private void jCgrupo_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCgrupo_1ActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCgrupo_1ActionPerformed

    private void jCdeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCdeliveryActionPerformed
        // TODO add your handling code here:
        seleccionar_check();
    }//GEN-LAST:event_jCdeliveryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnreset;
    private javax.swing.JCheckBox jCdelivery;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JCheckBox jCestado_terminado;
    private javax.swing.JCheckBox jCgrupo_0;
    private javax.swing.JCheckBox jCgrupo_1;
    private javax.swing.JCheckBox jCtipo_cliente;
    private javax.swing.JCheckBox jCtipo_funcionario;
    private javax.swing.JFormattedTextField jFcant_fila;
    private javax.swing.JFormattedTextField jFtotal_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList_cliente;
    private javax.swing.JPanel panel_buscarcliente;
    private javax.swing.JPanel panel_estado;
    private javax.swing.JPanel panel_fecha;
    private javax.swing.JPanel panel_grupo;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JPanel panel_tipo;
    private javax.swing.JTextField txtbucarCliente_nombre;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
