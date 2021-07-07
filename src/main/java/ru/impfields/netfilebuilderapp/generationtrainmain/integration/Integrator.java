package ru.impfields.netfilebuilderapp.generationtrainmain.integration;

import java.util.List;

public interface Integrator {

   double integrate(List<Double> points,double stepT);
}
