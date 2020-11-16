package ru.impfields.netfilebuilderapp.datashuffler;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class Shuffler{

    private static final int LIMIT_ARRAY = 30000;

    private BufferedReader bufferedReader;

    @Autowired
    public Shuffler(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void shuffle() throws IOException {

        CSVWriter csvWriter = new CSVWriter(new FileWriter("dataShuffled.csv"),',',CSVWriter.NO_QUOTE_CHARACTER);

        String str = bufferedReader.readLine();

        while(str != null) {
            List<String> list = new ArrayList<>();
            for(int i = 0; i < LIMIT_ARRAY; i++) {
                list.add(str);
                str = bufferedReader.readLine();
                if (str == null) break;
            }
            Collections.shuffle(list);
            for(String i : list){
                String[] csvline = i.split(",");
                csvWriter.writeNext(csvline);
            }
        }
    }
}
