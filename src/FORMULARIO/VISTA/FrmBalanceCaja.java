/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

//import BaseDato.ConnPostgres;
//import ClaseSQL.FUNinternalframe;
//import ClaseSQL.FunFechas;
//import ClaseSQL.SQLejecucion;
//import Formulario.Menu.FrmMenucomidarapida;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_cliente;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Digno
 */
public class FrmBalanceCaja extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmBalanceCaja
     */
    ConnPostgres cpt = new ConnPostgres();
    Connection conn = cpt.getConnPosgres();
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    EvenSQLDataSet evedata = new EvenSQLDataSet();
    FunFreeChard chard = new FunFreeChard();
    DAO_caja_detalle cdao = new DAO_caja_detalle();
    DAO_cliente cldao = new DAO_cliente();
    cla_color_pelete clacolor= new cla_color_pelete();
    void abrir_formulario() {
        this.setTitle("BALANCE");
        evetbl.centrar_formulario(this);
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        color_formulario();
    }
    void color_formulario(){
        panel_referencia_tipo.setBackground(clacolor.getColor_insertar_primario());
        panel_referencia_fecha.setBackground(clacolor.getColor_insertar_primario());
        panel_base_caja.setBackground(clacolor.getColor_insertar_secundario());
        panel_base_venta.setBackground(clacolor.getColor_insertar_secundario());
        panel_base_gasto.setBackground(clacolor.getColor_insertar_secundario());
        panel_base_deli.setBackground(clacolor.getColor_insertar_secundario());
        panel_base_compra.setBackground(clacolor.getColor_insertar_secundario());
        panel_base_cliente.setBackground(clacolor.getColor_insertar_secundario());
        panel_base_vale.setBackground(clacolor.getColor_insertar_secundario());
    }

    void cargar_grafico_caja() {
        String campo_fecha = "";
        String campo_fecha_2 = "";
        String tipo_fecha = "";
        if (jRfecha_dia.isSelected()) {
            campo_fecha = "to_char(fecha_emision,'yyyy-MM-dd')";
            campo_fecha_2 = "to_char(fecha_inicio,'yyyy-MM-dd')";
            tipo_fecha = "DIA";
        }
        if (jRfecha_semana.isSelected()) {
            campo_fecha = "to_char(fecha_emision,'MM-sem-WW')";
            campo_fecha_2 = "to_char(fecha_inicio,'MM-sem-WW')";
            tipo_fecha = "SEMANA";
        }
        if (jRfecha_mes.isSelected()) {
            campo_fecha = "to_char(fecha_emision,'yyyy-MM')";
            campo_fecha_2 = "to_char(fecha_inicio,'yyyy-MM')";
            tipo_fecha = "MES";
        }

        String titulo = "GRAFICO DE INGRESO_EGRESO POR " + tipo_fecha + "";
        String plano_horizontal = tipo_fecha;
        String plano_vertical = "MONTO";
        String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
        String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
        txtfecha_desde.setText(fecdesde);
        txtfecha_hasta.setText(fechasta);
        String filtro_fecha = " date(fecha_emision)>='" + fecdesde + "' \n"
                + "and date(fecha_emision)<='" + fechasta + "'   ";
        String filtro_fecha_2 = " date(fecha_inicio)>='" + fecdesde + "' \n"
                + "and date(fecha_inicio)<='" + fechasta + "'   ";
        String fecha_mostrar = "DESDE: " + fecdesde + " HASTA: " + fechasta + " ";
        lblfiltro_fecha.setText(fecha_mostrar);
        lblfiltro_fecha1.setText(fecha_mostrar);
        lblfiltro_fecha2.setText(fecha_mostrar);
        lblfiltro_fecha3.setText(fecha_mostrar);
        lblfiltro_fecha4.setText(fecha_mostrar);
        lblfiltro_fecha5.setText(fecha_mostrar);
        DefaultCategoryDataset dataset_ingreso_egreso = new DefaultCategoryDataset();
        if (jCtodos_venta.isSelected()) {
            evedata.createDataset_caja_ingreso_egreso(conn, dataset_ingreso_egreso, campo_fecha, "VENTA", filtro_fecha);
        }
        if (jCtodos_delivery.isSelected()) {
            evedata.createDataset_caja_ingreso_egreso(conn, dataset_ingreso_egreso, campo_fecha, "DELIVERY", filtro_fecha);
        }
        if (jCtodos_gasto.isSelected()) {
            evedata.createDataset_caja_ingreso_egreso(conn, dataset_ingreso_egreso, campo_fecha, "GASTO", filtro_fecha);
        }
        if (jCtodos_vale.isSelected()) {
            evedata.createDataset_caja_ingreso_egreso(conn, dataset_ingreso_egreso, campo_fecha, "VALE", filtro_fecha);
        }
        if (jCtodos_compra.isSelected()) {
            evedata.createDataset_caja_ingreso_egreso(conn, dataset_ingreso_egreso, campo_fecha, "COMPRA", filtro_fecha);
        }
        chard.crear_graficoBar3D(panel_grafico_barra_ingreso_egreso, dataset_ingreso_egreso, titulo, plano_horizontal, plano_vertical);
        cdao.actualizar_tabla_grafico_caja_detalle(conn, tblcaja_balance, campo_fecha, filtro_fecha);
        DefaultCategoryDataset dataset_venta = new DefaultCategoryDataset();
        String titulo_venta = "GRAFICO DE VENTA POR " + tipo_fecha + "";
        evedata.createDataset_caja_ingreso_egreso(conn, dataset_venta, campo_fecha, "VENTA", filtro_fecha);
        chard.crear_graficoBar3D(panel_grafico_barra_venta, dataset_venta, titulo_venta, plano_horizontal, plano_vertical);
        cdao.actualizar_tabla_grafico_caja_detalle_venta(conn, tblcaja_venta, campo_fecha, filtro_fecha);
        DefaultCategoryDataset dataset_gasto = new DefaultCategoryDataset();
        String titulo_gasto = "GRAFICO DE GASTO POR " + tipo_fecha + "";
        evedata.createDataset_caja_ingreso_egreso(conn, dataset_gasto, campo_fecha, "GASTO", filtro_fecha);
        chard.crear_graficoBar3D(panel_grafico_barra_gasto, dataset_gasto, titulo_gasto, plano_horizontal, plano_vertical);
        cdao.actualizar_tabla_grafico_caja_detalle_gasto(conn, tblcaja_gasto, campo_fecha, filtro_fecha);
        DefaultCategoryDataset dataset_vale = new DefaultCategoryDataset();
        String titulo_vale = "GRAFICO DE VALE POR " + tipo_fecha + "";
        evedata.createDataset_caja_ingreso_egreso(conn, dataset_vale, campo_fecha, "VALE", filtro_fecha);
        chard.crear_graficoBar3D(panel_grafico_barra_vale, dataset_vale, titulo_vale, plano_horizontal, plano_vertical);
        cdao.actualizar_tabla_grafico_caja_detalle_vale(conn, tblcaja_vale, campo_fecha, filtro_fecha);
        DefaultCategoryDataset dataset_delivery = new DefaultCategoryDataset();
        String titulo_delivery = "GRAFICO DE DELIVERY POR " + tipo_fecha + "";
        evedata.createDataset_caja_ingreso_egreso(conn, dataset_delivery, campo_fecha, "DELIVERY", filtro_fecha);
        chard.crear_graficoBar3D(panel_grafico_barra_delivery, dataset_delivery, titulo_delivery, plano_horizontal, plano_vertical);
        cdao.actualizar_tabla_grafico_caja_detalle_delivery(conn, tblcaja_delivery, campo_fecha, filtro_fecha);
        DefaultCategoryDataset dataset_cliente = new DefaultCategoryDataset();
        String titulo_cliente = "GRAFICO DE CANTIDAD CLIENTE POR " + tipo_fecha + "";
        evedata.createDataset_cantidad_cliente(conn, dataset_cliente, campo_fecha_2, "CANTIDAD_CLIENTE", filtro_fecha_2);
        chard.crear_graficoBar3D(panel_grafico_barra_cliente, dataset_cliente, titulo_cliente, plano_horizontal, plano_vertical);
        cldao.actualizar_tabla_grafico_cliente(conn, tblcant_cliente, campo_fecha_2, filtro_fecha_2);
        DefaultCategoryDataset dataset_compra_ins = new DefaultCategoryDataset();
        String titulo_compra_ins = "GRAFICO DE COMPRA INSUMO POR " + tipo_fecha + "";
        evedata.createDataset_caja_ingreso_egreso(conn, dataset_compra_ins, campo_fecha, "COMPRA", filtro_fecha);
        chard.crear_graficoBar3D(panel_grafico_barra_compra_insumo, dataset_compra_ins, titulo_compra_ins, plano_horizontal, plano_vertical);
        cdao.actualizar_tabla_grafico_caja_detalle_compra_ins(conn, tblcaja_compra_insumo, campo_fecha, filtro_fecha);
    }

    public FrmBalanceCaja() {
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

        gru_fec = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_base_caja = new javax.swing.JPanel();
        panel_referencia_fecha = new javax.swing.JPanel();
        jRfecha_dia = new javax.swing.JRadioButton();
        jRfecha_semana = new javax.swing.JRadioButton();
        jRfecha_mes = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        txtfecha_hasta = new javax.swing.JTextField();
        lblfiltro_fecha = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panel_grafico_barra_ingreso_egreso = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcaja_balance = new javax.swing.JTable();
        panel_referencia_tipo = new javax.swing.JPanel();
        jCtodos_venta = new javax.swing.JCheckBox();
        jCtodos_delivery = new javax.swing.JCheckBox();
        jCtodos_gasto = new javax.swing.JCheckBox();
        jCtodos_vale = new javax.swing.JCheckBox();
        jCtodos_compra = new javax.swing.JCheckBox();
        btnactualizar = new javax.swing.JButton();
        panel_base_venta = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblcaja_venta = new javax.swing.JTable();
        panel_grafico_barra_venta = new javax.swing.JPanel();
        lblfiltro_fecha1 = new javax.swing.JLabel();
        panel_base_gasto = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcaja_gasto = new javax.swing.JTable();
        lblfiltro_fecha2 = new javax.swing.JLabel();
        panel_grafico_barra_gasto = new javax.swing.JPanel();
        panel_base_vale = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcaja_vale = new javax.swing.JTable();
        lblfiltro_fecha3 = new javax.swing.JLabel();
        panel_grafico_barra_vale = new javax.swing.JPanel();
        panel_base_deli = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblcaja_delivery = new javax.swing.JTable();
        lblfiltro_fecha5 = new javax.swing.JLabel();
        panel_grafico_barra_delivery = new javax.swing.JPanel();
        panel_base_cliente = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblcant_cliente = new javax.swing.JTable();
        lblfiltro_fecha4 = new javax.swing.JLabel();
        panel_grafico_barra_cliente = new javax.swing.JPanel();
        panel_base_compra = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblcaja_compra_insumo = new javax.swing.JTable();
        panel_grafico_barra_compra_insumo = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        panel_referencia_fecha.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        gru_fec.add(jRfecha_dia);
        jRfecha_dia.setText("DIA");
        jRfecha_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRfecha_diaActionPerformed(evt);
            }
        });

        gru_fec.add(jRfecha_semana);
        jRfecha_semana.setSelected(true);
        jRfecha_semana.setText("SEMANA");
        jRfecha_semana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRfecha_semanaActionPerformed(evt);
            }
        });

        gru_fec.add(jRfecha_mes);
        jRfecha_mes.setText("MES");
        jRfecha_mes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRfecha_mesActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Fecha Hasta:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Fecha Desde:");

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_fechaLayout = new javax.swing.GroupLayout(panel_referencia_fecha);
        panel_referencia_fecha.setLayout(panel_referencia_fechaLayout);
        panel_referencia_fechaLayout.setHorizontalGroup(
            panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                        .addComponent(jRfecha_dia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRfecha_semana)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRfecha_mes))
                    .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                        .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_hasta))
                    .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_referencia_fechaLayout.setVerticalGroup(
            panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRfecha_dia)
                    .addComponent(jRfecha_semana)
                    .addComponent(jRfecha_mes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblfiltro_fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfiltro_fecha.setText("jLabel1");

        panel_grafico_barra_ingreso_egreso.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_grafico_barra_ingreso_egresoLayout = new javax.swing.GroupLayout(panel_grafico_barra_ingreso_egreso);
        panel_grafico_barra_ingreso_egreso.setLayout(panel_grafico_barra_ingreso_egresoLayout);
        panel_grafico_barra_ingreso_egresoLayout.setHorizontalGroup(
            panel_grafico_barra_ingreso_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 786, Short.MAX_VALUE)
        );
        panel_grafico_barra_ingreso_egresoLayout.setVerticalGroup(
            panel_grafico_barra_ingreso_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("GRAFICO ", panel_grafico_barra_ingreso_egreso);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcaja_balance.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblcaja_balance);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("TABLA", jPanel3);

        panel_referencia_tipo.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA TODOS"));

        jCtodos_venta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCtodos_venta.setSelected(true);
        jCtodos_venta.setText("VENTA");
        jCtodos_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtodos_ventaActionPerformed(evt);
            }
        });

        jCtodos_delivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCtodos_delivery.setText("DELIVERY");
        jCtodos_delivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtodos_deliveryActionPerformed(evt);
            }
        });

        jCtodos_gasto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCtodos_gasto.setSelected(true);
        jCtodos_gasto.setText("GASTO");
        jCtodos_gasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtodos_gastoActionPerformed(evt);
            }
        });

        jCtodos_vale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCtodos_vale.setText("VALE");
        jCtodos_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtodos_valeActionPerformed(evt);
            }
        });

        jCtodos_compra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCtodos_compra.setText("COMPRA");
        jCtodos_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCtodos_compraActionPerformed(evt);
            }
        });

        btnactualizar.setText("ACTUALIZAR");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_tipoLayout = new javax.swing.GroupLayout(panel_referencia_tipo);
        panel_referencia_tipo.setLayout(panel_referencia_tipoLayout);
        panel_referencia_tipoLayout.setHorizontalGroup(
            panel_referencia_tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_tipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCtodos_venta)
                    .addComponent(jCtodos_delivery)
                    .addComponent(jCtodos_gasto)
                    .addComponent(jCtodos_vale)
                    .addComponent(jCtodos_compra)
                    .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_referencia_tipoLayout.setVerticalGroup(
            panel_referencia_tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_tipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCtodos_venta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCtodos_delivery)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCtodos_gasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCtodos_vale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCtodos_compra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_base_cajaLayout = new javax.swing.GroupLayout(panel_base_caja);
        panel_base_caja.setLayout(panel_base_cajaLayout);
        panel_base_cajaLayout.setHorizontalGroup(
            panel_base_cajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_cajaLayout.createSequentialGroup()
                .addGroup(panel_base_cajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_referencia_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_referencia_tipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_cajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );
        panel_base_cajaLayout.setVerticalGroup(
            panel_base_cajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_cajaLayout.createSequentialGroup()
                .addComponent(panel_referencia_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_referencia_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_base_cajaLayout.createSequentialGroup()
                .addComponent(lblfiltro_fecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        jTabbedPane1.addTab("FILTRO BALANCE CAJA", panel_base_caja);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcaja_venta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblcaja_venta);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_grafico_barra_venta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_grafico_barra_ventaLayout = new javax.swing.GroupLayout(panel_grafico_barra_venta);
        panel_grafico_barra_venta.setLayout(panel_grafico_barra_ventaLayout);
        panel_grafico_barra_ventaLayout.setHorizontalGroup(
            panel_grafico_barra_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_grafico_barra_ventaLayout.setVerticalGroup(
            panel_grafico_barra_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        lblfiltro_fecha1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfiltro_fecha1.setText("jLabel1");

        javax.swing.GroupLayout panel_base_ventaLayout = new javax.swing.GroupLayout(panel_base_venta);
        panel_base_venta.setLayout(panel_base_ventaLayout);
        panel_base_ventaLayout.setHorizontalGroup(
            panel_base_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_ventaLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_ventaLayout.createSequentialGroup()
                        .addComponent(lblfiltro_fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(64, Short.MAX_VALUE))
                    .addComponent(panel_grafico_barra_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_base_ventaLayout.setVerticalGroup(
            panel_base_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_base_ventaLayout.createSequentialGroup()
                .addComponent(lblfiltro_fecha1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grafico_barra_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BALANCE VENTA", panel_base_venta);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcaja_gasto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblcaja_gasto);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblfiltro_fecha2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfiltro_fecha2.setText("jLabel1");

        panel_grafico_barra_gasto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_grafico_barra_gastoLayout = new javax.swing.GroupLayout(panel_grafico_barra_gasto);
        panel_grafico_barra_gasto.setLayout(panel_grafico_barra_gastoLayout);
        panel_grafico_barra_gastoLayout.setHorizontalGroup(
            panel_grafico_barra_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_grafico_barra_gastoLayout.setVerticalGroup(
            panel_grafico_barra_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_base_gastoLayout = new javax.swing.GroupLayout(panel_base_gasto);
        panel_base_gasto.setLayout(panel_base_gastoLayout);
        panel_base_gastoLayout.setHorizontalGroup(
            panel_base_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_gastoLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_gastoLayout.createSequentialGroup()
                        .addComponent(lblfiltro_fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 68, Short.MAX_VALUE))
                    .addComponent(panel_grafico_barra_gasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_base_gastoLayout.setVerticalGroup(
            panel_base_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_gastoLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_base_gastoLayout.createSequentialGroup()
                .addComponent(lblfiltro_fecha2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grafico_barra_gasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BALANCE GASTO", panel_base_gasto);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcaja_vale.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblcaja_vale);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblfiltro_fecha3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfiltro_fecha3.setText("jLabel1");

        panel_grafico_barra_vale.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_grafico_barra_valeLayout = new javax.swing.GroupLayout(panel_grafico_barra_vale);
        panel_grafico_barra_vale.setLayout(panel_grafico_barra_valeLayout);
        panel_grafico_barra_valeLayout.setHorizontalGroup(
            panel_grafico_barra_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_grafico_barra_valeLayout.setVerticalGroup(
            panel_grafico_barra_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_base_valeLayout = new javax.swing.GroupLayout(panel_base_vale);
        panel_base_vale.setLayout(panel_base_valeLayout);
        panel_base_valeLayout.setHorizontalGroup(
            panel_base_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_valeLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_valeLayout.createSequentialGroup()
                        .addComponent(lblfiltro_fecha3, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(66, Short.MAX_VALUE))
                    .addComponent(panel_grafico_barra_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_base_valeLayout.setVerticalGroup(
            panel_base_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_valeLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_base_valeLayout.createSequentialGroup()
                .addComponent(lblfiltro_fecha3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grafico_barra_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BALANCE VALE", panel_base_vale);

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcaja_delivery.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblcaja_delivery);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblfiltro_fecha5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfiltro_fecha5.setText("jLabel1");

        panel_grafico_barra_delivery.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_grafico_barra_deliveryLayout = new javax.swing.GroupLayout(panel_grafico_barra_delivery);
        panel_grafico_barra_delivery.setLayout(panel_grafico_barra_deliveryLayout);
        panel_grafico_barra_deliveryLayout.setHorizontalGroup(
            panel_grafico_barra_deliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_grafico_barra_deliveryLayout.setVerticalGroup(
            panel_grafico_barra_deliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_base_deliLayout = new javax.swing.GroupLayout(panel_base_deli);
        panel_base_deli.setLayout(panel_base_deliLayout);
        panel_base_deliLayout.setHorizontalGroup(
            panel_base_deliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_deliLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_deliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_deliLayout.createSequentialGroup()
                        .addComponent(lblfiltro_fecha5, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(66, Short.MAX_VALUE))
                    .addComponent(panel_grafico_barra_delivery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_base_deliLayout.setVerticalGroup(
            panel_base_deliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_deliLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_base_deliLayout.createSequentialGroup()
                .addComponent(lblfiltro_fecha5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grafico_barra_delivery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BALANCE DELIVERY", panel_base_deli);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcant_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblcant_cliente);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblfiltro_fecha4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfiltro_fecha4.setText("jLabel1");

        panel_grafico_barra_cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_grafico_barra_clienteLayout = new javax.swing.GroupLayout(panel_grafico_barra_cliente);
        panel_grafico_barra_cliente.setLayout(panel_grafico_barra_clienteLayout);
        panel_grafico_barra_clienteLayout.setHorizontalGroup(
            panel_grafico_barra_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_grafico_barra_clienteLayout.setVerticalGroup(
            panel_grafico_barra_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_base_clienteLayout = new javax.swing.GroupLayout(panel_base_cliente);
        panel_base_cliente.setLayout(panel_base_clienteLayout);
        panel_base_clienteLayout.setHorizontalGroup(
            panel_base_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_clienteLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_clienteLayout.createSequentialGroup()
                        .addComponent(lblfiltro_fecha4, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(66, Short.MAX_VALUE))
                    .addComponent(panel_grafico_barra_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_base_clienteLayout.setVerticalGroup(
            panel_base_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_clienteLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_base_clienteLayout.createSequentialGroup()
                .addComponent(lblfiltro_fecha4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grafico_barra_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CANTIDAD CLIENTE", panel_base_cliente);

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcaja_compra_insumo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblcaja_compra_insumo);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );

        panel_grafico_barra_compra_insumo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_grafico_barra_compra_insumo.setPreferredSize(new java.awt.Dimension(700, 558));

        javax.swing.GroupLayout panel_grafico_barra_compra_insumoLayout = new javax.swing.GroupLayout(panel_grafico_barra_compra_insumo);
        panel_grafico_barra_compra_insumo.setLayout(panel_grafico_barra_compra_insumoLayout);
        panel_grafico_barra_compra_insumoLayout.setHorizontalGroup(
            panel_grafico_barra_compra_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );
        panel_grafico_barra_compra_insumoLayout.setVerticalGroup(
            panel_grafico_barra_compra_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_grafico_barra_compra_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_grafico_barra_compra_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_base_compraLayout = new javax.swing.GroupLayout(panel_base_compra);
        panel_base_compra.setLayout(panel_base_compraLayout);
        panel_base_compraLayout.setHorizontalGroup(
            panel_base_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 990, Short.MAX_VALUE)
            .addGroup(panel_base_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_compraLayout.setVerticalGroup(
            panel_base_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
            .addGroup(panel_base_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_base_compraLayout.createSequentialGroup()
                    .addContainerGap(13, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("BALANCE COMPRA INSUMO", panel_base_compra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRfecha_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRfecha_diaActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jRfecha_diaActionPerformed

    private void jRfecha_semanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRfecha_semanaActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jRfecha_semanaActionPerformed

    private void jRfecha_mesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRfecha_mesActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jRfecha_mesActionPerformed

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_grafico_caja();
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_grafico_caja();
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void jCtodos_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtodos_ventaActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jCtodos_ventaActionPerformed

    private void jCtodos_deliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtodos_deliveryActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jCtodos_deliveryActionPerformed

    private void jCtodos_gastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtodos_gastoActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jCtodos_gastoActionPerformed

    private void jCtodos_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtodos_valeActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jCtodos_valeActionPerformed

    private void jCtodos_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCtodos_compraActionPerformed
        // TODO add your handling code here:
        cargar_grafico_caja();
    }//GEN-LAST:event_jCtodos_compraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar;
    private javax.swing.ButtonGroup gru_fec;
    private javax.swing.JCheckBox jCtodos_compra;
    private javax.swing.JCheckBox jCtodos_delivery;
    private javax.swing.JCheckBox jCtodos_gasto;
    private javax.swing.JCheckBox jCtodos_vale;
    private javax.swing.JCheckBox jCtodos_venta;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRfecha_dia;
    private javax.swing.JRadioButton jRfecha_mes;
    private javax.swing.JRadioButton jRfecha_semana;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblfiltro_fecha;
    private javax.swing.JLabel lblfiltro_fecha1;
    private javax.swing.JLabel lblfiltro_fecha2;
    private javax.swing.JLabel lblfiltro_fecha3;
    private javax.swing.JLabel lblfiltro_fecha4;
    private javax.swing.JLabel lblfiltro_fecha5;
    private javax.swing.JPanel panel_base_caja;
    private javax.swing.JPanel panel_base_cliente;
    private javax.swing.JPanel panel_base_compra;
    private javax.swing.JPanel panel_base_deli;
    private javax.swing.JPanel panel_base_gasto;
    private javax.swing.JPanel panel_base_vale;
    private javax.swing.JPanel panel_base_venta;
    private javax.swing.JPanel panel_grafico_barra_cliente;
    private javax.swing.JPanel panel_grafico_barra_compra_insumo;
    private javax.swing.JPanel panel_grafico_barra_delivery;
    private javax.swing.JPanel panel_grafico_barra_gasto;
    private javax.swing.JPanel panel_grafico_barra_ingreso_egreso;
    private javax.swing.JPanel panel_grafico_barra_vale;
    private javax.swing.JPanel panel_grafico_barra_venta;
    private javax.swing.JPanel panel_referencia_fecha;
    private javax.swing.JPanel panel_referencia_tipo;
    private javax.swing.JTable tblcaja_balance;
    private javax.swing.JTable tblcaja_compra_insumo;
    private javax.swing.JTable tblcaja_delivery;
    private javax.swing.JTable tblcaja_gasto;
    private javax.swing.JTable tblcaja_vale;
    private javax.swing.JTable tblcaja_venta;
    private javax.swing.JTable tblcant_cliente;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
