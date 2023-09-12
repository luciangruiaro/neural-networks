package ro.luciangruia.neuralnetworks.neuralNetwork;

import org.springframework.stereotype.Component;


@Component
public class Layer {
    public Neuron[] neurons;

    public Layer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.neurons = new Neuron[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            this.neurons[i] = new Neuron(numberOfInputsPerNeuron);
        }
    }

    // This method computes the output for each neuron in the layer for the given inputs
    public double[] computeOutputs(double[] inputs) {
        double[] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].output(inputs);
        }
        return outputs;
    }
}
