package com.covidsurvivals.util;

import com.covidsurvivals.coronabeer.CovidData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/*
    A Utility class for Import Data from CSV file to Collection of CovidData class.
 */

public class ImportData {

    // Static method for import data from csv to collection
    public static Collection<CovidData> importFromCSVToCollection(){
        Collection<CovidData> covidData = new ArrayList<>();
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\us_states.csv"));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                LocalDate date = LocalDate.parse(data[0]);
                String stateName = data[1];
                int stateId = Integer.parseInt(data[2]);
                long totalCases = Long.parseLong(data[3]);
                long totalDeaths = Long.parseLong(data[4]);
                CovidData item = new CovidData(date,stateId, stateName, totalCases, totalDeaths);
                covidData.add(item);
            }
            lineReader.close();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
        return covidData;
    }
}
