package ro.luciangruia.neuralnetworks.neuralNetwork;

import ro.luciangruia.neuralnetworks.neuralNetwork.network.NeuralNetwork;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_INPUT_RESOLUTION;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_NEURONS_OUTPUT;

public class NeuralNetworkService {

    public static void main(String[] args) {
        int[] hiddenSizes = {16, 16}; // Add or remove layer sizes as needed

        NeuralNetwork neuralNetwork = new NeuralNetwork(
                NEURAL_NETWORK_INPUT_RESOLUTION,
                hiddenSizes,
                NEURAL_NETWORK_NO_NEURONS_OUTPUT);

    }
}
