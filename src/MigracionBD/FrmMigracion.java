/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MigracionBD;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
//import BASEDATO.LOCAL.ConnSQLITEdato;
//import BASEDATO.SERVIDOR.ConnPostgres_SER;
//import BASEDATO.SERVIDOR.ConnSQLITEdato_SER;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmMigracion extends javax.swing.JFrame {

    /**
     * Creates new form FrmMigracion
     */
    Connection connviejo = null;
    Connection connNuevo = null;
    ConnPostgres2_bd c2bd = new ConnPostgres2_bd();
    EvenConexion eveconn = new EvenConexion();

    void abrir_formulario() {
        setDefaultCloseOperation(1);
    }

    void conectar_bd_viejo() {
//        c2bd.Connect1_DBpostgres();
//        connviejo = c2bd.getConn1Postgres();
//        ConnSQLITEdato_SER conLiSER = new ConnSQLITEdato_SER();
//        conLiSER.ConnectDBsqlite();
////        ConnPostgres_SER conPss=new ConnPostgres_SER();
////        conPss.ConnectDBpostgres_SER(true);
//        connviejo = conPss.getConnPosgres();
//        lblconn_viejo.setText(conPss.getDatos_conexion());
    }

    void conectar_bd_nuevo() {
        ConnPostgres conPs = new ConnPostgres();
        connNuevo = conPs.getConnPosgres();
        lblconn_nuevo.setText(conPs.getDatos_conexion());
    }

    void leer_ingrediente_bd1() {
        //JOptionPane.showMessageDialog(null, "LEER INGREDIENTE E INSERTAR");
        String titulo="leer_ingrediente_bd1";
        String sql = "select * from producto where fk_idcategoria=11 order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println("ingrediente:" + cant);
                }
                int idproducto_ingrediente = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                double precio_venta = rs.getDouble("preciov1");
//                System.out.println("id:" + idproducto_ingrediente + " nombre:" + nombre + " precio:" + precio_venta);
                insertar_producto_ingrediente(connNuevo, idproducto_ingrediente, nombre, precio_venta);
            }
