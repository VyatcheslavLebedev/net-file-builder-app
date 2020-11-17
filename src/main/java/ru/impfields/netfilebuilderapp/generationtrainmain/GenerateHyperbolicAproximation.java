package ru.impfields.netfilebuilderapp.generationtrainmain;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.generationtrainmain.integration.Integrator;
import ru.impfields.netfilebuilderapp.models.Limits;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

@Component
public class GenerateHyperbolicAproximation implements GenerateTrainMain {

    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private Limits limits;
    private FunctionTrajectory functionTrajectory;
    private Integrator integrator;

    @Autowired
    public GenerateHyperbolicAproximation(FileWriter fileWriter, CSVWriter csvWriter, Limits limits,
                                          FunctionTrajectory functionTrajectory, Integrator integrator){
        this.csvWriter = csvWriter;
        this.fileWriter = fileWriter;
        this.limits = limits;
        this.functionTrajectory = functionTrajectory;
        this.integrator = integrator;

    }

    public void generate(){

        double stepT = 1.0 / limits.getNumberPoints();
        double stepX = limits.getMinDepth() / limits.getNumberPoints();
        double a = limits.getMinA();
        double b = limits.getMinB();

        while(a < limits.getLimitA()){
            b = limits.getMinB();
            while(b < limits.getLimitB()){
                double res;
                if ((abs(b) > limits.getAmplitudeMin()) && (a < b)) {
                    res = 1.0;
                }
                else {
                    res = 0.0;
                }

                double alpha = limits.getMinAlpha();
                double beta = limits.getMinBeta();
                double gamma = limits.getMinGamma();
                double delta = limits.getMinDelta();
                double sigma = limits.getMinSigma();

                double fitnessMax = Double.MIN_VALUE;

                List<Double> exMaxFitness = new LinkedList<>();
                List<Double> sMaxFitness = new LinkedList<>();
                List<Double> gxMaxFitness = new LinkedList<>();
                Double betaMaxFitness = Double.MIN_VALUE;

                List<Double> dataWrite = new ArrayList<>();

                while(alpha <= limits.getMaxAlpha()){
                    beta = limits.getMinBeta();
                    gamma = limits.getMinGamma();
                    delta = limits.getMinDelta();
                    sigma = limits.getMinSigma();
                    while(beta <= limits.getMaxBeta()){
                        gamma = limits.getMinGamma();
                        delta = limits.getMinDelta();
                        sigma = limits.getMinSigma();
                        while(gamma <= limits.getMaxGamma()){
                            delta = limits.getMinDelta();
                            sigma = limits.getMinSigma();
                            while(delta <= limits.getMaxDelta()){
                                sigma = limits.getMinSigma();
                                while(sigma <= limits.getMaxSigma()) {

                                    List<Double> ex = new LinkedList<>();
                                    List<Double> s = new LinkedList<>();
                                    List<Double> gx = new LinkedList<>();
                                    List<Double> funcPoints = new ArrayList<>();

                                    double t = 0.0;
                                    double x = -1 * limits.getMinDepth();
                                    for (int i = 0; i < limits.getNumberPoints(); i++) {

                                        double exP = alpha * (Math.tanh(sigma*x) + 1.0);
                                        double sP = gamma * (Math.tanh(sigma*x) + 1.0) * (sin(2.0 * PI * t) + 1.0);
                                        double gxP = delta * sigma *((exp(x) + exp(-1 * x)) / 2.0);
                                        ex.add(exP);
                                        s.add(sP);
                                        gx.add(gxP);
                                        funcPoints.add(alpha*(Math.tanh(sigma* functionTrajectory.trajectoryPoint(t,a,b)))
                                                + gamma * (Math.tanh(sigma * functionTrajectory.trajectoryPoint(t,a,b)) + 1) * (sin(2 * PI * t) + 1)
                                                - 4 * PI * PI * beta * b * b * Math.sin(2* PI * t) * Math.sin(2* PI * t)
                                                + delta * (Math.exp(functionTrajectory.trajectoryPoint(t,a,b)) + exp(-1 * functionTrajectory.trajectoryPoint(t,a,b)))/2.0);
                                        t = t + stepT;
                                        x = x + stepX;
                                    }


                                    double fitness = integrator.integrate(funcPoints, stepT);
                                    if (fitness > fitnessMax) {
                                        fitnessMax = fitness;

                                        exMaxFitness.clear();
                                        exMaxFitness.addAll(ex);

                                        sMaxFitness.clear();
                                        sMaxFitness.addAll(s);

                                        gxMaxFitness.clear();
                                        gxMaxFitness.addAll(gx);

                                        betaMaxFitness = beta;
                                    }


                                    sigma = sigma + limits.getStepSigma();
                                }

                                delta = delta + limits.getStepDelta();
                            }
                            gamma = gamma + limits.getStepGamma();
                        }
                        beta = beta + limits.getStepBeta();
                    }
                    alpha = alpha + limits.getStepAlpha();
                }

                dataWrite.addAll(exMaxFitness);
                dataWrite.addAll(sMaxFitness);
                dataWrite.addAll(gxMaxFitness);
                dataWrite.add(betaMaxFitness);
                dataWrite.add(res);

                String[] array = new String[dataWrite.size()];

                for (int i = 0; i < dataWrite.size(); i++) {
                    array[i] = dataWrite.get(i).toString();
                }
                csvWriter.writeNext(array);

                b = b + limits.getStepB();
            }
            a = a + limits.getStepA();
        }



    }



}
