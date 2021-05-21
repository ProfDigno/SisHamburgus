/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import FORMULARIO.ENTIDAD.cotizacion;
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
import Evento.Utilitario.EvenUtil;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.BO.BO_cliente;
import FORMULARIO.BO.BO_item_venta_mesa;
import FORMULARIO.BO.BO_item_venta_mesa_venta;
import FORMULARIO.BO.BO_itemven_insumo;
import FORMULARIO.BO.BO_venta;
import FORMULARIO.BO.BO_venta_mesa;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import static FORMULARIO.VISTA.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.FrmCliente.txtzona;
import IMPRESORA_POS.PosImprimir_Venta;
import IMPRESORA_POS.PosImprimir_comanda;
import IMPRESORA_POS.PosImprimir_venta_mesa;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmVenta extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    PosImprimir_Venta posv = new PosImprimir_Venta();
    PosImprimir_comanda posco = new PosImprimir_comanda();
    cliente clie = new cliente();
    item_venta item = new item_venta();
    DAO_item_venta ivdao = new DAO_item_venta();
    venta ven = new venta();
    BO_venta vBO = new BO_venta();
    DAO_venta vdao = new DAO_venta();
    factura fac = new factura();
    DAO_cliente cdao = new DAO_cliente();
    producto prod = new producto();
    DAO_producto pdao = new DAO_producto();
    producto_ingrediente ingre = new producto_ingrediente();
    DAO_producto_ingrediente pidao = new DAO_producto_ingrediente();
    DAO_item_producto_ingrediente ipidao = new DAO_item_producto_ingrediente();
    DAO_item_venta_mesa ivmdao = new DAO_item_venta_mesa();
    BO_item_venta_mesa ivmBO = new BO_item_venta_mesa();
    item_venta_mesa ivmesa = new item_venta_mesa();
    DAO_item_venta_mesa_venta ivmvdao = new DAO_item_venta_mesa_venta();
    BO_item_venta_mesa_venta ivmvBO = new BO_item_venta_mesa_venta();
    item_venta_mesa_venta ivmv = new item_venta_mesa_venta();
    BO_venta_mesa vmBO = new BO_venta_mesa();
    DAO_venta_mesa vmDAO = new DAO_venta_mesa();
    venta_mesa vmesa = new venta_mesa();
    caja_detalle caja = new caja_detalle();
    BO_cliente cBO = new BO_cliente();
    zona_delivery zona = new zona_delivery();
    DAO_zona_delivery zdao = new DAO_zona_delivery();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    usuario usu = new usuario();
    DAO_comanda coma = new DAO_comanda();
    itemven_insumo iteminsu = new itemven_insumo();
    BO_itemven_insumo iviBO = new BO_itemven_insumo();
    DAO_entregador endao = new DAO_entregador();
    Connection connLocal = ConnPostgres.getConnPosgres();
    private java.util.List<JButton> botones_categoria;
    private java.util.List<JButton> botones_unidad;
    private java.util.List<JButton> botones_mesa;
    DefaultTableModel model_itemf = new DefaultTableModel();
    cla_color_pelete clacolor = new cla_color_pelete();
    PosImprimir_venta_mesa posmesa = new PosImprimir_venta_mesa();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private DAO_venta_mesa vmesa_dao = new DAO_venta_mesa();
    private DAO_item_venta_mesa ivm_dao = new DAO_item_venta_mesa();
    private int cant_boton_cate;
    private int cant_boton_unid;
    private String fk_idproducto_unidad;
    private String fk_idproducto_categoria;
    private String cantidad_producto = "0";
