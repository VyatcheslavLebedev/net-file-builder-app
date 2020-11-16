package ru.impfields.netfilebuilderapp;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.datashuffler.Shuffler;
import ru.impfields.netfilebuilderapp.generationtrainmain.*;
import ru.impfields.netfilebuilderapp.models.Limits;
import ru.impfields.netfilebuilderapp.verifyer.Counter;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class RunProgram{

    private final FileWriter fileWriter;

    private final CSVWriter csvWriter;

    private BufferedReader bufferedReader;

    private FileReader fileReader;


    private ConstantA constantA;

    private ConstantB constantB;

    private Limits limits;

    private FunctionTrajectory functionTrajectory;

    private Shuffler shuffler;

    private Counter counter;

    @Autowired
    public RunProgram(FileWriter fileWriter1, CSVWriter csvWriter1,
                      ConstantA constantA1, ConstantB constantB1,
                      FunctionTrajectory functionTrajectory1,Limits limits1,
                      BufferedReader bufferedReader,FileReader fileReader,Counter counter,Shuffler shuffler) {
        fileWriter = fileWriter1;
        csvWriter = csvWriter1;
        constantA = constantA1;
        constantB = constantB1;
        functionTrajectory = functionTrajectory1;
        limits = limits1;
        this.bufferedReader = bufferedReader;
        this.fileReader = fileReader;
        this.counter = counter;
        this.shuffler = shuffler;
    }

    @PostConstruct
    public void run() throws IOException {
        GenerateTrainMain generateTrainMain = new GenerateKvadraticLinearAproximation(fileWriter,csvWriter,constantA,
                constantB,functionTrajectory,limits);
        generateTrainMain.generate();
        counter.count();
        shuffler.shuffle();
        //fileWriter.close();
        //csvWriter.close();
        //fileReader.close();
        //bufferedReader.close();

    }
}
