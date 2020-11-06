package generationtrainconst;

import models.Constants;

public class ConstantA {

    public double getA(Constants constants, double minDepth){
        return (constants.getAlpha() - constants.getDelta() * minDepth - constants.getSigma())
        /(2 * constants.getDelta());
    }
}
