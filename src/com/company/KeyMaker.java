package com.company;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.*;

public class KeyMaker extends Thread {
    protected PipedWriter pw;
    protected PipedReader pr;

    public KeyMaker(PipedWriter pw, PipedReader pr) {
        this.pw = pw;
        this.pr = pr;
    }

    public void run() {
        try {
            int currentData = pr.read();
            String currentKey = "";
            String currentWord = "";
            while (currentData != -1 ){
                char currentChar = (char) currentData;
                if (currentChar == ' '){

                    //End of word is reached
                    if (!isEmptyOrSpace(currentKey) && !isEmptyOrSpace(currentWord)){
                        currentKey = sortString(currentKey);
                        pw.write(currentWord+"/"+currentKey+" ");
                    }
                    //reset temporary vars to ready them for next word.
                    currentWord = "";
                    currentKey = "";
                }

                if (currentChar != ' '){
                    //continue to receive a word.
                    String charToString = String.valueOf(currentChar);
                    currentWord+=(charToString);
                    currentKey+=(charToString);
                }

                currentData = pr.read();
            }
            pr.close();
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
}
