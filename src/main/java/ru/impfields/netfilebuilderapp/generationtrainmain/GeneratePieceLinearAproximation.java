package ru.impfields.netfilebuilderapp.generationtrainmain;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;

public class GeneratePieceLinearAproximation implements GenerateTrainMain{

    private FileWriter fileWriter;
    private CSVWriter csvWriter;

    @Autowired
    public GeneratePieceLinearAproximation(FileWriter fileWriter, CSVWriter csvWriter){
        this.csvWriter = csvWriter;
        this.fileWriter = fileWriter;


    }

    public void generate(){

    }
}
