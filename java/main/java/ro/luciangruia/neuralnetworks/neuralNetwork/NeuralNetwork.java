package ro.luciangruia.neuralnetworks.neuralNetwork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.luciangruia.neuralnetworks.helpers.MathHelpers;

import java.util.List;

@Component
public class NeuralNetwork {

    @Autowired
    Layer genericLayer;

    public Layer inputLayer;
    public List<Layer> hiddenLayers;
    public Layer outputLayer;
    private double[][] hiddenLayerOutputs;

    public NeuralNetwork() {
        this.inputLayer = genericLayer.createInputLayer();
        this.hiddenLayers = genericLayer.createHiddenLayers();
        this.outputLayer = genericLayer.createOutputLayer();
    }

    // This method computes the output for each neuron in the hidden or output layer for the given inputs
    public double[] computeOutputs(double[] inputs, Layer layer) {
        Neuron[] neurons = layer.neurons;
        double[] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].output(inputs);
        }
        return outputs;
    }

    public double[] networkOutputs(double[] inputsVector, NeuralNetwork network) {
        // Calculate outputs for neurons in hidden layers
        this.hiddenLayerOutputs = new double[network.hiddenLayers.size()][];
        for (int i = 0; i < network.hiddenLayers.size(); i++) {
            this.hiddenLayerOutputs[i] = computeOutputs(inputsVector, network.hiddenLayers.get(i));
            inputsVector = this.hiddenLayerOutputs[i];
        }
        // Calculate outputs for neurons in output layer
        return computeOutputs(this.hiddenLayerOutputs[this.hiddenLayerOutputs.length - 1], network.outputLayer);
    }

    public int predictResult(double[] inputsVector, NeuralNetwork network) {
        // Calculate outputs for neurons in output layer
        return MathHelpers.getMaxIndex( // return index of max value from softmax output
                MathHelpers.softmax( // apply softmax function to output layer
                        networkOutputs(inputsVector, network)));
    }

    public void setNeuronsDeltas(double[] expectedOutputs, double[] outputLayerOutputs, NeuralNetwork network) {
        calculateOutputLayerDeltas(expectedOutputs, outputLayerOutputs, network);
        backwardPassCalculation(network);
    }

    private void calculateOutputLayerDeltas(double[] expectedOutputs, double[] outputLayerOutputs, NeuralNetwork network) {
        for (int j = 0; j < network.outputLayer.neurons.length; j++) {
            network.outputLayer.neurons[j].delta = MathHelpers.computeDeltaForOutput(expectedOutputs[j], outputLayerOutputs[j]);
        }
    }

    private void backwardPassCalculation(NeuralNetwork network) {
        for (int i = network.hiddenLayers.size() - 1; i >= 0; i--) {
            Layer hiddenLayer = network.hiddenLayers.get(i);
            Layer nextLayer = (i == network.hiddenLayers.size() - 1) ? network.outputLayer : network.hiddenLayers.get(i + 1);

            calculateHiddenLayerDeltas(i, hiddenLayer, nextLayer, network);
        }
    }

    private void calculateHiddenLayerDeltas(int layerIndex, Layer hiddenLayer, Layer nextLayer, NeuralNetwork network) {
        for (int j = 0; j < hiddenLayer.neurons.length; j++) {
            double[] nextLayerDeltas = new double[nextLayer.neurons.length];
            double[] nextLayerWeights = new double[nextLayer.neurons.length];

            getNextLayerData(j, nextLayer, nextLayerDeltas, nextLayerWeights);

            network.hiddenLayers.get(layerIndex).neurons[j].delta = MathHelpers.computeDeltaForHidden(hiddenLayerOutputs[layerIndex][j], nextLayerDeltas, nextLayerWeights);
        }
    }

    private void getNextLayerData(int neuronIndex, Layer nextLayer, double[] nextLayerDeltas, double[] nextLayerWeights) {
        for (int k = 0; k < nextLayer.neurons.length; k++) {
            nextLayerDeltas[k] = nextLayer.neurons[k].delta;
            nextLayerWeights[k] = nextLayer.neurons[k].weights[neuronIndex];
        }
    }


}
