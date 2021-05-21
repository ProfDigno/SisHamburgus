/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.ENTIDAD;

/**
 *
 * @author Digno
 */
public class item_producto_ingrediente {
 /**
  * CREATE TABLE "item_producto_ingrediente" (
	"iditem_producto_ingrediente" SERIAL NOT NULL ,
	"fk_idproducto" INT NOT NULL ,
	"fk_idproducto_ingrediente" INT NOT NULL ,
	PRIMARY KEY("iditem_producto_ingrediente")
);
  */   
    private int iditem_producto_ingrediente;
    private int fk_idproducto;
    private int fk_idproducto_ingrediente;
    private static String tabla;
    private static String idtabla;
    
    public item_producto_ingrediente() {
        setTabla("item_producto_ingrediente");
        setIdtabla("iditem_producto_ingrediente");
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        item_producto_ingrediente.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        item_producto_ingrediente.idtabla = idtabla;
    }
    
    public int getIditem_producto_ingrediente() {
        return iditem_producto_ingrediente;
    }

    public void setIditem_producto_ingrediente(int iditem_producto_ingrediente) {
        this.iditem_producto_ingrediente = iditem_producto_ingrediente;
    }

    public int getFk_idproducto() {
        return fk_idproducto;
    }

    public void setFk_idproducto(int fk_idproducto) {
        this.fk_idproducto = fk_idproducto;
    }

    public int getFk_idproducto_ingrediente() {
        return fk_idproducto_ingrediente;
    }

    public void setFk_idproducto_ingrediente(int fk_idproducto_ingrediente) {
        this.fk_idproducto_ingrediente = fk_idproducto_ingrediente;
    }

    @Override
    public String toString() {
        return "item_producto_ingrediente{" + "iditem_producto_ingrediente=" + iditem_producto_ingrediente + ", fk_idproducto=" + fk_idproducto + ", fk_idproducto_ingrediente=" + fk_idproducto_ingrediente + '}';
    }
    
}
