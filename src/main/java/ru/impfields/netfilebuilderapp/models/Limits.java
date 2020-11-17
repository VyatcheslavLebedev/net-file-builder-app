package ru.impfields.netfilebuilderapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Limits {

    private double minDepth = 90.0;
    private int numberPoints = 30;


    private double amplitudeMin = 10.0;

    private double minSigma = 1.0;
    private double maxSigma = 3.0;
    private double stepSigma = 1.0;

    private double limitA = -1.0;
    private double limitB = -1.0;
    private double minB = -45.0;
    private double minA = -45.0;

    private double stepA = 1.0;
    private double stepB = 1.0;


    private double minAlpha = 0.5;
    private double minBeta = 0.01;
    private double minGamma = 0.5;
    private double minDelta = 0.1;


    private double maxAlpha = 15.0;
    private double maxBeta = 0.1;
    private double maxGamma = 55.0;
    private double maxDelta = 1.0;

    private double stepAlpha = 2.0;
    private double stepBeta = 0.01;
    private double stepGamma = 1.0;
    private double stepDelta = 0.1;
}
