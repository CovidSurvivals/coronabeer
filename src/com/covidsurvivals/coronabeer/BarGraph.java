package com.covidsurvivals.coronabeer;


import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarGraph extends Graph {
//    private static final long serialVersionUID = 1L;
//
//    static {
//        // set a theme using the new shadow generator feature available in
//        // 1.0.14 - for backwards compatibility it is not enabled by default
//        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
//                true));
//    }
//
//    /**
//     * Creates a new demo instance.
//     *
//     * @param title  the frame title.
//     */
//    public BarGraph(String title) {
//        super(title);
//        CategoryDataset dataset = createDataset();
//        JFreeChart chart = createChart(dataset);
//        ChartPanel chartPanel = new ChartPanel(chart, false);
//        chartPanel.setBackground(null);
//        chartPanel.setFillZoomRectangle(true);
//        chartPanel.setMouseWheelEnabled(true);
//        chartPanel.setDismissDelay(Integer.MAX_VALUE);
//        chartPanel.setPreferredSize(new Dimension(500, 270));
//        setContentPane(chartPanel);
//    }
//
//    /**
//     * Returns a sample dataset.
//     *
//     * @return The dataset.
//     */
//    private static CategoryDataset createDataset() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(7445, "JFreeSVG", "Warm-up");
//        dataset.addValue(24448, "Batik", "Warm-up");
//        dataset.addValue(4297, "JFreeSVG", "Test");
//        dataset.addValue(21022, "Batik", "Test");
//        return dataset;
//    }
//
//    /**
//     * Creates a sample chart.
//     *
//     * @param dataset  the dataset.
//     *
//     * @return The chart.
//     */
//    public static JFreeChart createChart(CategoryDataset dataset) {
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Performance: JFreeSVG vs Batik", null /* x-axis label*/,
//                "Milliseconds" /* y-axis label */, dataset);
//        chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
//                + "format (lower bars = better performance)"));
//        chart.setBackgroundPaint(null);
//        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//        plot.setBackgroundPaint(null);
//
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        BarRenderer renderer = (BarRenderer) plot.getRenderer();
//        renderer.setDrawBarOutline(false);
//        chart.getLegend().setFrame(BlockBorder.NONE);
//        return chart;
//    }
//
//    /**
//     * Starting point for the demonstration application.
//     *
//     * @param args  ignored.
//     */
//    public static void main(String[] args) {
//        BarGraph demo = new BarGraph("JFreeChart: BarChartDemo1.java");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//    }
}
