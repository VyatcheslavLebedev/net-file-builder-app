package ru.impfields.netfilebuilderapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limits {

    private double minDepth = 90.0;
    private int numberPointsTime = 30;


    private double minAlpha = 1.0;
    private double minBeta = 0.01;
    private double minGamma = 0.1;
    private double minDelta = 0.1;


    private double maxAlpha = 50.0;
    private double maxBeta = 1.0;
    private double maxGamma = 5.0;
    private double maxDelta = 1.0;

    private double stepAlpha = 2.0;
    private double stepBeta = 0.01;
    private double stepGamma = 0.1;
    private double stepDelta = 0.1;
}
