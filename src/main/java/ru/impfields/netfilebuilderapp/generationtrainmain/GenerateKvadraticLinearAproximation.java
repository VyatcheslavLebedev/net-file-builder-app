package ru.impfields.netfilebuilderapp.generationtrainmain;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;

public class GenerateKvadraticLinearAproximation implements GenerateTrainMain{

    private FileWriter fileWriter;
    private CSVWriter csvWriter;

    @Autowired
    public GenerateKvadraticLinearAproximation(FileWriter fileWriter, CSVWriter csvWriter){
        this.csvWriter = csvWriter;
        this.fileWriter = fileWriter;


    }

    public void generate(){

    }
}
