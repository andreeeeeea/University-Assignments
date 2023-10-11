package com.bham.pij.assignments.candidates;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CandidatesToInterview {

    ArrayList<String> addToFile;
    ArrayList<List<String>> candits;
    FileWriter fw;



    BufferedReader br;
    ArrayList<String> name;
    ArrayList<String> qual;
    ArrayList<String> pos1;
    ArrayList<String> exp1;
    ArrayList<String> pos2;
    ArrayList<String> exp2;
    ArrayList<String> mail;


    public void findCandidates() throws java.io.IOException
    {
        String[] keywordsDegree = {"Degree in Computer Science", "Masters in Computer Science"};
        String[] keywordsExperience = {"Data Analyst", "Programmer", "Computer programmer", "Operator"};
        this.addToFile = new ArrayList<String>();

        File file = new File("cleancv.txt");
        PrintWriter out = new PrintWriter("to-interview.txt");
        candits = new ArrayList<>();


        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (sc.hasNextLine()) //while reading the file
            {
                int ok = 1;
                String line = sc.nextLine(); //the line that's being read
                if ((line.contains(keywordsDegree[0]) && line.contains(keywordsDegree[1]))) ok = 0;
                if (ok == 1 && (line.contains(keywordsDegree[0]) || line.contains(keywordsDegree[1])) &&
                        (line.contains(keywordsExperience[0]) || line.contains(keywordsExperience[1]) || line.contains(keywordsExperience[2]) || line.contains(keywordsExperience[3]))) {
                    String[] str = line.split(",");
                    List<String> candInfo = Arrays.asList(str);
                    candits.add(candInfo);
                    String newline = line.replace(",", " ");
                    newline.trim();
                    addToFile.add(newline);
                }
            }
        }catch (IOException e) {
            System.out.println("IOException in findCandidates()");
        }
        int i;
        for(i=0; i<addToFile.size(); i++)
        {
            out.println(addToFile.get(i));
        }
        out.close();
    }

    public static boolean isNumber(String s) //check if string s is a number
    {
        try { int nr = Integer.parseInt(s);}
        catch (NumberFormatException nfe) {return false;}
        return true;
    }

    public void candidatesWithExperience() throws IOException
    {
        ArrayList<String> withExp = new ArrayList<String>();
        File file = new File("to-interview.txt");
        PrintWriter rn = new PrintWriter("to-interview-experience.txt");


        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (sc.hasNextLine()) //while reading the file
            {
                String line = sc.nextLine(); //the line that's being read
                String[] splitLine = line.split(" ");
                int i;
                int ok = 1;
                for(i=0; i<splitLine.length; i++)
                {
                    if(isNumber(splitLine[i]) && ok == 1)
                    {
                        ok = 0;
                        int n = Integer.parseInt(splitLine[i]);
                        if(n>5) {
                            String ar = splitLine[0] + " " + splitLine[i];
                            withExp.add(ar);
                        }
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("IOException in candidatesWithExperience()");
        }
        int i;
        for(i=0; i<withExp.size(); i++)
        {
            rn.println(withExp.get(i));
        }
        rn.close();
    }

    public void createCSVFile() throws IOException
    {
        try {
            fw = new FileWriter("to-interview-table-format.csv");
            fw.append("Identifier");
            fw.append(",");
            fw.append("Qualification");
            fw.append(",");
            fw.append("Position1");
            fw.append(",");
            fw.append("Experience1");
            fw.append(",");
            fw.append("Position2");
            fw.append(",");
            fw.append("Experience2");
            fw.append(",");
            fw.append("eMail");
            fw.append("\n");

            int i=0;
            for(i=0; i<candits.size(); i++)
            {
                if(candits.get(i).size() == 7) for (String s : candits.get(i)) {fw.append(s);fw.append(",");}
                else if(candits.get(i).size() == 5)
                {
                    int j;
                    for(j=0; j<4; j++)
                    {fw.append(candits.get(i).get(j));fw.append(",");}
                    fw.append(",");
                    fw.append(",");
                    fw.append(candits.get(i).get(candits.get(i).size()-1));
                    fw.append(",");
                }
                fw.append("\n");
            }

        }
        catch (IOException e){System.out.println("IOException in createCSVFile");}
        finally {
            try{
                fw.flush(); fw.close();
            }
            catch (IOException e){System.out.println("IOException in createCSVFile #2");}
        }
    }

    public void createReport() throws IOException //works
    {
        this.name = new ArrayList<>();
        this.qual = new ArrayList<>();
        this.pos1 = new ArrayList<>();
        this.exp1 = new ArrayList<>();
        this.pos2 = new ArrayList<>();
        this.exp2 = new ArrayList<>();
        this.mail = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("to-interview-table-format.csv")); //reading the csv file
            String line; //where the line that's currently being read is stored
            while((line = br.readLine()) != null) //while there's a line to read/ while reading the line
            {
                String[] words = line.split(","); //where the words/names/occupation/experience/etc. will be stored as a string array
                name.add(words[0]); //add to array for names
                qual.add(words[1]); //add to array for qualifications
                pos1.add(words[2]); //add to array for position 1
                exp1.add(words[3]); //add to array for experience 1
                if(words.length == 7) //if there is a position 2 and experience 2
                {
                    pos2.add(words[4]); //add to array for position 2
                    exp2.add(words[5]); //add to array for experience 2
                    mail.add(words[6]); //add to array for email
                }
                else if(words.length == 5) //if there isn't
                {
                    pos2.add(" "); //add empty space to array for position 2
                    exp2.add(" "); //add empty space to array for experience 2
                    mail.add(words[4]); //add to array for email
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error in createReport");
        }
        pos1.set(0, "Position");
        exp1.set(0, "Experience");
        int i;
        for(i=0; i<name.size(); i++)
        {
            System.out.println(String.format("%-20s", name.get(i))  + String.format("%-30s", qual.get(i)) + String.format("%-20s", pos1.get(i)) + String.format("%-20s", exp1.get(i)) + String.format("%-15s", mail.get(i)));
        }
    }


}
