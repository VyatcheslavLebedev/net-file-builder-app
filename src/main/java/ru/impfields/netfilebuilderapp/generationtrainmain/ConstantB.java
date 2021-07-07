package ru.impfields.netfilebuilderapp.generationtrainmain;

import ru.impfields.netfilebuilderapp.models.Constants;
import org.springframework.stereotype.Component;

import static java.lang.Math.PI;

@Component
public class ConstantB {

    public double getB(Constants constants) {
        return -1 * constants.getSigma()/(8 * constants.getBeta() * PI + 2 * constants.getDelta());
    }
}
