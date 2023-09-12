package ro.luciangruia.neuralnetworks.neuralNetwork.neuron;

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

    public NeuronVisualiser(Neuron neuron, double[] inputs) {
        this.noInputs = neuron.noInputs;
        this.weights = neuron.weights;
        this.output = neuron.output(inputs);
        this.outputClassified = neuron.classify(inputs);
        this.biasWeight = neuron.biasWeight;
        this.inputs = inputs;
        this.activated = this.output >= 0.5;
    }
}
