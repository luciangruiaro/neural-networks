package ro.luciangruia.neuralnetworks.simpleNeuron;

import java.util.Arrays;

public class Test {

    public static void testSingleNeuron(double[] newInput, Neuron singleNeuron) {
        System.out.println("Prediction for " + Arrays.toString(newInput) + " is: " + singleNeuron.output(newInput));
        singleNeuron.printState();
    }

}