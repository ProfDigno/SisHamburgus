/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MigracionBD;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
//import BASEDATO.SERVIDOR.ConnPostgres_SER;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_item_producto_ingrediente;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_producto_ingrediente;
import FORMULARIO.DAO.DAO_producto_unidad;
import FORMULARIO.DAO.DAO_zona_delivery;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class MigrarBDaServidor {
    EvenConexion eveconn=new EvenConexion();
    ConnPostgres conPs = new ConnPostgres();
//    ConnPostgres_SER conPsSER = new ConnPostgres_SER();
    Connection connLocal = conPs.getConnPosgres();
//    Connection connServi = conPsSER.getConnPosgres();
    DAO_cliente cldao = new DAO_cliente();
    DAO_gasto_tipo gtdao = new DAO_gasto_tipo();
    DAO_item_producto_ingrediente ipidao = new DAO_item_producto_ingrediente();
    DAO_producto pdao = new DAO_producto();
    DAO_producto_categoria pcdao = new DAO_producto_categoria();
    DAO_producto_unidad pudao = new DAO_producto_unidad();
    DAO_producto_ingrediente pidao = new DAO_producto_ingrediente();
    DAO_zona_delivery zdao=new DAO_zona_delivery();
    boolean insert_correcto;
    private int cant=0;

//    public MigrarBDaServidor() {
////     connLocal = conPs.getConnPosgres();
//     connServi = conPsSER.getConnPosgres();    
//    }
    
    void leer_1CLIENTE(Connection connLocal,Connection connServi) {
        String sql = "SELECT idcliente, fecha_inicio, nombre, direccion, telefono, ruc, fecha_cumple, \n"
                + "       tipo, fk_idzona_delivery\n"
                + "  FROM public.cliente order by 1 asc;";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int idcliente = rs.getInt("idcliente");
                Date dateInicio = rs.getDate("fecha_inicio");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String ruc = rs.getString("ruc");
                Date dateCumple = rs.getDate("fecha_cumple");
                String tipo = rs.getString("tipo");
                int idzona_delivery = rs.getInt("fk_idzona_delivery");
//                cldao.insertar_cliente(connServi, idcliente, dateInicio, nombre, direccion, telefono, ruc, dateCumple, tipo, idzona_delivery);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_2GASTO_TIPO(Connection connLocal,Connection connServi) {
        String titulo = "leer_tipo_gasto_bd1";
        String sql = "SELECT idgasto_tipo, nombre, activar  FROM public.gasto_tipo order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int idgasto_tipo = rs.getInt("idgasto_tipo");
                String nombre = rs.getString("nombre");
                boolean activar = rs.getBoolean("activar");
                gtdao.insertar_gasto_tipo(connServi, idgasto_tipo, nombre, activar);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_3ITEM_PRODUCTO_INGREDIENTE(Connection connLocal,Connection connServi) {
        String titulo = "leer_ITEMINGREDIENTE_bd1";
        String sql = "SELECT iditem_producto_ingrediente, fk_idproducto, fk_idproducto_ingrediente\n"
                + "  FROM public.item_producto_ingrediente order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int iditem_producto_ingrediente = rs.getInt("iditem_producto_ingrediente");
                int fk_idproducto = rs.getInt("fk_idproducto");
                int fk_idproducto_ingrediente = rs.getInt("fk_idproducto_ingrediente");
                ipidao.insertar_item_producto_ingrediente(connServi, iditem_producto_ingrediente, fk_idproducto, fk_idproducto_ingrediente);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_4PRODUCTO(Connection connLocal,Connection connServi) {
        String titulo = "leer_PRODUCTO_bd1";
        String sql = "SELECT idproducto, nombre, precio_venta, precio_compra, stock, orden, \n"
                + "       activar, cocina, descontar_stock, comprar, vender, fk_idproducto_categoria, \n"
                + "       fk_idproducto_unidad\n"
                + "  FROM public.producto order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int P1idproducto = rs.getInt("idproducto");
                String P2nombre = rs.getString("nombre");
                double P3precio_venta = rs.getDouble("precio_venta");
                double P4precio_compra = rs.getDouble("precio_compra");
                double P5stock = rs.getDouble("stock");
                int P6orden = rs.getInt("orden");
                boolean P7activar = rs.getBoolean("activar");
                boolean P8cocina = rs.getBoolean("cocina");
                boolean P9descontar_stock = rs.getBoolean("descontar_stock");
                boolean P10compra = rs.getBoolean("comprar");
                boolean P11vender = rs.getBoolean("vender");
                int P12fk_idcategoria = rs.getInt("fk_idproducto_categoria");
                int P13fk_idunidad = rs.getInt("fk_idproducto_unidad");
                pdao.insertar_producto(connServi, P1idproducto, P2nombre, P3precio_venta, P4precio_compra, P5stock, P6orden,
                        P7activar, P8cocina, P9descontar_stock, P10compra, P11vender, P12fk_idcategoria, P13fk_idunidad);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_5PRODUCTO_CATEGORIA(Connection connLocal,Connection connServi) {
        String titulo = "leer_CATEGORIA_bd1";
        String sql = "SELECT idproducto_categoria, nombre, activar, orden\n"
                + "  FROM public.producto_categoria order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int idproducto_categoria = rs.getInt("idproducto_categoria");
                String nombre = rs.getString("nombre");
                boolean activar = rs.getBoolean("activar");
                int orden = rs.getInt("orden");
                pcdao.insertar_producto_categoria(connServi, idproducto_categoria, nombre, activar, orden);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_6PRODUCTO_UNIDAD(Connection connLocal,Connection connServi) {
        String titulo = "leer_UNIDAD_bd1";
        String sql = "SELECT idproducto_unidad, nombre\n"
                + "  FROM public.producto_unidad order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int idunidad = rs.getInt("idproducto_unidad");
                String nombre = rs.getString("nombre");
                pudao.insertar_producto_unidad(connServi, idunidad, nombre);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_7PRODUCTO_INGREDIENTE(Connection connLocal,Connection connServi) {
        String sql = "SELECT idproducto_ingrediente, nombre, precio_venta\n"
                + "  FROM public.producto_ingrediente order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int idproducto_ingrediente = rs.getInt("idproducto_ingrediente");
                String nombre = rs.getString("nombre");
                double precio_venta = rs.getDouble("precio_venta");
                pidao.insertar_producto_ingrediente(connServi, idproducto_ingrediente, nombre, precio_venta);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }

    void leer_8ZONA_DELIVERY(Connection connLocal,Connection connServi) {
        String titulo = "leer_ZONA_bd1";
        String sql = "SELECT idzona_delivery, nombre, delivery\n"
                + "  FROM public.zona_delivery order by 1 asc";
        try {
            PreparedStatement pst = connLocal.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            int cant = 0;
            while (rs.next()) {
                cant++;
                int idzona_delivery = rs.getInt("idzona_delivery");
                String nombre = rs.getString("nombre");
                double delivery = rs.getDouble("delivery");
                zdao.insertar_zona_delivery(connServi, idzona_delivery, nombre, delivery);
            }
        } catch (Exception e) {
            insert_correcto=false;
            System.out.println("error:" + e);
        }
    }
    void limpiar_bd_nuevo(Connection connServi) {
        String sql = "TRUNCATE TABLE cliente ;\n"
                + "alter sequence cliente_idcliente_seq restart with 1 ; \n"
                + "TRUNCATE TABLE zona_delivery ;\n"
                + "alter sequence zona_delivery_idzona_delivery_seq restart with 1 ; \n"
                + "TRUNCATE TABLE item_producto_ingrediente ;\n"
                + "alter sequence item_producto_ingrediente_iditem_producto_ingrediente_seq restart with 1 ; \n"
                + "TRUNCATE TABLE producto ;\n"
                + "alter sequence producto_idproducto_seq restart with 1 ; \n"
                + "TRUNCATE TABLE producto_categoria ;\n"
                + "alter sequence producto_categoria_idproducto_categoria_seq restart with 1 ; \n"
                + "TRUNCATE TABLE producto_ingrediente ;\n"
                + "alter sequence producto_ingrediente_idproducto_ingrediente_seq restart with 1 ; \n"
                + "TRUNCATE TABLE producto_unidad ;\n"
                + "alter sequence producto_unidad_idproducto_unidad_seq restart with 1 ; \n"
                + "TRUNCATE TABLE gasto ;\n"
                + "alter sequence gasto_idgasto_seq restart with 1 ;\n"
                + "TRUNCATE TABLE gasto_tipo ;\n"
                + "alter sequence gasto_tipo_idgasto_tipo_seq restart with 1 ;"
                + "TRUNCATE TABLE vale ;\n"
                + "alter sequence vale_idvale_seq restart with 1 ;\n"
                + "TRUNCATE TABLE item_venta ;\n"
                + "alter sequence item_venta_iditem_venta_seq restart with 1 ; \n"
                + "TRUNCATE TABLE caja_detalle ;\n"
                + "alter sequence caja_detalle_idcaja_detalle_seq restart with 1 ; \n"
                + "TRUNCATE TABLE venta ;\n"
                + "alter sequence venta_idventa_seq restart with 1 ; ";
        eveconn.SQL_execute_libre(connServi, sql);  
    }

    public void copiar_de_local_a_servidor(Connection connLocal,Connection connServi,String mensaje){
//        if(conPsSER.isServidor_conectado()){
//            JOptionPane.showMessageDialog(null,mensaje);
//            insert_correcto=true;
//            limpiar_bd_nuevo(connServi);
//            leer_1CLIENTE(connLocal,connServi);
//            leer_2GASTO_TIPO(connLocal,connServi);
//            leer_3ITEM_PRODUCTO_INGREDIENTE(connLocal,connServi);
//            leer_4PRODUCTO(connLocal,connServi);
//            leer_5PRODUCTO_CATEGORIA(connLocal,connServi);
//            leer_6PRODUCTO_UNIDAD(connLocal,connServi);
//            leer_7PRODUCTO_INGREDIENTE(connLocal,connServi);
//            leer_8ZONA_DELIVERY(connLocal,connServi);
//            if(insert_correcto){
//                JOptionPane.showMessageDialog(null,"TODOS LOS DATOS INSERTADO CORRECTAMENTE CANTIDAD DE REGISTRO:"+cant);
//            }else{
//                JOptionPane.showMessageDialog(null,"OCURRIO UN ERROR");
//            }
//        }
    } 
}
