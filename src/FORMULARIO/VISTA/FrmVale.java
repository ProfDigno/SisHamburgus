/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.PosImprimir_Gasto;
import IMPRESORA_POS.PosImprimir_Vale;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmVale extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    vale val = new vale();
    BO_vale vBO = new BO_vale();
    DAO_vale vdao = new DAO_vale();
    caja_detalle caja = new caja_detalle();
    usuario usu = new usuario();
    EvenUtil eveut = new EvenUtil();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenJTextField evejtf = new EvenJTextField();
    EvenJtable evejta = new EvenJtable();
    EvenConexion eveconn = new EvenConexion();
    EvenFecha evefec = new EvenFecha();
//    EvenDatosPc evepc = new EvenDatosPc();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    PosImprimir_Vale posval = new PosImprimir_Vale();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor= new cla_color_pelete();
    private int fk_idcliente;
    private int idvale;
    private double monto_vale;
    private String indice_vale;
    private String indice;

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("VALE");
        evetbl.centrar_formulario(this);
        reestableser();
        color_formulario();
    }
    void color_formulario(){
        panel_tabla_vale.setBackground(clacolor.getColor_tabla());
        panel_insertar_vale.setBackground(clacolor.getColor_insertar_primario());
    }
    void actualizar_vale(int tipo) {
        String ocultarAnulado = "";
        String filtro = "";
        String fecha = "";
        String orden = " order by v.fecha_emision desc";
        if (jCocultar_anulado.isSelected()) {
            ocultarAnulado = " and v.estado='EMITIDO' ";
        }
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
            String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
            String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
            txtfecha_desde.setText(fecdesde);
            txtfecha_hasta.setText(fechasta);
            fecha = " and date(v.fecha_emision)>=date('" + fecdesde + "') and date(v.fecha_emision)<=date('" + fechasta + "')";
        }
        if (tipo == 2) {
            filtro = fecha + ocultarAnulado;
        }
        if (tipo == 3) {
            if ((txtbuscar_descripcion.getText().trim().length() > 0)) {
                String descrip = txtbuscar_descripcion.getText();
                filtro = " and v.descripcion ilike '%" + descrip + "%' " + fecha + ocultarAnulado;
            }
        }
        if (tipo == 4) {
            if ((txtbuscar_nombrecliente.getText().trim().length() > 0)) {
                String gtipo = txtbuscar_nombrecliente.getText();
                filtro = " and cl.nombre ilike '%" + gtipo + "%' " + fecha + ocultarAnulado;
            }
        }
        vdao.actualizar_tabla_vale(conn, tblvale, filtro, orden);
        double suma_monto = vdao.sumar_monto_vale(conn, filtro);
        jFsuma_monto.setValue(suma_monto);
        double suma_cantidad = vdao.sumar_cantidad_vale(conn, filtro);
        jFsuma_cantidad.setValue(suma_cantidad);
    }

    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_cliente, "DEBE CARGAR UN VALE TIPO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdescripcion, "SE DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_vale, "SE DEBE CARGAR UN MONTO")) {
            return false;
        } 
