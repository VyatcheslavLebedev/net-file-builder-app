package ru.impfields.netfilebuilderapp.generationtrainmain;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.generationtrainmain.integration.Integrator;
import ru.impfields.netfilebuilderapp.models.LimitsHyperbolic;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

@Component
public class GenerateHyperbolicAproximation implements GenerateTrainMain {

    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private LimitsHyperbolic limitsHyperbolic;
    private FunctionTrajectory functionTrajectory;
    private Integrator integrator;

    @Autowired
    public GenerateHyperbolicAproximation(FileWriter fileWriter, CSVWriter csvWriter, LimitsHyperbolic limitsHyperbolic,
                                          FunctionTrajectory functionTrajectory, Integrator integrator){
        this.csvWriter = csvWriter;
        this.fileWriter = fileWriter;
        this.limitsHyperbolic = limitsHyperbolic;
        this.functionTrajectory = functionTrajectory;
        this.integrator = integrator;

    }

    public void generate(){
        int countPos = 0;
        int countNeg = 0;
        double stepT = 1.0 / limitsHyperbolic.getNumberPoints();
        double stepX = limitsHyperbolic.getMinDepth() / limitsHyperbolic.getNumberPoints();
        double alpha = limitsHyperbolic.getMinAlpha();
        double beta = limitsHyperbolic.getMinBeta();
        double gamma = limitsHyperbolic.getMinGamma();
        double delta = limitsHyperbolic.getMinDelta();
        double sigma = limitsHyperbolic.getMinSigma();

        while(alpha <= limitsHyperbolic.getMaxAlpha()){
            beta = limitsHyperbolic.getMinBeta();
            gamma = limitsHyperbolic.getMinGamma();
            delta = limitsHyperbolic.getMinDelta();
            sigma = limitsHyperbolic.getMinSigma();
            while(beta <= limitsHyperbolic.getMaxBeta()){
                gamma = limitsHyperbolic.getMinGamma();
                delta = limitsHyperbolic.getMinDelta();
                sigma = limitsHyperbolic.getMinSigma();
                while(gamma <= limitsHyperbolic.getMaxGamma()){
                    delta = limitsHyperbolic.getMinDelta();
                    sigma = limitsHyperbolic.getMinSigma();
                    while(delta <= limitsHyperbolic.getMaxDelta()){
                        sigma = limitsHyperbolic.getMinSigma();
                        while(sigma <= limitsHyperbolic.getMaxSigma()) {
                            double timePoint = 0.0;
                            double xPoint = -1 * limitsHyperbolic.getMinDepth();

                            List<Double> ex = new LinkedList<>();
                            List<Double> s = new LinkedList<>();
                            List<Double> gx = new LinkedList<>();

                            List<Double> dataWrite = new ArrayList<>();

                            for (int i = 0; i < limitsHyperbolic.getNumberPoints(); i++) {

                                double exP = alpha * (Math.tanh(sigma*xPoint) + 1.0);
                                double sP = gamma * (Math.tanh(sigma*xPoint) + 1.0) * (sin(2.0 * PI * timePoint) + 1.0);
                                double gxP = delta * sigma *((exp(xPoint) + exp(-1 * xPoint)) / 2.0);
                                ex.add(exP);
                                s.add(sP);
                                gx.add(gxP);
                                timePoint = timePoint + stepT;
                                xPoint = xPoint + stepX;
                            }

                            double a = limitsHyperbolic.getMinA();
                            double b = limitsHyperbolic.getMinB();

                            double aMaxFitness = Double.MIN_VALUE;
                            double bMaxFitness = Double.MIN_VALUE;
                            double fitnessMax = Double.NEGATIVE_INFINITY;
                            double resMaxFitness = -1.0;


                            while(a < limitsHyperbolic.getLimitA()){
                                b = limitsHyperbolic.getMinB();
                                while(b < limitsHyperbolic.getLimitB()){
                                    double res;
                                    if ((abs(b) > limitsHyperbolic.getAmplitudeMin()) && (a <= b)) {
                                        res = 1.0;
                                    } else {
                                        res = 0.0;
                                    }

                                    List<Double> funcPoints = new ArrayList<>();

                                    double t = 0.0;
                                    double x = -1 * limitsHyperbolic.getMinDepth();
                                    for (int i = 0; i < limitsHyperbolic.getNumberPoints(); i++) {
                                        funcPoints.add(alpha*(Math.tanh(sigma* functionTrajectory.trajectoryPoint(t,a,b)))
                                                -gamma * (Math.tanh(sigma * functionTrajectory.trajectoryPoint(t,a,b)) + 1) * (sin(2 * PI * t) + 1)
                                                - 4 * PI * PI * beta * b * b * Math.sin(2* PI * t) * Math.sin(2* PI * t)
                                                - delta * (Math.exp(functionTrajectory.trajectoryPoint(t,a,b)) + exp(-1 * functionTrajectory.trajectoryPoint(t,a,b)))/2.0);
                                        t = t + stepT;
                                        x = x + stepX;
                                            }

                                    double fitness = integrator.integrate(funcPoints, stepT);
                                    if (fitness > fitnessMax) {
                                        fitnessMax = fitness;
                                        aMaxFitness = a;
                                        bMaxFitness = b;
                                        resMaxFitness = res;

                                    }

                                    b = b + limitsHyperbolic.getStepB();
                                }
                                a = a + limitsHyperbolic.getStepA();
                            }
                            if (resMaxFitness > 0.0){
                                countPos++;
                            } else {
                                countNeg++;
                            }
                            dataWrite.addAll(ex);
                            dataWrite.addAll(s);
                            dataWrite.addAll(gx);
                            dataWrite.add(beta);
                            dataWrite.add(resMaxFitness);

                            String[] array = new String[dataWrite.size()];

                            for (int i = 0; i < dataWrite.size(); i++) {
                                array[i] = dataWrite.get(i).toString();
                            }

                            if(resMaxFitness> 0.0) {
                                csvWriter.writeNext(array);
                            }

                            sigma = sigma + limitsHyperbolic.getStepSigma();
                        }

                        delta = delta + limitsHyperbolic.getStepDelta();
                    }
                    gamma = gamma + limitsHyperbolic.getStepGamma();
                }
                beta = beta + limitsHyperbolic.getStepBeta();
            }
            alpha = alpha + limitsHyperbolic.getStepAlpha();
        }





    }



}
