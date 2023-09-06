package ro.luciangruia.neuralnetworks.simpleNeuron;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_LEARNING_RATE;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradient;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.meanSquaredLoss;

public class NeuronPrinter {

    protected static void printNeuronState(Neuron neuron, double expected, double prediction) {
        System.out.println("Weights: " + java.util.Arrays.toString(neuron.weights));
        System.out.println("Loss: " + meanSquaredLoss(expected, prediction)); // Squared loss loss
        System.out.println("Gradient Descent: " + gradientDescent(gradient(expected, prediction, prediction), SN_LEARNING_RATE));
        System.out.println("Activation Output: " + prediction);
    }

    protected static void printNeuronState(Neuron neuron) {
        System.out.println("Weights: " + java.util.Arrays.toString(neuron.weights));
    }
}