//    private int fk_idcliente_servi;
    private double monto_venta;
    private double monto_delivery;
    private String zona_delivery;
    private String tipo_entrega;
    String linea_aux_entrega1 = "#>";
    String linea_aux_entrega2 = "<#";
    private String ent_comer_aca = "COMER_ACA";
    private String ent_delivery = "DELIVERY";
    private String ent_buscar_pedido = "BUSCAR_PED";
    private String entrega_comer_aca = linea_aux_entrega1 + ent_comer_aca + linea_aux_entrega2;
    private String entrega_delivery = linea_aux_entrega1 + ent_delivery + linea_aux_entrega2;
    private String entrega_buscar_pedido = linea_aux_entrega1 + ent_buscar_pedido + linea_aux_entrega2;
    private String entrega_funcio = "##>>FUNCIONARIO<<##";
    boolean esBoton_comer_aca = false;
    boolean esBoton_buscar_pedido = false;
    private String estado = "EMITIDO";
    private int idventa_ultimo;
    private String indice_venta;
    private int fk_idcliente_local;
    private int fk_idzona_corto;
    private boolean esFuncionario;
    private String idventa_mesa_select;
    private boolean hab_boton_cerrar;
    private int indice_mesa;
    private boolean isCargar_venta_mesa;
    private boolean esMesa_LIBRE;
    private String nombre_mesa;
    private String grupo_en_ingrediente;
    private boolean habilitar_crear_cliente_corto;
    private int idventa_ult_print;
    private boolean habilitar_cargado_zona;
    private String cli_tel_oca = "0000";
    private String cli_ruc_oca = "44444401";
    private String cli_dir_oca = "Sin Direccion";
    private String tipo_cliente = "cliente";
    private String pago_efectivo="EFECTIVO";
    private String pago_tarjeta="TARJETA";
    private String forma_pago;

    void abrir_formulario() {
        String servidor = "";
        this.setTitle("VENTA--> USUARIO:" + usu.getGlobal_nombre() + servidor);
        btnagregar_local.setText(ent_comer_aca);
        btnagregar_paquete.setText(ent_buscar_pedido);
        btnagregar_delivery.setText(ent_delivery);
        evetbl.centrar_formulario(this);
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
//        jList_cliente.setVisible(false);
        codao.cargar_cotizacion(coti, 1);
        botones_mesa = new ArrayList<>();
        cargar_botones_mesa();
        endao.actualizar_tabla_entregador_venta(connLocal, tblentregador);
        color_formulario();
        color_jpanel();
        reestableser_venta();
        cargar_boton_categoria();
        crear_item_producto();
    }

    void color_formulario() {
        panel_tabla_busca_cli.setBackground(clacolor.getColor_tabla());
        panel_insertar_pri_item.setBackground(clacolor.getColor_insertar_primario());
        panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        panel_insertar_sec_ingrediente.setBackground(clacolor.getColor_insertar_secundario());
        panel_referencia_categoria.setBackground(clacolor.getColor_tabla());
        panel_referencia_unidad.setBackground(clacolor.getColor_tabla());
        panel_tabla_venta.setBackground(clacolor.getColor_tabla());
        panel_tabla_item.setBackground(clacolor.getColor_tabla());
        panel_tabla_entregador.setBackground(clacolor.getColor_tabla());
        panel_referencia_venta.setBackground(clacolor.getColor_referencia());
        panel_insertar_sec_cliente.setBackground(clacolor.getColor_insertar_secundario());
        panel_tabla_cliente.setBackground(clacolor.getColor_tabla());
        panel_base_1.setBackground(new Color(91,109,91));
        panel_base_2.setBackground(clacolor.getColor_base());
    }

    void color_jpanel() {
        if (ven.isVenta_aux()) {
            panel_referencia_categoria.setBackground(Color.orange);
            panel_referencia_unidad.setBackground(Color.orange);
            panel_insertar_pri_producto.setBackground(Color.orange);
            panel_insertar_pri_item.setBackground(Color.orange);
            panel_tabla_venta.setBackground(Color.orange);
            panel_insertar_sec_cliente.setBackground(Color.orange);
            panel_tabla_busca_cli.setBackground(Color.orange);
//            btnventaaux.setEnabled(false);
        }
    }

    void crear_item_producto() {
        String dato[] = {"id", "T", "descripcion", "precio", "C", "total", "g"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    void ancho_item_producto() {
        int Ancho[] = {5, 5, 53, 15, 6, 14, 2};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
    }

    boolean validar_cargar_item_producto() {
        if (evejt.getBoolean_validar_select(tblproducto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_producto, "CARGAR UNA CANTIDAD")) {
            return false;
        }
        return true;
    }

    boolean validar_cargar_ingrediente() {
        if (evejt.getBoolean_validar_select(tblitem_producto)) {
            return false;
        }
        if (evejt.getBoolean_validar_select(tblingrediente)) {
            return false;
        }
        if (evejt.getString_select(tblitem_producto, 1).equals("I")) {
            JOptionPane.showMessageDialog(null, "EL INGREDIENTE NO PUEDE SER SELECCIONADO");
            return false;
        }
        return true;
    }

    void sumar_item_venta() {
        double total_guarani = evejt.getDouble_sumar_tabla(tblitem_producto, 5);
        monto_venta = total_guarani;
        jFtotal_guarani.setValue(total_guarani);
        jFtotal_dolar.setValue(total_guarani / coti.getDolar_guarani_STATIC());
        jFtotal_real.setValue(total_guarani / coti.getReal_guarani_STATIC());
        everende.rendertabla_item_venta(tblitem_producto);
    }

    void cargar_ingrediente(int tipo) {
        if (validar_cargar_ingrediente()) {
            String agregar = "";
            String idproducto_ingrediente = "";
            String descripcion = "";
            String precioUni = "";
            String cantidad = "1";
            String total = "";
            String grupo = grupo_en_ingrediente;
            if (tipo == 1) {
                agregar = "--(SIN)>";
                precioUni = "0";
                total = "0";
            }
            if (tipo == 2) {
                agregar = "++(AGRE)>";
                precioUni = evejt.getString_select(tblingrediente, 2);
                total = evejt.getString_select(tblingrediente, 2);
            }
            idproducto_ingrediente = evejt.getString_select(tblingrediente, 0);
            descripcion = agregar + evejt.getString_select(tblingrediente, 1);
            cantidad = "1";
            String dato[] = {idproducto_ingrediente, "I", descripcion, precioUni, cantidad, total, grupo};
            evejt.cargar_tabla_dato_bajolinea(tblitem_producto, model_itemf, dato);
            int idproducto = evejt.getInt_select_id(tblitem_producto);
            ipidao.actualizar_tabla_item_producto_ingrediente(connLocal, tblingrediente, idproducto);
            sumar_item_venta();
        }
    }

    void cargar_directo_delivery() {
        if (!esBoton_comer_aca && !esBoton_buscar_pedido) {
            if (fk_idcliente_local > 1) {
                color_boton_entrega(Color.white, Color.white, Color.orange);
                evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
                tipo_entrega = entrega_delivery;
                double precio = monto_delivery;
                String idproducto = "0";
                String descripcion = "#DELIVERY#>>" + zona_delivery;
                String precioUni = String.valueOf(precio);
                String cantidad = "1";
                double Dcantidad = Double.parseDouble(cantidad);
                String total = String.valueOf(precio * Dcantidad);
                String grupo = "0";
                String dato[] = {idproducto, "D", descripcion, precioUni, cantidad, total, grupo};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
                txtcantidad_producto.setText("1");
                sumar_item_venta();
            }
        }
    }

    void boton_tipo_entrega_Delivery() {
        esBoton_comer_aca = false;
        esBoton_buscar_pedido = false;
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        if (fk_idcliente_local > 1) {
            color_boton_entrega(Color.white, Color.white, Color.orange);
            evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
            tipo_entrega = entrega_delivery;
            double precio = monto_delivery;
            String idproducto = "0";
            String descripcion = "#DELIVERY#>>" + zona_delivery;
            String precioUni = String.valueOf(precio);
            String cantidad = "1";
            double Dcantidad = Double.parseDouble(cantidad);
            String total = String.valueOf(precio * Dcantidad);
            String grupo = "0";
            String dato[] = {idproducto, "D", descripcion, precioUni, cantidad, total, grupo};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText("1");
            sumar_item_venta();
        } else {
            JOptionPane.showMessageDialog(txtbucarCliente_nombre, "NO SE ENCONTRO NINGUN CLIENTE CARGADO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    void copiar_itemventa(int idventa) {
        String titulo = "duplicar_itemventa";
        String sql = "select fk_idproducto,descripcion,precio_venta,cantidad,tipo,grupo "
                + "from item_venta where fk_idventa=" + idventa
                + " order by iditem_venta asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String idproducto = rs.getString("fk_idproducto");
                String descripcion = rs.getString("descripcion");
                String precioUni = rs.getString("precio_venta");
                String cantidad = rs.getString("cantidad");
                String grupo = rs.getString("grupo");
                double Dcantidad = Double.parseDouble(cantidad);
                double DprecioUni = Double.parseDouble(precioUni);
                String total = String.valueOf(DprecioUni * Dcantidad);
                String tipo = rs.getString("tipo");
                String dato[] = {idproducto, tipo, descripcion, precioUni, cantidad, total, grupo};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    void cargar_item_producto() {
        if (validar_cargar_item_producto()) {
            int idproducto_select = evejt.getInt_select_id(tblproducto);
            pdao.cargar_producto(prod, idproducto_select);
            String idproducto = String.valueOf(prod.getP1idproducto());
//            String descripcion = prod.getP12categoria() + "-" + prod.getP13unidad() + "-" + prod.getP2nombre();
            String descripcion = prod.getP13unidad() + "-" + prod.getP2nombre();
            int IprecioUni = (int) prod.getP3precio_venta();
            String precioUni = String.valueOf(IprecioUni);
            String cantidad = txtcantidad_producto.getText();
            int Dcantidad = Integer.parseInt(cantidad);
            int subtotal = Dcantidad * IprecioUni;
            String total = String.valueOf(subtotal);
            String tipo = "";
            if (prod.isP8cocina()) {
                tipo = "P";
            } else {
                tipo = "N";
            }
            String grupo = String.valueOf(prod.getP14fk_idproducto_grupo());
            String dato[] = {idproducto, tipo, descripcion, precioUni, cantidad, total, grupo};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText("1");
            sumar_item_venta();
        }
    }

    void cargar_item_producto_observacion() {
        if (validar_cargar_item_producto()) {
            String idproducto = "0";
            String descripcion = "##OBS: " + txtobservacion_ingre.getText();
            String precioUni = "0";
            String cantidad = "1";
            String total = "0";
            String tipo = "I";
            String grupo = "0";
            String dato[] = {idproducto, tipo, descripcion, precioUni, cantidad, total, grupo};
            evejt.cargar_tabla_dato_bajolinea(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText("1");
            txtobservacion_ingre.setText(null);
            sumar_item_venta();
        }
    }

    void cargar_item_producto_descontar() {
        if (txtdescontar.getText().trim().length() > 0) {
            String idproducto = "0";
            String descripcion = "##DESCONTAR:";
            String precioUni = "-" + evejtf.getDouble_convertir_JTextField(txtdescontar);
            String cantidad = "1";
            String total = "-" + evejtf.getDouble_convertir_JTextField(txtdescontar);
            String tipo = "S";
            String grupo = "0";
            String dato[] = {idproducto, tipo, descripcion, precioUni, cantidad, total, grupo};
            evejt.cargar_tabla_dato_ultima_linea(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText("1");
            txtobservacion_ingre.setText(null);
            sumar_item_venta();
        }
    }

    private void cargar_boton_categoria() {
        String titulo = "cargar_boton_categoria";
        String sql = "SELECT  c.idproducto_categoria, c.nombre,c.orden \n"
                + "From producto_categoria c,producto p \n"
                + "where c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and c.activar=true \n"
                + "and p.activar=true \n"
                + "group by 1,2,3\n"
                + "order by c.orden asc ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            panel_referencia_categoria.removeAll();
            botones_categoria.clear();
            int cant = 0;
            while (rs.next()) {
                cant++;
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_categoria");
                crear_boton_categoria(textboton, nameboton);

                if (cant == 1) {
                    fk_idproducto_categoria = nameboton;
                    actualizar_tabla_producto(1);
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void crear_boton_categoria(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        panel_referencia_categoria.add(boton);
        botones_categoria.add(boton);
        cant_boton_cate++;
        boton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_cate; p++) {
                    botones_categoria.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_categoria = ((JButton) e.getSource()).getName();
                cargar_boton_unidad(fk_idproducto_categoria);
                System.out.println("fk_idproducto_categoria:" + fk_idproducto_categoria);
                actualizar_tabla_producto(1);
            }
        });
        panel_referencia_categoria.updateUI();
    }

    void cargar_boton_unidad(String idproducto_categoria) {
        String titulo = "cargar_boton_unidad";
        panel_referencia_unidad.removeAll();
        botones_unidad.clear();
        cant_boton_unid = 0;
        String sql = "select u.idproducto_unidad, u.nombre \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_unidad");
                boton_agregar_unidad(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void boton_agregar_unidad(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_unidad.add(boton);
        botones_unidad.add(boton);
        cant_boton_unid++;
        boton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_unid; p++) {
                    botones_unidad.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_unidad = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_unidad: " + fk_idproducto_unidad);
                actualizar_tabla_producto(2);
            }
        });
        panel_referencia_unidad.updateUI();
    }

    void actualizar_tabla_producto(int tipo) {
        String filtro_categoria = "";
        String filtro_unidad = "";
        String filtro_producto = "";
        if (tipo == 1) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
        }
        if (tipo == 2) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_unidad = " and p.fk_idproducto_unidad=" + fk_idproducto_unidad;
        }
        if (tipo == 3) {
            if (txtbuscar_producto.getText().trim().length() > 0) {
                String por = "%";
                String temp = por + txtbuscar_producto.getText() + por;
                filtro_producto = " and p.nombre ilike'" + temp + "' ";
                filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            }
        }
        String sql = "select p.idproducto,\n"
                + "(c.nombre||'-'||u.nombre||'-'||p.nombre) as producto,\n"
                //                + "(u.nombre||'-'||p.nombre) as producto,\n"
                + "TRIM(to_char(p.precio_venta,'999G999G999')) as preciov \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and c.activar=true \n"
                + "and p.activar=true \n" + filtro_categoria + filtro_unidad + filtro_producto
                + " order by p.orden  asc";
        eveconn.Select_cargar_jtable(connLocal, sql, tblproducto);
        ancho_tabla_producto();
    }

    void ancho_tabla_producto() {
        int Ancho[] = {10, 75, 15};
        evejt.setAnchoColumnaJtable(tblproducto, Ancho);
    }

    void actualizar_tabla_ingrediente() {
        if (evejt.getString_select(tblitem_producto, 1).equals("P")) {
            grupo_en_ingrediente = evejt.getString_select(tblitem_producto, 6);
            int idproducto = evejt.getInt_select_id(tblitem_producto);
            evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 1);
            ipidao.actualizar_tabla_item_producto_ingrediente(connLocal, tblingrediente, idproducto);
            int idproducto_select = evejt.getInt_select_id(tblitem_producto);
            pdao.cargar_producto(prod, idproducto_select);
            String descripcion = prod.getP12categoria() + "-" + prod.getP13unidad() + "-" + prod.getP2nombre();
            lblnombre_producto_ingre.setText(descripcion);
            txtobservacion_ingre.grabFocus();
        }
        if (evejt.getString_select(tblitem_producto, 1).equals("I")) {
            evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        }
        if (evejt.getString_select(tblitem_producto, 1).equals("N")) {
            evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        }
    }

    void cargar_cantidad_producto(int cant) {
        txtcantidad_producto.setText(String.valueOf(cant));
        cargar_item_producto();
    }

    void color_boton_entrega(Color local, Color paquete, Color delivery) {
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        btnagregar_local.setBackground(local);
        btnagregar_paquete.setBackground(paquete);
        btnagregar_delivery.setBackground(delivery);
    }

    void boton_tipo_entrega_Local() {
        color_boton_entrega(Color.orange, Color.white, Color.white);
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        sumar_item_venta();
        esBoton_comer_aca = true;
        tipo_entrega = entrega_comer_aca;
    }

    void boton_tipo_entrega_Paquete() {
        color_boton_entrega(Color.white, Color.orange, Color.white);
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        sumar_item_venta();
        esBoton_buscar_pedido = true;
        tipo_entrega = entrega_buscar_pedido;
    }

    int buscar_cliente_por_telefono(JTextField txttelefono) {
        int idcliente = 0;
        String titulo = "buscar_cliente_por_telefono";
        if (txttelefono.getText().trim().length() > 8) {
            String sql = "select idcliente from cliente "
                    + "where telefono='" + txttelefono.getText() + "'";
            try {
                ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
                if (rs.next()) {
                    idcliente = rs.getInt("idcliente");
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        }
        return idcliente;
    }

    int buscar_cliente_ocacional(String nombre, String direccion, String telefono, String ruc) {
        int idcliente = 0;
        String titulo = "buscar_cliente_por_telefono";
        String sql = "select idcliente from cliente "
                + "where nombre='" + nombre + "' and direccion='" + direccion + "' and telefono='" + telefono + "' and ruc='" + ruc + "' ;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (rs.next()) {
                idcliente = rs.getInt("idcliente");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return idcliente;
    }

    int buscar_zona_por_nombre() {
        int idzona = 0;
        String titulo = "buscar_zona_por_nombre";
        if (txtbucarCliente_zona.getText().trim().length() > 0) {
            String sql = "select idzona_delivery from zona_delivery where nombre='" + txtbucarCliente_zona.getText() + "'";
            try {
                ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
                if (rs.next()) {
                    idzona = rs.getInt("idzona_delivery");
                    txtbucarCliente_zona.setBackground(Color.white);
                } else {
                    txtbucarCliente_zona.setBackground(Color.red);
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        }
        return idzona;
    }

    void cargar_cliente_desde_telefono() {
        int idcliente = buscar_cliente_por_telefono(txtbucarCliente_telefono);
        if (idcliente > 1) {
            fk_idcliente_local = idcliente;
            cdao.cargar_cliente(connLocal, clie, fk_idcliente_local);
            cargar_cliente_principal(clie);
            cargar_directo_delivery();
            tblproducto.grabFocus();
        }
    }

    void cargar_zona_desde_nombre() {
        int idzona = buscar_zona_por_nombre();
        if (idzona > 0) {
            fk_idzona_corto = idzona;
            zdao.cargar_zona_delivery(zona, fk_idzona_corto);
            jFPrecio_zona.setValue(zona.getDelivery());
            habilitar_cargado_zona = true;
        } else {
            jFPrecio_zona.setValue(0);
        }
    }

    private void guardar_cliente_corto() {
        if (habilitar_crear_cliente_corto) {
            if (validar_guardar_cliente_corto()) {
                clie.setC2fecha_inicio("now");
                clie.setC3nombre(txtbucarCliente_nombre.getText());
                clie.setC4direccion(txtbucarCliente_direccion.getText());
                clie.setC5telefono(txtbucarCliente_telefono.getText());
                clie.setC6ruc(txtbucarCliente_ruc.getText());
                clie.setC7fecha_cumple(evefec.getString_formato_fecha());
                clie.setC8tipo(tipo_cliente);
                clie.setC9fk_idzona_delivery(fk_idzona_corto);
                int idcliente = buscar_cliente_por_telefono(txtbucarCliente_telefono);
                if (idcliente == 0) {
                    int idcli = buscar_cliente_ocacional(clie.getC3nombre(), clie.getC4direccion(), clie.getC5telefono(), clie.getC6ruc());
                    if (idcli == 0) {
                        cBO.insertar_cliente_directo(connLocal, clie, true);
//                        if(txtbucarCliente_telefono.getText().trim().length()<=6){
//                            boton_tipo_entrega_Local();
//                        }
                        seleccionar_cargar_cliente(2);
                    } else {
                        fk_idcliente_local = idcli;
//                        if(txtbucarCliente_telefono.getText().trim().length()<=6){
//                            boton_tipo_entrega_Local();
//                        }
                        seleccionar_cargar_cliente(1);
                    }
                } else {
                    fk_idcliente_local = idcliente;
                    seleccionar_cargar_cliente(1);
                }

                tblitem_producto.grabFocus();
            }
            System.out.println("fk_idzona_corto: " + fk_idzona_corto);
            System.out.println("fk_idcliente_local: " + fk_idcliente_local);
            System.out.println("habilitar_crear_cliente_corto: " + habilitar_crear_cliente_corto);
        } else {
            if (fk_idcliente_local == 1) {
                txtbucarCliente_nombre.setText("OCACIONAL");
                txtbucarCliente_telefono.setText(cli_tel_oca);
                txtbucarCliente_ruc.setText(cli_ruc_oca);
                txtbucarCliente_direccion.setText(cli_dir_oca);
            }
            System.out.println("fk_idzona_corto: " + fk_idzona_corto);
            System.out.println("fk_idcliente_local: " + fk_idcliente_local);
            System.out.println("habilitar_crear_cliente_corto: " + habilitar_crear_cliente_corto);
            if (fk_idcliente_local > 1 && fk_idzona_corto > 1 && habilitar_cargado_zona) {
                if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE MUDAR LA ZONA DEL CLIENTE", "MUDAR ZONA", "ACEPTAR", "CANCELAR")) {
                    clie.setC4direccion(txtbucarCliente_direccion.getText());
                    clie.setC9fk_idzona_delivery(fk_idzona_corto);
                    clie.setC1idcliente(fk_idcliente_local);
                    cBO.update_cliente_MUDAR_ZONA(clie);
                    seleccionar_cargar_cliente(1);
                    habilitar_cargado_zona = false;
                }
            }
        }
    }

    void limpiar_cliente() {
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        tipo_entrega = entrega_comer_aca;
        lblidcliente.setText("id:0");
        fk_idcliente_local = 1;
        fk_idzona_corto = 1;
        txtbucarCliente_zona.setText(null);
        txtbucarCliente_zona.setBackground(Color.white);
        jFPrecio_zona.setValue(0);
        txtbucarCliente_nombre.setText(null);
        txtbucarCliente_telefono.setText(null);
        txtbucarCliente_ruc.setText(cli_ruc_oca);
        txtbucarCliente_direccion.setText(null);
        txtbucarCliente_telefono.grabFocus();

    }

    void cargar_cliente_principal(cliente clie) {
        monto_delivery = clie.getC11deliveryDouble();
        zona_delivery = clie.getC10zona();
        txtbucarCliente_nombre.setText(clie.getC3nombre());
        txtbucarCliente_telefono.setText(clie.getC5telefono());
        txtbucarCliente_ruc.setText(clie.getC6ruc());
        txtbucarCliente_direccion.setText(clie.getC4direccion());
        if (clie.getC8tipo().equals("funcionario")) {
            esFuncionario = true;
        } else {
            esFuncionario = false;
        }
    }

    void seleccionar_cargar_cliente(int tipo) {
        if (tipo == 1) {
//            fk_idcliente_local = eveconn.getInt_Solo_seleccionar_JLista(connLocal, jList_cliente, "cliente", clie.getCliente_concat(), "idcliente");
        }
        if (tipo == 2) {
            fk_idcliente_local = (eveconn.getInt_ultimoID_mas_uno(connLocal, clie.getTabla(), clie.getIdtabla())) - 1;
        }
        if (tipo == 3) {
            fk_idcliente_local = evejt.getInt_select_id(tblbuscar_cliente);
        }
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
//        tipo_entrega = entrega_comer_aca;
        System.out.println("idclientelocal:" + fk_idcliente_local);
        lblidcliente.setText("id:" + fk_idcliente_local);
        cdao.cargar_cliente(connLocal, clie, fk_idcliente_local);
        cargar_cliente_principal(clie);
        cargar_directo_delivery();
    }

    void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            String tipo = evejt.getString_select(tblitem_producto, 1);
            if (tipo.equals("I") || tipo.equals("D") || tipo.equals("S")) {
                if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                    sumar_item_venta();
                    evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                }
            }
            if (tipo.equals("P") || tipo.equals("N")) {
                if (evejt.getBoolean_Eliminar_Fila_subfila(tblitem_producto, model_itemf)) {
                    sumar_item_venta();
                    evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                }
            }
        }
    }

    boolean validar_guardar_venta() {
        idventa_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ven.getTabla(), ven.getIdtabla()));
        if (evejt.getBoolean_validar_cant_cargado(tblitem_producto)) {
            return false;
        }
//        if (fk_idcliente_local == 1) {
////            txtbucarCliente_nombre.setText("OCACIONAL");
////            txtbucarCliente_telefono.setText("xxx");
////            txtbucarCliente_ruc.setText("xxx");
////            txtbucarCliente_direccion.setText("CDE");
//        }
        return true;
    }

    void reestableser_venta() {
        idventa_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ven.getTabla(), ven.getIdtabla()));
        txtidventa.setText(String.valueOf(idventa_ultimo));
        indice_venta = eveut.getString_crear_indice();
        color_boton_entrega(Color.white, Color.white, Color.white);
        txtbuscar_fecha.setText(evefec.getString_formato_fecha());
        jCestado_emitido.setSelected(true);
        jCestado_terminado.setSelected(false);
        jCestado_anulado.setSelected(true);
        jCfecha_todos.setSelected(false);
        jRpago_efectivo.setSelected(true);
        actualizar_venta(3);
        tipo_entrega = entrega_comer_aca;
        monto_venta = 0;
        monto_delivery = 0;
        txtcantidad_producto.setText("1");
