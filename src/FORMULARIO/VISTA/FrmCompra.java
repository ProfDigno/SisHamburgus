/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import FORMULARIO.ENTIDAD.cotizacion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
//import BASEDATO.SERVIDOR.ConnPostgres_SER;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.BO_cliente;
import FORMULARIO.BO.BO_compra;
import FORMULARIO.BO.BO_compra_insumo;
import FORMULARIO.BO.BO_itemven_insumo;
import FORMULARIO.BO.BO_venta;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import static FORMULARIO.VISTA.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.FrmCliente.txtzona;
import IMPRESORA_POS.PosImprimir_Compra;
import IMPRESORA_POS.PosImprimir_Compra_insumo;
import IMPRESORA_POS.PosImprimir_Venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Digno
 */
public class FrmCompra extends javax.swing.JInternalFrame {

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
    caja_detalle caja = new caja_detalle();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    DAO_producto ipdao = new DAO_producto();
    producto insup = new producto();
    proveedor ent_prov=new proveedor();
    compra compi = new compra();
    DAO_compra cidao = new DAO_compra();
    BO_compra ciBO = new BO_compra();
    DAO_item_compra icidao = new DAO_item_compra();
    DAO_caja_detalle cdao = new DAO_caja_detalle();
    usuario usu = new usuario();
    PosImprimir_Compra poscomp = new PosImprimir_Compra();
    Connection connLocal = ConnPostgres.getConnPosgres();
    DefaultTableModel model_itemf = new DefaultTableModel();
    cla_color_pelete clacolor = new cla_color_pelete();
    private String cantidad_producto = "0";
    private String tiponota = "PEDIDO";
    private String estado = "EMITIDO";
    private double monto_compra;
    String insu_tabla = "producto p,producto_categoria c,producto_unidad u ";
    String insu_mostrar = "(c.nombre||'-'||u.nombre||'-'||p.nombre) as producto";
    String insu_buscar = "p.fk_idproducto_categoria=c.idproducto_categoria \n"
            + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
            + "and p.comprar=true\n"
            + "and concat(c.nombre||'-'||u.nombre||'-'||p.nombre)";
    private int idinsumo_producto = -1;
    private String est_ANULADO = "ANULADO";
    private String est_EMITIDO = "EMITIDO";
    private String est_CONFIRMADO = "CONFIRMADO";
    private String tip_COMPRADO = "COMPRADO";
    private int idcompra_insumo_editar;
    private boolean habilitar_editar;
    private int idcompra_insumo_ultimo = 0;
    private boolean hab_update_precio_insumo;
    private String indice_creado;
    private String indice_select;
    private int idcompra_insumo_select;
    DAO_proveedor DAO_prov = new DAO_proveedor();
    private int fk_idproveedor;
    void abrir_formulario() {
        String servidor = "";
        this.setTitle("COMPRA--> USUARIO:" + usu.getGlobal_nombre() + servidor);
        evetbl.centrar_formulario(this);
        codao.cargar_cotizacion(coti, 1);
        crear_item_producto();
        reestableser_compra();
        color_formulario();
    }
    private void cargar_proveedor() {
        fk_idproveedor = 0;
        ent_prov.setIdproveedor_static(fk_idproveedor);
        DAO_prov.cargar_proveedor(connLocal, ent_prov, fk_idproveedor);
        txtprovee_nombre.setText(ent_prov.getC3nombre());
        txtprovee_ruc.setText(ent_prov.getC6ruc());
    }
    void color_formulario() {
        panel_tabla_compra.setBackground(clacolor.getColor_tabla());
        panel_insertar_pri_compra.setBackground(clacolor.getColor_insertar_primario());
        panel_insertar_seg_compra.setBackground(clacolor.getColor_insertar_secundario());
        panel_referencia_filtro.setBackground(clacolor.getColor_referencia());
        panel_base_1.setBackground(clacolor.getColor_base());
        panel_base_2.setBackground(clacolor.getColor_base());
    }

