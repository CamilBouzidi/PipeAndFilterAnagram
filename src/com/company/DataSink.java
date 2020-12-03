package com.company;

import java.io.PipedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSink extends Thread {
    protected PipedReader pr;
    protected String ans;

    public DataSink(PipedReader pr) {
        this.pr = pr;
        ans = "";
    }

    public void run() {
        try {
            int currentData = pr.read();
            String currentWord = "";
            System.out.println("Final list of anagrams: ");

            while (currentData != -1 ){
                char currentChar = (char) currentData;

                //List or word is of same anagrams is finished, move on to next word or set of anagrams
                if ((currentChar == ' ') || (currentChar == '\n')){
                    System.out.print(currentWord+currentChar);
                    currentWord = "";
                }

                if (currentChar != ' '){
                    String charToString = String.valueOf(currentChar);
                    currentWord += charToString;
                }

                currentData = pr.read();
            }
            pr.close();

        } catch (Exception e) {

        }
    }
}
