package com.covidsurvivals.coronabeer;

import java.util.Collection;
import java.util.Map;

/*
    Interface class for Graph
 */
interface IGraph {
    public Collection<CovidData> getGraphData(Collection<CovidData> allData, Map<String, String> filterMap);
}
