package com.covidsurvivals.coronabeer;

import java.util.Collection;
import java.util.Collections;
import java.time.*;

/*
    Business Class for Covid Data that was downloaded from Data Lake
 */

public class CovidData {

    // ----------------------------------- PRIVATE PROPERTIES -----------------------------------
    private LocalDate date;
    private int stateId;
    private String stateName;
    private long totalCases;
    private long totalDeaths;

    // ----------------------------------- CONSTRUCTORS -----------------------------------
    public CovidData(){

    }

    public CovidData(LocalDate date) {
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

    public CovidData(LocalDate date, int stateId) {
        this(date);
        setStateId(stateId);
    }

    public CovidData(LocalDate date, int stateId, String stateName) {
        this(date, stateId);
        setStateName(stateName);
    }

    public CovidData(LocalDate date, int stateId, String stateName, long totalCases) {
        this(date, stateId, stateName);
        setTotalCases(totalCases);
    }

    public CovidData(LocalDate date, long totalCases, long totalDeaths) {
        this.date = date;
        this.totalCases = totalCases;
        this.totalDeaths = totalDeaths;
    }

    public CovidData(LocalDate date, int stateId, String stateName, long totalCases, long totalDeaths) {
        this(date, stateId, stateName, totalCases);
        this.totalDeaths = totalDeaths;
    }


    // ----------------------------------- PUBLIC METHODS -----------------------------------
    // Read-only view of the entire Inventory.
    public static Collection<CovidData> getAll(Collection<CovidData> data){
        return Collections.unmodifiableCollection(data);
    }


    // ----------------------------------- GETTER - SETTER METHODS -----------------------------------
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
