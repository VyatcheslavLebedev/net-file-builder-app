package ru.impfields.netfilebuilderapp;

import com.opencsv.CSVWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.generationtrainmain.GenerateKvadraticLinearAproximation;
import ru.impfields.netfilebuilderapp.generationtrainmain.GenerateTrainMain;

import java.io.FileWriter;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private FileWriter fileWriter;

    @Autowired
    private CSVWriter csvWriter;

    public void run(String...args) {
        GenerateTrainMain generateTrainMain = new GenerateKvadraticLinearAproximation(fileWriter,csvWriter);
        generateTrainMain.generate();
    }
}
