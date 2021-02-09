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

        FileWriter writer = new FileWriter("src/Dictionary.txt", true);
        BufferedWriter writeDict = new BufferedWriter(writer);

        String readLine = readTests.readLine(); //reads line from file
        System.out.println(readLine);

        ArrayList<String> words = new ArrayList<String>();
        HashMap<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
        String[] sentencePeriod = readLine.trim().split("\\.|\\!|\\?|\\;"); //splits sentences
//        for(String lol : sentencePeriod) {
//            System.out.println(lol);
//        }
//        if(sentencePeriod.length == 0) {
//            System.out.println("bruh");
//        }

        for(int i = 0; i < sentencePeriod.length; i++) {
            String[] tempArray = sentencePeriod[i].trim().split("\\s+");
            for(int j = 0; j < tempArray.length; j++) {
                words.add(tempArray[j]);
            }
        }

        for(int i = 0; i < words.size()-1; i++) {
            if(!dict.containsKey(words.get(i))){
                ArrayList<String> nextWord = new ArrayList<String>();
                nextWord.add(words.get(i+1));
                dict.put(words.get(i), nextWord);
            }
            else {
                ArrayList<String> addedNewWord = dict.get(words.get(i));
                addedNewWord.add(words.get(i+1));
                dict.put(words.get(i), addedNewWord);
            }
        }

        for(Map.Entry<String, ArrayList<String>> entry : dict.entrySet()) {

            //put key and value separated by a colon
            writeDict.write( entry.getKey() + ":" + entry.getValue());

            //new line
            writeDict.newLine();
        }

        writeDict.close();
        
    }
}
