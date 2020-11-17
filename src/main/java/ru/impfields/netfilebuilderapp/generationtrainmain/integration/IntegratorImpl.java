package ru.impfields.netfilebuilderapp.generationtrainmain.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.models.Limits;

import java.util.List;

@Component
public class IntegratorImpl implements Integrator{

    private Limits limits;

    @Autowired
    public IntegratorImpl(Limits limits) {
        this.limits = limits;
    }


    @Override
    public double integrate(List<Double> points,double stepT) {
        int z = 4;
        double s;

        s = points.get(0)*points.get(points.size()-1);

        for(int i = 1; i < limits.getNumberPoints(); i++){
            s = s + z * points.get(i);
            z = 6 - z;
        }
        return (s * stepT)/3;
    }
}
