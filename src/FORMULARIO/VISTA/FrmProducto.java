/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Color.cla_color_pelete;
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
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Digno
 */
public class FrmProducto extends javax.swing.JInternalFrame {

    EvenJFRAME eveJfra = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenConexion eveconn = new EvenConexion();
    producto prod = new producto();
    insumo_producto inprod = new insumo_producto();
    DAO_insumo_producto inprodDAO = new DAO_insumo_producto();
    BO_producto pBO = new BO_producto();
    BO_item_producto_ingrediente ipiBO = new BO_item_producto_ingrediente();
    DAO_producto pdao = new DAO_producto();
    DAO_item_producto_ingrediente ipidao = new DAO_item_producto_ingrediente();
    BO_insumo_item_producto iipBO = new BO_insumo_item_producto();
    DAO_insumo_item_producto iipDAO = new DAO_insumo_item_producto();
    DAO_producto_grupo pgDAO = new DAO_producto_grupo();
    producto_categoria cate = new producto_categoria();
    producto_unidad unid = new producto_unidad();
    producto_ingrediente ingre = new producto_ingrediente();
    producto_grupo pgru = new producto_grupo();
    item_producto_ingrediente ipingre = new item_producto_ingrediente();
    insumo_item_producto iiprod = new insumo_item_producto();
    EvenJTextField evejtf = new EvenJTextField();
    EvenSQLDataSet evedata = new EvenSQLDataSet();
    FunFreeChard ffchar = new FunFreeChard();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor= new cla_color_pelete();
    private boolean isCargado_idcategoria;
    private boolean isCargado_idunidad;
    private double insumo_precio_sin_merma;
    private double insumo_conversion_unidad;
    private double insumo_merma;
    private double insumo_precio_con_merma;
    private double insumo_precio;

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("PRODUCTO");
        eveJfra.centrar_formulario(this);
        reestableser();
        pdao.actualizar_tabla_producto(conn, tblproducto);
        cargar_producto_grupo();
        color_formulario();
    }
    void color_formulario(){
        panel_insertar_producto.setBackground(clacolor.getColor_insertar_primario());
        panel_insertar_insumo.setBackground(clacolor.getColor_insertar_secundario());
        panel_tabla_producto.setBackground(clacolor.getColor_tabla());
        panel_tabla_ingrediente.setBackground(clacolor.getColor_tabla());
        panel_tabla_insumo.setBackground(clacolor.getColor_tabla());
        panel_referencia_precio.setBackground(clacolor.getColor_referencia());
        panel_base_1.setBackground(clacolor.getColor_base());
        panel_base_2.setBackground(clacolor.getColor_base());
    }
    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(p2_txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(p3_txtprecio_venta, "DEBE CARGAR UN PRECIO VENTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(p4_txtprecio_compra, "DEBE CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(p5_txtstock, "DEBE CARGAR UN STOCK")) {
            return false;
        }
        if (!isCargado_idcategoria) {
            JOptionPane.showMessageDialog(p12_txtcategoria, "NO SE CARGO CORRECTAMENTE LA CATEGORIA", "ERROR", JOptionPane.ERROR_MESSAGE);
            p12_txtcategoria.setText(null);
            p12_txtcategoria.grabFocus();
            return false;
        } else {
            p12_txtcategoria.setBackground(Color.white);
        }
        if (!isCargado_idunidad) {
            JOptionPane.showMessageDialog(p13_txtunidad, "NO SE CARGO CORRECTAMENTE LA UNIDAD", "ERROR", JOptionPane.ERROR_MESSAGE);
            p13_txtunidad.setText(null);
            p13_txtunidad.grabFocus();
            return false;
        } else {
            p13_txtunidad.setBackground(Color.white);
        }
        return true;
    }

    void boton_guardar() {
        if (validar_guardar()) {
            prod.setP2nombre(p2_txtnombre.getText());
            prod.setP3precio_venta(evejtf.getDouble_format_nro_entero1(p3_txtprecio_venta));
            prod.setP4precio_compra(evejtf.getDouble_format_nro_entero1(p4_txtprecio_compra));
            prod.setP5stock(Double.parseDouble(p5_txtstock.getText()));
            prod.setP6orden(0);
            prod.setP7activar(p7_jCactivar.isSelected());
            prod.setP8cocina(p8_jCcocina.isSelected());
            prod.setP9descontar_stock(p9_jCdescontar_stock.isSelected());
            prod.setP10comprar(p10_jCcompra.isSelected());
            prod.setP11vender(p11_jCvender.isSelected());
            if(jRgrupo_0.isSelected()){
                prod.setP14fk_idproducto_grupo(0);
            }if(jRgrupo_1.isSelected()){
                prod.setP14fk_idproducto_grupo(1);
            }
            pBO.insertar_producto(prod, tblproducto);
            reestableser();
        }
    }

    void boton_editar() {
        if (validar_guardar()) {
            prod.setP1idproducto(Integer.parseInt(p1_txtid.getText()));
            prod.setP2nombre(p2_txtnombre.getText());
            prod.setP3precio_venta(evejtf.getDouble_format_nro_entero1(p3_txtprecio_venta));
            prod.setP4precio_compra(evejtf.getDouble_format_nro_entero1(p4_txtprecio_compra));
            prod.setP5stock(Double.parseDouble(p5_txtstock.getText()));
            prod.setP6orden(0);
            prod.setP7activar(p7_jCactivar.isSelected());
            prod.setP8cocina(p8_jCcocina.isSelected());
            prod.setP9descontar_stock(p9_jCdescontar_stock.isSelected());
            prod.setP10comprar(p10_jCcompra.isSelected());
            prod.setP11vender(p11_jCvender.isSelected());
            if(jRgrupo_0.isSelected()){
                prod.setP14fk_idproducto_grupo(0);
            }if(jRgrupo_1.isSelected()){
                prod.setP14fk_idproducto_grupo(1);
            }
            pBO.update_producto(prod, tblproducto);
        }
    }

    void seleccionar_tabla() {
        int idproducto=eveJtab.getInt_select_id(tblproducto);
        pdao.cargar_producto(prod, idproducto);
        p1_txtid.setText(String.valueOf(prod.getP1idproducto()));
        iiprod.setFk_idproducto(prod.getP1idproducto());
        p2_txtnombre.setText(prod.getP2nombre());
        p3_txtprecio_venta.setText(evejtf.getString_format_nro_decimal(prod.getP3precio_venta()));
        p4_txtprecio_compra.setText(evejtf.getString_format_nro_decimal(prod.getP4precio_compra()));
        p5_txtstock.setText(evejtf.getString_format_nro_entero(prod.getP5stock()));
        p7_jCactivar.setSelected(prod.isP7activar());
        p8_jCcocina.setSelected(prod.isP8cocina());
        p9_jCdescontar_stock.setSelected(prod.isP9descontar_stock());
        p10_jCcompra.setSelected(prod.isP10comprar());
        p11_jCvender.setSelected(prod.isP11vender());
        p12_txtcategoria.setText(prod.getP12categoria());
        p13_txtunidad.setText(prod.getP13unidad());
        txtnombre_producto_venta.setText(prod.getP12categoria() + "-" + prod.getP2nombre() + "-" + prod.getP13unidad());
        int ingrediente = eveJtab.getInt_select(tblproducto, 3);
        if (ingrediente > 0) {
            actualizar_compra_ganacia();
        } else {
            producto_sin_ingrediente();
        }
        if(prod.getP14fk_idproducto_grupo()==0){
            jRgrupo_0.setSelected(true);
            jRgrupo_1.setSelected(false);
            System.out.println("grupo 0");
        }if(prod.getP14fk_idproducto_grupo()==1){
            jRgrupo_0.setSelected(false);
            jRgrupo_1.setSelected(true);
            System.out.println("grupo 1");
        }
        isCargado_idcategoria = true;
        isCargado_idunidad = true;
        ipidao.actualizar_tabla_item_producto_ingrediente_enproducto(conn, tblitem_ingrediente, Integer.parseInt(p1_txtid.getText()));
        iipDAO.actualizar_tabla_insumo_item_producto(conn, tblinsumo_item_producto, prod.getP1idproducto());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    void producto_sin_ingrediente() {
        double precio_compra_insumo = prod.getP4precio_compra();
        jFprecio_venta_prod.setValue(prod.getP3precio_venta());
        jFprecio_compra_prod.setValue(precio_compra_insumo);
        jFprecio_ganancia.setValue(prod.getP3precio_venta() - precio_compra_insumo);
        jFutilidad_ganancia.setValue(((prod.getP3precio_venta() - precio_compra_insumo) / prod.getP3precio_venta()) * 100);
        crear_grafico_precio_insumo();
    }

    void actualizar_compra_ganacia() {
        double precio_compra_insumo = iipDAO.suma_precio_insumo(conn, prod.getP1idproducto());
        jFprecio_venta_prod.setValue(prod.getP3precio_venta());
        jFprecio_compra_prod.setValue(precio_compra_insumo);
        jFprecio_ganancia.setValue(prod.getP3precio_venta() - precio_compra_insumo);
        jFutilidad_ganancia.setValue(((prod.getP3precio_venta() - precio_compra_insumo) / prod.getP3precio_venta()) * 100);
        prod.setP4precio_compra(precio_compra_insumo);
        pBO.update_producto_pre_compra(prod);
        crear_grafico_precio_insumo();
    }
    void cargar_producto_grupo(){
        pgDAO.cargar_producto_grupo(conn, pgru, 0);
        jRgrupo_0.setText(pgru.getC2nombre());
        pgDAO.cargar_producto_grupo(conn, pgru, 1);
        jRgrupo_1.setText(pgru.getC2nombre());
    }
    void reestableser() {
        isCargado_idcategoria = false;
        isCargado_idunidad = false;
        jList_categoria.setVisible(false);
        jList_unidad.setVisible(false);
        jList_ingrediente.setVisible(false);
        jList_insumo_producto.setVisible(false);
        p1_txtid.setText(null);
        p2_txtnombre.setText(null);
        p3_txtprecio_venta.setText(null);
        p4_txtprecio_compra.setText(null);
        p5_txtstock.setText(null);
        p7_jCactivar.setSelected(true);
        p8_jCcocina.setSelected(true);
        p9_jCdescontar_stock.setSelected(false);
        p10_jCcompra.setSelected(false);
        p11_jCvender.setSelected(true);
        p12_txtcategoria.setText(null);
        p13_txtunidad.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        p2_txtnombre.grabFocus();
    }

    void cargar_id_categoria() {
        int idcategoria = eveconn.getInt_seleccionar_JLista(conn, p12_txtcategoria, jList_categoria, cate.getTabla(), cate.getNombretabla(), cate.getIdtabla());
        prod.setP12fk_idproducto_categoria(idcategoria);
        isCargado_idcategoria = true;
        p13_txtunidad.grabFocus();
    }

    void cargar_id_unidad() {
        int idunidad = eveconn.getInt_seleccionar_JLista(conn, p13_txtunidad, jList_unidad, unid.getTabla(), unid.getNombretabla(), unid.getIdtabla());
        prod.setP13fk_idproducto_unidad(idunidad);
        isCargado_idunidad = true;
        p2_txtnombre.grabFocus();
    }

    void cargar_id_ingrediente() {
        int idproducto_ingrediente = eveconn.getInt_seleccionar_JLista(conn, txtbuscar_ingrediente, jList_ingrediente, ingre.getTabla(), ingre.getNombreTabla(), ingre.getIdtabla());
        System.out.println("idproducto_ingrediente:" + idproducto_ingrediente);
        if (validar_ingrediente()) {
            ipingre.setFk_idproducto(prod.getP1idproducto());
            ipingre.setFk_idproducto_ingrediente(idproducto_ingrediente);
            ipiBO.insertar_item_producto_ingrediente(ipingre, tblitem_ingrediente, Integer.parseInt(p1_txtid.getText()));
            txtbuscar_ingrediente.setText(null);
            txtbuscar_ingrediente.grabFocus();
        }

    }

    void cargar_id_insumo_producto() {
        int idinsumo_producto = eveconn.getInt_seleccionar_JLista(conn, txtinsumo_producto, jList_insumo_producto, "insumo_producto", "nombre", "idinsumo_producto");
        iiprod.setFk_idinsumo_producto(idinsumo_producto);
        lblnombre_insumo.setText("NOMBRE INSUMO:" + idinsumo_producto);
        inprodDAO.cargar_insumo_producto(inprod, idinsumo_producto);
        txtinsumo_unid_venta.setText(inprod.getNom_unidad_venta());
        insumo_precio_sin_merma = inprod.getPrecio();
        insumo_merma = inprod.getMerma();
        jFinsumo_precio_sin_merma.setValue(insumo_precio_sin_merma);
        insumo_precio_con_merma = (insumo_precio_sin_merma + ((insumo_precio_sin_merma * insumo_merma) / 100));
        jFinsumo_precio_con_merma.setValue(insumo_precio_con_merma);
        txtinsumo_unid_compra1.setText(inprod.getNom_unidad_compra());
        txtinsumo_unid_compra2.setText(inprod.getNom_unidad_compra());
        insumo_conversion_unidad = inprod.getConversion_unidad();
        txtinsumo_merma.setText(insumo_merma + "%");
        jFinsumo_precio_venta.setValue(0);
        txtinsumo_cantidad.setText(null);
        txtinsumo_cantidad.grabFocus();
    }

    boolean validar_ingrediente() {
        if (eveJtab.getBoolean_validar_select(tblproducto)) {
            txtbuscar_ingrediente.setText(null);
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(p1_txtid, "SELECCIONE LA TABLA PRODUCTO")) {
            return false;
        }
        return true;
    }

    boolean validar_eliminar_ingrediente() {
        if (eveJtab.getBoolean_validar_select(tblitem_ingrediente)) {
            return false;
        }
        return true;
    }

    void boton_eliminar_ingrediente() {
        if (validar_eliminar_ingrediente()) {
            int idingrediente = eveJtab.getInt_select_id(tblitem_ingrediente);
            ipingre.setIditem_producto_ingrediente(idingrediente);
            ipiBO.eliminar_item_producto_ingrediente(ipingre, tblitem_ingrediente, Integer.parseInt(p1_txtid.getText()));
        }
    }

    void boton_eliminar_insumo() {
        if (!eveJtab.getBoolean_validar_select(tblinsumo_item_producto)) {
            int Idinsumo_item_producto = eveJtab.getInt_select_id(tblinsumo_item_producto);
            iiprod.setIdinsumo_item_producto(Idinsumo_item_producto);
            iipBO.eliminar_insumo_item_producto(iiprod);
            iipDAO.actualizar_tabla_insumo_item_producto(conn, tblinsumo_item_producto, prod.getP1idproducto());
            actualizar_compra_ganacia();
        }
    }

    void limpiar_insumo() {
        txtinsumo_producto.setText(null);
        txtinsumo_unid_venta.setText(null);
        jFinsumo_precio_sin_merma.setValue(0);
        jFinsumo_precio_con_merma.setValue(0);
        txtinsumo_unid_compra1.setText(null);
        txtinsumo_unid_compra2.setText(null);
        txtinsumo_merma.setText(null);
        jFinsumo_precio_venta.setValue(0);
        txtinsumo_cantidad.setText(null);
        txtinsumo_producto.grabFocus();
    }

    boolean validar_carga_insumo() {
        if (eveJtab.getBoolean_validar_select(tblproducto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtinsumo_producto, "BUSCAR UN INSUMO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtinsumo_cantidad, "CARGAR UNA CANTIDAD EN SU UNIDAD DE VENTA")) {
            return false;
        }

        return true;
    }

    void boton_cargar_insumo() {
        if (validar_carga_insumo()) {
            double cantidad = Double.parseDouble(txtinsumo_cantidad.getText());
            iiprod.setCantidad(cantidad);
            iiprod.setPrecio(insumo_precio);
            iipBO.insertar_insumo_item_producto(iiprod);
            iipDAO.actualizar_tabla_insumo_item_producto(conn, tblinsumo_item_producto, prod.getP1idproducto());
            actualizar_compra_ganacia();
            limpiar_insumo();
        }
    }

    void crear_grafico_precio_insumo() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset = evedata.createDataset_precio_insumo(conn, prod.getP1idproducto());
        String titulo = "PRECIO INSUMO";
        ffchar.crear_grafico_circular(JPanel_grafico_insumo, dataset, titulo);
    }

    void boton_nuevo() {
        reestableser();
    }

    public FrmProducto() {
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

        pro_gru = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        panel_insertar_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        p1_txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        p2_txtnombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        p3_txtprecio_venta = new javax.swing.JTextField();
        p4_txtprecio_compra = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        p5_txtstock = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        p7_jCactivar = new javax.swing.JCheckBox();
        p8_jCcocina = new javax.swing.JCheckBox();
        p9_jCdescontar_stock = new javax.swing.JCheckBox();
        p10_jCcompra = new javax.swing.JCheckBox();
        p11_jCvender = new javax.swing.JCheckBox();
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
        jRgrupo_0 = new javax.swing.JRadioButton();
        jRgrupo_1 = new javax.swing.JRadioButton();
        panel_tabla_producto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtbuscar_categoria = new javax.swing.JTextField();
        txtbuscar_unidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        panel_tabla_ingrediente = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_ingrediente = new javax.swing.JTextField();
        jList_ingrediente = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_ingrediente = new javax.swing.JTable();
        btnnuevo_ingrediente = new javax.swing.JButton();
        btneliminar_ingrediente = new javax.swing.JButton();
        panel_base_2 = new javax.swing.JPanel();
        panel_insertar_insumo = new javax.swing.JPanel();
        jList_insumo_producto = new javax.swing.JList<>();
        txtinsumo_producto = new javax.swing.JTextField();
        lblnombre_insumo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtinsumo_cantidad = new javax.swing.JTextField();
        lblunidad_venta = new javax.swing.JLabel();
        lblinsumo_unid_compra = new javax.swing.JLabel();
        jFinsumo_precio_sin_merma = new javax.swing.JFormattedTextField();
        lblinsumo_compra = new javax.swing.JLabel();
        lblinsumo_compra1 = new javax.swing.JLabel();
        jFinsumo_precio_venta = new javax.swing.JFormattedTextField();
        txtinsumo_unid_venta = new javax.swing.JTextField();
        txtinsumo_unid_compra1 = new javax.swing.JTextField();
        lblinsumo_compra2 = new javax.swing.JLabel();
        jFinsumo_precio_con_merma = new javax.swing.JFormattedTextField();
        lblinsumo_unid_compra1 = new javax.swing.JLabel();
        txtinsumo_unid_compra2 = new javax.swing.JTextField();
        txtinsumo_merma = new javax.swing.JTextField();
        lblinsumo_compra3 = new javax.swing.JLabel();
        btncargar_insumo = new javax.swing.JButton();
        btnnuevo_insumo = new javax.swing.JButton();
        panel_tabla_insumo = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblinsumo_item_producto = new javax.swing.JTable();
        btneliminar_insumo = new javax.swing.JButton();
        panel_referencia_precio = new javax.swing.JPanel();
        txtnombre_producto_venta = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jFprecio_venta_prod = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jFprecio_compra_prod = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jFprecio_ganancia = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jFutilidad_ganancia = new javax.swing.JFormattedTextField();
        JPanel_grafico_insumo = new javax.swing.JPanel();

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

        panel_insertar_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ID:");

        p1_txtid.setEditable(false);
        p1_txtid.setBackground(new java.awt.Color(204, 204, 204));
        p1_txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        p2_txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        p2_txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p2_txtnombreKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("PRECIO VENTA:");

        p3_txtprecio_venta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        p3_txtprecio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p3_txtprecio_ventaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p3_txtprecio_ventaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p3_txtprecio_ventaKeyTyped(evt);
            }
        });

        p4_txtprecio_compra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        p4_txtprecio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p4_txtprecio_compraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p4_txtprecio_compraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p4_txtprecio_compraKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("PRECIO COMPRA:");

        p5_txtstock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        p5_txtstock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p5_txtstockKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p5_txtstockKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("STOCK:");

        p7_jCactivar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p7_jCactivar.setText("ACTIVAR (MOSTRAR)");

        p8_jCcocina.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p8_jCcocina.setText("PREPARAR EN COCINA");

        p9_jCdescontar_stock.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p9_jCdescontar_stock.setText("DESCONTAR STOCK");

        p10_jCcompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p10_jCcompra.setText("SE PUEDE COMPRAR");

        p11_jCvender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p11_jCvender.setText("SE PUEDE VENDER");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
        jLayeredPane1.add(jList_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 90, -1));

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
        jLayeredPane1.add(jList_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 120, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("UNIDAD:");
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("CATEGORIA:");
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        p12_txtcategoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        jLayeredPane1.add(p12_txtcategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, -1));

        p13_txtunidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        jLayeredPane1.add(p13_txtunidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 90, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 90, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 90, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane1.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 90, -1));

        btnnuevo_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_unidadActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 30, -1));

        btnnuevo_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_categoriaActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 30, -1));

        pro_gru.add(jRgrupo_0);
        jRgrupo_0.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRgrupo_0.setSelected(true);
        jRgrupo_0.setText("GRUPO-0");
        jLayeredPane1.add(jRgrupo_0, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 140, -1));

        pro_gru.add(jRgrupo_1);
        jRgrupo_1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRgrupo_1.setText("GRUPO-1");
        jLayeredPane1.add(jRgrupo_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 140, -1));

        javax.swing.GroupLayout panel_insertar_productoLayout = new javax.swing.GroupLayout(panel_insertar_producto);
        panel_insertar_producto.setLayout(panel_insertar_productoLayout);
        panel_insertar_productoLayout.setHorizontalGroup(
            panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(p2_txtnombre)
                        .addContainerGap())
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(p5_txtstock, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(p1_txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p3_txtprecio_venta)
                            .addComponent(p4_txtprecio_compra))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(p8_jCcocina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p11_jCvender))
                    .addComponent(p9_jCdescontar_stock)
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(p7_jCactivar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(p10_jCcompra)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        panel_insertar_productoLayout.setVerticalGroup(
            panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(p1_txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(p2_txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(p3_txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(p4_txtprecio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(p5_txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p7_jCactivar)
                    .addComponent(p10_jCcompra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p8_jCcocina)
                    .addComponent(p11_jCvender))
                .addGap(4, 4, 4)
                .addComponent(p9_jCdescontar_stock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_tabla_producto.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

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

        javax.swing.GroupLayout panel_tabla_productoLayout = new javax.swing.GroupLayout(panel_tabla_producto);
        panel_tabla_producto.setLayout(panel_tabla_productoLayout);
        panel_tabla_productoLayout.setHorizontalGroup(
            panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_productoLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addComponent(txtbuscar_unidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panel_tabla_productoLayout.setVerticalGroup(
            panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_productoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(37, 37, 37))
        );

        panel_tabla_ingrediente.setBackground(new java.awt.Color(51, 153, 255));
        panel_tabla_ingrediente.setBorder(javax.swing.BorderFactory.createTitledBorder("INGREDIENTES"));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("BUSCAR INGREDIENTE:");

        txtbuscar_ingrediente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbuscar_ingrediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_ingredienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_ingredienteKeyReleased(evt);
            }
        });

        jList_ingrediente.setBackground(new java.awt.Color(204, 204, 255));
        jList_ingrediente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jList_ingrediente.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_ingrediente.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_ingrediente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_ingredienteMouseReleased(evt);
            }
        });
        jList_ingrediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_ingredienteKeyPressed(evt);
            }
        });

        tblitem_ingrediente.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblitem_ingrediente);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnnuevo_ingrediente.setText("NUEVO INGREDIENTE");
        btnnuevo_ingrediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_ingredienteActionPerformed(evt);
            }
        });

        btneliminar_ingrediente.setText("ELIMINAR");
        btneliminar_ingrediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_ingredienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_ingredienteLayout = new javax.swing.GroupLayout(panel_tabla_ingrediente);
        panel_tabla_ingrediente.setLayout(panel_tabla_ingredienteLayout);
        panel_tabla_ingredienteLayout.setHorizontalGroup(
            panel_tabla_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ingredienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_tabla_ingredienteLayout.createSequentialGroup()
                .addComponent(btneliminar_ingrediente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnnuevo_ingrediente)
                .addGap(0, 149, Short.MAX_VALUE))
            .addGroup(panel_tabla_ingredienteLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_ingredienteLayout.createSequentialGroup()
                .addGroup(panel_tabla_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtbuscar_ingrediente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jList_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        panel_tabla_ingredienteLayout.setVerticalGroup(
            panel_tabla_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ingredienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jList_ingrediente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(panel_tabla_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btneliminar_ingrediente)
                    .addComponent(btnnuevo_ingrediente))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_insertar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("PRODUCTO PAVA VENTA", panel_base_1);

        panel_insertar_insumo.setBackground(new java.awt.Color(153, 153, 255));
        panel_insertar_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR INSUMO"));

        jList_insumo_producto.setBackground(new java.awt.Color(204, 204, 255));
        jList_insumo_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_insumo_producto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_insumo_producto.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_insumo_producto.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_insumo_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_insumo_productoMouseReleased(evt);
            }
        });
        jList_insumo_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_insumo_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jList_insumo_productoKeyReleased(evt);
            }
        });

        txtinsumo_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtinsumo_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtinsumo_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtinsumo_productoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsumo_productoKeyTyped(evt);
            }
        });

        lblnombre_insumo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblnombre_insumo.setText("NOMBRE INSUMO:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("CANTIDAD:");

        txtinsumo_cantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtinsumo_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtinsumo_cantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtinsumo_cantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsumo_cantidadKeyTyped(evt);
            }
        });

        lblunidad_venta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblunidad_venta.setText("UNI:");

        lblinsumo_unid_compra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblinsumo_unid_compra.setText("UNI:");

        jFinsumo_precio_sin_merma.setEditable(false);
        jFinsumo_precio_sin_merma.setBackground(new java.awt.Color(204, 204, 204));
        jFinsumo_precio_sin_merma.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFinsumo_precio_sin_merma.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblinsumo_compra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblinsumo_compra.setText("PRE. SIN MERMA:");

        lblinsumo_compra1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblinsumo_compra1.setText("PRECIO VENTA:");

        jFinsumo_precio_venta.setEditable(false);
        jFinsumo_precio_venta.setBackground(new java.awt.Color(204, 204, 204));
        jFinsumo_precio_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFinsumo_precio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFinsumo_precio_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtinsumo_unid_venta.setEditable(false);
        txtinsumo_unid_venta.setBackground(new java.awt.Color(204, 204, 204));
        txtinsumo_unid_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtinsumo_unid_compra1.setEditable(false);
        txtinsumo_unid_compra1.setBackground(new java.awt.Color(204, 204, 204));
        txtinsumo_unid_compra1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblinsumo_compra2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblinsumo_compra2.setText("PRE. CON MERMA:");

        jFinsumo_precio_con_merma.setEditable(false);
        jFinsumo_precio_con_merma.setBackground(new java.awt.Color(204, 204, 204));
        jFinsumo_precio_con_merma.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFinsumo_precio_con_merma.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblinsumo_unid_compra1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblinsumo_unid_compra1.setText("UNI:");

        txtinsumo_unid_compra2.setEditable(false);
        txtinsumo_unid_compra2.setBackground(new java.awt.Color(204, 204, 204));
        txtinsumo_unid_compra2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtinsumo_merma.setEditable(false);
        txtinsumo_merma.setBackground(new java.awt.Color(204, 204, 204));
        txtinsumo_merma.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblinsumo_compra3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblinsumo_compra3.setText("MERMA %:");

        btncargar_insumo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btncargar_insumo.setText("CARGAR");
        btncargar_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargar_insumoActionPerformed(evt);
            }
        });

        btnnuevo_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_insumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_insumoLayout = new javax.swing.GroupLayout(panel_insertar_insumo);
        panel_insertar_insumo.setLayout(panel_insertar_insumoLayout);
        panel_insertar_insumoLayout.setHorizontalGroup(
            panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblnombre_insumo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblinsumo_compra3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblinsumo_compra2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblinsumo_compra, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblinsumo_compra1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                        .addComponent(btncargar_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(155, 155, 155))
                    .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jFinsumo_precio_venta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(txtinsumo_cantidad, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFinsumo_precio_sin_merma, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFinsumo_precio_con_merma, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtinsumo_merma, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                                .addComponent(lblunidad_venta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtinsumo_unid_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                            .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                                .addComponent(lblinsumo_unid_compra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtinsumo_unid_compra1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_insumoLayout.createSequentialGroup()
                                .addComponent(lblinsumo_unid_compra1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtinsumo_unid_compra2)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_insumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jList_insumo_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtinsumo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(164, 164, 164))
        );
        panel_insertar_insumoLayout.setVerticalGroup(
            panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_insumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblnombre_insumo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtinsumo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_insumo))
                .addGap(2, 2, 2)
                .addComponent(jList_insumo_producto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtinsumo_merma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblinsumo_compra3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblinsumo_compra2)
                    .addComponent(jFinsumo_precio_con_merma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblinsumo_unid_compra1)
                    .addComponent(txtinsumo_unid_compra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblinsumo_compra)
                    .addComponent(jFinsumo_precio_sin_merma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblinsumo_unid_compra)
                    .addComponent(txtinsumo_unid_compra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(txtinsumo_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblunidad_venta)
                    .addComponent(txtinsumo_unid_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblinsumo_compra1)
                    .addComponent(jFinsumo_precio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncargar_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_tabla_insumo.setBackground(new java.awt.Color(102, 153, 255));
        panel_tabla_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA ITEM INSUMO"));

        tblinsumo_item_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblinsumo_item_producto);

        btneliminar_insumo.setText("ELIMINAR");
        btneliminar_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_insumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_insumoLayout = new javax.swing.GroupLayout(panel_tabla_insumo);
        panel_tabla_insumo.setLayout(panel_tabla_insumoLayout);
        panel_tabla_insumoLayout.setHorizontalGroup(
            panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
            .addGroup(panel_tabla_insumoLayout.createSequentialGroup()
                .addComponent(btneliminar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tabla_insumoLayout.setVerticalGroup(
            panel_tabla_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_insumoLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar_insumo))
        );

        panel_referencia_precio.setBackground(new java.awt.Color(102, 204, 255));
        panel_referencia_precio.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtnombre_producto_venta.setBackground(new java.awt.Color(102, 102, 102));
        txtnombre_producto_venta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnombre_producto_venta.setForeground(new java.awt.Color(255, 255, 0));
        txtnombre_producto_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("PRECIO VENTA:");

        jFprecio_venta_prod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFprecio_venta_prod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("PRECIO COMPRA:");

        jFprecio_compra_prod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFprecio_compra_prod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("PREC. GANANCIA:");

        jFprecio_ganancia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFprecio_ganancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("UTILIDAD:");

        jFutilidad_ganancia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00 '%'"))));
        jFutilidad_ganancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout panel_referencia_precioLayout = new javax.swing.GroupLayout(panel_referencia_precio);
        panel_referencia_precio.setLayout(panel_referencia_precioLayout);
        panel_referencia_precioLayout.setHorizontalGroup(
            panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_precioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnombre_producto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_referencia_precioLayout.createSequentialGroup()
                        .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFutilidad_ganancia, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(jFprecio_venta_prod)
                            .addComponent(jFprecio_ganancia)
                            .addComponent(jFprecio_compra_prod))))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        panel_referencia_precioLayout.setVerticalGroup(
            panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_precioLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txtnombre_producto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jFprecio_venta_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jFprecio_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jFprecio_compra_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jFutilidad_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        JPanel_grafico_insumo.setBackground(new java.awt.Color(153, 204, 255));
        JPanel_grafico_insumo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout JPanel_grafico_insumoLayout = new javax.swing.GroupLayout(JPanel_grafico_insumo);
        JPanel_grafico_insumo.setLayout(JPanel_grafico_insumoLayout);
        JPanel_grafico_insumoLayout.setHorizontalGroup(
            JPanel_grafico_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        JPanel_grafico_insumoLayout.setVerticalGroup(
            JPanel_grafico_insumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_base_2Layout = new javax.swing.GroupLayout(panel_base_2);
        panel_base_2.setLayout(panel_base_2Layout);
        panel_base_2Layout.setHorizontalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addGroup(panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_insertar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panel_referencia_precio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_tabla_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JPanel_grafico_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_base_2Layout.setVerticalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addComponent(panel_insertar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_referencia_precio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addComponent(panel_tabla_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPanel_grafico_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("INSUMO DEL PRODUCTO", panel_base_2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
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
        pdao.ancho_tabla_producto(tblproducto);
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

    private void p2_txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2_txtnombreKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, p2_txtnombre, p3_txtprecio_venta);
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            boton_guardar();
        }
    }//GEN-LAST:event_p2_txtnombreKeyPressed

    private void p3_txtprecio_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p3_txtprecio_ventaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, p3_txtprecio_venta, p4_txtprecio_compra);
    }//GEN-LAST:event_p3_txtprecio_ventaKeyPressed

    private void p3_txtprecio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p3_txtprecio_ventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_p3_txtprecio_ventaKeyTyped

    private void p4_txtprecio_compraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p4_txtprecio_compraKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, p4_txtprecio_compra, p5_txtstock);
    }//GEN-LAST:event_p4_txtprecio_compraKeyPressed

    private void p4_txtprecio_compraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p4_txtprecio_compraKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_p4_txtprecio_compraKeyTyped

    private void p5_txtstockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p5_txtstockKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, p5_txtstock, p12_txtcategoria);
    }//GEN-LAST:event_p5_txtstockKeyPressed

    private void p5_txtstockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p5_txtstockKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_p5_txtstockKeyTyped

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
        eveconn.buscar_cargar_Jlista(conn, p12_txtcategoria, jList_categoria, cate.getTabla(), cate.getNombretabla(), cate.getNombretabla(), 4);
        isCargado_idcategoria = false;
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
        eveconn.buscar_cargar_Jlista(conn, p13_txtunidad, jList_unidad, unid.getTabla(), unid.getNombretabla(), unid.getNombretabla(), 4);
        isCargado_idunidad = false;
    }//GEN-LAST:event_p13_txtunidadKeyReleased

    private void txtbuscar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_categoriaKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_categoria, 1);
    }//GEN-LAST:event_txtbuscar_categoriaKeyReleased

    private void txtbuscar_unidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_unidadKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_unidad, 2);
    }//GEN-LAST:event_txtbuscar_unidadKeyReleased

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_nombre, 3);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_ingredienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_ingredienteKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_ingrediente);
    }//GEN-LAST:event_txtbuscar_ingredienteKeyPressed

    private void txtbuscar_ingredienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_ingredienteKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbuscar_ingrediente, jList_ingrediente, ingre.getTabla(), ingre.getNombreTabla(), ingre.getNombreTabla(), 4);
    }//GEN-LAST:event_txtbuscar_ingredienteKeyReleased

    private void jList_ingredienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_ingredienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_ingrediente();
        }
    }//GEN-LAST:event_jList_ingredienteKeyPressed

    private void jList_ingredienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_ingredienteMouseReleased
        // TODO add your handling code here:
        cargar_id_ingrediente();
    }//GEN-LAST:event_jList_ingredienteMouseReleased

    private void btneliminar_ingredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_ingredienteActionPerformed
        // TODO add your handling code here:
        boton_eliminar_ingrediente();
    }//GEN-LAST:event_btneliminar_ingredienteActionPerformed

    private void btnnuevo_ingredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_ingredienteActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmProducto_Ingrediente());
    }//GEN-LAST:event_btnnuevo_ingredienteActionPerformed

    private void jList_insumo_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_insumo_productoMouseReleased
        // TODO add your handling code here:
        cargar_id_insumo_producto();
    }//GEN-LAST:event_jList_insumo_productoMouseReleased

    private void jList_insumo_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_insumo_productoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_insumo_producto();
            //            evejtf.saltar_campo_enter(evt, p12_txtcategoria, p13_txtunidad);
        }
    }//GEN-LAST:event_jList_insumo_productoKeyPressed

    private void jList_insumo_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_insumo_productoKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jList_insumo_productoKeyReleased

    private void txtinsumo_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_productoKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_insumo_producto);

    }//GEN-LAST:event_txtinsumo_productoKeyPressed

    private void txtinsumo_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_productoKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtinsumo_producto, jList_insumo_producto, "insumo_producto", "activar=true and nombre", "nombre", 4);
    }//GEN-LAST:event_txtinsumo_productoKeyReleased

    private void txtinsumo_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_productoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtinsumo_productoKeyTyped

    private void txtinsumo_cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_cantidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_cargar_insumo();
        }
    }//GEN-LAST:event_txtinsumo_cantidadKeyPressed

    private void txtinsumo_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_cantidadKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtinsumo_cantidadKeyTyped

    private void txtinsumo_cantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_cantidadKeyReleased
        // TODO add your handling code here:
        if (txtinsumo_cantidad.getText().trim().length() > 0) {
            double cantidad = Double.parseDouble(txtinsumo_cantidad.getText());
            insumo_precio = (insumo_precio_con_merma / insumo_conversion_unidad) * cantidad;
            jFinsumo_precio_venta.setValue(insumo_precio);

        }
    }//GEN-LAST:event_txtinsumo_cantidadKeyReleased

    private void btncargar_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargar_insumoActionPerformed
        // TODO add your handling code here:
        boton_cargar_insumo();
    }//GEN-LAST:event_btncargar_insumoActionPerformed

    private void btneliminar_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_insumoActionPerformed
        // TODO add your handling code here:
        boton_eliminar_insumo();
    }//GEN-LAST:event_btneliminar_insumoActionPerformed

    private void btnnuevo_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_categoriaActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_btnnuevo_categoriaActionPerformed

    private void btnnuevo_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_unidadActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmProducto_unidad());
    }//GEN-LAST:event_btnnuevo_unidadActionPerformed

    private void btnnuevo_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_insumoActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmInsumo_Producto());
    }//GEN-LAST:event_btnnuevo_insumoActionPerformed

    private void p3_txtprecio_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p3_txtprecio_ventaKeyReleased
        // TODO add your handling code here:
