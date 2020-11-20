package ru.impfields.netfilebuilderapp.fitandevaluatemain;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FitNetwork {

    public void fit() throws IOException, InterruptedException {
        int CLASSES_COUNT = 2;
        int FEATURES_COUNT = 91;
        int seed = 123;

        int numInputs = 91;
        int numOutputs = 2;
        int numHiddenNodes = 2 * numInputs + numOutputs;
        double learningRate = 0.1;

            RecordReader recordReader = new CSVRecordReader(0, ',');
            recordReader.initialize(new FileSplit(new ClassPathResource("dataShuffled.csv").getFile()));

            System.out.println("start to read data");
            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 43999, FEATURES_COUNT,
                    CLASSES_COUNT);

            //DataSet testData = iterator.next();

            DataSet allData = iterator.next();
            //allData.shuffle();

            System.out.println("splitted data start to build configuration");
            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .biasInit(0)
                    .l2(1e-4)
                    .updater(new Adam(0.9))
                    .list()
                    .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                            .weightInit(WeightInit.XAVIER)
                            .activation(Activation.RELU)
                            .build())
                    .layer(1, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                            .weightInit(WeightInit.XAVIER)
                            .activation(Activation.RELU)
                            .build())
                    .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                            .weightInit(WeightInit.XAVIER)
                            .activation(Activation.SOFTMAX)
                            .nIn(numHiddenNodes).nOut(numOutputs).build())
                    .build();

            MultiLayerNetwork model = new MultiLayerNetwork(conf);
            model.init();

            System.out.println("compiled configuration");
            System.out.println("start to fit model");
                DataSet trainData = iterator.next();
                DataNormalization normalizer = new NormalizerStandardize();
                normalizer.fit(trainData);
                normalizer.transform(allData);
                SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65);
                DataSet trainingData = testAndTrain.getTrain();
                DataSet testData = testAndTrain.getTest();
                for (int i = 0; i <46; i++) {
                    model.fit(trainingData);
                }
            //trainingData = iterator.next();
            //trainingData.shuffle();
            //}
            // System.out.println("ended epoch number : " + i);
            //
       /* INDArray output = model.output(testData.getFeatures());
        Evaluation eval = new Evaluation(CLASSES_COUNT);
        //Evaluation eval = new EvaluationBinary();
        eval.eval(testData.getLabels(), output);
        //testData.getLabels();
        //EvaluationBinary eval = new EvaluationBinary(testData.getLabels());
 Evaluation eval = new Evaluation(CLASSES_COUNT);

               INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(CLASSES_COUNT);*/
            //DataSet testData = DataSet.merge(allTestData);
            INDArray output = model.output(testData.getFeatures());
            Evaluation eval = new Evaluation(CLASSES_COUNT);
            eval.eval(testData.getLabels(),output);
            //eval.eval(testData.getLabels(),output);
            System.out.println(eval.stats());
            //File locationToSave = new File("");
            model.save(new File("src/main/resources/mynet.zip"));

            recordReader.close();
        }
    }

