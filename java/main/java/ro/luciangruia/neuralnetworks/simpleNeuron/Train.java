package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Train {

    private static final List<NeuronState> neuronStates = new ArrayList<>();

    protected static List<NeuronState> trainMoreEpochs(double[][] trainingDataSet, double[] expectedOutputs, Neuron singleNeuron, int epocsLimit) {
        log.info("Start training for {} epochs...", epocsLimit);
        singleNeuron.printState();
        neuronStates.add(new NeuronState(singleNeuron));

        for (int epoch = 0; epoch < epocsLimit; epoch++) {
            for (int i = 0; i < trainingDataSet.length; i++) {
                train(trainingDataSet[i], expectedOutputs[i], singleNeuron);
            }
            neuronStates.add(new NeuronState(singleNeuron));
        }

        log.info("Finished {} epochs! Training done.", epocsLimit);
        singleNeuron.printState();

        return neuronStates;
    }

    private static void train(double[] inputValues, double expectedValue, Neuron singleNeuron) {
        // Make a prediction with the current state of the neuron
        double prediction = singleNeuron.output(inputValues);
        // Based on the prediction, calculate the loss and adjust the weights
        singleNeuron.adjustWeights(inputValues, expectedValue, prediction);
        // Print the current state of the neuron
        // singleNeuron.printState(expectedValue, singleNeuron.output(inputValues));
    }
}
