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
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Digno
 */
public class FrmInsumo_Producto extends javax.swing.JInternalFrame {

    EvenJFRAME eveJfra = new EvenJFRAME();
    EvenJtable eveJtab =new EvenJtable();
    EvenFecha evefec = new EvenFecha();
    EvenConexion eveconn = new EvenConexion();
    insumo_producto prod = new insumo_producto();
    FunFreeChard chard=new FunFreeChard();
    BO_insumo_producto pBO = new BO_insumo_producto();
    EvenSQLDataSet evedata=new EvenSQLDataSet();
    DAO_insumo_producto pdao = new DAO_insumo_producto();
    DAO_itemven_insumo iidao=new DAO_itemven_insumo();
    insumo_categoria cate=new insumo_categoria();
    insumo_unidad unid=new insumo_unidad();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor= new cla_color_pelete();
    private boolean isCargado_idcategoria;
    private boolean isCargado_idunidad;

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("INSUMO PRODUCTO");
        eveJfra.centrar_formulario(this);     
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        reestableser();
        jCactivos_select.setSelected(true);
        prod.setActivar_select("true");
        pdao.actualizar_tabla_insumo_producto(conn, tblproducto);
        color_formulario();
    }
    void color_formulario(){
        panel_tabla_grafico.setBackground(clacolor.getColor_tabla());
        panel_tabla_insumo.setBackground(clacolor.getColor_tabla());
        panel_insertar_pri_insumo.setBackground(clacolor.getColor_insertar_primario());
        panel_referencia_fecha.setBackground(clacolor.getColor_tabla());
        panel_base_1.setBackground(clacolor.getColor_base());
    }
    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmerma, "DEBE CARGAR UN PRECIO VENTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio, "DEBE CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtstock, "DEBE CARGAR UN STOCK")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcod, "DEBE CARGAR UN CODBARRA")) {
            return false;
        }
        if(!isCargado_idcategoria){
            JOptionPane.showMessageDialog(p12_txtcategoria,"NO SE CARGO CORRECTAMENTE LA CATEGORIA","ERROR",JOptionPane.ERROR_MESSAGE);
            p12_txtcategoria.setText(null);
            p12_txtcategoria.grabFocus();
            return false;
        }else{
            p12_txtcategoria.setBackground(Color.white);
        }
        if(!isCargado_idunidad){
            JOptionPane.showMessageDialog(p13_txtunidad,"NO SE CARGO CORRECTAMENTE LA UNIDAD","ERROR",JOptionPane.ERROR_MESSAGE);
            p13_txtunidad.setText(null);
            p13_txtunidad.grabFocus();
            return false;
        }else{
            p13_txtunidad.setBackground(Color.white);
        }
        return true;
    }

    void boton_guardar() {
        if (validar_guardar()) {
            prod.setNombre(txtnombre.getText());
            prod.setMerma(Double.parseDouble(txtmerma.getText()));
            prod.setPrecio(evejtf.getDouble_format_nro_entero1(txtprecio));
            prod.setStock(Double.parseDouble(txtstock.getText()));
            prod.setActivar(jCactivar.isSelected());
            prod.setCodbarra(Integer.parseInt(txtcod.getText()));
            pBO.insertar_insumo_producto(prod, tblproducto);
            reestableser();
        }
    }

    void boton_editar() {
        if (validar_guardar()) {
            prod.setIdinsumo_producto(Integer.parseInt(p1_txtid.getText()));
            prod.setNombre(txtnombre.getText());
            prod.setMerma(Double.parseDouble(txtmerma.getText()));
            prod.setPrecio(evejtf.getDouble_format_nro_entero1(txtprecio));
            prod.setStock(Double.parseDouble(txtstock.getText()));
            prod.setActivar(jCactivar.isSelected());
            prod.setCodbarra(Integer.parseInt(txtcod.getText()));
            pBO.update_insumo_producto(prod, tblproducto);
        }
    }
    void cargar_datos_graficos_insumo(){
        String campo_fecha = "";
        String tipo_fecha = "";
        String campo_valor="";
        String plano_vertical = "CANTIDAD";
        if (jRfecha_dia.isSelected()) {
            campo_fecha = "to_char(v.fecha_inicio,'yyyy-MM-dd') as fecha";
            tipo_fecha = "DIA";
        }
        if (jRfecha_semana.isSelected()) {
            campo_fecha = "to_char(v.fecha_inicio,'MM-sem-WW') as semana";
            tipo_fecha = "SEMANA";
        }
        if (jRfecha_mes.isSelected()) {
            campo_fecha = "to_char(v.fecha_inicio,'yyyy-MM') as mes";
            tipo_fecha = "MES";
        }
        if(jRcampo_cant.isSelected()){
            campo_valor="TOTAL_CANTIDAD";
            plano_vertical = "CANTIDAD";
        }
        if(jRcampo_monto.isSelected()){
            campo_valor="TOTAL_MONTO";
            plano_vertical = "MONTO";
        }
        String titulo = "INSUMO ("+prod.getNom_categoria()+"-"+prod.getNombre()+"-"+prod.getNom_unidad_venta()+") POR " + tipo_fecha + "";
        String plano_horizontal = tipo_fecha;
        
        String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
            String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
            txtfecha_desde.setText(fecdesde);
            txtfecha_hasta.setText(fechasta);
        String filtro_fecha = "and date(v.fecha_inicio)>='" + fecdesde + "' \n"
                + "and date(v.fecha_inicio)<='" + fechasta + "'   ";
        DefaultCategoryDataset dataset_ingreso_egreso=new DefaultCategoryDataset();
        iidao.actualizar_tabla_grafico_insumo_producto(conn, tblinsumo_producto, campo_fecha, filtro_fecha, prod.getIdinsumo_producto());
        evedata.createDataset_INSUMO_PRODUCTO(conn, dataset_ingreso_egreso, campo_fecha, campo_valor, filtro_fecha, prod.getIdinsumo_producto());
        chard.crear_graficoBar3D(Panel_insumo, dataset_ingreso_egreso, titulo, plano_horizontal, plano_vertical);
        double tt_cant=iidao.getDoubleSuma_insumo_producto(conn, filtro_fecha, "tt_cant", prod.getIdinsumo_producto());
        double tt_precio=iidao.getDoubleSuma_insumo_producto(conn, filtro_fecha, "tt_precio", prod.getIdinsumo_producto());
        jFsuma_cantidad.setValue(tt_cant);
        jFsuma_monto.setValue(tt_precio);
        lblcant_descrip.setText("CANT. "+prod.getNom_unidad_venta());
    }
    void seleccionar_tabla() {
        pdao.cargar_insumo_producto(prod, tblproducto);
        p1_txtid.setText(String.valueOf(prod.getIdinsumo_producto()));
        txtnombre.setText(prod.getNombre());
        txtcod.setText(String.valueOf(prod.getCodbarra()));
        txtmerma.setText(evejtf.getString_format_nro_entero(prod.getMerma()));
        txtprecio.setText(evejtf.getString_format_nro_decimal(prod.getPrecio()));
        txtstock.setText(evejtf.getString_format_nro_entero(prod.getStock()));
        jCactivar.setSelected(prod.isActivar());
        p12_txtcategoria.setText(prod.getNom_categoria());
        p13_txtunidad.setText(prod.getNom_unidad_compra());
        lblnom_conv_uni.setText(prod.getNom_conversion_unidad());
        isCargado_idcategoria=true;
        isCargado_idunidad=true;
        calcular_precio_real();
        cargar_datos_graficos_insumo();
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    void reestableser(){
        isCargado_idcategoria=false;
        isCargado_idunidad=false;
        jList_categoria.setVisible(false);
        jList_unidad.setVisible(false);
        p1_txtid.setText(null);
        txtnombre.setText(null);
        txtmerma.setText(null);
        txtprecio.setText(null);
        txtstock.setText(null);
        txtcod.setText(null);
        jCactivar.setSelected(true);
        p12_txtcategoria.setText(null);
        p13_txtunidad.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }
    void cargar_id_categoria(){
        int idcategoria=eveconn.getInt_seleccionar_JLista(conn,p12_txtcategoria, jList_categoria,cate.getTabla(),"nombre",cate.getIdtabla());
        prod.setFk_idinsumo_categoria(idcategoria);
        isCargado_idcategoria=true;
        p13_txtunidad.grabFocus();
    }
    void cargar_id_unidad(){
        int idunidad=eveconn.getInt_seleccionar_JLista(conn, p13_txtunidad, jList_unidad,unid.getTabla(),"nom_compra",unid.getIdtabla());
        prod.setFk_idinsumo_unidad(idunidad);
        isCargado_idunidad=true;
        txtnombre.grabFocus();
    }
    void calcular_precio_real(){
        if(txtmerma.getText().trim().length()>0 && txtprecio.getText().trim().length()>0){
            double merma=Double.parseDouble(txtmerma.getText());
            double preciocompra=evejtf.getDouble_format_nro_entero1(txtprecio);
            double precioreal=(preciocompra+((preciocompra*merma)/100));
            txtprecio_real.setText(evejtf.getString_format_nro_decimal(precioreal));
        }
    }
    void activar_select(boolean activar){
        if(activar){
            prod.setActivar_select("true");
        }else{
            prod.setActivar_select("false");
        }
        String sql_select = "SELECT ip.idinsumo_producto as idip,ip.codbarra as cod,(ic.nombre||'-'||ip.nombre||'-'||iu.nom_compra) as insumo_compra,\n"
            + "TRIM(to_char(ip.precio,'999G999G999')) as pre_comp,ip.merma,\n"
            + "TRIM(to_char((ip.precio+((ip.precio*ip.merma)/100)),'999G999G999')) as pre_util,ip.activar \n"
            + "FROM insumo_producto ip,insumo_categoria ic,insumo_unidad iu\n"
            + "where ip.fk_idinsumo_categoria=ic.idinsumo_categoria \n"
            + "and ip.activar="+prod.getActivar_select()
            + " and ip.fk_idinsumo_unidad=iu.idinsumo_unidad ORDER BY 3 ASC;";
        pdao.actualizar_tabla_insumo_producto_activar(conn, tblproducto, sql_select);
    }
    void boton_nuevo(){
        reestableser();
    }
    public FrmInsumo_Producto() {
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

        fec_insu = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        panel_insertar_pri_insumo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        p1_txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtmerma = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtstock = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_unidad = new javax.swing.JList<>();
        jList_categoria = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        p12_txtcategoria = new javax.swing.JTextField();
        p13_txtunidad = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        btnnuevo_unidad = new javax.swing.JButton();
        btnnuevo_categoria = new javax.swing.JButton();
        lblnom_conv_uni = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtprecio_real = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jCactivar = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        panel_tabla_insumo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtbuscar_categoria = new javax.swing.JTextField();
        txtbuscar_unidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        jCactivos_select = new javax.swing.JCheckBox();
        panel_base_1 = new javax.swing.JPanel();
        panel_referencia_fecha = new javax.swing.JPanel();
        jRfecha_dia = new javax.swing.JRadioButton();
        jRfecha_semana = new javax.swing.JRadioButton();
        jRfecha_mes = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        txtfecha_hasta = new javax.swing.JTextField();
        jRcampo_cant = new javax.swing.JRadioButton();
        jRcampo_monto = new javax.swing.JRadioButton();
        panel_tabla_grafico = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblinsumo_producto = new javax.swing.JTable();
        jFsuma_cantidad = new javax.swing.JFormattedTextField();
        lblcant_descrip = new javax.swing.JLabel();
        jFsuma_monto = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        Panel_insumo = new javax.swing.JPanel();

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

        panel_insertar_pri_insumo.setBackground(new java.awt.Color(102, 153, 255));
        panel_insertar_pri_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("ID:");

        p1_txtid.setEditable(false);
        p1_txtid.setBackground(new java.awt.Color(204, 204, 204));
        p1_txtid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("MERMA %:");

        txtmerma.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtmerma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmermaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmermaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmermaKeyTyped(evt);
            }
        });

        txtprecio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecioKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("PRECIO COMPRA:");

        txtstock.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtstock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtstockKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstockKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("STOCK:");

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_unidad.setBackground(new java.awt.Color(204, 204, 255));
        jList_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_unidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_unidad.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_unidad.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_unidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_unidadMouseReleased(evt);
            }
        });
        jList_unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_unidadKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jList_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 180, -1));

        jList_categoria.setBackground(new java.awt.Color(204, 204, 255));
        jList_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_categoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_categoria.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_categoria.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_categoriaMouseReleased(evt);
            }
        });
        jList_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jList_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("UNIDAD COMPRA:");
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("CATEGORIA:");
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        p12_txtcategoria.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        p12_txtcategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p12_txtcategoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p12_txtcategoriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p12_txtcategoriaKeyTyped(evt);
            }
        });
        jLayeredPane1.add(p12_txtcategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 140, -1));

        p13_txtunidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        p13_txtunidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p13_txtunidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p13_txtunidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p13_txtunidadKeyTyped(evt);
            }
        });
        jLayeredPane1.add(p13_txtunidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 140, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, -1, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane1.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, -1, -1));

        btnnuevo_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_unidadActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 30, -1));

        btnnuevo_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_categoriaActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 30, -1));

        lblnom_conv_uni.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblnom_conv_uni.setText("jLabel17");
        jLayeredPane1.add(lblnom_conv_uni, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 170, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("PRECIO UTIL:");

        txtprecio_real.setEditable(false);
        txtprecio_real.setBackground(new java.awt.Color(204, 204, 204));
        txtprecio_real.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprecio_real.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_realKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_realKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("ACTIVAR:");

        jCactivar.setText("ACTIVAR");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("COD:");

        txtcod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_insumoLayout = new javax.swing.GroupLayout(panel_insertar_pri_insumo);
        panel_insertar_pri_insumo.setLayout(panel_insertar_pri_insumoLayout);
        panel_insertar_pri_insumoLayout.setHorizontalGroup(
            panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_insumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_insumoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_insertar_pri_insumoLayout.createSequentialGroup()
                                .addComponent(p1_txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtnombre)
                            .addComponent(txtmerma, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCactivar)
                            .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtprecio_real, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                .addComponent(txtprecio, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_insertar_pri_insumoLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_insertar_pri_insumoLayout.setVerticalGroup(
            panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_insumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(p1_txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtmerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtprecio_real, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertar_pri_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jCactivar))
                .addGap(24, 24, 24)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_tabla_insumo.setBackground(new java.awt.Color(102, 204, 255));
        panel_tabla_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblproducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        jLabel8.setText("CATEGORIA:");

        txtbuscar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_categoriaKeyReleased(evt);
            }
        });

        txtbuscar_unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_unidadKeyReleased(evt);
            }
        });

        jLabel9.setText("UNIDAD:");

        jLabel10.setText("NOMBRE:");

        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        jCactivos_select.setText("Activos");
        jCactivos_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCactivos_selectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_insumoLayout = new javax.swing.GroupLayout(panel_tabla_insumo);
        panel_tabla_insumo.setLayout(panel_tabla_insumoLayout);
        panel_tabla_insumoLayout.setHorizontalGroup(
            panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(panel_tabla_insumoLayout.createSequentialGroup()
                .addGroup(panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_insumoLayout.createSequentialGroup()
                        .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCactivos_select))
                    .addComponent(jLabel10))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        panel_tabla_insumoLayout.setVerticalGroup(
            panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_insumoLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCactivos_select))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(panel_insertar_pri_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar_pri_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("PRODUCTO INSUMO", jPanel3);

        panel_referencia_fecha.setBackground(new java.awt.Color(102, 204, 255));
        panel_referencia_fecha.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        fec_insu.add(jRfecha_dia);
        jRfecha_dia.setText("DIA");
        jRfecha_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRfecha_diaActionPerformed(evt);
            }
        });

        fec_insu.add(jRfecha_semana);
        jRfecha_semana.setSelected(true);
        jRfecha_semana.setText("SEMANA");
        jRfecha_semana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRfecha_semanaActionPerformed(evt);
            }
        });

        fec_insu.add(jRfecha_mes);
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

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        buttonGroup1.add(jRcampo_cant);
        jRcampo_cant.setSelected(true);
        jRcampo_cant.setText("CANTIDAD");
        jRcampo_cant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcampo_cantActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRcampo_monto);
        jRcampo_monto.setText("MONTO");
        jRcampo_monto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcampo_montoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_fechaLayout = new javax.swing.GroupLayout(panel_referencia_fecha);
        panel_referencia_fecha.setLayout(panel_referencia_fechaLayout);
        panel_referencia_fechaLayout.setHorizontalGroup(
            panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                        .addComponent(jRcampo_cant)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRcampo_monto))
                    .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                        .addComponent(jRfecha_dia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRfecha_semana)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRfecha_mes))
                    .addGroup(panel_referencia_fechaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtfecha_desde)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfecha_hasta))))
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
                    .addComponent(jRcampo_cant)
                    .addComponent(jRcampo_monto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_tabla_grafico.setBackground(new java.awt.Color(102, 204, 255));
        panel_tabla_grafico.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblinsumo_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblinsumo_producto);

        jFsuma_cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_cantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblcant_descrip.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblcant_descrip.setText("CANTIDAD:");

        jFsuma_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_monto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("MONTO:");

        javax.swing.GroupLayout panel_tabla_graficoLayout = new javax.swing.GroupLayout(panel_tabla_grafico);
        panel_tabla_grafico.setLayout(panel_tabla_graficoLayout);
        panel_tabla_graficoLayout.setHorizontalGroup(
            panel_tabla_graficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_tabla_graficoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_graficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblcant_descrip, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panel_tabla_graficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFsuma_monto, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panel_tabla_graficoLayout.setVerticalGroup(
            panel_tabla_graficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_graficoLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_graficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcant_descrip)
                    .addComponent(jFsuma_cantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_graficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        Panel_insumo.setBackground(new java.awt.Color(102, 204, 255));
        Panel_insumo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout Panel_insumoLayout = new javax.swing.GroupLayout(Panel_insumo);
        Panel_insumo.setLayout(Panel_insumoLayout);
        Panel_insumoLayout.setHorizontalGroup(
            Panel_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 774, Short.MAX_VALUE)
        );
        Panel_insumoLayout.setVerticalGroup(
            Panel_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_tabla_grafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_referencia_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Panel_insumo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addComponent(panel_referencia_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_tabla_grafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("GRAFICO INSUMO VENDIDO POR FECHA", panel_base_1);

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

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        pdao.ancho_tabla_insumo_producto(tblproducto);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtnombre, txtmerma);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtmermaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmermaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtmerma, txtprecio);
    }//GEN-LAST:event_txtmermaKeyPressed

    private void txtmermaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmermaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmermaKeyTyped

    private void txtprecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtprecio, txtstock);
    }//GEN-LAST:event_txtprecioKeyPressed

    private void txtprecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprecioKeyTyped

    private void txtstockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstockKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtstock, p12_txtcategoria);
    }//GEN-LAST:event_txtstockKeyPressed

    private void txtstockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstockKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtstockKeyTyped

    private void p12_txtcategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p12_txtcategoriaKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, p12_txtcategoria, p13_txtunidad);
        evejtf.seleccionar_lista(evt, jList_categoria);
        
    }//GEN-LAST:event_p12_txtcategoriaKeyPressed

    private void p12_txtcategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p12_txtcategoriaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_p12_txtcategoriaKeyTyped

    private void p13_txtunidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p13_txtunidadKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_unidad);
    }//GEN-LAST:event_p13_txtunidadKeyPressed

    private void p13_txtunidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p13_txtunidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_p13_txtunidadKeyTyped

    private void p12_txtcategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p12_txtcategoriaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, p12_txtcategoria, jList_categoria,cate.getTabla(),"nombre","nombre",4);
        isCargado_idcategoria=false;
    }//GEN-LAST:event_p12_txtcategoriaKeyReleased

    private void jList_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_categoriaMouseReleased
        // TODO add your handling code here:
        cargar_id_categoria();
    }//GEN-LAST:event_jList_categoriaMouseReleased

    private void jList_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jList_categoriaKeyReleased

    private void jList_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_categoria();
