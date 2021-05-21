/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INSERT_AUTO;

import BASEDATO.EvenConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Digno
 */
public class Cla_insert_automatico {

    EvenConexion eveconn = new EvenConexion();

    public void insertar_ingredientes(Connection connNuevo) {
        /**
         * INSERT INTO public.producto(idproducto, nombre, precio_venta,
         * precio_compra, stock, orden,activar, cocina, descontar_stock,
         * comprar, vender, fk_idproducto_categoria,fk_idproducto_unidad) VALUES
         */
        String sql = "INSERT INTO public.producto(idproducto, nombre, precio_venta, precio_compra, stock, orden,\n"
                + "activar, cocina, descontar_stock, comprar, vender, fk_idproducto_categoria,fk_idproducto_unidad) \n"
                + "VALUES \n"
                + "(76,'LECHE CONDENSADA EXTRA',5000.0,0.0,100.0,14,true,true,false,false,true,11,14),\n"
                + "(96,'BANANAS EXTRA ',3000.0,0.0,100.0,42,true,true,false,false,true,11,16),\n"
                + "(98,'FRUTILLAS EXTRA',3000.0,0.0,100.0,23,true,true,false,false,true,11,16),\n"
                + "(106,'CHANTILLY EXTRA',5000.0,0.0,100.0,17,true,true,false,false,true,11,16),\n"
                + "(107,'LECHE EN POLVO EXTRA',5000.0,0.0,100.0,15,true,true,false,false,true,11,14),\n"
                + "(110,'SIN GRANOLA - 500ML',3000.0,0.0,100.0,50,true,true,false,false,true,11,23),\n"
                + "(111,'CEREAL EXTRA ',3000.0,0.0,100.0,20,true,true,false,false,true,11,14),\n"
                + "(112,'GRANOLA EXTRA ',3000.0,0.0,100.0,21,true,true,false,false,true,11,14),\n"
                + "(118,'NUTELLA EXTRA - CAMADAS ',10000.0,0.0,100.0,18,true,true,false,false,true,11,15),\n"
                + "(120,'PRODUCTOS',5000.0,0.0,100.0,28,true,true,false,false,true,11,23),\n"
                + "(130,'KIWI ',3000.0,0.0,100.0,31,true,true,false,false,true,11,16),\n"
                + "(131,'COBERTURA CHURROS - EXTRA  ',3000.0,0.0,100.0,49,true,true,false,false,true,11,14),\n"
                + "(132,'SIN GRANOLA - 770ML',5000.0,0.0,100.0,51,true,true,false,false,true,11,23),\n"
                + "(133,'1 MODIFICACION ',3000.0,0.0,100.0,57,true,true,false,false,true,11,23),\n"
                + "(134,'Vainilla',0.0,0.0,100.0,16,true,false,false,false,true,11,1),\n"
                + "(135,'TOMATE',3000.0,0.0,100.0,12,true,true,false,false,true,11,1),\n"
                + "(136,'SALSA Q CHURRON',2000.0,0.0,100.0,19,true,true,false,false,true,11,1),\n"
                + "(137,'SALSA DE AJO',2000.0,0.0,100.0,22,true,true,false,false,true,11,1),\n"
                + "(138,'Queso Paraguay',0.0,0.0,100.0,29,true,false,false,false,true,11,1),\n"
                + "(139,'POLLO',0.0,0.0,100.0,30,true,false,false,false,true,11,1),\n"
                + "(140,'Pinha',0.0,0.0,100.0,32,true,false,false,false,true,11,1),\n"
                + "(141,'PEPINILLO',5000.0,0.0,100.0,26,true,false,false,false,true,11,1),\n"
                + "(142,'Parmesano',0.0,0.0,100.0,33,true,false,false,false,true,11,1),\n"
                + "(143,'PAN P/ HAMBURGUESA',3000.0,0.0,100.0,34,true,true,false,false,true,11,1),\n"
                + "(144,'Palmito',0.0,0.0,100.0,35,true,false,false,false,true,11,1),\n"
                + "(145,'Oregano',0.0,0.0,100.0,36,true,false,false,false,true,11,1),\n"
                + "(146,'NUTELLA',5000.0,0.0,100.0,52,true,true,false,false,true,11,16),\n"
                + "(147,'MOZZARELA',3000.0,0.0,100.0,37,true,true,false,false,true,11,1),\n"
                + "(148,'MOUSSE DE FRUTILLA',5000.0,0.0,100.0,56,true,true,false,false,true,11,1),\n"
                + "(149,'2 -UNI - VASITOS COBERTURA CHURROS - EXTRA ',5000.0,0.0,100.0,88,true,true,false,false,true,11,14),\n"
                + "(150,'MERMELADA DE FRUTILLA',7000.0,0.0,100.0,41,true,true,false,false,true,11,1),\n"
                + "(151,'MANI',3000.0,0.0,100.0,69,true,true,false,false,true,11,1),\n"
                + "(152,'LECHUGA ',2000.0,0.0,100.0,24,true,true,false,false,true,11,1),\n"
                + "(153,'Leche Entera',0.0,0.0,100.0,38,true,false,false,false,true,11,1),\n"
                + "(154,'LECHE EN POLVO',5000.0,0.0,100.0,39,true,true,false,false,true,11,1),\n"
                + "(155,'LECHE CONDENSADA',5000.0,0.0,100.0,55,true,true,false,false,true,11,1),\n"
                + "(156,'JAMON DE PAVO',0.0,0.0,100.0,54,true,false,false,false,true,11,1),\n"
                + "(157,'JAMON CRUDO',0.0,0.0,100.0,75,true,false,false,false,true,11,1),\n"
                + "(158,'JAMON',3000.0,0.0,100.0,53,true,true,false,false,true,11,1),\n"
                + "(159,'CARNE EXTRA HAMBUGUESA',10000.0,0.0,100.0,25,true,true,false,false,true,11,1),\n"
                + "(160,'GUAYABA',0.0,0.0,100.0,40,true,false,false,false,true,11,1),\n"
                + "(161,'GRANOLA',3000.0,0.0,100.0,68,true,true,false,false,true,11,1),\n"
                + "(162,'FRUTILLAS',3000.0,0.0,100.0,73,true,true,false,false,true,11,1),\n"
                + "(163,'FRUTAS FRESCAS',0.0,0.0,100.0,74,true,false,false,false,true,11,1),\n"
                + "(164,'DULCE DE LECHE ',0.0,0.0,100.0,72,true,false,false,false,true,11,1),\n"
                + "(165,'Crema Pastelera',0.0,0.0,100.0,89,true,false,false,false,true,11,1),\n"
                + "(166,'COCO RALLADO',0.0,0.0,100.0,13,true,false,false,false,true,11,1),\n"
                + "(167,'coca lata',5000.0,0.0,100.0,58,true,false,false,false,true,11,1),\n"
                + "(168,'Chocolate Negro',0.0,0.0,100.0,59,true,false,false,false,true,11,1),\n"
                + "(169,'Chocolate',0.0,0.0,100.0,60,true,false,false,false,true,11,1),\n"
                + "(170,'Choclo',0.0,0.0,100.0,61,true,false,false,false,true,11,1),\n"
                + "(171,'DOBLE CHEDDAR FETAS',6000.0,0.0,100.0,79,true,true,false,false,true,11,1),\n"
                + "(172,'CHANTILLY',5000.0,0.0,100.0,77,true,true,false,false,true,11,1),\n"
                + "(173,'CEREALES',3000.0,0.0,100.0,67,true,true,false,false,true,11,1),\n"
                + "(174,'CEBOLLA',3000.0,0.0,100.0,78,true,true,false,false,true,11,1),\n"
                + "(175,'CATUPIRY EXTRA MANGA',7000.0,0.0,100.0,71,true,true,false,false,true,11,1),\n"
                + "(176,'CARNE SECA ',0.0,0.0,100.0,70,true,false,false,false,true,11,1),\n"
                + "(177,'CARNE',0.0,0.0,100.0,62,true,false,false,false,true,11,1),\n"
                + "(178,'CANELA',0.0,0.0,100.0,48,true,false,false,false,true,11,1),\n"
                + "(179,'Basos de 300ml',0.0,0.0,100.0,63,true,false,false,false,true,11,1),\n"
                + "(180,'Baso de 500ml',0.0,0.0,100.0,64,true,false,false,false,true,11,1),\n"
                + "(181,'BANANA',3000.0,0.0,100.0,47,true,false,false,false,true,11,1),\n"
                + "(182,'BACON',0.0,0.0,100.0,46,true,false,false,false,true,11,1),\n"
                + "(183,'ARVEJA',2000.0,0.0,100.0,45,true,true,false,false,true,11,1),\n"
                + "(184,'ACEITUNAS',0.0,0.0,100.0,44,true,true,false,false,true,11,1),\n"
                + "(185,'ACAI',0.0,0.0,100.0,65,true,false,false,false,true,11,1),\n"
                + "(186,'2 MODIFICACION ',5000.0,0.0,100.0,27,true,true,false,false,true,11,1),\n"
                + "(193,'PRODUCTOS A AGREGAR',0.0,0.0,100.0,76,true,true,false,false,true,11,23),\n"
                + "(196,'SIN LECHE POLVO - 500ML',3000.0,0.0,100.0,43,true,true,false,false,true,11,23),\n"
                + "(203,'BEIJINHO ',10000.0,0.0,100.0,66,true,true,false,false,true,11,15),\n"
                + "(224,'HUEVO',3000.0,0.0,100.0,80,true,true,false,false,true,11,1),\n"
                + "(227,'SIN GRANOLA ',3000.0,0.0,100.0,81,true,true,false,false,true,11,1),\n"
                + "(231,'CONSUMICION ',320000.0,0.0,100.0,90,true,true,false,false,true,11,23),\n"
                + "(238,'BATATA PALLA',3000.0,0.0,100.0,82,true,true,false,false,true,11,1),\n"
                + "(239,'PURE DE PAPAS ',3000.0,0.0,100.0,83,true,true,false,false,true,11,1),\n"
                + "(240,'SALSA ROJA ',1000.0,0.0,100.0,84,true,true,false,false,true,11,1),\n"
                + "(241,'PANCHO-SALCHICHA',3000.0,0.0,100.0,85,true,true,false,false,true,11,1),\n"
                + "(242,'SALAME ITALIANO',3000.0,0.0,100.0,86,true,true,false,false,true,11,1),\n"
                + "(243,'PAN HOT DOG',3000.0,0.0,100.0,87,true,true,false,false,true,11,1),\n"
                + "(268,'--DELIVERY --',3000.0,0.0,100.0,8,true,false,false,false,true,11,1),\n"
                + "(270,'PESTANHA',50000.0,0.0,100.0,6,true,true,false,false,true,11,1),\n"
                + "(275,'KILO - PESO',6400.0,0.0,100.0,9,true,true,false,false,true,11,1),\n"
                + "(310,'GUARANA ENERGETICO',0.0,0.0,100.0,11,true,true,false,false,true,11,1),\n"
                + "(311,'JUGO DE NARANJA',0.0,0.0,100.0,7,true,true,false,false,true,11,1),\n"
                + "(328,'BACON ADICIONAL ',7000.0,0.0,100.0,10,true,true,false,false,true,11,1),\n"
                + "(329,'LECHUGA + TOMATE - EXTRA',5000.0,0.0,100.0,2,true,true,false,false,true,11,1),\n"
                + "(335,'HAMBURGUESA SIMPLES',3500.0,0.0,100.0,3,true,true,false,false,true,11,1),\n"
                + "(341,'LECHE EN POLVO',5000.0,0.0,100.0,4,true,true,false,false,true,11,15),\n"
                + "(342,'LECHE CONDENSADA',5000.0,0.0,100.0,5,true,true,false,false,true,11,15),\n"
                + "(413,'SALSA BBQ',2000.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(425,'VALE FUNCIONARIOS ',50000.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(436,'CHEDDAR EXTRA MANGA',5000.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(437,'MAYONESA',0.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(438,'KATCHUP',0.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(439,'MOSTAZA ',0.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(440,'CHEDDAR FETA ',3000.0,0.0,100.0,0,true,true,false,false,true,11,1),\n"
                + "(441,'CARNE HAMBURGUESA 130GRS',10000.0,0.0,100.0,0,true,true,false,false,true,11,1);";
            String sql1="select * from producto where idproducto=118;";
            if(getBoolean_valInsert(connNuevo, sql1)){
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>SE INSERTO INGREDIENTE...!!");
            }else{
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>NO SE INSERTO INGREDIENTE");
                eveconn.SQL_execute_libre(connNuevo, sql);
            }
    }

    boolean getBoolean_valInsert(Connection conn, String sql) {
        boolean valInsert = false;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                valInsert = true;
            }
        } catch (Exception e) {
            System.out.println("ERROR:" + sql + "\n" + e);
        }
        return valInsert;
    }
}
