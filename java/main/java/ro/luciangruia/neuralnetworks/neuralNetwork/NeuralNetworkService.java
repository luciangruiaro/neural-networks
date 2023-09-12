package ro.luciangruia.neuralnetworks.neuralNetwork;

import org.springframework.stereotype.Service;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_INPUT_RESOLUTION;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_NEURONS_OUTPUT;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_NO_NEURONS_PER_HIDDEN_LAYERS;

@Service
public class NeuralNetworkService {

    public static void main(String[] args) {

        // configure hidden layers
        int[] hiddenSizes = new int[NEURAL_NETWORK_NO_HIDDEN_LAYERS];
        for (int i = 0; i < NEURAL_NETWORK_NO_HIDDEN_LAYERS; i++) {
            hiddenSizes[i] = NEURAL_NETWORK_NO_NEURONS_PER_HIDDEN_LAYERS;
        }

        NeuralNetwork neuralNetwork = new NeuralNetwork(NEURAL_NETWORK_INPUT_RESOLUTION, hiddenSizes, NEURAL_NETWORK_NO_NEURONS_OUTPUT);

    }
}
