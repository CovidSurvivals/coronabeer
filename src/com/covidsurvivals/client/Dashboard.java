package com.covidsurvivals.client;

import com.covidsurvivals.coronabeer.CovidData;
import com.covidsurvivals.coronabeer.GraphFactory;
import com.covidsurvivals.coronabeer.GraphType;
import com.covidsurvivals.coronabeer.State;
import com.covidsurvivals.util.HttpDownloadUtility;
import com.covidsurvivals.util.ImportData;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;

/*
    Client Class of Dashboard that display filters and graph
 */

public class Dashboard {
    private static Collection<CovidData> covidData = new ArrayList<>();
    private static Map<String, String> filterMap = new HashMap<>();
    private static Collection<CovidData> filteredData = new ArrayList<>();

    public static void main(String[] args) {
        // One time import data from CSV to Collection when program starts
        importDataFromCSVToCollection();

        // Display UI of filters and graphs
        displayUIAndGraph();
    }

    //Method for Import data from CSV to Collection from after download CSV file from AWS Data lake URL
    public static void importDataFromCSVToCollection() {
        HttpDownloadUtility.downloadFile();
        covidData = CovidData.getAll(ImportData.importFromCSVToCollection());
    }

    // Method for display UI (Filters and Graph)
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

        JPanel filterPanel = new JPanel();
        JPanel graphPanel = new JPanel();

        displayFilters(mainPanel,filterPanel, graphPanel);
        displayGraph(mainPanel,graphPanel);

