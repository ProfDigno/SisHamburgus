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
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.BO_factura;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.item_factura;
import FORMULARIO.ENTIDAD.venta;
import IMPRESORA_POS.PosImprimir_Venta;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmFactura extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenNumero_a_Letra nl = new EvenNumero_a_Letra();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    cliente clie = new cliente();
    DAO_cliente cdao = new DAO_cliente();
    venta ven = new venta();
    factura fac = new factura();
    DAO_factura fdao = new DAO_factura();
    BO_factura fBO = new BO_factura();
    item_factura item = new item_factura();
    Connection conn = ConnPostgres.getConnPosgres();
    private int fk_idcliente;
    DefaultTableModel model_itemf = new DefaultTableModel();
    cla_color_pelete clacolor= new cla_color_pelete();
    private String Stotalmonto;
    private String totalletra;
    private String Siva;
    private int idfactura;
    private double Diva;
    private double Dtotalmonto;

    /**
     * Creates new form FrmFactura
     */
    void abrir_formulario() {
        this.setTitle("FACTURA");
        evetbl.centrar_formulario(this);
        jList_cliente.setVisible(false);
        reestableser();
        crear_item_producto();
        color_formulario();
        if (fac.isFactura_cargada()) {
            seleccionar_cargar_cliente(4);
            cargar_itemfactura_deventa();
        }
    }
    void color_formulario(){
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
        panel_factura.setBackground(clacolor.getColor_insertar_secundario());
        panel_tabla.setBackground(clacolor.getColor_insertar_secundario());
        panel_base.setBackground(clacolor.getColor_insertar_primario());
    }
    void reestableser() {
        ultimo_idfactura();
        limpiar_cliente();
        txtfecha.setText(evefec.getString_formato_fecha_hora());
        evejt.limpiar_tabla_datos(model_itemf);
        fdao.actualizar_tabla_factura(conn, tblfactura, "");
        txttotalletra.setText("0");
        jFmonto_factura.setValue(0);
        jFmonto_iva.setValue(0);
    }

    void crear_item_producto() {
        String dato[] = {"id", "descripcion", "precio", "C", "total"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    void ancho_item_producto() {
        int Ancho[] = {10, 54, 15, 6, 15};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
    }

    void seleccionar_cargar_cliente(int tipo) {
        if (tipo == 1) {
            fk_idcliente = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_cliente, "cliente", clie.getCliente_concat(), "idcliente");
        }
        if (tipo == 2) {
            fk_idcliente = (eveconn.getInt_ultimoID_mas_uno(conn, clie.getTabla(), clie.getIdtabla())) - 1;
        }
        if (tipo == 3) {
//            fk_idcliente = evejt.getInt_select_id(tblbuscar_cliente);
        }
        if (tipo == 4) {
            fk_idcliente = ven.getC12fk_idcliente_estatico();
        }
        System.out.println("idcliente:" + fk_idcliente);
        lblidcliente.setText("id:" + fk_idcliente);
        cdao.cargar_cliente(conn,clie, fk_idcliente);
        txtbucarCliente_nombre.setText(clie.getC3nombre());
        txtbucarCliente_telefono.setText(clie.getC5telefono());
        txtbucarCliente_ruc.setText(clie.getC6ruc());
        txtbucarCliente_direccion.setText(clie.getC4direccion());
    }

    void limpiar_cliente() {
        lblidcliente.setText("id:0");
        fk_idcliente = 1;
        txtbucarCliente_nombre.setText(null);
        txtbucarCliente_telefono.setText(null);
        txtbucarCliente_ruc.setText(null);
        txtbucarCliente_direccion.setText(null);
        txtbucarCliente_telefono.grabFocus();
    }

    void sumarTotalgral(JTable tblitemFactura) {
        double IVA10 = 11;
        double total_item = 0;
        for (int row = 0; row < tblitemFactura.getRowCount(); row++) {
            String subtotal = (tblitemFactura.getModel().getValueAt(row, 4).toString());
            double DOsubtotal = Double.parseDouble(subtotal);
            total_item = total_item + DOsubtotal;
        }
        Diva = (total_item / IVA10);
        Dtotalmonto = (total_item);
        jFmonto_iva.setValue(Diva);
        jFmonto_factura.setValue(Dtotalmonto);
        Stotalmonto = String.valueOf(Dtotalmonto);
        totalletra = nl.Convertir(Stotalmonto, true);
        txttotalletra.setText(totalletra);
    }

    void cargar_itemfactura_deventa() {
        String titulo = "cargar_itemfactura_deventa";
        String sql = "SELECT iditem_venta, descripcion, precio_venta, precio_compra, cantidad, \n"
                + "       tipo, fk_idventa, fk_idproducto,(precio_venta*cantidad) as total\n"
                + "  FROM public.item_venta "
                + "where fk_idventa=" + ven.getC1idventa_estatico();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                int precio = rs.getInt("precio_venta");
                if (precio > 0) {
                    String idproducto = (rs.getString("fk_idproducto"));
                    String nombre = (rs.getString("descripcion"));
                    String cantidad = (rs.getString("cantidad"));
                    String precio_venta = (rs.getString("precio_venta"));
                    String total = (rs.getString("total"));
                    String dato[] = {idproducto, nombre, precio_venta, cantidad, total};
                    evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
                }
            }
            sumarTotalgral(tblitem_producto);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                sumarTotalgral(tblitem_producto);
            }
        }
    }

    boolean validar_guardar_factura() {
        if (evejtf.getBoo_JTextField_vacio(txtnro_factura, "CARGUE UN NUMERO DE FACTURA")) {
            return false;
        }
        return true;
    }

    void ultimo_idfactura() {
        idfactura = eveconn.getInt_ultimoID_mas_uno(conn, fac.getTabla(), fac.getIdtabla());
        txtidfactura.setText(String.valueOf(idfactura));
        txtnro_factura.setText(String.valueOf(idfactura));
    }

    void cargar_datos_factura() {
        fac.setC1idfactura(idfactura);
        fac.setC2nro_factura(Integer.parseInt(txtnro_factura.getText()));
        fac.setC3fecha_emision(txtfecha.getText());
        fac.setC4estado("EMITIDO");
        fac.setC5totalmonto(Dtotalmonto);
        fac.setC6iva(Diva);
        fac.setC7totalletra(totalletra);
        fac.setC8fk_idcliente(fk_idcliente);
    }

    void boton_guardar_factura() {
        if (validar_guardar_factura()) {
            cargar_datos_factura();
            if (fBO.getBoolean_insertar_factura(tblitem_producto, item, fac)) {
                this.dispose();
                fdao.imprimir_factura(conn, idfactura);
            }
        }
    }

    void boton_anular_factura() {
        if (!evejt.getBoolean_validar_select(tblfactura)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR ESTA FACTURA", "ANULAR", "ACEPTAR", "CANCELAR")) {
                int idfactura = evejt.getInt_select_id(tblfactura);
                fac.setC1idfactura(idfactura);
                fac.setC4estado("ANULADO");
                if (fBO.getBoolean_update_anular_factura(fac)) {
                    fdao.actualizar_tabla_factura(conn, tblfactura, "");
                }
            }
        }
    }

    void boton_imprimir_factura() {
        if (!evejt.getBoolean_validar_select(tblfactura)) {
            int idfactura = evejt.getInt_select_id(tblfactura);
            fdao.imprimir_factura(conn, idfactura);
        }
    }

    public FrmFactura() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insert = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_cliente = new javax.swing.JList<>();
        txtbucarCliente_direccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtbucarCliente_nombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucarCliente_telefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtbucarCliente_ruc = new javax.swing.JTextField();
        btnnuevo_cliente = new javax.swing.JButton();
        btnbuscar_cliente = new javax.swing.JButton();
        lblidcliente = new javax.swing.JLabel();
        btnlimpiar_cliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txttotalletra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jFmonto_iva = new javax.swing.JFormattedTextField();
        jFmonto_factura = new javax.swing.JFormattedTextField();
        panel_factura = new javax.swing.JPanel();
        btneliminar_item = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtidfactura = new javax.swing.JTextField();
        txtnro_factura = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnguardar_factura = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtfecha = new javax.swing.JTextField();
        panel_base = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblfactura = new javax.swing.JTable();
        btnanular = new javax.swing.JButton();
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

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_cliente.setBackground(new java.awt.Color(204, 204, 255));
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
        jLayeredPane1.add(jList_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 660, 110));

        txtbucarCliente_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_direccionKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtbucarCliente_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 350, -1));

        jLabel6.setText("DIREC:");
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, -1, -1));

        jLabel3.setText("CLI:");
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtbucarCliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtbucarCliente_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 10, 350, -1));

        jLabel4.setText("TEL.:");
        jLayeredPane1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        txtbucarCliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtbucarCliente_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 140, -1));

        jLabel5.setText("RUC:");
        jLayeredPane1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        txtbucarCliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtbucarCliente_ruc, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 130, -1));

        btnnuevo_cliente.setText("NUEVO CLIENTE");
        btnnuevo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_clienteActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 30));

        btnbuscar_cliente.setText("BUSCAR CLIENTE");
        btnbuscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_clienteActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnbuscar_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, 30));

        lblidcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblidcliente.setText("id");
        jLayeredPane1.add(lblidcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, -1, -1));

        btnlimpiar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/escoba.png"))); // NOI18N
        btnlimpiar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiar_clienteActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnlimpiar_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 40, 30));

        tblitem_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblitem_producto.setRowHeight(25);
        tblitem_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblitem_productoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblitem_producto);

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 700, 250));

        jLabel1.setText("TOTAL LETRA:");

        txttotalletra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("TOTAL IVA:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("TOTAL FACTURA:");

        jFmonto_iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_iva.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFmonto_factura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_factura.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotalletra))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFmonto_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFmonto_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txttotalletra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jFmonto_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        panel_factura.setBackground(new java.awt.Color(102, 102, 255));
        panel_factura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btneliminar_item.setText("ELIMINAR ITEM");
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jLabel8.setText("idfactura:");

        txtidfactura.setEditable(false);
        txtidfactura.setBackground(new java.awt.Color(204, 204, 255));

        txtnro_factura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnro_factura.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setText("Nro Factura:");

        btnguardar_factura.setText("GUARDAR");
        btnguardar_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_facturaActionPerformed(evt);
            }
        });

        jLabel10.setText("Fecha:");

        javax.swing.GroupLayout panel_facturaLayout = new javax.swing.GroupLayout(panel_factura);
        panel_factura.setLayout(panel_facturaLayout);
        panel_facturaLayout.setHorizontalGroup(
            panel_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_facturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnguardar_factura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(txtidfactura)
                    .addComponent(txtnro_factura)
                    .addGroup(panel_facturaLayout.createSequentialGroup()
                        .addGroup(panel_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtfecha))
                .addContainerGap())
        );
        panel_facturaLayout.setVerticalGroup(
            panel_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_facturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtidfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnro_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btneliminar_item)
                .addGap(43, 43, 43)
                .addComponent(btnguardar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("DATOS FACTURA", jPanel1);

        panel_tabla.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("FACTURA"));

        tblfactura.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblfactura);

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
        );

        btnanular.setText("ANULAR");
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        btnimprimir.setText("IMPRIMIR FACTURA");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_baseLayout = new javax.swing.GroupLayout(panel_base);
        panel_base.setLayout(panel_baseLayout);
        panel_baseLayout.setHorizontalGroup(
            panel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_baseLayout.setVerticalGroup(
            panel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_baseLayout.createSequentialGroup()
                .addComponent(panel_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnanular)
                    .addComponent(btnimprimir))
                .addGap(0, 66, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FILTRO FACTURA", panel_base);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_cliente(1);
    }//GEN-LAST:event_jList_clienteMouseReleased

    private void jList_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_cliente(1);
        }
    }//GEN-LAST:event_jList_clienteKeyPressed

    private void txtbucarCliente_direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_direccionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbucarCliente_direccionKeyReleased

    private void txtbucarCliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_telefono.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_nombreKeyPressed

    private void txtbucarCliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucarCliente_nombre, jList_cliente, "cliente", "nombre", clie.getCliente_mostrar(),4);
    }//GEN-LAST:event_txtbucarCliente_nombreKeyReleased

    private void txtbucarCliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_ruc.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyPressed

    private void txtbucarCliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucarCliente_telefono, jList_cliente, "cliente", "telefono", clie.getCliente_mostrar(),4);
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyReleased

    private void txtbucarCliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_nombre.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_rucKeyPressed

    private void txtbucarCliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucarCliente_ruc, jList_cliente, "cliente", "ruc", clie.getCliente_mostrar(),4);
    }//GEN-LAST:event_txtbucarCliente_rucKeyReleased

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
//        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
    }//GEN-LAST:event_btnnuevo_clienteActionPerformed

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
//        boton_buscar_cliente_venta();
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void btnlimpiar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiar_clienteActionPerformed
        // TODO add your handling code here:
        limpiar_cliente();
    }//GEN-LAST:event_btnlimpiar_clienteActionPerformed

    private void tblitem_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_productoMouseReleased
        // TODO add your handling code here:

//        actualizar_tabla_ingrediente();
    }//GEN-LAST:event_tblitem_productoMouseReleased

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        fdao.ancho_tabla_factura(tblfactura);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void btnguardar_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_facturaActionPerformed
        // TODO add your handling code here:
        boton_guardar_factura();
    }//GEN-LAST:event_btnguardar_facturaActionPerformed

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular_factura();
    }//GEN-LAST:event_btnanularActionPerformed

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir_factura();
    }//GEN-LAST:event_btnimprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnguardar_factura;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnlimpiar_cliente;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JFormattedTextField jFmonto_factura;
    private javax.swing.JFormattedTextField jFmonto_iva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblidcliente;
    private javax.swing.JPanel panel_base;
    private javax.swing.JPanel panel_factura;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblfactura;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTextField txtbucarCliente_direccion;
    private javax.swing.JTextField txtbucarCliente_nombre;
    private javax.swing.JTextField txtbucarCliente_ruc;
    private javax.swing.JTextField txtbucarCliente_telefono;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txtidfactura;
    private javax.swing.JTextField txtnro_factura;
    private javax.swing.JTextField txttotalletra;
    // End of variables declaration//GEN-END:variables
}
