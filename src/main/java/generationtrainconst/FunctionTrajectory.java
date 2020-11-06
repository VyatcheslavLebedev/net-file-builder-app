package generationtrainconst;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class FunctionTrajectory {
    public double trajectoryPoint(double t,double a,double b) {
        return a + b * cos(2*PI * t);
    }
}