//        lbltipocliente.setText("OCASIONAL");
        txtobservacion.setText("Ninguna");
        btnconfirmar_venta.setText("CONFIRMAR VENTA");
        nombre_mesa = "SIN-MESA";
        jFmonto_mesa.setValue(0);
        jFmonto_mesa.setVisible(false);
        btnconfirmar_venta.setBackground(Color.white);
        txtobservacion_ingre.setText(null);
        jFtotal_guarani.setValue(monto_venta);
        jFtotal_dolar.setValue(monto_venta);
        jFtotal_real.setValue(monto_venta);
        evejt.limpiar_tabla_datos(model_itemf);
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        isCargar_venta_mesa = false;
        habilitar_crear_cliente_corto = false;
        esBoton_comer_aca = false;
        esBoton_buscar_pedido = false;
        habilitar_cargado_zona = false;
        cargar_botones_mesa();
        limpiar_cliente();
    }

    String getString_crear_comanda() {
        String comanda = "";
        String salto = "\n";
        int cant_registro = 0;
        for (int fila = 0; fila < tblitem_producto.getRowCount(); fila++) {
            String tipo = (tblitem_producto.getModel().getValueAt(fila, 1).toString());
            if (tipo.equals("P")) {
                cant_registro++;
                String descripcion = (tblitem_producto.getModel().getValueAt(fila, 2).toString());
                String cantidad = (tblitem_producto.getModel().getValueAt(fila, 4).toString());
                comanda = comanda + "(" + cantidad + ")>" + eveut.getString_salto_de_linea(descripcion) + salto;
            }
            if (tipo.equals("I")) {
                cant_registro++;
                String descripcion = (tblitem_producto.getModel().getValueAt(fila, 2).toString());
                comanda = comanda + "->" + eveut.getString_salto_de_linea(descripcion) + salto;
            }
        }
        if (cant_registro == 0) {
            comanda = "nada";
        }
        return comanda;
    }

    void cargar_datos_venta(int fk_idcliente) {
        monto_delivery = evejt.getDouble_monto_validado(tblitem_producto, 5, 1, "D");
        boolean delivery = false;
        if (monto_delivery > 0) {
            delivery = true;
        }
        if (esFuncionario) {
            tipo_entrega = entrega_funcio;
        }
        if(jRpago_efectivo.isSelected()){
            forma_pago=pago_efectivo;
        }
        if(jRpago_tarjeta.isSelected()){
            forma_pago=pago_tarjeta;
        }
        ven.setC2fecha_inicio("now");//falta hora
        ven.setC3fecha_fin("now");
        ven.setC4tipo_entrega(tipo_entrega);
        ven.setC5estado(estado);
        ven.setC6monto_venta(monto_venta);
        ven.setC7monto_delivery(monto_delivery);
        ven.setC8delivery(delivery);
        ven.setC9observacion(txtobservacion.getText());
        ven.setC10comanda(getString_crear_comanda());
        ven.setC11equipo(evepc.getString_nombre_pc());
        ven.setC12fk_idcliente(fk_idcliente);
        ven.setC13fk_idusuario(usu.getGlobal_idusuario());
        ven.setC14fk_identregador(1);
        ven.setC15indice(indice_venta);
        ven.setC16nombre_mesa(nombre_mesa);
        ven.setC17forma_pago(forma_pago);
        ven.setIdventaGlobal(idventa_ultimo);
        ven.setMonto_ventaGlobal(monto_venta);
    }

    void cargar_datos_caja() {
        caja.setC2fecha_emision(evefec.getString_formato_fecha_hora());
        caja.setC3descripcion("(VENTA) " + nombre_mesa + " id:" + idventa_ultimo + " Cli:" + txtbucarCliente_nombre.getText());
        caja.setC4monto_venta(monto_venta);
        caja.setC5monto_delivery(monto_delivery);
        caja.setC6monto_gasto(0);
        caja.setC7monto_compra(0);
        caja.setC8monto_vale(0);
        caja.setC9id_origen(idventa_ultimo);
        caja.setC10tabla_origen("VENTA");
        caja.setC11fk_idusuario(usu.getGlobal_idusuario());
        caja.setC12indice(indice_venta);
        caja.setC13equipo1(evepc.getString_nombre_pc());
        caja.setC15monto_caja1(0);
        caja.setC16monto_cierre(0);
    }

    void cargar_datos_venta_por_mesa() {
        if (isCargar_venta_mesa) {
            if (esMesa_LIBRE) {
                insertar_item_venta_mesa();//ev venta
                insertar_item_venta_mesa_venta(true);//en venta
                modificar_estado_venta_mesa(true);//en venta
            } else {
                insertar_item_venta_mesa_venta(false);//en venta
                modificar_estado_venta_mesa(false);//en venta
                modificar_monto_item_venta_mesa();//en venta
            }
        }
    }

    void boton_guardar_venta() {
        if (validar_guardar_venta()) {
            habilitar_cargado_zona = false;
            guardar_cliente_corto();
            cargar_datos_venta(fk_idcliente_local);
            cargar_datos_caja();
            esMesa_LIBRE = verificar_venta_mesa_es_LIBRE(idventa_mesa_select);
            cargar_datos_venta_por_mesa();
            if (vBO.getBoolean_insertar_venta(connLocal, tblitem_producto, item, ven, caja, isCargar_venta_mesa, esMesa_LIBRE,
                    ivmesa, ivmv, vmesa, iteminsu, idventa_ultimo, idventa_mesa_select)) {
                if (jCvuelto.isSelected()) {
                    FrmVuelto vuel = new FrmVuelto(null, true);
                    vuel.setVisible(true);
                } else {
                    idventa_ult_print = idventa_ultimo;
                    imprimir_venta(idventa_ultimo);
                }
//                iviBO.insertar_itemven_insumo(iteminsu, idventa_ultimo);
                reestableser_venta();
                endao.actualizar_tabla_entregador_venta(connLocal, tblentregador);
                if (ven.isVenta_aux()) {
                    ven.setVenta_aux(false);
                    this.dispose();
                }
            }
        }
    }

    void imprimir_venta(int idventa) {
        posv.boton_imprimir_pos_VENTA(connLocal, idventa);
        posco.boton_imprimir_pos_COMANDA(connLocal, idventa);
    }

    void boton_imprimirPos_venta() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa = (evejt.getInt_select_id(tblventa));
            imprimir_venta(idventa);
        }
    }

    void boton_factura() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            fac.setFactura_cargada(true);
            int idventa = (evejt.getInt_select_id(tblventa));
            vdao.cargar_venta(ven, idventa);
            evetbl.abrir_TablaJinternal(new FrmFactura());
        }
    }

    void boton_copiar_venta() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE COPIAR  VENTA", "COPIAR", "COPIAR", "CANCELAR")) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
                limpiar_cliente();
                evejt.limpiar_tabla_datos(model_itemf);
                int idventa = (evejt.getInt_select_id(tblventa));
                copiar_itemventa(idventa);
                sumar_item_venta();
                vdao.cargar_venta(ven, idventa);
                tipo_entrega = ven.getC4tipo_entrega();
                System.out.println("IDCLIENTE copiar:" + ven.getC12fk_idcliente());
                cargar_cliente_copiado(ven.getC12fk_idcliente());
            }
        }
    }

    void boton_pasar_cocina() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE PASAR A COCINA", "PASAR A COCINA", "COCINA", "CANCELAR")) {
                ven.setC5estado("EMITIDO");
                String indice = evejt.getString_select(tblventa, 1);
                ven.setC15indice(indice);
                vBO.update_estado_pasar_cocina(connLocal, ven);
                actualizar_venta(3);
            }
        }
    }

    void boton_estado_venta_terminar() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            ven.setC5estado("TERMINADO");
            String indice = evejt.getString_select(tblventa, 1);
            ven.setC15indice(indice);
            vBO.update_estado_venta(connLocal, ven);
