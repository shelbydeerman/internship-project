package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Project {
    public static void main(String[] args) {
        File file = new File(args[0]);
        String[][] meteorArray = new String[2000][11]; // fix to include bigger files
        int meteorNum = 0;

        try (Scanner scanner = new Scanner(file)) {
            for (int i = 0; scanner.hasNext(); i++) {

                // scan the data into a 2d array and count the number of meteorites
                String line = scanner.nextLine();
                String[] array = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int j = 0; j < array.length; j++) {
                    meteorArray[i][j] = array[j];
                }
                meteorNum++;
            }
            meteorNum = meteorNum - 1;

            // iterate through the array to get the sum of the masses
            double massSum = 0;
            int meteorWithMassNum = 0;
            for (int i = 1; i < meteorNum + 1; i++) {
                if (!meteorArray[i][4].equals("")) {
                    double num = Double.parseDouble(meteorArray[i][4]);
                    massSum += num;
                    meteorWithMassNum++;
                }
            }
            // calculate average of the masses and then convert to pounds
            double massAverageGrams = massSum / meteorWithMassNum;
            double massAveragePounds = massAverageGrams / 453.59237;

            // count the dates that repeat and save the number of repeats in "repeatDates"
            int[] repeatDates = new int[meteorArray.length];
            for (int i = 1; i < meteorNum + 1; i++) {
                for (int j = 0; j < meteorNum + 1; j++) {
                    if (meteorArray[i][6].equals(meteorArray[j][6]) &&
                            !meteorArray[j][6].equals("") && i != j) {
                        repeatDates[i]++;
                    }
                }
            }
            // find out which date repeats the most and assign it to "mode"
            // assign the year which has the most meteorites in it to "year"
            int mode = 0;
            String year = "";
            for (int i = 0; i < repeatDates.length; i++) {
                if (repeatDates[i] > mode) {
                    mode = repeatDates[i];
                    year = meteorArray[i][6].substring(6, 10);
                }
            }

            System.out.println("Total number of meteorites: " + meteorNum);
            System.out.println("Average weight (mass) of meteorites: " + massAveragePounds + " lbs");
            System.out.println("Year with the most meteorites: " + year);

        } catch (FileNotFoundException e) {
            System.out.println("No file found.");
        }

    }
}
