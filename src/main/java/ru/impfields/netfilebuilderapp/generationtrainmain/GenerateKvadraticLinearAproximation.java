package ru.impfields.netfilebuilderapp.generationtrainmain;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.impfields.netfilebuilderapp.models.Constants;
import ru.impfields.netfilebuilderapp.models.Limits;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

public class GenerateKvadraticLinearAproximation implements GenerateTrainMain{

    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private ConstantA constantA;
    private ConstantB constantB;
    private FunctionTrajectory trajectory;
    private Limits limits;


    @Autowired
    public GenerateKvadraticLinearAproximation(FileWriter fileWriter, CSVWriter csvWriter,ConstantA constantA,
                                               ConstantB constantB,FunctionTrajectory trajectory,Limits limits){
        this.csvWriter = csvWriter;
        this.fileWriter = fileWriter;
        this.constantA = constantA;
        this.constantB = constantB;
        this.trajectory = trajectory;
        this.limits = limits;


    }

    public void generate(){
         double stepT = 1.0 / limits.getNumberPointsTime();
         double alpha = limits.getMinAlpha();
         double beta = limits.getMinBeta();
         double gamma = limits.getMinGamma();
         double delta = limits.getMinDelta();

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
                        double t = 0.0;
                        List<Double> dataWrite = new LinkedList<>();
                        List<Double> ex = new LinkedList<>();
                        List<Double> s = new LinkedList<>();
                        List<Double> gx = new LinkedList<>();
                        Constants constants = new Constants(alpha,beta,gamma,delta);
                      for(int i = 0; i <limits.getNumberPointsTime(); i++){
                          double point = trajectory.trajectoryPoint(t,constantA.getA(constants,limits),
                                  constantB.getB(constants));
                          ex.add(alpha*(point + limits.getMinDepth()));
                          s.add(gamma * (-1*(point + limits.getMinDepth())) * cos(2* PI * t) + 1);
                          gx.add(delta * -1 * (point + limits.getMinDepth()/2) * (point + limits.getMinDepth()/2));
                          t = t + stepT;
                      }
                      dataWrite.addAll(ex);
                      dataWrite.addAll(s);
                      dataWrite.addAll(gx);
                      dataWrite.add(beta);

                      if ((abs(constantB.getB(constants)) >= 10) &&
                              (constantA.getA(constants,limits) < constantB.getB(constants))){
                          dataWrite.add(1.0);
                      } else{
                         dataWrite.add(0.0);
                      }

                      String[] itemsArray = new String[dataWrite.size()];
                      csvWriter.writeNext(dataWrite.toArray(itemsArray));

                      delta = delta + limits.getStepDelta();
                    }
                    gamma = gamma + limits.getStepGamma();
                }
                beta = beta + limits.getStepBeta();
            }
            alpha = alpha + limits.getStepAlpha();
         }
    }
}
