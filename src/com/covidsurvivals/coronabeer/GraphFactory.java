package com.covidsurvivals.coronabeer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.resources.JFreeChartResources;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GraphFactory {
    private GraphFactory(){
    }

    public static JFreeChart createGraph(Collection<CovidData> allData, Map<String, String> filterMap) {
        JFreeChart graph = null;
        Collection<CovidData> data = new Graph().getGraphData(allData, filterMap);
        data.forEach(System.out::println);

        GraphType type = GraphType.valueOf(filterMap.get("graphType"));

        if (type.equals(GraphType.BAR)) {
            System.out.println("BAR");
            // TODO: BarGraph drawgraph method call
            BarGraph barGraph = new BarGraph();
            graph = barGraph.drawGraph(data);
        }
        else if(type.equals(GraphType.LINE)) {
            System.out.println("LINE");
            // TODO: LineGraph drawgraph method call
            LineGraph lineGraph = new LineGraph();
            graph = lineGraph.drawGraph(data);
        }

        return graph;
    }
}