    void crear_item_producto() {
        String dato[] = {"id", "CATEGORIA-PRODUCTO", "UNIDAD", "PRE_COMPRA", "CANTIDAD", "SUBTOTAL"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    void ancho_item_producto() {
        int Ancho[] = {5, 50, 13, 11, 10, 11};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
    }

    void sumar_item_compra() {
        evejt.calcular_subtotal(tblitem_producto, model_itemf, 4, 3, 5);
        double total_guarani = evejt.getDouble_sumar_tabla(tblitem_producto, 5);
        monto_compra = total_guarani;
        jFtotal_guarani.setValue(total_guarani);
    }

    void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                sumar_item_compra();
            }
        }
    }

    void reestableser_compra() {
        idcompra_insumo_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, compi.getTb_compra(), compi.getId_idcompra()));
        indice_creado = eveut.getString_crear_indice();
        txtidventa.setText(String.valueOf(idcompra_insumo_ultimo));
        txtbuscar_fecha.setText(evefec.getString_formato_fecha());
        jList_insumo_producto.setVisible(false);
        evejt.limpiar_tabla_datos(model_itemf);
        txtobservacion.setText("Ninguna");
        monto_compra = 0;
        sumar_item_compra();
        cidao.actualizar_tabla_compra(connLocal, tblcompra_insumo);
        habilitar_editar = false;
        btnconfirmar_insertar.setText("CONFIRMAR");
        cargar_proveedor();
        txtbuscar_insumo_producto.grabFocus();
    }

    void seleccionar_buscar_insumo() {
        idinsumo_producto = eveconn.getInt_Solo_seleccionar_JLista(connLocal, jList_insumo_producto, insu_tabla, insu_buscar, "idproducto");
        ipdao.cargar_producto(insup, idinsumo_producto);
        txtbuscar_insumo_producto.setText(insup.getP12categoria()+"-"+insup.getP13unidad()+"-"+insup.getP2nombre());
        txtinsumo_unidad.setText(insup.getP13unidad());
        txtinsumo_pre_com.setText(String.valueOf(insup.getP4precio_compra()));
        txtinsumo_cant.setBackground(Color.ORANGE);
        txtinsumo_cant.setText("1");
        txtinsumo_cant.grabFocus();
    }

    boolean validar_subtotal() {
        if (txtinsumo_cant.getText().trim().length() < 0) {
            return false;
        }
        if (txtinsumo_pre_com.getText().trim().length() < 0) {
            return false;
        }
        return true;
    }

    void calcular_subtotal_item() {
        if (validar_subtotal()) {
            try {
                double cantidad = Double.parseDouble(txtinsumo_cant.getText());
                double precio = Double.parseDouble(txtinsumo_pre_com.getText());
                double subtotal = cantidad * precio;
                jFsutotal.setValue(subtotal);
            } catch (Exception e) {
                System.out.println("ERROR calcular_subtotal_item:" + e);
            }

        }
    }

    boolean validar_carga_item() {
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_insumo_producto, "CARGAR NOMBRE")) {
            txtbuscar_insumo_producto.setText(insup.getP12categoria()+"-"+insup.getP13unidad() + "-" + insup.getP2nombre());
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtinsumo_cant, "CARGAR CANTIDAD")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtinsumo_pre_com, "CARGAR PRECIO")) {
            txtinsumo_pre_com.setText(String.valueOf(insup.getP4precio_compra()));
            return false;
        }
        if (idinsumo_producto == -1) {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO\nESCRIBIR EL PRODUCTO INSUMO", "ERROR", JOptionPane.ERROR_MESSAGE);
            reestableser_item();
            return false;
        }
        return true;
    }

    void reestableser_item() {
        txtbuscar_insumo_producto.setText(null);
        txtinsumo_unidad.setText(null);
        txtinsumo_cant.setText(null);
        txtinsumo_pre_com.setText(null);
        jFsutotal.setValue(0);
        idinsumo_producto = -1;
        txtbuscar_insumo_producto.grabFocus();
    }

    void update_precio_insumo() {
        if (hab_update_precio_insumo) {
            ipdao.update_producto_precio_compra(connLocal, insup);
            hab_update_precio_insumo = false;
        }
    }

    void cargar_item_insumo() {
        if (validar_carga_item()) {
            txtinsumo_pre_com.setBackground(Color.WHITE);
            txtinsumo_cant.setBackground(Color.WHITE);
            ipdao.cargar_producto(insup, idinsumo_producto);
            String idproducto = String.valueOf(idinsumo_producto);
            String descripcion = txtbuscar_insumo_producto.getText();
            String unidad = txtinsumo_unidad.getText();
            String precioUni = txtinsumo_pre_com.getText();
            String cantidad = txtinsumo_cant.getText();
            try {
                double Dcantidad = Double.parseDouble(cantidad);
                double DprecioUni = Double.parseDouble(precioUni);
                String total = String.valueOf(DprecioUni * Dcantidad);
                String dato[] = {idproducto, descripcion, unidad, precioUni, cantidad, total};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
                insup.setP1idproducto(idinsumo_producto);
                insup.setP4precio_compra(DprecioUni);
                update_precio_insumo();
                sumar_item_compra();
                reestableser_item();
            } catch (Exception e) {
                System.out.println("ERROR:" + e);
            }

        }
    }

    void boton_cancelar() {
        if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE CANCELAR", "CANCELAR", "ACEPTAR", "NO-CANCELAR")) {
            reestableser_compra();
            reestableser_item();
        }
    }

    boolean validar_compra() {
        if (evejt.getBoolean_validar_cant_cargado(tblitem_producto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "CARGAR UNA OBSERVACION")) {
            return false;
        }
        return true;
    }

    void cargar_datos_compra() {
        compi.setC3estado(estado);
        compi.setC4observacion(txtobservacion.getText());
        compi.setC5forma_pago(tiponota);
        compi.setC6monto_compra(monto_compra);
        compi.setC7fk_idproveedor(ent_prov.getIdproveedor_static());
        compi.setC8fk_idusuario(usu.getGlobal_idusuario());
        compi.setC9indice(indice_creado);
        
    }
    void cargar_datos_caja() {
        caja.setC2fecha_emision(evefec.getString_formato_fecha_hora());
        caja.setC3descripcion("(COMPRA) id:" + idcompra_insumo_ultimo + " Pro:" + txtprovee_nombre.getText());
        caja.setC4monto_venta(0);
        caja.setC5monto_delivery(0);
        caja.setC6monto_gasto(0);
        caja.setC7monto_compra(monto_compra);
        caja.setC8monto_vale(0);
        caja.setC9id_origen(idcompra_insumo_ultimo);
        caja.setC10tabla_origen("COMPRA");
        caja.setC11fk_idusuario(usu.getGlobal_idusuario());
        caja.setC12indice(indice_creado);
        caja.setC13equipo1(evepc.getString_nombre_pc());
        caja.setC15monto_caja1(0);
        caja.setC16monto_cierre(0);
    }
    void boton_comfirmar_compra() {
        if (validar_compra()) {
            sumar_item_compra();
            cargar_datos_compra();
            cargar_datos_caja();
            if (ciBO.getBoolean_compra(tblitem_producto, compi,caja)) {
                if (habilitar_editar) {
                    habilitar_editar = false;
                    btnconfirmar_insertar.setText("CONFIRMAR");
                    anular_compra(idcompra_insumo_editar, indice_select);
                }
                poscomp.boton_imprimir_pos_compra(connLocal, idcompra_insumo_ultimo);
                reestableser_compra();
            }
        }
    }

    void seleccionar_compra_insumo() {
        if (!evejt.getBoolean_validar_select(tblcompra_insumo)) {
            String estado = evejt.getString_select(tblcompra_insumo, 5);
            idcompra_insumo_select = evejt.getInt_select_id(tblcompra_insumo);
            indice_select = evejt.getString_select(tblcompra_insumo, 1);
            if (estado.equals(est_ANULADO)) {
                btnanularventa.setEnabled(false);
                btnconfirmado.setEnabled(false);
                btneditar_compra.setEnabled(true);
            }
            if (estado.equals(est_EMITIDO)) {
                btnanularventa.setEnabled(true);
                btnconfirmado.setEnabled(true);
                btneditar_compra.setEnabled(true);
            }
            if (estado.equals(est_CONFIRMADO)) {
                btnanularventa.setEnabled(false);
                btnconfirmado.setEnabled(false);
                btneditar_compra.setEnabled(false);
            }
            int idcompra_insumo = evejt.getInt_select_id(tblcompra_insumo);
            icidao.tabla_item_compra_insumo_filtro(connLocal, tblitem_compra_insumo, idcompra_insumo);
        }
    }

    void anular_compra(int idcompra_insumo, String indice) {
        compi.setC1idcompra(idcompra_insumo);
        compi.setC3estado(est_ANULADO);
        compi.setC9indice(indice);
        caja.setC12indice(indice);
        ciBO.update_anular_compra(connLocal, compi, caja);
        cidao.actualizar_tabla_compra(connLocal, tblcompra_insumo);
    }

    void boton_anular_compra() {
        if (!evejt.getBoolean_validar_select(tblcompra_insumo)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR ESTA COMPRA", "ANULAR", "ACEPTAR", "CANCELAR")) {
                int idcompra_insumo = evejt.getInt_select_id(tblcompra_insumo);
                String indice = evejt.getString_select(tblcompra_insumo, 1);
                anular_compra(idcompra_insumo, indice);
            }
        }
    }

    void boton_imprimir_pos() {
        if (!evejt.getBoolean_validar_select(tblcompra_insumo)) {
            int idcompra_insumo = evejt.getInt_select_id(tblcompra_insumo);
            poscomp.boton_imprimir_pos_compra(connLocal, idcompra_insumo);
        }
    }

    void boton_imprimir_json() {
        if (!evejt.getBoolean_validar_select(tblcompra_insumo)) {
            JSONObject json_itemCompra = new JSONObject();
            int idcompra_insumo = evejt.getInt_select_id(tblcompra_insumo);
            json_itemCompra.put("fecha", evefec.getString_formato_fecha_hora());
            json_itemCompra.put("idcompra_insumo", new Integer(idcompra_insumo));
            String titulo = "boton_imprimir_json";
            String sql = "select ip.codbarra,cp.cantidad\n"
                    + "from insumo_producto ip,item_compra_insumo cp\n"
                    + "where ip.idinsumo_producto=cp.fk_idinsumo_producto\n"
                    + "and cp.fk_idcompra_insumo=" + idcompra_insumo;
            try {
                ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
                JSONArray list_cod = new JSONArray();
                JSONArray list_cant = new JSONArray();
                while (rs.next()) {
                    int codbarra = rs.getInt("codbarra");
                    int cantidad = rs.getInt("cantidad");
                    list_cant.add(cantidad);
                    list_cod.add(codbarra);
                }
                json_itemCompra.put("cantidad", list_cant);
                json_itemCompra.put("codbarra", list_cod);
                JOptionPane.showMessageDialog(null, "json\n" + json_itemCompra);
                System.out.println("json:" + json_itemCompra);

            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
            crear_archivo_json(json_itemCompra);
        }
    }

    void crear_archivo_json(JSONObject json_itemCompra) {
        try {
            String fecha = evefec.getString_formato_fecha();
            FileWriter file = new FileWriter("C:\\Users\\PROBA SUC01\\Dropbox\\SISTEMA_PROBA\\SISTEMA_A8_server\\JSON\\Pedido_compra_" + fecha + ".json");
            file.write(json_itemCompra.toJSONString());
            file.flush();
            file.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        } finally {
            System.out.print(json_itemCompra);
        }
    }

    void boton_confirmar_compra() {
        if (!evejt.getBoolean_validar_select(tblcompra_insumo)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE CONFIRMAR ESTA COMPRA", "CONFIRMAR", "ACEPTAR", "CANCELAR")) {
                int idcompra_insumo = evejt.getInt_select_id(tblcompra_insumo);
                compi.setC1idcompra(idcompra_insumo);
                compi.setC3estado(est_CONFIRMADO);
                compi.setC5forma_pago(tip_COMPRADO);
//                if (ciBO.getBoolean_update_estado_compra_insumo(compi, caja, false)) {
//                    cargar_datos_caja_insertar(idcompra_insumo);
//                    cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, "");
//                }
            }
        }
    }

    private void boton_editar_compra() {
        if (!evejt.getBoolean_validar_select(tblcompra_insumo)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE EDITAR ESTA COMPRA\nAL EDITAR, SE ANULA EL ORIGINAL Y SE CREA UNA COPIA", "EDITAR COMPRA", "ACEPTAR", "CANCELAR")) {
                idcompra_insumo_editar = evejt.getInt_select_id(tblcompra_insumo);
                String titulo = "boton_editar_compra";
                String sql = "select fk_idinsumo_producto,nombre,\n"
                        + "unidad,precio,cantidad,(precio*cantidad) as subtotal \n"
                        + "from item_compra_insumo\n"
                        + "where fk_idcompra_insumo=" + idcompra_insumo_editar;
                try {
                    ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
                    while (rs.next()) {
                        String fk_idinsumo_producto = rs.getString("fk_idinsumo_producto");
                        String nombre = rs.getString("nombre");
                        String unidad = rs.getString("unidad");
                        String precio = rs.getString("precio");
                        String cantidad = rs.getString("cantidad");
                        String subtotal = rs.getString("subtotal");
                        String dato[] = {fk_idinsumo_producto, nombre, unidad, precio, cantidad, subtotal};
                        evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
                    }
                } catch (Exception e) {
                    evemen.mensaje_error(e, sql, titulo);
                }
                habilitar_editar = true;
                btnconfirmar_insertar.setText("CONFIRMAR-EDITAR");
                evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
                sumar_item_compra();
            }
        }
    }

    public String filtro_estado() {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCestado_emitido.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " ci.estado='EMITIDO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_confirmado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " ci.estado='CONFIRMADO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_anulado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " ci.estado='ANULADO' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }

    private void buscar_insumo_producto() {
        if (txtbuscar_insumo_producto.getText().trim().length() > 0) {
            if (idinsumo_producto == -1) {
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO\nESCRIBIR EL PRODUCTO INSUMO", "ERROR", JOptionPane.ERROR_MESSAGE);
                reestableser_item();
            } else {
                txtinsumo_cant.setBackground(Color.ORANGE);
                txtinsumo_cant.grabFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "ESCRIBIR NOMBRE DEL PRODUCTO INSUMO\nLUEGO SELECCIONAR EL CUADRO DE BUSQUEDA", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtbuscar_insumo_producto.grabFocus();
        }
    }

    void cargar_datos_caja_insertar(int idcompra_insumo) {
        cidao.cargar_compra(connLocal, compi, idcompra_insumo);
        caja.setC2fecha_emision(evefec.getString_formato_fecha_hora());
        caja.setC3descripcion("(COMPRA) id:" + idcompra_insumo + " Usuario:" + usu.getGlobal_nombre());
        caja.setC4monto_venta(0);
        caja.setC5monto_delivery(0);
        caja.setC6monto_gasto(0);
        caja.setC7monto_compra(compi.getC6monto_compra());
        caja.setC8monto_vale(0);
        caja.setC9id_origen(idcompra_insumo);
        caja.setC10tabla_origen("COMPRA");
        caja.setC11fk_idusuario(usu.getGlobal_idusuario());
        caja.setC12indice(indice_creado);
        caja.setC13equipo1(evepc.getString_nombre_pc());
        caja.setC15monto_caja1(0);
        caja.setC16monto_cierre(0);
        cdao.insertar_caja_detalle(connLocal, caja);
    }

    void cargar_itemventa_cantidad(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (txtinsumo_cant.getText().trim().length() > 0) {
                String cant_actual = txtinsumo_cant.getText().trim();
                double Icant_actual = Double.parseDouble(cant_actual);
                Icant_actual = Icant_actual - 1;
                if (Icant_actual <= 0) {
                    Icant_actual = 1;
                }
                txtinsumo_cant.setText(String.valueOf(Icant_actual));
            }
            calcular_subtotal_item();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (txtinsumo_cant.getText().trim().length() > 0) {
                String cant_actual = txtinsumo_cant.getText().trim();
                double Icant_actual = Double.parseDouble(cant_actual);
                Icant_actual = Icant_actual + 1;
                txtinsumo_cant.setText(String.valueOf(Icant_actual));
            }
            calcular_subtotal_item();
        }
    }

    public FrmCompra() {
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

        gru_campo = new javax.swing.ButtonGroup();
        jTabbedPane_VENTA = new javax.swing.JTabbedPane();
        panel_base_2 = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_compra = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_insumo_producto = new javax.swing.JTextField();
        panel_insertar_seg_compra = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jFtotal_guarani = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        btneliminar_item = new javax.swing.JButton();
        btnconfirmar_insertar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnsumar = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_insumo_producto = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtinsumo_unidad = new javax.swing.JTextField();
        txtinsumo_cant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtinsumo_pre_com = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jFsutotal = new javax.swing.JFormattedTextField();
        btnnuevo_insumo = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtidventa = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtprovee_nombre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtprovee_ruc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnbuscar_provee = new javax.swing.JButton();
        btnnuevo_provee = new javax.swing.JButton();
        panel_base_1 = new javax.swing.JPanel();
        panel_tabla_compra = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcompra_insumo = new javax.swing.JTable();
        btnanularventa = new javax.swing.JButton();
        btnconfirmado = new javax.swing.JButton();
        btnimprimirNota = new javax.swing.JButton();
        panel_referencia_filtro = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_idventa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_fecha = new javax.swing.JTextField();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_confirmado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_compra_insumo = new javax.swing.JTable();
        btneditar_compra = new javax.swing.JButton();
        btnimprimirJSON = new javax.swing.JButton();

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

        panel_insertar_pri_compra.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_compra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTO:");

        txtbuscar_insumo_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbuscar_insumo_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_insumo_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_insumo_productoKeyReleased(evt);
            }
        });

        panel_insertar_seg_compra.setBackground(new java.awt.Color(102, 204, 255));
        panel_insertar_seg_compra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("GUARANI:");

        jFtotal_guarani.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_guarani.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_guarani.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("OBSERVACION:");

        txtobservacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtobservacion.setText("ninguna");
        txtobservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtobservacionActionPerformed(evt);
            }
        });

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        btnconfirmar_insertar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnconfirmar_insertar.setText("CONFIRMAR");
        btnconfirmar_insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_insertarActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnsumar.setText("SUMAR TABLA");
        btnsumar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsumarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_seg_compraLayout = new javax.swing.GroupLayout(panel_insertar_seg_compra);
        panel_insertar_seg_compra.setLayout(panel_insertar_seg_compraLayout);
        panel_insertar_seg_compraLayout.setHorizontalGroup(
            panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtobservacion)
                        .addGap(387, 387, 387))
                    .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                        .addGroup(panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                                .addComponent(jFtotal_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnconfirmar_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnsumar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 172, Short.MAX_VALUE))))
        );
        panel_insertar_seg_compraLayout.setVerticalGroup(
            panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFtotal_guarani, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnconfirmar_insertar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsumar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLayeredPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_insumo_producto.setBackground(new java.awt.Color(204, 204, 255));
        jList_insumo_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_insumo_producto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_insumo_producto.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10" };
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
        });
        jLayeredPane1.add(jList_insumo_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 8, 520, 190));

        tblitem_producto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        tblitem_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblitem_productoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblitem_producto);

        jLayeredPane1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 910, 210));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("UNIDAD:");

        txtinsumo_unidad.setEditable(false);
        txtinsumo_unidad.setBackground(new java.awt.Color(204, 204, 255));
        txtinsumo_unidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtinsumo_cant.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtinsumo_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtinsumo_cantKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtinsumo_cantKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsumo_cantKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("CANTIDAD:");

        txtinsumo_pre_com.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtinsumo_pre_com.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtinsumo_pre_comKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtinsumo_pre_comKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsumo_pre_comKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("PRECIO COMPRA:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("PRECIO COMPRA:");

        jFsutotal.setEditable(false);
        jFsutotal.setBackground(new java.awt.Color(204, 204, 255));
        jFsutotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsutotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnnuevo_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_insumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_compraLayout = new javax.swing.GroupLayout(panel_insertar_pri_compra);
        panel_insertar_pri_compra.setLayout(panel_insertar_pri_compraLayout);
        panel_insertar_pri_compraLayout.setHorizontalGroup(
            panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
                    .addComponent(panel_insertar_seg_compra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtbuscar_insumo_producto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtinsumo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtinsumo_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtinsumo_pre_com, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jFsutotal, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panel_insertar_pri_compraLayout.setVerticalGroup(
            panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbuscar_insumo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFsutotal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtinsumo_cant, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtinsumo_pre_com, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(txtinsumo_unidad, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_insertar_seg_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS INSUMO", panel_insertar_pri_compra);

        jLabel12.setText("IDCOMPRA:");

        txtidventa.setEditable(false);
        txtidventa.setBackground(new java.awt.Color(0, 0, 255));
        txtidventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidventa.setForeground(new java.awt.Color(255, 255, 0));
        txtidventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("PROVEEDOR"));

        jLabel8.setText("PROVEEDOR:");

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("F1: BUSCAR");

        txtprovee_ruc.setEditable(false);

        jLabel9.setText("RUC:");

        btnbuscar_provee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_proveeActionPerformed(evt);
            }
        });

        btnnuevo_provee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_proveeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprovee_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprovee_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_provee, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnnuevo_provee, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnnuevo_provee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addComponent(btnbuscar_provee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtprovee_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(txtprovee_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_base_2Layout = new javax.swing.GroupLayout(panel_base_2);
        panel_base_2.setLayout(panel_base_2Layout);
        panel_base_2Layout.setHorizontalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_base_2Layout.setVerticalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("CREAR COMPRA INSUMO", panel_base_2);

        panel_tabla_compra.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla_compra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcompra_insumo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblcompra_insumo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcompra_insumo.setRowHeight(30);
        tblcompra_insumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcompra_insumoMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblcompra_insumo);

        javax.swing.GroupLayout panel_tabla_compraLayout = new javax.swing.GroupLayout(panel_tabla_compra);
        panel_tabla_compra.setLayout(panel_tabla_compraLayout);
        panel_tabla_compraLayout.setHorizontalGroup(
            panel_tabla_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panel_tabla_compraLayout.setVerticalGroup(
            panel_tabla_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
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

        btnconfirmado.setBackground(new java.awt.Color(102, 102, 255));
        btnconfirmado.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnconfirmado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_terminar.png"))); // NOI18N
        btnconfirmado.setText("CONFIRMAR");
        btnconfirmado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnconfirmado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnconfirmado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmadoActionPerformed(evt);
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

        panel_referencia_filtro.setBackground(new java.awt.Color(204, 204, 255));
        panel_referencia_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO COMPRA"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("IDCOMPRA:");

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

        jCestado_emitido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_confirmado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCestado_confirmado.setText("CONFIRMADO");
        jCestado_confirmado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_confirmadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_filtroLayout = new javax.swing.GroupLayout(panel_referencia_filtro);
        panel_referencia_filtro.setLayout(panel_referencia_filtroLayout);
        panel_referencia_filtroLayout.setHorizontalGroup(
            panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_filtroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCestado_anulado)
                    .addComponent(jCestado_confirmado)
                    .addGroup(panel_referencia_filtroLayout.createSequentialGroup()
                        .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(txtbuscar_fecha)))
                    .addComponent(jCestado_emitido))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_referencia_filtroLayout.setVerticalGroup(
            panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_filtroLayout.createSequentialGroup()
                .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_confirmado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_anulado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblitem_compra_insumo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_compra_insumo);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        btneditar_compra.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btneditar_compra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar2.png"))); // NOI18N
        btneditar_compra.setText("EDITAR");
        btneditar_compra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar_compra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditar_compraActionPerformed(evt);
            }
        });

        btnimprimirJSON.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnimprimirJSON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/enviar.png"))); // NOI18N
        btnimprimirJSON.setText("ENVIAR");
        btnimprimirJSON.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnimprimirJSON.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnimprimirJSON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirJSONActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_tabla_compra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_base_1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnanularventa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnconfirmado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btneditar_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnimprimirJSON, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(69, 69, 69)
                        .addComponent(panel_referencia_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_tabla_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnconfirmado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnimprimirNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnimprimirJSON, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnanularventa, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(btneditar_compra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addComponent(panel_referencia_filtro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane_VENTA.addTab("FILTRO COMPRA", panel_base_1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        evetbl.maximizar_jinternal(this);
        ancho_item_producto();
        cidao.ancho_tabla_compra(tblcompra_insumo);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtbuscar_insumo_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_insumo_productoKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbuscar_insumo_producto, jList_insumo_producto,
                insu_tabla,
                insu_buscar,
                insu_mostrar, 10);
    }//GEN-LAST:event_txtbuscar_insumo_productoKeyReleased

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:

        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void btnconfirmar_insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_insertarActionPerformed
        // TODO add your handling code here:
        boton_comfirmar_compra();
    }//GEN-LAST:event_btnconfirmar_insertarActionPerformed

    private void btnanularventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularventaActionPerformed
        // TODO add your handling code here:
        boton_anular_compra();
    }//GEN-LAST:event_btnanularventaActionPerformed

    private void btnconfirmadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmadoActionPerformed
        // TODO add your handling code here:
        boton_confirmar_compra();
    }//GEN-LAST:event_btnconfirmadoActionPerformed

    private void btnimprimirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirNotaActionPerformed
        // TODO add your handling code here:
        boton_imprimir_pos();
    }//GEN-LAST:event_btnimprimirNotaActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
