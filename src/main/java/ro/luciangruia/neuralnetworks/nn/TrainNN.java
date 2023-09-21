package ro.luciangruia.neuralnetworks.nn;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_EPOCHS;

public class TrainNN {


    public static List<double[]> train(double[][] trainingInputs, double[][] expectedOutputs, NN network) {
        List<double[]> predictedOutputs = new ArrayList<>();
        for (int epoch = 0; epoch < NN_EPOCHS; epoch++) {
            for (int i = 0; i < trainingInputs.length; i++) {
                double[] inputsVector = trainingInputs[i];
                // Feed forward
                predictedOutputs.add(network.networkOutputs(inputsVector));
                // Backpropagation
                network.backpropagation(expectedOutputs[i]);
                // Adjust weights
                network.adjustAllWeights(inputsVector);
            }
        }
        return predictedOutputs;
    }

}
