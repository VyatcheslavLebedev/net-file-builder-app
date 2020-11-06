package generationtrainconst;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:config-base.properties")
public class GenerateTrainConstImpl implements GenerateTrainConst{

    @Value("constants.number_train_lines_const")
    private Integer numberTrainLinesConst;

    @Value("constants.minAlpha")
    private Double minAlpha;

    @Value("constants.minBeta")
    private Double minBeta;

    @Value("constants.minSigma")
    private Double minSigma;

    @Value("constants.minDelta")
    private Double minDelta;

    @Value("constants.maxAlpha")
    private Double maxAlpha;

    @Value("constants.maxBeta")
    private Double maxBeta;

    @Value("constants.maxSigma")
    private Double maxSigma;

    @Value("constants.maxDelta")
    private Double maxDelta;

    @Value("constants.stepAlpha")
    private Double stepAlpha;

    @Value("constants.stepBeta")
    private Double stepBeta;

    @Value("constants.stepSigma")
    private Double stepSigma;

    @Value("constants.stepDelta")
    private Double stepDelta;

    private final FunctionTrajectory functionTrajectory;
    private final ConstantA constantA;
    private final ConstantB constantB;
    private final CSVWriter csvWriter;


    @Autowired
    public GenerateTrainConstImpl(CSVWriter csvWriter,FunctionTrajectory functionTrajectory,
                                  ConstantB constantB, ConstantA constantA){
        this.functionTrajectory = functionTrajectory;
        this.constantA = constantA;
        this.constantB = constantB;
        this.csvWriter = csvWriter;
    }


    public void generate(){

        for(int i = 0; i < numberTrainLinesConst/2; i++){
            //0
            
            //1
        }
    }
}
