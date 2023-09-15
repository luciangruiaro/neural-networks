package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Train {

    private static final List<NeuronState> neuronStates = new ArrayList<>();

    protected static List<NeuronState> trainMoreEpochs(double[][] trainingDataSet, double[] expectedOutputs, SNeuron sNeuron, int epocsLimit) {
        log.info("Start training for {} epochs...", epocsLimit);
        sNeuron.printState();
        neuronStates.add(new NeuronState(sNeuron));

        for (int epoch = 0; epoch < epocsLimit; epoch++) {
            for (int i = 0; i < trainingDataSet.length; i++) {
                train(trainingDataSet[i], expectedOutputs[i], sNeuron);
            }
            neuronStates.add(new NeuronState(sNeuron));
        }

        log.info("Finished {} epochs! Training done.", epocsLimit);
        sNeuron.printState();

        return neuronStates;
    }

    private static void train(double[] inputValues, double expectedValue, SNeuron sNeuron) {
        // Make a prediction with the current state of the neuron
        double prediction = sNeuron.output(inputValues);
        // Based on the prediction, calculate the loss and adjust the weights
        sNeuron.adjustWeights(inputValues, expectedValue, prediction);
        // Print the current state of the neuron
        // singleNeuron.printState(expectedValue, singleNeuron.output(inputValues));
    }
}
