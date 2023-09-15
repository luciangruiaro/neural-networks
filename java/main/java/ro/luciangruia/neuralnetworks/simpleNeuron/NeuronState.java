package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.Data;

@Data
public class NeuronState {
    private final double[] weights;
    private final double biasWeight;

    public NeuronState(SNeuron sNeuron) {
        this.weights = sNeuron.weights.clone();
        this.biasWeight = sNeuron.biasWeight;
    }

}
