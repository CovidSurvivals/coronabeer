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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

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

        JPanel filterPanel = new JPanel();
        JPanel graphPanel = new JPanel();

        displayFilters(mainPanel,filterPanel, graphPanel);
        displayGraph(mainPanel,graphPanel);

        // Add both Panels to Frame
        frame.getContentPane().add(mainPanel);
        mainPanel.setLayout(new GridLayout(2, 1));
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void displayFilters(JPanel mainPanel, JPanel filterPanel, JPanel graphPanel){

        filterPanel.setLayout(new GridLayout(1, 2));
        filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Filter Layout"));

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

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
        lblConfirmedCount.setText("1,336,601"); //TODO
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
        lblDeathsCount.setText("79,694"); //TODO
        deathsPanel.add(lblDeathsCount);

        confirmedAndDeathsPanel.add(confirmedPanel);
        confirmedAndDeathsPanel.add(deathsPanel);

        // %Death/Confirmed Panel
        JPanel deathVsConfirmedPanel = new JPanel();
        deathVsConfirmedPanel.setLayout(new GridLayout(1, 2));
        deathVsConfirmedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        JPanel deathVsCConfirmedlblPanel = new JPanel();
        deathVsCConfirmedlblPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblDeathVsConfirmed = new JLabel("%Death/Confirmed: ");
        lblDeathVsConfirmed.setFont(new Font("Serif", Font.BOLD, 16));
        lblDeathVsConfirmed.setBounds(10,10,100,100);
        deathVsCConfirmedlblPanel.add(lblDeathVsConfirmed);

        JPanel deathVsConfirmedCountPanel = new JPanel();
        deathVsConfirmedCountPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblDeathVsConfirmedCount = new JLabel();
        lblDeathVsConfirmedCount.setFont(new Font("Serif", Font.BOLD, 18));
        lblDeathVsConfirmedCount.setHorizontalTextPosition(SwingConstants.LEFT);
        //lblDeathVsConfirmedCount.setBounds(10,10,100,100);
        lblDeathVsConfirmedCount.setForeground(Color.RED);
        lblDeathVsConfirmedCount.setText("5.96" + "%"); // TODO
        deathVsConfirmedCountPanel.add(lblDeathVsConfirmedCount);

        deathVsConfirmedPanel.add(deathVsCConfirmedlblPanel);
        deathVsConfirmedPanel.add(deathVsConfirmedCountPanel);

        // Trend Panel
        JPanel trendPanel = new JPanel();
        trendPanel.setLayout(new GridLayout(1, 2));
        trendPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));

        JPanel confirmedTrendPanel = new JPanel();
        confirmedTrendPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblConfirmedTrend = new JLabel("Confirmed Trend");
        lblConfirmedTrend.setFont(new Font("Serif", Font.BOLD, 16));
        lblConfirmedTrend.setBounds(10,10,100,100);
        confirmedTrendPanel.add(lblConfirmedTrend);



        JPanel deathsTrendPanel = new JPanel();
        deathsTrendPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        JLabel lblDeathsTrend = new JLabel("Death Trend");
        lblDeathsTrend.setFont(new Font("Serif", Font.BOLD, 16));
        lblDeathsTrend.setBounds(10,10,100,100);
        deathsTrendPanel.add(lblDeathsTrend);

        trendPanel.add(confirmedTrendPanel);
        trendPanel.add(deathsTrendPanel);

        leftPanel.add(labelPanel);
        leftPanel.add(filterInputPanel);
        leftPanel.add(confirmedAndDeathsPanel);
        leftPanel.add(deathVsConfirmedPanel);
        leftPanel.add(trendPanel);

        filterPanel.add(leftPanel);
        filterPanel.add(rightPanel);
        mainPanel.add(filterPanel);
    }

    public static void displayGraph(JPanel mainPanel, JPanel graphPanel){
        JFreeChart graph = GraphFactory.createGraph(covidData,filterMap);

        graphPanel.setLayout(new BorderLayout());
        graphPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Graph Layout"));

        graphPanel.removeAll();
        graphPanel.revalidate();

        // Create ChartPanel and add graph to it
        ChartPanel chartPanel = new ChartPanel( graph );
        chartPanel.setMouseZoomable(true, false);
        chartPanel.setVisible(true);
        graphPanel.add(chartPanel);
        mainPanel.add(graphPanel);
    }
}
