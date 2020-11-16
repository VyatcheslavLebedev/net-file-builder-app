package ru.impfields.netfilebuilderapp.verifyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.models.Limits;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class Counter {

    private Limits limits;
    private BufferedReader bufferedReader;
    private FileReader fileReader;

     @Autowired
     public Counter(BufferedReader bufferedReader, Limits limits, FileReader fileReader){
        this.bufferedReader = bufferedReader;
        this.limits = limits;
        this.fileReader = fileReader;
     }

     public void count() throws IOException {
         int countPos = 0;
         int countNeg = 0;
         int countAll = 0;
         String str = bufferedReader.readLine();
         while(str != null){
             String[] csvline = str.split(",");
             List<String> list = Arrays.asList(csvline);
             double parseNumber = Double.parseDouble(list.get(list.size()-1));
             if (parseNumber >0.0){
                 countPos++;

             }
             if(parseNumber < 1.0){
                 countNeg++;
             }
             countAll++;
             str = bufferedReader.readLine();


         }

         System.out.print("count 1: " + countPos +" count 0: "+ countNeg + " countAll: " + countAll);
     }

}
