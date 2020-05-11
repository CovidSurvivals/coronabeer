package com.covidsurvivals.client;

import com.covidsurvivals.coronabeer.CovidData;
import com.covidsurvivals.util.HttpDownloadUtility;
import com.covidsurvivals.util.ImportData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/*
    Client Class of Dashboard
 */

public class Dashboard {
    private static Collection<CovidData> covidData = new ArrayList<>();

    public static void main(String[] args) {
        // One time import data from CSV to Collection when program starts
        importDataFromCSVToCollection();

        // Display data from Collection of Covid Data
        displayDataFromCollection();
    }

    //Method for Import data to SQL database from downloaded CSV file
    public static void importDataFromCSVToCollection() {
        try {
            HttpDownloadUtility.downloadFile();
            covidData = CovidData.getAll(ImportData.importFromCSVToCollection());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayDataFromCollection() {
        System.out.println("Data from SQL\n");
        covidData.forEach(System.out :: println);
    }
}
