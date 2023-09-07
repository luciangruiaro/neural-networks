package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.api.PythonApi;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_EPOCHS;
import static ro.luciangruia.neuralnetworks.simpleNeuron.Test.testSingleNeuron;
import static ro.luciangruia.neuralnetworks.simpleNeuron.Train.trainMoreEpochs;

@Service
public class SimpleNeuronService {

    @Autowired
    Neuron singleNeuron;

    @Autowired
    PythonApi pythonApi;

    private static List<NeuronState> neuronStates = new ArrayList<>();

    @SneakyThrows
    public void main() {
        // Training Data set
        double[][] trainingDataSet = {{0, 0, 0}, {0, 0, 1},
//                {0, 1, 0},
//                {0, 1, 1},
//                {1, 0, 0},
//                {1, 0, 1},
                {1, 1, 0}, {1, 1, 1}};
//        double[] expectedOutputs = {0, 0, 0, 0, 1, 1, 1, 1};
        double[] expectedOutputs = {0, 0, 1, 1};

        // Test scenario
        double[] newInput = {1, 0, 1};

        // Initial
        testSingleNeuron(newInput, singleNeuron, "initial");

        // Training
        neuronStates = trainMoreEpochs(trainingDataSet, expectedOutputs, singleNeuron, SN_EPOCHS);

        // Test after training
        testSingleNeuron(newInput, singleNeuron, "after training");

        // Visualise how neuron learned
        pythonApi.pyPlotNeuronStates(neuronStates);
    }
}
