package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradient;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.meanSquaredLoss;

@Service
@Slf4j
public class NeuronPrinter {

    protected static void printNeuronState(Neuron neuron, double expected, double prediction) {
        printNeuronState(neuron);
        log.info("Loss: {}", meanSquaredLoss(expected, prediction)); // Squared loss loss
        log.info("Gradient: {}", gradient(expected, prediction, prediction));
        log.info("Gradient Descent: {}", gradientDescent(gradient(expected, prediction, prediction)));
        log.info("Activation Output: {}", prediction);
    }

    protected static void printNeuronState(Neuron neuron) {
        log.info("---------- Neuron state ----------");
        log.info("Weights: " + Arrays.toString(neuron.weights));
        log.info("Bias Weight: " + neuron.biasWeight);
        log.info("----------------------------------");
    }

    public static void printNeuronCreation(Neuron neuron) {
        log.info("Neuron created with {} inputs.", neuron.noInputs);
    }
}
