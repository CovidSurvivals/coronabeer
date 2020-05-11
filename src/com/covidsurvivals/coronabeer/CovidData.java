package com.covidsurvivals.coronabeer;

import java.util.Collection;
import java.util.Collections;
import java.sql.Date;

/*
    Business Class for Covid Data that was downloaded from Data Lake
 */

public class CovidData {

    // ----------------------------------- PRIVATE PROPERTIES -----------------------------------
    private Date date;
    private int stateId;
    private String stateName;
    private long totalCases;
    private long totalDeaths;

    // ----------------------------------- CONSTRUCTORS -----------------------------------
    public CovidData(){

    }

    public CovidData(Date date) {
        setDate(date);
    }

    public CovidData(int stateId) {
        setStateId(stateId);
    }

    public CovidData(String stateName) {
        setStateName(stateName);
    }

    public CovidData(long totalCases) {
        setTotalCases(totalCases);
    }

    public CovidData(Date date, int stateId) {
        this(date);
        setStateId(stateId);
    }

    public CovidData(Date date, int stateId, String stateName) {
        this(date, stateId);
        setStateName(stateName);
    }

    public CovidData(Date date, int stateId, String stateName, long totalCases) {
        this(date, stateId, stateName);
        setTotalCases(totalCases);
    }

    public CovidData(Date date, int stateId, String stateName, long totalCases, long totalDeaths) {
        this(date, stateId, stateName, totalCases);
        this.totalDeaths = totalDeaths;
    }


    // ----------------------------------- PUBLIC METHODS -----------------------------------
    // Read-only view of the entire Inventory.
    public static Collection<CovidData> getAll(Collection<CovidData> data){
        return Collections.unmodifiableCollection(data);
    }


    // ----------------------------------- GETTER - SETTER METHODS -----------------------------------
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(long totalCases) {
        this.totalCases = totalCases;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }


    // ----------------------------------- TOSTRING() METHOD -----------------------------------
    @Override
    public String toString() {
        return "Covid Data: Date: " + getDate() + " , State: " + getStateName() + " , TotalCases: " + getTotalCases() + " , TotalDeaths: " + getTotalDeaths();
    }
}
