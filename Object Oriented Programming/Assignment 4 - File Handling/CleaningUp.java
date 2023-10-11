package com.bham.pij.assignments.candidates;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class CleaningUp {

    public void cleanUpFile() throws java.io.IOException {

        ArrayList<String> cleanCV = new ArrayList<String>();
        ArrayList<String> info = new ArrayList<String>();

        File file = new File("dirtycv.txt");
        PrintWriter out = new PrintWriter("cleancv.txt");

        int number = 1;

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (sc.hasNextLine()) //while reading the file
                {
                    String line = sc.nextLine();
                    if(line.equalsIgnoreCase("End") == false)
                    {
                        if(line.contains("Surname"))
                        {
                            String name = line.substring(8) + String.format("%04d", number);
                            info.add(name);
                        }

                        if(line.contains("Qualification"))
                        {
                            String qual = line.substring(14);
                            info.add(qual);
                        }

                        if(line.contains("Position"))
                        {
                            String pos = line.substring(9);
                            info.add(pos);
                        }

                        if(line.contains("Experience"))
                        {
                            String exp = line.substring(11);
                            info.add(exp);
                        }

                        if(line.contains("eMail"))
                        {
                            String mail = line.substring(6);
                            info.add(mail);
                        }
                    }
                    else {
                        String aa = String.join(",", info) + ",";
                        cleanCV.add(aa);
                        number = number+1;
                        info.clear();
                    }

            }
        } catch (IOException e) {
            System.out.println("IOException error");

        }
        int i;
        for(i=0; i<cleanCV.size();i++)
        {
            out.println(cleanCV.get(i));
        }
        out.close();
    }



}
