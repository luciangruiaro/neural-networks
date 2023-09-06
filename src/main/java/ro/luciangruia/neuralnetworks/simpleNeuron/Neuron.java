package ro.luciangruia.neuralnetworks.simpleNeuron;

import ro.luciangruia.neuralnetworks.helpers.MathHelpers;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_LEARNING_RATE;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.sigmoid;
import static ro.luciangruia.neuralnetworks.simpleNeuron.NeuronPrinter.printNeuronState;

public class Neuron {

    protected int noInputs;
    protected double[] weights;
    protected double outputThreshold = 0.5;
    protected double bias = 1.0;

    protected Neuron(int inputSize) {
        this.noInputs = inputSize;
        initializeWeights(noInputs);
        printState();
    }

    protected double inputSignal(double[] inputValues) {
        double inputSignal = 0;
        for (int i = 0; i < inputValues.length; i++) {
            inputSignal += inputValues[i] * weights[i];
        }
        inputSignal += bias * weights[noInputs];
        return inputSignal;
    }

    protected double activationFunction(double inputSignal) {
        return sigmoid(inputSignal); // We are choosing sigmoid as our activation function
    }

    protected void initializeWeights(int noInputs) {
        weights = new double[noInputs + 1];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() - 0.5;
        }
    }

    protected double output(double[] inputValues) {
        return activationFunction(inputSignal(inputValues)) >= outputThreshold ? 1 : 0;
    }

    protected void adjustWeights(double[] inputs, double expected, double prediction) {
        for (int i = 0; i < weights.length; i++) {
            if (i < noInputs) {
                weights[i] += gradientDescent(MathHelpers.gradient(inputs[i], expected, prediction), SN_LEARNING_RATE);
            } else {
                weights[i] += gradientDescent(MathHelpers.gradient(1, expected, prediction), SN_LEARNING_RATE);
            }
        }
    }

    protected void printState(double expected, double prediction) {
        printNeuronState(this, expected, prediction);
        System.out.println();
    }

    protected void printState() {
        printNeuronState(this);
        System.out.println();
    }


}

