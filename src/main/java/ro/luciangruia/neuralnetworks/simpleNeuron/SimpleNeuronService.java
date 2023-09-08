package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.api.PythonApi;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_EPOCHS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_INPUT_SIZE;
import static ro.luciangruia.neuralnetworks.simpleNeuron.Test.testSingleNeuron;
import static ro.luciangruia.neuralnetworks.simpleNeuron.Train.trainMoreEpochs;

@Service
public class SimpleNeuronService {

    @Autowired
    PythonApi pythonApi;

    static Neuron singleNeuron = new Neuron(SN_INPUT_SIZE);

    private static List<NeuronState> neuronStates = new ArrayList<>();

    // Test scenario
    double[] newInput = {1, 0, 0};

    // Training Data set
    double[][] trainingDataSet = {
            {0, 0, 0},
            {0, 0, 1},
            {0, 1, 0},
            {0, 1, 1},
//            {1, 0, 0},
//            {1, 0, 1},
//            {1, 1, 0},
            {1, 1, 1}};
    //        double[] expectedOutputs = {0, 0, 0, 0, 1, 1, 1, 1};
    double[] expectedOutputs = {0, 0, 0, 0, 1};

    @SneakyThrows
    public NeuronVisualiser getEducatedNeuron() {

        testSingleNeuron(newInput, singleNeuron, "initial");

        neuronStates = trainMoreEpochs(trainingDataSet, expectedOutputs, singleNeuron, SN_EPOCHS);

        pythonApi.pyPlotNeuronStates(neuronStates);

        testSingleNeuron(newInput, singleNeuron, "after training");

        return new NeuronVisualiser(singleNeuron, newInput);
    }

    public NeuronVisualiser getJustCreatedNeuron() {
        return new NeuronVisualiser(singleNeuron, newInput);
    }
}
