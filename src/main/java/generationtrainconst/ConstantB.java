package generationtrainconst;

import models.Constants;

import static java.lang.Math.PI;

public class ConstantB {

    public double getB(Constants constants) {
        return -1 * constants.getSigma()/(8 * constants.getBeta() * PI + 2* constants.getDelta());
    }
}
