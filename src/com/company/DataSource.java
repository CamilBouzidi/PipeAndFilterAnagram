package com.company;

import java.io.PipedWriter;
import java.util.Scanner;

public class DataSource extends Thread {
    protected PipedWriter pw;

    public DataSource(PipedWriter pw) {
        this.pw = pw;
    }

    public void run() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter a list of words separated by spaces. There must not be any space after the final word:");
            String wordList = input.nextLine();
            //For easier splitting/treatment, add space at the end.
            wordList+=(" ");
            pw.write(wordList);
            pw.close();

        } catch (Exception e) {

        }

    }
}
