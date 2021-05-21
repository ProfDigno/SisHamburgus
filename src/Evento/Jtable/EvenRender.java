/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jtable;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Digno
 */
public class EvenRender {
    public void rendertabla_item_venta(JTable tblitem_producto) {
        System.out.println("-->rendertabla_item_venta");
        tblitem_producto.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=1;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1="P";
                String campo2="I";
                String campo3="D";
                String campo4="O";
                String campo5="S";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.YELLOW;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.ORANGE;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.PINK;
                    color_text = Color.BLACK;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = new Color(153,153,255);
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_estados(JTable tbltabla,final int columna) {
        System.out.println("-->rendertabla_venta");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1="EMITIDO";
                String campo2="TERMINADO";
                String campo3="ANULADO";
                String campo4="CONFIRMADO";
                String campo5="ANULADO_temp";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
}
