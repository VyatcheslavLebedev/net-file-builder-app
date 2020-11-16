package ru.impfields.netfilebuilderapp.generationtrainmain;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.impfields.netfilebuilderapp.models.Constants;
import ru.impfields.netfilebuilderapp.models.Limits;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

public class GenerateHyperbolicAproximation implements GenerateTrainMain {

    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private Limits limits;
    private FunctionTrajectory functionTrajectory;

    @Autowired
    GenerateHyperbolicAproximation(FileWriter fileWriter, CSVWriter csvWriter,Limits limits,
                                   FunctionTrajectory functionTrajectory){
        this.csvWriter = csvWriter;
        this.fileWriter = fileWriter;
        this.limits = limits;
        this.functionTrajectory = functionTrajectory;

    }

    public void generate(){
        double a = limits.getMinA();
        double b = limits.getMinB();

        while(a < limits.getMinA()){
            b = limits.getMinB();
            while(b < limits.getLimitB()){
                double res;
                if ((abs(b) > limits.getAmplitudeMin()) && (a < b)) {
                    res = 1.0;
                }
                else {
                    res = 0.0;
                }

                double stepT = 1.0 / limits.getNumberPointsTime();
                double alpha = limits.getMinAlpha();
                double beta = limits.getMinBeta();
                double gamma = limits.getMinGamma();
                double delta = limits.getMinDelta();

                List<Double> exMaxFitness = new LinkedList<>();
                List<Double> sMaxFitness = new LinkedList<>();
                List<Double> gxMaxFitness = new LinkedList<>();
                Double betaMaxFitness = Double.MIN_VALUE;

                while(alpha <= limits.getMaxAlpha()){
                    beta = limits.getMinBeta();
                    gamma = limits.getMinGamma();
                    delta = limits.getMinDelta();
                    while(beta <= limits.getMaxBeta()){
                        gamma = limits.getMinGamma();
                        delta = limits.getMinDelta();
                        while(gamma <= limits.getMaxGamma()){
                            delta = limits.getMinDelta();
                            while(delta <= limits.getMaxDelta()){
                                List<Double> ex = new LinkedList<>();
                                List<Double> s = new LinkedList<>();
                                List<Double> gx = new LinkedList<>();
                                List<Double> funcPoints = new ArrayList<>();
                                double t = 0.0;
                                for(int i = 0; i <limits.getNumberPointsTime(); i++){
                                    double point = functionTrajectory.trajectoryPoint(t,a,b);
                                    double exP = alpha* (Math.tanh(point) + 1);
                                    double sP = gamma * (Math.tanh(point) + 1) * (sin(2* PI * t) + 1);
                                    double gxP = delta * ((exp(point) + exp(-1 * point)) / 2);
                                    ex.add(exP);
                                    s.add(sP);
                                    gx.add(gxP);
                                    funcPoints.add(exP + sP + gxP - beta * 4* PI * PI * b * b * sin(2 * PI * t) * sin(2* PI *t));
                                    t = t + stepT;
                                }

                                delta = delta + limits.getStepDelta();
                            }
                            gamma = gamma + limits.getStepGamma();
                        }
                        beta = beta + limits.getStepBeta();
                    }
                    alpha = alpha + limits.getStepAlpha();
                }

                b = b + limits.getStepB();
            }
            a = a + limits.getStepA();
        }



    }



}