//            JOptionPane.showMessageDialog(null, "CANTIDAD INGREDIENTE: " + cant);
            System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_CATEGORIA_bd1() {
        String titulo = "leer_CATEGORIA_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select * from categoria order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idproducto_categoria = rs.getInt("idcategoria");
                String nombre = rs.getString("nombre");
                boolean activar = rs.getBoolean("boton");
                int orden = rs.getInt("orden");

                insertar_producto_categoria(connNuevo, idproducto_categoria, nombre, activar, orden);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_UNIDAD_bd1() {
        String titulo = "leer_UNIDAD_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select * from unidad order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idunidad = rs.getInt("idunidad");
                String nombre = rs.getString("nombre");
                insertar_producto_unidad(connNuevo, idunidad, nombre);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_PRODUCTO_bd1() {
        //
        String titulo = "leer_PRODUCTO_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select idproducto,nombre,preciocompra,preciov1,fk_idcategoria,fk_idunidad,cocina,activar,orden \n"
                + "from producto where fk_idcategoria!=11\n"
                + "order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int P1idproducto = rs.getInt("idproducto");
                String P2nombre = rs.getString("nombre");
                double P3precio_venta = rs.getDouble("preciov1");
                double P4precio_compra = rs.getDouble("preciocompra");
                double P5stock = 100;
                int P6orden = rs.getInt("orden");
                boolean P7activar = rs.getBoolean("activar");
                boolean P8cocina = rs.getBoolean("cocina");
                int P12fk_idcategoria = rs.getInt("fk_idcategoria");
                int P13fk_idunidad = rs.getInt("fk_idunidad");
                insertar_producto(connNuevo, P1idproducto, P2nombre, P3precio_venta, P4precio_compra, P5stock, P6orden, P7activar, P8cocina, P12fk_idcategoria, P13fk_idunidad);
            }
             System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
            //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }
    void leer_PRODUCTO_INGREDIENTE_bd1() {
        
        
        String titulo = "leer_PRODUCTO_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select idproducto,nombre,preciocompra,preciov1,fk_idcategoria,fk_idunidad,cocina,activar,orden \n"
                + "from producto where fk_idcategoria=11\n"
                + "order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int P1idproducto = rs.getInt("idproducto");
                String P2nombre = rs.getString("nombre");
                double P3precio_venta = rs.getDouble("preciov1");
                double P4precio_compra = rs.getDouble("preciocompra");
                double P5stock = 100;
                int P6orden = rs.getInt("orden");
                boolean P7activar = rs.getBoolean("activar");
                boolean P8cocina = rs.getBoolean("cocina");
                int P12fk_idcategoria = rs.getInt("fk_idcategoria");
                int P13fk_idunidad = rs.getInt("fk_idunidad");
                /**
         * String sql_insert = "INSERT INTO public.producto(\n"
                + "            idproducto, nombre, precio_venta, precio_compra, stock, orden, \n"
                + "            activar, cocina, descontar_stock, comprar, vender, fk_idproducto_categoria, \n"
                + "            fk_idproducto_unidad)\n"
         */
                System.out.println("("+P1idproducto+",'"+P2nombre+"',"+P3precio_venta+","+P4precio_compra+","+P5stock+
                        ","+P6orden+","+P7activar+","+P8cocina+",false,false,true,"+P12fk_idcategoria+","+P13fk_idunidad+"),");
//                insertar_producto(connNuevo, P1idproducto, P2nombre, P3precio_venta, P4precio_compra, P5stock, P6orden, P7activar, P8cocina, P12fk_idcategoria, P13fk_idunidad);
            }
             System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
            //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }
    void leer_ITEMINGREDIENTE_bd1() {
        String titulo = "leer_ITEMINGREDIENTE_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select iditemproductoingrediente,fk_idproducto,fk_idproducto2 "
                + "from itemproductoingrediente order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int iditem_producto_ingrediente = rs.getInt("iditemproductoingrediente");
                int fk_idproducto = rs.getInt("fk_idproducto");
                int fk_idproducto_ingrediente = rs.getInt("fk_idproducto2");
                insertar_item_producto_ingrediente(connNuevo, iditem_producto_ingrediente, fk_idproducto, fk_idproducto_ingrediente);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_ZONA_bd1() {
        String titulo = "leer_ZONA_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select idciudad,nombre,delivery from ciudad order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idciudad = rs.getInt("idciudad");
                String nombre = rs.getString("nombre");
                double delivery = rs.getDouble("delivery");
                insertar_zona_delivery(connNuevo, idciudad, nombre, delivery);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_CLIENTE_bd1() {
        String titulo = "leer_CLIENTE_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select idcliente,nombre,direccion,telefono,ruc, \n"
                + "fecha_inicio,fecha_nac,('cliente') as tipo,fk_idciudad\n"
                + "from cliente order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idcliente = rs.getInt("idcliente");
                Date dateInicio = rs.getDate("fecha_inicio");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String ruc = rs.getString("ruc");
                Date dateCumple = rs.getDate("fecha_nac");
                String tipo = rs.getString("tipo");
                int idzona_delivery = rs.getInt("fk_idciudad");
                insertar_cliente(connNuevo, idcliente, dateInicio, nombre, direccion, telefono, ruc, dateCumple, tipo, idzona_delivery);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_VENTACOMANDA_bd1() {
        String sqlp = "update pedido set fecha_terminado=fecha where fecha_terminado is null;";
        eveconn.SQL_execute_libre(connviejo, sqlp);
        String titulo = "leer_VENTACOMANDA_bd1=";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select v.idventacomanda as idventacomanda,\n"
                + "v.fecha_emision as fecha_inicio,p.fecha_terminado as fecha_fin,\n"
                + "case \n"
                + "when v.tipo='BANCADA' then '##>>LOCAL<<##'\n"
                + "when v.tipo='DELIVERY' then '##>>DELIVERY<<##' \n"
                + "else 'nulo' \n"
                + "end as tipo_entrega,\n"
                + "case \n"
                + "when (c.estado='TERMINADO' and v.estado='PAGADO') then 'TERMINADO'\n"
                + "when c.estado='cocina' then 'EMITIDO' \n"
                + "when (c.estado='TERMINADO' and v.estado='ANULADO') then 'ANULADO' \n"
                + "else 'nulo' \n"
                + "end as estado,\n"
                + "v.totalmonto as monto_venta,(0) as monto_delivery,\n"
                + "(false) as delivery,v.observacion,\n"
                + "c.descripcion as comanda,('mi-pc') as equipo,(v.idventacomanda||'-VVVVV') as indice,v.fk_idcliente,\n"
                + "(1) as idusuario,(1) as fk_identregador,\n"
                + "('(VENTA) ID:'||v.idventacomanda||' Cliente:'||cl.nombre) as descripcion \n"
                + "from ventacomanda v,comanda c,pedido p,usuario u,cliente cl \n"
                + "where v.fk_idpedido=c.fk_idpedido \n"
                + "and v.fk_idusuario=u.idusuario \n"
                + "and v.fk_idcliente=cl.idcliente \n"
                + "and v.fk_idpedido=p.idpedido and v.idventacomanda > 9 \n"
                + "order by v.idventacomanda asc  ";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 5000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idventa = rs.getInt("idventacomanda");
                Timestamp fecha_inicio = rs.getTimestamp("fecha_inicio");
                Timestamp fecha_fin = rs.getTimestamp("fecha_fin");
                String tipo_entrega = rs.getString("tipo_entrega");
                String estado = rs.getString("estado");
                double monto_venta = rs.getDouble("monto_venta");
                double monto_delivery = rs.getDouble("monto_delivery");
                boolean delivery = rs.getBoolean("delivery");
                String observacion = rs.getString("observacion");
                String comanda = rs.getString("comanda");
                String equipo = rs.getString("equipo");
                String indice = rs.getString("indice");
                int fk_idcliente = rs.getInt("fk_idcliente");
                int idusuario = rs.getInt("idusuario");
                int fk_identregador = rs.getInt("fk_identregador");
                insertar_venta(connNuevo, idventa, fecha_inicio, fecha_fin, tipo_entrega, estado, monto_venta, monto_delivery, delivery, observacion, comanda, equipo, indice, fk_idcliente, idusuario, fk_identregador);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (SQLException e) {
            System.out.println(titulo+"-error:" + e);
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    void leer_CAJADETALLE_VENTA_bd2() {
        String titulo = "leer_CAJADETALLE_VENTA_bd2=";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select v.idventa,v.fecha_inicio,v.monto_venta,\n"
                + "v.monto_delivery,v.equipo,v.indice,(1) as idusuario,\n"
                + "('(VENTA) ID:'||v.idventa||' Cliente:'||c.nombre) as descripcion \n"
                + " from venta v,cliente c \n"
                + "where v.fk_idcliente=c.idcliente and v.estado!='ANULADO'\n"
                + " order by 1 asc ";
        try {
            PreparedStatement pst = connNuevo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                
                int idventa = rs.getInt("idventa");
                Timestamp fecha_inicio = rs.getTimestamp("fecha_inicio");
                double monto_venta = rs.getDouble("monto_venta");
                double monto_delivery = rs.getDouble("monto_delivery");
                String equipo = rs.getString("equipo");
                String indice = rs.getString("indice");
                int idusuario = rs.getInt("idusuario");
                String descripcion = rs.getString("descripcion");
                double monto_gasto = 0;
                double monto_compra = 0;
                double monto_vale = 0;
                int id_origen = idventa;
                String tabla_origen = "VENTA";
                int fk_idusuario = idusuario;
                insertar_caja_detalle(connNuevo, fecha_inicio, descripcion, monto_venta, monto_delivery, monto_gasto, monto_compra, monto_vale, id_origen, tabla_origen, fk_idusuario, indice, equipo);
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (SQLException e) {
            System.out.println(titulo+"-error:" + e);
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    void leer_CAJADETALLE_GASTO_bd2() {
        String titulo = "leer_CAJADETALLE_GASTO_bd2=";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select g.idgasto,g.fecha_emision,g.monto_gasto,\n"
                + "                g.equipo,g.indice,(1) as idusuario,\n"
                + "                ('(GASTO) ID:'||g.idgasto||' Tipo:'||t.nombre) as descripcion \n"
                + "                from gasto g,gasto_tipo t \n"
                + "                where g.fk_idgasto_tipo=t.idgasto_tipo and g.estado!='ANULADO'\n"
                + "                order by 1 asc ";
        try {
            PreparedStatement pst = connNuevo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idgasto = rs.getInt("idgasto");
                Timestamp fecha_inicio = rs.getTimestamp("fecha_emision");
                double monto_venta = 0;
                double monto_delivery = 0;
                String equipo = rs.getString("equipo");
                String indice = rs.getString("indice");
                int idusuario = rs.getInt("idusuario");
                String descripcion = rs.getString("descripcion");
                double monto_gasto = rs.getDouble("monto_gasto");
                double monto_compra = 0;
                double monto_vale = 0;
                int id_origen = idgasto;
                String tabla_origen = "GASTO";
                int fk_idusuario = idusuario;
                insertar_caja_detalle(connNuevo, fecha_inicio, descripcion, monto_venta, monto_delivery, monto_gasto, monto_compra, monto_vale, id_origen, tabla_origen, fk_idusuario, indice, equipo);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (SQLException e) {
            System.out.println(titulo+"-error:" + e);
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    void leer_CARGAR_DELIVERY_bd1() {
        String titulo = "leer_CARGAR_DELIVERY_bd1=";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select ing_delivery,fk_id_tabla \n"
                + "from caja_detalle\n"
                + "where origen_tabla='DELIVERY' "
                + "order by 2 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 5000) == 0) {
                    System.out.println(titulo + cant);
                }
                int fk_id_tabla = rs.getInt("fk_id_tabla");
                double ing_delivery = rs.getDouble("ing_delivery");
                update_delivery_venta(fk_id_tabla, ing_delivery);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_ITEMVENTA_DELIVERY_bd1() {
        String titulo = "leer_ITEMVENTA_DELIVERY_bd1= ";
      //  JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select iditemventacomanda, nombre as descripcion, \n"
                + "precio as precio_venta,preciocompra as precio_compra,\n"
                + "cantidad,('D') as tipo,fk_idventacomanda as fk_idventa,fk_idproducto\n"
                + "from itemventacomanda where fk_idproducto=54 order by fk_idventa asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int iditemventacomanda = rs.getInt("iditemventacomanda");
                String descripcion = rs.getString("descripcion");
                double precio_venta = rs.getDouble("precio_venta");
                double precio_compra = rs.getDouble("precio_compra");
                double cantidad = rs.getDouble("cantidad");
                String tipo = rs.getString("tipo");
                int fk_idventa = rs.getInt("fk_idventa");
                int fk_idproducto = rs.getInt("fk_idproducto");
                insertar_item_venta(connNuevo, iditemventacomanda, descripcion, precio_venta, precio_compra, cantidad, tipo, fk_idventa, fk_idproducto);
            }
        //    JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
        System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_ITEMVENTA_PRODUCTO_bd1() {
        String titulo = "leer_ITEMVENTA_PRODUCTO_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select iditemventacomanda, nombre as descripcion, \n"
                + "precio as precio_venta,preciocompra as precio_compra,\n"
                + "cantidad,('P') as tipo,fk_idventacomanda as fk_idventa,fk_idproducto\n"
                + "from itemventacomanda where fk_idproducto!=54 order by fk_idventa asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int iditemventacomanda = rs.getInt("iditemventacomanda");
                String descripcion = rs.getString("descripcion");
                double precio_venta = rs.getDouble("precio_venta");
                double precio_compra = rs.getDouble("precio_compra");
                double cantidad = rs.getDouble("cantidad");
                String tipo = rs.getString("tipo");
                int fk_idventa = rs.getInt("fk_idventa");
                int fk_idproducto = rs.getInt("fk_idproducto");
                insertar_item_venta(connNuevo, iditemventacomanda, descripcion, precio_venta, precio_compra, cantidad, tipo, fk_idventa, fk_idproducto);
            }
          //  JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
          System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_caja_detalle(Connection connNuevo, Timestamp fecha_inicio, String descripcion, double monto_venta, double monto_delivery,
            double monto_gasto, double monto_compra, double monto_vale, int id_origen, String tabla_origen,
            int fk_idusuario, String indice, String equipo) {
        int idcaja_detalle = (eveconn.getInt_ultimoID_mas_uno(connNuevo, "caja_detalle", "idcaja_detalle"));
        String titulo = "insertar_caja_detalle= ";
        String sql_insert = "INSERT INTO public.caja_detalle(\n"
                + "            idcaja_detalle, fecha_emision, descripcion, monto_venta, monto_delivery, \n"
                + "            monto_gasto, monto_compra, monto_vale, id_origen, tabla_origen, \n"
                + "            fk_idusuario,indice,equipo)\n"
                + "    VALUES (?, ?, ?, ?, ?, \n"
                + "            ?, ?, ?, ?, ?,?,?,?);";
        PreparedStatement pst = null;
        try {
            pst = connNuevo.prepareStatement(sql_insert);
            pst.setInt(1, idcaja_detalle);
            pst.setTimestamp(2, fecha_inicio);
            pst.setString(3, descripcion);
            pst.setDouble(4, monto_venta);
            pst.setDouble(5, monto_delivery);
            pst.setDouble(6, monto_gasto);
            pst.setDouble(7, monto_compra);
            pst.setDouble(8, monto_vale);
            pst.setInt(9, id_origen);
            pst.setString(10, tabla_origen);
            pst.setInt(11, fk_idusuario);
            pst.setString(12, indice);
            pst.setString(13, equipo);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_item_venta(Connection conn, int iditem_venta, String descripcion, double precio_venta, double precio_compra,
            double cantidad, String tipo, int fk_idventa, int fk_idproducto) {
        String titulo = "insertar_item_venta= ";
        String sql_insert = "INSERT INTO public.item_venta(\n"
                + "            iditem_venta, descripcion, precio_venta, precio_compra, cantidad, \n"
                + "            tipo, fk_idventa, fk_idproducto)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, iditem_venta);
            pst.setString(2, descripcion);
            pst.setDouble(3, precio_venta);
            pst.setDouble(4, precio_compra);
            pst.setDouble(5, cantidad);
            pst.setString(6, tipo);
            pst.setInt(7, fk_idventa);
            pst.setInt(8, fk_idproducto);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void update_delivery_venta(int fk_id_tabla, double ing_delivery) {
        String sql = "update venta set monto_delivery=" + ing_delivery + ",delivery=true "
                + "where idventa=" + fk_id_tabla + " ";
        eveconn.SQL_execute_libre(connNuevo, sql);
    }

    public void insertar_venta(Connection conn, int idventa, Timestamp fecha_inicio, Timestamp fecha_fin, String tipo_entrega,
            String estado, double monto_venta, double monto_delivery, boolean delivery, String observacion,
            String comanda, String equipo, String indice, int fk_idcliente, int idusuario, int fk_identregador) {
        String titulo = "insertar_venta= ";
        String sql_insert = "INSERT INTO public.venta(\n"
                + "            idventa, fecha_inicio, fecha_fin, tipo_entrega, estado, monto_venta, \n"
                + "            monto_delivery, delivery, observacion, comanda, equipo, fk_idcliente, \n"
                + "            fk_idusuario, fk_identregador,indice)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, \n"
                + "            ?, ?, ?, ?, ?, ?, \n"
                + "            ?, ?,?);";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, idventa);
            pst.setTimestamp(2, fecha_inicio);
            pst.setTimestamp(3, fecha_fin);
            pst.setString(4, tipo_entrega);
            pst.setString(5, estado);
            pst.setDouble(6, monto_venta);
            pst.setDouble(7, monto_delivery);
            pst.setBoolean(8, delivery);
            pst.setString(9, observacion);
            pst.setString(10, comanda);
            pst.setString(11, equipo);
            pst.setInt(12, fk_idcliente);
            pst.setInt(13, idusuario);
            pst.setInt(14, fk_identregador);
            pst.setString(15, indice);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void insertar_producto_ingrediente(Connection conn, int idproducto_ingrediente, String nombre, double precio_venta) {
        String titulo = "insertar_producto_ingrediente= ";
        String sql_insert = "INSERT INTO public.producto_ingrediente(\n"
                + "            idproducto_ingrediente, nombre, precio_venta)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, idproducto_ingrediente);
            pst.setString(2, nombre);
            pst.setDouble(3, precio_venta);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void insertar_producto_categoria(Connection conn2, int idproducto_categoria, String nombre, boolean activar, int orden) {
        String titulo="insertar_producto_categoria";
        String sql_insert = "INSERT INTO public.producto_categoria(\n"
                + "            idproducto_categoria, nombre, activar,orden)\n"
                + "    VALUES (?, ?, ?,?);";
        PreparedStatement pst = null;
        try {
            pst = conn2.prepareStatement(sql_insert);
            pst.setInt(1, idproducto_categoria);
            pst.setString(2, nombre);
            pst.setBoolean(3, activar);
            pst.setInt(4, orden);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_producto_unidad(Connection conn2, int idunidad, String nombre) {
        String titulo = "insertar_producto_unidad= ";
        String sql_insert = "INSERT INTO public.producto_unidad(\n"
                + "            idproducto_unidad, nombre)\n"
                + "    VALUES (?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn2.prepareStatement(sql_insert);
            pst.setInt(1, idunidad);
            pst.setString(2, nombre);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void insertar_producto(Connection conn, int P1idproducto, String P2nombre, double P3precio_venta, double P4precio_compra, double P5stock, int P6orden, boolean P7activar, boolean P8cocina, int P12fk_idcategoria, int P13fk_idunidad) {
        String titulo = "insertar_producto= ";
        String sql_insert = "INSERT INTO public.producto(\n"
                + "            idproducto, nombre, precio_venta, precio_compra, stock, orden, \n"
                + "            activar, cocina, descontar_stock, comprar, vender, fk_idproducto_categoria, \n"
                + "            fk_idproducto_unidad)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, \n"
                + "            ?, ?, ?, ?, ?, ?, \n"
                + "            ?);";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, P1idproducto);
            pst.setString(2, P2nombre);
            pst.setDouble(3, P3precio_venta);
            pst.setDouble(4, P4precio_compra);
            pst.setDouble(5, P5stock);
            pst.setInt(6, P6orden);
            pst.setBoolean(7, P7activar);
            pst.setBoolean(8, P8cocina);
            pst.setBoolean(9, false);
            pst.setBoolean(10, false);
            pst.setBoolean(11, true);
            pst.setInt(12, P12fk_idcategoria);
            pst.setInt(13, P13fk_idunidad);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_item_producto_ingrediente(Connection conn2, int iditem_producto_ingrediente, int fk_idproducto, int fk_idproducto_ingrediente) {
        String titulo = "insertar_producto_ingrediente= ";
        String sql_insert = "INSERT INTO public.item_producto_ingrediente(\n"
                + "            iditem_producto_ingrediente, fk_idproducto, fk_idproducto_ingrediente)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn2.prepareStatement(sql_insert);
            pst.setInt(1, iditem_producto_ingrediente);
            pst.setInt(2, fk_idproducto);
            pst.setInt(3, fk_idproducto_ingrediente);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_zona_delivery(Connection conn2, int Idzona_delivery, String Nombre, double Delivery) {
        String titulo = "insertar_zona_delivery= ";
        String sql_insert = "INSERT INTO public.zona_delivery(\n"
                + "            idzona_delivery, nombre, delivery)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn2.prepareStatement(sql_insert);
            pst.setInt(1, Idzona_delivery);
            pst.setString(2, Nombre);
            pst.setDouble(3, Delivery);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void insertar_cliente(Connection conn, int idcliente, Date dateInicio, String nombre, String direccion, String telefono, String ruc, Date dateCumple, String tipo, int idzona_delivery) {
        String titulo = "insertar_cliente= ";
        String sql_insert = "INSERT INTO public.cliente(\n"
                + "            idcliente, fecha_inicio, nombre, direccion, telefono, ruc, fecha_cumple, \n"
                + "            tipo, fk_idzona_delivery)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, idcliente);
            pst.setDate(2, dateInicio);
            pst.setString(3, nombre);
            pst.setString(4, direccion);
            pst.setString(5, telefono);
            pst.setString(6, ruc);
            pst.setDate(7, dateCumple);
            pst.setString(8, tipo);
            pst.setInt(9, idzona_delivery);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_tipo_gasto_bd1() {
        String titulo = "leer_tipo_gasto_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select idtipogasto,descripcion from tipogasto order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idgasto_tipo = rs.getInt("idtipogasto");
                String nombre = rs.getString("descripcion");
                insertar_tipo_gasto(connNuevo, idgasto_tipo, nombre);
            }
            //JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
            System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_tipo_gasto(Connection conn2, int idgasto_tipo, String nombre) {
        String titulo = "insertar_producto_unidad";
        String sql_insert = "INSERT INTO public.gasto_tipo(\n"
                + "            idgasto_tipo, nombre, activar)\n"
                + "    VALUES (?, ?, ?);";
        PreparedStatement pst = null;
        try {
            pst = conn2.prepareStatement(sql_insert);
            pst.setInt(1, idgasto_tipo);
            pst.setString(2, nombre);
            pst.setBoolean(3, true);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void leer_GASTO_bd1() {
        String titulo = "leer_GASTO_bd1= ";
        //JOptionPane.showMessageDialog(null, "LEER " + titulo + " E INSERTAR");
        String sql = "select idgastos as idgasto,\n"
                + "fecha_emision,\n"
                + "descripcion,\n"
                + "monto as monto_gasto,\n"
                + "('mi-pc') as equipo,\n"
                + "estado,\n"
                + "(idgastos||'-CCCCC') as indice,\n"
                + "fk_idtipogasto as fk_idgasto_tipo,\n"
                + "(1) as fk_idusuario\n"
                + "from gastos order by 1 asc";
        try {
            PreparedStatement pst = connviejo.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int cant = 0;
            while (rs.next()) {
                cant++;
                if ((cant % 1000) == 0) {
                    System.out.println(titulo + cant);
                }
                int idgasto = rs.getInt("idgasto");
                Timestamp fecha_emision = rs.getTimestamp("fecha_emision");
                String descripcion = rs.getString("descripcion");
                double monto_gasto = rs.getDouble("monto_gasto");
                String equipo = rs.getString("equipo");
                String estado = rs.getString("estado");
                String indice = rs.getString("indice");
                int fk_idgasto_tipo = rs.getInt("fk_idgasto_tipo");
                int fk_idusuario = rs.getInt("fk_idusuario");
                insertar_gasto(connNuevo, idgasto, fecha_emision, descripcion, monto_gasto, equipo, estado, fk_idgasto_tipo, fk_idusuario, indice);
            }
            //JOptionPane.showMessageDialog(null, "CANTIDAD " + titulo + ": " + cant);
            System.out.println("SQL:"+sql+"\nCANTIDAD INSERTADO: "+cant);
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    public void insertar_gasto(Connection connNuevo, int idgasto, Timestamp fecha_emision, String descripcion, double monto_gasto,
            String equipo, String estado, int fk_idgasto_tipo, int fk_idusuario, String indice) {
        String titulo = "insertar_gasto";
        String sql_insert = "INSERT INTO public.gasto(\n"
                + "            idgasto, fecha_emision, descripcion, monto_gasto, equipo,estado, fk_idgasto_tipo, \n"
                + "            fk_idusuario,indice)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?,?,?);";
        PreparedStatement pst = null;
        try {
            pst = connNuevo.prepareStatement(sql_insert);
            pst.setInt(1, idgasto);
            pst.setTimestamp(2, fecha_emision);
            pst.setString(3, descripcion);
            pst.setDouble(4, monto_gasto);
            pst.setString(5, equipo);
            pst.setString(6, estado);
            pst.setInt(7, fk_idgasto_tipo);
            pst.setInt(8, fk_idusuario);
            pst.setString(9, indice);
            pst.execute();
            pst.close();
        } catch (Exception e) {
            System.out.println(titulo+"-error:" + e);
        }
    }

    void pasar_todas_tablas() {
        limpiar_bd_nuevo();
        leer_CATEGORIA_bd1();
        leer_CLIENTE_bd1();
        leer_ITEMINGREDIENTE_bd1();
        leer_PRODUCTO_bd1();
        leer_UNIDAD_bd1();
        leer_ZONA_bd1();
        leer_ingrediente_bd1();
        leer_tipo_gasto_bd1();
        leer_GASTO_bd1();
        leer_VENTACOMANDA_bd1();
        leer_CARGAR_DELIVERY_bd1();
        leer_ITEMVENTA_DELIVERY_bd1();
        leer_ITEMVENTA_PRODUCTO_bd1();
        leer_CAJADETALLE_VENTA_bd2();
        leer_CAJADETALLE_GASTO_bd2();
//        String sql = "TRUNCATE TABLE cliente;";
//        eveconn.SQL_execute_libre(connNuevo, sql);
        leer_PRODUCTO_INGREDIENTE_bd1();
//        leer_CLIENTE_bd1();

        JOptionPane.showMessageDialog(null, "MIGRACION TERMINADA");
        System.out.println("######MIGRACION TERMINADA######");
    }

    void limpiar_bd_nuevo() {
        String sql = "TRUNCATE TABLE cliente ;\n"
                + "TRUNCATE TABLE zona_delivery ;\n"
                + "TRUNCATE TABLE item_producto_ingrediente ;\n"
                + "TRUNCATE TABLE producto ;\n"
                + "TRUNCATE TABLE producto_categoria ;\n"
                + "TRUNCATE TABLE producto_ingrediente ;\n"
                + "TRUNCATE TABLE producto_unidad ;\n"
                + "TRUNCATE TABLE gasto ;\n"
                + "TRUNCATE TABLE gasto_tipo ;\n"
                + "TRUNCATE TABLE vale ;\n"
                + "TRUNCATE TABLE item_venta ;\n"
                + "TRUNCATE TABLE caja_detalle ;\n"
                + "TRUNCATE TABLE venta ;\n"
                + "TRUNCATE TABLE item_venta ;\n"
                + "TRUNCATE TABLE caja_detalle;\n";
        eveconn.SQL_execute_libre(connNuevo, sql);

    }

    public FrmMigracion() {
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

        jButton9 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblconn_viejo = new javax.swing.JLabel();
        lblconn_nuevo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton9.setText("PASAR TABLAS VIEJAS AL NUEVO");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton1.setText("CONECTAR VIEJO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CONECTAR NUEVO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lblconn_viejo.setText("jLabel1");

        lblconn_nuevo.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(lblconn_viejo)
                            .addComponent(jButton2)
                            .addComponent(lblconn_nuevo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblconn_viejo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(7, 7, 7)
                .addComponent(lblconn_nuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conectar_bd_viejo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        conectar_bd_nuevo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        pasar_todas_tablas();
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmMigracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMigracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMigracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMigracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMigracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel lblconn_nuevo;
    private javax.swing.JLabel lblconn_viejo;
    // End of variables declaration//GEN-END:variables
}