        // Add both Panels to Frame
        frame.getContentPane().add(mainPanel);
        mainPanel.setLayout(new GridLayout(2, 1));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        filteredData = GraphFactory.getGraphData(covidData, filterMap);
    }

    // Display filters (Top panel) using JFrame and fill values plus call actionperformed methods to refresh the page data
    public static void displayFilters(JPanel mainPanel, JPanel filterPanel, JPanel graphPanel){

        filterPanel.setLayout(new GridLayout(1, 1));
        filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Filter Panel"));

        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new GridLayout(5, 1));
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        // Top Label Panel
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        labelPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblCovid = new JLabel("COVID-19 DATA - USA", SwingConstants.CENTER);
        lblCovid.setFont(new Font("Serif", Font.BOLD, 30));
        lblCovid.setForeground(Color.RED);
        lblCovid.setBounds(10,10,100,100);
        labelPanel.add(lblCovid);

        // Confirmed and Deaths Count Panel
        JPanel confirmedAndDeathsPanel = new JPanel();
        confirmedAndDeathsPanel.setLayout(new GridLayout(1, 2));
        confirmedAndDeathsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        JPanel confirmedPanel = new JPanel();
        confirmedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblConfirmed = new JLabel("Confirmed Cases: ");
        lblConfirmed.setFont(new Font("Serif", Font.BOLD, 16));
        lblConfirmed.setBounds(10,10,100,100);
        confirmedPanel.add(lblConfirmed);
        JLabel lblConfirmedCount = new JLabel();
        lblConfirmedCount.setFont(new Font("Serif", Font.BOLD, 18));
        lblConfirmedCount.setBounds(10,10,100,100);
        lblConfirmedCount.setForeground(Color.BLUE);

        confirmedPanel.add(lblConfirmedCount);

        JPanel deathsPanel = new JPanel();
        deathsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblDeaths = new JLabel("Deaths: ");
        lblDeaths.setFont(new Font("Serif", Font.BOLD, 16));
        lblDeaths.setBounds(10,10,100,100);
        deathsPanel.add(lblDeaths);
        JLabel lblDeathsCount = new JLabel();
        lblDeathsCount.setFont(new Font("Serif", Font.BOLD, 18));
        lblDeathsCount.setForeground(Color.RED);
        deathsPanel.add(lblDeathsCount);

        confirmedAndDeathsPanel.add(confirmedPanel);
        confirmedAndDeathsPanel.add(deathsPanel);

        // %Death/Confirmed Panel
        JPanel deathVsConfirmedPanel = new JPanel();
        deathVsConfirmedPanel.setLayout(new GridLayout(1, 1));
        deathVsConfirmedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        JLabel lblDeathVsConfirmed = new JLabel("%Death/Confirmed: ",SwingConstants.CENTER);
        lblDeathVsConfirmed.setFont(new Font("Serif", Font.BOLD, 16));
        lblDeathVsConfirmed.setBounds(10,10,100,100);
        deathVsConfirmedPanel.add(lblDeathVsConfirmed);

        JLabel lblDeathVsConfirmedCount = new JLabel();
        lblDeathVsConfirmedCount.setFont(new Font("Serif", Font.BOLD, 18));
        lblDeathVsConfirmedCount.setBounds(10,10,100,100);
        lblDeathVsConfirmedCount.setForeground(Color.RED);
        deathVsConfirmedPanel.add(lblDeathVsConfirmedCount);

        // Trend Panel
        JPanel trendPanel = new JPanel();
        trendPanel.setLayout(new GridLayout(1, 2));
        trendPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        JPanel confirmedTrendPanel = new JPanel();
        JPanel deathsTrendPanel = new JPanel();

        trendPanel.add(confirmedTrendPanel);
        trendPanel.add(deathsTrendPanel);

        // Filter Input Panel
        JPanel filterInputPanel = new JPanel();
        filterInputPanel.setLayout(new GridLayout(1, 2));
        filterInputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        // Create ComboBox for Graph Type
        JComboBox graphTypeComboBox = new JComboBox(GraphType.values());
        graphTypeComboBox.setSize(200,graphTypeComboBox.getPreferredSize().height);
        graphTypeComboBox.setSelectedItem(GraphType.BAR.toString());
        filterMap.put("graphType", GraphType.BAR.toString());
        graphTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                GraphType item = (GraphType)comboBox.getSelectedItem();
                filterMap.put("graphType", item.toString());
                displayGraph(mainPanel,graphPanel);
            }
        });
        graphTypeComboBox.setVisible(true);

        // Create ComboBox for State
        JComboBox stateComboBox = new JComboBox(State.values());
        stateComboBox.setSelectedItem(State.ALL.toString());
        filterMap.put("stateId", String.valueOf(State.ALL.getStateId()));
        stateComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                State item = (State) comboBox.getSelectedItem();
                filterMap.put("stateId", String.valueOf(item.getStateId()));
                lblConfirmedCount.setText(String.format("%,8d%n", getConfirmedCases()));
                lblDeathsCount.setText(String.format("%,8d%n", getDeaths()));
                DecimalFormat df = new DecimalFormat("#.00");
                lblDeathVsConfirmedCount.setText(df.format(getDeathsVsConfirmedPercentage()) + "%");
                confirmedTrendPanel.add(getConfirmedTrend(confirmedTrendPanel));
                deathsTrendPanel.add(getDeathTrend(deathsTrendPanel));
                displayGraph(mainPanel,graphPanel);
            }
        });
        stateComboBox.setVisible(true);

        JPanel graphTypePanel = new JPanel();
        JPanel statePanel = new JPanel();
        JLabel lblGraphType = new JLabel("Graph Type:");
        lblGraphType.setFont(new Font("Serif", Font.BOLD, 16));
        graphTypePanel.add(lblGraphType);
        graphTypePanel.add(graphTypeComboBox);
        JLabel lblState = new JLabel("State:");
        lblState.setFont(new Font("Serif", Font.BOLD, 16));
        statePanel.add(lblState);
        statePanel.add(stateComboBox);

        filterInputPanel.add(graphTypePanel);
        filterInputPanel.add(statePanel);

        lblConfirmedCount.setText(String.format("%,8d%n", getConfirmedCases()));
        lblDeathsCount.setText(String.format("%,8d%n", getDeaths()));
        DecimalFormat df = new DecimalFormat("#.00");
        lblDeathVsConfirmedCount.setText(df.format(getDeathsVsConfirmedPercentage()) + "%");
        confirmedTrendPanel.add(getConfirmedTrend(confirmedTrendPanel));
        deathsTrendPanel.add(getDeathTrend(deathsTrendPanel));

        leftPanel.add(labelPanel);
        leftPanel.add(filterInputPanel);
        leftPanel.add(confirmedAndDeathsPanel);
        leftPanel.add(deathVsConfirmedPanel);
        leftPanel.add(trendPanel);

        filterPanel.add(leftPanel);
        mainPanel.add(filterPanel);
    }

    // Display Graphs (bottom panel) with filtered data
    public static void displayGraph(JPanel mainPanel, JPanel graphPanel){
        graphPanel.setLayout(new GridLayout(1,2));
        graphPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Graph Panel"));

        graphPanel.removeAll();
        graphPanel.revalidate();

        // Create ChartPanel and add both graphs to it
        JFreeChart graph1 = GraphFactory.createGraph(covidData,filterMap,true);
        ChartPanel chartPanel1 = new ChartPanel( graph1 );
        chartPanel1.setMouseZoomable(true, false);
        chartPanel1.setVisible(true);
        graphPanel.add(chartPanel1);

        JFreeChart graph2 = GraphFactory.createGraph(covidData,filterMap,false);
        ChartPanel chartPanel2 = new ChartPanel( graph2 );
        chartPanel2.setMouseZoomable(true, false);
        chartPanel2.setVisible(true);
        graphPanel.add(chartPanel2);

        mainPanel.add(graphPanel);
    }

    //Method to get Total confirmed cases data
    public static long getConfirmedCases(){
        filteredData = GraphFactory.getGraphData(covidData, filterMap);

        LocalDate maxDate = filteredData.stream().map(CovidData::getDate).max(LocalDate::compareTo).get();
        long confirmedCases = filteredData.stream()
                .collect(Collectors.groupingBy(CovidData::getDate)).get(maxDate).stream().collect(Collectors.summingLong(CovidData::getTotalCases));
        return confirmedCases;
    }

    //Method to get Total deaths data
    public static long getDeaths(){
        filteredData = GraphFactory.getGraphData(covidData, filterMap);

        LocalDate maxDate = filteredData.stream().map(CovidData::getDate).max(LocalDate::compareTo).get();
        long deaths = filteredData.stream()
                .collect(Collectors.groupingBy(CovidData::getDate)).get(maxDate).stream().collect(Collectors.summingLong(CovidData::getTotalDeaths));
        return deaths;
    }

    //Method to get %Death/Confirmed percentage data
    public static double getDeathsVsConfirmedPercentage(){
        filteredData = GraphFactory.getGraphData(covidData, filterMap);

        LocalDate maxDate = filteredData.stream().map(CovidData::getDate).max(LocalDate::compareTo).get();
        long confirmedCases = filteredData.stream()
                .collect(Collectors.groupingBy(CovidData::getDate)).get(maxDate).stream().collect(Collectors.summingLong(CovidData::getTotalCases));
        long deaths = filteredData.stream()
                .collect(Collectors.groupingBy(CovidData::getDate)).get(maxDate).stream().collect(Collectors.summingLong(CovidData::getTotalDeaths));
        double deathVsConfirmedPercentage = (double)((deaths * 100) / (double)confirmedCases);
        return deathVsConfirmedPercentage;
    }

    //Method to get Confirmed trend data
    public static JPanel getConfirmedTrend(JPanel confirmedTrendPanel){
        filteredData = GraphFactory.getGraphData(covidData, filterMap);

        confirmedTrendPanel.removeAll();
        confirmedTrendPanel.revalidate();

        confirmedTrendPanel.setLayout(new GridLayout(2, 1));
        confirmedTrendPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblConfirmedTrend = new JLabel("Confirmed Trend",SwingConstants.CENTER);
        lblConfirmedTrend.setFont(new Font("Serif", Font.BOLD, 16));
        lblConfirmedTrend.setBounds(10,10,100,100);
        confirmedTrendPanel.add(lblConfirmedTrend);

        LocalDate maxDate = filteredData.stream().map(CovidData::getDate).max(LocalDate::compareTo).get();
        long confirmedCasesMax = filteredData.stream().filter(item -> item.getDate().compareTo(maxDate) == 0).mapToLong(CovidData::getTotalCases).sum();

        LocalDate secondDate = maxDate.minusDays(1);
        long confirmedCasesSecond = filteredData.stream().filter(item -> item.getDate().compareTo(secondDate) == 0).mapToLong(CovidData::getTotalCases).sum();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new GridLayout(2, 1));
        JLabel maxConfirmedDate = new JLabel();
        maxConfirmedDate.setText(DateTimeFormatter.ofPattern("MMM dd, yyyy").format(maxDate));
        JLabel maxConfirmedLbl = new JLabel();
        maxConfirmedLbl.setText(String.format("%,8d%n",confirmedCasesMax));
        maxPanel.add(maxConfirmedDate);
        maxPanel.add(maxConfirmedLbl);

        JPanel maxPercentagePanel = new JPanel();
        maxPercentagePanel.setLayout(new GridLayout(1, 1));
        JLabel confirmedTrendLbl = new JLabel();
        confirmedTrendLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        confirmedTrendLbl.setForeground(Color.BLUE);
        double confirmedTrendCount = (double)((confirmedCasesMax - confirmedCasesSecond) * 100) / (double) (confirmedCasesSecond);
        DecimalFormat df = new DecimalFormat("#.00");
        confirmedTrendLbl.setText(df.format(confirmedTrendCount) + "%" + (confirmedCasesMax >= confirmedCasesSecond ? (confirmedCasesMax == confirmedCasesSecond) ? " (NO CHANGE)" : " (UP)" : " (DOWN)"));
        maxPercentagePanel.add(confirmedTrendLbl);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(2, 1));
        JLabel secondConfirmedDate = new JLabel();
        secondConfirmedDate.setText(DateTimeFormatter.ofPattern("MMM dd, yyyy").format(secondDate));
        JLabel secondConfirmedLbl = new JLabel();
        secondConfirmedLbl.setText(String.format("%,8d%n", confirmedCasesSecond));
        secondPanel.add(secondConfirmedDate);
        secondPanel.add(secondConfirmedLbl);

        panel.add(maxPanel);
        panel.add(maxPercentagePanel);
        panel.add(secondPanel);

        return panel;
    }

    //Method to get Death trend data
    public static JPanel getDeathTrend(JPanel deathsTrendPanel){
        filteredData = GraphFactory.getGraphData(covidData, filterMap);

        deathsTrendPanel.removeAll();
        deathsTrendPanel.revalidate();
        deathsTrendPanel.setLayout(new GridLayout(2, 1));
        deathsTrendPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblDeathsTrend = new JLabel("Death Trend",SwingConstants.CENTER);
        lblDeathsTrend.setFont(new Font("Serif", Font.BOLD, 16));
        lblDeathsTrend.setBounds(10,10,100,100);
        deathsTrendPanel.add(lblDeathsTrend);

        LocalDate maxDate = filteredData.stream().map(CovidData::getDate).max(LocalDate::compareTo).get();
        long deathsMax = filteredData.stream().filter(item -> item.getDate().compareTo(maxDate) == 0).mapToLong(CovidData::getTotalDeaths).sum();

        LocalDate secondDate = maxDate.minusDays(1);
        long deathsSecond = filteredData.stream().filter(item -> item.getDate().compareTo(secondDate) == 0).mapToLong(CovidData::getTotalDeaths).sum();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new GridLayout(2, 1));
        JLabel maxDeathDate = new JLabel();
        maxDeathDate.setBounds(10,10,100,100);
        maxDeathDate.setText(DateTimeFormatter.ofPattern("MMM dd, yyyy").format(maxDate));
        JLabel maxDeathLbl = new JLabel();
        maxDeathLbl.setBounds(10,10,100,100);
        maxDeathLbl.setText(String.format("%,8d%n",deathsMax));
        maxPanel.add(maxDeathDate);
        maxPanel.add(maxDeathLbl);

        JPanel maxPercentagePanel = new JPanel();
        maxPercentagePanel.setLayout(new GridLayout(1, 1));
        JLabel deathTrendLbl = new JLabel();
        deathTrendLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        deathTrendLbl.setForeground(Color.RED);
        double deathTrendCount = (double)((deathsMax - deathsSecond) * 100) / (double) (deathsSecond);
        DecimalFormat df = new DecimalFormat("#.00");
        deathTrendLbl.setText(df.format(deathTrendCount) + "%" + (deathsMax >= deathsSecond ? (deathsMax == deathsSecond) ? " (NO CHANGE)" :  " (UP)" : " (DOWN)"));
        maxPercentagePanel.add(deathTrendLbl);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(2, 1));
        JLabel secondDeathDate = new JLabel();
        secondDeathDate.setBounds(10,10,100,100);
        secondDeathDate.setText(DateTimeFormatter.ofPattern("MMM dd, yyyy").format(secondDate));
        JLabel secondDeathLbl = new JLabel();
        secondDeathLbl.setBounds(10,10,100,100);
        secondDeathLbl.setText(String.format("%,8d%n", deathsSecond));
        secondPanel.add(secondDeathDate);
        secondPanel.add(secondDeathLbl);

        panel.add(maxPanel);
        panel.add(maxPercentagePanel);
        panel.add(secondPanel);

        return panel;
    }
}
