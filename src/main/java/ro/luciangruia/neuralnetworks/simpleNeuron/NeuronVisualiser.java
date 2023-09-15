package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.Data;

@Data
public class NeuronVisualiser {

    public int noInputs;
    public double[] weights;
    public double output;
    public double outputClassified;
    public double biasWeight;
    public double[] inputs;
    public boolean activated;

    public NeuronVisualiser(SNeuron sNeuron, double[] inputs) {
        this.noInputs = sNeuron.noInputs;
        this.weights = sNeuron.weights;
        this.output = sNeuron.output(inputs);
        this.outputClassified = sNeuron.classify(inputs);
        this.biasWeight = sNeuron.biasWeight;
        this.inputs = inputs;
        this.activated = this.output >= 0.5;
    }
}
