package ro.luciangruia.neuralnetworks.neuralNetwork;

import org.springframework.stereotype.Component;

@Component
public class Train {

    public void train(NeuralNetwork network, double[][] trainingInputs, double[][] expectedOutputs, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainingInputs.length; i++) {
                double[] inputs = trainingInputs[i];
                double[] expected = expectedOutputs[i];

                // Forward pass
                double[] inputLayerOutputs = network.inputLayer.computeOutputs(inputs);
                double[][] hiddenLayerOutputs = new double[network.hiddenLayers.size()][];
                for (int h = 0; h < network.hiddenLayers.size(); h++) {
                    hiddenLayerOutputs[h] = network.hiddenLayers.get(h).computeOutputs(inputLayerOutputs);
                    inputLayerOutputs = hiddenLayerOutputs[h];
                }
                double[] outputLayerOutputs = network.outputLayer.computeOutputs(inputLayerOutputs);

                // Calculate deltas for output layer
                for (int j = 0; j < network.outputLayer.neurons.length; j++) {
                    network.outputLayer.neurons[j].computeDeltaForOutput(expected[j], outputLayerOutputs[j]);
                }

                // Backward pass
                for (int h = network.hiddenLayers.size() - 1; h >= 0; h--) {
                    Layer hiddenLayer = network.hiddenLayers.get(h);
                    Layer nextLayer = (h == network.hiddenLayers.size() - 1) ? network.outputLayer : network.hiddenLayers.get(h + 1);
                    for (int j = 0; j < hiddenLayer.neurons.length; j++) {
                        Neuron neuron = hiddenLayer.neurons[j];
                        double[] nextLayerDeltas = new double[nextLayer.neurons.length];
                        double[] nextLayerWeights = new double[nextLayer.neurons.length];
                        for (int k = 0; k < nextLayer.neurons.length; k++) {
                            nextLayerDeltas[k] = nextLayer.neurons[k].delta;
                            nextLayerWeights[k] = nextLayer.neurons[k].weights[j];
                        }
                        neuron.computeDeltaForHidden(hiddenLayerOutputs[h], nextLayerDeltas, nextLayerWeights);
                    }
                }

                // Adjust weights using deltas
                for (Layer layer : network.hiddenLayers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.adjustWeights(inputs);
                    }
                }
                for (Neuron neuron : network.outputLayer.neurons) {
                    neuron.adjustWeights(inputs);
                }
            }
        }
    }

}
