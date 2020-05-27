/*
 * NAME: Linghang Kong
 * PID: A16127732
 */

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Line Counter program
 *
 * @author Linghang Kong
 * @since 05/26/2020
 */
public class LineCounter {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;


    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    public static void main(String[] args){

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable

        for (int i = 0; i < numArgs; i++) {
            tableList[i] = new HashTable();
            try{
                File file = new File(args[i]);
                Scanner sc = new Scanner(file);
                while(sc.hasNextLine()){
                    tableList[i].insert(sc.nextLine());
                }
                sc.close();
            }catch(FileNotFoundException exception){
                exception.getMessage();
            }
        }

        int total = 0;
        int similarity = 0;
        // Find similarities across files
        for (int i = 0; i < numArgs; i++) {
            printFileName(args[i]);
            File indFile = new File(args[i]);
            for (int j = 0; j<numArgs;j++){
                if (j != i){
                    try{
                        Scanner sc = new Scanner(indFile);
                        while(sc.hasNextLine()){
                           total += 1;
                           String content = sc.nextLine();
                           if (tableList[j].lookup(content)==true){
                               similarity+=1;
                           }
                        }
                        sc.close();
                        double samePercent = (similarity/(double)total)*100;
                        int percentage = (int)samePercent;
                        printStatistics(args[j],percentage);
                        total = 0;
                        similarity = 0;
                    } catch (FileNotFoundException exception){
                        exception.getMessage();
                    }
                }
            }
        }
    }

}