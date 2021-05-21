/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Grafico;

import java.awt.BorderLayout;
import java.text.AttributedString;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author Digno
 */
public class FunFreeChard {
    public void crear_graficoBar3D(JPanel panel_grafico,DefaultCategoryDataset dataset,String titulo,String domain,String rango) {
        panel_grafico.removeAll();
        JFreeChart chart = ChartFactory.createBarChart3D(
                titulo, // chart title
                domain, // domain axis label
                rango, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
        );

        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0)
        );

        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelsVisible(true);
        final BarRenderer r = (BarRenderer) renderer;
        r.setMaximumBarWidth(0.05);
        panel_grafico.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(chart);
        panel_grafico.add(CP, BorderLayout.CENTER);
        panel_grafico.validate();
    }
    public void crear_grafico_circular(JPanel panel_grafico,DefaultPieDataset  dataset,String titulo){
        panel_grafico.removeAll();
        final JFreeChart chart = ChartFactory.createPieChart(
            titulo,  // chart title
            dataset,             // data
            false,               // include legend
            true,
            false
        );

        final PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new CustomLabelGenerator());
        panel_grafico.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(chart);
        panel_grafico.add(CP, BorderLayout.CENTER);
        panel_grafico.validate();
    }
     /**
     * A custom label generator (returns null for one item as a test).
     */
    static class CustomLabelGenerator implements PieSectionLabelGenerator {
        
        /**
         * Generates a label for a pie section.
         * 
         * @param dataset  the dataset (<code>null</code> not permitted).
         * @param key  the section key (<code>null</code> not permitted).
         * 
         * @return the label (possibly <code>null</code>).
         */
        public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
            String result = null;    
            if (dataset != null) {
                if (!key.equals("Two")) {
                    result = key.toString();   
                }
            }
            return result;
        }

        @Override
        public AttributedString generateAttributedSectionLabel(PieDataset pd, Comparable cmprbl) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
   
    }
}
