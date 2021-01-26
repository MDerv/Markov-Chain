/* Dhruv Sharma
10/26/2020
10/27/2020
This file defines the MarkovChain class.
*/
import java.io.*;
import java.util.*;

public class MarkovChain {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("What is the name of the training file?");
        String trainingFileName = s.nextLine();
        System.out.println("How many words would you like to generate?");
        int numWords = s.nextInt();
        s.nextLine();

        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/" + trainingFileName));
        BufferedReader readTests = new BufferedReader(reader); //allows the training file to be read

        FileWriter writer = new FileWriter("src/Dictionary.json", true);
        BufferedWriter writeLog = new BufferedWriter(writer);


        String st = readTests.readLine();
        System.out.println(st);


        writeLog.write(st + " = " + "\n");
        writeLog.close();

    }
}
