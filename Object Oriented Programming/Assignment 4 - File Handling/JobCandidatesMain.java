package com.bham.pij.assignments.candidates;

import java.io.IOException;

public class JobCandidatesMain {

    public static void main(String[] args) {
        CleaningUp CU = new CleaningUp();
        CandidatesToInterview CTI = new CandidatesToInterview();
        try{
            CU.cleanUpFile();
            CTI.findCandidates();
            CTI.candidatesWithExperience();
            CTI.createCSVFile();
            CTI.createReport();
        }
        catch(IOException e){System.out.println("Error");}
    }

}
