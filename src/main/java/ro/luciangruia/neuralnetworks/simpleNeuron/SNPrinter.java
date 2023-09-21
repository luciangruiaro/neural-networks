package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradient;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.meanSquaredLoss;

@Slf4j
public class SNPrinter {

    protected static void printNeuronState(SNeuron sNeuron, double expected, double prediction) {
        printNeuronState(sNeuron);
        log.info("Loss: {}", meanSquaredLoss(expected, prediction)); // Squared loss loss
        log.info("Gradient: {}", gradient(expected, prediction, prediction));
        log.info("Gradient Descent: {}", gradientDescent(gradient(expected, prediction, prediction)));
        log.info("Activation Output: {}", prediction);
    }

    protected static void printNeuronState(SNeuron sNeuron) {
        log.info("---------- Neuron state ----------");
        log.info("Weights: " + Arrays.toString(sNeuron.weights));
        log.info("Bias Weight: " + sNeuron.biasWeight);
        log.info("----------------------------------");
    }

    public static void printNeuronCreation(SNeuron sNeuron) {
        log.info("Neuron created with {} inputs.", sNeuron.noInputs);
    }
}
