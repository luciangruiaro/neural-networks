package ro.luciangruia.neuralnetworks.neuralNetwork.layers;

import org.springframework.stereotype.Component;
import ro.luciangruia.neuralnetworks.neuralNetwork.neuron.Neuron;

@Component
public class Layer {
    public Neuron[] neurons;

    public Layer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.neurons = new Neuron[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            this.neurons[i] = new Neuron(numberOfInputsPerNeuron);
        }
    }

    public void adjustWeights(double[] inputs, double expected, double prediction) {
        for (Neuron neuron : neurons) {
            neuron.adjustWeights(inputs, expected, prediction);
        }
    }

    public void adjustBiasWeight(double expected, double prediction) {
        for (Neuron neuron : neurons) {
            neuron.adjustBiasWeight(expected, prediction);
        }
    }


}