//        cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro_estado());
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_confirmadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_confirmadoActionPerformed
        // TODO add your handling code here:
//        cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro_estado());
    }//GEN-LAST:event_jCestado_confirmadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
//        cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro_estado());
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void txtbuscar_idventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtbuscar_idventa.getText().trim().length() > 0) {
                String filtro = " and ci.idcompra_insumo=" + txtbuscar_idventa.getText() + " ";
//                cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro);
            }
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
            if (txtbuscar_fecha.getText().trim().length() > 0) {
                String filtro = " and date(ci.fecha_emision)='" + txtbuscar_fecha.getText() + "'";
//                cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro);
            }
        }
    }//GEN-LAST:event_txtbuscar_fechaKeyPressed

    private void tblcompra_insumoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcompra_insumoMouseReleased
        // TODO add your handling code here:
        seleccionar_compra_insumo();
    }//GEN-LAST:event_tblcompra_insumoMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boton_cancelar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtobservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtobservacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtobservacionActionPerformed

    private void txtbuscar_insumo_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_insumo_productoKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_insumo_producto);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_insumo_producto();
        }
    }//GEN-LAST:event_txtbuscar_insumo_productoKeyPressed

    private void jList_insumo_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_insumo_productoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_buscar_insumo();
        }
    }//GEN-LAST:event_jList_insumo_productoKeyPressed

    private void jList_insumo_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_insumo_productoMouseReleased
        // TODO add your handling code here:
        seleccionar_buscar_insumo();
    }//GEN-LAST:event_jList_insumo_productoMouseReleased

    private void txtinsumo_cantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_cantKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtinsumo_cantKeyTyped

    private void txtinsumo_pre_comKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_pre_comKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtinsumo_pre_comKeyTyped

    private void txtinsumo_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_cantKeyReleased
        // TODO add your handling code here:
        calcular_subtotal_item();
    }//GEN-LAST:event_txtinsumo_cantKeyReleased

    private void txtinsumo_pre_comKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_pre_comKeyReleased
        // TODO add your handling code here:
        calcular_subtotal_item();
    }//GEN-LAST:event_txtinsumo_pre_comKeyReleased

    private void txtinsumo_cantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_cantKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtinsumo_cant.setBackground(Color.WHITE);
            txtinsumo_pre_com.setBackground(Color.ORANGE);
            txtinsumo_pre_com.grabFocus();
        }
        cargar_itemventa_cantidad(evt);
    }//GEN-LAST:event_txtinsumo_cantKeyPressed

    private void txtinsumo_pre_comKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsumo_pre_comKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_insumo();
        }
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            hab_update_precio_insumo = true;
        }
    }//GEN-LAST:event_txtinsumo_pre_comKeyPressed

    private void btnnuevo_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_insumoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnnuevo_insumoActionPerformed

    private void btneditar_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar_compraActionPerformed
        // TODO add your handling code here:
        boton_editar_compra();
    }//GEN-LAST:event_btneditar_compraActionPerformed

    private void btnsumarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsumarActionPerformed
        // TODO add your handling code here:
        sumar_item_compra();
    }//GEN-LAST:event_btnsumarActionPerformed

    private void btnimprimirJSONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirJSONActionPerformed
        // TODO add your handling code here:
        boton_imprimir_json();
    }//GEN-LAST:event_btnimprimirJSONActionPerformed

    private void tblitem_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblitem_productoKeyPressed
        // TODO add your handling code here:
        sumar_item_compra();
    }//GEN-LAST:event_tblitem_productoKeyPressed

    private void btnbuscar_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_proveeActionPerformed
        // TODO add your handling code here:
        FrmJD_buscarproveedor frm = new FrmJD_buscarproveedor(null, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btnbuscar_proveeActionPerformed

    private void btnnuevo_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_proveeActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProveedor());
    }//GEN-LAST:event_btnnuevo_proveeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanularventa;
    private javax.swing.JButton btnbuscar_provee;
    private javax.swing.JButton btnconfirmado;
    private javax.swing.JButton btnconfirmar_insertar;
    private javax.swing.JButton btneditar_compra;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnimprimirJSON;
    private javax.swing.JButton btnimprimirNota;
    private javax.swing.JButton btnnuevo_insumo;
    private javax.swing.JButton btnnuevo_provee;
    private javax.swing.JButton btnsumar;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_confirmado;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JFormattedTextField jFsutotal;
    private javax.swing.JFormattedTextField jFtotal_guarani;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_insumo_producto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_VENTA;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_base_2;
    private javax.swing.JPanel panel_insertar_pri_compra;
    private javax.swing.JPanel panel_insertar_seg_compra;
    private javax.swing.JPanel panel_referencia_filtro;
    private javax.swing.JPanel panel_tabla_compra;
    private javax.swing.JTable tblcompra_insumo;
    private javax.swing.JTable tblitem_compra_insumo;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTextField txtbuscar_fecha;
    private javax.swing.JTextField txtbuscar_idventa;
    public static javax.swing.JTextField txtbuscar_insumo_producto;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtinsumo_cant;
    private javax.swing.JTextField txtinsumo_pre_com;
    private javax.swing.JTextField txtinsumo_unidad;
    private javax.swing.JTextField txtobservacion;
    public static javax.swing.JTextField txtprovee_nombre;
    public static javax.swing.JTextField txtprovee_ruc;
    // End of variables declaration//GEN-END:variables

}
