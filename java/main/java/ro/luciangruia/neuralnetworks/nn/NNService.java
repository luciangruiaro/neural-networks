package ro.luciangruia.neuralnetworks.nn;

import org.springframework.stereotype.Service;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;

@Service
public class NNService {

    public static void main(String[] args) {

        // configure hidden layers
        int[] hiddenSizes = new int[NN_NO_HIDDEN_LAYERS];
        for (int i = 0; i < NN_NO_HIDDEN_LAYERS; i++) {
            hiddenSizes[i] = NN_NO_NEURONS_PER_HIDDEN_LAYERS;
        }

        NN neuralNetwork = new NN();

    }
}