//        evejtf.getDouble_format_nro_entero(p3_txtprecio_venta);
    }//GEN-LAST:event_p3_txtprecio_ventaKeyReleased

    private void p4_txtprecio_compraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p4_txtprecio_compraKeyReleased
        // TODO add your handling code here:
//        evejtf.getDouble_format_nro_entero(p4_txtprecio_compra);
    }//GEN-LAST:event_p4_txtprecio_compraKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel_grafico_insumo;
    private javax.swing.JButton btncargar_insumo;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar_ingrediente;
    private javax.swing.JButton btneliminar_insumo;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_categoria;
    private javax.swing.JButton btnnuevo_ingrediente;
    private javax.swing.JButton btnnuevo_insumo;
    private javax.swing.JButton btnnuevo_unidad;
    private javax.swing.JFormattedTextField jFinsumo_precio_con_merma;
    private javax.swing.JFormattedTextField jFinsumo_precio_sin_merma;
    private javax.swing.JFormattedTextField jFinsumo_precio_venta;
    private javax.swing.JFormattedTextField jFprecio_compra_prod;
    private javax.swing.JFormattedTextField jFprecio_ganancia;
    private javax.swing.JFormattedTextField jFprecio_venta_prod;
    private javax.swing.JFormattedTextField jFutilidad_ganancia;
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
    private javax.swing.JList<String> jList_ingrediente;
    private javax.swing.JList<String> jList_insumo_producto;
    private javax.swing.JList<String> jList_unidad;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRgrupo_0;
    private javax.swing.JRadioButton jRgrupo_1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblinsumo_compra;
    private javax.swing.JLabel lblinsumo_compra1;
    private javax.swing.JLabel lblinsumo_compra2;
    private javax.swing.JLabel lblinsumo_compra3;
    private javax.swing.JLabel lblinsumo_unid_compra;
    private javax.swing.JLabel lblinsumo_unid_compra1;
    private javax.swing.JLabel lblnombre_insumo;
    private javax.swing.JLabel lblunidad_venta;
    private javax.swing.JCheckBox p10_jCcompra;
    private javax.swing.JCheckBox p11_jCvender;
    private javax.swing.JTextField p12_txtcategoria;
    private javax.swing.JTextField p13_txtunidad;
    private javax.swing.JTextField p1_txtid;
    private javax.swing.JTextField p2_txtnombre;
    private javax.swing.JTextField p3_txtprecio_venta;
    private javax.swing.JTextField p4_txtprecio_compra;
    private javax.swing.JTextField p5_txtstock;
    private javax.swing.JCheckBox p7_jCactivar;
    private javax.swing.JCheckBox p8_jCcocina;
    private javax.swing.JCheckBox p9_jCdescontar_stock;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_base_2;
    private javax.swing.JPanel panel_insertar_insumo;
    private javax.swing.JPanel panel_insertar_producto;
    private javax.swing.JPanel panel_referencia_precio;
    private javax.swing.JPanel panel_tabla_ingrediente;
    private javax.swing.JPanel panel_tabla_insumo;
    private javax.swing.JPanel panel_tabla_producto;
    private javax.swing.ButtonGroup pro_gru;
    private javax.swing.JTable tblinsumo_item_producto;
    private javax.swing.JTable tblitem_ingrediente;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTextField txtbuscar_categoria;
    private javax.swing.JTextField txtbuscar_ingrediente;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_unidad;
    private javax.swing.JTextField txtinsumo_cantidad;
    private javax.swing.JTextField txtinsumo_merma;
    private javax.swing.JTextField txtinsumo_producto;
    private javax.swing.JTextField txtinsumo_unid_compra1;
    private javax.swing.JTextField txtinsumo_unid_compra2;
    private javax.swing.JTextField txtinsumo_unid_venta;
    private javax.swing.JTextField txtnombre_producto_venta;
    // End of variables declaration//GEN-END:variables
}
