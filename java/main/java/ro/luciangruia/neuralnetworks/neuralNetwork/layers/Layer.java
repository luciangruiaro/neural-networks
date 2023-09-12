package ro.luciangruia.neuralnetworks.neuralNetwork.layers;

import ro.luciangruia.neuralnetworks.neuralNetwork.neuron.Neuron;

public class Layer {
    public Neuron[] neurons;

    public Layer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.neurons = new Neuron[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            this.neurons[i] = new Neuron(numberOfInputsPerNeuron);
        }
    }

}
