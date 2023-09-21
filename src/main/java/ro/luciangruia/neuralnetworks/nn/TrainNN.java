package ro.luciangruia.neuralnetworks.nn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_EPOCHS;

@Service
@Slf4j
public class TrainNN {

    @Autowired
    NN nn;

    @Autowired
    TestNN testNN;

    public List<double[]> go(double[][] trainingInputs, double[][] expectedOutputs) {
        List<double[]> predictedOutputs = new ArrayList<>();
        for (int epoch = 0; epoch < NN_EPOCHS; epoch++) {
            double[] lastExpectedOutputs = new double[0];
            for (int i = 0; i < trainingInputs.length; i++) {
                nn.inputsVector = trainingInputs[i];
                // --> Feed forward. We basically make predictions here
                predictedOutputs.add(nn.networkOutputs());
                // --> Backpropagation. We review what we knew and adjust our knowledge
                nn.backpropagation(expectedOutputs[i]);
                nn.adjustAllWeights();
                lastExpectedOutputs = expectedOutputs[i];
            }
            log.info("Epoch: {} ended.", epoch);
        }
        return predictedOutputs;
    }

}
