package ro.luciangruia.neuralnetworks.nn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.api.PythonApi;
import ro.luciangruia.neuralnetworks.models.ImageData;
import ro.luciangruia.neuralnetworks.models.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_JSON_MODEL;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_REQ_LABEL;
import static ro.luciangruia.neuralnetworks.helpers.DataHelper.loadTrainingData;
import static ro.luciangruia.neuralnetworks.helpers.MathHelpers.flattenMatrix;

@Service
public class NNService {

    @Autowired
    NN nn;

    @Autowired
    TestNN testNN;
    @Autowired
    TrainNN trainNN;

    public static void main(String[] args) throws IOException {
        NN nn = new NN();
        nn.saveToJSON(NN_JSON_MODEL);
        System.out.println("Saved to JSON");
        nn.loadFromJSON(NN_JSON_MODEL);
        System.out.println("Loaded from JSON");

    }

    public String trainNN() throws IOException {
        nn = nn.loadFromJSON(NN_JSON_MODEL);
        // take training images
        List<ImageData> trainingImages = loadTrainingData();
        // train the NN and collect predictions
        List<double[]> predictedOutputs =
                trainNN.go(
                        Pair.transformListToTrainingData(trainingImages).images,
                        Pair.transformListToTrainingData(trainingImages).labels);
        // plot the predicted outputs
        PythonApi.pyPlotPredictedOutputs(predictedOutputs, NN_TRAINING_REQ_LABEL);
        nn.saveToJSON(NN_JSON_MODEL);

        return Arrays.stream(nn.outputLayerOutputs).mapToObj(Double::toString).collect(Collectors.joining(", "));
    }

    public void testNN(double[][] inputsMatrix) {
        nn = nn.loadFromJSON(NN_JSON_MODEL);
        nn.inputsVector = flattenMatrix(inputsMatrix);
        testNN.go();
    }
}
