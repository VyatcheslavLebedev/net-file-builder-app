package ru.impfields.netfilebuilderapp.generationtrainmain.integration;

import java.util.List;

public interface Integrator {

    public double integrate(List<Double> points,double stepT);
}
