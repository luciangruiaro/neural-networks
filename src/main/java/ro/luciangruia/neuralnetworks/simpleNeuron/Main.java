package ro.luciangruia.neuralnetworks.simpleNeuron;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_EPOCHS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_INPUT_SIZE;
import static ro.luciangruia.neuralnetworks.simpleNeuron.Test.testSingleNeuron;
import static ro.luciangruia.neuralnetworks.simpleNeuron.Train.trainMoreEpochs;

public class Main {

    public static void main(String[] args) {
        // Training Data set
        double[][] trainingDataSet = {
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0},
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 1}
        };
        double[] expectedOutputs = {0, 0, 0, 0, 1, 1, 1, 1};

        // Test scenario
        double[] newInput = {0, 0, 0};

        // Our neuron
        Neuron singleNeuron = new Neuron(SN_INPUT_SIZE);

        // Initial
        testSingleNeuron(newInput, singleNeuron);

        // Training
        trainMoreEpochs(trainingDataSet, expectedOutputs, singleNeuron, SN_EPOCHS);

        // Test after training
        testSingleNeuron(newInput, singleNeuron);
    }
}