//        else {
//            monto_vale = Double.parseDouble(txtmonto_vale.getText());
//        }
        if (fk_idcliente == 0) {
            JOptionPane.showMessageDialog(this, "NO SE CARGO CORRECTAMENTE", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtbuscar_cliente.setText(null);
            txtbuscar_cliente.grabFocus();
            return false;
        }
        return true;
    }

    void cargar_datos_caja() {
        caja.setC2fecha_emision(txtfecha_emision.getText());
        caja.setC3descripcion("(VALE) id:" + idvale + " Funcionario:" + txtbuscar_cliente.getText());
        caja.setC4monto_venta(0);
        caja.setC5monto_delivery(0);
        caja.setC6monto_gasto(0);
        caja.setC7monto_compra(0);
        caja.setC8monto_vale(evejtf.getDouble_format_nro_entero1(txtmonto_vale));
        caja.setC9id_origen(idvale);
        caja.setC10tabla_origen("VALE");
        caja.setC11fk_idusuario(usu.getGlobal_idusuario());
        caja.setC12indice(indice_vale);
        caja.setC13equipo1(evepc.getString_nombre_pc());
        caja.setC15monto_caja1(0);
        caja.setC16monto_cierre(0);
    }

    void cargar_datos_vale() {
        val.setFecha_emision(txtfecha_emision.getText());
        val.setDescripcion(txtdescripcion.getText());
        val.setEquipo(evepc.getString_nombre_pc());
        val.setEstado("EMITIDO");
        val.setMonto_vale(evejtf.getDouble_format_nro_entero1(txtmonto_vale));
        val.setFk_idcliente(fk_idcliente);
        val.setFk_idusuario(usu.getGlobal_idusuario());
        val.setIndice(indice_vale);
    }

    void boton_guardar() {
        if (validar_guardar()) {
            cargar_datos_vale();
            cargar_datos_caja();
            if (vBO.getboolean_insertar_vale(val, caja)) {
                posval.boton_imprimir_pos_VALE(conn, idvale);
                reestableser();

            }
        }
    }

    void cargar_datos_editar_caja() {
        caja.setC2fecha_emision(txtfecha_emision.getText());
        caja.setC3descripcion("(VALE) id:" + idvale + " Funcionario:" + txtbuscar_cliente.getText());
        caja.setC4monto_venta(0);
        caja.setC5monto_delivery(0);
        caja.setC6monto_gasto(0);
        caja.setC7monto_compra(0);
        caja.setC8monto_vale(evejtf.getDouble_format_nro_entero1(txtmonto_vale));
        caja.setC9id_origen(idvale);
        caja.setC10tabla_origen("VALE");
        caja.setC12indice(indice);
    }

    void cargar_datos_editar_vale() {
        val.setIdvale(idvale);
        val.setFecha_emision(txtfecha_emision.getText());
        val.setDescripcion(txtdescripcion.getText());
        val.setMonto_vale(evejtf.getDouble_format_nro_entero1(txtmonto_vale));
        val.setFk_idcliente(fk_idcliente);
        val.setFk_idusuario(usu.getGlobal_idusuario());
        val.setIndice(indice);
    }

    void boton_editar() {
        if (!evejta.getBoolean_validar_select(tblvale)) {
            if (validar_guardar()) {
                cargar_datos_editar_vale();
                cargar_datos_editar_caja();
                vBO.update_vale(val, caja);
                actualizar_vale(2);
            }
        }
    }

    void boton_anular() {
        if (!evejta.getBoolean_validar_select(tblvale)) {
            val.setEstado("ANULADO");
            val.setIdvale(idvale);
            val.setIndice(indice);
            caja.setC9id_origen(idvale);
            caja.setC10tabla_origen("VALE");
            caja.setC12indice(indice);
            vBO.update_vale_anular(val, caja);
            reestableser();
        }
    }

    void boton_imprimir() {
        if (!evejta.getBoolean_validar_select(tblvale)) {
            posval.boton_imprimir_pos_VALE(conn, idvale);
        }
    }

    void seleccionar_tabla() {
        vdao.cargar_vale(val, tblvale);
        txtid.setText(String.valueOf(val.getIdvale()));
        idvale = val.getIdvale();
        indice = val.getIndice();
        txtbuscar_cliente.setText(val.getCliente());
        fk_idcliente = val.getFk_idcliente();
        txtfecha_emision.setText(val.getFecha_emision());
        txtdescripcion.setText(val.getDescripcion());
        txtmonto_vale.setText(evejtf.getString_format_nro_decimal(val.getMonto_vale()));
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
        btnanular.setEnabled(true);
        btnimprimir.setEnabled(true);
    }

    void reestableser() {
        idvale = eveconn.getInt_ultimoID_mas_uno(conn, val.getTabla(), val.getIdtabla());
        indice_vale = eveut.getString_crear_indice();
        fk_idcliente = 0;
        jList_cliente.setVisible(false);
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        txtfecha_emision.setText(evefec.getString_formato_fecha_hora());
        actualizar_vale(2);
        txtid.setText(null);
        txtbuscar_cliente.setText(null);
        txtdescripcion.setText("VALE");
        txtmonto_vale.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btnanular.setEnabled(false);
        btnimprimir.setEnabled(false);
        txtbuscar_cliente.grabFocus();
    }

    void boton_nuevo() {
        reestableser();
    }

    void cargar_idvale_tipo() {
        fk_idcliente = eveconn.getInt_seleccionar_JLista(conn, txtbuscar_cliente, jList_cliente, "cliente", "nombre", "idcliente");
        jList_cliente.setVisible(false);
        txtdescripcion.grabFocus();
    }

    public FrmVale() {
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

        panel_insertar_vale = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_cliente = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        btngasto_tipo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtmonto_vale = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtfecha_emision = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtbuscar_cliente = new javax.swing.JTextField();
        panel_tabla_vale = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblvale = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnbuscar_fecha = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        txtbuscar_descripcion = new javax.swing.JTextField();
        txtbuscar_nombrecliente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jFsuma_monto = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jFsuma_cantidad = new javax.swing.JFormattedTextField();
        jCocultar_anulado = new javax.swing.JCheckBox();
        btnimprimir = new javax.swing.JButton();

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

        panel_insertar_vale.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
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

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_cliente.setBackground(new java.awt.Color(204, 204, 255));
        jList_cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_cliente.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
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
        jLayeredPane1.add(jList_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 260, 100));

        txtdescripcion.setColumns(20);
        txtdescripcion.setRows(5);
        txtdescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdescripcionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtdescripcion);

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 320, -1));

        jLabel3.setText("Descripcion:");
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btngasto_tipo.setText("CLIENTE/FUNCIONARIO");
        btngasto_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngasto_tipoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("MONTO:");

        txtmonto_vale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtmonto_vale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_valeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_valeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_valeKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("FECHA:");

        txtfecha_emision.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("FUNCIO:");

        txtbuscar_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_clienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_clienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_valeLayout = new javax.swing.GroupLayout(panel_insertar_vale);
        panel_insertar_vale.setLayout(panel_insertar_valeLayout);
        panel_insertar_valeLayout.setHorizontalGroup(
            panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_valeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btngasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertar_valeLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(11, 11, 11)
                        .addComponent(txtmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_valeLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_valeLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_cliente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_valeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLayeredPane1)
                    .addGroup(panel_insertar_valeLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnanular)))
                .addGap(77, 77, 77))
        );
        panel_insertar_valeLayout.setVerticalGroup(
            panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_valeLayout.createSequentialGroup()
                .addGroup(panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtfecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(134, 134, 134)
                .addGroup(panel_insertar_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btnanular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btngasto_tipo)
                .addContainerGap())
        );

        panel_tabla_vale.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblvale.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblvale.setModel(new javax.swing.table.DefaultTableModel(
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
        tblvale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblvaleMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblvale);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("fec. Desde:");

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Fec. Hasta:");

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        jLabel7.setText("año-mes-dia");

        btnbuscar_fecha.setText("BUSCAR FECHA ");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setText("BUSCAR DESCRIPCION:");

        txtbuscar_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_descripcionKeyReleased(evt);
            }
        });

        txtbuscar_nombrecliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreclienteKeyReleased(evt);
            }
        });

        jLabel9.setText("BUSCAR TIPO:");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel10.setText("SUMA MONTO:");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jFsuma_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_monto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel11.setText("SUMA CANTIDAD:");

        jFsuma_cantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFsuma_cantidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jCocultar_anulado.setText("OCULTAR ANULADO");
        jCocultar_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCocultar_anuladoActionPerformed(evt);
            }
        });

        btnimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/imprimir.png"))); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnimprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_valeLayout = new javax.swing.GroupLayout(panel_tabla_vale);
        panel_tabla_vale.setLayout(panel_tabla_valeLayout);
        panel_tabla_valeLayout.setHorizontalGroup(
            panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_valeLayout.createSequentialGroup()
                .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtfecha_hasta))
                            .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(txtbuscar_descripcion)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtbuscar_nombrecliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCocultar_anulado)
                            .addComponent(btnimprimir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFsuma_monto)
                            .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                                .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panel_tabla_valeLayout.setVerticalGroup(
            panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_fecha))
                            .addComponent(jSeparator1)))
                    .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_tabla_valeLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(1, 1, 1)
                                .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_valeLayout.createSequentialGroup()
                                .addComponent(btnimprimir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCocultar_anulado)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_insertar_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_tabla_vale, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_insertar_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        vdao.ancho_tabla_vale(tblvale);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblvaleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvaleMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblvaleMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtbuscar_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_clienteKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        evejtf.seleccionar_lista(evt, jList_cliente);
    }//GEN-LAST:event_txtbuscar_clienteKeyPressed

    private void txtbuscar_clienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_clienteKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_condicion_Jlista(conn, txtbuscar_cliente, jList_cliente, "cliente", "nombre", "nombre", "and tipo='funcionario'");
    }//GEN-LAST:event_txtbuscar_clienteKeyReleased

    private void jList_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_idvale_tipo();
        }
    }//GEN-LAST:event_jList_clienteKeyPressed

    private void jList_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_clienteMouseReleased
        // TODO add your handling code here:
        cargar_idvale_tipo();
    }//GEN-LAST:event_jList_clienteMouseReleased

    private void txtdescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescripcionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtmonto_vale.grabFocus();
        }
    }//GEN-LAST:event_txtdescripcionKeyPressed

    private void txtmonto_valeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_valeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_guardar();
        }
    }//GEN-LAST:event_txtmonto_valeKeyPressed

    private void txtmonto_valeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_valeKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_valeKeyTyped

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        actualizar_vale(2);
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void txtbuscar_descripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_descripcionKeyReleased
        // TODO add your handling code here:
        actualizar_vale(3);
    }//GEN-LAST:event_txtbuscar_descripcionKeyReleased

    private void txtbuscar_nombreclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreclienteKeyReleased
        // TODO add your handling code here:
        actualizar_vale(4);
    }//GEN-LAST:event_txtbuscar_nombreclienteKeyReleased

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_vale(2);
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void jCocultar_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCocultar_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_vale(2);
    }//GEN-LAST:event_jCocultar_anuladoActionPerformed

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular();
    }//GEN-LAST:event_btnanularActionPerformed

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void btngasto_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngasto_tipoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_btngasto_tipoActionPerformed

    private void txtmonto_valeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_valeKeyReleased
        // TODO add your handling code here:
//        evejtf.getDouble_format_nro_entero(txtmonto_vale);
    }//GEN-LAST:event_txtmonto_valeKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btngasto_tipo;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JCheckBox jCocultar_anulado;
    private javax.swing.JFormattedTextField jFsuma_cantidad;
    private javax.swing.JFormattedTextField jFsuma_monto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_cliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel panel_insertar_vale;
    private javax.swing.JPanel panel_tabla_vale;
    private javax.swing.JTable tblvale;
    private javax.swing.JTextField txtbuscar_cliente;
    private javax.swing.JTextField txtbuscar_descripcion;
    private javax.swing.JTextField txtbuscar_nombrecliente;
    private javax.swing.JTextArea txtdescripcion;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_emision;
    private javax.swing.JTextField txtfecha_hasta;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_vale;
    // End of variables declaration//GEN-END:variables
}
