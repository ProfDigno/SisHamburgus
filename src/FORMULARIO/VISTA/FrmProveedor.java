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
public class FrmProveedor extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenJtable evejt = new EvenJtable();
    Connection conn = ConnPostgres.getConnPosgres();
    proveedor prov = new proveedor();
    BO_proveedor cBO = new BO_proveedor();
    DAO_proveedor cdao = new DAO_proveedor();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    cla_color_pelete clacolor= new cla_color_pelete();
    /**
     * Creates new form FrmZonaDelivery
     */
    private void abrir_formulario_cliente() {
        this.setTitle("PROVEEDOR");
        evetbl.centrar_formulario(this);
        reestableser_proveedor();
        color_formulario();
        cdao.actualizar_tabla_proveedor(conn, tblproveedor);
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
    private boolean validar_guardar_proveedor() {
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
        return true;
    }

    private void boton_guardar_proveedor() {
        if (validar_guardar_proveedor()) {
            prov.setC2fecha_inicio("now");
            prov.setC3nombre(txtnombre.getText());
            prov.setC4direccion(txtdireccion.getText());
            prov.setC5telefono(txttelefono.getText());
            prov.setC6ruc(txtruc.getText());
            cBO.insertar_proveedor(prov, tblproveedor);
            reestableser_proveedor();
        }
    }

    private void boton_editar_proveedor() {
        if (validar_guardar_proveedor()) {
            prov.setC1idproveedor(Integer.parseInt(txtidcliente.getText()));
            prov.setC2fecha_inicio(txtfecha_inicio.getText());
            prov.setC3nombre(txtnombre.getText());
            prov.setC4direccion(txtdireccion.getText());
            prov.setC5telefono(txttelefono.getText());
            prov.setC6ruc(txtruc.getText());
            cBO.update_proveedor(prov, tblproveedor);
        }
    }

    private void seleccionar_tabla_proveedor() {
        int idcliente = evejt.getInt_select_id(tblproveedor);
        cdao.cargar_proveedor(conn, prov, idcliente);
        txtidcliente.setText(String.valueOf(prov.getC1idproveedor()));
        txtfecha_inicio.setText(prov.getC2fecha_inicio());
        txtnombre.setText(prov.getC3nombre());
        txtdireccion.setText(prov.getC4direccion());
        txttelefono.setText(prov.getC5telefono());
        txtruc.setText(prov.getC6ruc());
//        cargar_tabla_venta(idcliente);
//        sumar_monto_venta_cliente(idcliente);
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser_proveedor() {
        txtidcliente.setText(null);
        txtnombre.setText(null);
        txtfecha_inicio.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtruc.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo_cliente() {
        reestableser_proveedor();
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
        eveconn.Select_cargar_jtable(conn, sql, tblcompra);
        int Ancho[] = {10, 22, 56, 12};
        evejt.setAnchoColumnaJtable(tblcompra, Ancho);
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

    public FrmProveedor() {
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
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproveedor = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_telefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtbuscar_ruc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        panel_fecha = new javax.swing.JPanel();
        panel_filtro = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcompra = new javax.swing.JTable();
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

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                                .addComponent(txtfecha_inicio))
                            .addGroup(panel_insertLayout.createSequentialGroup()
                                .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtnombre)))
                    .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_insertLayout.createSequentialGroup()
                            .addComponent(btnnuevo)
                            .addGap(9, 9, 9)
                            .addComponent(btnguardar)
                            .addGap(9, 9, 9)
                            .addComponent(btneditar)
                            .addGap(9, 9, 9)
                            .addComponent(btndeletar))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btndeletar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblproveedor.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblproveedor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproveedorMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproveedor);

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
                .addGap(5, 5, 5)
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
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
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

        jTabbedPane1.addTab("CREAR PROVEEDOR", jPanel3);

        panel_filtro.setBackground(new java.awt.Color(51, 51, 255));
        panel_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA VENTA"));

        tblcompra.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblcompra);

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

        jTabbedPane1.addTab("FILTRO DE COMPRA", panel_fecha);

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
        boton_guardar_proveedor();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        cdao.ancho_tabla_proveedor(tblproveedor);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblproveedorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproveedorMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_proveedor();
    }//GEN-LAST:event_tblproveedorMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_proveedor();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_cliente();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtruc, txttelefono);
    }//GEN-LAST:event_txtrucKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter2(evt, txttelefono, txtdireccion);
//        evejtf.saltar_campo_enter(evt, txttelefono, txtfecha_nacimiento);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
//        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_nombre, 1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_telefonoKeyReleased
        // TODO add your handling code here:
//        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_telefono, 2);
    }//GEN-LAST:event_txtbuscar_telefonoKeyReleased

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
//        cdao.buscar_tabla_cliente(conn, tblpro_cliente, txtbuscar_ruc, 3);
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
        if (!evejt.getBoolean_validar_select(tblproveedor)) {
            int idcliente = evejt.getInt_select_id(tblproveedor);
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_buscar;
    private javax.swing.JPanel panel_fecha;
    private javax.swing.JPanel panel_filtro;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblcompra;
    private javax.swing.JTable tblproveedor;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_ruc;
    private javax.swing.JTextField txtbuscar_telefono;
    private javax.swing.JTextArea txtdireccion;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    private javax.swing.JTextField txtfecha_inicio;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
