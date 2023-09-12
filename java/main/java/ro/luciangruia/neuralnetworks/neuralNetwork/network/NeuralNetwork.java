package ro.luciangruia.neuralnetworks.neuralNetwork.network;

import ro.luciangruia.neuralnetworks.neuralNetwork.layers.Layer;

import java.util.ArrayList;
import java.util.List;

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

}
