/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import CONFIGURACION.ClaCorteAdmin;
import Config_JSON.json_config;
import Config_JSON.json_crear_cliente;
import Config_JSON.json_imprimir_pos;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_cotizacion;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.DAO.DAO_producto_grupo;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.cotizacion;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.producto_grupo;
import INSERT_AUTO.Cla_insert_automatico;
import MigracionBD.FrmMigracion;
import MigracionBD.MigrarBDaServidor;
import java.awt.Color;
import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmMenuHamburgus extends javax.swing.JFrame {

    /**
     * Creates new form FrmMenuQchurron
     */
    Connection conn = null;
    Connection connSER = null;
    ConnPostgres conPs = new ConnPostgres();
    VariablesBD var = new VariablesBD();
    control_vista covi = new control_vista();
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    factura fac = new factura();
    DAO_factura fdao = new DAO_factura();
    DAO_cliente cdao = new DAO_cliente();
    DAO_venta vdao = new DAO_venta();
    MigrarBDaServidor LO_SER = new MigrarBDaServidor();
    Cla_insert_automatico ins_auto = new Cla_insert_automatico();
    EvenConexion eveconn = new EvenConexion();
    caja_cierre cjcie = new caja_cierre();
    DAO_caja_cierre cjcie_dao = new DAO_caja_cierre();
    cla_color_pelete clacolor = new cla_color_pelete();
    json_config jsconfig = new json_config();
    json_crear_cliente jsCli = new json_crear_cliente();
    ClaCorteAdmin corte = new ClaCorteAdmin();
//    json_crear_cliente jsCli=new json_crear_cliente();
    json_imprimir_pos jsprint = new json_imprimir_pos();
    private Timer tiempo;
    DAO_producto_grupo pgDAO = new DAO_producto_grupo();
    producto_grupo pgru = new producto_grupo();
    cliente clie = new cliente();

    void abrir_formulario() {
        conPs.ConnectDBpostgres(conn, false, false);
        conn = conPs.getConnPosgres();
        covi.setComanda_abierto(true);
        jsconfig.cargar_jsom_configuracion();
        jsprint.cargar_jsom_imprimir_pos();
        this.setExtendedState(MAXIMIZED_BOTH);
        iniciarTiempo();
        habilitar_menu(false);
        codao.cargar_cotizacion(coti, 1);
        corte.setFecha_corte("2021-12-30");
        txtvercion.setText("V:" + jsconfig.getVersion());
        jFdolar.setValue(coti.getDolar_guarani());
        jFreal.setValue(coti.getReal_guarani());
        iniciar_color();
        actualizacion_version_v1();
    }

    void cargar_producto_grupo() {
        pgDAO.cargar_producto_grupo(conn, pgru, 0);
        pgru.setNom_grupo_0(pgru.getC2nombre());
        pgDAO.cargar_producto_grupo(conn, pgru, 1);
        pgru.setNom_grupo_1(pgru.getC2nombre());
    }

    void iniciar_color() {
        clacolor.setColor_insertar_primario(new Color(137, 201, 184));
        clacolor.setColor_insertar_secundario(new Color(132, 169, 172));
        clacolor.setColor_tabla(new Color(217, 173, 173));
        clacolor.setColor_referencia(new Color(239, 187, 207));
        clacolor.setColor_base(new Color(134, 117, 169));
    }

    void titulo_sistema(String servidor) {
        String titulo = jsconfig.getNombre_sistema() + " V." + jsconfig.getVersion()
                + " BD: " + var.getPsLocalhost() + "/" + var.getPsPort() + "/" + var.getPsNomBD()
                + " Fecha: " + jsconfig.getFecha_sis() + servidor+" /Dias:"+corte.getCant_dias();
        this.setTitle(titulo);
    }

    void conectar_servidor(boolean mensaje) {
        String servidor = "";
        titulo_sistema(servidor);
    }

    private void habilitar_menu(boolean blo) {
        FrmMenuHamburgus.btncaja_detalle.setEnabled(blo);
        FrmMenuHamburgus.btncliente.setEnabled(blo);
        FrmMenuHamburgus.btngasto.setEnabled(blo);
        FrmMenuHamburgus.btnproducto.setEnabled(blo);
        FrmMenuHamburgus.btnvale.setEnabled(blo);
        FrmMenuHamburgus.btnventa.setEnabled(blo);
        FrmMenuHamburgus.btncomanda.setEnabled(blo);
        FrmMenuHamburgus.btncategoria.setEnabled(blo);
        FrmMenuHamburgus.btnbalance.setEnabled(blo);
        FrmMenuHamburgus.btncambiar_usuario.setEnabled(blo);
        FrmMenuHamburgus.jMenu_caja.setEnabled(blo);
        FrmMenuHamburgus.jMenu_cliente.setEnabled(blo);
        FrmMenuHamburgus.jMenu_config.setEnabled(blo);
        FrmMenuHamburgus.jMenu_delivery.setEnabled(blo);
        FrmMenuHamburgus.jMenu_gasto.setEnabled(blo);
        FrmMenuHamburgus.jMenu_producto.setEnabled(blo);
        FrmMenuHamburgus.jMenu_venta.setEnabled(blo);
        FrmMenuHamburgus.jMenu_fatura.setEnabled(blo);
        FrmMenuHamburgus.btncomprainsumo.setEnabled(blo);
        FrmMenuHamburgus.jMenu_compra.setEnabled(blo);
        FrmMenuHamburgus.btncotizacion.setEnabled(blo);
    }


    void crear_cliente_de_json() {
        jsCli.leer_jsom_cliente();
        System.out.println("Nombre:" + jsCli.getJs_nombre());
    }

    void iniciarTiempo() {
        tiempo = new Timer();
        //le asignamos una tarea al timer
        tiempo.schedule(new FrmMenuHamburgus.clasetimer(), 0, 1000 * 1);
        System.out.println("Timer Iniciado en COMANDA");
    }

    void pararTiempo() {
        tiempo.cancel();
        System.out.println("Timer Parado en COMANDA");
    }

    class clasetimer extends TimerTask {

        private int contador_segundo;

        public void run() {
            contador_segundo++;
            txtfechahora.setText(evefec.getString_formato_hora());
            if (contador_segundo >= 5) {
                contador_segundo = 0;
//                crear_cliente_de_json();
            }
        }
    }

    private void abrir_caja_cierre() {
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
        cjcie.setC1idcaja_cierre(idcaja_cierre);
        cjcie_dao.cargar_caja_cierre(cjcie);
        if (cjcie.getC4estado().equals("CERRADO")) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
        } else {
            evetbl.abrir_TablaJinternal(new FrmCaja_Cierre());
        }
    }

    void actualizacion_version_v1() {
        /**
         * ALTER TABLE cliente ADD COLUMN delivery boolean; update cliente set
         * delivery=true; update gastos set fk_idusuario=7 where fk_idusuario=1;
         * alter table itemventacomanda alter column preciocompra type
         * numeric(14,0) using preciocompra::numeric;
         */
        String sql = "DO $$ \n"
                + "    BEGIN\n"
                + "        BEGIN\n"
                //                + "        update producto set orden='0'  where orden is null;\n "
                                + "        ALTER TABLE venta ADD COLUMN forma_pago text; "
                                + "        update venta set forma_pago='EFECTIVO'; "

                
                                + "        EXCEPTION\n"
                + "            WHEN duplicate_column THEN RAISE NOTICE 'duplicate_column.';\n"
                + "        END;\n"
                + "    END;\n"
                + "$$ ";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public FrmMenuHamburgus() {
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

        escritorio = new javax.swing.JDesktopPane();
        btncliente = new javax.swing.JButton();
        btnproducto = new javax.swing.JButton();
        btnventa = new javax.swing.JButton();
        btncaja_detalle = new javax.swing.JButton();
        btngasto = new javax.swing.JButton();
        btnvale = new javax.swing.JButton();
        lblusuario = new javax.swing.JLabel();
        btncomanda = new javax.swing.JButton();
        btncategoria = new javax.swing.JButton();
        btnbalance = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jFdolar = new javax.swing.JFormattedTextField();
        jFreal = new javax.swing.JFormattedTextField();
        btncambiar_usuario = new javax.swing.JButton();
        txtfechahora = new javax.swing.JLabel();
        txtvercion = new javax.swing.JLabel();
        btncomprainsumo = new javax.swing.JButton();
        btncotizacion = new javax.swing.JButton();
        btndelivery_venta = new javax.swing.JButton();
        btncajacerrar = new javax.swing.JButton();
        btncaja_cierre = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_venta = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu_cliente = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu_producto = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenu_delivery = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenu_config = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu_caja = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenu_gasto = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu_fatura = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu_compra = new javax.swing.JMenu();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btncliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/cliente.png"))); // NOI18N
        btncliente.setText("CLIENTE/FUNCIO");
        btncliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclienteActionPerformed(evt);
            }
        });

        btnproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/producto.png"))); // NOI18N
        btnproducto.setText("PRODUCTO");
        btnproducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnproducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductoActionPerformed(evt);
            }
        });

        btnventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/venta.png"))); // NOI18N
        btnventa.setText("VENTA");
        btnventa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnventa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventaActionPerformed(evt);
            }
        });

        btncaja_detalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/caja_calendar.png"))); // NOI18N
        btncaja_detalle.setText("CAJA DIARIO");
        btncaja_detalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncaja_detalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncaja_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_detalleActionPerformed(evt);
            }
        });

        btngasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/gasto.png"))); // NOI18N
        btngasto.setText("GASTO");
        btngasto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btngasto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btngasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngastoActionPerformed(evt);
            }
        });

        btnvale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/vale.png"))); // NOI18N
        btnvale.setText("VALE");
        btnvale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnvale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnvale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvaleActionPerformed(evt);
            }
        });

        lblusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblusuario.setForeground(new java.awt.Color(255, 255, 255));
        lblusuario.setText("usuario");

        btncomanda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/comanda.png"))); // NOI18N
        btncomanda.setText("COMANDA");
        btncomanda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncomanda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncomanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncomandaActionPerformed(evt);
            }
        });

        btncategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/categoria.png"))); // NOI18N
        btncategoria.setText("CATEGORIA");
        btncategoria.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncategoria.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncategoriaActionPerformed(evt);
            }
        });

        btnbalance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/balance.png"))); // NOI18N
        btnbalance.setText("BALANCE");
        btnbalance.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnbalance.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnbalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbalanceActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("COTIZACION"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("DOLAR:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("REAL:");

        jFdolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFdolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdolar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFreal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFreal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFreal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jFreal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFdolar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFdolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jFreal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btncambiar_usuario.setText("CAMBIAR USUARIO");
        btncambiar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncambiar_usuarioActionPerformed(evt);
            }
        });

        txtfechahora.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtfechahora.setForeground(new java.awt.Color(255, 255, 255));
        txtfechahora.setText("jLabel3");

        txtvercion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtvercion.setForeground(new java.awt.Color(255, 255, 255));
        txtvercion.setText("jLabel3");

        btncomprainsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/compra.png"))); // NOI18N
        btncomprainsumo.setText("COMPRA");
        btncomprainsumo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncomprainsumo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncomprainsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncomprainsumoActionPerformed(evt);
            }
        });

        btncotizacion.setText("COTIZACION");
        btncotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncotizacionActionPerformed(evt);
            }
        });

        btndelivery_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/delivery.png"))); // NOI18N
        btndelivery_venta.setText("DELIVERY");
        btndelivery_venta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndelivery_venta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btndelivery_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelivery_ventaActionPerformed(evt);
            }
        });

        btncajacerrar.setText("CAJA CERRAR");
        btncajacerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncajacerrarActionPerformed(evt);
            }
        });

        btncaja_cierre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/caja_cierre.png"))); // NOI18N
        btncaja_cierre.setText("CAJA CIERRE");
        btncaja_cierre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncaja_cierre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncaja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_cierreActionPerformed(evt);
            }
        });

        escritorio.setLayer(btncliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnproducto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnventa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncaja_detalle, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btngasto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnvale, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblusuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncomanda, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncategoria, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnbalance, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncambiar_usuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(txtfechahora, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(txtvercion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncomprainsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncotizacion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btndelivery_venta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncajacerrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncaja_cierre, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btngasto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnventa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnvale, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncomanda, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addComponent(btncaja_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncaja_cierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addComponent(btnproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnbalance, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncomprainsumo, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(btndelivery_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfechahora)
                            .addComponent(txtvercion))
                        .addContainerGap(323, Short.MAX_VALUE))
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblusuario)
                            .addComponent(btncambiar_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btncotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btncajacerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncomanda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnproducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncategoria, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btndelivery_venta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(txtfechahora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtvercion)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnvale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnbalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btngasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncaja_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncomprainsumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncaja_cierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblusuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncambiar_usuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncotizacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncajacerrar)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jMenu_venta.setText("VENTA");

        jMenuItem1.setText("CREAR VENTA");
        jMenu_venta.add(jMenuItem1);

        jMenuItem31.setText("MESA");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu_venta.add(jMenuItem31);

        jMenu3.setText("REPORTE");

        jMenuItem33.setText("VENTA TODOS");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem33);

        jMenuItem34.setText("VENTA DETALLE");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem34);

        jMenu_venta.add(jMenu3);

        jMenuBar1.add(jMenu_venta);

        jMenu5.setText("COMPRA");

        jMenuItem14.setText("PROVEEDOR");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem14);

        jMenuItem19.setText("COMPRA PRODUCTO");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenu6.setText("REPORTE");

        jMenuItem20.setText("COMPRA TODOS");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenu5.add(jMenu6);

        jMenuBar1.add(jMenu5);

        jMenu_cliente.setText("CLIENTE");

        jMenuItem2.setText("CREAR CLIENTE");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu_cliente.add(jMenuItem2);

        jMenuItem3.setText("ZONA DELIVERY");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu_cliente.add(jMenuItem3);

        jMenuBar1.add(jMenu_cliente);

        jMenu_producto.setText("PRODUCTO");

        jMenuItem4.setText("CREAR PRODUCTO");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem4);

        jMenuItem32.setText("GRUPO");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem32);

        jMenuItem5.setText("CATEGORIA");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem5);

        jMenuItem6.setText("UNIDAD");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem6);

        jMenuItem7.setText("INGREDIENTE");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem7);

        jMenu7.setText("REPORTE");

        jMenuItem35.setText("VENTA PRODUCTO POR CATEGORIA");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem35);

        jMenu_producto.add(jMenu7);

        jMenuBar1.add(jMenu_producto);

        jMenu_delivery.setText("DELIVERY");

        jMenuItem8.setText("ZONA");
        jMenu_delivery.add(jMenuItem8);

        jMenuItem9.setText("ENTREGADOR");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu_delivery.add(jMenuItem9);

        jMenuItem27.setText("DELIVERY VENTA");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu_delivery.add(jMenuItem27);

        jMenuBar1.add(jMenu_delivery);

        jMenu_config.setText("CONFIGURACION");

        jMenuItem10.setText("USUARIO");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu_config.add(jMenuItem10);

        jMenu1.setText("BACKUP");

        jMenuItem15.setText("DATOS BACKUP");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem15);

        jMenuItem16.setText("CREAR BACKUP");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenu_config.add(jMenu1);

        jMenuItem17.setText("COTIZACION");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu_config.add(jMenuItem17);

        jMenuBar1.add(jMenu_config);

        jMenu_caja.setText("CAJA");

        jMenuItem11.setText("CAJA DETALLE");
        jMenu_caja.add(jMenuItem11);

        jMenuItem28.setText("CAJA ABRIR");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem28);

        jMenuItem29.setText("CAJA CIERRE");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem29);

        jMenuItem30.setText("CAJA ABRIR-CERRAR");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem30);

        jMenuBar1.add(jMenu_caja);

        jMenu_gasto.setText("GASTO");

        jMenuItem12.setText("GASTO");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu_gasto.add(jMenuItem12);

        jMenuItem13.setText("GASTO TIPO");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu_gasto.add(jMenuItem13);

        jMenuBar1.add(jMenu_gasto);

        jMenu_fatura.setText("FACTURA");

        jMenuItem18.setText("FILTRO FACTURA");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu_fatura.add(jMenuItem18);

        jMenuBar1.add(jMenu_fatura);

        jMenu_compra.setText("INSUMO");

        jMenuItem24.setText("COMPRA INSUMO");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu_compra.add(jMenuItem24);

        jMenu2.setText("PRODUCTO INSUMO");

        jMenuItem21.setText("PRODUCTO");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem21);

        jMenuItem22.setText("CATEGORIA");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem22);

        jMenuItem23.setText("UNIDAD");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem23);

        jMenu_compra.add(jMenu2);

        jMenu4.setText("REPORTE INSUMO");

        jMenuItem25.setText("PRODUCTOS MAS COMPRADOS");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem25);

        jMenuItem26.setText("PRODUCTOS MAS VENDIDOS ");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem26);

        jMenu_compra.add(jMenu4);

        jMenuBar1.add(jMenu_compra);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmZonaDelivery());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_Ingrediente());
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_unidad());
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclienteActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_btnclienteActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto());
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto());
    }//GEN-LAST:event_btnproductoActionPerformed

    private void btnventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVenta());
    }//GEN-LAST:event_btnventaActionPerformed

    private void btncaja_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_detalleActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCajaDetalle());
    }//GEN-LAST:event_btncaja_detalleActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_tipo());
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void btngastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngastoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_btngastoActionPerformed

    private void btnvaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvaleActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVale());
    }//GEN-LAST:event_btnvaleActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario());
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        if (corte.verificar_corte_admin(conn)) {
            JDiaLogin log = new JDiaLogin(this, true);
            log.setVisible(true);
        }
        conectar_servidor(true);
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmBackup());
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCrearBackup());
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void btncomandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncomandaActionPerformed
        // TODO add your handling code here:

        if (covi.isComanda_abierto()) {
            FrmComandaCocina frm = new FrmComandaCocina();
            frm.setVisible(true);
        }
    }//GEN-LAST:event_btncomandaActionPerformed

    private void btncategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncategoriaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_btncategoriaActionPerformed

    private void btnbalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbalanceActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmBalanceCaja());
    }//GEN-LAST:event_btnbalanceActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCotizacion());
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        fac.setFactura_cargada(false);
        evetbl.abrir_TablaJinternal(new FrmFactura());
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void btncambiar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncambiar_usuarioActionPerformed
        // TODO add your handling code here:
        JDiaLogin log = new JDiaLogin(this, true);
        log.setVisible(true);
    }//GEN-LAST:event_btncambiar_usuarioActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmInsumo_unidad());
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmInsumo_categoria());
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmInsumo_Producto());
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void btncomprainsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncomprainsumoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCompra());
    }//GEN-LAST:event_btncomprainsumoActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCompra_insumo());
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmFiltro_insumo_mas_comprado());
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmFiltro_insumo_mas_vendido());
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void btncotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncotizacionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCotizacion());
    }//GEN-LAST:event_btncotizacionActionPerformed

    private void btndelivery_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelivery_ventaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador_venta());
    }//GEN-LAST:event_btndelivery_ventaActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador_venta());
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
        abrir_caja_cierre();
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_abrir_cerrar());
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void btncajacerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncajacerrarActionPerformed
        // TODO add your handling code here:
        abrir_caja_cierre();
    }//GEN-LAST:event_btncajacerrarActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVenta_mesa());
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_grupo());
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void btncaja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_cierreActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_abrir_cerrar());
    }//GEN-LAST:event_btncaja_cierreActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVenta());
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVentaDetalle());
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProveedor());
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:.
        evetbl.abrir_TablaJinternal(new FrmCompra());
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepCompra());
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVentaProductoCaategoria());
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMenuHamburgus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenuHamburgus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenuHamburgus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenuHamburgus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuHamburgus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnbalance;
    public static javax.swing.JButton btncaja_cierre;
    public static javax.swing.JButton btncaja_detalle;
    private javax.swing.JButton btncajacerrar;
    public static javax.swing.JButton btncambiar_usuario;
    public static javax.swing.JButton btncategoria;
    public static javax.swing.JButton btncliente;
    public static javax.swing.JButton btncomanda;
    public static javax.swing.JButton btncomprainsumo;
    public static javax.swing.JButton btncotizacion;
    public static javax.swing.JButton btndelivery_venta;
    public static javax.swing.JButton btngasto;
    public static javax.swing.JButton btnproducto;
    public static javax.swing.JButton btnvale;
    public static javax.swing.JButton btnventa;
    public static javax.swing.JDesktopPane escritorio;
    public static javax.swing.JFormattedTextField jFdolar;
    public static javax.swing.JFormattedTextField jFreal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public static javax.swing.JMenu jMenu_caja;
    public static javax.swing.JMenu jMenu_cliente;
    public static javax.swing.JMenu jMenu_compra;
    public static javax.swing.JMenu jMenu_config;
    public static javax.swing.JMenu jMenu_delivery;
    public static javax.swing.JMenu jMenu_fatura;
    public static javax.swing.JMenu jMenu_gasto;
    public static javax.swing.JMenu jMenu_producto;
    public static javax.swing.JMenu jMenu_venta;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblusuario;
    private javax.swing.JLabel txtfechahora;
    private javax.swing.JLabel txtvercion;
    // End of variables declaration//GEN-END:variables
}
