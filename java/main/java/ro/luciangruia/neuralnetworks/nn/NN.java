package ro.luciangruia.neuralnetworks.nn;

import org.springframework.stereotype.Component;
import ro.luciangruia.neuralnetworks.helpers.MathHelpers;

import java.util.List;

import static ro.luciangruia.neuralnetworks.nn.Layer.calculateOutputsForLayer;
import static ro.luciangruia.neuralnetworks.nn.Layer.createHiddenLayers;
import static ro.luciangruia.neuralnetworks.nn.Layer.createInputLayer;
import static ro.luciangruia.neuralnetworks.nn.Layer.createOutputLayer;

@Component
public class NN {


    public Layer inputLayer;
    public List<Layer> hiddenLayers;
    public Layer outputLayer;
    public double[][] hiddenLayerOutputs;
    public double[] outputLayerOutputs;

    public NN() {
        inputLayer = createInputLayer();
        hiddenLayers = createHiddenLayers();
        outputLayer = createOutputLayer();
    }


    public double[] networkOutputs(double[] inputsVector) {
        // Calculate outputs for neurons in hidden layers
        hiddenLayerOutputs = new double[hiddenLayers.size()][];
        for (int i = 0; i < hiddenLayers.size(); i++) {
            hiddenLayerOutputs[i] = calculateOutputsForLayer(inputsVector, hiddenLayers.get(i));
            inputsVector = hiddenLayerOutputs[i];
        }
        // Calculate outputs for neurons in output layer
        outputLayerOutputs = calculateOutputsForLayer(hiddenLayerOutputs[hiddenLayerOutputs.length - 1], outputLayer);
        return outputLayerOutputs;
    }

    public int predictResult(double[] inputsVector) {
        // Calculate outputs for neurons in output layer
        return MathHelpers.getMaxIndex( // return index of max value from softmax output
                MathHelpers.softmax( // apply softmax function to output layer
                        networkOutputs(inputsVector)));
    }

    public void backpropagation(double[] expectedOutputs) {
        updateDeltasOutputLayer(expectedOutputs, outputLayerOutputs, this);
        backwardPassCalculation(this);
    }

    public static void updateDeltasOutputLayer(double[] expectedOutputs, double[] outputLayerOutputs, NN network) {
        for (int j = 0; j < network.outputLayer.neurons.length; j++) {
            network.outputLayer.neurons[j].delta = MathHelpers.computeDeltaForOutput(expectedOutputs[j], outputLayerOutputs[j]);
        }
    }

    public static void backwardPassCalculation(NN network) {
        for (int i = network.hiddenLayers.size() - 1; i >= 0; i--) {
            Layer hiddenLayer = network.hiddenLayers.get(i);
            Layer nextLayer = (i == network.hiddenLayers.size() - 1) ? network.outputLayer : network.hiddenLayers.get(i + 1);
            updateDeltasHiddenLayers(i, hiddenLayer, nextLayer, network);
        }
    }

    private static void updateDeltasHiddenLayers(int layerIndex, Layer hiddenLayer, Layer nextLayer, NN network) {
        for (int j = 0; j < hiddenLayer.neurons.length; j++) {
            double[] nextLayerDeltas = new double[nextLayer.neurons.length];
            double[] nextLayerWeights = new double[nextLayer.neurons.length];
            getNextLayerData(j, nextLayer, nextLayerDeltas, nextLayerWeights);
            network.hiddenLayers.get(layerIndex).neurons[j].delta = MathHelpers.computeDeltaForHidden(network.hiddenLayerOutputs[layerIndex][j], nextLayerDeltas, nextLayerWeights);
        }
    }

    private static void getNextLayerData(int neuronIndex, Layer nextLayer, double[] nextLayerDeltas, double[] nextLayerWeights) {
        for (int k = 0; k < nextLayer.neurons.length; k++) {
            nextLayerDeltas[k] = nextLayer.neurons[k].delta;
            nextLayerWeights[k] = nextLayer.neurons[k].weights[neuronIndex];
        }
    }

    public void adjustAllWeights(double[] inputsVector) {
        for (Layer layer : hiddenLayers) {
            for (Neuron neuron : layer.neurons) {
                neuron.adjustWeights(inputsVector);
                // Update the input for the next layer using the current layer's outputs
                inputsVector = calculateOutputsForLayer(inputsVector, layer);
            }
        }
        for (Neuron neuron : outputLayer.neurons) {
            neuron.adjustWeights(inputsVector);
        }
    }


}
