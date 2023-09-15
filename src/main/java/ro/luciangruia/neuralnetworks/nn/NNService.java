package ro.luciangruia.neuralnetworks.nn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.helpers.MathHelpers;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;

@Service
public class NNService {

    @Autowired
    NN neuralNetwork;

    public static void main(String[] args) {
        // configure hidden layers
        int[] hiddenSizes = new int[NN_NO_HIDDEN_LAYERS];
        for (int i = 0; i < NN_NO_HIDDEN_LAYERS; i++) {
            hiddenSizes[i] = NN_NO_NEURONS_PER_HIDDEN_LAYERS;
        }
        NN neuralNetwork = new NN();
    }

    public void testNN(double[][] inputsMatrix) {
        TestNN.testSN(MathHelpers.flattenMatrix(inputsMatrix), neuralNetwork);
    }

}
