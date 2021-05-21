/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Digno
 */
public class FrmCliente extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenJtable evejt = new EvenJtable();
    Connection conn = ConnPostgres.getConnPosgres();
    cliente clie = new cliente();
    BO_cliente cBO = new BO_cliente();
    DAO_cliente cdao = new DAO_cliente();
    zona_delivery zona = new zona_delivery();
    DAO_zona_delivery zdao = new DAO_zona_delivery();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    cla_color_pelete clacolor= new cla_color_pelete();
    /**
     * Creates new form FrmZonaDelivery
     */
    private void abrir_formulario_cliente() {
        this.setTitle("CLIENTE");
        evetbl.centrar_formulario(this);
        reestableser_cliente();
        color_formulario();
        cdao.actualizar_tabla_cliente(conn, tblpro_cliente);
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
    }
    void color_formulario(){
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
        panel_tabla.setBackground(clacolor.getColor_insertar_secundario());
        panel_filtro.setBackground(clacolor.getColor_insertar_secundario());
        panel_fecha.setBackground(clacolor.getColor_base());
        panel_buscar.setBackground(clacolor.getColor_insertar_primario());
    }
    private boolean validar_guardar_cliente() {
        txtfecha_inicio.setText(evefec.getString_formato_fecha());
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtzona, "DEBE CARGAR UNA ZONA")) {
            return false;
        }
        if (txtfecha_nacimiento.getText().trim().length() == 0) {
            txtfecha_nacimiento.setText(evefec.getString_formato_fecha());
            clie.setC7fecha_cumple(evefec.getString_formato_fecha());
//            return false;
        }
        return true;
    }

    private String tipo_cliente() {
        String tipo = "cliente";
        if (jRtipo_cliente.isSelected()) {
            tipo = "cliente";
        }
        if (jRtipo_funcionario.isSelected()) {
            tipo = "funcionario";
        }
        return tipo;
    }

    private void boton_guardar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC2fecha_inicio("now");
            clie.setC3nombre(txtnombre.getText());
            clie.setC4direccion(txtdireccion.getText());
            clie.setC5telefono(txttelefono.getText());
            clie.setC6ruc(txtruc.getText());
            clie.setC7fecha_cumple(txtfecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            cBO.insertar_cliente(conn, clie, tblpro_cliente,true);
            reestableser_cliente();
        }
    }

    private void boton_editar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC1idcliente(Integer.parseInt(txtidcliente.getText()));
            clie.setC2fecha_inicio(txtfecha_inicio.getText());
            clie.setC3nombre(txtnombre.getText());
            clie.setC4direccion(txtdireccion.getText());
            clie.setC5telefono(txttelefono.getText());
            clie.setC6ruc(txtruc.getText());
            clie.setC7fecha_cumple(txtfecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            cBO.update_cliente(clie, tblpro_cliente);
        }
    }

    private void seleccionar_tabla_cliente() {
        int idcliente = evejt.getInt_select_id(tblpro_cliente);
        cdao.cargar_cliente(conn, clie, idcliente);
        txtidcliente.setText(String.valueOf(clie.getC1idcliente()));
        txtfecha_inicio.setText(clie.getC2fecha_inicio());
        txtnombre.setText(clie.getC3nombre());
        txtdireccion.setText(clie.getC4direccion());
        txttelefono.setText(clie.getC5telefono());
        txtruc.setText(clie.getC6ruc());
        txtfecha_nacimiento.setText(clie.getC7fecha_cumple());
        if (clie.getC8tipo().equals("cliente")) {
            jRtipo_cliente.setSelected(true);
        }
        if (clie.getC8tipo().equals("funcionario")) {
            jRtipo_funcionario.setSelected(true);
        }
        cargar_tabla_venta(idcliente);
        sumar_monto_venta_cliente(idcliente);
        txtzona.setText(clie.getC10zona());
        txtdelivery.setText(clie.getC11delivery());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser_cliente() {
        jLzona.setVisible(false);
        jRtipo_cliente.setSelected(true);
        txtidcliente.setText(null);
        txtnombre.setText(null);
        txtfecha_inicio.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtruc.setText(null);
        txtfecha_nacimiento.setText(null);
        txtzona.setText(null);
        txtdelivery.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void cargar_zona_cliente() {
        int idzona = eveconn.getInt_seleccionar_JLista(conn, txtzona, jLzona, zona.getTabla(), zona.getNombretabla(), zona.getIdtabla());
        clie.setC9fk_idzona_delivery(idzona);
        zdao.cargar_zona_delivery(zona, idzona);
        txtdelivery.setText(String.valueOf(zona.getDelivery()));
    }

    private void boton_nuevo_cliente() {
        reestableser_cliente();
    }

    private String filtro_fecha() {
        String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
        String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
        txtfecha_desde.setText(fecdesde);
        txtfecha_hasta.setText(fechasta);
        String filtro_fecha = " and date(v.fecha_inicio)>='" + fecdesde + "' \n"
                + "and date(v.fecha_inicio)<='" + fechasta + "'   ";
        return filtro_fecha;
    }

    void cargar_tabla_venta(int idcliente) {
        String sql = "select v.idventa,to_char(v.fecha_inicio,'yyyy-MM-dd HH24:mm') as fecha,\n"
                + "('('||iv.cantidad||')-'||iv.descripcion) as cant_producto,\n"
                + "TRIM(to_char((iv.cantidad*iv.precio_venta),'999G999G999')) as monto\n"
                + "from venta v,item_venta iv\n"
                + "where v.fk_idcliente=" + idcliente
                + " and v.idventa=iv.fk_idventa\n"
                + "and iv.tipo='P' " + filtro_fecha()
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tblventa);
        int Ancho[] = {10, 22, 56, 12};
        evejt.setAnchoColumnaJtable(tblventa, Ancho);
    }

    void sumar_monto_venta_cliente(int idcliente) {
        String titulo = "sumar_monto_venta_cliente";
        String sql = "select (sum(iv.cantidad*iv.precio_venta)) as monto\n"
                + "from venta v,item_venta iv\n"
                + "where v.fk_idcliente=" + idcliente
                + " and v.idventa=iv.fk_idventa\n"
                + "and iv.tipo='P' " + filtro_fecha()
                + " ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            if (rs.next()) {
                double monto = rs.getDouble("monto");
                jFsuma_monto.setValue(monto);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public FrmCliente() {
        initComponents();
        abrir_formulario_cliente();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_tipo = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        panel_insert = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jRtipo_cliente = new javax.swing.JRadioButton();
        jRtipo_funcionario = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdireccion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtidcliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtfecha_inicio = new javax.swing.JTextField();
        lblfecnac = new javax.swing.JLabel();
        txtfecha_nacimiento = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLzona = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        txtzona = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtdelivery = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpro_cliente = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_telefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtbuscar_ruc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        panel_fecha = new javax.swing.JPanel();
        panel_filtro = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        panel_buscar = new javax.swing.JPanel();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        btnbuscar_fecha = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jFsuma_monto = new javax.swing.JFormattedTextField();

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

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Tipo:");

        gru_tipo.add(jRtipo_cliente);
        jRtipo_cliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_cliente.setSelected(true);
        jRtipo_cliente.setText("CLIENTE");
        jRtipo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_clienteActionPerformed(evt);
            }
        });

        gru_tipo.add(jRtipo_funcionario);
        jRtipo_funcionario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_funcionario.setText("FUNCIONARIO");
        jRtipo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_funcionarioActionPerformed(evt);
            }
        });

        txtdireccion.setColumns(20);
        txtdireccion.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtdireccion.setRows(5);
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtdireccion);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Direccion:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Ruc:");

        txtruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Telefono:");

        txttelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("ID:");

        txtidcliente.setEditable(false);
        txtidcliente.setBackground(new java.awt.Color(204, 204, 204));
        txtidcliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Fecha Inicio:");

        txtfecha_inicio.setEditable(false);
        txtfecha_inicio.setBackground(new java.awt.Color(204, 204, 204));
        txtfecha_inicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblfecnac.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblfecnac.setText("Fec. Nac.:");

        txtfecha_nacimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("año-mes-dia");

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLzona.setBackground(new java.awt.Color(204, 204, 255));
        jLzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLzona.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jLzona.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jLzona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLzonaMouseReleased(evt);
            }
        });
        jLzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLzonaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jLzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 40, 220, 100));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("ZONA:");
        jLayeredPane1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, -1));

        txtzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtzonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtzonaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 13, 210, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("DELIVERY:");
        jLayeredPane1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        txtdelivery.setEditable(false);
        txtdelivery.setBackground(new java.awt.Color(204, 204, 204));
        txtdelivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLayeredPane1.add(txtdelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 147, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, -1, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane1.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRtipo_cliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRtipo_funcionario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblfecnac)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_insertLayout.createSequentialGroup()
                                    .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_insertLayout.createSequentialGroup()
                                            .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_insertLayout.createSequentialGroup()
                                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtnombre)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRtipo_cliente)
                    .addComponent(jRtipo_funcionario)
                    .addComponent(lblfecnac)
                    .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
        );

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblpro_cliente.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblpro_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpro_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpro_clienteMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblpro_cliente);

        jLabel6.setText("NOMBRE:");

        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        txtbuscar_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_telefonoKeyReleased(evt);
            }
        });

        jLabel9.setText("TELEFONO:");

        txtbuscar_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyReleased(evt);
            }
        });

        jLabel10.setText("RUC:");

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                    .addGroup(panel_tablaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tablaLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tablaLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tablaLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CREAR CLIENTE", jPanel3);

        panel_filtro.setBackground(new java.awt.Color(51, 51, 255));
        panel_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA VENTA"));

        tblventa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblventa);

        javax.swing.GroupLayout panel_filtroLayout = new javax.swing.GroupLayout(panel_filtro);
        panel_filtro.setLayout(panel_filtroLayout);
        panel_filtroLayout.setHorizontalGroup(
            panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        panel_filtroLayout.setVerticalGroup(
            panel_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        panel_buscar.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO FECHA"));

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

        btnbuscar_fecha.setText("BUSCAR");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_buscarLayout = new javax.swing.GroupLayout(panel_buscar);
        panel_buscar.setLayout(panel_buscarLayout);
        panel_buscarLayout.setHorizontalGroup(
            panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(panel_buscarLayout.createSequentialGroup()
                        .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_buscarLayout.createSequentialGroup()
                                .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_fecha))
                            .addComponent(jLabel15))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panel_buscarLayout.setVerticalGroup(
            panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_buscarLayout.createSequentialGroup()
                .addGroup(panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_fecha))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("SUMA MONTO:");

        jFsuma_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_monto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout panel_fechaLayout = new javax.swing.GroupLayout(panel_fecha);
        panel_fecha.setLayout(panel_fechaLayout);
        panel_fechaLayout.setHorizontalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addComponent(panel_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 158, Short.MAX_VALUE))
        );
        panel_fechaLayout.setVerticalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_filtro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addComponent(panel_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FILTRO DE VENTA", panel_fecha);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_cliente();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        cdao.ancho_tabla_cliente(tblpro_cliente);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblpro_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpro_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_cliente();
    }//GEN-LAST:event_tblpro_clienteMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_cliente();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_cliente();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void jRtipo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_clienteActionPerformed
        // TODO add your handling code here:
