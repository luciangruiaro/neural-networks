package ro.luciangruia.neuralnetworks.neuralNetwork.network;

import org.springframework.stereotype.Component;
import ro.luciangruia.neuralnetworks.neuralNetwork.layers.Layer;

import java.util.ArrayList;
import java.util.List;

@Component
public class NeuralNetwork {

    public Layer inputLayer;
    public List<Layer> hiddenLayers;
    public Layer outputLayer;

    public NeuralNetwork(int inputSize, int[] hiddenSizes, int outputSize) {
        this.inputLayer = new Layer(inputSize, 0); // input layer doesn't have any weights or biases

        // Initialize hidden layers
        this.hiddenLayers = new ArrayList<>();
        int previousLayerSize = inputSize;
        for (int size : hiddenSizes) {
            hiddenLayers.add(new Layer(size, previousLayerSize));
            previousLayerSize = size;
        }
        this.outputLayer = new Layer(outputSize, previousLayerSize);
    }

    public void adjustWeights(double[][] inputs, double[] expected, double[] predictions) {
        // Assumption: Each row of inputs corresponds to inputs for a layer.
        for (int i = 0; i < hiddenLayers.size(); i++) {
            hiddenLayers.get(i).adjustWeights(inputs[i], expected, predictions);
        }
        outputLayer.adjustWeights(inputs[inputs.length - 1], expected, predictions);
    }

    public void adjustBiasWeight(double[] expected, double[] predictions) {
        for (Layer layer : hiddenLayers) {
            layer.adjustBiasWeight(expected, predictions);
        }
        outputLayer.adjustBiasWeight(expected, predictions);
    }


}
