package com.covidsurvivals.coronabeer;

import java.util.Collection;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class LineGraph extends Graph {

    public JFreeChart drawGraph(Collection<CovidData> data) {
        TimeSeries confirmedSeries = new TimeSeries("Cases");
        TimeSeries deathSeries = new TimeSeries("Deaths");

        for(CovidData item : data){
            confirmedSeries.add(new Day(item.getDate()), item.getTotalCases());
            deathSeries.add(new Day(item.getDate()), item.getTotalDeaths());
        }

        final TimeSeriesCollection line_chart_dataset = new TimeSeriesCollection( );
        line_chart_dataset.addSeries( confirmedSeries );
        line_chart_dataset.addSeries( deathSeries );

        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
                "Total number of Confirmed Cases, Deaths by Date", // Chart
                "Date", // X-Axis Label
                "Total Count", // Y-Axis Label
                line_chart_dataset);

        return lineChart;
    }
}