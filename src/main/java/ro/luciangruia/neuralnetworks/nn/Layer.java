package ro.luciangruia.neuralnetworks.nn;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_OUTPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;

@Component
public class Layer {
    public Neuron[] neurons;

    public Layer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.neurons = new Neuron[numberOfNeurons];
        for (int i = 0; i < numberOfNeurons; i++) {
            this.neurons[i] = new Neuron(numberOfInputsPerNeuron);
        }
    }

    private static int getNumberOfInputValues() {
        return NN_INPUT_NEURONS;
    }

    public static Layer createInputLayer() {
        return new Layer(getNumberOfInputValues(), 0);
    }

    public static List<Layer> createHiddenLayers() {
        List<Layer> hiddenLayers = new ArrayList<>();
        int previousLayerSize = getNumberOfInputValues(); // set input size as previous layer size for the first hidden layer
        for (int hiddenLayerSize : configureHiddenLayersLayout()) { // loop through hidden layers
            hiddenLayers.add(new Layer(hiddenLayerSize, previousLayerSize));
            previousLayerSize = hiddenLayerSize;
        }
        return hiddenLayers;
    }

    public static int[] configureHiddenLayersLayout() {
        int[] hiddenSizes = new int[NN_NO_HIDDEN_LAYERS];
        for (int i = 0; i < NN_NO_HIDDEN_LAYERS; i++) {
            hiddenSizes[i] = NN_NO_NEURONS_PER_HIDDEN_LAYERS;
        }
        return hiddenSizes;
    }

    public static Layer createOutputLayer() {
        return new Layer(NN_OUTPUT_NEURONS, getNumberOfNeuronsOnLastHiddenLayer());
    }

    private static int getNumberOfNeuronsOnLastHiddenLayer() {
        return configureHiddenLayersLayout()[NN_NO_HIDDEN_LAYERS - 1];
    }

    // This method computes the output for each neuron in the hidden or output layer for the given inputs
    public static double[] calculateOutputsForLayer(double[] inputs, Layer layer) {
        Neuron[] neurons = layer.neurons;
        double[] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].output(inputs);
        }
        return outputs;
    }

}