//            evejtf.saltar_campo_enter(evt, p12_txtcategoria, p13_txtunidad);
        }
    }//GEN-LAST:event_jList_categoriaKeyPressed

    private void jList_unidadMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_unidadMouseReleased
        // TODO add your handling code here:
        cargar_id_unidad();
    }//GEN-LAST:event_jList_unidadMouseReleased

    private void jList_unidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_unidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_unidad();
//            evejtf.saltar_campo_enter(evt, p13_txtunidad, p2_txtnombre);
        }
    }//GEN-LAST:event_jList_unidadKeyPressed

    private void p13_txtunidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p13_txtunidadKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, p13_txtunidad, jList_unidad,unid.getTabla(),"nom_compra","nom_compra",4);
        isCargado_idunidad=false;
    }//GEN-LAST:event_p13_txtunidadKeyReleased

    private void txtbuscar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_categoriaKeyReleased
        // TODO add your handling code here:
//        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_categoria,1);
    }//GEN-LAST:event_txtbuscar_categoriaKeyReleased

    private void txtbuscar_unidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_unidadKeyReleased
        // TODO add your handling code here:
//        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_unidad,2);
    }//GEN-LAST:event_txtbuscar_unidadKeyReleased

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
//        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_nombre,3);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtmermaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmermaKeyReleased
        // TODO add your handling code here:
        evejtf.cantidad_caracteres(txtmerma,7);
        calcular_precio_real();
    }//GEN-LAST:event_txtmermaKeyReleased

    private void txtprecio_realKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_realKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecio_realKeyPressed

    private void txtprecio_realKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_realKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecio_realKeyTyped

    private void txtprecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyReleased
        // TODO add your handling code here:
        calcular_precio_real();
    }//GEN-LAST:event_txtprecioKeyReleased

    private void jRfecha_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRfecha_diaActionPerformed
        // TODO add your handling code here:
        cargar_datos_graficos_insumo();
