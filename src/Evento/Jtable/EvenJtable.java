/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jtable;

import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Digno
 */
public class EvenJtable {

    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    /**
     * Da el ancho de las columnas de una tabla se calcula si la tabla y el
     * vector tienen el mismo tamanho la sumatoria del vector debe dar 100 y la
     * cantidad depende de la cantidad de columna
     *
     * @param tabla jtable la tabla a dar tamanho
     * @param Ancho el vector con el ancho de cada columna se calcula por
     * porcentaje
     */
    public void setAnchoColumnaJtable(JTable tabla, int Ancho[]) {
        if (tabla.getColumnCount() <= Ancho.length) {
            JViewport scroll = (JViewport) tabla.getParent();
            int ancho = scroll.getWidth();
            TableColumnModel modeloColumna = tabla.getColumnModel();
            TableColumn columnaTabla;
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                columnaTabla = modeloColumna.getColumn(i);
                int anchoColumna1 = (Ancho[i] * ancho) / 100;
                columnaTabla.setPreferredWidth(anchoColumna1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "EL TAMANO DE LA TABLA CON EL VERCTOR NO ES IGUAL", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getInt_select_id(JTable tabla) {
        String sid = "0";
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            sid = ((tabla.getModel().getValueAt(row, 0).toString()));
        }
        return Integer.parseInt(sid);
    }

    public String getString_select(JTable tabla, int columna) {
        String sid = "0";
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            sid = ((tabla.getModel().getValueAt(row, columna).toString()));
        }
        return sid;
    }
    public int getInt_select(JTable tabla, int columna) {
        String sid = "0";
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            sid = ((tabla.getModel().getValueAt(row, columna).toString()));
        }
        return Integer.parseInt(sid);
    }
    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            JOptionPane.showMessageDialog(null, "ESTE CAMPO ESTA EN NULO");
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "POSIBLEMENTE ESTE CAMPO NO ES NUMERICO\n" + nfe);
            return false;
        }
        return true;
    }

    private boolean isMayorColumna(JTable tblitem, int columna) {
        if (columna > tblitem.getColumnCount()) {
            JOptionPane.showMessageDialog(null, "LA COLUMNA SUPERA LA CANTIDAD MAXIMA: " + tblitem.getColumnCount());
            return false;
        } else {
            return true;
        }
    }

    public boolean getBoolean_Eliminar_Fila(JTable tabla, DefaultTableModel Detabla) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Detabla.removeRow(fila);
            return true;
        } else {
            JOptionPane.showMessageDialog(tabla, "SELECCIONAR UNA FILA PARA ELIMINAR");
            return false;
        }
    }

    public boolean getBoolean_Eliminar_Fila_subfila(JTable tabla, DefaultTableModel Detabla) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String ingrediente = ((tabla.getModel().getValueAt(fila, 1).toString()));
            Detabla.removeRow(fila);
            while (true) {
                String nombre = "";
                try {
                    ingrediente = ((tabla.getModel().getValueAt(fila, 1).toString()));
                    nombre = ((tabla.getModel().getValueAt(fila, 2).toString()));
                } catch (Exception e) {
                    System.out.println("error:" + e);
                    break;
                }
                if (ingrediente.equals("P")|| ingrediente.equals("N") || ingrediente.equals("S")) {
                    break;
                } else {
                    Detabla.removeRow(fila);
                    System.out.println("Eliminar: " + fila + "-" + nombre);
                }
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(tabla, "SELECCIONAR UNA FILA PARA ELIMINAR");
            return false;
        }
    }

    public boolean getBoolean_Eliminar_Fila_delivery(JTable tabla, DefaultTableModel Detabla) {
        int cant_fila = tabla.getRowCount();
        if (cant_fila > 0) {

            for (int row = 0; row < cant_fila; row++) {
                String ingrediente = "";
                try {
                    ingrediente = ((tabla.getModel().getValueAt(row, 1).toString()));
                } catch (Exception e) {
                    System.out.println("error:" + e);
//                    break;
                }
                if (ingrediente.equals("D")) {
                    Detabla.removeRow(row);
                    System.out.println("Eliminar delivery: " + row);
                }
            }
            return true;
        } else {
//            JOptionPane.showMessageDialog(tabla, "SELECCIONAR UNA FILA PARA ELIMINAR");
            return false;
        }
    }

    public void mostrar_JTabbedPane(JTabbedPane jtabee, int panel) {
        jtabee.setSelectedIndex(panel);
    }

    public double getDouble_sumar_tabla(JTable tblitem, int columna) {
        double total_item = 0;
        if (tblitem.getRowCount() > 0) {
            if (isMayorColumna(tblitem, columna)) {
                String textar = "";
                try {
                    textar = (tblitem.getModel().getValueAt(0, columna).toString());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ES POSIBLE QUE SEA NULA EL CAMPO\n" + e);
                }

                if (isNumeric(textar)) {
                    for (int row = 0; row < tblitem.getRowCount(); row++) {
                        String subtotal = (tblitem.getModel().getValueAt(row, columna).toString());
                        double DOsubtotal = Double.parseDouble(subtotal);
                        total_item = total_item + DOsubtotal;
                    }
                }
            }
        }
        return total_item;
    }

    public double getDouble_monto_validado(JTable tblitem, int columna, int columnaval, String textoval) {
        double total_item = 0;
        if (tblitem.getRowCount() > 0) {
            if (isMayorColumna(tblitem, columna)) {
                for (int row = 0; row < tblitem.getRowCount(); row++) {
                    String tipo = (tblitem.getModel().getValueAt(row, columnaval).toString());
                    if (tipo.equals(textoval)) {
                        String subtotal = (tblitem.getModel().getValueAt(row, columna).toString());
                        total_item = Double.parseDouble(subtotal);
                    }
                }
            }
        }
        return total_item;
    }

    public boolean getBoolean_validar_select(JTable tabla) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "SELECCIONE LA TABLA", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public boolean getBoolean_validar_cant_cargado(JTable tabla) {
        int row = tabla.getRowCount();
        System.out.println("cantidad fila=" + row);
        if (row == 0) {
            JOptionPane.showMessageDialog(tabla, "CARGAR POR LO MENOS UN ITEM", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public void limpiar_tabla_datos(DefaultTableModel Detabla) {
        String titulo = "limpiar_tabla_datos";
        try {
            int a = Detabla.getRowCount();
            for (int i = 0; i < a; i++) {
                Detabla.removeRow(0);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, titulo);
        }
    }

    public void cargar_tabla_datos(JTable tabla, DefaultTableModel Detabla, String dato[]) {
        String titulo = "cargar_tabla_datos";
        try {
            Detabla.addRow(dato);
            tabla.setModel(Detabla);
            for (int c = 0; c < dato.length; c++) {
                System.out.print(dato[c] + "\t");
            }
            System.out.println();
        } catch (Exception e) {
            evmen.mensaje_error(e, titulo);
        }

    }

    public void cargar_tabla_dato_bajolinea(JTable tabla, DefaultTableModel Detabla, String dato[]) {
        String titulo = "cargar_tabla_dato_bajolinea";
        try {
            int row = tabla.getSelectedRow() + 1;
            Detabla.insertRow(row, dato);
            tabla.setModel(Detabla);
            for (int c = 0; c < dato.length; c++) {
                System.out.print(dato[c] + "\t");
            }
            System.out.println();
        } catch (Exception e) {
            evmen.mensaje_error(e, titulo);
        }

    }
    public void cargar_tabla_dato_ultima_linea(JTable tabla, DefaultTableModel Detabla, String dato[]) {
        String titulo = "cargar_tabla_dato_ultima_linea";
        try {
            int row = tabla.getRowCount();
            Detabla.insertRow(row, dato);
            tabla.setModel(Detabla);
            for (int c = 0; c < dato.length; c++) {
                System.out.print(dato[c] + "\t");
            }
            System.out.println();
        } catch (Exception e) {
            evmen.mensaje_error(e, titulo);
        }

    }
    public void crear_tabla_datos(JTable tabla, DefaultTableModel Detabla, String titulos[]) {
        String tutulo = "TablaLOCALCrear";
        try {
            Detabla.setColumnIdentifiers(titulos);
            tabla.setModel(Detabla);
            for (int c = 0; c < titulos.length; c++) {
                System.out.print(titulos[c] + "\t");
            }
            System.out.println();
        } catch (Exception e) {
            evmen.mensaje_error(e, tutulo);
        }
    }

    public void calcular_subtotal(JTable tblitem, DefaultTableModel Detabla, int col_cantidad,int col_precio,int col_subtotal) {
        double Doitem_subtotal = 0;
        if (tblitem.getRowCount() > 0) {
            for (int row = 0; row < tblitem.getRowCount(); row++) {
                String Sitem_cantidad = (tblitem.getModel().getValueAt(row, col_cantidad).toString());
                double Doitem_cantidad = Double.parseDouble(Sitem_cantidad);
                String Sitem_precio = (tblitem.getModel().getValueAt(row, col_precio).toString());
                double Doitem_precio = Double.parseDouble(Sitem_precio);
                Doitem_subtotal = Doitem_precio * Doitem_cantidad;
                Detabla.setValueAt(Doitem_subtotal, row, col_subtotal);
            }
        }
    }
    // model.setValueAt(value, 1, 1);
}
