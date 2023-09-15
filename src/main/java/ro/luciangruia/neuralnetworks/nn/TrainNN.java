package ro.luciangruia.neuralnetworks.nn;

import org.springframework.stereotype.Component;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_EPOCHS;

@Component
public class TrainNN {


    public void train(double[][] trainingInputs, double[][] expectedOutputs) {
        NN network = new NN();
        for (int epoch = 0; epoch < NN_EPOCHS; epoch++) {
            for (int i = 0; i < trainingInputs.length; i++) {
                double[] inputsVector = trainingInputs[i];
                // Feed forward
                double[] predictedOutputs = network.networkOutputs(inputsVector);
                // Backpropagation
                network.backpropagation(expectedOutputs[i]);
                // Adjust weights
                network.adjustAllWeights(inputsVector);
            }
        }
    }

}
