package ro.luciangruia.neuralnetworks.neuralNetwork;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_OUTPUT_THRESHOLD;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.generateRandomWeight;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.sigmoid;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.sigmoidDerivativeOfActivatedValue;

public class Neuron {
    public int noInputs;
    public double[] weights;
    public double bias = 1.0;
    public double biasWeight;
    public double delta; // To store the error term

    public Neuron(int inputSize) {
        noInputs = inputSize;
        weights = new double[noInputs];
        initializeWeights(noInputs);
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

    // Adjust weights using the computed delta
    public void adjustWeights(double[] inputs) {
        for (int i = 0; i < noInputs; i++) {
            weights[i] += gradientDescent(delta * inputs[i]);
        }
        adjustBiasWeight();
    }

    public void adjustBiasWeight() {
        biasWeight += gradientDescent(delta);
    }




}