//            update_estado_venta_servidor();
            actualizar_venta(3);
        }
    }

    void SELECCIONAR_cambiar_entregador() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (!evejt.getBoolean_validar_select(tblentregador)) {
                int idventa = evejt.getInt_select_id(tblventa);
                vdao.cargar_venta(ven, idventa);
                if (ven.getC7monto_delivery() > 0) {
                    int fk_identregador = evejt.getInt_select_id(tblentregador);
                    ven.setC14fk_identregador(fk_identregador);
                    String indice = evejt.getString_select(tblventa, 1);
                    ven.setC15indice(indice);
                    vBO.update_cambio_entregador(connLocal, ven);
                    actualizar_venta(3);
                } else {
                    JOptionPane.showMessageDialog(null, "ESTA VENTA NO TIENE DELIVERY\n"
                            + "NO SE PUEDE ASIGNAR UN ENTREGADOR SIN DELIVERY", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    void actualizar_venta(int tipo) {
        String filtro = "";
        String campo_filtro = "";
        String filtro_estado = auxvent.filtro_estado(jCestado_emitido, jCestado_terminado, jCestado_anulado);
        if (jRcampo_comanda.isSelected()) {
            campo_filtro = "v.comanda";
        }
        if (jRcampo_cliente.isSelected()) {
            campo_filtro = "(c.idcliente||'-'||c.nombre) as cliente";
        }
        if (tipo == 1) {
            filtro = filtro_estado;
        }
        if (tipo == 2) {
            if (txtbuscar_idventa.getText().trim().length() > 0) {
                filtro = " and v.idventa=" + txtbuscar_idventa.getText() + " ";
            }
        }
        if (tipo == 3) {
            if (txtbuscar_fecha.getText().trim().length() > 0) {
                if (jCfecha_todos.isSelected()) {
                    filtro = filtro_estado;
                } else {
                    filtro = " and date(v.fecha_inicio)='" + txtbuscar_fecha.getText() + "' " + filtro_estado;
                }
            }
        }
        lblcantidad_filtro.setText("CANT FILTRO: ( " + tblventa.getRowCount() + " )");
        vdao.actualizar_tabla_venta(connLocal, tblventa, filtro, campo_filtro);
        coma.cargar_sql_comanda(connLocal);
    }

    void boton_estado_venta_anular() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR LA VENTA", "ANULAR", "ANULAR", "CANCELAR")) {
                String indice = evejt.getString_select(tblventa, 1);
                ven.setC5estado("ANULADO_temp");
                ven.setC15indice(indice);
                caja.setC12indice(indice);
                vBO.update_anular_venta(connLocal, ven, caja);
                actualizar_venta(3);
                actualizar_todos_venta_mesa();
                cargar_botones_mesa();
            }
        }
    }

    private void actualizar_todos_venta_mesa() {
        String titulo = "actualizar_todos_mesa";
        String sql = "select * from venta_mesa where estado='OCUPADO' order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (connLocal.getAutoCommit()) {
                connLocal.setAutoCommit(false);
            }
            while (rs.next()) {
                idventa_mesa_select = rs.getString("idventa_mesa");
                modificar_estado_venta_mesa(false);
                modificar_monto_item_venta_mesa();
                vmesa_dao.update_venta_mesaEstado(connLocal, vmesa);
                ivm_dao.update_item_venta_mesa_monto(connLocal, ivmesa);
            }
            connLocal.commit();
        } catch (Exception e) {
            evemen.mensaje_error(e, ven.toString(), titulo);
            try {
                connLocal.rollback();
            } catch (SQLException e1) {
                evemen.Imprimir_serial_sql_error(e1, vmesa.toString() + "\n" + ivmesa.toString(), titulo);
            }
        }
    }

    ///###################CLIENTE#####################
    private void abrir_formulario_cliente() {
        reestableser_cliente();
    }

    private boolean validar_guardar_cliente() {
        txtcli_fecha_inicio.setText(evefec.getString_formato_fecha());
        if (evejtf.getBoo_JTextField_vacio(txtcli_nombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_ruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_telefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_direccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_zona, "DEBE CARGAR UNA ZONA")) {
            return false;
        }
        if (txtcli_fecha_nacimiento.getText().trim().length() == 0) {
            txtcli_fecha_nacimiento.setText(evefec.getString_formato_fecha());
            clie.setC7fecha_cumple(evefec.getString_formato_fecha());
        }
        return true;
    }

    private boolean validar_guardar_cliente_corto() {
        if(fk_idzona_corto==1){
            zdao.cargar_zona_delivery(zona, fk_idzona_corto);
            txtbucarCliente_zona.setText(zona.getNombre());
            jFPrecio_zona.setValue(zona.getDelivery());
        }
        if (evejtf.getBoo_JTextField_vacio(txtbucarCliente_nombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (txtbucarCliente_telefono.getText().trim().length() == 0) {
            txtbucarCliente_telefono.setText(cli_tel_oca);
        }
        if (txtbucarCliente_ruc.getText().trim().length() == 0) {
            txtbucarCliente_ruc.setText(cli_ruc_oca);
        }
        if (txtbucarCliente_direccion.getText().trim().length() == 0) {
            txtbucarCliente_direccion.setText(cli_dir_oca);
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

//    void insertar_cliente_servidor() {
//    }
    private void boton_guardar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC2fecha_inicio("now");
            clie.setC3nombre(txtcli_nombre.getText());
            clie.setC4direccion(txtcli_direccion.getText());
            clie.setC5telefono(txtcli_telefono.getText());
            clie.setC6ruc(txtcli_ruc.getText());
            clie.setC7fecha_cumple(txtcli_fecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            cBO.insertar_cliente(connLocal, clie, tblpro_cliente, true);
            reestableser_cliente();
            seleccionar_cargar_cliente(2);

        }
    }

    private void boton_editar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC1idcliente(Integer.parseInt(txtcli_idcliente.getText()));
            clie.setC2fecha_inicio(txtcli_fecha_inicio.getText());
            clie.setC3nombre(txtcli_nombre.getText());
            clie.setC4direccion(txtcli_direccion.getText());
            clie.setC5telefono(txtcli_telefono.getText());
            clie.setC6ruc(txtcli_ruc.getText());
            clie.setC7fecha_cumple(txtcli_fecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            cBO.update_cliente(clie, tblpro_cliente);
        }
    }

    private void cargar_cliente_copiado(int idcliente) {
        cdao.cargar_cliente(connLocal, clie, idcliente);
        fk_idcliente_local = idcliente;
        lblidcliente.setText("id:" + idcliente);
        cargar_cliente_principal(clie);
    }

    private void seleccionar_tabla_cliente() {
        int id = evejt.getInt_select_id(tblpro_cliente);
        cdao.cargar_cliente(connLocal, clie, id);
        txtcli_idcliente.setText(String.valueOf(clie.getC1idcliente()));
        txtcli_fecha_inicio.setText(clie.getC2fecha_inicio());
        txtcli_nombre.setText(clie.getC3nombre());
        txtcli_direccion.setText(clie.getC4direccion());
        txtcli_telefono.setText(clie.getC5telefono());
        txtcli_ruc.setText(clie.getC6ruc());
        txtcli_fecha_nacimiento.setText(clie.getC7fecha_cumple());
        if (clie.getC8tipo().equals(tipo_cliente)) {
            jRtipo_cliente.setSelected(true);
        }
        if (clie.getC8tipo().equals("funcionario")) {
            jRtipo_funcionario.setSelected(true);
        }
        txtcli_zona.setText(clie.getC10zona());
        txtcli_delivery.setText(clie.getC11delivery());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser_cliente() {
        jLzona.setVisible(false);
        jRtipo_cliente.setSelected(true);
        txtcli_idcliente.setText(null);
        txtcli_nombre.setText(null);
        txtcli_fecha_inicio.setText(null);
        txtcli_direccion.setText(null);
        txtcli_telefono.setText(null);
        txtcli_ruc.setText(cli_ruc_oca);
        txtcli_fecha_nacimiento.setText(null);
        txtcli_zona.setText(null);
        txtcli_delivery.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtcli_nombre.grabFocus();
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
    }

    private void cargar_zona_cliente() {
        int idzona = eveconn.getInt_Solo_seleccionar_JLista(connLocal, jLzona, zona.getTabla(), zona.getZona_concat(), zona.getIdtabla());
        clie.setC9fk_idzona_delivery(idzona);
        zdao.cargar_zona_delivery(zona, idzona);
        txtcli_delivery.setText(String.valueOf(zona.getDelivery()));
        txtcli_zona.setText(zona.getNombre());
    }

    private void boton_nuevo_cliente() {
        reestableser_cliente();
    }

    private void limpiar_buscardor_cliente() {
        jCfuncionario.setSelected(false);
        txtbuscador_cliente_nombre.setText(null);
        txtbuscador_cliente_telefono.setText(null);
        txtbuscador_cliente_ruc.setText(null);
        txtbuscador_cliente_telefono.grabFocus();
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }

    private void boton_buscar_cliente_venta() {
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 3);
        limpiar_buscardor_cliente();
    }

    private void boton_seleccionar_venta_mesa() {
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 4);
    }

    private void boton_ir_venta() {
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
    }

    private void seleccionar_tabla_venta() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            item.setC7fk_idventa(evejt.getInt_select_id(tblventa));
            String estado = evejt.getString_select(tblventa, 6);
            if (estado.equals("EMITIDO")) {
                btnterminarcomanda.setEnabled(true);
                btncocina.setEnabled(false);
                btnanularventa.setEnabled(true);
            }
            if (estado.equals("TERMINADO")) {
                btnterminarcomanda.setEnabled(false);
                btncocina.setEnabled(true);
                btnanularventa.setEnabled(true);
            }
            if (estado.equals("ANULADO")) {
                btnterminarcomanda.setEnabled(false);
                btncocina.setEnabled(false);
                btnanularventa.setEnabled(false);
            }
            if (estado.equals("ANULADO_temp")) {
                btnterminarcomanda.setEnabled(false);
                btncocina.setEnabled(false);
                btnanularventa.setEnabled(false);
            }
            ivdao.tabla_item_cliente_filtro(connLocal, tblitem_venta_filtro, item);
        }
    }

    //######################## MESA ##########################
    void cargar_botones_mesa() {
        String titulo = "cargar_botones_mesa";
        jPboton_mesa.removeAll();
        botones_mesa.clear();
        indice_mesa = 0;
        String sql = "select idventa_mesa,nombre,estado,TRIM(to_char(monto,'999G999G999')) as monto "
                + "from venta_mesa "
                + "where habilitar=true order by 1 asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String estado = rs.getString("estado");
                String idmesa = rs.getString("idventa_mesa");
                String monto = rs.getString("monto");
                String textboton = "<html><h1>" + nombre + "</h1><br>" + estado + "<br>" + monto + "Gs</html>";
                boolean libre = false;
                if (estado.equals("LIBRE")) {
                    libre = true;
                }
                boton_seleccionar_mesa(textboton, idmesa, libre);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void boton_seleccionar_mesa(String textboton, String idmesa, boolean libre) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setName(idmesa);
        if (libre) {
            boton.setBackground(new java.awt.Color(102, 255, 102));
        } else {
            boton.setBackground(new java.awt.Color(255, 102, 51));
        }
        jPboton_mesa.add(boton);
        botones_mesa.add(boton);
        indice_mesa++;
        boton.addActionListener(new ActionListener() {
            private String monto_mesa;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < indice_mesa; p++) {
                    botones_mesa.get(p).setForeground(Color.BLACK);
                }
                ((JButton) e.getSource()).setForeground(Color.RED);
                idventa_mesa_select = ((JButton) e.getSource()).getName();
                System.out.println("idventa_mesa_select:" + idventa_mesa_select);
                btnconfirmar_venta.setText("AGREGAR(MESA-" + idventa_mesa_select + ")");
                btnconfirmar_venta.setBackground(Color.orange);
                txtbucarCliente_nombre.setText("(MESA-" + idventa_mesa_select + ")");
                nombre_mesa = "(MESA-" + idventa_mesa_select + ")";
                txtnumero_mesa.setText(nombre_mesa);
                String vistaprevia = posmesa.getString_venta_mesa(connLocal, "0", idventa_mesa_select, 2);
                int monto = vmDAO.getInt_sumar_venta_mesa(connLocal, idventa_mesa_select);
                jFmonto_mesa.setValue(monto);
                jFmonto_mesa.setVisible(true);
                txtaVistaPrevia.setText(vistaprevia);
                isCargar_venta_mesa = true;
                hab_boton_cerrar = true;
            }
        });
        jPboton_mesa.updateUI();
    }

    void modificar_estado_venta_mesa(boolean inicio) {//en venta
        double monto = 0;
        if (inicio) {
            monto = monto_venta;
        } else {
            monto = vmDAO.getInt_sumar_venta_mesa(connLocal, idventa_mesa_select);
        }
        vmesa.setC1idventa_mesa(Integer.parseInt(idventa_mesa_select));
        vmesa.setC3estado("OCUPADO");
        vmesa.setC4monto(monto);
    }

    void modificar_monto_item_venta_mesa() {//en venta
        double monto = 0;
        monto = vmDAO.getInt_sumar_venta_mesa(connLocal, idventa_mesa_select);
        int fk_iditem_venta_mesa = vmDAO.cargar_idventa_mesa(connLocal, idventa_mesa_select);
        ivmesa.setC1iditem_venta_mesa(fk_iditem_venta_mesa);
        ivmesa.setC5totalmonto(monto);
    }

    void modificar_item_venta_mesa_CERRAR() {
        int fk_iditem_venta_mesa = vmDAO.cargar_idventa_mesa(connLocal, idventa_mesa_select);
        ivmesa.setC1iditem_venta_mesa(fk_iditem_venta_mesa);
        ivmesa.setC4estado("CERRADO");
        ivmBO.update_item_venta_mesa_CERRAR(ivmesa);
    }

    void insertar_item_venta_mesa_venta(boolean inicio) {//en venta
        int fk_iditem_venta_mesa = 0;
        if (inicio) {
            fk_iditem_venta_mesa = eveconn.getInt_ultimoID_mas_uno(connLocal, "item_venta_mesa", "iditem_venta_mesa");
        } else {
            fk_iditem_venta_mesa = vmDAO.cargar_idventa_mesa(connLocal, idventa_mesa_select);
        }
        ivmv.setC2fk_idventa(idventa_ultimo);
        ivmv.setC3fk_iditem_venta_mesa(fk_iditem_venta_mesa);
    }

    boolean verificar_venta_mesa_es_LIBRE(String idmesa) {
        String titulo = "verificar_venta_mesa";
        boolean libre = false;
        String sql = "select * from venta_mesa where idventa_mesa=" + idmesa;
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (rs.next()) {
                String estado = rs.getString("estado");
                if (estado.equals("LIBRE")) {
                    libre = true;
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return libre;
    }

    void insertar_item_venta_mesa() {//en venta
        ivmesa.setC4estado("ABIERTO");
        ivmesa.setC5totalmonto(monto_venta);
        ivmesa.setC6fk_idventa_mesa(Integer.parseInt(idventa_mesa_select));
    }

    void boton_cerrar_mesa() {
        if (hab_boton_cerrar) {
            int monto = vmDAO.getInt_sumar_venta_mesa(connLocal, idventa_mesa_select);
            if (evemen.MensajeGeneral_question("ESTAS SEGURO DE CERRAR ESTA MESA " + idventa_mesa_select
                    + "\nMONTO TOTAL:" + monto, "CERRAR MESA", "ACEPTAR", "CANCELAR")) {
                posmesa.boton_imprimir_pos_venta_mesa(connLocal, "0", idventa_mesa_select, 2);
                modificar_estado_mesa();
                modificar_item_venta_mesa_CERRAR();
                reestableser_venta();
            }
        } else {
            JOptionPane.showMessageDialog(this, "SELECCIONAR UNA MESA PARA CERRAR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    void modificar_estado_mesa() {
        vmesa.setC1idventa_mesa(Integer.parseInt(idventa_mesa_select));
        vmesa.setC3estado("LIBRE");
        vmesa.setC4monto(0);
        vmBO.update_venta_mesa_Estado(vmesa);
    }

    public FrmVenta() {
        initComponents();
        abrir_formulario();
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

        gru_campo = new javax.swing.ButtonGroup();
        gru_tipocli = new javax.swing.ButtonGroup();
        gru_pago = new javax.swing.ButtonGroup();
        jTabbedPane_VENTA = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        panel_referencia_categoria = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        txtcantidad_producto = new javax.swing.JTextField();
        btncantidad_1 = new javax.swing.JButton();
        btncantidad_2 = new javax.swing.JButton();
        btncantidad_3 = new javax.swing.JButton();
        btncantidad_4 = new javax.swing.JButton();
        btncantidad_5 = new javax.swing.JButton();
        btnagregar_cantidad = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtidventa = new javax.swing.JTextField();
        panel_insertar_sec_ingrediente = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblingrediente = new javax.swing.JTable();
        lblnombre_producto_ingre = new javax.swing.JLabel();
        btningrediente_quitar = new javax.swing.JButton();
        btningrediente_agregar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtobservacion_ingre = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        btnobservacion_ingre = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        panel_insertar_pri_item = new javax.swing.JPanel();
        btnagregar_delivery = new javax.swing.JButton();
        btneliminar_item = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jFtotal_guarani = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFtotal_real = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFtotal_dolar = new javax.swing.JFormattedTextField();
        btnconfirmar_venta = new javax.swing.JButton();
        btnagregar_paquete = new javax.swing.JButton();
        btnagregar_local = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtbucarCliente_ruc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtbucarCliente_nombre = new javax.swing.JTextField();
        txtbucarCliente_direccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucarCliente_telefono = new javax.swing.JTextField();
        btnlimpiar_cliente = new javax.swing.JButton();
        btnnuevo_cliente = new javax.swing.JButton();
        btnbuscar_cliente = new javax.swing.JButton();
        lblidcliente = new javax.swing.JLabel();
        btnmesas = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        txtbucarCliente_zona = new javax.swing.JTextField();
        jFPrecio_zona = new javax.swing.JFormattedTextField();
        btnultima_impresion = new javax.swing.JButton();
        jRpago_efectivo = new javax.swing.JRadioButton();
        jLabel32 = new javax.swing.JLabel();
        jRpago_tarjeta = new javax.swing.JRadioButton();
        jCvuelto = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        txtdescontar = new javax.swing.JTextField();
        btndescontar = new javax.swing.JButton();
        panel_referencia_venta = new javax.swing.JPanel();
        panel_tabla_venta = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnanularventa = new javax.swing.JButton();
        btnfacturar = new javax.swing.JButton();
        btnterminarcomanda = new javax.swing.JButton();
        btnimprimirNota = new javax.swing.JButton();
        panel_tabla_item = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_venta_filtro = new javax.swing.JTable();
        btnactualizar_tabla = new javax.swing.JButton();
        btnduplicar = new javax.swing.JButton();
        panel_tabla_entregador = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblentregador = new javax.swing.JTable();
        btncocina = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_idventa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_fecha = new javax.swing.JTextField();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_terminado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        lblcantidad_filtro = new javax.swing.JLabel();
        jCfecha_todos = new javax.swing.JCheckBox();
        jRcampo_cliente = new javax.swing.JRadioButton();
        jRcampo_comanda = new javax.swing.JRadioButton();
        panel_base_2 = new javax.swing.JPanel();
        panel_insertar_sec_cliente = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jRtipo_cliente = new javax.swing.JRadioButton();
        jRtipo_funcionario = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtcli_ruc = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtcli_telefono = new javax.swing.JTextField();
        txtcli_nombre = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtcli_idcliente = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtcli_fecha_inicio = new javax.swing.JTextField();
        lblfecnac = new javax.swing.JLabel();
        txtcli_fecha_nacimiento = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLzona = new javax.swing.JList<>();
        jLabel21 = new javax.swing.JLabel();
        txtcli_zona = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtcli_delivery = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        btnnuevazona = new javax.swing.JButton();
        btnlimpiarzona = new javax.swing.JButton();
        txtcli_direccion = new javax.swing.JTextField();
        panel_tabla_cliente = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblpro_cliente = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_telefono = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtbuscar_ruc = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        panel_tabla_busca_cli = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblbuscar_cliente = new javax.swing.JTable();
        txtbuscador_cliente_nombre = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtbuscador_cliente_telefono = new javax.swing.JTextField();
        txtbuscador_cliente_ruc = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jCfuncionario = new javax.swing.JCheckBox();
        btnnuevoCliente = new javax.swing.JButton();
        btnseleccionarCerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPboton_mesa = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtaVistaPrevia = new javax.swing.JTextArea();
        btnpreimpreso = new javax.swing.JButton();
        btncerrar_mesa = new javax.swing.JButton();
        btnseleccionar_cerrar = new javax.swing.JButton();
        txtnumero_mesa = new javax.swing.JTextField();
        jFmonto_mesa = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));

        panel_referencia_unidad.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_unidad.setLayout(new java.awt.GridLayout(1, 0));

        panel_insertar_pri_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTO:");

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        tblproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
        tblproducto.setRowHeight(30);
        jScrollPane1.setViewportView(tblproducto);

        txtcantidad_producto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_producto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btncantidad_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_1.png"))); // NOI18N
        btncantidad_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_1ActionPerformed(evt);
            }
        });

        btncantidad_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_2.png"))); // NOI18N
        btncantidad_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_2ActionPerformed(evt);
            }
        });

        btncantidad_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_3.png"))); // NOI18N
        btncantidad_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_3ActionPerformed(evt);
            }
        });

        btncantidad_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_4.png"))); // NOI18N
        btncantidad_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_4ActionPerformed(evt);
            }
        });

        btncantidad_5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_5.png"))); // NOI18N
        btncantidad_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_5ActionPerformed(evt);
            }
        });

        btnagregar_cantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/flecha_derecha.png"))); // NOI18N
        btnagregar_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_cantidadActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("IDVENTA:");

        txtidventa.setBackground(new java.awt.Color(0, 0, 255));
        txtidventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidventa.setForeground(new java.awt.Color(255, 255, 0));
        txtidventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btncantidad_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btncantidad_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btncantidad_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btncantidad_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btncantidad_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        panel_insertar_sec_ingrediente.setBackground(new java.awt.Color(51, 51, 255));
        panel_insertar_sec_ingrediente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblingrediente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblingrediente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblingrediente.setRowHeight(30);
        jScrollPane3.setViewportView(tblingrediente);

        lblnombre_producto_ingre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblnombre_producto_ingre.setForeground(new java.awt.Color(255, 255, 255));
        lblnombre_producto_ingre.setText("jLabel10");

        btningrediente_quitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/fle_isq_rojo.png"))); // NOI18N
        btningrediente_quitar.setText("QUITAR");
        btningrediente_quitar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btningrediente_quitar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btningrediente_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btningrediente_quitarActionPerformed(evt);
            }
        });

        btningrediente_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/fle_dere_verde.png"))); // NOI18N
        btningrediente_agregar.setText("AGREGAR");
        btningrediente_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btningrediente_agregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btningrediente_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btningrediente_agregarActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/lupa.png"))); // NOI18N
        jButton3.setText("PROD.");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtobservacion_ingre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("OBSERVACION:");

        btnobservacion_ingre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnobservacion_ingre.setText("OBSERVACION");
        btnobservacion_ingre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnobservacion_ingreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_sec_ingredienteLayout = new javax.swing.GroupLayout(panel_insertar_sec_ingrediente);
        panel_insertar_sec_ingrediente.setLayout(panel_insertar_sec_ingredienteLayout);
        panel_insertar_sec_ingredienteLayout.setHorizontalGroup(
            panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_sec_ingredienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnombre_producto_ingre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_sec_ingredienteLayout.createSequentialGroup()
                        .addGroup(panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_sec_ingredienteLayout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtobservacion_ingre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnobservacion_ingre))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btningrediente_agregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btningrediente_quitar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panel_insertar_sec_ingredienteLayout.setVerticalGroup(
            panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_sec_ingredienteLayout.createSequentialGroup()
                .addComponent(lblnombre_producto_ingre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_sec_ingredienteLayout.createSequentialGroup()
                        .addComponent(btningrediente_quitar)
                        .addGap(2, 2, 2)
                        .addComponent(btningrediente_agregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnobservacion_ingre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertar_sec_ingredienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtobservacion_ingre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTab_producto_ingrediente.addTab("INGREDIENTES", panel_insertar_sec_ingrediente);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("OBS:");

        txtobservacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtobservacion.setText("ninguna");

        panel_insertar_pri_item.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_item.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnagregar_delivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_deliveryActionPerformed(evt);
            }
        });

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("GUARANI:");

        jFtotal_guarani.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_guarani.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_guarani.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("REAL:");

        jFtotal_real.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00 R"))));
        jFtotal_real.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_real.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("DOLAR:");

        jFtotal_dolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00 $"))));
        jFtotal_dolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_dolar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btnconfirmar_venta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnconfirmar_venta.setText("CONFIRMAR");
        btnconfirmar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_ventaActionPerformed(evt);
            }
        });

        btnagregar_paquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_paqueteActionPerformed(evt);
            }
        });

        btnagregar_local.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_localActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtbucarCliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("RUC:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("DIREC:");

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

        txtbucarCliente_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_direccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_direccionKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("TEL.:");

        txtbucarCliente_telefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbucarCliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyReleased(evt);
            }
        });

        btnlimpiar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/escoba.png"))); // NOI18N
        btnlimpiar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiar_clienteActionPerformed(evt);
            }
        });

        btnnuevo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_clienteActionPerformed(evt);
            }
        });

        btnbuscar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_clienteActionPerformed(evt);
            }
        });

        lblidcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblidcliente.setText("id");

        btnmesas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnmesas.setText("MESAS");
        btnmesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmesasActionPerformed(evt);
            }
        });

        tblitem_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        jLabel31.setText("ZONA:");

        txtbucarCliente_zona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_zonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_zonaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbucarCliente_zonaKeyTyped(evt);
            }
        });

        jFPrecio_zona.setEditable(false);
        jFPrecio_zona.setBackground(new java.awt.Color(204, 204, 204));
        jFPrecio_zona.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFPrecio_zona.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFPrecio_zona.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        btnultima_impresion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnultima_impresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnultima_impresionActionPerformed(evt);
            }
        });

        gru_pago.add(jRpago_efectivo);
        jRpago_efectivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRpago_efectivo.setForeground(new java.awt.Color(51, 51, 51));
        jRpago_efectivo.setSelected(true);
        jRpago_efectivo.setText("EFECTIVO");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("PAGO:");

        gru_pago.add(jRpago_tarjeta);
        jRpago_tarjeta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRpago_tarjeta.setForeground(new java.awt.Color(51, 51, 51));
        jRpago_tarjeta.setText("TARJETA");

        javax.swing.GroupLayout panel_insertar_pri_itemLayout = new javax.swing.GroupLayout(panel_insertar_pri_item);
        panel_insertar_pri_item.setLayout(panel_insertar_pri_itemLayout);
        panel_insertar_pri_itemLayout.setHorizontalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_itemLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18))
                        .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(26, 26, 26)))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbucarCliente_direccion)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(txtbucarCliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucarCliente_ruc)))
                .addContainerGap())
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2)
                .addGap(10, 10, 10))
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnlimpiar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbucarCliente_zona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFPrecio_zona, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRpago_efectivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRpago_tarjeta)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                                .addComponent(jFtotal_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jFtotal_real, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jFtotal_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnconfirmar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnmesas)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnultima_impresion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 10, Short.MAX_VALUE))
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(btnagregar_local, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnagregar_paquete, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnagregar_delivery, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))))
        );
        panel_insertar_pri_itemLayout.setVerticalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtbucarCliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblidcliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtbucarCliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtbucarCliente_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnlimpiar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel31)
                    .addComponent(txtbucarCliente_zona)
                    .addComponent(jFPrecio_zona, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnagregar_delivery, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btneliminar_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnagregar_paquete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnagregar_local, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_guarani)
                    .addComponent(jFtotal_dolar)
                    .addComponent(jFtotal_real, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel32)
                    .addComponent(jRpago_efectivo)
                    .addComponent(jRpago_tarjeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnultima_impresion, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(btnmesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnconfirmar_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        jCvuelto.setForeground(new java.awt.Color(255, 255, 255));
        jCvuelto.setText("VUELTO");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("DESCONTAR:");

        txtdescontar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtdescontar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdescontarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdescontarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescontarKeyTyped(evt);
            }
        });

        btndescontar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/descuento.png"))); // NOI18N
        btndescontar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndescontarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdescontar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndescontar))
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCvuelto)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel30)
                            .addComponent(txtdescontar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndescontar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jCvuelto))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("CREAR VENTA", panel_base_1);

        panel_tabla_venta.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla_venta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        tblventa.setRowHeight(30);
        tblventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblventaMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblventa);

        javax.swing.GroupLayout panel_tabla_ventaLayout = new javax.swing.GroupLayout(panel_tabla_venta);
        panel_tabla_venta.setLayout(panel_tabla_ventaLayout);
        panel_tabla_ventaLayout.setHorizontalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panel_tabla_ventaLayout.setVerticalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnanularventa.setBackground(new java.awt.Color(255, 51, 51));
        btnanularventa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnanularventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btnanularventa.setText("ANULAR");
        btnanularventa.setToolTipText("");
        btnanularventa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanularventa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanularventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularventaActionPerformed(evt);
            }
        });

        btnfacturar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnfacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_factura.png"))); // NOI18N
        btnfacturar.setText("FACTURAR ");
        btnfacturar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnfacturar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnfacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfacturarActionPerformed(evt);
            }
        });

        btnterminarcomanda.setBackground(new java.awt.Color(102, 102, 255));
        btnterminarcomanda.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnterminarcomanda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_terminar.png"))); // NOI18N
        btnterminarcomanda.setText("TERMINAR ");
        btnterminarcomanda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnterminarcomanda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnterminarcomanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnterminarcomandaActionPerformed(evt);
            }
        });

        btnimprimirNota.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnimprimirNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimirNota.setText("IMPRIMIR");
        btnimprimirNota.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnimprimirNota.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnimprimirNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirNotaActionPerformed(evt);
            }
        });

        panel_tabla_item.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblitem_venta_filtro.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_venta_filtro);

        javax.swing.GroupLayout panel_tabla_itemLayout = new javax.swing.GroupLayout(panel_tabla_item);
        panel_tabla_item.setLayout(panel_tabla_itemLayout);
        panel_tabla_itemLayout.setHorizontalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        panel_tabla_itemLayout.setVerticalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );

        btnactualizar_tabla.setText("ACTUAL");
        btnactualizar_tabla.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnactualizar_tabla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnactualizar_tabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_tablaActionPerformed(evt);
            }
        });

        btnduplicar.setText("COPIAR");
        btnduplicar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnduplicar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnduplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnduplicarActionPerformed(evt);
            }
        });

        panel_tabla_entregador.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblentregador.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
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
        tblentregador.setRowHeight(25);
        tblentregador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentregadorMouseReleased(evt);
            }
        });
        jScrollPane10.setViewportView(tblentregador);

        javax.swing.GroupLayout panel_tabla_entregadorLayout = new javax.swing.GroupLayout(panel_tabla_entregador);
        panel_tabla_entregador.setLayout(panel_tabla_entregadorLayout);
        panel_tabla_entregadorLayout.setHorizontalGroup(
            panel_tabla_entregadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );
        panel_tabla_entregadorLayout.setVerticalGroup(
            panel_tabla_entregadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btncocina.setText("P/ a COCINA");
        btncocina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncocinaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("IDVENTA:");

        txtbuscar_idventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_idventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_idventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscar_idventaKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("FECHA:");

        txtbuscar_fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_fechaKeyPressed(evt);
            }
        });

        jCestado_emitido.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_terminado.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCestado_terminado.setText("TERMINADO");
        jCestado_terminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_terminadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        lblcantidad_filtro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblcantidad_filtro.setForeground(new java.awt.Color(0, 0, 204));
        lblcantidad_filtro.setText("jLabel12");

        jCfecha_todos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCfecha_todos.setText("FACHA TODOS");
        jCfecha_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfecha_todosActionPerformed(evt);
            }
        });

        gru_campo.add(jRcampo_cliente);
        jRcampo_cliente.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRcampo_cliente.setSelected(true);
        jRcampo_cliente.setText("VER CLIENTE");
        jRcampo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcampo_clienteActionPerformed(evt);
            }
        });

        gru_campo.add(jRcampo_comanda);
        jRcampo_comanda.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRcampo_comanda.setText("VER COMANDA");
        jRcampo_comanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcampo_comandaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_ventaLayout = new javax.swing.GroupLayout(panel_referencia_venta);
        panel_referencia_venta.setLayout(panel_referencia_ventaLayout);
        panel_referencia_ventaLayout.setHorizontalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_tabla_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnduplicar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncocina, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_tabla_entregador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(btnterminarcomanda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnanularventa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnfacturar)
                                .addGap(18, 18, 18)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblcantidad_filtro))
                                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                        .addComponent(jCestado_emitido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCestado_terminado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCestado_anulado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCfecha_todos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRcampo_cliente)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRcampo_comanda)))))
                        .addContainerGap(329, Short.MAX_VALUE))))
        );
        panel_referencia_ventaLayout.setVerticalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addComponent(panel_tabla_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnterminarcomanda, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(btnanularventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimirNota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(btnfacturar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblcantidad_filtro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCestado_emitido)
                            .addComponent(jCestado_terminado)
                            .addComponent(jCestado_anulado)
                            .addComponent(jCfecha_todos)
                            .addComponent(jRcampo_cliente)
                            .addComponent(jRcampo_comanda))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addComponent(btnactualizar_tabla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnduplicar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncocina))
                    .addComponent(panel_tabla_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_tabla_entregador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("FILTRO VENTA", panel_referencia_venta);

        panel_insertar_sec_cliente.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_sec_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Tipo:");

        gru_tipocli.add(jRtipo_cliente);
        jRtipo_cliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_cliente.setSelected(true);
        jRtipo_cliente.setText("CLIENTE");
        jRtipo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_clienteActionPerformed(evt);
            }
        });

        gru_tipocli.add(jRtipo_funcionario);
        jRtipo_funcionario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_funcionario.setText("FUNCIONARIO");
        jRtipo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_funcionarioActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Direccion:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Ruc:");

        txtcli_ruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_rucKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Telefono:");

        txtcli_telefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_telefonoKeyPressed(evt);
            }
        });

        txtcli_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_nombreKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Nombre:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("ID:");

        txtcli_idcliente.setEditable(false);
        txtcli_idcliente.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_idcliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Fecha Inicio:");

        txtcli_fecha_inicio.setEditable(false);
        txtcli_fecha_inicio.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_fecha_inicio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblfecnac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfecnac.setText("Fec. Nac.:");

        txtcli_fecha_nacimiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_fecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_fecha_nacimientoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcli_fecha_nacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcli_fecha_nacimientoKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("año-mes-dia");

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLzona.setBackground(new java.awt.Color(204, 204, 255));
        jLzona.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLzona.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        jLayeredPane2.add(jLzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 70, 330, 90));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("ZONA:");
        jLayeredPane2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, -1));

        txtcli_zona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtcli_zona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_zonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcli_zonaKeyReleased(evt);
            }
        });
        jLayeredPane2.add(txtcli_zona, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 327, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("DELIVERY:");
        jLayeredPane2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        txtcli_delivery.setEditable(false);
        txtcli_delivery.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_delivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLayeredPane2.add(txtcli_delivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 147, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, -1, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane2.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, -1));

        btnnuevazona.setText("NUEVA ZONA");
        btnnuevazona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevazonaActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnnuevazona, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btnlimpiarzona.setText("LIMPIAR ZONA");
        btnlimpiarzona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarzonaActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnlimpiarzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        txtcli_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_direccionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_sec_clienteLayout = new javax.swing.GroupLayout(panel_insertar_sec_cliente);
        panel_insertar_sec_cliente.setLayout(panel_insertar_sec_clienteLayout);
        panel_insertar_sec_clienteLayout.setHorizontalGroup(
            panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                        .addComponent(txtcli_idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcli_fecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                        .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcli_telefono, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                    .addComponent(txtcli_nombre)
                    .addComponent(txtcli_direccion)))
            .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_sec_clienteLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane2))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_sec_clienteLayout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addComponent(jLabel16)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jRtipo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)
                    .addComponent(jRtipo_funcionario)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblfecnac)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtcli_fecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel20)))
        );
        panel_insertar_sec_clienteLayout.setVerticalGroup(
            panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(txtcli_idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtcli_fecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRtipo_cliente)
                    .addComponent(jRtipo_funcionario)
                    .addComponent(lblfecnac)
                    .addComponent(txtcli_fecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_tabla_cliente.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

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
        jScrollPane6.setViewportView(tblpro_cliente);

        jLabel23.setText("NOMBRE:");

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

        jLabel24.setText("TELEFONO:");

        txtbuscar_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyReleased(evt);
            }
        });

        jLabel25.setText("RUC:");

        javax.swing.GroupLayout panel_tabla_clienteLayout = new javax.swing.GroupLayout(panel_tabla_cliente);
        panel_tabla_cliente.setLayout(panel_tabla_clienteLayout);
        panel_tabla_clienteLayout.setHorizontalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtbuscar_nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(1, 1, 1))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
        );
        panel_tabla_clienteLayout.setVerticalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_base_2Layout = new javax.swing.GroupLayout(panel_base_2);
        panel_base_2.setLayout(panel_base_2Layout);
        panel_base_2Layout.setHorizontalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addComponent(panel_insertar_sec_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_2Layout.setVerticalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addGroup(panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_insertar_sec_cliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("CREAR CLIENTE", panel_base_2);

        panel_tabla_busca_cli.setBackground(new java.awt.Color(51, 102, 255));
        panel_tabla_busca_cli.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA BUSCAR CLIENTE"));

        tblbuscar_cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblbuscar_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbuscar_cliente.setRowHeight(30);
        tblbuscar_cliente.setRowMargin(5);
        tblbuscar_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblbuscar_clienteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbuscar_clienteMouseReleased(evt);
            }
        });
        tblbuscar_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblbuscar_clienteKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tblbuscar_cliente);

        txtbuscador_cliente_nombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_nombreKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 204));
        jLabel26.setText("Buscar Nombre:");

        jLabel27.setBackground(new java.awt.Color(255, 255, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 204));
        jLabel27.setText("Buscar Telefono:");

        txtbuscador_cliente_telefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_telefonoKeyReleased(evt);
            }
        });

        txtbuscador_cliente_ruc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_rucKeyReleased(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 204));
        jLabel28.setText("Buscar Ruc:");

        jCfuncionario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCfuncionario.setForeground(new java.awt.Color(255, 255, 255));
        jCfuncionario.setText("FUNCIONARIO");
        jCfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfuncionarioActionPerformed(evt);
            }
        });

        btnnuevoCliente.setText("NUEVO CLIENTE");
        btnnuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoClienteActionPerformed(evt);
            }
        });

        btnseleccionarCerrar.setBackground(new java.awt.Color(255, 255, 153));
        btnseleccionarCerrar.setText("SELECCIONAR IR VENTA");
        btnseleccionarCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionarCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_busca_cliLayout = new javax.swing.GroupLayout(panel_tabla_busca_cli);
        panel_tabla_busca_cli.setLayout(panel_tabla_busca_cliLayout);
        panel_tabla_busca_cliLayout.setHorizontalGroup(
            panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txtbuscador_cliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(txtbuscador_cliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                                .addComponent(txtbuscador_cliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevoCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnseleccionarCerrar)
                            .addComponent(jCfuncionario))
                        .addContainerGap(348, Short.MAX_VALUE))))
        );
        panel_tabla_busca_cliLayout.setVerticalGroup(
            panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbuscador_cliente_nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbuscador_cliente_telefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbuscador_cliente_ruc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7))
                    .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jCfuncionario)
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, panel_tabla_busca_cliLayout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnseleccionarCerrar, javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(btnnuevoCliente, javax.swing.GroupLayout.Alignment.CENTER)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_busca_cli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_busca_cli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane_VENTA.addTab("BUSCAR CLIENTE", jPanel11);

        jPanel2.setBackground(new java.awt.Color(255, 255, 51));

        jPboton_mesa.setLayout(new java.awt.GridLayout(0, 5));
        jScrollPane9.setViewportView(jPboton_mesa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtaVistaPrevia.setColumns(20);
        txtaVistaPrevia.setRows(5);
        jScrollPane11.setViewportView(txtaVistaPrevia);

        btnpreimpreso.setBackground(new java.awt.Color(153, 153, 255));
        btnpreimpreso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpreimpreso.setText("PRE-IMPRESO");
        btnpreimpreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpreimpresoActionPerformed(evt);
            }
        });

        btncerrar_mesa.setBackground(new java.awt.Color(255, 204, 0));
        btncerrar_mesa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btncerrar_mesa.setText("CERRAR MESA");
        btncerrar_mesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrar_mesaActionPerformed(evt);
            }
        });

        btnseleccionar_cerrar.setBackground(new java.awt.Color(51, 255, 51));
        btnseleccionar_cerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnseleccionar_cerrar.setText("SELECCIONAR Y CERRAR");
        btnseleccionar_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionar_cerrarActionPerformed(evt);
            }
        });

        txtnumero_mesa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtnumero_mesa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jFmonto_mesa.setBackground(new java.awt.Color(0, 0, 255));
        jFmonto_mesa.setForeground(new java.awt.Color(255, 255, 0));
        jFmonto_mesa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_mesa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFmonto_mesa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnpreimpreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btncerrar_mesa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnseleccionar_cerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                .addComponent(txtnumero_mesa, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jFmonto_mesa, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtnumero_mesa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnseleccionar_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncerrar_mesa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpreimpreso, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_mesa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_VENTA.addTab("VENTA POR MESA", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane_VENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 632, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        ancho_tabla_producto();
        vdao.ancho_tabla_venta(tblventa);
        cdao.ancho_tabla_cliente(tblpro_cliente);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btncantidad_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_1ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(1);
    }//GEN-LAST:event_btncantidad_1ActionPerformed

    private void btncantidad_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_2ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(2);
    }//GEN-LAST:event_btncantidad_2ActionPerformed

    private void btncantidad_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_3ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(3);
    }//GEN-LAST:event_btncantidad_3ActionPerformed

    private void btncantidad_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_4ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(4);
    }//GEN-LAST:event_btncantidad_4ActionPerformed

    private void btncantidad_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_5ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(5);
    }//GEN-LAST:event_btncantidad_5ActionPerformed

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        actualizar_tabla_producto(3);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void txtbucarCliente_direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_direccionKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtbucarCliente_direccionKeyReleased

    private void txtbucarCliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyReleased
        // TODO add your handling code here:
        if (txtbucarCliente_nombre.getText().trim().length() >= 3) {
            habilitar_crear_cliente_corto = true;
        }
