package com.covidsurvivals.coronabeer;

import org.jfree.chart.JFreeChart;
import java.util.Collection;
import java.util.Map;

public class GraphFactory {
    private GraphFactory(){
    }

    public static JFreeChart createGraph(Collection<CovidData> allData, Map<String, String> filterMap) {
        JFreeChart graph = null;
        Collection<CovidData> data = new Graph().getGraphData(allData, filterMap);

        String type = filterMap.get("graphType");

        if (type.equals(GraphType.BAR.toString())) {
            BarGraph barGraph = new BarGraph();
            graph = barGraph.drawGraph(data);
        }
        else if(type.equals(GraphType.LINE.toString())) {
            LineGraph lineGraph = new LineGraph();
            graph = lineGraph.drawGraph(data);
        }
        return graph;
    }
}
