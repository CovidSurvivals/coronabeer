package com.covidsurvivals.coronabeer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/*
    Business class for creating Line chart using filters and categories
    Extend super class Graph
 */

class LineGraph extends Graph {

    public JFreeChart drawGraph(Collection<CovidData> data, boolean isConfirmed) {
        TimeSeries confirmedSeries = new TimeSeries("Cases");
        TimeSeries deathSeries = new TimeSeries("Deaths");

        for(CovidData item : data){
            RegularTimePeriod date = new Day(Date.from(item.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if(isConfirmed) {
                confirmedSeries.add(date, item.getTotalCases());
            }
            else {
                deathSeries.add(date, item.getTotalDeaths());
            }
        }

        final TimeSeriesCollection line_chart_dataset = new TimeSeriesCollection( );
        if(isConfirmed) {
            line_chart_dataset.addSeries(confirmedSeries);
        } else {
            line_chart_dataset.addSeries(deathSeries);
        }

        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
                isConfirmed ? "Total number of Confirmed Cases by Date" : "Total number of Deaths by Date",
                "Date", // X-Axis Label
                "Total Count", // Y-Axis Label
                line_chart_dataset);

        XYPlot plot = lineChart.getXYPlot();
        plot.setBackgroundPaint( Color.WHITE );
        lineChart.getTitle().setFont(new Font("Serif", Font.BOLD, 22));
        if(isConfirmed) {
            plot.getRenderer().setSeriesPaint(0, Color.BLUE);
        } else {
            plot.getRenderer().setSeriesPaint(0, Color.RED);
        }

        DateAxis dateAxis = (DateAxis) lineChart.getXYPlot().getDomainAxis();
        dateAxis.setAutoRange(true);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MMM dd, yyyy"));

        return lineChart;
    }
}