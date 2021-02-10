/* Dhruv Sharma
1/27/2021
2/10/2021
This file defines the MarkovChain class by making a Dictionary from the training file and using it to create a Markov Chain.
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

        //reading from and writing to files
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/" + trainingFileName));
        BufferedReader readTests = new BufferedReader(reader); //allows the training file to be read

        FileWriter writer = new FileWriter("src/Dictionary.txt", true);
        BufferedWriter writeDict = new BufferedWriter(writer); //writes to Dictionary.txt file

        String readLine = readTests.readLine(); //reads line from file

        ArrayList<String> words = new ArrayList<String>(); //ArrayList of all words from training file in order
        HashMap<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
        String[] sentencePeriod = readLine.trim().split("\\.|\\!|\\?|\\;"); //splits sentences according to 4 types of punctuation
        Random r = new Random();

        //splits sentences into words
        for(int i = 0; i < sentencePeriod.length; i++) {
            String[] tempArray = sentencePeriod[i].trim().split("\\s+");
            for(int j = 0; j < tempArray.length; j++) {
                words.add(tempArray[j]);
            }
        }

        //adds key value pairs to dictionary
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

        //pastes dict into Dictionary.txt
        for(Map.Entry<String, ArrayList<String>> entry : dict.entrySet()) {

            //put key and value separated by a colon
            writeDict.write( entry.getKey() + ":" + entry.getValue());

            //new line
            writeDict.newLine();
        }

        writeDict.close(); //closes writeDict object

        String lastWord = words.get(words.size()-1);

        String currentKey = words.get(r.nextInt(words.size()-1)); //first word to be displayed to user
        System.out.print(currentKey.substring(0,1).toUpperCase() + currentKey.substring(1)); //uppercase first letter of first word

        //compiling all punctuation from the training file text into an ArrayList
        ArrayList<String> punctuation = new  ArrayList<String>();
        for(int i = 0; i < readLine.length(); i++) {
            if(readLine.charAt(i) == '.' || readLine.charAt(i) == '?' || readLine.charAt(i) == '!') {
                punctuation.add("" + readLine.charAt(i));
            }
        }

        String currentPunctuation; //the punctuation that will be selected when a sentence is completed
        String wordSelected = ""; //one of the words in the value of a key that is selected to be displayed

        //creating and printing out the Markov Chain
        for(int i = 0; i < numWords; i++) {
            //determine word to be displayed if the sentence doesn't end
            if(currentKey != lastWord && dict.get(currentKey).size() > 1) {
                wordSelected = dict.get(currentKey).get(r.nextInt(dict.get(currentKey).size()));
            }
            else if(currentKey != lastWord && dict.get(currentKey).size() == 1) {
                wordSelected = dict.get(currentKey).get(0);
            }

            //determine whether to use wordSelected in Markov Chain or end sentence
            if(currentKey != lastWord && !Character.isUpperCase(wordSelected.charAt(0))) {
                System.out.print(" " + wordSelected);
                currentKey = wordSelected;
            }
            else {
                currentPunctuation = punctuation.get(r.nextInt(punctuation.size()));
                System.out.print(currentPunctuation + " ");
                currentKey = words.get(r.nextInt(words.size()));
                System.out.print(currentKey.substring(0,1).toUpperCase() + currentKey.substring(1));
            }
        }
    }
}