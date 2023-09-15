package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.api.PythonApi;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_EPOCHS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_INPUT_SIZE;

@Service
public class SimpleNeuronService {

    @Autowired
    PythonApi pythonApi;

    static SNeuron singleSNeuron = new SNeuron(SN_INPUT_SIZE);

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

        Test.testSingleNeuron(newInput, singleSNeuron, "initial");

        neuronStates = Train.trainMoreEpochs(trainingDataSet, expectedOutputs, singleSNeuron, SN_EPOCHS);

        pythonApi.pyPlotNeuronStates(neuronStates);

        Test.testSingleNeuron(newInput, singleSNeuron, "after training");

        return new NeuronVisualiser(singleSNeuron, newInput);
    }

    public NeuronVisualiser getJustCreatedNeuron() {
        return new NeuronVisualiser(singleSNeuron, newInput);
    }
}
