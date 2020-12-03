package com.company;


import java.io.PipedReader;
import java.io.PipedWriter;

public class Main {

    public static void main(String[] args) {
        /*
        * DataSource reads the strings to be checked and writes them to pipe.
        * Filter: KeyMaker reads the strings sent to DataSource and appends a "/" and a key (sorted letters of the string) to every string. It sends the word/key combination to pipe.
        * Filter: Mapper reads every word/key combination from the pipe and adds every word with the same key to the same entry of a map. It sends lists of anagrams to the pipe.
        * DataSink takes all of the lists of anagrams (separated with a \n) and prints out the lists.
        * Every reader is connected to the previous writer.
        * */
        try {
            PipedWriter pwDataSource = new PipedWriter();
            DataSource dataSource = new DataSource(pwDataSource);
            PipedReader prKeyMaker = new PipedReader(pwDataSource);
            PipedWriter pwKeyMaker = new PipedWriter();
            KeyMaker keyMaker = new KeyMaker(pwKeyMaker,prKeyMaker);
            PipedReader prMapper = new PipedReader(pwKeyMaker);
            PipedWriter pwMapper = new PipedWriter();
            Mapper mapper = new Mapper(prMapper,pwMapper);
            PipedReader prDataSink = new PipedReader(pwMapper);
            DataSink dataSink = new DataSink(prDataSink);

            dataSource.start();
            keyMaker.start();
            mapper.start();
            dataSink.run();
        } catch (Exception e){
            System.out.println(e);
        }

    }
}
