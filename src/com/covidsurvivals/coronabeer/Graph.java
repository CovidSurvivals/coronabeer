package com.covidsurvivals.coronabeer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.Map;
import java.util.List;
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
        boolean isStateSelected = false;
        boolean isDatesSelected = false;
        if(filterMap.get("stateId") != null){
            isStateSelected = true;
        }
        if(filterMap.get("startDate") != null && filterMap.get("endDate") != null){
            isDatesSelected = true;
        }

//        Collection<CovidData> result = allData.stream()
//                .filter(item -> isStateSelected == true ? item.getStateId() == Integer.parseInt(filterMap.get("stateId"))
//                        && isDatesSelected ? item.getDate().after(Date.valueOf(filterMap.get("startDate"))) && item.getDate().before(Date.valueOf(filterMap.get("endDate"))))
//                .collect(Collectors.toList());

        Collection<CovidData> result = allData.stream()
                .filter(item -> filterMap.get("stateId") != null ? item.getStateId() == Integer.parseInt(filterMap.get("stateId")) : false)
                .filter(item -> filterMap.get("startDate") != null && filterMap.get("endDate") != null ? item.getDate().compareTo(Date.valueOf(filterMap.get("startDate"))) >= 0 && item.getDate().compareTo(Date.valueOf(filterMap.get("endDate"))) <= 0 : false)
                .collect(Collectors.toList());

        return result;
    }

}
