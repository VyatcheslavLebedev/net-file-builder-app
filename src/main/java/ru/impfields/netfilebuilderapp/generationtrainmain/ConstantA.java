package ru.impfields.netfilebuilderapp.generationtrainmain;

import ru.impfields.netfilebuilderapp.models.Constants;
import org.springframework.stereotype.Component;
import ru.impfields.netfilebuilderapp.models.Limits;

@Component
public class ConstantA {



    public double getA(Constants constants,Limits limits){
        return (constants.getAlpha() - constants.getDelta() * limits.getMinDepth() - constants.getSigma())
        /(2 * constants.getDelta());
    }
}
