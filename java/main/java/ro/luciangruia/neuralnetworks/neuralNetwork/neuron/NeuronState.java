package ro.luciangruia.neuralnetworks.neuralNetwork.neuron;

import lombok.Data;

@Data
public class NeuronState {
    private final double[] weights;
    private final double biasWeight;

    public NeuronState(Neuron neuron) {
        this.weights = neuron.weights.clone();
        this.biasWeight = neuron.biasWeight;
    }

}