//        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_nombre, jList_cliente, "cliente", "nombre", clie.getCliente_mostrar(), 4);
//        tblitem_producto.setVisible(false);
    }//GEN-LAST:event_txtbucarCliente_nombreKeyReleased

    private void txtbucarCliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyReleased
        // TODO add your handling code here:
        cargar_cliente_desde_telefono();
//        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_telefono, jList_cliente, "cliente", "telefono", clie.getCliente_mostrar(), 4);
//        tblitem_producto.setVisible(false);
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyReleased

    private void txtbucarCliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyReleased
        // TODO add your handling code here:
//        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_ruc, jList_cliente, "cliente", "ruc", clie.getCliente_mostrar(), 4);
//        tblitem_producto.setVisible(false);
    }//GEN-LAST:event_txtbucarCliente_rucKeyReleased

    private void txtbucarCliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyPressed
        // TODO add your handling code here:
//        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_direccion.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_nombreKeyPressed

    private void txtbucarCliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyPressed
        // TODO add your handling code here:
//        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_nombre.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyPressed

    private void txtbucarCliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyPressed
        // TODO add your handling code here:
//        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_nombre.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_rucKeyPressed

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:

        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void tblitem_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_productoMouseReleased
        // TODO add your handling code here:

        actualizar_tabla_ingrediente();
    }//GEN-LAST:event_tblitem_productoMouseReleased

    private void btningrediente_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btningrediente_quitarActionPerformed
        // TODO add your handling code here:
        cargar_ingrediente(1);
    }//GEN-LAST:event_btningrediente_quitarActionPerformed

    private void btningrediente_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btningrediente_agregarActionPerformed
        // TODO add your handling code here:
        cargar_ingrediente(2);
    }//GEN-LAST:event_btningrediente_agregarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnlimpiar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiar_clienteActionPerformed
        // TODO add your handling code here:
        limpiar_cliente();
        sumar_item_venta();
    }//GEN-LAST:event_btnlimpiar_clienteActionPerformed

    private void btnconfirmar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_ventaActionPerformed
        // TODO add your handling code here:
        boton_guardar_venta();
    }//GEN-LAST:event_btnconfirmar_ventaActionPerformed

    private void btnagregar_deliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_deliveryActionPerformed
        // TODO add your handling code here:
        boton_tipo_entrega_Delivery();
    }//GEN-LAST:event_btnagregar_deliveryActionPerformed

    private void btnagregar_localActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_localActionPerformed
        // TODO add your handling code here:
        boton_tipo_entrega_Local();
    }//GEN-LAST:event_btnagregar_localActionPerformed

    private void btnagregar_paqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_paqueteActionPerformed
        // TODO add your handling code here:
        boton_tipo_entrega_Paquete();
    }//GEN-LAST:event_btnagregar_paqueteActionPerformed

    private void btnanularventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularventaActionPerformed
        // TODO add your handling code here:
        boton_estado_venta_anular();
    }//GEN-LAST:event_btnanularventaActionPerformed

    private void btnfacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfacturarActionPerformed
        // TODO add your handling code here:
        boton_factura();

    }//GEN-LAST:event_btnfacturarActionPerformed

    private void btnterminarcomandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnterminarcomandaActionPerformed
        // TODO add your handling code here:
        boton_estado_venta_terminar();
    }//GEN-LAST:event_btnterminarcomandaActionPerformed

    private void btnimprimirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirNotaActionPerformed
        // TODO add your handling code here:
        boton_imprimirPos_venta();
    }//GEN-LAST:event_btnimprimirNotaActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_terminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_terminadoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCestado_terminadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void txtbuscar_idventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_venta(2);
        }
    }//GEN-LAST:event_txtbuscar_idventaKeyPressed

    private void txtbuscar_idventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtbuscar_idventaKeyTyped

    private void txtbuscar_fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_fechaKeyPressed
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtbuscar_fecha);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_venta(3);
        }
    }//GEN-LAST:event_txtbuscar_fechaKeyPressed

    private void jCfecha_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfecha_todosActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCfecha_todosActionPerformed

    private void jRtipo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_clienteActionPerformed
        // TODO add your handling code here:
        //        jCdelivery.setSelected(true);
    }//GEN-LAST:event_jRtipo_clienteActionPerformed

    private void jRtipo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_funcionarioActionPerformed
        // TODO add your handling code here:
        //        jCdelivery.setSelected(false);
    }//GEN-LAST:event_jRtipo_funcionarioActionPerformed

    private void txtcli_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_rucKeyPressed
        // TODO add your handling code here:
        //        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtcli_ruc, txtcli_telefono);
    }//GEN-LAST:event_txtcli_rucKeyPressed

    private void txtcli_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_telefonoKeyPressed
        // TODO add your handling code here:
        //        pasarCampoEnter2(evt, txttelefono, txtdireccion);
        evejtf.saltar_campo_enter(evt, txtcli_telefono, txtcli_direccion);
    }//GEN-LAST:event_txtcli_telefonoKeyPressed

    private void txtcli_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_nombreKeyPressed
        // TODO add your handling code here:
        //        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtcli_nombre, txtcli_ruc);
    }//GEN-LAST:event_txtcli_nombreKeyPressed

    private void txtcli_fecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_fecha_nacimientoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcli_fecha_nacimiento.setText(evefec.getString_validar_fecha(txtcli_fecha_nacimiento.getText()));
            evejtf.saltar_campo_enter(evt, txtcli_fecha_nacimiento, txtcli_zona);
        }

    }//GEN-LAST:event_txtcli_fecha_nacimientoKeyPressed

    private void txtcli_fecha_nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_fecha_nacimientoKeyReleased
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtcli_fecha_nacimiento);
    }//GEN-LAST:event_txtcli_fecha_nacimientoKeyReleased

    private void txtcli_fecha_nacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_fecha_nacimientoKeyTyped
        // TODO add your handling code here:
        //        fo.soloFechaText(evt);
    }//GEN-LAST:event_txtcli_fecha_nacimientoKeyTyped

    private void jLzonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzonaMouseReleased
        // TODO add your handling code here:
        cargar_zona_cliente();
    }//GEN-LAST:event_jLzonaMouseReleased

    private void jLzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzonaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_zona_cliente();
        }
    }//GEN-LAST:event_jLzonaKeyReleased

    private void txtcli_zonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_zonaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jLzona);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcli_zona.setBackground(Color.WHITE);
            txtcli_direccion.setBackground(Color.YELLOW);
            txtcli_direccion.grabFocus();
        }
    }//GEN-LAST:event_txtcli_zonaKeyPressed

    private void txtcli_zonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_zonaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtcli_zona, jLzona, zona.getTabla(), zona.getNombretabla(), zona.getZona_mostrar(), 10);
    }//GEN-LAST:event_txtcli_zonaKeyReleased

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_cliente();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_cliente();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_cliente();
    }//GEN-LAST:event_btneditarActionPerformed

    private void tblpro_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpro_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_cliente();
    }//GEN-LAST:event_tblpro_clienteMouseReleased

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(connLocal, tblpro_cliente, txtbuscar_nombre, 1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_telefonoKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(connLocal, tblpro_cliente, txtbuscar_telefono, 2);
    }//GEN-LAST:event_txtbuscar_telefonoKeyReleased

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(connLocal, tblpro_cliente, txtbuscar_ruc, 3);
    }//GEN-LAST:event_txtbuscar_rucKeyReleased

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
    }//GEN-LAST:event_btnnuevo_clienteActionPerformed

    private void tblbuscar_clienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscar_clienteMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            habilitar_crear_cliente_corto = false;
            seleccionar_cargar_cliente(3);
            limpiar_buscardor_cliente();
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
        }
    }//GEN-LAST:event_tblbuscar_clienteMousePressed

    private void tblbuscar_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscar_clienteMouseReleased
        // TODO add your handling code here:
        // anchotabla();
    }//GEN-LAST:event_tblbuscar_clienteMouseReleased

    private void tblbuscar_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscar_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!evejt.getBoolean_validar_select(tblbuscar_cliente)) {
                seleccionar_cargar_cliente(3);
                limpiar_buscardor_cliente();
                evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
            }
        }
    }//GEN-LAST:event_tblbuscar_clienteKeyPressed

    private void txtbuscador_cliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_nombreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_nombreKeyPressed

    private void txtbuscador_cliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_nombreKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_nombre, jCfuncionario, 1);
    }//GEN-LAST:event_txtbuscador_cliente_nombreKeyReleased

    private void txtbuscador_cliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_telefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_telefonoKeyPressed

    private void txtbuscador_cliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_telefonoKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }//GEN-LAST:event_txtbuscador_cliente_telefonoKeyReleased

    private void txtbuscador_cliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_rucKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_rucKeyPressed

    private void txtbuscador_cliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_rucKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_ruc, jCfuncionario, 3);
    }//GEN-LAST:event_txtbuscador_cliente_rucKeyReleased

    private void jCfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfuncionarioActionPerformed
        // TODO add your handling code here:
        if (jCfuncionario.isSelected()) {
            panel_tabla_busca_cli.setBackground(Color.orange);
        } else {
            panel_tabla_busca_cli.setBackground(clacolor.getColor_tabla());//[51,102,255]
        }
        txtbuscador_cliente_nombre.grabFocus();
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_nombre, jCfuncionario, 1);
    }//GEN-LAST:event_jCfuncionarioActionPerformed

    private void btnnuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoClienteActionPerformed
        // TODO add your handling code here:
        limpiar_buscardor_cliente();
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
    }//GEN-LAST:event_btnnuevoClienteActionPerformed

    private void btnseleccionarCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionarCerrarActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblbuscar_cliente)) {
            seleccionar_cargar_cliente(3);
            limpiar_buscardor_cliente();
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
        }
    }//GEN-LAST:event_btnseleccionarCerrarActionPerformed

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
        boton_buscar_cliente_venta();
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void btnagregar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_cantidadActionPerformed
        // TODO add your handling code here:
        cargar_item_producto();
    }//GEN-LAST:event_btnagregar_cantidadActionPerformed

    private void tblventaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblventaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_venta();
    }//GEN-LAST:event_tblventaMouseReleased

    private void jRcampo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcampo_clienteActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jRcampo_clienteActionPerformed

    private void jRcampo_comandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcampo_comandaActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jRcampo_comandaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE CANCELAR", "CANCELAR VENTA", "---SI---", "---NO---")) {
            reestableser_venta();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnobservacion_ingreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnobservacion_ingreActionPerformed
        // TODO add your handling code here:
        cargar_item_producto_observacion();
    }//GEN-LAST:event_btnobservacion_ingreActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        ven.setVenta_aux(false);
    }//GEN-LAST:event_formInternalFrameClosing

    private void btnnuevazonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevazonaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmZonaDelivery());
    }//GEN-LAST:event_btnnuevazonaActionPerformed

    private void btnlimpiarzonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarzonaActionPerformed
        // TODO add your handling code here:
        txtcli_zona.setText(null);
        txtcli_delivery.setText(null);
        txtcli_zona.grabFocus();
    }//GEN-LAST:event_btnlimpiarzonaActionPerformed

    private void btnactualizar_tablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_tablaActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_btnactualizar_tablaActionPerformed

    private void btnduplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnduplicarActionPerformed
        // TODO add your handling code here:
        boton_copiar_venta();
    }//GEN-LAST:event_btnduplicarActionPerformed

    private void btncocinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncocinaActionPerformed
        // TODO add your handling code here:
        boton_pasar_cocina();
    }//GEN-LAST:event_btncocinaActionPerformed

    private void tblentregadorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentregadorMouseReleased
        // TODO add your handling code here:
        SELECCIONAR_cambiar_entregador();
    }//GEN-LAST:event_tblentregadorMouseReleased

    private void btnpreimpresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpreimpresoActionPerformed
        // TODO add your handling code here:
        posmesa.boton_imprimir_pos_venta_mesa(connLocal, "0", idventa_mesa_select, 2);
    }//GEN-LAST:event_btnpreimpresoActionPerformed

    private void btncerrar_mesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrar_mesaActionPerformed
        // TODO add your handling code here:
        boton_cerrar_mesa();
    }//GEN-LAST:event_btncerrar_mesaActionPerformed

    private void btnseleccionar_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionar_cerrarActionPerformed
        // TODO add your handling code here:
        boton_ir_venta();
    }//GEN-LAST:event_btnseleccionar_cerrarActionPerformed

    private void btnmesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmesasActionPerformed
        // TODO add your handling code here:
        boton_seleccionar_venta_mesa();
    }//GEN-LAST:event_btnmesasActionPerformed

    private void txtdescontarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescontarKeyReleased
        // TODO add your handling code here:
