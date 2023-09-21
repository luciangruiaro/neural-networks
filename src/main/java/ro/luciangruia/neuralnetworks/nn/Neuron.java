package ro.luciangruia.neuralnetworks.nn;

import org.json.JSONArray;
import org.json.JSONObject;

import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.generateRandomWeight;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.gradientDescent;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.sigmoid;

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

    // Adjust weights using the computed delta
    public void adjustWeights(double[] inputs) {
        if (inputs.length != noInputs) {
            throw new IllegalArgumentException("Input array size (" + inputs.length + ") does not match expected size (" + noInputs + ").");
        }
        for (int i = 0; i < noInputs; i++) {
            weights[i] += gradientDescent(delta * inputs[i]);
        }
        adjustBiasWeight();
    }

    public void adjustBiasWeight() {
        biasWeight += gradientDescent(delta);
    }

    // JSON representation of the neuron
    public JSONObject toJSON() {
        JSONObject jsonNeuron = new JSONObject();
        jsonNeuron.put("noInputs", noInputs);
        jsonNeuron.put("weights", new JSONArray(weights));
        jsonNeuron.put("biasWeight", biasWeight);
        jsonNeuron.put("delta", delta);
        return jsonNeuron;
    }

    public static Neuron fromJSON(JSONObject jsonNeuron) {
        Neuron neuron = new Neuron(jsonNeuron.getInt("noInputs"));
        JSONArray jsonWeights = jsonNeuron.getJSONArray("weights");
        for (int i = 0; i < jsonWeights.length(); i++) {
            neuron.weights[i] = jsonWeights.getDouble(i);
        }
        neuron.biasWeight = jsonNeuron.getDouble("biasWeight");
        neuron.delta = jsonNeuron.getDouble("delta");
        return neuron;
    }


}
