package com.covidsurvivals.coronabeer;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

/*
    Business Class for Graph that take Filters from user and draw the graph
 */

public class Graph implements IGraph {

    // ----------------------------------- PRIVATE PROPERTIES -----------------------------------
    private GraphType graphType;
    private int stateId;
    private Date startDate;
    private Date endDate;


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

    public Graph(GraphType graphType, Date startDate, Date endDate) {
        this(graphType);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public Graph(GraphType graphType, int stateId, Date startDate, Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
                        .filter(item -> item.getDate().compareTo(Date.valueOf(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(Date.valueOf(filterMap.get("endDate"))) <= 0)
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
                        .filter(item -> item.getDate().compareTo(Date.valueOf(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(Date.valueOf(filterMap.get("endDate"))) <= 0)
                        .collect(Collectors.toList());
            }
        }

        Collection<CovidData> result = new ArrayList<>();
        Map<Date, List<CovidData>> filteredMap = filteredData.stream()
                .collect(Collectors.groupingBy(CovidData::getDate));


        for (Date date : filteredMap.keySet()) {
            Date caseDate = Date.valueOf(date.toString());
            long cases = filteredMap.get(date).stream().collect(Collectors.summingLong(CovidData::getTotalCases));
            long deaths = filteredMap.get(date).stream().collect(Collectors.summingLong(CovidData::getTotalDeaths));
            CovidData item = new CovidData(caseDate,1,"State", cases, deaths);
            result.add(item);
        }

        return result.stream().sorted(Comparator.comparing(CovidData::getDate)).collect(Collectors.toList());

//        Stream<CovidData> stream = allData.stream();
//        if(filterMap.get("stateId") != "0"){
//            stream = stream.filter(item -> item.getStateId() == Integer.parseInt(filterMap.get("stateId")));
//        }
//        if(filterMap.get("startDate") != null && filterMap.get("endDate") != null){
//            stream = stream.filter(item -> item.getDate().compareTo(Date.valueOf(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(Date.valueOf(filterMap.get("endDate"))) <= 0);
//        }
//        List<CovidData> result = stream.collect(Collectors.toList());

//        List<CovidData> result = allData.stream()
//                .filter(filterMap.get("stateId") != "0" ? item -> item.getStateId() == Integer.parseInt(filterMap.get("stateId")) : item -> true)
//                .filter(item -> filterMap.get("startDate") != null && filterMap.get("endDate") != null ? item.getDate().compareTo(Date.valueOf(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(Date.valueOf(filterMap.get("endDate"))) <= 0 : false)
//                .collect(Collectors.toList());
    }
}