//        evejtf.getString_format_nro_entero(txtdescontar);
    }//GEN-LAST:event_txtdescontarKeyReleased

    private void btndescontarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndescontarActionPerformed
        // TODO add your handling code here:
        cargar_item_producto_descontar();
    }//GEN-LAST:event_btndescontarActionPerformed

    private void txtdescontarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescontarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto_descontar();
        }
    }//GEN-LAST:event_txtdescontarKeyPressed

    private void txtcli_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_direccionKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtcli_direccion, txtcli_zona);
    }//GEN-LAST:event_txtcli_direccionKeyPressed

    private void txtbucarCliente_zonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_zonaKeyReleased
        // TODO add your handling code here:
        cargar_zona_desde_nombre();
    }//GEN-LAST:event_txtbucarCliente_zonaKeyReleased

    private void txtbucarCliente_zonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_zonaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            guardar_cliente_corto();
        }
    }//GEN-LAST:event_txtbucarCliente_zonaKeyPressed

    private void txtbucarCliente_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_direccionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_zona.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_direccionKeyPressed

    private void btnultima_impresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnultima_impresionActionPerformed
        // TODO add your handling code here:
        if (idventa_ult_print > 0) {
            imprimir_venta(idventa_ult_print);
        }
    }//GEN-LAST:event_btnultima_impresionActionPerformed

    private void txtdescontarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescontarKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtdescontarKeyTyped

    private void txtbucarCliente_zonaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_zonaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtbucarCliente_zonaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_tabla;
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btnagregar_delivery;
    private javax.swing.JButton btnagregar_local;
    private javax.swing.JButton btnagregar_paquete;
    private javax.swing.JButton btnanularventa;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btncantidad_1;
    private javax.swing.JButton btncantidad_2;
    private javax.swing.JButton btncantidad_3;
    private javax.swing.JButton btncantidad_4;
    private javax.swing.JButton btncantidad_5;
    private javax.swing.JButton btncerrar_mesa;
    private javax.swing.JButton btncocina;
    private javax.swing.JButton btnconfirmar_venta;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btndescontar;
    private javax.swing.JButton btnduplicar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnfacturar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimirNota;
    private javax.swing.JButton btningrediente_agregar;
    private javax.swing.JButton btningrediente_quitar;
    private javax.swing.JButton btnlimpiar_cliente;
    private javax.swing.JButton btnlimpiarzona;
    private javax.swing.JButton btnmesas;
    private javax.swing.JButton btnnuevazona;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevoCliente;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JButton btnobservacion_ingre;
    private javax.swing.JButton btnpreimpreso;
    private javax.swing.JButton btnseleccionarCerrar;
    private javax.swing.JButton btnseleccionar_cerrar;
    private javax.swing.JButton btnterminarcomanda;
    private javax.swing.JButton btnultima_impresion;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.ButtonGroup gru_pago;
    private javax.swing.ButtonGroup gru_tipocli;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JCheckBox jCestado_terminado;
    private javax.swing.JCheckBox jCfecha_todos;
    private javax.swing.JCheckBox jCfuncionario;
    private javax.swing.JCheckBox jCvuelto;
    private javax.swing.JFormattedTextField jFPrecio_zona;
    private javax.swing.JFormattedTextField jFmonto_mesa;
    private javax.swing.JFormattedTextField jFtotal_dolar;
    private javax.swing.JFormattedTextField jFtotal_guarani;
    private javax.swing.JFormattedTextField jFtotal_real;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JList<String> jLzona;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPboton_mesa;
    private javax.swing.JRadioButton jRcampo_cliente;
    private javax.swing.JRadioButton jRcampo_comanda;
    private javax.swing.JRadioButton jRpago_efectivo;
    private javax.swing.JRadioButton jRpago_tarjeta;
    private javax.swing.JRadioButton jRtipo_cliente;
    private javax.swing.JRadioButton jRtipo_funcionario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_VENTA;
    private javax.swing.JLabel lblcantidad_filtro;
    private javax.swing.JLabel lblfecnac;
    private javax.swing.JLabel lblidcliente;
    private javax.swing.JLabel lblnombre_producto_ingre;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_base_2;
    private javax.swing.JPanel panel_insertar_pri_item;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_insertar_sec_cliente;
    private javax.swing.JPanel panel_insertar_sec_ingrediente;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JPanel panel_referencia_venta;
    private javax.swing.JPanel panel_tabla_busca_cli;
    private javax.swing.JPanel panel_tabla_cliente;
    private javax.swing.JPanel panel_tabla_entregador;
    private javax.swing.JPanel panel_tabla_item;
    private javax.swing.JPanel panel_tabla_venta;
    private javax.swing.JTable tblbuscar_cliente;
    private javax.swing.JTable tblentregador;
    private javax.swing.JTable tblingrediente;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblitem_venta_filtro;
    private javax.swing.JTable tblpro_cliente;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTable tblventa;
    private javax.swing.JTextArea txtaVistaPrevia;
    private javax.swing.JTextField txtbucarCliente_direccion;
    private javax.swing.JTextField txtbucarCliente_nombre;
    private javax.swing.JTextField txtbucarCliente_ruc;
    private javax.swing.JTextField txtbucarCliente_telefono;
    private javax.swing.JTextField txtbucarCliente_zona;
    private javax.swing.JTextField txtbuscador_cliente_nombre;
    private javax.swing.JTextField txtbuscador_cliente_ruc;
    private javax.swing.JTextField txtbuscador_cliente_telefono;
    private javax.swing.JTextField txtbuscar_fecha;
    private javax.swing.JTextField txtbuscar_idventa;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtbuscar_ruc;
    private javax.swing.JTextField txtbuscar_telefono;
    private javax.swing.JTextField txtcantidad_producto;
    public static javax.swing.JTextField txtcli_delivery;
    private javax.swing.JTextField txtcli_direccion;
    private javax.swing.JTextField txtcli_fecha_inicio;
    private javax.swing.JTextField txtcli_fecha_nacimiento;
    private javax.swing.JTextField txtcli_idcliente;
    private javax.swing.JTextField txtcli_nombre;
    private javax.swing.JTextField txtcli_ruc;
    private javax.swing.JTextField txtcli_telefono;
    public static javax.swing.JTextField txtcli_zona;
    private javax.swing.JTextField txtdescontar;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtnumero_mesa;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtobservacion_ingre;
    // End of variables declaration//GEN-END:variables
}
