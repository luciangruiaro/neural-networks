package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.Data;

@Data
public class SNState {
    private final double[] weights;
    private final double biasWeight;

    public SNState(SNeuron sNeuron) {
        this.weights = sNeuron.weights.clone();
        this.biasWeight = sNeuron.biasWeight;
    }

}
