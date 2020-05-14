package com.covidsurvivals.coronabeer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/*
    Business Class for Graph that take Filters from user and draw the graph
 */

class Graph implements IGraph {

    // ----------------------------------- PRIVATE PROPERTIES -----------------------------------
    private GraphType graphType;
    private int stateId;
    private LocalDate startDate;
    private LocalDate endDate;


    // ----------------------------------- CONSTRUCTORS -----------------------------------
    public Graph(){

    }

    public Graph(GraphType graphType) {
        setGraphType(graphType);
    }

    public Graph(int stateId) {
        setStateId(stateId);
    }

    public Graph(GraphType graphType, int stateId) {
        this(graphType);
        setStateId(stateId);
    }

    public Graph(GraphType graphType, LocalDate startDate, LocalDate endDate) {
        this(graphType);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public Graph(GraphType graphType, int stateId, LocalDate startDate, LocalDate endDate) {
        this(graphType, startDate, endDate);
        setStateId(stateId);
    }


    // ----------------------------------- GETTER - SETTER METHODS -----------------------------------
    public GraphType getGraphType() {
        return graphType;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    // ----------------------------------- PUBLIC METHODS -----------------------------------
    @Override
    public Collection<CovidData> getGraphData(Collection<CovidData> allData, Map<String, String> filterMap) {
        Collection<CovidData> filteredData = allData;

        if(Integer.parseInt(filterMap.get("stateId")) != 0){
            if(filterMap.get("startDate") != null && filterMap.get("endDate") != null){
                filteredData = allData.stream()
                        .filter(item -> item.getStateId() == Integer.parseInt(filterMap.get("stateId")))
                        .filter(item -> item.getDate().compareTo(LocalDate.parse(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(LocalDate.parse(filterMap.get("endDate"))) <= 0)
                        .collect(Collectors.toList());

            }
            else
            {
                filteredData = allData.stream()
                        .filter(item -> item.getStateId() == Integer.parseInt(filterMap.get("stateId")))
                        .collect(Collectors.toList());
            }
        } else {
            if(filterMap.get("startDate") != null && filterMap.get("endDate") != null){
                filteredData = allData.stream()
                        .filter(item -> item.getDate().compareTo(LocalDate.parse(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(LocalDate.parse(filterMap.get("endDate"))) <= 0)
                        .collect(Collectors.toList());
            }
        }

        Collection<CovidData> result = new ArrayList<>();
        Map<LocalDate, List<CovidData>> filteredMap = filteredData.stream()
                .collect(Collectors.groupingBy(CovidData::getDate));


        for (LocalDate date : filteredMap.keySet()) {
            long cases = filteredMap.get(date).stream().collect(Collectors.summingLong(CovidData::getTotalCases));
            long deaths = filteredMap.get(date).stream().collect(Collectors.summingLong(CovidData::getTotalDeaths));
            CovidData item = new CovidData(date,1,"State", cases, deaths);
            result.add(item);
        }

        return result.stream().sorted(Comparator.comparing(CovidData::getDate)).collect(Collectors.toList());
    }
}