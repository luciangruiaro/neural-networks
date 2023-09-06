package ro.luciangruia.neuralnetworks.simpleNeuron;

public class Train {

    protected static void trainMoreEpochs(double[][] trainingDataSet, double[] expectedOutputs, Neuron singleNeuron, int epocsLimit) {
        for (int epoch = 0; epoch < epocsLimit; epoch++) {
            System.out.println("Start training. Epoch: " + epoch);
            for (int i = 0; i < trainingDataSet.length; i++) {
                train(trainingDataSet[i], expectedOutputs[i], singleNeuron);
                // Print the current state of the neuron
                singleNeuron.printState(expectedOutputs[i], singleNeuron.output(trainingDataSet[i]));
            }
        }
    }

    private static void train(double[] inputValues, double expectedValue, Neuron singleNeuron) {
        // Make a prediction with the current state of the neuron
        double prediction = singleNeuron.output(inputValues);
        // Based on the prediction, calculate the loss and adjust the weights
        singleNeuron.adjustWeights(inputValues, expectedValue, prediction);
    }
}
