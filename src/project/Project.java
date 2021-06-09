/*
Shelby Deerman
6/7/21
Meteorite Project
 */

package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Project {
    public static void main(String[] args) {

        File fs = new File(args[0]);
        int rowSize = 0;

        // find the number of rows in the file by scanning each line
        // and incrementing 'rowSize' each time
        try (Scanner sc = new Scanner(fs)) {

            while (sc.hasNext()) {
                String temp = sc.nextLine();
                rowSize++;
            }

            // the number of meteors will be 1 less than the number of rows
            int meteorNum = rowSize - 1;

            // reopen the file and create an array big enough for the data
            File file = new File(args[0]);
            String[][] meteorArray = new String[rowSize][10];

            // scan the data into the 2d array 'meteorArray' by scanning in
            // each line and separating the data where the commas are except
            // when there are quotes around the data
            try (Scanner scanner = new Scanner(file)) {
                for (int i = 0; scanner.hasNext(); i++) {
                    String line = scanner.nextLine();
                    String[] lineArray = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    for (int j = 0; j < lineArray.length; j++) {
                        meteorArray[i][j] = lineArray[j];
                    }
                }

                double massSum = 0;
                int meteorWithMassNum = 0;
                int[] repeatDates = new int[meteorArray.length];

                // iterate through the array to get the sum of the masses of all the meteors,
                // assign the sum to 'massSum', and count the number of meteors that have
                // given masses by incrementing 'meteorWithMassNum'
                // count the dates that repeat and save the number of repeats in 'repeatDates'
                for (int i = 1; i < rowSize; i++) {
                    if (!meteorArray[i][4].equals("")) {
                        double num = Double.parseDouble(meteorArray[i][4]);
                        massSum += num;
                        meteorWithMassNum++;
                    }
                    for (int j = 0; j < rowSize; j++) {
                        if (meteorArray[i][6] != null && meteorArray[j][6] != null
                        && i != j && meteorArray[i][6].equals(meteorArray[j][6])) {
                            repeatDates[i]++;
                        }
                    }
                }

                // calculate average of the masses and then convert from grams to pounds
                double massAverageGrams = massSum / meteorWithMassNum;
                double massAveragePounds = massAverageGrams / 453.59237;

                // find out which date repeats the most and assign it to 'mode'
                // assign the year which has the most meteorites in it to 'year'
                int mode = 0;
                String year = "";
                for (int i = 1; i < repeatDates.length; i++) {
                    if (repeatDates[i] > mode) {
                        mode = repeatDates[i];
                        if (meteorArray[i][6] != null) {
                            year = meteorArray[i][6].substring(6, 10);
                        }
                    }
                }

                // print out all the data that was found
                // ('meteorNum', 'massAveragePounds', 'year')
                System.out.println("Total number of meteorites: " + meteorNum);
                System.out.println("Average weight (mass) of meteorites: " + massAveragePounds + " lbs");
                System.out.println("Year with the most meteorites: " + year);

            } catch (FileNotFoundException e) {
                System.out.println("No file found.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found.");
        }
    }
}
