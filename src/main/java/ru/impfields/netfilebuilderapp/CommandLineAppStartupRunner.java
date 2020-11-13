package ru.impfields.netfilebuilderapp;

import com.opencsv.CSVWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.generationtrainmain.*;
import ru.impfields.netfilebuilderapp.models.Limits;

import java.io.FileWriter;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private FileWriter fileWriter;

    @Autowired
    private CSVWriter csvWriter;

    @Autowired
    private ConstantA constantA;

    @Autowired
    private ConstantB constantB;

    @Autowired
    private FunctionTrajectory functionTrajectory;


    public void run(String...args) {
        Limits limits = new Limits();
        //setLimit If You want
        GenerateTrainMain generateTrainMain = new GenerateKvadraticLinearAproximation(fileWriter,csvWriter,constantA,
                constantB,functionTrajectory,limits);
        generateTrainMain.generate();
    }
}
