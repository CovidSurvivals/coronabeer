package com.covidsurvivals.coronabeer;

import java.io.*;
import java.sql.Date;
import java.util.Collection;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineGraph extends Graph {

    public JFreeChart drawGraph(Collection<CovidData> data) {
        TimeSeries confirmedSeries = new TimeSeries("Confirmed Cases");
        TimeSeries deathSeries = new TimeSeries("Deaths");

        for(CovidData item : data){
            confirmedSeries.add(new Day(item.getDate()), item.getTotalCases());
            deathSeries.add(new Day(item.getDate()), item.getTotalDeaths());
        }

        final TimeSeriesCollection line_chart_dataset = new TimeSeriesCollection( );
        line_chart_dataset.addSeries( confirmedSeries );
        line_chart_dataset.addSeries( deathSeries );

        JFreeChart lineChartObject = ChartFactory.createXYLineChart(
                "Total number of Confirmed Cases by Date","Date",
                "Confirmed Cases",
                line_chart_dataset,PlotOrientation.VERTICAL,
                true,true,false);

        return lineChartObject;
    }
}