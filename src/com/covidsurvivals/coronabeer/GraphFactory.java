package com.covidsurvivals.coronabeer;

import org.jfree.chart.JFreeChart;
import java.util.Collection;
import java.util.Map;

/*
    * Factory class for Graph
    * Called from client for one level Encapsulation so do not have to create instance of Graph in client class
    * Takes GraphType and decide which Graph type class to call and draw graph.
 */
public class GraphFactory {
    private GraphFactory(){
    }

    // Method for create Graph called from client and decide which subclass to call to draw graph
    public static JFreeChart createGraph(Collection<CovidData> allData, Map<String, String> filterMap, boolean isConfirmed) {
        JFreeChart graph = null;
        Collection<CovidData> data = getGraphData(allData,filterMap);

        String type = filterMap.get("graphType");

        if (type.equals(GraphType.BAR.toString())) {
            BarGraph barGraph = new BarGraph();
            graph = barGraph.drawGraph(data,isConfirmed);
        }
        else if(type.equals(GraphType.LINE.toString())) {
            LineGraph lineGraph = new LineGraph();
            graph = lineGraph.drawGraph(data,isConfirmed);
        }
        return graph;
    }

    //Method that creates Graph class instance and get filtered data for graph
    public static Collection<CovidData> getGraphData(Collection<CovidData> allData, Map<String, String> filterMap){
        Collection<CovidData> data = new Graph().getGraphData(allData, filterMap);
        return  data;
    }
}
