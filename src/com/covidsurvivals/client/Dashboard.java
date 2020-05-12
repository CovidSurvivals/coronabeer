package com.covidsurvivals.client;

import com.covidsurvivals.coronabeer.CovidData;
import com.covidsurvivals.coronabeer.GraphFactory;
import com.covidsurvivals.coronabeer.GraphType;
import com.covidsurvivals.coronabeer.State;
import com.covidsurvivals.util.HttpDownloadUtility;
import com.covidsurvivals.util.ImportData;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static Map<String, String> filterMap = new HashMap<>();

    public static void main(String[] args) {
        // One time import data from CSV to Collection when program starts
        importDataFromCSVToCollection();

        // Display UI of filters and graphs
        displayUIAndGraph();

//        filterMap.put("startDate", "2020-05-01");
//        filterMap.put("endDate", "2020-05-09");
    }

    //Method for Import data from CSV to Collection from after download CSV file from AWS Data lake URL
    public static void importDataFromCSVToCollection() {
        HttpDownloadUtility.downloadFile();
        covidData = CovidData.getAll(ImportData.importFromCSVToCollection());
    }

    private static GraphicsConfiguration gc;
    public static void displayUIAndGraph() {
        // Create JFrame
        JFrame frame= new JFrame(gc);
        frame.setTitle("Welcome to Dashboard - Covid Survivals");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        JPanel mainPanel = new JPanel();
        frame.add(mainPanel);

        displayFilters(mainPanel);
        displayGraph(mainPanel);

        // Add both Panels to Frame
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void displayFilters(JPanel mainPanel){
        JPanel filterPanel = new JPanel();

        // Create ComboBox for Graph Type
        JComboBox graphTypeComboBox = new JComboBox(GraphType.values());
        graphTypeComboBox.setSelectedItem(GraphType.BAR);
        filterMap.put("graphType", GraphType.BAR.toString());
        graphTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                GraphType item = (GraphType)comboBox.getSelectedItem();
                filterMap.put("graphType", item.toString());
                displayGraph(mainPanel);
            }
        });
        graphTypeComboBox.setVisible(true);

        // Create ComboBox for State
        JComboBox stateComboBox = new JComboBox(State.values());
        stateComboBox.setSelectedItem(State.ALL);
        filterMap.put("stateId", String.valueOf(State.TEXAS.getStateId()));
        stateComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                State item = (State) comboBox.getSelectedItem();
                filterMap.put("stateId", String.valueOf(item.getStateId()));
                displayGraph(mainPanel);
            }
        });
        stateComboBox.setVisible(true);

        filterPanel.add(graphTypeComboBox);
        filterPanel.add(stateComboBox);
        mainPanel.add(filterPanel);
    }

    public static void displayGraph(JPanel mainPanel){
        JFreeChart graph = GraphFactory.createGraph(covidData,filterMap);

        // Create ChartPanel and add graph to it
        ChartPanel chartPanel = new ChartPanel( graph );
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        chartPanel.setVisible(true);
        mainPanel.add(chartPanel);
    }
}
