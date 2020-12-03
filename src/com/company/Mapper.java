package com.company;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.*;

public class Mapper extends Thread {
    protected PipedReader pr;
    protected PipedWriter pw;

    public Mapper(PipedReader pr, PipedWriter pw) {
        this.pr = pr;
        this.pw = pw;
    }

    public void run() {
        try {
            int currentData = pr.read();
            String currentPair = "";
            String currentWord = "";
            String currentKey = "";
            Map<String, List<String>> anagramDictionary = new HashMap(); //To be used to keep track of anagrams
            while (currentData != -1 ){
                char currentChar = (char) currentData;

                //end of entry reached. Split the word and key:
                if (currentChar == ' '){
                    String[] splitted = currentPair.split("/");
                    currentWord = splitted[0];
                    currentKey = splitted[1];
                    validateEntry(currentWord,currentKey,anagramDictionary);
                    currentWord = "";
                    currentKey = "";
                    currentPair = "";

                }
                //Keep receiving word/key pair
                if (currentChar != ' '){
                    String charToString = String.valueOf(currentChar);
                    currentPair += charToString;
                }

                currentData = pr.read();
            }
            pr.close();
            //Send anagrams together in a single line. Jump to next line any time to anagram type is met.
            for (Map.Entry<String, List<String>> e: anagramDictionary.entrySet()){
                for (String s: e.getValue()){
                    pw.write(s+" ");
                }
                pw.write("\n");
            }
            pw.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String sortString(String s){
        char tempArray[] = s.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    public boolean isEmptyOrSpace (String s){
        return s == "" || s == " ";
    }
    /*
    * Here, we add every word to the list associated to its sorted key. All words in the same list are anagrams of each other.
    * */
    public void validateEntry(String word, String key, Map<String,List<String>> dictionary) {
        if (dictionary.size() == 0){
            ArrayList<String> list = new ArrayList();
            list.add(word);
            dictionary.put(key,list);
        } else {
            boolean keyExists = false;
            for (Map.Entry<String, List<String>> e: dictionary.entrySet()){
                if (key.equals(e.getKey())){
                    keyExists = true;
                    if (e.getValue().indexOf(word) == -1 ){
                        e.getValue().add(word);
                        return;
                    }
                }
            }
            if (!keyExists){
                ArrayList<String> list = new ArrayList();
                list.add(word);
                dictionary.put(key,list);
            }
        }
    }
}
