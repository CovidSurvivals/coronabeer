package com.covidsurvivals.coronabeer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class BarGraph extends Graph {

    public JFreeChart drawGraph(Collection<CovidData> data) {
        //final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        TimeSeries confirmedSeries = new TimeSeries("Cases");
        TimeSeries deathSeries = new TimeSeries("Deaths");

        for(CovidData item : data){
            confirmedSeries.add(new Day(item.getDate()), item.getTotalCases());
            deathSeries.add(new Day(item.getDate()), item.getTotalDeaths());
        }

        final TimeSeriesCollection bar_chart_dataset = new TimeSeriesCollection( );
        bar_chart_dataset.addSeries( confirmedSeries );
        bar_chart_dataset.addSeries( deathSeries );

//
//        for(CovidData item : data){
//            dataset.addValue(item.getTotalCases(),"Cases", item.getDate());
//            dataset.addValue(item.getTotalDeaths(),"Deaths", item.getDate());
//        }

        JFreeChart barChart = ChartFactory.createXYBarChart(
                "Total number of Confirmed Cases, Deaths by Date",
                "Date",
                true,
                "Total Count",
                bar_chart_dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = barChart.getXYPlot();
        XYBarRenderer br = (XYBarRenderer) plot.getRenderer();
        br.setMargin(0.2);
        br.setDrawBarOutline(false);

        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setAutoRange(true);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MMM dd, yyyy"));

        barChart.getPlot().setBackgroundPaint( Color.WHITE );

        return barChart;
    }
}
