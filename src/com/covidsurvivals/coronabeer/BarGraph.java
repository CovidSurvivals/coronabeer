package com.covidsurvivals.coronabeer;

import java.util.Collection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarGraph extends Graph {

    public JFreeChart drawGraph(Collection<CovidData> data) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for(CovidData item : data){
            dataset.addValue(item.getTotalCases(),"Cases", item.getDate());
            dataset.addValue(item.getTotalDeaths(),"Deaths", item.getDate());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Total number of Confirmed Cases, Deaths by Date",
                "Date",
                "Total Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0);

        return barChart;
    }
}
