/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_item_venta;
import FORMULARIO.DAO.DAO_item_venta_mesa;
import FORMULARIO.DAO.DAO_item_venta_mesa_venta;
import FORMULARIO.DAO.DAO_itemven_insumo;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.DAO.DAO_venta_mesa;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.item_venta;
import FORMULARIO.ENTIDAD.item_venta_mesa;
import FORMULARIO.ENTIDAD.item_venta_mesa_venta;
import FORMULARIO.ENTIDAD.itemven_insumo;
import FORMULARIO.ENTIDAD.venta;
import FORMULARIO.ENTIDAD.venta_mesa;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_venta {

    private DAO_venta vdao = new DAO_venta();
    private DAO_item_venta ivdao = new DAO_item_venta();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    private DAO_item_venta_mesa ivm_dao = new DAO_item_venta_mesa();
    private DAO_item_venta_mesa_venta ivmv_dao = new DAO_item_venta_mesa_venta();
    private DAO_venta_mesa vmesa_dao = new DAO_venta_mesa();
    private DAO_itemven_insumo ividao = new DAO_itemven_insumo();

    public boolean getBoolean_insertar_venta(Connection conn, JTable tblitem_producto, item_venta item, venta ven, caja_detalle caja,
            boolean isCargar_venta_mesa, boolean esMesa_LIBRE, item_venta_mesa ivm, item_venta_mesa_venta ivmv, venta_mesa vmesa,
            itemven_insumo iteminsu, int fk_idventa, String idventa_mesa_select) {
        boolean insertado = false;
        String titulo = "getBoolean_insertar_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.insertar_venta(conn, ven);
            ivdao.insertar_item_venta_de_tabla(conn, tblitem_producto, item, ven);
            cdao.insertar_caja_detalle(conn, caja);
            if (isCargar_venta_mesa) {
                System.out.println("ES VENTA PARA LA MESA");
                if (esMesa_LIBRE) {
                    System.out.println("ESTA MESA ESTA LIBRE: " + vmesa.getC1idventa_mesa());
                    ivm_dao.insertar_item_venta_mesa(conn, ivm);
                    ivmv_dao.insertar_item_venta_mesa_venta(conn, ivmv);
                    vmesa_dao.update_venta_mesaEstado(conn, vmesa);
                } else {
                    System.out.println("ESTA MESA ESTA OCUPADA: " + vmesa.getC1idventa_mesa());
                    ivmv_dao.insertar_item_venta_mesa_venta(conn, ivmv);
                    double monto = vmesa_dao.getInt_sumar_venta_mesa(conn, idventa_mesa_select);
                    vmesa.setC4monto(monto);
                    vmesa_dao.update_venta_mesaEstado(conn, vmesa);
                    int fk_iditem_venta_mesa = vmesa_dao.cargar_idventa_mesa(conn, idventa_mesa_select);
                    ivm.setC1iditem_venta_mesa(fk_iditem_venta_mesa);
                    ivm.setC5totalmonto(monto);
                    ivm_dao.update_item_venta_mesa_monto(conn, ivm);
                }

            } else {
                System.out.println("ES VENTA NORMAL");
            }
            ividao.recorrer_item_venta(conn, iteminsu, fk_idventa);
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString()+"\n"+
                        caja.toString()+"\n"+
                        ivm.toString()+"\n"+
                        ivmv.toString()+"\n"+
                        vmesa.toString()+"\n", titulo);
            }
        }
        return insertado;
    }

    public void update_estado_venta(Connection conn, venta ven) {
        String titulo = "insertar_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_estado_venta(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }

    public void update_cambio_entregador(Connection conn, venta ven) {
        String titulo = "update_cambio_entregador";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_cambio_entregador(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }

    public void update_estado_pasar_cocina(Connection conn, venta ven) {
        String titulo = "insertar_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_estado_pasar_cocina(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }

    public void update_anular_venta(Connection conn, venta ven, caja_detalle caja) {
        String titulo = "update_anular_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_estado_venta(conn, ven);
            cdao.anular_caja_detalle(conn, caja);
            ivdao.recargar_stock_producto(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }
}
