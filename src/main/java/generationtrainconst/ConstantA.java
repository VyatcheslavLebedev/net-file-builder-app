package generationtrainconst;

import models.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantA {

    @Value("constants.min_depth")
    private Double minDepth;

    public double getA(Constants constants){
        return (constants.getAlpha() - constants.getDelta() * minDepth - constants.getSigma())
        /(2 * constants.getDelta());
    }
}
