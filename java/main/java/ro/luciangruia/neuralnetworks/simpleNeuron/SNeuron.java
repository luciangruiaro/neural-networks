package ro.luciangruia.neuralnetworks.simpleNeuron;

import org.springframework.stereotype.Component;
import ro.luciangruia.neuralnetworks.helpers.MathHelpers;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_OUTPUT_THRESHOLD;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.generateRandomWeight;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.sigmoid;
import static ro.luciangruia.neuralnetworks.simpleNeuron.NeuronPrinter.printNeuronCreation;
import static ro.luciangruia.neuralnetworks.simpleNeuron.NeuronPrinter.printNeuronState;

@Component
public class SNeuron {

    public int noInputs;
    public double[] weights;
    public double bias = 1.0;
    public double biasWeight;

    public SNeuron(int inputSize) {
        noInputs = inputSize;
        weights = new double[noInputs];
        initializeWeights(noInputs);
        printCretion();
        printState();
    }

    protected double inputSignal(double[] inputValues) {
        if (inputValues.length != noInputs) {
            throw new IllegalArgumentException("Mismatch in number of inputs.");
        }
        double inputSignal = 0;
        for (int i = 0; i < inputValues.length; i++) {
            inputSignal += inputValues[i] * weights[i];
        }
        inputSignal += bias * biasWeight;
        return inputSignal;
    }

    protected double activationFunction(double inputSignal) {
        return sigmoid(inputSignal); // We are choosing sigmoid as our activation function
    }

    protected void initializeWeights(int noInputs) {
        for (int i = 0; i < noInputs; i++) {
            weights[i] = generateRandomWeight();
        }
        biasWeight = generateRandomWeight();
    }

    protected double output(double[] inputValues) {
        return activationFunction(inputSignal(inputValues));
    }

    protected double classify(double[] inputValues) {
        return output(inputValues) >= SN_OUTPUT_THRESHOLD ? 1 : 0;
    }

    protected void adjustWeights(double[] inputs, double expected, double prediction) {
        for (int i = 0; i < noInputs; i++) {
            weights[i] += gradientDescent(MathHelpers.gradient(inputs[i], expected, prediction));
        }
        adjustBiasWeight(expected, prediction);
    }

    protected void adjustBiasWeight(double expected, double prediction) {
        biasWeight += gradientDescent(MathHelpers.gradient(1, expected, prediction));
    }

    protected void printState(double expected, double prediction) {
        printNeuronState(this, expected, prediction);
    }

    protected void printState() {
        printNeuronState(this);
    }

    protected void printCretion() {
        printNeuronCreation(this);
    }

}

