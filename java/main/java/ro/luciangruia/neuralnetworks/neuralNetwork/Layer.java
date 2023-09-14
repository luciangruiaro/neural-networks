package ro.luciangruia.neuralnetworks.neuralNetwork;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_INPUT_RESOLUTION;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_NEURONS_OUTPUT;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_NEURONS_PER_HIDDEN_LAYERS;


@Component
public class Layer {
    public Neuron[] neurons;

    public Layer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.neurons = new Neuron[numberOfNeurons];
        for (int i = 0; i < numberOfNeurons; i++) {
            this.neurons[i] = new Neuron(numberOfInputsPerNeuron);
        }
    }

    private int getNumberOfInputValues() {
        return (int) Math.pow(NEURAL_NETWORK_INPUT_RESOLUTION, 2);
    }

    public Layer createInputLayer() {
        return new Layer(getNumberOfInputValues(), 0);
    }

    public List<Layer> createHiddenLayers() {
        List<Layer> hiddenLayers = new ArrayList<>();
        int previousLayerSize = getNumberOfInputValues(); // set input size as previous layer size for the first hidden layer
        for (int hiddenLayerSize : configureHiddenLayersLayout()) { // loop through hidden layers
            hiddenLayers.add(new Layer(hiddenLayerSize, previousLayerSize));
            previousLayerSize = hiddenLayerSize;
        }
        return hiddenLayers;
    }

    public int[] configureHiddenLayersLayout() {
        int[] hiddenSizes = new int[NEURAL_NETWORK_NO_HIDDEN_LAYERS];
        for (int i = 0; i < NEURAL_NETWORK_NO_HIDDEN_LAYERS; i++) {
            hiddenSizes[i] = NEURAL_NETWORK_NO_NEURONS_PER_HIDDEN_LAYERS;
        }
        return hiddenSizes;
    }

    public Layer createOutputLayer() {
        return new Layer(NEURAL_NETWORK_NO_NEURONS_OUTPUT, getNumberOfNeuronsOnLastHiddenLayer());
    }

    private int getNumberOfNeuronsOnLastHiddenLayer() {
        return configureHiddenLayersLayout()[NEURAL_NETWORK_NO_HIDDEN_LAYERS - 1];
    }


}
