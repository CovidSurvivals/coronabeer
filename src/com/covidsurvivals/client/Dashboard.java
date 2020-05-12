package com.covidsurvivals.client;

import com.covidsurvivals.coronabeer.CovidData;
import com.covidsurvivals.coronabeer.GraphFactory;
import com.covidsurvivals.coronabeer.GraphType;
import com.covidsurvivals.util.HttpDownloadUtility;
import com.covidsurvivals.util.ImportData;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import java.awt.GraphicsConfiguration;

/*
    Client Class of Dashboard
 */

public class Dashboard {
    private static Collection<CovidData> covidData = new ArrayList<>();

    public static void main(String[] args) {
        // One time import data from CSV to Collection when program starts
        importDataFromCSVToCollection();

        // Display data from Collection of Covid Data
        //displayDataFromCollection();

        //TODO:call this with actual filter
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("graphType", GraphType.LINE.toString());
        filterMap.put("stateId", "48");
        filterMap.put("startDate", "2020-05-01");
        filterMap.put("endDate", "2020-05-09");
        JFreeChart graph = GraphFactory.createGraph(covidData,filterMap);
        displayGraph(graph);
    }

    //Method for Import data from CSV to Collection from after download CSV file from AWS Data lake URL
    public static void importDataFromCSVToCollection() {
        HttpDownloadUtility.downloadFile();
        covidData = CovidData.getAll(ImportData.importFromCSVToCollection());
    }

    public static void displayDataFromCollection() {
        System.out.println("Data from Collection\n");
        covidData.forEach(System.out :: println);
    }

    static GraphicsConfiguration gc;
    public static void displayGraph(JFreeChart graph){
        // Create JFrame
        JFrame frame= new JFrame(gc);
        frame.setTitle("Welcome to Dashboard - Covid Survivals");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        // Create ChartPanel and add graph to it
        ChartPanel chartPanel = new ChartPanel( graph );
        // Add ChartPanel to Frame
        frame.getContentPane().add(chartPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