//        cargar_grafico_caja();
    }//GEN-LAST:event_jRfecha_diaActionPerformed

    private void jRfecha_semanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRfecha_semanaActionPerformed
        // TODO add your handling code here:
        cargar_datos_graficos_insumo();
//        cargar_grafico_caja();
    }//GEN-LAST:event_jRfecha_semanaActionPerformed

    private void jRfecha_mesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRfecha_mesActionPerformed
        // TODO add your handling code here:
        cargar_datos_graficos_insumo();
//        cargar_grafico_caja();
    }//GEN-LAST:event_jRfecha_mesActionPerformed

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_datos_graficos_insumo();
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_datos_graficos_insumo();
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void jRcampo_cantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcampo_cantActionPerformed
        // TODO add your handling code here:
        cargar_datos_graficos_insumo();
    }//GEN-LAST:event_jRcampo_cantActionPerformed

    private void jRcampo_montoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcampo_montoActionPerformed
        // TODO add your handling code here:
        cargar_datos_graficos_insumo();
    }//GEN-LAST:event_jRcampo_montoActionPerformed

    private void btnnuevo_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_categoriaActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmInsumo_categoria());
    }//GEN-LAST:event_btnnuevo_categoriaActionPerformed

    private void btnnuevo_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_unidadActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmInsumo_unidad());
    }//GEN-LAST:event_btnnuevo_unidadActionPerformed

    private void jCactivos_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCactivos_selectActionPerformed
        // TODO add your handling code here:
        activar_select(jCactivos_select.isSelected());
    }//GEN-LAST:event_jCactivos_selectActionPerformed

    private void txtcodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodKeyPressed

    private void txtcodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodKeyReleased

    private void txtcodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_insumo;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_categoria;
    private javax.swing.JButton btnnuevo_unidad;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup fec_insu;
    private javax.swing.JCheckBox jCactivar;
    private javax.swing.JCheckBox jCactivos_select;
    private javax.swing.JFormattedTextField jFsuma_cantidad;
    private javax.swing.JFormattedTextField jFsuma_monto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_categoria;
    private javax.swing.JList<String> jList_unidad;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRcampo_cant;
    private javax.swing.JRadioButton jRcampo_monto;
    private javax.swing.JRadioButton jRfecha_dia;
    private javax.swing.JRadioButton jRfecha_mes;
    private javax.swing.JRadioButton jRfecha_semana;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblcant_descrip;
    private javax.swing.JLabel lblnom_conv_uni;
    private javax.swing.JTextField p12_txtcategoria;
    private javax.swing.JTextField p13_txtunidad;
    private javax.swing.JTextField p1_txtid;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_insertar_pri_insumo;
    private javax.swing.JPanel panel_referencia_fecha;
    private javax.swing.JPanel panel_tabla_grafico;
    private javax.swing.JPanel panel_tabla_insumo;
    private javax.swing.JTable tblinsumo_producto;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTextField txtbuscar_categoria;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_unidad;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    private javax.swing.JTextField txtmerma;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtprecio;
    private javax.swing.JTextField txtprecio_real;
    private javax.swing.JTextField txtstock;
    // End of variables declaration//GEN-END:variables
}