//        jCdelivery.setSelected(true);
    }//GEN-LAST:event_jRtipo_clienteActionPerformed

    private void jRtipo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_funcionarioActionPerformed
        // TODO add your handling code here:
//        jCdelivery.setSelected(false);
    }//GEN-LAST:event_jRtipo_funcionarioActionPerformed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtruc, txttelefono);
    }//GEN-LAST:event_txtrucKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter2(evt, txttelefono, txtdireccion);
        evejtf.saltar_campo_enter(evt, txttelefono, txtfecha_nacimiento);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtfecha_nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyReleased
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtfecha_nacimiento);
    }//GEN-LAST:event_txtfecha_nacimientoKeyReleased

    private void txtfecha_nacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyTyped
        // TODO add your handling code here:
//        fo.soloFechaText(evt);
    }//GEN-LAST:event_txtfecha_nacimientoKeyTyped

    private void txtzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtzona, jLzona, zona.getTabla(), zona.getNombretabla(), zona.getNombretabla(), 4);
    }//GEN-LAST:event_txtzonaKeyReleased

    private void jLzonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzonaMouseReleased
        // TODO add your handling code here:
        cargar_zona_cliente();
    }//GEN-LAST:event_jLzonaMouseReleased

    private void txtzonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jLzona);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtzona.setBackground(Color.WHITE);
            txtdireccion.setBackground(Color.YELLOW);
            txtdireccion.grabFocus();
        }
    }//GEN-LAST:event_txtzonaKeyPressed

    private void jLzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzonaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_zona_cliente();
        }
    }//GEN-LAST:event_jLzonaKeyReleased

    private void txtfecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_nacimiento.setText(evefec.getString_validar_fecha(txtfecha_nacimiento.getText()));
            evejtf.saltar_campo_enter(evt, txtfecha_nacimiento, txtzona);
        }

    }//GEN-LAST:event_txtfecha_nacimientoKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_nombre, 1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_telefonoKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_telefono, 2);
    }//GEN-LAST:event_txtbuscar_telefonoKeyReleased

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_ruc, 3);
    }//GEN-LAST:event_txtbuscar_rucKeyReleased

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

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblpro_cliente)) {
            int idcliente = evejt.getInt_select_id(tblpro_cliente);
//            seleccionar_tabla_cliente();
            cargar_tabla_venta(idcliente);
            sumar_monto_venta_cliente(idcliente);
        }
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JFormattedTextField jFsuma_monto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jLzona;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRtipo_cliente;
    private javax.swing.JRadioButton jRtipo_funcionario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblfecnac;
    private javax.swing.JPanel panel_buscar;
    private javax.swing.JPanel panel_fecha;
    private javax.swing.JPanel panel_filtro;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblpro_cliente;
    private javax.swing.JTable tblventa;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_ruc;
    private javax.swing.JTextField txtbuscar_telefono;
    public static javax.swing.JTextField txtdelivery;
    private javax.swing.JTextArea txtdireccion;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    private javax.swing.JTextField txtfecha_inicio;
    private javax.swing.JTextField txtfecha_nacimiento;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txttelefono;
    public static javax.swing.JTextField txtzona;
    // End of variables declaration//GEN-END:variables
}
